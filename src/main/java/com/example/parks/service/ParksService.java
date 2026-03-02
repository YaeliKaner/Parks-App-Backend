//////package com.example.parks.service;
//////
//////import com.example.parks.dto.ParksDTO;
//////import com.example.parks.model.Parks;
//////import com.example.parks.service.mappers.ParksMapper;
//////import org.springframework.stereotype.Service;
//////
//////import java.util.List;
//////
//////@Service
//////public class ParksService {
//////
//////    private final ParksRepository parksRepository;
//////    private final ParksMapper parksMapper;
//////
//////    public ParksService(ParksRepository parksRepository, ParksMapper parksMapper) {
//////        this.parksRepository = parksRepository;
//////        this.parksMapper = parksMapper;
//////    }
//////
//////    // כל הפארקים למסך הראשי
//////    public List<ParksDTO> getAllParks() {
//////        List<Parks> parks = parksRepository.findAllByOrderByUpdateDateDescIdDesc();
//////        // משתמשים ב-parksToDto של MapStruct
//////        return parksMapper.parksToDto(parks);
//////    }
//////
//////    // 🔎 חיפוש טקסט חופשי
//////    public List<ParksDTO> searchParks(String text) {
//////        if (text == null || text.isBlank()) {
//////            return List.of();
//////        }
//////
//////        List<Parks> parks = parksRepository.searchByText(text);
//////        return parksMapper.parksToDto(parks);
//////    }
//////}
////package com.example.parks.service;
////
////import com.example.parks.dto.ParksDTO;
////import com.example.parks.model.Parks;
////import com.example.parks.service.mappers.ParksMapper;
////import org.springframework.stereotype.Service;
////
////import java.util.List;
////
////@Service
////public class ParksService {
////
////    private final ParksRepository parksRepository;
////    private final ParksMapper parksMapper;
////
////    public ParksService(ParksRepository parksRepository,
////                        ParksMapper parksMapper) {
////        this.parksRepository = parksRepository;
////        this.parksMapper = parksMapper;
////    }
////
////    // כל הפארקים למסך הראשי
////    public List<ParksDTO> getAllParks() {
////        List<Parks> parks = parksRepository.findAllByOrderByUpdateDateDescIdDesc();
////        return parksMapper.parksToDto(parks);
////    }
////
////    // 🔎 חיפוש טקסט חופשי
////    public List<ParksDTO> searchParks(String text) {
////        if (text == null || text.isBlank()) {
////            return List.of();
////        }
////
////        List<Parks> parks = parksRepository.searchByText(text);
////        return parksMapper.parksToDto(parks);
////    }
////}
//package com.example.parks.service;
//
//import com.example.parks.dto.ParksDTO;
//import com.example.parks.model.Parks;
//import com.example.parks.service.mappers.ParksMapper;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ParksService {
//
//    private final ParksRepository parksRepository;
//    private final ParksMapper parksMapper;
//
//    public ParksService(ParksRepository parksRepository,
//                        ParksMapper parksMapper) {
//        this.parksRepository = parksRepository;
//        this.parksMapper = parksMapper;
//    }
//
//    // כל הפארקים למסך הראשי
//    public List<ParksDTO> getAllParks() {
//        List<Parks> parks = parksRepository.findAllByOrderByUpdateDateDescIdDesc();
//        return parksMapper.parksToDto(parks);
//    }
//
//    // 🔎 חיפוש טקסט חופשי
//    public List<ParksDTO> searchParks(String text) {
//        if (text == null || text.isBlank()) {
//            return List.of();
//        }
//
//        List<Parks> parks = parksRepository.searchByText(text);
//        return parksMapper.parksToDto(parks);
//    }
//
//    // 🌟 פארקים מומלצים – לפי ממוצע חוות דעת (satisfaction)
//    public List<ParksDTO> getRecommendedParks() {
//        double MIN_RATING = 4.0; // אפשר לשנות לסף אחר אם תרצי
//
//        List<Parks> parks = parksRepository.findRecommendedParks(MIN_RATING);
//        return parksMapper.parksToDto(parks);
//    }
//}
package com.example.parks.service;

import com.example.parks.dto.ParksDTO;
import com.example.parks.model.Parks;
import com.example.parks.service.mappers.ParksMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.io.IOException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ParksService {

    private final ParksRepository parksRepository;
    private final ParksMapper parksMapper;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;  // לפרסינג JSON

    public ParksService(ParksRepository parksRepository, ParksMapper parksMapper) {
        this.parksRepository = parksRepository;
        this.parksMapper = parksMapper;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public List<ParksDTO> getAllParks() {
        List<Parks> parks = parksRepository.findAllByOrderByUpdateDateDescIdDesc();
        return parksMapper.parksToDto(parks);
    }

    public List<ParksDTO> searchParks(String text) {
        if (text == null || text.isBlank()) {
            return List.of();
        }
        List<Parks> parks = parksRepository.searchByText(text);
        return parksMapper.parksToDto(parks);
    }

    // פארקים מומלצים – לפי ממוצע ציון בדוחות (reports)
    public List<ParksDTO> getRecommendedParks() {
        // אפשר לשחק עם הסף – 3.5 / 4 / 4.5
        List<Parks> parks = parksRepository.findRecommendedParks(4.0);
        return parksMapper.parksToDto(parks);
    }




//    @Value("${GEOAPIFY_API_KEY}")
    @Value("${geoapify.api.key}")

    private String apiKey;

    // מתודה לשמירה עם חישוב אוטומטי
    public Parks savePark(Parks park) {
        // אם אין קואורדינטות (או 0), חשב אותן
        if (park.getLatitude() == null && park.getLongitude() == null &&
                park.getAddress() != null && park.getCity() != null) {
            String address = park.getAddress() + " " + park.getCity() + " " + "ישראל";  // בנה כתובת
            String url = String.format("https://api.geoapify.com/v1/geocode/search?text=%s&apiKey=%s",
                    address, apiKey);

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                try {
                    JsonNode root = objectMapper.readTree(response.getBody());
                    JsonNode features = root.path("features");
                    if (!features.isEmpty()) {
                        JsonNode firstResult = features.get(0).path("properties");
                        double lat = firstResult.path("lat").asDouble();
                        double lon = firstResult.path("lon").asDouble();
                        park.setLatitude(lat);
                        park.setLongitude(lon);
                    } else {
                        throw new RuntimeException("No results from Geoapify for address: " + address);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Error parsing Geoapify response", e);
                }
            } else {
                throw new RuntimeException("Geoapify API call failed: " + response.getStatusCode());
            }
        }
        return parksRepository.save(park);  // שמור ל-DB
    }
}
