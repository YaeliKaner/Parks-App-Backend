package com.example.parks.Controller;


import com.example.parks.dto.ParkDesignDTO;
import com.example.parks.dto.ParksDTO;
import com.example.parks.model.ParkDesign;
import com.example.parks.model.Parks;
import com.example.parks.model.Users;
import com.example.parks.service.ParkDesignRepository;
import com.example.parks.service.UsersRepository;
import com.example.parks.service.mappers.ParkDesignMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/parkDesign")
//@CrossOrigin
public class ParkDesignController {

    ParkDesignRepository parkDesignRepository;
    ParkDesignMapper parkDesignMapper;
    UsersRepository usersRepository;

    @Autowired
    public ParkDesignController(ParkDesignRepository parkDesignRepository, ParkDesignMapper parkDesignMapper, UsersRepository usersRepository) {
        this.parkDesignRepository = parkDesignRepository;
        this.parkDesignMapper = parkDesignMapper;
        this.usersRepository = usersRepository;
    }





    @GetMapping("/getParksDesigns")
    public ResponseEntity<List<ParkDesignDTO>> getParksDesigns() {
        try {
            List<ParkDesign> parksDesigns = parkDesignRepository.findAll();
            return new ResponseEntity<>(parkDesignMapper.parksDesignsToDto(parksDesigns), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/addParkDesign")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ParkDesignDTO> addParkDesign(@RequestBody ParkDesign pd) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            Users authenticatedUser = usersRepository.findByEmail(userEmail);
            if(authenticatedUser == null){
                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
            }
            pd.setUser(authenticatedUser);
            pd.setUpdateDate(LocalDate.now());
            ParkDesign pd1 = parkDesignRepository.save(pd);
            return new ResponseEntity<>(parkDesignMapper.toDto(pd1), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/getLatestDesignByParkAndFeature/{parkId}/{featureId}")
    public ResponseEntity<ParkDesignDTO> getLatestDesignByParkAndFeature(@PathVariable Long parkId, @PathVariable Long featureId){
        try{
            ParkDesign pd  = parkDesignRepository.findTopByParkIdAndFeatureIdOrderByUpdateDateDesc(parkId, featureId);
            if(pd == null){
               return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
               }
            return new ResponseEntity<>(parkDesignMapper.toDto(pd), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/getLatestDesignsForPark/{parkId}")
    public ResponseEntity<List<ParkDesignDTO>> getLatestDesignsForPark(@PathVariable Long parkId) {

        try {
            List<ParkDesign> allDesigns = parkDesignRepository.findByParkIdOrderByUpdateDateDescIdDesc(parkId);
            List<ParkDesign> result = new ArrayList<>();
            Set<Long> seen = new HashSet<>();

            for (ParkDesign pd : allDesigns) {
                if (seen.add(pd.getFeature().getId())) { // אם נוסף (כלומר לא היה קודם)
                    result.add(pd);

                }
            }
            return new ResponseEntity<>(parkDesignMapper.parksDesignsToDto(result), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}