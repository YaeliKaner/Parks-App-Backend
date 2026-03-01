////package com.example.parks.model;
////
////import com.example.parks.PersonalStatus;
////
////import com.example.parks.RatingsBreakdown;
////import com.example.parks.dto.ParksDTO;
////import jakarta.persistence.*;
////
////import java.time.LocalDate;
////
////@Entity
////public class ParkDesign {
////    @Id
////    @GeneratedValue
////    private Long id;
////    private LocalDate updateDate;
////    private String remark;
////
////    @ManyToOne
////    private Parks park;
////
////    @ManyToOne
////    private Users user;
////
////    @ManyToOne
////    private Features feature;
////
//////    @ManyToOne
//////   private RatingsBreakdown ratingDetails;
////
////   @Enumerated(EnumType.STRING)
////   private RatingsBreakdown ratingsBreakdown;
////
////    public ParkDesign() {}
////
////    public ParkDesign(Long id, LocalDate updateDate, String remark, Parks park, Users user, Features feature, RatingsBreakdown ratingsBreakdown) {
////        this.id = id;
////        this.updateDate = updateDate;
////        this.remark = remark;
////        this.park = park;
////        this.user = user;
////        this.feature = feature;
////        this.ratingsBreakdown = ratingsBreakdown;
////    }
////
////    public Long getId() {
////        return id;
////    }
////
////    public void setId(Long id) {
////        this.id = id;
////    }
////
////    public LocalDate getUpdateDate() {
////        return updateDate;
////    }
////
////    public void setUpdateDate(LocalDate updateDate) {
////        this.updateDate = updateDate;
////    }
////
////    public String getRemark() {return remark;}
////
////    public void setRemark(String remark) {
////        this.remark = remark;
////    }
////
////    public Parks getPark() {
////        return park;
////    }
////
////    public void setPark(Parks park) {
////        this.park = park;
////    }
////
////    public Users getUser() {
////        return user;
////    }
////
////    public void setUser(Users user) {
////        this.user = user;
////    }
////
////    public Features getFeature() {
////        return feature;
////    }
////
////    public void setFeature(Features feature) {
////        this.feature = feature;
////    }
////
////    public RatingsBreakdown getRatingsBreakdown() {
////        return ratingsBreakdown;
////    }
////
////    public void setRatingsBreakdown(RatingsBreakdown ratingsBreakdown) {
////        this.ratingsBreakdown = ratingsBreakdown;
////    }
////}
//package com.example.parks.model;
//
//import com.example.parks.RatingsBreakdown;
//import jakarta.persistence.*;
//import java.time.LocalDate;
//
//@Entity
//public class ParkDesign {
//
//    @Id
//    @GeneratedValue
//    private Long id;
//
//    private LocalDate updateDate;
//    private String remark;
//
//    @ManyToOne
//    private Parks park;
//
//    @ManyToOne
//    private Users user;
//
//    @ManyToOne
//    private Features feature;
//
//    // ENUM במקום ManyToOne
//    @Enumerated(EnumType.STRING)
//    private RatingsBreakdown ratingsBreakdown;
//
//    public ParkDesign() {
//    }
//
//    public ParkDesign(Long id,
//                      LocalDate updateDate,
//                      String remark,
//                      Parks park,
//                      Users user,
//                      Features feature,
//                      RatingsBreakdown ratingsBreakdown) {
//        this.id = id;
//        this.updateDate = updateDate;
//        this.remark = remark;
//        this.park = park;
//        this.user = user;
//        this.feature = feature;
//        this.ratingsBreakdown = ratingsBreakdown;
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
//    public LocalDate getUpdateDate() {
//        return updateDate;
//    }
//
//    public void setUpdateDate(LocalDate updateDate) {
//        this.updateDate = updateDate;
//    }
//
//    public String getRemark() {
//        return remark;
//    }
//
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }
//
//    public Parks getPark() {
//        return park;
//    }
//
//    public void setPark(Parks park) {
//        this.park = park;
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
//    public Features getFeature() {
//        return feature;
//    }
//
//    public void setFeature(Features feature) {
//        this.feature = feature;
//    }
//
//    public RatingsBreakdown getRatingsBreakdown() {
//        return ratingsBreakdown;
//    }
//
//    public void setRatingsBreakdown(RatingsBreakdown ratingsBreakdown) {
//        this.ratingsBreakdown = ratingsBreakdown;
//    }
//}
//
package com.example.parks.model;

import com.example.parks.RatingsBreakdown;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class ParkDesign {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate updateDate;
    private String remark;

    @ManyToOne
    private Parks park;

    @ManyToOne
    private Users user;

    @ManyToOne
    private Features feature;

    @Enumerated(EnumType.STRING)
    private RatingsBreakdown ratingsBreakdown;

    public ParkDesign() {
    }

    public ParkDesign(Long id,
                      LocalDate updateDate,
                      String remark,
                      Parks park,
                      Users user,
                      Features feature,
                      RatingsBreakdown ratingsBreakdown) {
        this.id = id;
        this.updateDate = updateDate;
        this.remark = remark;
        this.park = park;
        this.user = user;
        this.feature = feature;
        this.ratingsBreakdown = ratingsBreakdown;
    }

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

    public Parks getPark() {
        return park;
    }

    public void setPark(Parks park) {
        this.park = park;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
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
}
