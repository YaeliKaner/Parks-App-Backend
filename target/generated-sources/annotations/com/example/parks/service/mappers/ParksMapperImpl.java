package com.example.parks.service.mappers;

import com.example.parks.dto.ParksDTO;
import com.example.parks.model.Parks;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-02T12:58:03+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class ParksMapperImpl implements ParksMapper {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public ParksDTO parkToDto(Parks p) {
        if ( p == null ) {
            return null;
        }

        ParksDTO parksDTO = new ParksDTO();

        parksDTO.setUserDTO( usersMapper.userToDto( p.getUser() ) );
        parksDTO.setId( p.getId() );
        parksDTO.setName( p.getName() );
        parksDTO.setAddress( p.getAddress() );
        parksDTO.setUploadDate( p.getUploadDate() );
        parksDTO.setCity( p.getCity() );
        parksDTO.setLatitude( p.getLatitude() );
        parksDTO.setLongitude( p.getLongitude() );

        fillImage( parksDTO, p );

        return parksDTO;
    }

    @Override
    public Parks dtoToEntity(ParksDTO p) {
        if ( p == null ) {
            return null;
        }

        Parks parks = new Parks();

        parks.setId( p.getId() );
        parks.setName( p.getName() );
        parks.setAddress( p.getAddress() );
        parks.setLatitude( p.getLatitude() );
        parks.setLongitude( p.getLongitude() );
        parks.setUploadDate( p.getUploadDate() );
        parks.setCity( p.getCity() );

        return parks;
    }

    @Override
    public List<ParksDTO> parksToDto(List<Parks> p) {
        if ( p == null ) {
            return null;
        }

        List<ParksDTO> list = new ArrayList<ParksDTO>( p.size() );
        for ( Parks parks : p ) {
            list.add( parkToDto( parks ) );
        }

        return list;
    }
}
