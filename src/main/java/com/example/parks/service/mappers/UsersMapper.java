package com.example.parks.service.mappers;

import com.example.parks.PersonalStatus;
import com.example.parks.dto.ParksDTO;
import com.example.parks.dto.UsersDTO;
import com.example.parks.dto.UsersLogInDTO;
import com.example.parks.dto.UsersSignUpDTO;
import com.example.parks.model.Cities;
import com.example.parks.model.Parks;
import com.example.parks.model.Users;
import com.example.parks.service.ImageUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsersMapper {
    @Mapping(target = "imageBase64", ignore = true)
    UsersDTO userToDto(Users u);
    Users dtoToEntity(UsersDTO u);
    List<UsersDTO> usersToDto(List<Users> u);

    @AfterMapping
    default void fillImage(@MappingTarget UsersDTO dto, Users u) {
        String path = u.getPicturePath();
        try {
            dto.setImageBase64(ImageUtils.getImage(path));
        } catch (Exception e) {
            e.printStackTrace();
            dto.setImageBase64(null);
        }
    }
}