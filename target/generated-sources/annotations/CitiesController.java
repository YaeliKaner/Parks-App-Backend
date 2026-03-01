package com.example.parks.Controller;

import com.example.parks.dto.ParksDTO;
import com.example.parks.model.Cities;
import com.example.parks.model.Parks;
import com.example.parks.service.CitiesRepository;
import com.example.parks.service.ParksRepository;
import com.example.parks.service.mappers.ParksMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/cities")
//@CrossOrigin
public class CitiesController {

    CitiesRepository citiesRepository;

    @Autowired
    public CitiesController(CitiesRepository citiesRepository) {
        this.citiesRepository = citiesRepository;
    }

    @GetMapping("/getCities")
    public ResponseEntity<List<Cities>> getCities(){
        try {
            List<Cities> cities = citiesRepository.findAllByOrderByName();
            return new ResponseEntity<>(cities, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addCity")
    public ResponseEntity<Cities> addCity(@RequestBody Cities c) {
        try {
            Cities c1 = citiesRepository.findByName(c.getName());
            if (c1 != null)
                return new ResponseEntity<>(c, HttpStatus.OK);//העיר כבר נמצאת בטבלה, אבל אין צורך להחזיר שגיאה אלא פשוט לא להוסיף אותה
            c = citiesRepository.save(c);
            return new ResponseEntity<>(c, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @GetMapping("/getCityById/{id}")
    public ResponseEntity<Cities> get(@PathVariable long id) throws IOException {
        Cities c = citiesRepository.findById(id).get();
        if (c != null)
            return new ResponseEntity<>(c, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }




//
//    CitiesRepository citiesRepository;
//
//    @Autowired
//    public CitiesController(CitiesRepository citiesRepository) {
//        this.citiesRepository = citiesRepository;
//    }
//
//    @GetMapping("/getCities")
//    public ResponseEntity<List<Cities>> getCities(){
//        try {
//            List<Cities> cities = citiesRepository.findAll();
//            return new ResponseEntity<>(cities, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PostMapping("/addCity")
//    public ResponseEntity<Cities> addCity(@RequestBody Cities c) {
//        try {
//            Cities c1 = citiesRepository.findByName(c.getName());
//            if (c1 != null)
//                return new ResponseEntity<>(c, HttpStatus.OK);//העיר כבר נמצאת בטבלה, אבל אין צורך להחזיר שגיאה אלא פשוט לא להוסיף אותה
//            c = citiesRepository.save(c);
//            return new ResponseEntity<>(c, HttpStatus.CREATED);
//        }
//        catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//
//
//
//    @GetMapping("/getCityById/{id}")
//    public ResponseEntity<Cities> get(@PathVariable long id) throws IOException {
//        Cities c = citiesRepository.findById(id).get();
//        if (c != null)
//            return new ResponseEntity<>(c, HttpStatus.OK);
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
}

