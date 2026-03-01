package com.example.parks.service.mappers;

import com.example.parks.dto.UsersDTO;
import com.example.parks.model.Users;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-28T17:37:16+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class UsersMapperImpl implements UsersMapper {

    @Override
    public UsersDTO userToDto(Users u) {
        if ( u == null ) {
            return null;
        }

        UsersDTO usersDTO = new UsersDTO();

        usersDTO.setId( u.getId() );
        usersDTO.setName( u.getName() );

        fillImage( usersDTO, u );

        return usersDTO;
    }

    @Override
    public Users dtoToEntity(UsersDTO u) {
        if ( u == null ) {
            return null;
        }

        Users users = new Users();

        users.setId( u.getId() );
        users.setName( u.getName() );

        return users;
    }

    @Override
    public List<UsersDTO> usersToDto(List<Users> u) {
        if ( u == null ) {
            return null;
        }

        List<UsersDTO> list = new ArrayList<UsersDTO>( u.size() );
        for ( Users users : u ) {
            list.add( userToDto( users ) );
        }

        return list;
    }
}
