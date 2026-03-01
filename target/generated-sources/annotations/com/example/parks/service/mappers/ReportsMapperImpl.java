package com.example.parks.service.mappers;

import com.example.parks.dto.ReportsDTO;
import com.example.parks.model.Reports;
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
public class ReportsMapperImpl implements ReportsMapper {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private ParksMapper parksMapper;

    @Override
    public ReportsDTO reportToDto(Reports r) {
        if ( r == null ) {
            return null;
        }

        ReportsDTO reportsDTO = new ReportsDTO();

        reportsDTO.setUserDTO( usersMapper.userToDto( r.getUser() ) );
        reportsDTO.setParkDTO( parksMapper.parkToDto( r.getPark() ) );
        reportsDTO.setId( r.getId() );
        reportsDTO.setReportingDate( r.getReportingDate() );
        reportsDTO.setFreeText( r.getFreeText() );
        reportsDTO.setSatisfaction( r.getSatisfaction() );

        fillImage( reportsDTO, r );

        return reportsDTO;
    }

    @Override
    public Reports dtoToEntity(ReportsDTO p) {
        if ( p == null ) {
            return null;
        }

        Reports reports = new Reports();

        reports.setId( p.getId() );
        reports.setReportingDate( p.getReportingDate() );
        reports.setFreeText( p.getFreeText() );
        reports.setSatisfaction( p.getSatisfaction() );

        return reports;
    }

    @Override
    public List<ReportsDTO> reportsToDto(List<Reports> p) {
        if ( p == null ) {
            return null;
        }

        List<ReportsDTO> list = new ArrayList<ReportsDTO>( p.size() );
        for ( Reports reports : p ) {
            list.add( reportToDto( reports ) );
        }

        return list;
    }
}
