//package com.example.parks.model;
//
//
//
//import com.example.parks.PersonalStatus;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//
//import java.time.LocalDate;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Entity
//public class Users {
//
//    @Id
//    @GeneratedValue
//    private Long id;
//    private String name;
//    private String password;
//    private LocalDate birthDate;
//    @ManyToOne
//    private Cities city;
//    private String phone;
//    private String email;
//    @Enumerated(EnumType.STRING)
//    private PersonalStatus personalStatus;
//
//    @OneToMany(mappedBy = "user")
//    @JsonIgnore
//    private List<Parks> parks;
//
//    @OneToMany(mappedBy = "user")
//    @JsonIgnore
//    private List<Reports> reports;
//
//    @OneToMany(mappedBy = "user")
//    @JsonIgnore
//    private List<Favorites> favorites;
//
//    @OneToMany(mappedBy = "user")
//    @JsonIgnore
//    private List<ParkDesign> parkDesigns;
//
//    @ManyToMany
//    private Set<Role> roles = new HashSet<>();
//
//
//    public Users() {}
//
//
//    public Users(Long id, String name, String password, LocalDate birthDate, Cities city, String phone, String email, PersonalStatus personalStatus, List<Parks> parks, List<Reports> reports, List<Favorites> favorites, List<ParkDesign> parkDesigns, Set<Role> roles) {
//        this.id = id;
//        this.name = name;
//        this.password = password;
//        this.birthDate = birthDate;
//        this.city = city;
//        this.phone = phone;
//        this.email = email;
//        this.personalStatus = personalStatus;
//        this.parks = parks;
//        this.reports = reports;
//        this.favorites = favorites;
//        this.parkDesigns = parkDesigns;
//        this.roles = roles;
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
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public LocalDate getBirthSDate() {
//        return birthDate;
//    }
//
//    public void setBirthSDate(LocalDate birthSDate) {
//        this.birthDate = birthSDate;
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
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public PersonalStatus getPersonalStatus() {
//        return personalStatus;
//    }
//
//    public void setPersonalStatus(PersonalStatus personalStatus) {
//        this.personalStatus = personalStatus;
//    }
//
//    public List<Parks> getParks() {
//        return parks;
//    }
//
//    public void setParks(List<Parks> parks) {
//        this.parks = parks;
//    }
//
//    public List<Reports> getReports() {
//        return reports;
//    }
//
//    public void setReports(List<Reports> reports) {
//        this.reports = reports;
//    }
//
//    public List<Favorites> getFavorites() {
//        return favorites;
//    }
//
//    public void setFavorites(List<Favorites> favorites) {
//        this.favorites = favorites;
//    }
//
//    public LocalDate getBirthDate() {
//        return birthDate;
//    }
//
//    public void setBirthDate(LocalDate birthDate) {
//        this.birthDate = birthDate;
//    }
//
//    public List<ParkDesign> getParkDesigns() {
//        return parkDesigns;
//    }
//
//    public void setParkDesigns(List<ParkDesign> parkDesigns) {
//        this.parkDesigns = parkDesigns;
//    }
//
//    public Set<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<Role> roles) {
//        this.roles = roles;
//    }
//}
package com.example.parks.model;



import com.example.parks.PersonalStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Users {




    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "You must enter the your name.")
    private String name;

    @NotBlank(message = "You must enter the your password.")
    private String password;

    private LocalDate birthDate;
    @ManyToOne
    private Cities city;

    @NotBlank(message = "Phone is required")
    @Pattern(
            regexp = "^(05\\d{8}|0[1-46-9]\\d{7})$",
            message = "Invalid phone number format"
    )
    private String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    private String email;
    @Enumerated(EnumType.STRING)
    private PersonalStatus personalStatus;

    private String picturePath;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Parks> parks;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Reports> reports;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Favorites> favorites;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<ParkDesign> parkDesigns;

    @ManyToMany
    private Set<Role> roles = new HashSet<>();


    public Users() {}


    public Users(Long id, String name, String password, LocalDate birthDate, Cities city, String phone, String email, PersonalStatus personalStatus, String picturePath, List<Parks> parks, List<Reports> reports, List<Favorites> favorites, List<ParkDesign> parkDesigns, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.birthDate = birthDate;
        this.city = city;
        this.phone = phone;
        this.email = email;
        this.personalStatus = personalStatus;
        this.picturePath = picturePath;
        this.parks = parks;
        this.reports = reports;
        this.favorites = favorites;
        this.parkDesigns = parkDesigns;
        this.roles = roles;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthSDate() {
        return birthDate;
    }

    public void setBirthSDate(LocalDate birthSDate) {
        this.birthDate = birthSDate;
    }

    public Cities getCity() {
        return city;
    }

    public void setCity(Cities city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PersonalStatus getPersonalStatus() {
        return personalStatus;
    }

    public void setPersonalStatus(PersonalStatus personalStatus) {
        this.personalStatus = personalStatus;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public List<Parks> getParks() {
        return parks;
    }

    public void setParks(List<Parks> parks) {
        this.parks = parks;
    }

    public List<Reports> getReports() {
        return reports;
    }

    public void setReports(List<Reports> reports) {
        this.reports = reports;
    }

    public List<Favorites> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorites> favorites) {
        this.favorites = favorites;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public List<ParkDesign> getParkDesigns() {
        return parkDesigns;
    }

    public void setParkDesigns(List<ParkDesign> parkDesigns) {
        this.parkDesigns = parkDesigns;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }









//
//    @Id
//    @GeneratedValue
//    private Long id;
//
//    @NotBlank(message = "You must enter the your name.")
//    private String name;
//
//    @NotBlank(message = "You must enter the your password.")
//    private String password;
//
//    private LocalDate birthDate;
//    @ManyToOne
//    private Cities city;
//
//    @NotBlank(message = "Phone is required")
//    @Pattern(
//            regexp = "^(05\\d{8}|0[1-46-9]\\d{7})$",
//            message = "Invalid phone number format"
//    )
//    private String phone;
//
//    @NotBlank(message = "Email is required")
//    @Email(message = "Email format is invalid")
//    private String email;
//    @Enumerated(EnumType.STRING)
//    private PersonalStatus personalStatus;
//
//    @OneToMany(mappedBy = "user")
//    @JsonIgnore
//    private List<Parks> parks;
//
//    @OneToMany(mappedBy = "user")
//    @JsonIgnore
//    private List<Reports> reports;
//
//    @OneToMany(mappedBy = "user")
//    @JsonIgnore
//    private List<Favorites> favorites;
//
//    @OneToMany(mappedBy = "user")
//    @JsonIgnore
//    private List<ParkDesign> parkDesigns;
//
//    @ManyToMany
//    private Set<Role> roles = new HashSet<>();
//
//
//    public Users() {}
//
//
//    public Users(Long id, String name, String password, LocalDate birthDate, Cities city, String phone, String email, PersonalStatus personalStatus, List<Parks> parks, List<Reports> reports, List<Favorites> favorites, List<ParkDesign> parkDesigns, Set<Role> roles) {
//        this.id = id;
//        this.name = name;
//        this.password = password;
//        this.birthDate = birthDate;
//        this.city = city;
//        this.phone = phone;
//        this.email = email;
//        this.personalStatus = personalStatus;
//        this.parks = parks;
//        this.reports = reports;
//        this.favorites = favorites;
//        this.parkDesigns = parkDesigns;
//        this.roles = roles;
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
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public LocalDate getBirthSDate() {
//        return birthDate;
//    }
//
//    public void setBirthSDate(LocalDate birthSDate) {
//        this.birthDate = birthSDate;
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
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public PersonalStatus getPersonalStatus() {
//        return personalStatus;
//    }
//
//    public void setPersonalStatus(PersonalStatus personalStatus) {
//        this.personalStatus = personalStatus;
//    }
//
//    public List<Parks> getParks() {
//        return parks;
//    }
//
//    public void setParks(List<Parks> parks) {
//        this.parks = parks;
//    }
//
//    public List<Reports> getReports() {
//        return reports;
//    }
//
//    public void setReports(List<Reports> reports) {
//        this.reports = reports;
//    }
//
//    public List<Favorites> getFavorites() {
//        return favorites;
//    }
//
//    public void setFavorites(List<Favorites> favorites) {
//        this.favorites = favorites;
//    }
//
//    public LocalDate getBirthDate() {
//        return birthDate;
//    }
//
//    public void setBirthDate(LocalDate birthDate) {
//        this.birthDate = birthDate;
//    }
//
//    public List<ParkDesign> getParkDesigns() {
//        return parkDesigns;
//    }
//
//    public void setParkDesigns(List<ParkDesign> parkDesigns) {
//        this.parkDesigns = parkDesigns;
//    }
//
//    public Set<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<Role> roles) {
//        this.roles = roles;
//    }
}
