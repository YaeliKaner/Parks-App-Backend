////////////////////////package com.example.parks.service;
////////////////////////
////////////////////////import com.example.parks.model.Parks;
////////////////////////import com.example.parks.RatingsBreakdown;
////////////////////////import org.springframework.data.jpa.repository.JpaRepository;
////////////////////////import org.springframework.data.jpa.repository.Query;
////////////////////////import org.springframework.data.repository.query.Param;
////////////////////////import org.springframework.stereotype.Repository;
////////////////////////
////////////////////////import java.util.List;
////////////////////////
////////////////////////@Repository
////////////////////////public interface ParksRepository extends JpaRepository<Parks, Long> {
////////////////////////
////////////////////////    // כל הפארקים לפי תאריך עדכון ואז ID
////////////////////////    List<Parks> findAllByOrderByUpdateDateDescIdDesc();
////////////////////////
////////////////////////    // לשימוש ב-getParksByCityId בקונטרולר
////////////////////////    List<Parks> findParksByCityId(Long id);
////////////////////////
////////////////////////    // חיפוש לפי שם עיר (ל-AI)
////////////////////////    List<Parks> findByCity_NameContainingIgnoreCase(String cityName);
////////////////////////
////////////////////////    // החיפוש החכם והמתקדם – עובד ב-100% עם ה-enum שלך!
////////////////////////    @Query("""
////////////////////////        SELECT DISTINCT p FROM Parks p
////////////////////////        LEFT JOIN p.parkDesigns pd
////////////////////////        LEFT JOIN pd.feature f
////////////////////////        WHERE (:city IS NULL OR LOWER(p.city.name) LIKE LOWER(CONCAT('%', :city, '%')))
////////////////////////          AND (COALESCE(:features) IS NULL OR LOWER(f.name) IN :features)
////////////////////////          AND (
////////////////////////                :minShade IS NULL
////////////////////////                OR (:minShade = 'רב'     AND pd.ratingsBreakdown = com.example.parks.RatingsBreakdown.aLot)
////////////////////////                OR (:minShade = 'בינוני' AND pd.ratingsBreakdown IN (com.example.parks.RatingsBreakdown.aLot, com.example.parks.RatingsBreakdown.enough))
////////////////////////                OR (:minShade = 'מועט'   AND pd.ratingsBreakdown IN (com.example.parks.RatingsBreakdown.aLot, com.example.parks.RatingsBreakdown.enough, com.example.parks.RatingsBreakdown.little))
////////////////////////              )
////////////////////////        ORDER BY p.updateDate DESC
////////////////////////        """)
////////////////////////    List<Parks> searchAdvanced(
////////////////////////            @Param("city") String city,
////////////////////////            @Param("features") List<String> features,
////////////////////////            @Param("minShade") String minShade
////////////////////////    );
////////////////////////}
//////////////////////package com.example.parks.service;
//////////////////////
//////////////////////import com.example.parks.model.Parks;
//////////////////////import com.example.parks.RatingsBreakdown;
//////////////////////import org.springframework.data.jpa.repository.JpaRepository;
//////////////////////import org.springframework.data.jpa.repository.Query;
//////////////////////import org.springframework.data.repository.query.Param;
//////////////////////import org.springframework.stereotype.Repository;
//////////////////////
//////////////////////import java.util.List;
//////////////////////
//////////////////////@Repository
//////////////////////public interface ParksRepository extends JpaRepository<Parks, Long> {
//////////////////////
//////////////////////    List<Parks> findAllByOrderByUpdateDateDescIdDesc();
//////////////////////    List<Parks> findParksByCityId(Long id);
//////////////////////    List<Parks> findByCity_NameContainingIgnoreCase(String cityName);
//////////////////////
//////////////////////    @Query("""
//////////////////////        SELECT DISTINCT p FROM Parks p
//////////////////////        LEFT JOIN p.parkDesigns pd
//////////////////////        LEFT JOIN pd.feature f
//////////////////////        WHERE (:city IS NULL OR LOWER(p.city.name) LIKE LOWER(CONCAT('%', :city, '%')))
//////////////////////          AND (COALESCE(:features) IS NULL OR SIZE(:features) = 0 OR LOWER(f.name) IN :features)
//////////////////////          AND (
//////////////////////                :minShade IS NULL
//////////////////////                OR (:minShade = 'רב'     AND pd.ratingsBreakdown = com.example.parks.RatingsBreakdown.aLot)
//////////////////////                OR (:minShade = 'בינוני' AND pd.ratingsBreakdown IN (com.example.parks.RatingsBreakdown.aLot, com.example.parks.RatingsBreakdown.enough))
//////////////////////                OR (:minShade = 'מועט'   AND pd.ratingsBreakdown IN (com.example.parks.RatingsBreakdown.aLot, com.example.parks.RatingsBreakdown.enough, com.example.parks.RatingsBreakdown.little))
//////////////////////              )
//////////////////////        ORDER BY p.updateDate DESC
//////////////////////        """)
//////////////////////    List<Parks> searchAdvanced(
//////////////////////            @Param("city") String city,
//////////////////////            @Param("features") List<String> features,   // תמיד List – גם אם ריק
//////////////////////            @Param("minShade") String minShade
//////////////////////    );
//////////////////////}
////////////////////package com.example.parks.service;
////////////////////
////////////////////import com.example.parks.model.Parks;
////////////////////import org.springframework.data.jpa.repository.JpaRepository;
////////////////////import org.springframework.data.jpa.repository.Query;
////////////////////import org.springframework.data.repository.query.Param;
////////////////////import org.springframework.stereotype.Repository;
////////////////////
////////////////////import java.util.List;
////////////////////
////////////////////@Repository
////////////////////public interface ParksRepository extends JpaRepository<Parks, Long> {
////////////////////
////////////////////    List<Parks> findAllByOrderByUpdateDateDescIdDesc();
////////////////////    List<Parks> findParksByCityId(Long cityId);
////////////////////    List<Parks> findByCity_NameContainingIgnoreCase(String cityName);
////////////////////
////////////////////    @Query("""
////////////////////        SELECT DISTINCT p FROM Parks p
////////////////////        LEFT JOIN p.parkDesigns pd
////////////////////        LEFT JOIN pd.feature f
////////////////////        WHERE (:city IS NULL OR LOWER(p.city.name) LIKE LOWER(CONCAT('%', :city, '%')))
////////////////////          AND (
////////////////////                :featuresEmpty = true
////////////////////                OR LOWER(f.name) IN :features
////////////////////              )
////////////////////          AND (
////////////////////                :minShade IS NULL
////////////////////                OR (:minShade = 'רב'     AND pd.ratingsBreakdown = 'aLot')
////////////////////                OR (:minShade = 'בינוני' AND pd.ratingsBreakdown IN ('aLot', 'enough'))
////////////////////                OR (:minShade = 'מועט'   AND pd.ratingsBreakdown IN ('aLot', 'enough', 'little'))
////////////////////              )
////////////////////        ORDER BY p.updateDate DESC
////////////////////        """)
////////////////////    List<Parks> searchAdvanced(
////////////////////            @Param("city") String city,
////////////////////            @Param("features") List<String> features,
////////////////////            @Param("minShade") String minShade,
////////////////////            @Param("featuresEmpty") boolean featuresEmpty
////////////////////    );
////////////////////}
//////////////////package com.example.parks.service;
//////////////////
//////////////////import com.example.parks.model.Parks;
//////////////////import org.springframework.data.jpa.repository.JpaRepository;
//////////////////import org.springframework.data.jpa.repository.Query;
//////////////////import org.springframework.data.repository.query.Param;
//////////////////import org.springframework.stereotype.Repository;
//////////////////
//////////////////import java.util.List;
//////////////////
//////////////////@Repository
//////////////////public interface ParksRepository extends JpaRepository<Parks, Long> {
//////////////////
//////////////////    List<Parks> findAllByOrderByUpdateDateDescIdDesc();
//////////////////    List<Parks> findParksByCityId(Long cityId);
//////////////////    List<Parks> findByCity_NameContainingIgnoreCase(String cityName);
//////////////////
//////////////////    @Query("""
//////////////////        SELECT DISTINCT p FROM Parks p
//////////////////        LEFT JOIN p.parkDesigns pd
//////////////////        LEFT JOIN pd.feature f
//////////////////        LEFT JOIN p.reports r  -- JOIN ל-Reports
//////////////////        LEFT JOIN p.favorites fav  -- JOIN ל-Favorites
//////////////////        WHERE (:city IS NULL OR LOWER(p.city.name) LIKE LOWER(CONCAT('%', :city, '%')))
//////////////////          AND (
//////////////////                :featuresEmpty = true
//////////////////                OR LOWER(f.name) IN :features
//////////////////              )
//////////////////          AND (
//////////////////                :minShade IS NULL
//////////////////                OR (:minShade = 'רב'     AND pd.ratingsBreakdown = 'aLot')
//////////////////                OR (:minShade = 'בינוני' AND pd.ratingsBreakdown IN ('aLot', 'enough'))
//////////////////                OR (:minShade = 'מועט'   AND pd.ratingsBreakdown IN ('aLot', 'enough', 'little'))
//////////////////              )
//////////////////          AND (r.satisfaction > 3 OR r IS NULL)  -- מסנן דירוג >3 (התאמי אם צריך)
//////////////////        GROUP BY p  -- לקבץ ולמיין
//////////////////        ORDER BY COUNT(fav) DESC, AVG(r.satisfaction) DESC, p.updateDate DESC
//////////////////        """)
//////////////////    List<Parks> searchAdvanced(
//////////////////            @Param("city") String city,
//////////////////            @Param("features") List<String> features,
//////////////////            @Param("minShade") String minShade,
//////////////////            @Param("featuresEmpty") boolean featuresEmpty
//////////////////    );
//////////////////}
//////////////// // ← שמתי לב שהקובץ שלך היה ב-service, צריך להיות ב-repository!
////////////////package com.example.parks.service;
////////////////import com.example.parks.model.Parks;
////////////////import org.springframework.data.jpa.repository.JpaRepository;
////////////////import org.springframework.data.jpa.repository.Query;
////////////////import org.springframework.data.repository.query.Param;
////////////////import org.springframework.stereotype.Repository;
////////////////
////////////////import java.util.List;
////////////////
////////////////@Repository
////////////////public interface ParksRepository extends JpaRepository<Parks, Long> {
////////////////
////////////////    List<Parks> findAllByOrderByUpdateDateDescIdDesc();
////////////////    List<Parks> findParksByCityId(Long cityId);
////////////////    List<Parks> findByCity_NameContainingIgnoreCase(String cityName);
////////////////
////////////////    @Query("""
////////////////    SELECT p
////////////////    FROM Parks p
////////////////    LEFT JOIN p.parkDesigns pd
////////////////    LEFT JOIN pd.feature f
////////////////    LEFT JOIN p.reports r
////////////////    LEFT JOIN p.favorites fav
////////////////    WHERE (:city IS NULL OR LOWER(p.city.name) LIKE LOWER(CONCAT('%', :city, '%')))
////////////////      AND (:featuresEmpty = true OR LOWER(f.name) IN :features)
////////////////      AND (:minShade IS NULL
////////////////           OR (:minShade = 'רב'     AND pd.ratingsBreakdown = 'aLot')
////////////////           OR (:minShade = 'בינוני' AND pd.ratingsBreakdown IN ('aLot', 'enough'))
////////////////           OR (:minShade = 'מועט'   AND pd.ratingsBreakdown IN ('aLot', 'enough', 'little'))
////////////////          )
////////////////    GROUP BY p
////////////////    ORDER BY
////////////////        COUNT(fav) DESC,
////////////////        COALESCE(AVG(r.satisfaction), 0) DESC,
////////////////        p.updateDate DESC
////////////////    """)
////////////////    List<Parks> searchAdvanced(
////////////////            @Param("city") String city,
////////////////            @Param("features") List<String> features,
////////////////            @Param("minShade") String minShade,
////////////////            @Param("featuresEmpty") boolean featuresEmpty
////////////////    );
////////////////}
//////////////package com.example.parks.service;
//////////////
//////////////import com.example.parks.model.Parks;
//////////////import org.springframework.data.jpa.repository.JpaRepository;
//////////////import org.springframework.data.jpa.repository.Query;
//////////////import org.springframework.data.repository.query.Param;
//////////////import org.springframework.stereotype.Repository;
//////////////
//////////////import java.util.List;
//////////////
//////////////@Repository
//////////////public interface ParksRepository extends JpaRepository<Parks, Long> {
//////////////
//////////////    // כל הפארקים לפי תאריך עדכון ואז ID
//////////////    List<Parks> findAllByOrderByUpdateDateDescIdDesc();
//////////////
//////////////    // לפי עיר ID
//////////////    List<Parks> findParksByCityId(Long cityId);
//////////////
//////////////    // לפי שם עיר (לשימוש אם צריך)
//////////////    List<Parks> findByCity_NameContainingIgnoreCase(String cityName);
//////////////
//////////////    // 🔎 חיפוש טקסט חופשי – שם פארק / כתובת / עיר
//////////////    @Query("""
//////////////           SELECT p
//////////////           FROM Parks p
//////////////           WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :text, '%'))
//////////////              OR LOWER(p.address) LIKE LOWER(CONCAT('%', :text, '%'))
//////////////              OR LOWER(p.city.name) LIKE LOWER(CONCAT('%', :text, '%'))
//////////////           ORDER BY p.updateDate DESC
//////////////           """)
//////////////    List<Parks> searchByText(@Param("text") String text);
//////////////
//////////////    // 🔎 חיפוש מתקדם (AI / דירוגים / פיצ׳רים וכו׳)
//////////////    @Query("""
//////////////    SELECT p
//////////////    FROM Parks p
//////////////    LEFT JOIN p.parkDesigns pd
//////////////    LEFT JOIN pd.feature f
//////////////    LEFT JOIN p.reports r
//////////////    LEFT JOIN p.favorites fav
//////////////    WHERE (:city IS NULL OR LOWER(p.city.name) LIKE LOWER(CONCAT('%', :city, '%')))
//////////////      AND (:featuresEmpty = true OR LOWER(f.name) IN :features)
//////////////      AND (:minShade IS NULL
//////////////           OR (:minShade = 'רב'     AND pd.ratingsBreakdown = 'aLot')
//////////////           OR (:minShade = 'בינוני' AND pd.ratingsBreakdown IN ('aLot', 'enough'))
//////////////           OR (:minShade = 'מועט'   AND pd.ratingsBreakdown IN ('aLot', 'enough', 'little'))
//////////////          )
//////////////    GROUP BY p
//////////////    ORDER BY
//////////////        COUNT(fav) DESC,
//////////////        COALESCE(AVG(r.satisfaction), 0) DESC,
//////////////        p.updateDate DESC
//////////////    """)
//////////////    List<Parks> searchAdvanced(
//////////////            @Param("city") String city,
//////////////            @Param("features") List<String> features,
//////////////            @Param("minShade") String minShade,
//////////////            @Param("featuresEmpty") boolean featuresEmpty
//////////////    );
//////////////}
////////////package com.example.parks.service;
////////////
////////////import com.example.parks.model.Parks;
////////////import org.springframework.data.jpa.repository.JpaRepository;
////////////import org.springframework.data.jpa.repository.Query;
////////////import org.springframework.data.repository.query.Param;
////////////import org.springframework.stereotype.Repository;
////////////
////////////import java.util.List;
////////////
////////////@Repository
////////////public interface ParksRepository extends JpaRepository<Parks, Long> {
////////////
////////////    // כל הפארקים לפי תאריך עדכון ואז ID
////////////    List<Parks> findAllByOrderByUpdateDateDescIdDesc();
////////////
////////////    // לפי עיר ID
////////////    List<Parks> findParksByCityId(Long cityId);
////////////
////////////    // לפי שם עיר (לשימוש אם צריך)
////////////    List<Parks> findByCity_NameContainingIgnoreCase(String cityName);
////////////
////////////    // 🔎 חיפוש טקסט חופשי – שם פארק / כתובת / עיר
////////////    @Query("""
////////////           SELECT p
////////////           FROM Parks p
////////////           WHERE LOWER(p.name)      LIKE LOWER(CONCAT('%', :text, '%'))
////////////              OR LOWER(p.address)   LIKE LOWER(CONCAT('%', :text, '%'))
////////////              OR LOWER(p.city.name) LIKE LOWER(CONCAT('%', :text, '%'))
////////////           ORDER BY p.updateDate DESC
////////////           """)
////////////    List<Parks> searchByText(@Param("text") String text);
////////////
////////////    // 🔎 חיפוש מתקדם (AI / דירוגים / פיצ׳רים וכו׳)
////////////    @Query("""
////////////           SELECT p
////////////           FROM Parks p
////////////           LEFT JOIN p.parkDesigns pd
////////////           LEFT JOIN pd.feature f
////////////           LEFT JOIN p.reports r
////////////           LEFT JOIN p.favorites fav
////////////           WHERE (:city IS NULL OR LOWER(p.city.name) LIKE LOWER(CONCAT('%', :city, '%')))
////////////             AND (:featuresEmpty = true OR LOWER(f.name) IN :features)
////////////             AND (
////////////                    :minShade IS NULL
////////////                 OR (:minShade = 'רב'     AND pd.ratingsBreakdown = 'aLot')
////////////                 OR (:minShade = 'בינוני' AND pd.ratingsBreakdown IN ('aLot', 'enough'))
////////////                 OR (:minShade = 'מועט'   AND pd.ratingsBreakdown IN ('aLot', 'enough', 'little'))
////////////                 )
////////////           GROUP BY p
////////////           ORDER BY
////////////               COUNT(fav) DESC,
////////////               COALESCE(AVG(r.satisfaction), 0) DESC,
////////////               p.updateDate DESC
////////////           """)
////////////    List<Parks> searchAdvanced(
////////////            @Param("city") String city,
////////////            @Param("features") List<String> features,
////////////            @Param("minShade") String minShade,
////////////            @Param("featuresEmpty") boolean featuresEmpty
////////////    );
////////////}
//////////package com.example.parks.service;
//////////
//////////import com.example.parks.model.Parks;
//////////import org.springframework.data.jpa.repository.JpaRepository;
//////////import org.springframework.data.jpa.repository.Query;
//////////import org.springframework.data.repository.query.Param;
//////////import org.springframework.stereotype.Repository;
//////////
//////////import java.util.List;
//////////
//////////@Repository
//////////public interface ParksRepository extends JpaRepository<Parks, Long> {
//////////
//////////    // כל הפארקים לפי תאריך עדכון ואז ID
//////////    List<Parks> findAllByOrderByUpdateDateDescIdDesc();
//////////
//////////    // לפי עיר ID
//////////    List<Parks> findParksByCityId(Long cityId);
//////////
//////////    // לפי שם עיר (לשימוש אם צריך)
//////////    List<Parks> findByCity_NameContainingIgnoreCase(String cityName);
//////////
//////////    // 🔎 חיפוש טקסט חופשי – שם פארק / כתובת / עיר
//////////    @Query("""
//////////           SELECT p
//////////           FROM Parks p
//////////           WHERE LOWER(p.name)      LIKE LOWER(CONCAT('%', :text, '%'))
//////////              OR LOWER(p.address)   LIKE LOWER(CONCAT('%', :text, '%'))
//////////              OR LOWER(p.city.name) LIKE LOWER(CONCAT('%', :text, '%'))
//////////           ORDER BY p.updateDate DESC
//////////           """)
//////////    List<Parks> searchByText(@Param("text") String text);
//////////
//////////    // 🔎 חיפוש מתקדם (AI / דירוגים / פיצ׳רים / remark)
//////////    @Query("""
//////////           SELECT p
//////////           FROM Parks p
//////////           LEFT JOIN p.parkDesigns pd
//////////           LEFT JOIN pd.feature f
//////////           LEFT JOIN p.reports r
//////////           LEFT JOIN p.favorites fav
//////////           WHERE (:city IS NULL OR LOWER(p.city.name) LIKE LOWER(CONCAT('%', :city, '%')))
//////////             AND (
//////////                    :featuresEmpty = true
//////////                    OR LOWER(f.name) IN :features
//////////                    OR (:remarkText IS NOT NULL AND LOWER(pd.remark) LIKE LOWER(CONCAT('%', :remarkText, '%')))
//////////                 )
//////////             AND (
//////////                    :minShade IS NULL
//////////                 OR (:minShade = 'רב'     AND pd.ratingsBreakdown = 'aLot')
//////////                 OR (:minShade = 'בינוני' AND pd.ratingsBreakdown IN ('aLot', 'enough'))
//////////                 OR (:minShade = 'מועט'   AND pd.ratingsBreakdown IN ('aLot', 'enough', 'little'))
//////////                 )
//////////           GROUP BY p
//////////           ORDER BY
//////////               COUNT(fav) DESC,
//////////               COALESCE(AVG(r.satisfaction), 0) DESC,
//////////               p.updateDate DESC
//////////           """)
//////////    List<Parks> searchAdvanced(
//////////            @Param("city") String city,
//////////            @Param("features") List<String> features,
//////////            @Param("minShade") String minShade,
//////////            @Param("featuresEmpty") boolean featuresEmpty,
//////////            @Param("remarkText") String remarkText
//////////    );
//////////}
////////package com.example.parks.service;
////////
////////import com.example.parks.model.Parks;
////////import org.springframework.data.jpa.repository.JpaRepository;
////////import org.springframework.data.jpa.repository.Query;
////////import org.springframework.data.repository.query.Param;
////////import org.springframework.stereotype.Repository;
////////
////////import java.util.List;
////////
////////@Repository
////////public interface ParksRepository extends JpaRepository<Parks, Long> {
////////
////////    // כל הפארקים לפי תאריך עדכון ואז ID
////////    List<Parks> findAllByOrderByUpdateDateDescIdDesc();
////////
////////    // לפי עיר ID
////////    List<Parks> findParksByCityId(Long cityId);
////////
////////    // לפי שם עיר (אם צריך פעם)
////////    List<Parks> findByCity_NameContainingIgnoreCase(String cityName);
////////
////////    // 🔎 חיפוש טקסט חופשי – שם פארק / כתובת / עיר
////////    @Query("""
////////           SELECT p
////////           FROM Parks p
////////           JOIN Cities c ON p.city.id = c.id
////////           WHERE LOWER(p.name)      LIKE LOWER(CONCAT('%', :text, '%'))
////////              OR LOWER(p.address)   LIKE LOWER(CONCAT('%', :text, '%'))
////////              OR LOWER(c.name)      LIKE LOWER(CONCAT('%', :text, '%'))
////////           ORDER BY p.updateDate DESC
////////           """)
////////    List<Parks> searchByText(@Param("text") String text);
////////
////////
////////
////////    // 🔍 חיפוש מתקדם – התחשבות ב־features + remark + report (דירוג כללי)
////////    @Query("""
////////           SELECT p
////////           FROM Parks p
////////           LEFT JOIN p.parkDesigns pd
////////           LEFT JOIN pd.feature f
////////           LEFT JOIN p.reports r
////////           LEFT JOIN p.favorites fav
////////           WHERE (:city IS NULL OR LOWER(p.city.name) LIKE LOWER(CONCAT('%', :city, '%')))
////////             AND (
////////                    :featuresEmpty = true
////////                    OR LOWER(f.name) IN :features
////////                    OR (:remarkKeyword IS NOT NULL AND LOWER(pd.remark) LIKE LOWER(CONCAT('%', :remarkKeyword, '%')))
////////                 )
////////             AND (
////////                    :minShade IS NULL
////////                    OR (:minShade = 'רב' AND pd.ratingsBreakdown = 'aLot')
////////                    OR (:minShade = 'בינוני' AND pd.ratingsBreakdown IN ('aLot', 'enough'))
////////                    OR (:minShade = 'מועט' AND pd.ratingsBreakdown IN ('aLot', 'enough', 'little'))
////////                 )
////////             -- ⭐ התחשבות ב-reports (נכניס רק פארקים שקיבלו לפחות 3 כוכבים בממוצע)
////////             AND (
////////                    r IS NULL
////////                    OR r.satisfaction >= 3
////////                 )
////////           GROUP BY p
////////           ORDER BY
////////               COUNT(fav) DESC,                         -- פופולריות (מועדפים)
////////               COALESCE(AVG(CAST(r.satisfaction AS float)), 0) DESC, -- דירוג כללי מה-reports
////////               p.updateDate DESC                       -- הכי חדש ראשון
////////           """)
////////    List<Parks> searchAdvanced(
////////            @Param("city") String city,
////////            @Param("features") List<String> features,
////////            @Param("minShade") String minShade,
////////            @Param("featuresEmpty") boolean featuresEmpty,
////////            @Param("remarkKeyword") String remarkKeyword
////////    );
////////}
//////package com.example.parks.service;
//////
//////import com.example.parks.model.Parks;
//////import org.springframework.data.jpa.repository.JpaRepository;
//////import org.springframework.data.jpa.repository.Query;
//////import org.springframework.data.repository.query.Param;
//////import org.springframework.stereotype.Repository;
//////
//////import java.util.List;
//////
//////@Repository
//////public interface ParksRepository extends JpaRepository<Parks, Long> {
//////
//////    // כל הפארקים לפי תאריך עדכון ואז ID
//////    List<Parks> findAllByOrderByUpdateDateDescIdDesc();
//////
//////    // לפי עיר ID
//////    List<Parks> findParksByCityId(Long cityId);
//////
//////    // לפי שם עיר (אם צריך פעם)
//////    List<Parks> findByCity_NameContainingIgnoreCase(String cityName);
//////
//////    // 🔎 חיפוש טקסט חופשי – שם פארק / כתובת / עיר
//////    @Query("""
//////           SELECT p
//////           FROM Parks p
//////           WHERE LOWER(p.name)      LIKE LOWER(CONCAT('%', :text, '%'))
//////              OR LOWER(p.address)   LIKE LOWER(CONCAT('%', :text, '%'))
//////              OR LOWER(p.city.name) LIKE LOWER(CONCAT('%', :text, '%'))
//////           ORDER BY p.updateDate DESC
//////           """)
//////    List<Parks> searchByText(@Param("text") String text);
//////
//////    // 🔍 חיפוש מתקדם – features + remark + reports + favorites
//////    @Query("""
//////           SELECT p
//////           FROM Parks p
//////           LEFT JOIN p.parkDesigns pd
//////           LEFT JOIN pd.feature f
//////           LEFT JOIN p.reports r
//////           LEFT JOIN p.favorites fav
//////           WHERE (:city IS NULL OR LOWER(p.city.name) LIKE LOWER(CONCAT('%', :city, '%')))
//////             AND (
//////                    :featuresEmpty = true
//////                    OR LOWER(f.name) IN :features
//////                    OR (:remarkKeyword IS NOT NULL AND LOWER(pd.remark) LIKE LOWER(CONCAT('%', :remarkKeyword, '%')))
//////                 )
//////             AND (
//////                    :minShade IS NULL
//////                    OR (:minShade = 'רב'     AND pd.ratingsBreakdown = 'aLot')
//////                    OR (:minShade = 'בינוני' AND pd.ratingsBreakdown IN ('aLot', 'enough'))
//////                    OR (:minShade = 'מועט'   AND pd.ratingsBreakdown IN ('aLot', 'enough', 'little'))
//////                 )
//////           GROUP BY p
//////           ORDER BY
//////               COUNT(fav) DESC,
//////               COALESCE(AVG(r.satisfaction), 0) DESC,
//////               p.updateDate DESC
//////           """)
//////    List<Parks> searchAdvanced(
//////            @Param("city") String city,
//////            @Param("features") List<String> features,
//////            @Param("minShade") String minShade,
//////            @Param("featuresEmpty") boolean featuresEmpty,
//////            @Param("remarkKeyword") String remarkKeyword
//////    );
//////}
////package com.example.parks.service;
////
////import com.example.parks.model.Parks;
////import org.springframework.data.jpa.repository.JpaRepository;
////import org.springframework.data.jpa.repository.Query;
////import org.springframework.data.repository.query.Param;
////import org.springframework.stereotype.Repository;
////
////import java.util.List;
////
////@Repository
////public interface ParksRepository extends JpaRepository<Parks, Long> {
////
////    // כל הפארקים לפי תאריך עדכון ואז ID
////    List<Parks> findAllByOrderByUpdateDateDescIdDesc();
////
////    // לפי עיר ID
////    List<Parks> findParksByCityId(Long cityId);
////
////    // לפי שם עיר (אם צריך פעם)
////    List<Parks> findByCity_NameContainingIgnoreCase(String cityName);
////
////    // 🆕 חיפוש לפי שם פארק (מאוד שימושי ל-AI!)
////    List<Parks> findByNameContainingIgnoreCase(String name);
////
////    // 🔎 חיפוש טקסט חופשי – שם פארק / כתובת / עיר
////    @Query("""
////           SELECT p
////           FROM Parks p
////           WHERE LOWER(p.name)      LIKE LOWER(CONCAT('%', :text, '%'))
////              OR LOWER(p.address)   LIKE LOWER(CONCAT('%', :text, '%'))
////              OR LOWER(p.city.name) LIKE LOWER(CONCAT('%', :text, '%'))
////           ORDER BY p.updateDate DESC
////           """)
////    List<Parks> searchByText(@Param("text") String text);
////
////    // 🔍 חיפוש מתקדם – features + remark + reports + favorites
////    @Query("""
////           SELECT p
////           FROM Parks p
////           LEFT JOIN p.parkDesigns pd
////           LEFT JOIN pd.feature f
////           LEFT JOIN p.reports r
////           LEFT JOIN p.favorites fav
////           WHERE (:city IS NULL OR LOWER(p.city.name) LIKE LOWER(CONCAT('%', :city, '%')))
////             AND (
////                    :featuresEmpty = true
////                    OR LOWER(f.name) IN :features
////                    OR (:remarkKeyword IS NOT NULL AND LOWER(pd.remark) LIKE LOWER(CONCAT('%', :remarkKeyword, '%')))
////                 )
////             AND (
////                    :minShade IS NULL
////                    OR (:minShade = 'רב'     AND pd.ratingsBreakdown = 'aLot')
////                    OR (:minShade = 'בינוני' AND pd.ratingsBreakdown IN ('aLot', 'enough'))
////                    OR (:minShade = 'מועט'   AND pd.ratingsBreakdown IN ('aLot', 'enough', 'little'))
////                 )
////           GROUP BY p
////           ORDER BY
////               COUNT(fav) DESC,
////               COALESCE(AVG(r.satisfaction), 0) DESC,
////               p.updateDate DESC
////           """)
////    List<Parks> searchAdvanced(
////            @Param("city") String city,
////            @Param("features") List<String> features,
////            @Param("minShade") String minShade,
////            @Param("featuresEmpty") boolean featuresEmpty,
////            @Param("remarkKeyword") String remarkKeyword
////    );
////}
//package com.example.parks.service;
//
//import com.example.parks.model.Parks;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface ParksRepository extends JpaRepository<Parks, Long> {
//
//    // כל הפארקים לפי תאריך עדכון ואז ID
//    List<Parks> findAllByOrderByUpdateDateDescIdDesc();
//
//    // לפי עיר ID
//    List<Parks> findParksByCityId(Long cityId);
//
//    // לפי שם עיר (אם צריך פעם)
//    List<Parks> findByCity_NameContainingIgnoreCase(String cityName);
//
//    // 🆕 חיפוש לפי שם פארק (מאוד שימושי ל-AI!)
//    List<Parks> findByNameContainingIgnoreCase(String name);
//
//    // 🔎 חיפוש טקסט חופשי – שם פארק / כתובת / עיר
//    @Query("""
//           SELECT p
//           FROM Parks p
//           WHERE LOWER(p.name)      LIKE LOWER(CONCAT('%', :text, '%'))
//              OR LOWER(p.address)   LIKE LOWER(CONCAT('%', :text, '%'))
//              OR LOWER(p.city.name) LIKE LOWER(CONCAT('%', :text, '%'))
//           ORDER BY p.updateDate DESC
//           """)
//    List<Parks> searchByText(@Param("text") String text);
//
//    // 🔍 חיפוש מתקדם – features + remark + reports + favorites
//    @Query("""
//           SELECT p
//           FROM Parks p
//           LEFT JOIN p.parkDesigns pd
//           LEFT JOIN pd.feature f
//           LEFT JOIN p.reports r
//           LEFT JOIN p.favorites fav
//           WHERE (:city IS NULL OR LOWER(p.city.name) LIKE LOWER(CONCAT('%', :city, '%')))
//             AND (
//                    :featuresEmpty = true
//                    OR LOWER(f.name) IN :features
//                    OR (:remarkKeyword IS NOT NULL AND LOWER(pd.remark) LIKE LOWER(CONCAT('%', :remarkKeyword, '%')))
//                 )
//             AND (
//                    :minShade IS NULL
//                    OR (:minShade = 'רב'     AND pd.ratingsBreakdown = 'aLot')
//                    OR (:minShade = 'בינוני' AND pd.ratingsBreakdown IN ('aLot', 'enough'))
//                    OR (:minShade = 'מועט'   AND pd.ratingsBreakdown IN ('aLot', 'enough', 'little'))
//                 )
//           GROUP BY p
//           ORDER BY
//               COUNT(fav) DESC,
//               COALESCE(AVG(r.satisfaction), 0) DESC,
//               p.updateDate DESC
//           """)
//    List<Parks> searchAdvanced(
//            @Param("city") String city,
//            @Param("features") List<String> features,
//            @Param("minShade") String minShade,
//            @Param("featuresEmpty") boolean featuresEmpty,
//            @Param("remarkKeyword") String remarkKeyword
//    );
//
//    // 🌟 פארקים מומלצים – לפי ממוצע satisfaction מ-Reports
//    @Query("""
//           SELECT p
//           FROM Parks p
//           JOIN p.reports r
//           GROUP BY p
//           HAVING AVG(r.satisfaction) >= :minRating
//           ORDER BY AVG(r.satisfaction) DESC, COUNT(r) DESC
//           """)
//    List<Parks> findRecommendedParks(@Param("minRating") double minRating);
//}
package com.example.parks.service;

