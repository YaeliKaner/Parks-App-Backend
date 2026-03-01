//package com.example.parks.model;
//
//import jakarta.persistence.*;
//
//
//import java.time.LocalDate;
//
//@Entity
//public class Reports {
//
//    @Id
//    @GeneratedValue
//    private Long id;
//    private LocalDate reportingDate;
//    private String freeText;
//    private int satisfaction;
//    private String picturePath;
//    @ManyToOne
//    private Users user;
//    @ManyToOne
//    private Parks park;
//
//    public Reports() {}
//
//    public Reports(Long id, LocalDate reportingDate, String freeText, int satisfaction, String picturePath, Users user, Parks park) {
//        this.id = id;
//        this.reportingDate = reportingDate;
//        this.freeText = freeText;
//        this.satisfaction = satisfaction;
//        this.picturePath = picturePath;
//        this.user = user;
//        this.park = park;
//    }
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
//    public String getPicturePath() {
//        return picturePath;
//    }
//
//    public void setPicturePath(String picturePath) {
//        this.picturePath = picturePath;
//    }
//
//    public Users getUser() {
//        return user;
//    }
//
//    public void setUser(Users user) {
//        this.user = user;
//    }
//
//    public Parks getPark() {
//        return park;
//    }
//
//    public void setPark(Parks park) {
//        this.park = park;
//    }
//}
package com.example.parks.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;


import java.time.LocalDate;

@Entity
public class Reports {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate reportingDate;

    @NotBlank(message = "You must enter a report.")
    private String freeText;

    @Range(min = 0, max = 5)
    private int satisfaction;

    private String picturePath;
    @ManyToOne
    private Users user;
    @ManyToOne
    private Parks park;

    public Reports() {}

    public Reports(Long id, LocalDate reportingDate, String freeText, int satisfaction, String picturePath, Users user, Parks park) {
        this.id = id;
        this.reportingDate = reportingDate;
        this.freeText = freeText;
        this.satisfaction = satisfaction;
        this.picturePath = picturePath;
        this.user = user;
        this.park = park;
    }

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

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Parks getPark() {
        return park;
    }

    public void setPark(Parks park) {
        this.park = park;
    }
}
