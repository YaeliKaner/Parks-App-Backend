package com.example.parks.service.mappers;

import com.example.parks.dto.ParkDesignDTO;
import com.example.parks.model.ParkDesign;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-28T17:37:16+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class ParkDesignMapperImpl implements ParkDesignMapper {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private ParksMapper parksMapper;

    @Override
    public ParkDesignDTO toDto(ParkDesign e) {
        if ( e == null ) {
            return null;
        }

        ParkDesignDTO parkDesignDTO = new ParkDesignDTO();

        parkDesignDTO.setUserDTO( usersMapper.userToDto( e.getUser() ) );
        parkDesignDTO.setParkDTO( parksMapper.parkToDto( e.getPark() ) );
        parkDesignDTO.setId( e.getId() );
        parkDesignDTO.setUpdateDate( e.getUpdateDate() );
        parkDesignDTO.setRemark( e.getRemark() );
        parkDesignDTO.setFeature( e.getFeature() );
        parkDesignDTO.setRatingsBreakdown( e.getRatingsBreakdown() );

        return parkDesignDTO;
    }

    @Override
    public ParkDesign toEntity(ParkDesignDTO d) {
        if ( d == null ) {
            return null;
        }

        ParkDesign parkDesign = new ParkDesign();

        parkDesign.setId( d.getId() );
        parkDesign.setUpdateDate( d.getUpdateDate() );
        parkDesign.setRemark( d.getRemark() );
        parkDesign.setFeature( d.getFeature() );
        parkDesign.setRatingsBreakdown( d.getRatingsBreakdown() );

        return parkDesign;
    }

    @Override
    public List<ParkDesignDTO> parksDesignsToDto(List<ParkDesign> p) {
        if ( p == null ) {
            return null;
        }

        List<ParkDesignDTO> list = new ArrayList<ParkDesignDTO>( p.size() );
        for ( ParkDesign parkDesign : p ) {
            list.add( toDto( parkDesign ) );
        }

        return list;
    }
}
