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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParksService {

    private final ParksRepository parksRepository;
    private final ParksMapper parksMapper;

    public ParksService(ParksRepository parksRepository, ParksMapper parksMapper) {
        this.parksRepository = parksRepository;
        this.parksMapper = parksMapper;
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
}
