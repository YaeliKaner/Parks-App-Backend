//////package com.example.parks.dto;
//////
//////public class ParkSearchCriteria {
//////
//////    // שם העיר, למשל "בני ברק"
//////    private String cityName;
//////
//////    // מילת מפתח למשל "צל", "נדנדה", "דשא"
//////    private String keyword;
//////
//////    public String getCityName() {
//////        return cityName;
//////    }
//////
//////    public void setCityName(String cityName) {
//////        this.cityName = cityName;
//////    }
//////
//////    public String getKeyword() {
//////        return keyword;
//////    }
//////
//////    public void setKeyword(String keyword) {
//////        this.keyword = keyword;
//////    }
//////}
////package com.example.parks.dto;
////
////public class ParkSearchCriteria {
////
////    private String cityName;   // שם עיר
////    private String keyword;    // צל / ספסלים / מתקנים...
////
////    public String getCityName() {
////        return cityName;
////    }
////
////    public void setCityName(String cityName) {
////        this.cityName = cityName;
////    }
////
////    public String getKeyword() {
////        return keyword;
////    }
////
////    public void setKeyword(String keyword) {
////        this.keyword = keyword;
////    }
////
////}
//package com.example.parks.dto;
//
//public class ParkSearchCriteria {
//
//    private String cityName;   // שם עיר
//    private String keyword;    // צל / ספסלים / מתקנים...
//
//    // --- בנאי ריק (חובה לג'קסון!) ---
//    public ParkSearchCriteria() {}
//
//    // --- בנאי עם פרמטרים (מאוד נוח לקוד שלך) ---
//    public ParkSearchCriteria(String cityName, String keyword) {
//        this.cityName = cityName;
//        this.keyword = keyword;
//    }
//
//    public String getCityName() {
//        return cityName;
//    }
//
//    public void setCityName(String cityName) {
//        this.cityName = cityName;
//    }
//
//    public String getKeyword() {
//        return keyword;
//    }
//
//    public void setKeyword(String keyword) {
//        this.keyword = keyword;
//    }
//}
//package com.example.parks.dto;
//
//public class ParkSearchCriteria {
//
//    private String cityName;   // שם עיר
//    private String keyword;    // צל / ספסלים / מתקנים...
//
//    // --- בנאי ריק (חובה לג'קסון / JSON!) ---
//    public ParkSearchCriteria() {}
//
//    // --- בנאי עם פרמטרים (נוח שימוש בקוד) ---
//    public ParkSearchCriteria(String cityName, String keyword) {
//        this.cityName = cityName;
//        this.keyword = keyword;
//    }
//
//    public String getCityName() {
//        return cityName;
//    }
//
//    public void setCityName(String cityName) {
//        this.cityName = cityName;
//    }
//
//    public String getKeyword() {
//        return keyword;
//    }
//
//    public void setKeyword(String keyword) {
//        this.keyword = keyword;
//    }
//}
package com.example.parks.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParkSearchCriteria {

    private String city;
    private List<String> features = new ArrayList<>();
    private String minShade;
    private String remark;
    private String keyword;

    // קריטי — המרה בטוחה ללא נפילות
    public static ParkSearchCriteria fromJsonSafe(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, ParkSearchCriteria.class);
        } catch (Exception e) {
            System.err.println("❌ JSON ERROR → fallback: " + e.getMessage());

            // fallback אמיתי — אבל בלי להרוס את features
            ParkSearchCriteria c = new ParkSearchCriteria();
            c.keyword = json; // שיהיה בסיסי
            return c;
        }
    }

    public boolean isSimpleTextSearch() {
        return keyword != null && !keyword.isBlank()
                && city == null
                && (features == null || features.isEmpty())
                && minShade == null
                && remark == null;
    }

    // GETTERS / SETTERS
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public List<String> getFeatures() { return features; }
    public void setFeatures(List<String> features) {
        this.features = (features == null ? new ArrayList<>() : features);
    }

    public String getMinShade() { return minShade; }
    public void setMinShade(String minShade) { this.minShade = minShade; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }
}