import com.example.parks.dto.ParksDTO;
import com.example.parks.model.Cities;
import com.example.parks.model.Parks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParksRepository extends JpaRepository<Parks, Long> {

    List<Parks> findAllByOrderByUpdateDateDescIdDesc();
    List<Parks> findParksByCityId(Long cityId);
    Parks findByNameAndCity(String name, Cities city);
    List<Parks> findByCity_NameContainingIgnoreCase(String cityName);
    List<Parks> findByNameContainingIgnoreCase(String name);

    // חיפוש טקסט חופשי – שם פארק / כתובת / עיר
    @Query("""
           SELECT p FROM Parks p
           WHERE LOWER(p.name)      LIKE LOWER(CONCAT('%', :text, '%'))
              OR LOWER(p.address)   LIKE LOWER(CONCAT('%', :text, '%'))
              OR LOWER(p.city.name) LIKE LOWER(CONCAT('%', :text, '%'))
           ORDER BY p.updateDate DESC
           """)
    List<Parks> searchByText(@Param("text") String text);

    // חיפוש מתקדם – הגרסה הסופית שעובדת תמיד (גם עם רשימת פיצ'רים ריקה!)
    @Query("""
           SELECT p
           FROM Parks p
           LEFT JOIN p.parkDesigns pd
           LEFT JOIN pd.feature f
           LEFT JOIN p.reports r
           LEFT JOIN p.favorites fav
           WHERE (:city IS NULL OR LOWER(p.city.name) LIKE LOWER(CONCAT('%', :city, '%')))
             AND (:featuresEmpty = true OR f.name IN :features)
             AND (:remarkKeyword IS NULL OR LOWER(pd.remark) LIKE LOWER(CONCAT('%', :remarkKeyword, '%')))
             AND (:minShade IS NULL
                  OR (:minShade = 'רב'     AND pd.ratingsBreakdown = 'aLot')
                  OR (:minShade = 'בינוני' AND pd.ratingsBreakdown IN ('aLot', 'enough'))
                  OR (:minShade = 'מועט'   AND pd.ratingsBreakdown IN ('aLot', 'enough', 'little'))
                 )
           GROUP BY p.id, p
           ORDER BY 
               COUNT(fav) DESC,
               COALESCE(AVG(r.satisfaction), 0) DESC,
               p.updateDate DESC
           """)
    List<Parks> searchAdvanced(
            @Param("city") String city,
            @Param("features") List<String> features,
            @Param("minShade") String minShade,
            @Param("featuresEmpty") boolean featuresEmpty,
            @Param("remarkKeyword") String remarkKeyword
    );

    // פארקים מומלצים לפי דירוג
    @Query("""
           SELECT p FROM Parks p
           JOIN p.reports r
           GROUP BY p
           HAVING AVG(r.satisfaction) >= :minRating
           ORDER BY AVG(r.satisfaction) DESC, COUNT(r) DESC
           """)
    List<Parks> findRecommendedParks(@Param("minRating") double minRating);


    @Query("""
           SELECT p FROM Parks p
           LEFT JOIN Reports r ON r.park.id = p.id
           GROUP BY p.id
           ORDER BY
                   CASE WHEN COUNT(r.id) = 0 THEN 1 ELSE 0 END ASC,
                   AVG(r.satisfaction) DESC
           """)
    List<Parks> findAllOrderBySatisfaction();
}

