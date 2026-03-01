package com.example.parks.service.mappers;

import com.example.parks.dto.ParkDesignDTO;

import com.example.parks.dto.ParksDTO;
import com.example.parks.model.Features;
import com.example.parks.model.ParkDesign;
import com.example.parks.model.Parks;
//import com.example.parks.model.RatingsBreakdown;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UsersMapper.class, ParksMapper.class})
public interface ParkDesignMapper {
    @Mapping(target = "userDTO", source = "user")
    @Mapping(target = "parkDTO", source = "park")

    ParkDesignDTO toDto(ParkDesign e);

     ParkDesign toEntity(ParkDesignDTO d);

    List<ParkDesignDTO> parksDesignsToDto(List<ParkDesign> p);
}
