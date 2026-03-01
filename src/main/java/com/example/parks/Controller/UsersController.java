package com.example.parks.Controller;

import com.example.parks.dto.UsersDTO;
import com.example.parks.model.Users;
import com.example.parks.security.CustomUserDetails;
import com.example.parks.security.jwt.JwtUtils;
import com.example.parks.service.ImageUtils;
import com.example.parks.service.RoleRepository;
import com.example.parks.service.UsersRepository;
import com.example.parks.service.mappers.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/Users")
//@CrossOrigin
public class UsersController {

    UsersRepository usersRepository;
    UsersMapper usersMapper;
    RoleRepository roleRepository;
    AuthenticationManager authenticationManager;
    JwtUtils jwtUtils;


    @Autowired
    public UsersController(UsersRepository usersRepository,
                           UsersMapper usersMapper,
                           RoleRepository roleRepository,
                           AuthenticationManager authenticationManager,
                           JwtUtils jwtUtils) {
        this.usersRepository = usersRepository;
        this.usersMapper = usersMapper;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/getUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Users>> getUsers() {
        try {
            return new ResponseEntity<>(usersRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody Users u) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(u.getEmail(), u.getPassword()));

        //שומר את האימות
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //CustomUserDetails לוקח את פרטי המשתמש ומכניס אותם
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(userDetails.getUsername());
    }

//    @PostMapping("/signUp")
//    public ResponseEntity<?> signUp(@RequestBody Users user) {
//        //נבדוק ששם המשתמש לא קיים
//        Users u = usersRepository.findByEmail(user.getEmail());
//        if (u != null)
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        String pass = user.getPassword();//הסיסמא שהמשתמש הכניס - לא מוצפנת
//        user.setPassword(new BCryptPasswordEncoder().encode(pass));
//
//        user.getRoles().add(roleRepository.findById((long) 1).get());
//        usersRepository.save(user);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }


    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestPart("image") MultipartFile file,
                                    @RequestPart("User") Users u) {
        try {
            Users u1 = usersRepository.findByEmail(u.getEmail());
            if (u1 != null)
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            String pass = u.getPassword();
            u.setPassword(new BCryptPasswordEncoder().encode(pass));

            u.getRoles().add(roleRepository.findById((long) 1).get());
            ImageUtils.uploadImage(file);
            u.setPicturePath(file.getOriginalFilename());

            Users user = usersRepository.save(u);
            return new ResponseEntity<>(usersMapper.userToDto(user), HttpStatus.CREATED);
        }
        catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signOut")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> signOut() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("you've been signed out!");
    }

    @GetMapping("/isLoggedIn")
    public ResponseEntity<?> isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UsersDTO> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            Users user = usersRepository.findByEmail(auth.getName());

            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(usersMapper.userToDto(user), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }











////    UsersRepository usersRepository;
////    UsersMapper usersMapper;
////    private JwtUtils jwtUtils;
//
//  UsersRepository usersRepository;
//  UsersMapper usersMapper;
//  RoleRepository roleRepository;
//  AuthenticationManager authenticationManager;
//  JwtUtils jwtUtils;
//
//
//    @Autowired
//    public UsersController(UsersRepository usersRepository,
//                           UsersMapper usersMapper,
//                           RoleRepository roleRepository,
//                           AuthenticationManager authenticationManager,
//                           JwtUtils jwtUtils) {
//        this.usersRepository = usersRepository;
//        this.usersMapper = usersMapper;
//        this.roleRepository = roleRepository;
//        this.authenticationManager = authenticationManager;
//        this.jwtUtils = jwtUtils;
//    }
//
//    @GetMapping("/getUsers")
//    public ResponseEntity<List<Users>> getUsers(){
//        try{
//            return new ResponseEntity<>(usersRepository.findAll(), HttpStatus.OK);
//        }
//        catch(Exception e){
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//
//
//    @PostMapping("/signIn")
//    public ResponseEntity<?> signIn(@RequestBody Users u){
//        Authentication authentication=authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(u.getEmail(),u.getPassword()));
//
//        //שומר את האימות
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        //CustomUserDetails לוקח את פרטי המשתמש ומכניס אותם
//        CustomUserDetails userDetails=(CustomUserDetails)authentication.getPrincipal();
//
//        ResponseCookie jwtCookie=jwtUtils.generateJwtCookie(userDetails);
//
//        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,jwtCookie.toString())
//                .body(userDetails.getUsername());
//    }
//
//    @PostMapping("/signUp")
//    public ResponseEntity<?> signUp(@RequestBody Users user){
//        //נבדוק ששם המשתמש לא קיים
//        Users u=usersRepository.findByEmail(user.getEmail());
//        if(u!=null)
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        String pass=user.getPassword();//הסיסמא שהמשתמש הכניס - לא מוצפנת
//        user.setPassword(new BCryptPasswordEncoder().encode(pass));
//
//        user.getRoles().add(roleRepository.findById((long)1).get());
//        usersRepository.save(user);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
//
//    @PostMapping("/signOut")
//    public ResponseEntity<?> signOut(){
//        ResponseCookie cookie=jwtUtils.getCleanJwtCookie();
//        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString())
//                .body("you've been signed out!");
//    }
}
