//package com.example.parks.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//
//import java.util.List;
//
//@Entity
//public class Features {
//
//    @Id
//    @GeneratedValue
//    private Long id;
//    private String name;
//
//    @OneToMany(mappedBy = "feature")
//    @JsonIgnore
//    private List<ParkDesign> parkDesigns;
//
//    public Features() {}
//
//    public Features(Long id, String name, List<ParkDesign> parkDesigns) {
//        this.id = id;
//        this.name = name;
//        this.parkDesigns = parkDesigns;
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
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public List<ParkDesign> getParkDesigns() {
//        return parkDesigns;
//    }
//
//    public void setParkDesigns(List<ParkDesign> parkDesigns) {
//        this.parkDesigns = parkDesigns;
//    }
//}
package com.example.parks.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Features {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "feature")
    @JsonIgnore
    private List<ParkDesign> parkDesigns;

    public Features() {
    }

    public Features(Long id, String name, List<ParkDesign> parkDesigns) {
        this.id = id;
        this.name = name;
        this.parkDesigns = parkDesigns;
    }

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

    public List<ParkDesign> getParkDesigns() {
        return parkDesigns;
    }

    public void setParkDesigns(List<ParkDesign> parkDesigns) {
        this.parkDesigns = parkDesigns;
    }
}
