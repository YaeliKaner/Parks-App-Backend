//package com.example.parks.service;
//
//import com.example.parks.model.Favorites;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface FavoritesRepository extends JpaRepository<Favorites, Long> {
//    List<Favorites> findFavoritesByUserId(Long id);
//}
//
//package com.example.parks.service;
//
//import com.example.parks.model.Favorites;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface FavoritesRepository extends JpaRepository<Favorites, Long> {
//
//    // כל המועדפים של משתמש מסוים
//    List<Favorites> findFavoritesByUserId(Long id);
//
//    // בדיקה אם כבר קיים מועדף למשתמש+פארק
//    boolean existsByUserIdAndParkId(Long userId, Long parkId);
//
//    // מחיקה של מועדף לפי משתמש+פארק
//    void deleteByUserIdAndParkId(Long userId, Long parkId);
//}
//package com.example.parks.service;
//
//import com.example.parks.model.Favorites;
//import com.example.parks.model.Parks;
//import com.example.parks.model.Users;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface FavoritesRepository extends JpaRepository<Favorites, Long> {
//
//    List<Favorites> findByUser(Users user);
//
//    boolean existsByUserAndPark(Users user, Parks park);
//
//    void deleteByUserAndPark(Users user, Parks park);
//}
package com.example.parks.service;

import com.example.parks.model.Favorites;
import com.example.parks.model.Parks;
import com.example.parks.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// ⬇️ שני אימפורטים חדשים
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, Long> {

    List<Favorites> findByUser(Users user);

    boolean existsByUserAndPark(Users user, Parks park);

    // ⬇️ כאן להוסיף את שתי האנוטציות
    @Transactional
    @Modifying
    void deleteByUserAndPark(Users user, Parks park);
}
