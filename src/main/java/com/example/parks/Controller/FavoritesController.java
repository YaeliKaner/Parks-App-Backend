//////package com.example.parks.Controller;
//////
//////import com.example.parks.dto.ParksDTO;
//////import com.example.parks.model.Favorites;
//////import com.example.parks.model.Parks;
//////import com.example.parks.service.FavoritesRepository;
//////import com.example.parks.service.ParkDesignRepository;
//////import com.example.parks.service.ParksRepository;
//////import com.example.parks.service.mappers.ParksMapper;
//////import org.springframework.beans.factory.annotation.Autowired;
//////import org.springframework.http.HttpStatus;
//////import org.springframework.http.MediaType;
//////import org.springframework.http.ResponseEntity;
//////import org.springframework.web.bind.annotation.*;
//////import org.springframework.web.multipart.MultipartFile;
//////
//////import java.io.IOException;
//////import java.util.List;
//////
//////@RestController
//////@RequestMapping("/api/favorites")
////////@CrossOrigin
//////public class FavoritesController {
//////
//////    FavoritesRepository favoritesRepository;
//////
//////    @Autowired
//////    public FavoritesController(FavoritesRepository favoritesRepository) {
//////        this.favoritesRepository = favoritesRepository;
//////    }
//////
//////
//////    @GetMapping("/getFavoritesByUserId/{id}")
//////    public ResponseEntity<List<Favorites>> getFavoritesByUserId(@PathVariable Long id){
//////        try{
//////            return new ResponseEntity<> (favoritesRepository.findFavoritesByUserId(id), HttpStatus.OK);
//////        }
//////        catch(Exception e){
//////            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//////        }
//////    }
//////
//////}
////package com.example.parks.Controller;
////
////import com.example.parks.dto.ParksDTO;
////import com.example.parks.model.Favorites;
////import com.example.parks.model.Parks;
////import com.example.parks.model.Users;
////import com.example.parks.service.FavoritesRepository;
////import com.example.parks.service.ParksRepository;
////import com.example.parks.service.UsersRepository;
////import com.example.parks.service.mappers.ParksMapper;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.http.HttpStatus;
////import org.springframework.http.ResponseEntity;
////import org.springframework.security.core.Authentication;
////import org.springframework.security.core.context.SecurityContextHolder;
////import org.springframework.web.bind.annotation.*;
////
////import java.time.LocalDate;
////import java.util.List;
////
////@RestController
////@RequestMapping("/api/favorites")
//////@CrossOrigin
////public class FavoritesController {
////
////    private final FavoritesRepository favoritesRepository;
////    private final UsersRepository usersRepository;
////    private final ParksRepository parksRepository;
////    private final ParksMapper parksMapper;
////
////    @Autowired
////    public FavoritesController(FavoritesRepository favoritesRepository,
////                               UsersRepository usersRepository,
////                               ParksRepository parksRepository,
////                               ParksMapper parksMapper) {
////        this.favoritesRepository = favoritesRepository;
////        this.usersRepository = usersRepository;
////        this.parksRepository = parksRepository;
////        this.parksMapper = parksMapper;
////    }
////
////    // ***********************
////    // 1. הוספת פארק למועדפים
////    // ***********************
////    @PostMapping("/addFavorite/{parkId}")
////    public ResponseEntity<?> addFavorite(@PathVariable Long parkId) {
////        try {
////            // מי המשתמש המחובר (לפי ה־JWT / קובץ קוקי)
////            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////            String userEmail = authentication.getName();
////
////            Users authenticatedUser = usersRepository.findByEmail(userEmail);
////            if (authenticatedUser == null) {
////                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
////            }
////
////            // אם אין כזה פארק – נחזיר 404
////            Parks park = parksRepository.findById(parkId).orElse(null);
////            if (park == null) {
////                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
////            }
////
////            // אם כבר קיים מועדף כזה – לא צריך להוסיף שוב
////            if (favoritesRepository.existsByUserIdAndParkId(authenticatedUser.getId(), parkId)) {
////                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
////            }
////
////            Favorites fav = new Favorites();
////            fav.setUser(authenticatedUser);
////            fav.setPark(park);
////            fav.setSelectionDate(LocalDate.now());
////
////            favoritesRepository.save(fav);
////
////            return new ResponseEntity<>(HttpStatus.CREATED);
////        } catch (Exception e) {
////            e.printStackTrace();
////            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
////        }
////    }
////
////    // ************************
////    // 2. הסרת פארק מהמועדפים
////    // ************************
////    @DeleteMapping("/removeFavorite/{parkId}")
////    public ResponseEntity<?> removeFavorite(@PathVariable Long parkId) {
////        try {
////            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////            String userEmail = authentication.getName();
////
////            Users authenticatedUser = usersRepository.findByEmail(userEmail);
////            if (authenticatedUser == null) {
////                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
////            }
////
////            favoritesRepository.deleteByUserIdAndParkId(authenticatedUser.getId(), parkId);
////
////            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
////        } catch (Exception e) {
////            e.printStackTrace();
////            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
////        }
////    }
////
////    // *****************************************
////    // 3. כל המועדפים של המשתמש המחובר (ParksDTO)
////    // *****************************************
////    @GetMapping("/my")
////    public ResponseEntity<List<ParksDTO>> getMyFavorites() {
////        try {
////            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////            String userEmail = authentication.getName();
////
////            Users authenticatedUser = usersRepository.findByEmail(userEmail);
////            if (authenticatedUser == null) {
////                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
////            }
////
////            List<Favorites> favorites = favoritesRepository.findFavoritesByUserId(authenticatedUser.getId());
////
////            // הופכים לרשימת Parks
////            List<Parks> parks = favorites.stream()
////                    .map(Favorites::getPark)
////                    .toList();
////
////            // ממירים ל־DTO בעזרת ParksMapper
////            List<ParksDTO> dtos = parksMapper.parksToDto(parks);
////
////            return new ResponseEntity<>(dtos, HttpStatus.OK);
////        } catch (Exception e) {
////            e.printStackTrace();
////            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
////        }
////    }
////
////    // (אופציונלי) אם את עדיין רוצה להשאיר את ה־endpoint הישן לפי userId
////    @GetMapping("/getFavoritesByUserId/{id}")
////    public ResponseEntity<List<Favorites>> getFavoritesByUserId(@PathVariable Long id) {
////        try {
////            return new ResponseEntity<>(favoritesRepository.findFavoritesByUserId(id), HttpStatus.OK);
////        } catch (Exception e) {
////            e.printStackTrace();
////            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
////        }
////    }
////}
////
////
////
//package com.example.parks.Controller;
//
//import com.example.parks.dto.ParksDTO;
//import com.example.parks.model.Favorites;
//import com.example.parks.model.Parks;
//import com.example.parks.model.Users;
//import com.example.parks.service.FavoritesRepository;
//import com.example.parks.service.ParksRepository;
//import com.example.parks.service.UsersRepository;
//import com.example.parks.service.mappers.ParksMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/favorites")
//public class FavoritesController {
//
//    private final FavoritesRepository favoritesRepository;
//    private final ParksRepository parksRepository;
//    private final UsersRepository usersRepository;
//    private final ParksMapper parksMapper;
//
//    @Autowired
//    public FavoritesController(FavoritesRepository favoritesRepository,
//                               ParksRepository parksRepository,
//                               UsersRepository usersRepository,
//                               ParksMapper parksMapper) {
//        this.favoritesRepository = favoritesRepository;
//        this.parksRepository = parksRepository;
//        this.usersRepository = usersRepository;
//        this.parksMapper = parksMapper;
//    }
//
//    private Users getCurrentUser() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String email = auth.getName();
//        return usersRepository.findByEmail(email);
//    }
//
//    // ➕ הוספת פארק למועדפים
//    @PostMapping("/{parkId}")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<Void> addFavorite(@PathVariable Long parkId) {
//        Users user = getCurrentUser();
//        if (user == null) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//
//        Parks park = parksRepository.findById(parkId).orElse(null);
//        if (park == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        if (!favoritesRepository.existsByUserAndPark(user, park)) {
//            Favorites fav = new Favorites();
//            fav.setUser(user);
//            fav.setPark(park);
//            fav.setSelectionDate(LocalDate.now());
//            favoritesRepository.save(fav);
//        }
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    // ❌ הסרת פארק מהמועדפים
//    @DeleteMapping("/{parkId}")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<Void> removeFavorite(@PathVariable Long parkId) {
//        Users user = getCurrentUser();
//        if (user == null) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//
//        Parks park = parksRepository.findById(parkId).orElse(null);
//        if (park == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        favoritesRepository.deleteByUserAndPark(user, park);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    // 💚 כל המועדפים של המשתמש הנוכחי
//    @GetMapping("/my")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<List<ParksDTO>> getMyFavorites() {
//        Users user = getCurrentUser();
//        if (user == null) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//
//        List<Favorites> favorites = favoritesRepository.findByUser(user);
//        List<Parks> parks = favorites.stream()
//                .map(Favorites::getPark)
//                .toList();
//
//        return new ResponseEntity<>(parksMapper.parksToDto(parks), HttpStatus.OK);
//    }
//}
package com.example.parks.Controller;

