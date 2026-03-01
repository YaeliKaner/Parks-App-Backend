//package com.example.parks.dto;
//
//import java.time.LocalDate;
//
//public class ReportsDTO {
//    private Long id;
//    private LocalDate reportingDate;
//    private String freeText;
//    private int satisfaction;
//    private String imageBase64; // במקום picture path
//    private Long userId;
//    private Long parkId;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public LocalDate getReportingDate() {
//        return reportingDate;
//    }
//
//    public void setReportingDate(LocalDate reportingDate) {
//        this.reportingDate = reportingDate;
//    }
//
//    public String getFreeText() {
//        return freeText;
//    }
//
//    public void setFreeText(String freeText) {
//        this.freeText = freeText;
//    }
//
//    public int getSatisfaction() {
//        return satisfaction;
//    }
//
//    public void setSatisfaction(int satisfaction) {
//        this.satisfaction = satisfaction;
//    }
//
//    public String getImageBase64() {
//        return imageBase64;
//    }
//
//    public void setImageBase64(String imageBase64) {
//        this.imageBase64 = imageBase64;
//    }
//
//    public Long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }
//
//    public Long getParkId() {
//        return parkId;
//    }
//
//    public void setParkId(Long parkId) {
//        this.parkId = parkId;
//    }
//}
package com.example.parks.dto;

import com.example.parks.model.Parks;
import com.example.parks.model.Users;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class ReportsDTO {
    private Long id;
    private LocalDate reportingDate;
    private String freeText;
    private int satisfaction;
    private String imageBase64; // במקום picture path
    private UsersDTO userDTO;
    private ParksDTO parkDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getReportingDate() {
        return reportingDate;
    }

    public void setReportingDate(LocalDate reportingDate) {
        this.reportingDate = reportingDate;
    }

    public String getFreeText() {
        return freeText;
    }

    public void setFreeText(String freeText) {
        this.freeText = freeText;
    }

    public int getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(int satisfaction) {
        this.satisfaction = satisfaction;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public UsersDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UsersDTO userDTO) {
        this.userDTO = userDTO;
    }

    public ParksDTO getParkDTO() {
        return parkDTO;
    }

    public void setParkDTO(ParksDTO parkDTO) {
        this.parkDTO = parkDTO;
    }
}
