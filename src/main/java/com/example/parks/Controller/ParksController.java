/// /////package com.example.parks.Controller;
/// /////
/// /////
/// /////import com.example.parks.dto.ParksDTO;
/// /////import com.example.parks.model.*;
/// ///////import com.example.parks.service.ImageUtils;
/// /////import com.example.parks.service.AIChatService;
/// /////import com.example.parks.service.ImageUtils;
/// /////import com.example.parks.service.UsersRepository;
/// /////import com.example.parks.service.mappers.ParksMapper;
/// /////import com.example.parks.service.ParksRepository;
/// /////import com.fasterxml.jackson.annotation.JsonIgnore;
/// /////import jakarta.persistence.ManyToOne;
/// /////import jakarta.persistence.OneToMany;
/// /////import org.springframework.beans.factory.annotation.Autowired;
/// /////import org.springframework.http.HttpStatus;
/// /////import org.springframework.http.ResponseEntity;
/// /////import org.springframework.security.access.prepost.PreAuthorize;
/// /////import org.springframework.security.core.Authentication;
/// /////import org.springframework.security.core.context.SecurityContextHolder;
/// /////import org.springframework.security.core.userdetails.UserDetails;
/// /////import org.springframework.web.bind.annotation.*;
/// /////import org.springframework.web.multipart.MultipartFile;
/// /////
/// /////import java.io.IOException;
/// /////import java.time.LocalDate;
/// /////import java.util.List;
/// /////
/// /////@RestController
/// /////@RequestMapping("/api/parks")
/// ///////@CrossOrigin
/// /////public class ParksController {
/// /////
/// /////    ParksRepository parksRepository;
/// /////    ParksMapper parksMapper;
/// /////    UsersRepository usersRepository;
/// /////    AIChatService aiChatService;
/// /////
/// /////    @Autowired
/// /////    public ParksController(ParksRepository parksRepository, ParksMapper parksMapper, UsersRepository usersRepository, AIChatService aiChatService) {
/// /////        this.parksRepository = parksRepository;
/// /////        this.parksMapper = parksMapper;
/// /////        this.usersRepository = usersRepository;
/// /////        this.aiChatService = aiChatService;
/// /////    }
/// /////
/// /////
/// /////
/// /////
/// /////
/// ///////    public ParksController(ParksRepository parksRepository, ParksMapper parksMapper) {
/// ///////        this.parksRepository=parksRepository;
/// ///////        this.parksMapper=parksMapper;
/// ///////
/// ///////    }
/// /////
/// /////
/// /////
/// ///////    @GetMapping("/test")
/// ///////    public ResponseEntity<String> test() {
/// ///////        return new ResponseEntity<>("Controller is working!", HttpStatus.OK);
/// ///////    }
/// /////
/// ///////    @GetMapping("/getParkById/{id}")
/// ///////    public ResponseEntity<ParksDTO> get(@PathVariable long id) throws IOException {
/// ///////        Parks parks = parksRepository.findById(id).get();
/// ///////        if(p!=null)
/// ///////            return new ResponseEntity<>(parkToDto(),HttpStatus.OK);
/// ///////        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
/// ///////    }
/// /////
/// /////    @GetMapping("/getParks")
/// /////    public ResponseEntity<List<ParksDTO>> getParks() {
/// /////        try {
/// /////            List<Parks> parks = parksRepository.findAllByOrderByUpdateDateDescIdDesc();
/// /////            return new ResponseEntity<>(parksMapper.parksToDto(parks), HttpStatus.OK);
/// /////        } catch (Exception e) {
/// /////            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
/// /////        }
/// /////    }
/// /////
/// /////    @GetMapping("/getParkById/{id}")
/// /////    public ResponseEntity<ParksDTO> getParkById(@PathVariable Long id) {
/// /////        try {
/// /////            Parks park = parksRepository.findById(id).get();
/// /////            return new ResponseEntity<>(parksMapper.parkToDto(park), HttpStatus.OK);
/// /////        } catch (Exception e) {
/// /////            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
/// /////        }
/// /////    }
/// /////
/// /////    @GetMapping("/getParksByCityId/{id}")
/// /////    public ResponseEntity<List<Parks>> getParksByCityId(@PathVariable Long id) {
/// /////        try {
/// /////            return new ResponseEntity<>(parksRepository.findParksByCityId(id), HttpStatus.OK);
/// /////        } catch (Exception e) {
/// /////            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
/// /////        }
/// /////    }
/// ///////    @GetMapping("/getParksByFeatureId/{id}")
/// ///////    public ResponseEntity<List<Parks>> getParksByFeatureId(@PathVariable Long id) {
/// ///////        try {
/// ///////            return new ResponseEntity<>(parksRepository.findParksByFeatureId(id), HttpStatus.OK);
/// ///////        } catch (Exception e) {
/// ///////            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
/// ///////        }
/// ///////    }
/// /////    @PostMapping("/uploadPark")
/// /////    @PreAuthorize("isAuthenticated()")
/// /////    public ResponseEntity<ParksDTO> uploadParkWithImage(@RequestPart("image") MultipartFile file
/// /////            ,@RequestPart("Park") Parks p) {
/// /////        try {
/// /////            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
/// /////            String userEmail = authentication.getName();
/// /////            Users authenticatedUser = usersRepository.findByEmail(userEmail);
/// /////            if(authenticatedUser == null){
/// /////                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
/// /////            }
/// /////
/// /////            ImageUtils.uploadImage(file);
/// /////            p.setPicturePath(file.getOriginalFilename());
/// /////            p.setUploadDate(LocalDate.now());
/// /////            p.setUpdateDate(LocalDate.now());
/// /////            p.setUser(authenticatedUser);
/// /////            Parks park= parksRepository.save(p);
/// /////            return new ResponseEntity<>(parksMapper.parkToDto(park),HttpStatus.CREATED);
/// /////        }
/// /////        catch (IOException e) {
/// /////            System.out.println(e);
/// /////            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
/// /////        }
/// /////    }
/// /////
/// /////    @PutMapping("/updatePark")
/// /////    @PreAuthorize("isAuthenticated()")
/// /////    public ResponseEntity<Parks> updatePark(@RequestPart("image") MultipartFile file,
/// /////                                            @RequestPart("Park") Parks p){
/// /////        Parks park = parksRepository.findById(p.getId()).get();
/// /////        if(park == null){
/// /////            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
/// /////        }
/// /////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
/// /////        String currentUserEmail = authentication.getName();
/// /////        if(!((park.getUser().getEmail()).equals(currentUserEmail))){
/// /////            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
/// /////        }
/// /////        try{
/// /////            ImageUtils.uploadImage(file);
/// /////            park.setPicturePath(file.getOriginalFilename());
/// /////            park.setUpdateDate(LocalDate.now());
/// /////            park.setName(p.getName());
/// /////            park.setDesc(p.getDesc());
/// /////            park.setAddress(p.getAddress());
/// /////            park.setCity(p.getCity());
/// /////            parksRepository.save(park);
/// /////            return new ResponseEntity<>(park, HttpStatus.OK);
/// /////        }
/// /////     catch (Exception e) {
/// /////        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
/// /////    }
/// /////    }
/// /////
/// ///////    @GetMapping("/chat")
/// ///////    public String getResponse(@RequestParam String prompt){
/// ///////        return aiChatService.getResponse(prompt);
/// ///////    }
/// ///////    @GetMapping("/debug-parks")
/// ///////    public List<Parks> debugParks() {
/// ///////        return parksRepository.findAll();
/// ///////    }
/// ///////@GetMapping("/chat")
/// ///////public String chat(@RequestParam String prompt) {
/// ///////    return aiChatService.answerFromDb(prompt);
/// ///////}
/// /////// ... (שאר הקוד כמו קודם)
/// /////
/// /////    @GetMapping("/chat")
/// /////    @PreAuthorize("isAuthenticated()")  // הוסף כדי לקבל user
/// /////    public String chat(@RequestParam String prompt) {
/// /////        return aiChatService.answerFromDb(prompt);
/// /////    }
/// /////}
/// ///package com.example.parks.Controller;
/// ///
/// ///import com.example.parks.dto.ParksDTO;
/// ///import com.example.parks.model.*;
/// ///import com.example.parks.service.AIChatService;
/// ///import com.example.parks.service.ImageUtils;
/// ///import com.example.parks.service.UsersRepository;
/// ///import com.example.parks.service.mappers.ParksMapper;
/// ///import com.example.parks.service.ParksRepository;
/// ///import org.springframework.beans.factory.annotation.Autowired;
/// ///import org.springframework.http.HttpStatus;
/// ///import org.springframework.http.ResponseEntity;
/// ///import org.springframework.security.access.prepost.PreAuthorize;
/// ///import org.springframework.security.core.Authentication;
/// ///import org.springframework.security.core.context.SecurityContextHolder;
/// ///import org.springframework.web.bind.annotation.*;
/// ///import org.springframework.web.multipart.MultipartFile;
/// ///
/// ///import java.io.IOException;
/// ///import java.time.LocalDate;
/// ///import java.util.List;
/// ///
/// ///@RestController
/// ///@RequestMapping("/api/parks")
/// /////@CrossOrigin
/// ///public class ParksController {
/// ///
/// ///    ParksRepository parksRepository;
/// ///    ParksMapper parksMapper;
/// ///    UsersRepository usersRepository;
/// ///    AIChatService aiChatService;
/// ///
/// ///    @Autowired
/// ///    public ParksController(ParksRepository parksRepository,
/// ///                           ParksMapper parksMapper,
/// ///                           UsersRepository usersRepository,
/// ///                           AIChatService aiChatService) {
/// ///        this.parksRepository = parksRepository;
/// ///        this.parksMapper = parksMapper;
/// ///        this.usersRepository = usersRepository;
/// ///        this.aiChatService = aiChatService;
/// ///    }
/// ///
/// ///    @GetMapping("/getParks")
/// ///    public ResponseEntity<List<ParksDTO>> getParks() {
/// ///        try {
/// ///            List<Parks> parks = parksRepository.findAllByOrderByUpdateDateDescIdDesc();
/// ///            return new ResponseEntity<>(parksMapper.parksToDto(parks), HttpStatus.OK);
/// ///        } catch (Exception e) {
/// ///            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
/// ///        }
/// ///    }
/// ///
/// ///    @GetMapping("/getParkById/{id}")
/// ///    public ResponseEntity<ParksDTO> getParkById(@PathVariable Long id) {
/// ///        try {
/// ///            Parks park = parksRepository.findById(id).get();
/// ///            return new ResponseEntity<>(parksMapper.parkToDto(park), HttpStatus.OK);
/// ///        } catch (Exception e) {
/// ///            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
/// ///        }
/// ///    }
/// ///
/// ///    @GetMapping("/getParksByCityId/{id}")
/// ///    public ResponseEntity<List<Parks>> getParksByCityId(@PathVariable Long id) {
/// ///        try {
/// ///            return new ResponseEntity<>(parksRepository.findParksByCityId(id), HttpStatus.OK);
/// ///        } catch (Exception e) {
/// ///            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
/// ///        }
/// ///    }
/// ///
/// ///    @PostMapping("/uploadPark")
/// ///    @PreAuthorize("isAuthenticated()")
/// ///    public ResponseEntity<ParksDTO> uploadParkWithImage(@RequestPart("image") MultipartFile file,
/// ///                                                        @RequestPart("Park") Parks p) {
/// ///        try {
/// ///            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
/// ///            String userEmail = authentication.getName();
/// ///            Users authenticatedUser = usersRepository.findByEmail(userEmail);
/// ///            if (authenticatedUser == null) {
/// ///                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
/// ///            }
/// ///
/// ///            ImageUtils.uploadImage(file);
/// ///            p.setPicturePath(file.getOriginalFilename());
/// ///            p.setUploadDate(LocalDate.now());
/// ///            p.setUpdateDate(LocalDate.now());
/// ///            p.setUser(authenticatedUser);
/// ///            Parks park = parksRepository.save(p);
/// ///            return new ResponseEntity<>(parksMapper.parkToDto(park), HttpStatus.CREATED);
/// ///        } catch (IOException e) {
/// ///            System.out.println(e);
/// ///            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
/// ///        }
/// ///    }
/// ///
/// ///    @PutMapping("/updatePark")
/// ///    @PreAuthorize("isAuthenticated()")
/// ///    public ResponseEntity<Parks> updatePark(@RequestPart("image") MultipartFile file,
/// ///                                            @RequestPart("Park") Parks p) {
/// ///        Parks park = parksRepository.findById(p.getId()).get();
/// ///        if (park == null) {
/// ///            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
/// ///        }
/// ///        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
/// ///        String currentUserEmail = authentication.getName();
/// ///        if (!((park.getUser().getEmail()).equals(currentUserEmail))) {
/// ///            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
/// ///        }
/// ///        try {
/// ///            ImageUtils.uploadImage(file);
/// ///            park.setPicturePath(file.getOriginalFilename());
/// ///            park.setUpdateDate(LocalDate.now());
/// ///            park.setName(p.getName());
/// ///            park.setDesc(p.getDesc());
/// ///            park.setAddress(p.getAddress());
/// ///            park.setCity(p.getCity());
/// ///            parksRepository.save(park);
/// ///            return new ResponseEntity<>(park, HttpStatus.OK);
/// ///        } catch (Exception e) {
/// ///            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
/// ///        }
/// ///    }
/// ///
/// ///    // ===== צ'אט עם זיכרון =====
/// ///    @GetMapping("/chat")
/// ///    @PreAuthorize("isAuthenticated()")
/// ///    public String chat(@RequestParam String prompt) {
/// ///        return aiChatService.answerFromDb(prompt);
/// ///    }
/// ///
/// ///}
/// /package com.example.parks.Controller;
/// /
/// /import com.example.parks.dto.ParksDTO;
/// /import com.example.parks.model.Parks;
/// /import com.example.parks.model.Users;
/// /import com.example.parks.service.AIChatService;
/// /import com.example.parks.service.ImageUtils;
/// /import com.example.parks.service.ParksRepository;
/// /import com.example.parks.service.ParksService;
/// /import com.example.parks.service.UsersRepository;
/// /import com.example.parks.service.mappers.ParksMapper;
/// /import org.springframework.beans.factory.annotation.Autowired;
/// /import org.springframework.http.HttpStatus;
/// /import org.springframework.http.ResponseEntity;
/// /import org.springframework.security.access.prepost.PreAuthorize;
/// /import org.springframework.security.core.Authentication;
/// /import org.springframework.security.core.context.SecurityContextHolder;
/// /import org.springframework.web.bind.annotation.*;
/// /import org.springframework.web.multipart.MultipartFile;
/// /
/// /import java.io.IOException;
/// /import java.time.LocalDate;
/// /import java.util.List;
/// /
/// /@RestController
/// /@RequestMapping("/api/parks")
/// ///@CrossOrigin
/// /public class ParksController {
/// /
/// /    private final ParksRepository parksRepository;
/// /    private final ParksMapper parksMapper;
/// /    private final UsersRepository usersRepository;
/// /    private final AIChatService aiChatService;
/// /    private final ParksService parksService;   // 👈 ה-Service החדש
/// /
/// /    @Autowired
/// /    public ParksController(ParksRepository parksRepository,
/// /                           ParksMapper parksMapper,
/// /                           UsersRepository usersRepository,
/// /                           AIChatService aiChatService,
/// /                           ParksService parksService) {
/// /        this.parksRepository = parksRepository;
/// /        this.parksMapper = parksMapper;
/// /        this.usersRepository = usersRepository;
/// /        this.aiChatService = aiChatService;
/// /        this.parksService = parksService;
/// /    }
/// /
/// /    // ✅ מסך ראשי – משתמש ב-ParksService
/// /    @GetMapping("/getParks")
/// /    public ResponseEntity<List<ParksDTO>> getParks() {
/// /        try {
/// /            return new ResponseEntity<>(parksService.getAllParks(), HttpStatus.OK);
/// /        } catch (Exception e) {
/// /            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
/// /        }
/// /    }
/// /
/// /    @GetMapping("/getParkById/{id}")
/// /    public ResponseEntity<ParksDTO> getParkById(@PathVariable Long id) {
/// /        try {
/// /            Parks park = parksRepository.findById(id).orElse(null);
/// /            if (park == null) {
/// /                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
/// /            }
/// /            return new ResponseEntity<>(parksMapper.parkToDto(park), HttpStatus.OK);
/// /        } catch (Exception e) {
/// /            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
/// /        }
/// /    }
/// /
/// /    @GetMapping("/getParksByCityId/{id}")
/// /    public ResponseEntity<List<Parks>> getParksByCityId(@PathVariable Long id) {
/// /        try {
/// /            return new ResponseEntity<>(parksRepository.findParksByCityId(id), HttpStatus.OK);
/// /        } catch (Exception e) {
/// /            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
/// /        }
/// /    }
/// /
/// /    // ✅ חיפוש טקסט חופשי
/// /    @GetMapping("/search")
/// /    public ResponseEntity<List<ParksDTO>> searchParks(@RequestParam String term) {
/// /        try {
/// /            return new ResponseEntity<>(parksService.searchParks(term), HttpStatus.OK);
/// /        } catch (Exception e) {
/// /            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
/// /        }
/// /    }
/// /
/// /    @PostMapping("/uploadPark")
/// /    @PreAuthorize("isAuthenticated()")
/// /    public ResponseEntity<ParksDTO> uploadParkWithImage(@RequestPart("image") MultipartFile file,
/// /                                                        @RequestPart("Park") Parks p) {
/// /        try {
/// /            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
/// /            String userEmail = authentication.getName();
/// /            Users authenticatedUser = usersRepository.findByEmail(userEmail);
/// /            if (authenticatedUser == null) {
/// /                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
/// /            }
/// /
/// /            ImageUtils.uploadImage(file);
/// /            p.setPicturePath(file.getOriginalFilename());
/// /            p.setUploadDate(LocalDate.now());
/// /            p.setUpdateDate(LocalDate.now());
/// /            p.setUser(authenticatedUser);
/// /            Parks park = parksRepository.save(p);
/// /            return new ResponseEntity<>(parksMapper.parkToDto(park), HttpStatus.CREATED);
/// /        } catch (IOException e) {
/// /            System.out.println(e);
/// /            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
/// /        }
/// /    }
/// /
/// /    @PutMapping("/updatePark")
/// /    @PreAuthorize("isAuthenticated()")
/// /    public ResponseEntity<Parks> updatePark(@RequestPart("image") MultipartFile file,
/// /                                            @RequestPart("Park") Parks p) {
/// /        Parks park = parksRepository.findById(p.getId()).orElse(null);
/// /        if (park == null) {
/// /            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
/// /        }
/// /
/// /        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
/// /        String currentUserEmail = authentication.getName();
/// /        if (!park.getUser().getEmail().equals(currentUserEmail)) {
/// /            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
/// /        }
/// /
/// /        try {
/// /            ImageUtils.uploadImage(file);
/// /            park.setPicturePath(file.getOriginalFilename());
/// /            park.setUpdateDate(LocalDate.now());
/// /            park.setName(p.getName());
/// /            park.setDesc(p.getDesc());
/// /            park.setAddress(p.getAddress());
/// /            park.setCity(p.getCity());
/// /            parksRepository.save(park);
/// /            return new ResponseEntity<>(park, HttpStatus.OK);
/// /        } catch (Exception e) {
/// /            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
/// /        }
/// /    }
/// /
/// /    // צ'אט AI
/// /    @GetMapping("/chat")
/// /    @PreAuthorize("isAuthenticated()")
/// /    public String chat(@RequestParam String prompt) {
/// /        return aiChatService.answerFromDb(prompt);
/// /    }
/// /}
//package com.example.parks.Controller;
//
//import com.example.parks.dto.ParksDTO;
//import com.example.parks.model.Parks;
//import com.example.parks.model.Users;
//import com.example.parks.service.AIChatService;
//import com.example.parks.service.ImageUtils;
//import com.example.parks.service.ParksRepository;
//import com.example.parks.service.ParksService;
//import com.example.parks.service.UsersRepository;
//import com.example.parks.service.mappers.ParksMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/parks")
/// /@CrossOrigin
//public class ParksController {
//
//    private final ParksRepository parksRepository;
//    private final ParksMapper parksMapper;
//    private final UsersRepository usersRepository;
//    private final AIChatService aiChatService;
//    private final ParksService parksService;
//
//    @Autowired
//    public ParksController(ParksRepository parksRepository,
//                           ParksMapper parksMapper,
//                           UsersRepository usersRepository,
//                           AIChatService aiChatService,
//                           ParksService parksService) {
//        this.parksRepository = parksRepository;
//        this.parksMapper = parksMapper;
//        this.usersRepository = usersRepository;
//        this.aiChatService = aiChatService;
//        this.parksService = parksService;
//    }
//
//    // ✅ מסך ראשי – כל הפארקים
//    @GetMapping("/getParks")
//    public ResponseEntity<List<ParksDTO>> getParks() {
//        try {
//            return new ResponseEntity<>(parksService.getAllParks(), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    // 🌟 עמוד "מומלצים"
//    @GetMapping("/recommended")
//    public ResponseEntity<List<ParksDTO>> getRecommendedParks() {
//        try {
//            return new ResponseEntity<>(parksService.getRecommendedParks(), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @GetMapping("/getParkById/{id}")
//    public ResponseEntity<ParksDTO> getParkById(@PathVariable Long id) {
//        try {
//            Parks park = parksRepository.findById(id).orElse(null);
//            if (park == null) {
//                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//            }
//            return new ResponseEntity<>(parksMapper.parkToDto(park), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @GetMapping("/getParksByCityId/{id}")
//    public ResponseEntity<List<Parks>> getParksByCityId(@PathVariable Long id) {
//        try {
//            return new ResponseEntity<>(parksRepository.findParksByCityId(id), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    // 🔎 חיפוש טקסט חופשי
//    @GetMapping("/search")
//    public ResponseEntity<List<ParksDTO>> searchParks(@RequestParam String term) {
//        try {
//            return new ResponseEntity<>(parksService.searchParks(term), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PostMapping("/uploadPark")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<ParksDTO> uploadParkWithImage(@RequestPart("image") MultipartFile file,
//                                                        @RequestPart("Park") Parks p) {
//        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String userEmail = authentication.getName();
//            Users authenticatedUser = usersRepository.findByEmail(userEmail);
//            if (authenticatedUser == null) {
//                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
//            }
//
//            ImageUtils.uploadImage(file);
//            p.setPicturePath(file.getOriginalFilename());
//            p.setUploadDate(LocalDate.now());
//            p.setUpdateDate(LocalDate.now());
//            p.setUser(authenticatedUser);
//            Parks park = parksRepository.save(p);
//            return new ResponseEntity<>(parksMapper.parkToDto(park), HttpStatus.CREATED);
//        } catch (IOException e) {
//            System.out.println(e);
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PutMapping("/updatePark")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<Parks> updatePark(@RequestPart("image") MultipartFile file,
//                                            @RequestPart("Park") Parks p) {
//        Parks park = parksRepository.findById(p.getId()).orElse(null);
//        if (park == null) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentUserEmail = authentication.getName();
//        if (!park.getUser().getEmail().equals(currentUserEmail)) {
//            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
//        }
//
//        try {
//            ImageUtils.uploadImage(file);
//            park.setPicturePath(file.getOriginalFilename());
//            park.setUpdateDate(LocalDate.now());
//            park.setName(p.getName());
//            park.setDesc(p.getDesc());
//            park.setAddress(p.getAddress());
//            park.setCity(p.getCity());
//            parksRepository.save(park);
//            return new ResponseEntity<>(park, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    // צ'אט AI
//    @GetMapping("/chat")
//    @PreAuthorize("isAuthenticated()")
//    public String chat(@RequestParam String prompt) {
//        return aiChatService.answerFromDb(prompt);
//    }
//}
package com.example.parks.Controller;

import com.example.parks.dto.ParksDTO;
import com.example.parks.model.Cities;
import com.example.parks.model.Parks;
import com.example.parks.model.Users;
import com.example.parks.service.*;
import com.example.parks.service.CitiesRepository;
import com.example.parks.service.mappers.ParksMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/parks")
//@CrossOrigin
public class ParksController {

    private final ParksRepository parksRepository;
    private final ParksMapper parksMapper;
    private final UsersRepository usersRepository;
    private final AIChatService aiChatService;
    private final ParksService parksService;
    private final CitiesRepository citiesRepository;

    @Autowired
    public ParksController(ParksRepository parksRepository,
                           ParksMapper parksMapper,
                           UsersRepository usersRepository,
                           AIChatService aiChatService,
                           ParksService parksService,
                           CitiesRepository citiesRepository, ParksRepository parksRepository) {
        this.parksRepository = parksRepository;
        this.parksMapper = parksMapper;
        this.usersRepository = usersRepository;
        this.aiChatService = aiChatService;
        this.parksService = parksService;
        this.citiesRepository = citiesRepository;
    }


    @GetMapping("/getParks")
    public ResponseEntity<List<ParksDTO>> getParks() {
        try {
            return new ResponseEntity<>(parksService.getAllParks(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/recommended")
    public ResponseEntity<List<ParksDTO>> getRecommendedParks() {
        try {
            return new ResponseEntity<>(parksService.getRecommendedParks(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getParkById/{id}")
    public ResponseEntity<ParksDTO> getParkById(@PathVariable Long id) {
        try {
            Parks park = parksRepository.findById(id).orElse(null);
            if (park == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(parksMapper.parkToDto(park), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getParksByCityId/{id}")
    public ResponseEntity<List<Parks>> getParksByCityId(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(parksRepository.findParksByCityId(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/getParkByNameAndCity/{name}/{city}")
//    public ResponseEntity<ParksDTO> getParkByNameAndCity(@PathVariable String name, @PathVariable String city) {
//
//        Optional<ParksDTO> park = parksRepository.findByNameAndCity(name, city);
//
//        if (park.isPresent()) {
//            return ResponseEntity.ok(park.get());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/getParkByNameAndCity/{name}/{cityId}")
    public ResponseEntity<ParksDTO> getParkByNameAndCity(@PathVariable String name, @PathVariable Long cityId) {
        try {
            Cities city = citiesRepository.findById(cityId)
                    .orElseThrow(() -> new RuntimeException("City not found"));
            Parks p2 = parksRepository.findByNameAndCity(name, city);
            if (p2 == null)
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(parksMapper.parkToDto(p2), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(("/getParksOrderByRecommended"))
    public ResponseEntity<List<ParksDTO>> getParksOrderByRecommended() {
        try {
            List<Parks> recommended = parksRepository.findAllOrderBySatisfaction();
            if (recommended == null)
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(parksMapper.parksToDto(recommended), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ParksDTO>> searchParks(@RequestParam String term) {
        try {
            return new ResponseEntity<>(parksService.searchParks(term), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/uploadPark")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ParksDTO> uploadParkWithImage(@RequestPart("image") MultipartFile file,
                                                        @RequestPart("Park") Parks p) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            Users authenticatedUser = usersRepository.findByEmail(userEmail);
            if (authenticatedUser == null) {
                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
            }

            ImageUtils.uploadImage(file);
            p.setPicturePath(file.getOriginalFilename());
            p.setUploadDate(LocalDate.now());
            p.setUpdateDate(LocalDate.now());
            p.setUser(authenticatedUser);
            Parks p1 = parksRepository.findByNameAndCity(p.getName(), p.getCity());
            ParksDTO p1DTO = parksMapper.parkToDto(p1);
            if (p1DTO != null)
                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            Parks park = parksRepository.save(p);
            return new ResponseEntity<>(parksMapper.parkToDto(park), HttpStatus.CREATED);
        } catch (IOException e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updatePark")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Parks> updatePark(@RequestPart("image") MultipartFile file,
                                            @RequestPart("Park") Parks p) {
        Parks park = parksRepository.findById(p.getId()).orElse(null);
        if (park == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        if (!park.getUser().getEmail().equals(currentUserEmail)) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        try {
            ImageUtils.uploadImage(file);
            park.setPicturePath(file.getOriginalFilename());
            park.setUpdateDate(LocalDate.now());
            park.setName(p.getName());
            park.setDesc(p.getDesc());
            park.setAddress(p.getAddress());
            park.setCity(p.getCity());
            parksRepository.save(park);
            return new ResponseEntity<>(park, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // צ'אט AI
    @GetMapping("/chat")
    @PreAuthorize("isAuthenticated()")
    public String chat(@RequestParam String prompt) {
        return aiChatService.answerFromDb(prompt);
    }
}
