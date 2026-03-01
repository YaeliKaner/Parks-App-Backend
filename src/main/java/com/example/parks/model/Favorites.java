//package com.example.parks.model;
//
//import jakarta.persistence.*;
//
//import java.time.LocalDate;
//
//@Entity
//public class Favorites {
//    @Id
//    @GeneratedValue
//    private Long id;
//    private LocalDate selectionDate;
//
//    @ManyToOne
//    private Users user;
//
//    @ManyToOne
//    private Parks park;
//
//    public Favorites() {}
//
//    public Favorites(Long id, LocalDate selectionDate, Users user, Parks park) {
//        this.id = id;
//        this.selectionDate = selectionDate;
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
//    public LocalDate getSelectionDate() {
//        return selectionDate;
//    }
//
//    public void setSelectionDate(LocalDate selectionDate) {
//        this.selectionDate = selectionDate;
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

import java.time.LocalDate;

@Entity
public class Favorites {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate selectionDate;

    @ManyToOne
    private Users user;

    @ManyToOne
    private Parks park;

    public Favorites() {
    }

    public Favorites(Long id, LocalDate selectionDate, Users user, Parks park) {
        this.id = id;
        this.selectionDate = selectionDate;
        this.user = user;
        this.park = park;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSelectionDate() {
        return selectionDate;
    }

    public void setSelectionDate(LocalDate selectionDate) {
        this.selectionDate = selectionDate;
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
