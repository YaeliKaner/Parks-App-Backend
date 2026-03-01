package com.example.parks.dto;

import com.example.parks.RatingsBreakdown;
import com.example.parks.model.Features;
import com.example.parks.model.Parks;
//import com.example.parks.model.RatingsBreakdown;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

public class ParkDesignDTO {

    private Long id;
    private LocalDate updateDate;
    private String remark;

    private ParksDTO parkDTO;

    private UsersDTO userDTO;

    private Features feature;

    private RatingsBreakdown ratingsBreakdown;



//    private RatingsBreakdown ratingDetails;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ParksDTO getParkDTO() {
        return parkDTO;
    }

    public void setParkDTO(ParksDTO parkDTO) {
        this.parkDTO = parkDTO;
    }

    public UsersDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UsersDTO userDTO) {
        this.userDTO = userDTO;
    }

    public Features getFeature() {
        return feature;
    }

    public void setFeature(Features feature) {
        this.feature = feature;
    }

    public RatingsBreakdown getRatingsBreakdown() {
        return ratingsBreakdown;
    }

    public void setRatingsBreakdown(RatingsBreakdown ratingsBreakdown) {
        this.ratingsBreakdown = ratingsBreakdown;
    }


    //    public RatingsBreakdown getRatingsBreakdown() {
//        return ratingsBreakdown;
//    }
//
//    public void setRatingsBreakdown(RatingsBreakdown ratingsBreakdown) {
//        this.ratingsBreakdown = ratingsBreakdown;
//    }
}