import com.example.parks.dto.ParksDTO;
import com.example.parks.model.Favorites;
import com.example.parks.model.Parks;
import com.example.parks.model.Users;
import com.example.parks.service.FavoritesRepository;
import com.example.parks.service.ParksRepository;
import com.example.parks.service.UsersRepository;
import com.example.parks.service.mappers.ParksMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoritesController {

    private final FavoritesRepository favoritesRepository;
    private final ParksRepository parksRepository;
    private final UsersRepository usersRepository;
    private final ParksMapper parksMapper;

    @Autowired
    public FavoritesController(FavoritesRepository favoritesRepository,
                               ParksRepository parksRepository,
                               UsersRepository usersRepository,
                               ParksMapper parksMapper) {
        this.favoritesRepository = favoritesRepository;
        this.parksRepository = parksRepository;
        this.usersRepository = usersRepository;
        this.parksMapper = parksMapper;
    }

    // --------- פונקציה בטוחה להבאת המשתמש המחובר ---------
    private Users getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }

        Object principal = auth.getPrincipal();
        String email;

        if (principal instanceof UserDetails userDetails) {
            email = userDetails.getUsername();
        } else {
            email = auth.getName();
        }

        if (email == null || "anonymousUser".equals(email)) {
            return null;
        }

        return usersRepository.findByEmail(email);
    }

    // ➕ הוספת פארק למועדפים
    @PostMapping("/{parkId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> addFavorite(@PathVariable Long parkId) {
        try {
            Users user = getCurrentUser();
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            Parks park = parksRepository.findById(parkId).orElse(null);
            if (park == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            if (!favoritesRepository.existsByUserAndPark(user, park)) {
                Favorites fav = new Favorites();
                fav.setUser(user);
                fav.setPark(park);
                fav.setSelectionDate(LocalDate.now());
                favoritesRepository.save(fav);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace(); // כדי לראות בקונסול מה קרה
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ❌ הסרת פארק מהמועדפים
    @DeleteMapping("/{parkId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long parkId) {
        try {
            Users user = getCurrentUser();
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            Parks park = parksRepository.findById(parkId).orElse(null);
            if (park == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            favoritesRepository.deleteByUserAndPark(user, park);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace(); // פה תראי בקונסול מה ה-500 האמיתי
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 💚 כל המועדפים של המשתמש הנוכחי
    @GetMapping("/my")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ParksDTO>> getMyFavorites() {
        try {
            Users user = getCurrentUser();
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            List<Favorites> favorites = favoritesRepository.findByUser(user);
            List<Parks> parks = favorites.stream()
                    .map(Favorites::getPark)
                    .toList();

            return new ResponseEntity<>(parksMapper.parksToDto(parks), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
