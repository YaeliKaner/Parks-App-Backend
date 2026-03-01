//package com.example.parks.Controller;
//
//import com.example.parks.model.Features;
//import com.example.parks.service.FeaturesRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/features")
////@CrossOrigin
//public class FeaturesController {
//
//    FeaturesRepository featuresRepository;
//
//
//    @Autowired
//    public FeaturesController(FeaturesRepository featuresRepository) {
//        this.featuresRepository = featuresRepository;
//
//    }
//
//}
package com.example.parks.Controller;

import com.example.parks.dto.ParksDTO;
import com.example.parks.model.Features;
import com.example.parks.model.Parks;
import com.example.parks.service.FeaturesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/features")
//@CrossOrigin
public class FeaturesController {

    FeaturesRepository featuresRepository;


    @Autowired
    public FeaturesController(FeaturesRepository featuresRepository) {
        this.featuresRepository = featuresRepository;

    }

    @GetMapping("/getFeatures")
    public ResponseEntity<List<Features>> getFeatures() {
        try {
            List<Features> features = featuresRepository.findAll();
            return new ResponseEntity<>(features, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}