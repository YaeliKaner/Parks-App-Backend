//package com.example.parks.service.mappers;
//
//import com.example.parks.dto.ReportsDTO;
//import com.example.parks.model.Parks;
//import com.example.parks.model.Reports;
//import com.example.parks.model.Users;
//import org.mapstruct.Mapper;
//
//import java.nio.file.*; import java.util.Base64;
//
//@Mapper(componentModel = "spring")
//public interface ReportsMapper {
//}
package com.example.parks.service.mappers;

import com.example.parks.dto.ParksDTO;
import com.example.parks.dto.ReportsDTO;
import com.example.parks.model.Parks;
import com.example.parks.model.Reports;
import com.example.parks.model.Users;
import com.example.parks.service.ImageUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.nio.file.*; import java.util.Base64;
import java.util.List;

@Mapper(componentModel = "spring", uses = {UsersMapper.class, ParksMapper.class})
public interface ReportsMapper {
    @Mapping(target = "imageBase64", ignore = true)
    @Mapping(target = "userDTO", source = "user")
    @Mapping(target = "parkDTO", source = "park")
    ReportsDTO reportToDto(Reports r);
    Reports dtoToEntity(ReportsDTO p);
    List<ReportsDTO> reportsToDto(List<Reports> p);

    @AfterMapping
    default void fillImage(@MappingTarget ReportsDTO dto, Reports r) {
        String path = r.getPicturePath();
        try {
            dto.setImageBase64(ImageUtils.getImage(path));
        } catch (Exception e) {
            e.printStackTrace();
            dto.setImageBase64(null);
        }
    }
}