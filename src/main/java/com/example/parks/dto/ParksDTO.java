//package com.example.parks.dto;
//
//import com.example.parks.model.Cities;
//import com.example.parks.model.Users;
//
//import java.time.LocalDate;
//
//public class ParksDTO {
//    private Long id;
//    private String name;
//    private String address;
//    private LocalDate uploadDate;
//    private UsersDTO userDTO;
//    private Cities city;
//    private String imageBase64;
//
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public LocalDate getUploadDate() {
//        return uploadDate;
//    }
//
//    public void setUploadDate(LocalDate uploadDate) {
//        this.uploadDate = uploadDate;
//    }
//
//    public UsersDTO getUserDTO() {
//        return userDTO;
//    }
//
//    public void setUserDTO(UsersDTO userDTO) {
//        this.userDTO = userDTO;
//    }
//
//    public Cities getCity() {
//        return city;
//    }
//
//    public void setCity(Cities city) {
//        this.city = city;
//    }
//
//    public String getImageBase64() {
//        return imageBase64;
//    }
//
//    public void setImageBase64(String imageBase64) {
//        this.imageBase64 = imageBase64;
//    }
//}
//from here
package com.example.parks.dto;

import com.example.parks.model.Cities;

import java.time.LocalDate;

public class ParksDTO {
    private Long id;
    private String name;
    private String address;
    private LocalDate uploadDate;
    private UsersDTO userDTO;
    private Cities city;
    private String imageBase64;

    // 🆕 קואורדינטות
    private Double latitude;
    private Double longitude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }

    public UsersDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UsersDTO userDTO) {
        this.userDTO = userDTO;
    }

    public Cities getCity() {
        return city;
    }

    public void setCity(Cities city) {
        this.city = city;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    // 🆕 getters/setters לקואורדינטות

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
