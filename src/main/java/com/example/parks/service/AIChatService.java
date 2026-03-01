package com.example.parks.service;

import com.example.parks.dto.AdvancedSearchCriteria;
import com.example.parks.model.ParkDesign;
import com.example.parks.model.Parks;
import com.example.parks.model.Reports;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class AIChatService {

    private final ChatClient chatClient;
    private final ParksRepository parksRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    // היסטוריית שיחה
    private final Map<String, Deque<String>> conversationHistory = new ConcurrentHashMap<>();

    // הקריטריונים האחרונים לכל משתמש
    private final Map<String, AdvancedSearchCriteria> lastCriteriaByUser = new ConcurrentHashMap<>();

    private static final String ANSWER_SYSTEM = """
            את עוזרת חמודה, חמה ומקצועית לחיפוש פארקים וגינות בישראל.
            את מקבלת לכל פארק:
            • שם, עיר וכתובת
            • חוות דעת משתמשים (remark)
            • דירוג לפי פיצ'ר בשדה ratingsBreakdown (aLot, enough, little, none)
            • דירוג כללי מהמשתמשים (reports) בסולם 1–5
            עני רק על סמך הרשימה שקיבלת – אסור להמציא פארקים שלא מופיעים בה.
            כתבי תמיד בעברית נעימה, קצרה וחמימה.
            """;

    private static final String CRITERIA_SYSTEM = """
            את מומחית לחילוץ כוונות מחיפוש פארקים בישראל.
            תחזירי תמיד JSON תקין בלבד – בלי טקסט נוסף ובלי ```.

            {"city":"שם עיר או null",
             "features":["צל","ספסלים","מתקנים","שולחנות","דשא","נגישות"],
             "minShade":"רב" או "בינוני" או "מועט" או null}
            """;

    public AIChatService(ChatClient.Builder chatClientBuilder,
                         ParksRepository parksRepository) {
        this.chatClient = chatClientBuilder.build();
        this.parksRepository = parksRepository;
    }

    // ===== נקודת כניסה מה-Controller =====
    public String answerFromDb(String userPrompt) {

        // מפתח זיהוי למשתמש (מחובר או אורח עם Chat-Session)
        String userKey = getUserKey();

        Deque<String> history =
                conversationHistory.computeIfAbsent(userKey, k -> new ArrayDeque<>());

        String recentHistory = history.stream()
                .skip(Math.max(0, history.size() - 6))
                .collect(Collectors.joining("\n"));

        // 1. חילוץ קריטריונים מה-GPT (ולאחר מכן חיזוק לפי מילים בטקסט)
        AdvancedSearchCriteria extracted = extractCriteria(userPrompt, recentHistory);

        // הקריטריונים הקודמים של אותו משתמש (אם קיימים)
        AdvancedSearchCriteria previous = lastCriteriaByUser.get(userKey);

        // 2. מיזוג עם הקודמים אם יש, אחרת נשתמש במה שיש עכשיו
        AdvancedSearchCriteria criteria;
        if (!isEmptyCriteria(extracted)) {
            criteria = mergeCriteria(extracted, previous);
        } else {
            criteria = previous; // אולי יש קריטריונים מהודעה קודמת
        }

        // ⭐ אם עדיין אין קריטריונים – ננסה חיפוש טקסט חופשי / כל הפארקים
//        if (isEmptyCriteria(criteria)) {
//            System.out.println("CRITERIA ריק – עוברים לחיפוש טקסט חופשי ב-DB");
//
//            // קודם מנסים לפי כל הטקסט שהמשתמש כתב
//            List<Parks> freeTextParks = parksRepository.searchByText(userPrompt);
//            System.out.println("תוצאה מחיפוש טקסט חופשי (full prompt): " + freeTextParks.size());
//
//            // אם לא מצא כלום – ניקח את כל הפארקים מה-DB
//            if (freeTextParks.isEmpty()) {
//                System.out.println("לא נמצאו פארקים לפי הטקסט – מביא את כל הפארקים מה-DB");
//                freeTextParks = parksRepository.findAllByOrderByUpdateDateDescIdDesc();
//            }
//
//            // אם יש בכלל פארקים ב-DB – נבנה תשובה מהם
//            if (!freeTextParks.isEmpty()) {
//                String response = buildAnswerWithGpt(userPrompt, freeTextParks, recentHistory);
//                saveToHistory(history, userPrompt, response);
//                return response;
//            }
//
//            // אם אפילו ב-DB אין פארקים – רק אז ניפול לפולבאק הטקסטואלי
//            String fallback = "לא הצלחתי להבין מה את מחפשת 😅 " +
//                    "נסי לכתוב עיר ודרישות כמו צל, ספסלים, מתקנים, דשא או נגישות.";
//            saveToHistory(history, userPrompt, fallback);
//            return fallback;
//        }



        // ⭐ אם עדיין אין קריטריונים – ננסה חיפוש טקסט חופשי / כל הפארקים
        if (isEmptyCriteria(criteria)) {
            System.out.println("CRITERIA ריק – עוברים לחיפוש טקסט חופשי ב-DB");

            // 🔹 1. קודם מנסים לחלץ שמות פארקים מהטקסט (למשל "גן סאקר")
            List<String> foundParkNames = extractPossibleParkNames(userPrompt);

            if (!foundParkNames.isEmpty()) {
                System.out.println("נמצאו שמות פארקים בטקסט: " + foundParkNames);

                List<Parks> directMatches = parksRepository.findAll().stream()
                        .filter(p -> foundParkNames.contains(p.getName().toLowerCase()))
                        .toList();

                if (!directMatches.isEmpty()) {
                    String response = buildAnswerWithGpt(userPrompt, directMatches, recentHistory);
                    saveToHistory(history, userPrompt, response);
                    return response;
                }
            }

            // 🔹 2. אם לא הצלחנו לפי שם פארק → חוזרים לחיפוש טקסט חופשי רגיל
            List<Parks> freeTextParks = parksRepository.searchByText(userPrompt);
            System.out.println("תוצאה מחיפוש טקסט חופשי (full prompt): " + freeTextParks.size());

            // אם לא מצא כלום – ניקח את כל הפארקים מה-DB
            if (freeTextParks.isEmpty()) {
                System.out.println("לא נמצאו פארקים לפי הטקסט – מביא את כל הפארקים מה-DB");
                freeTextParks = parksRepository.findAllByOrderByUpdateDateDescIdDesc();
            }

            // אם יש בכלל פארקים ב-DB – נבנה תשובה מהם
            if (!freeTextParks.isEmpty()) {
                String response = buildAnswerWithGpt(userPrompt, freeTextParks, recentHistory);
                saveToHistory(history, userPrompt, response);
                return response;
            }

            // אם אפילו ב-DB אין פארקים – רק אז ניפול לפולבאק הטקסטואלי
            String fallback = "לא הצלחתי להבין מה את מחפשת 😅 " +
                    "נסי לכתוב עיר ודרישות כמו צל, ספסלים, מתקנים, דשא או נגישות.";
            saveToHistory(history, userPrompt, fallback);
            return fallback;
        }




        // 4. שומרים קריטריונים אחרונים למשתמש הזה
        lastCriteriaByUser.put(userKey, criteria);

        // 5. הכנת פרמטרים ל-Repository
        String cityParam = mapCityToDb(criteria.getCity());

        List<String> featuresLower;
        if (criteria.getFeatures() == null || criteria.getFeatures().isEmpty()) {
            featuresLower = Collections.emptyList();
        } else {
            featuresLower = criteria.getFeatures().stream()
                    .filter(Objects::nonNull)
                    .map(String::toLowerCase)
                    .toList();
        }
        boolean featuresEmpty = featuresLower.isEmpty();

        String minShade = (criteria.getMinShade() == null || criteria.getMinShade().isBlank())
                ? null
                : criteria.getMinShade();

        // מילת מפתח מהטקסט לחיפוש ב-remark (למשל "שבור")
        String remarkKeyword = extractRemarkKeyword(userPrompt);

        System.out.println("=== DEBUG CRITERIA ===");
        System.out.println("CITY       → " + cityParam);
        System.out.println("FEATURES   → " + featuresLower);
        System.out.println("MIN SHADE  → " + minShade);
        System.out.println("REMARK KW  → " + remarkKeyword);
        System.out.println("=========================");

        List<Parks> parks = parksRepository.searchAdvanced(
                cityParam,
                featuresLower,
                minShade,
                featuresEmpty,
                remarkKeyword
        );

        // דיבוג ל-ParkDesigns + Reports
        System.out.println("==== PARK DESIGN + REPORTS DEBUG ====");
        parks.subList(0, Math.min(3, parks.size()))
                .forEach(p -> {
                    System.out.println("פארק: " + p.getName());
                    if (p.getParkDesigns() != null) {
                        p.getParkDesigns().forEach(d ->
                                System.out.println("  FEATURE=" + (d.getFeature() != null ? d.getFeature().getName() : "null") +
                                        " RATING=" + d.getRatingsBreakdown() +
                                        " REMARK=" + d.getRemark()));
                    } else {
                        System.out.println("  אין parkDesigns");
                    }

                    if (p.getReports() != null && !p.getReports().isEmpty()) {
                        p.getReports().forEach(r ->
                                System.out.println("  REPORT SAT=" + r.getSatisfaction() +
                                        " TEXT=" + r.getFreeText()));
                    } else {
                        System.out.println("  אין reports");
                    }
                });
        System.out.println("============================");

        String response;
        if (parks.isEmpty()) {
            response = "מצטערת, לא נמצאו פארקים תואמים כרגע 😔\n" +
                    "אולי תנסי עיר אחרת או קצת פחות דרישות?";
        } else {
            response = buildAnswerWithGpt(userPrompt, parks, recentHistory);
        }

        saveToHistory(history, userPrompt, response);
        return response;
    }

    // ===== בדיקה אם הקריטריונים ריקים =====
    private boolean isEmptyCriteria(AdvancedSearchCriteria c) {
        if (c == null) return true;
        boolean noCity = (c.getCity() == null || c.getCity().isBlank());
        boolean noFeatures = (c.getFeatures() == null || c.getFeatures().isEmpty());
        boolean noShade = (c.getMinShade() == null || c.getMinShade().isBlank());
        return noCity && noFeatures && noShade;
    }

    // ===== מיזוג קריטריונים נוכחיים עם קודמים =====
    private AdvancedSearchCriteria mergeCriteria(AdvancedSearchCriteria current,
                                                 AdvancedSearchCriteria previous) {
        if (previous == null) {
            return current;
        }

        String city = (current.getCity() != null && !current.getCity().isBlank())
                ? current.getCity()
                : previous.getCity();

        List<String> features;
        if (current.getFeatures() == null || current.getFeatures().isEmpty()) {
            features = previous.getFeatures();
        } else if (previous.getFeatures() == null || previous.getFeatures().isEmpty()) {
            features = current.getFeatures();
        } else {
            // מאחדת פיצ'רים ישנים + חדשים בלי כפילויות
            Set<String> merged = new LinkedHashSet<>(previous.getFeatures());
            merged.addAll(current.getFeatures());
            features = new ArrayList<>(merged);
        }

        String minShade = (current.getMinShade() != null && !current.getMinShade().isBlank())
                ? current.getMinShade()
                : previous.getMinShade();

        return new AdvancedSearchCriteria(city, features, minShade);
    }

    // ===== חילוץ קריטריונים מ-GPT + חיזוק לפי מילים בטקסט =====
    private AdvancedSearchCriteria extractCriteria(String userPrompt, String recentHistory) {
        String fullPrompt = (recentHistory == null || recentHistory.isBlank())
                ? userPrompt
                : userPrompt + "\n\nהיסטוריה אחרונה של השיחה:\n" + recentHistory;

        AdvancedSearchCriteria baseCriteria = null;

        try {
            String content = chatClient.prompt()
                    .system(CRITERIA_SYSTEM)
                    .user(fullPrompt)
                    .call()
                    .content();

            System.out.println("GPT RAW CRITERIA RESPONSE: " + content);

            if (content != null) {
                String json = content.trim()
                        .replace("```json", "")
                        .replace("```", "")
                        .trim();

                if (json.startsWith("{") && json.endsWith("}")) {
                    baseCriteria = mapper.readValue(json, AdvancedSearchCriteria.class);
                }
            }
        } catch (Exception e) {
            System.err.println("שגיאה בחילוץ קריטריונים מ-GPT:");
            e.printStackTrace();
        }

        if (baseCriteria == null) {
            baseCriteria = new AdvancedSearchCriteria(null, null, null);
        }

        // חיזוק קריטריונים לפי מילים בטקסט
        return applyKeywordHeuristics(baseCriteria, userPrompt);
    }

    // ===== הוספת קריטריונים לפי מילים בטקסט =====
    private AdvancedSearchCriteria applyKeywordHeuristics(AdvancedSearchCriteria criteria,
                                                          String userPrompt) {
        if (userPrompt == null) {
            return criteria;
        }

        String text = userPrompt.toLowerCase();

        // נתחיל מרשימת הפיצ'רים הקיימת (אם יש)
        List<String> features = (criteria.getFeatures() != null)
                ? new ArrayList<>(criteria.getFeatures())
                : new ArrayList<>();

        // פונקציה קטנה להוספה בלי כפילויות
        java.util.function.Consumer<String> addFeature = f -> {
            if (!features.contains(f)) {
                features.add(f);
            }
        };

        // ספסלים
        if (text.contains("ספסל")) {
            addFeature.accept("ספסלים");
        }

        // צל
        if (text.contains("צל")) {
            addFeature.accept("צל");
        }

        // מתקנים
        if (text.contains("מתקן") || text.contains("מתקנים")) {
            addFeature.accept("מתקנים");
        }

        // שולחנות
        if (text.contains("שולחן") || text.contains("שולחנות")) {
            addFeature.accept("שולחנות");
        }

        // דשא
        if (text.contains("דשא")) {
            addFeature.accept("דשא");
        }

        // נגישות
        if (text.contains("נגיש")) {
            addFeature.accept("נגישות");
        }

        // צל מינימלי – אם GPT לא קבע כבר
        String minShade = criteria.getMinShade();
        if (minShade == null || minShade.isBlank()) {
            if (text.contains("הרבה צל") || text.contains("מלא צל") || text.contains("המון צל")) {
                minShade = "רב";
            } else if (text.contains("קצת צל") || text.contains("מעט צל")) {
                minShade = "מועט";
            } else if (text.contains("בינוני צל") || text.contains("גם צל וגם שמש")) {
                minShade = "בינוני";
            }
        }

        List<String> finalFeatures = features.isEmpty() ? null : features;

        return new AdvancedSearchCriteria(
                criteria.getCity(),
                finalFeatures,
                minShade
        );
    }

    // ===== מיצוי מילת מפתח לחיפוש בתוך remark (למשל "שבור") =====
    private String extractRemarkKeyword(String userPrompt) {
        if (userPrompt == null) return null;
        String text = userPrompt.toLowerCase();

        if (text.contains("שבורים") || text.contains("שבור") || text.contains("שבירה")) {
            return "שבור";
        }

        if (text.contains("מלוכלך") || text.contains("מלוכלכים")) {
            return "מלוכלך";
        }

        // אפשר להוסיף כאן עוד מילים בעתיד...

        return null;
    }

    // ===== בניית תשובה סופית עם GPT (כולל features + reports) =====
    private String buildAnswerWithGpt(String userPrompt,
                                      List<Parks> parks,
                                      String recentHistory) {

        StringBuilder sb = new StringBuilder();
        int max = Math.min(3, parks.size());

        for (Parks p : parks.subList(0, max)) {
            sb.append("• ").append(p.getName());

            if (p.getCity() != null && p.getCity().getName() != null) {
                sb.append(" – ").append(p.getCity().getName());
            }
            if (p.getAddress() != null && !p.getAddress().isBlank()) {
                sb.append(" (").append(p.getAddress()).append(")");
            }
            sb.append("\n");

            // חוות דעת לפי פיצ'רים (ParkDesign)
            if (p.getParkDesigns() != null && !p.getParkDesigns().isEmpty()) {
                sb.append("  חוות דעת לפי פיצ'רים:\n");
                p.getParkDesigns().stream()
                        .limit(3)
                        .forEach(d -> appendDesignLine(sb, d));
            }

            // דוחות כלליים (Reports)
            if (p.getReports() != null && !p.getReports().isEmpty()) {
                double avg = p.getReports().stream()
                        .mapToInt(Reports::getSatisfaction)
                        .average()
                        .orElse(0.0);

                int count = p.getReports().size();

                sb.append("  דירוג כללי מהמשתמשים: בערך ")
                        .append(String.format("%.1f", avg))
                        .append(" מתוך 5 (מבוסס על ")
                        .append(count)
                        .append(" דיווחים)\n");
            }

            sb.append("\n");
        }

        String message = """
                היסטוריה אחרונה של השיחה (אם יש):
                %s

                השאלה הנוכחית של המשתמש/ת:
                %s

                אלו הפארקים הכי מתאימים שמצאתי (כולל חוות דעת ודירוגים):
                %s

                עכשיו תכתבי תשובה סופית קצרה, חמה וטבעית בעברית (מקסימום 4 שורות).
                חובה:
                • בלי כוכביות, בלי שמות אנשים
                • תתחילי ב"היי!", "וואו!", "מצאתי לך!" או פתיחה חמודה אחרת
                • תמליצי רק על הפארקים מהרשימה למעלה
                • תתייחסי בטבעיות גם להתרשמות המשתמשים (remark) וגם לדירוגים (ratingsBreakdown והדירוג הכללי ב-reports)
                • תסיימי באימוג'י של עץ או לב
                """.formatted(
                (recentHistory == null || recentHistory.isBlank()) ? "זו ההודעה הראשונה" : recentHistory,
                userPrompt,
                sb
        );

        return chatClient.prompt()
                .system(ANSWER_SYSTEM)
                .user(message)
                .call()
                .content()
                .trim();
    }

    private void appendDesignLine(StringBuilder sb, ParkDesign d) {
        sb.append("  - ");

        if (d.getFeature() != null && d.getFeature().getName() != null) {
            sb.append(d.getFeature().getName()).append(": ");
        }

        if (d.getRemark() != null && !d.getRemark().isBlank()) {
            sb.append(d.getRemark());
        } else {
            sb.append("בלי הערה מיוחדת");
        }

        if (d.getRatingsBreakdown() != null) {
            sb.append(" (דירוג: ")
                    .append(d.getRatingsBreakdown().name())
                    .append(")");
        }

        sb.append("\n");
    }

    // ===== מיפוי עיר משם בעברית לשם ב-DB =====
    private String mapCityToDb(String city) {
        if (city == null || city.isBlank()) {
            return null;
        }

        String normalized = city.toLowerCase().replace(" ", "");

        if (normalized.contains("בניברק")) {
            return "bney brak";
        }

        if (normalized.contains("פתחתקווה") || normalized.contains("פתחתקוה") ||
                normalized.contains("petah tikva")) {
            return "petah tikva";
        }

        if (normalized.contains("ירושלים") || normalized.contains("jerusalem")) {
            return "jerusalem";
        }

        return city.toLowerCase();
    }

    private void saveToHistory(Deque<String> history, String userPrompt, String response) {
        history.addLast("משתמש/ת: " + userPrompt);
        history.addLast("עוזרת: " + response);
        while (history.size() > 20) {
            history.removeFirst();
        }
    }

    // ===== זיהוי משתמש (מחובר / אורח) =====
    private String getUserKey() {
        // אם יש משתמש מחובר – נשתמש במייל שלו
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()
                && !"anonymousUser".equals(auth.getName())) {
            return "user_" + auth.getName();
        }

        // אחרת – ננסה לקחת מזהות מה-Header Chat-Session
        ServletRequestAttributes attrs =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            String chatSession = attrs.getRequest().getHeader("Chat-Session");
            if (chatSession != null && !chatSession.isBlank()) {
                return "guest_" + chatSession.trim();
            }
        }

        // fallback – משתמש כללי אחד
        return "guest_default";
    }




    // --- חילוץ שמות פארקים מתוך הטקסט של המשתמש
    private List<String> extractPossibleParkNames(String text) {
        if (text == null || text.isBlank()) return List.of();

        // מילים שצריך להסיר
        List<String> stopWords = List.of(
                "מה", "מזג", "האוויר", "עכשיו", "יש", "אני", "רוצה",
                "לדעת", "איך", "איפה", "איזה", "על", "עם"
        );

        // ניקוי סימני פיסוק
        String cleaned = text.replaceAll("[^א-תa-zA-Z0-9 ]", " ").toLowerCase();

        // פיצול למילים
        List<String> words = Arrays.stream(cleaned.split("\\s+"))
                .filter(w -> !stopWords.contains(w))
                .filter(w -> w.length() >= 2)
                .toList();

        if (words.isEmpty()) return List.of();

        // שמות כל הפארקים מה-DB
        List<String> allParkNames = parksRepository.findAll().stream()
                .map(p -> p.getName().toLowerCase())
                .toList();

        List<String> matches = new ArrayList<>();

        // מנסה להרכיב צמדי מילים ומשפטים קצרים
        for (int i = 0; i < words.size(); i++) {
            for (int j = i + 1; j <= Math.min(i + 3, words.size()); j++) {
                String phrase = String.join(" ", words.subList(i, j)).trim();
                if (phrase.length() >= 2) {
                    for (String name : allParkNames) {
                        if (name.contains(phrase)) {
                            matches.add(name);
                        }
                    }
                }
            }
        }

        return matches.stream().distinct().toList();
    }

}

