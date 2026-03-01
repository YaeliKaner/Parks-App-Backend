//package com.example.parks.service.mappers;
//
//import com.example.parks.Payment;
//import com.example.parks.dto.ParksDTO;
//import com.example.parks.model.Cities;
//import com.example.parks.model.Parks;
//import com.example.parks.model.Users;
//import com.example.parks.service.ImageUtils;
//import org.mapstruct.AfterMapping;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.MappingTarget;
//
//
//import java.io.IOException;
//import java.nio.file.*;
//import java.time.LocalDate;
//import java.util.Base64;
//import java.util.List;
//
//
//@Mapper(componentModel = "spring", uses = {UsersMapper.class})
//public interface ParksMapper {
//    @Mapping(target = "imageBase64", ignore = true)
//    @Mapping(target = "userDTO", source = "user")
//    ParksDTO parkToDto(Parks p);
//    Parks dtoToEntity(ParksDTO p);
//    List<ParksDTO> parksToDto(List<Parks> p);
//
//    @AfterMapping
//    default void fillImage(@MappingTarget ParksDTO dto, Parks p) {
//        String path = p.getPicturePath();
////        System.out.println("cwd=" + java.nio.file.Paths.get("").toAbsolutePath());
////        System.out.println("picturePath=" + path);
//        try {
//            dto.setImageBase64(ImageUtils.getImage(path));
//        } catch (Exception e) {
//            e.printStackTrace();
//            dto.setImageBase64(null);
//        }
//    }
//}
//from here:
package com.example.parks.service.mappers;

import com.example.parks.dto.ParksDTO;
import com.example.parks.model.Parks;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UsersMapper.class})
public interface ParksMapper {

    @Mapping(target = "imageBase64", ignore = true)
    @Mapping(target = "userDTO", source = "user")
    ParksDTO parkToDto(Parks p);

    Parks dtoToEntity(ParksDTO p);

    List<ParksDTO> parksToDto(List<Parks> p);

    @AfterMapping
    default void fillImage(@MappingTarget ParksDTO dto, Parks p) {
        String path = p.getPicturePath();
        try {
            dto.setImageBase64(com.example.parks.service.ImageUtils.getImage(path));
        } catch (Exception e) {
            e.printStackTrace();
            dto.setImageBase64(null);
        }
    }
}
