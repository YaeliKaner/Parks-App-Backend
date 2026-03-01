//package com.example.parks.Controller;
//
//
//import com.example.parks.dto.ParksDTO;
//import com.example.parks.model.Parks;
////import com.example.parks.service.ImageUtils;
//import com.example.parks.model.Reports;
//import com.example.parks.service.ImageUtils;
//import com.example.parks.service.ReportsRepository;
//import com.example.parks.service.mappers.ParksMapper;
//import com.example.parks.service.ParksRepository;
//import com.example.parks.service.mappers.ReportsMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/Reports")
////@CrossOrigin
//public class ReportsController {
//
//    ReportsRepository reportsRepository;
//    ReportsMapper reportsMapper;
//
//    @Autowired
//    public ReportsController(ReportsRepository reportsRepository, ReportsMapper reportsMapper) {
//        this.reportsRepository=reportsRepository;
//        this.reportsMapper= reportsMapper;
//    }
//
//
//    @GetMapping("/getReportsByParkId/{id}")
//    public ResponseEntity<List<Reports>> getReportsByParkId(@PathVariable Long id) {
//        try {
//            return new ResponseEntity<>( reportsRepository.findReportsByParkId(id), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//
//    @PostMapping("/uploadReport")
//    public ResponseEntity<Reports> uploadReportWithImage(@RequestPart("image") MultipartFile file
//            ,@RequestPart("") Reports r) {
//        try {
//            ImageUtils.uploadImage(file);
//            r.setPicturePath(file.getOriginalFilename());
//            Reports report= reportsRepository.save(r);
//            return new ResponseEntity<>(report,HttpStatus.CREATED);
//
//        } catch (IOException e) {
//            System.out.println(e);
//            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//
//    }
//}
package com.example.parks.Controller;


import com.example.parks.dto.ParksDTO;
import com.example.parks.dto.ReportsDTO;
import com.example.parks.model.Parks;
//import com.example.parks.service.ImageUtils;
import com.example.parks.model.Reports;
import com.example.parks.model.Users;
import com.example.parks.service.*;
import com.example.parks.service.mappers.ParksMapper;
import com.example.parks.service.mappers.ReportsMapper;
import jakarta.validation.Valid;
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

@RestController
@RequestMapping("/api/reports")
//@CrossOrigin
public class ReportsController {

    ReportsRepository reportsRepository;
    ReportsMapper reportsMapper;
    UsersRepository usersRepository;
    AIChatService aiChatService;
    ParksRepository parksRepository;


    @Autowired
    public ReportsController(ReportsRepository reportsRepository, ReportsMapper reportsMapper, UsersRepository usersRepository, AIChatService aiChatService, ParksRepository parksRepository) {
        this.reportsRepository = reportsRepository;
        this.reportsMapper = reportsMapper;
        this.usersRepository = usersRepository;
        this.aiChatService = aiChatService;
        this.parksRepository = parksRepository;
    }

    @GetMapping("/getReportsByParkId/{id}")
    public ResponseEntity<List<ReportsDTO>> getReportsByParkId(@PathVariable Long id) {
        try {
            List<Reports> reports = reportsRepository.findAllByParkIdOrderByReportingDateDescIdDesc(id);
            return new ResponseEntity<>(reportsMapper.reportsToDto(reports), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    @GetMapping("/getsReportByParkId/{id}")
//    public ResponseEntity<List<Reports>> getReportByParkId(@PathVariable Long id) {
//        try {
//            return new ResponseEntity<>(reportsRepository.findReportsByParkId(id), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    @PostMapping("/uploadReport/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReportsDTO> uploadReport(
            @PathVariable("id") Long parkId,
            @RequestPart("image") MultipartFile file,
            @Valid @RequestPart("Report") Reports r) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            Users authenticatedUser = usersRepository.findByEmail(userEmail);
            if (authenticatedUser == null) {
                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
            }

            Parks park = parksRepository.findById(parkId).orElse(null);
            if (park == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }


            if (file != null && !file.isEmpty()) {
                ImageUtils.uploadImage(file);
                r.setPicturePath(file.getOriginalFilename());
            }
//            ImageUtils.uploadImage(file);
//
//            r.setPicturePath(file.getOriginalFilename());
            r.setReportingDate(LocalDate.now());
            r.setUser(authenticatedUser);
            r.setPark(park);

            Reports report = reportsRepository.save(r);
            return new ResponseEntity<>(reportsMapper.reportToDto(report), HttpStatus.CREATED);

        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    @PostMapping("/uploadReport/{id}")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<ReportsDTO> uploadReportWithImage(@RequestPart("image") MultipartFile file
//            , @Valid @RequestPart("Report") Reports r) {
//        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String userEmail = authentication.getName();
//            Users authenticatedUser = usersRepository.findByEmail(userEmail);
//            if(authenticatedUser == null){
//                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
//            }
//
//            ImageUtils.uploadImage(file);
//            r.setPicturePath(file.getOriginalFilename());
//            r.setReportingDate(LocalDate.now());
//            r.setUser(authenticatedUser);
//            Reports report= reportsRepository.save(r);
//            return new ResponseEntity<>(reportsMapper.reportToDto(report),HttpStatus.CREATED);
//        }
//        catch (IOException e) {
//            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
