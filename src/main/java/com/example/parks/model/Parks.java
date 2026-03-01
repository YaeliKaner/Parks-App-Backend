////////////package com.example.parks.model;
////////////
////////////import com.example.parks.Payment;
////////////import com.example.parks.PersonalStatus;
////////////
////////////
////////////import com.fasterxml.jackson.annotation.JsonIgnore;
////////////import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
////////////import jakarta.persistence.*;
////////////
////////////import java.time.LocalDate;
////////////import java.util.List;
////////////
////////////@Entity
////////////public class Parks {
////////////
////////////
////////////    @Id
////////////    @GeneratedValue
////////////    private Long id;
////////////    private String name;
////////////    private String desc;
////////////    private String address;
//////////////    @Enumerated(EnumType.STRING)
//////////////    private Payment payment;
////////////    private LocalDate uploadDate;
////////////    private LocalDate updateDate;
////////////    private String picturePath;
////////////
////////////    @ManyToOne
////////////    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
////////////    private Users user;
////////////    @ManyToOne
////////////    private Cities city;
////////////
////////////    @OneToMany(mappedBy = "park")
////////////    @JsonIgnore
////////////    private List<ParkDesign> ParkDesigns;
////////////
////////////    @OneToMany(mappedBy = "park")
////////////    @JsonIgnore
////////////    private List<Reports> reports;
////////////
////////////    @OneToMany(mappedBy = "park")
////////////    @JsonIgnore
////////////    private List<Favorites> favorites;
////////////
////////////    public Parks() {
////////////    }
////////////
////////////
////////////    public Parks(Long id, String name, String desc, String address, LocalDate uploadDate, LocalDate updateDate, String picturePath, Users user, Cities city, List<ParkDesign> parkDesigns, List<Reports> reports, List<Favorites> favorites) {
////////////        this.id = id;
////////////        this.name = name;
////////////        this.desc = desc;
////////////        this.address = address;
////////////        this.uploadDate = uploadDate;
////////////        this.updateDate = updateDate;
////////////        this.picturePath = picturePath;
////////////        this.user = user;
////////////        this.city = city;
////////////        ParkDesigns = parkDesigns;
////////////        this.reports = reports;
////////////        this.favorites = favorites;
////////////    }
////////////
////////////    public Long getId() {
////////////        return id;
////////////    }
////////////
////////////    public void setId(Long id) {
////////////        this.id = id;
////////////    }
////////////
////////////    public String getName() {
////////////        return name;
////////////    }
////////////
////////////    public void setName(String name) {
////////////        this.name = name;
////////////    }
////////////
////////////    public String getDesc() {
////////////        return desc;
////////////    }
////////////
////////////    public void setDesc(String desc) {
////////////        this.desc = desc;
////////////    }
////////////
////////////    public String getAddress() {
////////////        return address;
////////////    }
////////////
////////////    public void setAddress(String address) {
////////////        this.address = address;
////////////    }
////////////
//////////////    public Payment getPayment() {
//////////////        return payment;
//////////////    }
//////////////
//////////////    public void setPayment(Payment payment) {
//////////////        this.payment = payment;
//////////////    }
////////////
////////////    public LocalDate getUploadDate() {
////////////        return uploadDate;
////////////    }
////////////
////////////    public void setUploadDate(LocalDate uploadDate) {
////////////        this.uploadDate = uploadDate;
////////////    }
////////////
////////////    public LocalDate getUpdateDate() {
////////////        return updateDate;
////////////    }
////////////
////////////    public void setUpdateDate(LocalDate updateDate) {
////////////        this.updateDate = updateDate;
////////////    }
////////////
////////////    public String getPicturePath() {
////////////        return picturePath;
////////////    }
////////////
////////////    public void setPicturePath(String picturePath) {
////////////        this.picturePath = picturePath;
////////////    }
////////////
////////////    public Users getUser() {
////////////        return user;
////////////    }
////////////
////////////    public void setUser(Users user) {
////////////        this.user = user;
////////////    }
////////////
////////////    public Cities getCity() {
////////////        return city;
////////////    }
////////////
////////////    public void setCity(Cities city) {
////////////        this.city = city;
////////////    }
////////////
////////////    public List<ParkDesign> getParkDesigns() {
////////////        return ParkDesigns;
////////////    }
////////////
////////////    public void setParkDesigns(List<ParkDesign> parkDesigns) {
////////////        ParkDesigns = parkDesigns;
////////////    }
////////////
////////////    public List<Reports> getReports() {
////////////        return reports;
////////////    }
////////////
////////////    public void setReports(List<Reports> reports) {
////////////        this.reports = reports;
////////////    }
////////////
////////////    public List<Favorites> getFavorites() {
////////////        return favorites;
////////////    }
////////////
////////////    public void setFavorites(List<Favorites> favorites) {
////////////        this.favorites = favorites;
////////////    }
////////////}
////////////
////////////
//////////package com.example.parks.model;
//////////
//////////import com.fasterxml.jackson.annotation.JsonIgnore;
//////////import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//////////import jakarta.persistence.*;
//////////
//////////import java.time.LocalDate;
//////////import java.util.List;
//////////
//////////@Entity
//////////public class Parks {
//////////
//////////    @Id
//////////    @GeneratedValue
//////////    private Long id;
//////////
//////////    private String name;
//////////    private String desc;
//////////    private String address;
////////////    @Enumerated(EnumType.STRING)
////////////    private Payment payment;
//////////
//////////    private LocalDate uploadDate;
//////////    private LocalDate updateDate;
//////////    private String picturePath;
//////////
//////////    @ManyToOne
//////////    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//////////    private Users user;
//////////
//////////    @ManyToOne
//////////    private Cities city;
//////////
//////////    @OneToMany(mappedBy = "park")
//////////    @JsonIgnore
//////////    private List<ParkDesign> parkDesigns;   // 👈 שימי לב לאות קטנה
//////////
//////////    @OneToMany(mappedBy = "park")
//////////    @JsonIgnore
//////////    private List<Reports> reports;
//////////
//////////    @OneToMany(mappedBy = "park")
//////////    @JsonIgnore
//////////    private List<Favorites> favorites;
//////////
//////////    public Parks() {
//////////    }
//////////
//////////    public Parks(Long id,
//////////                 String name,
//////////                 String desc,
//////////                 String address,
//////////                 LocalDate uploadDate,
//////////                 LocalDate updateDate,
//////////                 String picturePath,
//////////                 Users user,
//////////                 Cities city,
//////////                 List<ParkDesign> parkDesigns,
//////////                 List<Reports> reports,
//////////                 List<Favorites> favorites) {
//////////        this.id = id;
//////////        this.name = name;
//////////        this.desc = desc;
//////////        this.address = address;
//////////        this.uploadDate = uploadDate;
//////////        this.updateDate = updateDate;
//////////        this.picturePath = picturePath;
//////////        this.user = user;
//////////        this.city = city;
//////////        this.parkDesigns = parkDesigns;
//////////        this.reports = reports;
//////////        this.favorites = favorites;
//////////    }
//////////
//////////    public Long getId() {
//////////        return id;
//////////    }
//////////
//////////    public void setId(Long id) {
//////////        this.id = id;
//////////    }
//////////
//////////    public String getName() {
//////////        return name;
//////////    }
//////////
//////////    public void setName(String name) {
//////////        this.name = name;
//////////    }
//////////
//////////    public String getDesc() {
//////////        return desc;
//////////    }
//////////
//////////    public void setDesc(String desc) {
//////////        this.desc = desc;
//////////    }
//////////
//////////    public String getAddress() {
//////////        return address;
//////////    }
//////////
//////////    public void setAddress(String address) {
//////////        this.address = address;
//////////    }
//////////
////////////    public Payment getPayment() {
////////////        return payment;
////////////    }
////////////
////////////    public void setPayment(Payment payment) {
////////////        this.payment = payment;
////////////    }
//////////
//////////    public LocalDate getUploadDate() {
//////////        return uploadDate;
//////////    }
//////////
//////////    public void setUploadDate(LocalDate uploadDate) {
//////////        this.uploadDate = uploadDate;
//////////    }
//////////
//////////    public LocalDate getUpdateDate() {
//////////        return updateDate;
//////////    }
//////////
//////////    public void setUpdateDate(LocalDate updateDate) {
//////////        this.updateDate = updateDate;
//////////    }
//////////
//////////    public String getPicturePath() {
//////////        return picturePath;
//////////    }
//////////
//////////    public void setPicturePath(String picturePath) {
//////////        this.picturePath = picturePath;
//////////    }
//////////
//////////    public Users getUser() {
//////////        return user;
//////////    }
//////////
//////////    public void setUser(Users user) {
//////////        this.user = user;
//////////    }
//////////
//////////    public Cities getCity() {
//////////        return city;
//////////    }
//////////
//////////    public void setCity(Cities city) {
//////////        this.city = city;
//////////    }
//////////
//////////    public List<ParkDesign> getParkDesigns() {
//////////        return parkDesigns;
//////////    }
//////////
//////////    public void setParkDesigns(List<ParkDesign> parkDesigns) {
//////////        this.parkDesigns = parkDesigns;
//////////    }
//////////
//////////    public List<Reports> getReports() {
//////////        return reports;
//////////    }
//////////
//////////    public void setReports(List<Reports> reports) {
//////////        this.reports = reports;
//////////    }
//////////
//////////    public List<Favorites> getFavorites() {
//////////        return favorites;
//////////    }
//////////
//////////    public void setFavorites(List<Favorites> favorites) {
//////////        this.favorites = favorites;
//////////    }
//////////}
////////package com.example.parks.model;
////////
////////import com.fasterxml.jackson.annotation.JsonIgnore;
////////import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
////////import jakarta.persistence.*;
////////
////////import java.time.LocalDate;
////////import java.util.List;
////////
////////@Entity
////////public class Parks {
////////
////////    @Id
////////    @GeneratedValue
////////    private Long id;
////////    private String name;
////////    private String desc;
////////    private String address;
////////    private LocalDate uploadDate;
////////    private LocalDate updateDate;
////////    private String picturePath;
////////
////////    @ManyToOne
////////    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
////////    private Users user;
////////
////////    @ManyToOne
////////    private Cities city;
////////
////////    // *** שימי לב לשם: parkDesigns (p קטן!) ***
////////    @OneToMany(mappedBy = "park")
////////    @JsonIgnore
////////    private List<ParkDesign> parkDesigns;
////////
////////    @OneToMany(mappedBy = "park")
////////    @JsonIgnore
////////    private List<Reports> reports;
////////
////////    @OneToMany(mappedBy = "park")
////////    @JsonIgnore
////////    private List<Favorites> favorites;
////////
////////    public Parks() {
////////    }
////////
////////    public Parks(Long id, String name, String desc, String address,
////////                 LocalDate uploadDate, LocalDate updateDate,
////////                 String picturePath, Users user, Cities city,
////////                 List<ParkDesign> parkDesigns,
////////                 List<Reports> reports,
////////                 List<Favorites> favorites) {
////////        this.id = id;
////////        this.name = name;
////////        this.desc = desc;
////////        this.address = address;
////////        this.uploadDate = uploadDate;
////////        this.updateDate = updateDate;
////////        this.picturePath = picturePath;
////////        this.user = user;
////////        this.city = city;
////////        this.parkDesigns = parkDesigns;
////////        this.reports = reports;
////////        this.favorites = favorites;
////////    }
////////
////////    // getters / setters
////////
////////    public Long getId() { return id; }
////////    public void setId(Long id) { this.id = id; }
////////
////////    public String getName() { return name; }
////////    public void setName(String name) { this.name = name; }
////////
////////    public String getDesc() { return desc; }
////////    public void setDesc(String desc) { this.desc = desc; }
////////
////////    public String getAddress() { return address; }
////////    public void setAddress(String address) { this.address = address; }
////////
////////    public LocalDate getUploadDate() { return uploadDate; }
////////    public void setUploadDate(LocalDate uploadDate) { this.uploadDate = uploadDate; }
////////
////////    public LocalDate getUpdateDate() { return updateDate; }
////////    public void setUpdateDate(LocalDate updateDate) { this.updateDate = updateDate; }
////////
////////    public String getPicturePath() { return picturePath; }
////////    public void setPicturePath(String picturePath) { this.picturePath = picturePath; }
////////
////////    public Users getUser() { return user; }
////////    public void setUser(Users user) { this.user = user; }
////////
////////    public Cities getCity() { return city; }
////////    public void setCity(Cities city) { this.city = city; }
////////
////////    public List<ParkDesign> getParkDesigns() { return parkDesigns; }
////////    public void setParkDesigns(List<ParkDesign> parkDesigns) { this.parkDesigns = parkDesigns; }
////////
////////    public List<Reports> getReports() { return reports; }
////////    public void setReports(List<Reports> reports) { this.reports = reports; }
////////
////////    public List<Favorites> getFavorites() { return favorites; }
////////    public void setFavorites(List<Favorites> favorites) { this.favorites = favorites; }
////////}
//////package com.example.parks.model;
//////
//////import com.fasterxml.jackson.annotation.JsonIgnore;
//////import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//////import jakarta.persistence.*;
//////
//////import java.time.LocalDate;
//////import java.util.List;
//////
//////@Entity
//////public class Parks {
//////
//////    @Id
//////    @GeneratedValue
//////    private Long id;
//////
//////    private String name;
//////    private String desc;
//////    private String address;
//////    private LocalDate uploadDate;
//////    private LocalDate updateDate;
//////    private String picturePath;
//////
//////    @ManyToOne
//////    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//////    private Users user;
//////
//////    @ManyToOne
//////    private Cities city;
//////
//////    // נטען ב-EAGER כדי שה-AI יראה את ה-ParkDesigns
//////    @OneToMany(mappedBy = "park", fetch = FetchType.EAGER)
//////    @JsonIgnore
//////    private List<ParkDesign> parkDesigns;
//////
//////    @OneToMany(mappedBy = "park")
//////    @JsonIgnore
//////    private List<Reports> reports;
//////
//////    @OneToMany(mappedBy = "park")
//////    @JsonIgnore
//////    private List<Favorites> favorites;
//////
//////    public Parks() {
//////    }
//////
//////    public Parks(Long id, String name, String desc, String address,
//////                 LocalDate uploadDate, LocalDate updateDate,
//////                 String picturePath, Users user, Cities city,
//////                 List<ParkDesign> parkDesigns,
//////                 List<Reports> reports,
//////                 List<Favorites> favorites) {
//////        this.id = id;
//////        this.name = name;
//////        this.desc = desc;
//////        this.address = address;
//////        this.uploadDate = uploadDate;
//////        this.updateDate = updateDate;
//////        this.picturePath = picturePath;
//////        this.user = user;
//////        this.city = city;
//////        this.parkDesigns = parkDesigns;
//////        this.reports = reports;
//////        this.favorites = favorites;
//////    }
//////
//////    public Long getId() { return id; }
//////    public void setId(Long id) { this.id = id; }
//////
//////    public String getName() { return name; }
//////    public void setName(String name) { this.name = name; }
//////
//////    public String getDesc() { return desc; }
//////    public void setDesc(String desc) { this.desc = desc; }
//////
//////    public String getAddress() { return address; }
//////    public void setAddress(String address) { this.address = address; }
//////
//////    public LocalDate getUploadDate() { return uploadDate; }
//////    public void setUploadDate(LocalDate uploadDate) { this.uploadDate = uploadDate; }
//////
//////    public LocalDate getUpdateDate() { return updateDate; }
//////    public void setUpdateDate(LocalDate updateDate) { this.updateDate = updateDate; }
//////
//////    public String getPicturePath() { return picturePath; }
//////    public void setPicturePath(String picturePath) { this.picturePath = picturePath; }
//////
//////    public Users getUser() { return user; }
//////    public void setUser(Users user) { this.user = user; }
//////
//////    public Cities getCity() { return city; }
//////    public void setCity(Cities city) { this.city = city; }
//////
//////    public List<ParkDesign> getParkDesigns() { return parkDesigns; }
//////    public void setParkDesigns(List<ParkDesign> parkDesigns) { this.parkDesigns = parkDesigns; }
//////
//////    public List<Reports> getReports() { return reports; }
//////    public void setReports(List<Reports> reports) { this.reports = reports; }
//////
//////    public List<Favorites> getFavorites() { return favorites; }
//////    public void setFavorites(List<Favorites> favorites) { this.favorites = favorites; }
//////}
////////package com.example.parks.model;
////////
////////import com.example.parks.Payment;
////////import com.example.parks.PersonalStatus;
////////
////////
////////import com.fasterxml.jackson.annotation.JsonIgnore;
////////import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
////////import jakarta.persistence.*;
////////
////////import java.time.LocalDate;
////////import java.util.List;
////////
////////@Entity
////////public class Parks {
////////
////////
////////    @Id
////////    @GeneratedValue
////////    private Long id;
////////    private String name;
////////    private String desc;
////////    private String address;
//////////    @Enumerated(EnumType.STRING)
//////////    private Payment payment;
////////    private LocalDate uploadDate;
////////    private LocalDate updateDate;
////////    private String picturePath;
////////
////////    @ManyToOne
////////    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
////////    private Users user;
////////    @ManyToOne
////////    private Cities city;
////////
////////    @OneToMany(mappedBy = "park")
////////    @JsonIgnore
////////    private List<ParkDesign> ParkDesigns;
////////
////////    @OneToMany(mappedBy = "park")
////////    @JsonIgnore
////////    private List<Reports> reports;
////////
////////    @OneToMany(mappedBy = "park")
////////    @JsonIgnore
////////    private List<Favorites> favorites;
////////
////////    public Parks() {
////////    }
////////
////////
////////    public Parks(Long id, String name, String desc, String address, LocalDate uploadDate, LocalDate updateDate, String picturePath, Users user, Cities city, List<ParkDesign> parkDesigns, List<Reports> reports, List<Favorites> favorites) {
////////        this.id = id;
////////        this.name = name;
////////        this.desc = desc;
////////        this.address = address;
////////        this.uploadDate = uploadDate;
////////        this.updateDate = updateDate;
////////        this.picturePath = picturePath;
////////        this.user = user;
////////        this.city = city;
////////        ParkDesigns = parkDesigns;
////////        this.reports = reports;
////////        this.favorites = favorites;
////////    }
////////
////////    public Long getId() {
////////        return id;
////////    }
////////
////////    public void setId(Long id) {
////////        this.id = id;
////////    }
////////
////////    public String getName() {
////////        return name;
////////    }
////////
////////    public void setName(String name) {
////////        this.name = name;
////////    }
////////
////////    public String getDesc() {
////////        return desc;
////////    }
////////
////////    public void setDesc(String desc) {
////////        this.desc = desc;
////////    }
////////
////////    public String getAddress() {
////////        return address;
////////    }
////////
////////    public void setAddress(String address) {
////////        this.address = address;
////////    }
////////
//////////    public Payment getPayment() {
//////////        return payment;
//////////    }
//////////
//////////    public void setPayment(Payment payment) {
//////////        this.payment = payment;
//////////    }
////////
////////    public LocalDate getUploadDate() {
////////        return uploadDate;
////////    }
////////
////////    public void setUploadDate(LocalDate uploadDate) {
////////        this.uploadDate = uploadDate;
////////    }
////////
////////    public LocalDate getUpdateDate() {
////////        return updateDate;
////////    }
////////
////////    public void setUpdateDate(LocalDate updateDate) {
////////        this.updateDate = updateDate;
////////    }
////////
////////    public String getPicturePath() {
////////        return picturePath;
////////    }
////////
////////    public void setPicturePath(String picturePath) {
////////        this.picturePath = picturePath;
////////    }
////////
////////    public Users getUser() {
////////        return user;
////////    }
////////
////////    public void setUser(Users user) {
////////        this.user = user;
////////    }
////////
////////    public Cities getCity() {
////////        return city;
////////    }
////////
////////    public void setCity(Cities city) {
////////        this.city = city;
////////    }
////////
////////    public List<ParkDesign> getParkDesigns() {
////////        return ParkDesigns;
////////    }
////////
////////    public void setParkDesigns(List<ParkDesign> parkDesigns) {
////////        ParkDesigns = parkDesigns;
////////    }
////////
////////    public List<Reports> getReports() {
////////        return reports;
////////    }
////////
////////    public void setReports(List<Reports> reports) {
////////        this.reports = reports;
////////    }
////////
////////    public List<Favorites> getFavorites() {
////////        return favorites;
////////    }
////////
////////    public void setFavorites(List<Favorites> favorites) {
////////        this.favorites = favorites;
////////    }
////////}
////////
////////
//////package com.example.parks.model;
//////
//////import com.fasterxml.jackson.annotation.JsonIgnore;
//////import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//////import jakarta.persistence.*;
//////
//////import java.time.LocalDate;
//////import java.util.List;
//////
//////@Entity
//////public class Parks {
//////
//////    @Id
//////    @GeneratedValue
//////    private Long id;
//////
//////    private String name;
//////    private String desc;
//////    private String address;
////////    @Enumerated(EnumType.STRING)
////////    private Payment payment;
//////
//////    private LocalDate uploadDate;
//////    private LocalDate updateDate;
//////    private String picturePath;
//////
//////    @ManyToOne
//////    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//////    private Users user;
//////
//////    @ManyToOne
//////    private Cities city;
//////
//////    @OneToMany(mappedBy = "park")
//////    @JsonIgnore
//////    private List<ParkDesign> parkDesigns;   // 👈 שימי לב לאות קטנה
//////
//////    @OneToMany(mappedBy = "park")
//////    @JsonIgnore
//////    private List<Reports> reports;
//////
//////    @OneToMany(mappedBy = "park")
//////    @JsonIgnore
//////    private List<Favorites> favorites;
//////
//////    public Parks() {
//////    }
//////
//////    public Parks(Long id,
//////                 String name,
//////                 String desc,
//////                 String address,
//////                 LocalDate uploadDate,
//////                 LocalDate updateDate,
//////                 String picturePath,
//////                 Users user,
//////                 Cities city,
//////                 List<ParkDesign> parkDesigns,
//////                 List<Reports> reports,
//////                 List<Favorites> favorites) {
//////        this.id = id;
//////        this.name = name;
//////        this.desc = desc;
//////        this.address = address;
//////        this.uploadDate = uploadDate;
//////        this.updateDate = updateDate;
//////        this.picturePath = picturePath;
//////        this.user = user;
//////        this.city = city;
//////        this.parkDesigns = parkDesigns;
//////        this.reports = reports;
//////        this.favorites = favorites;
//////    }
//////
//////    public Long getId() {
//////        return id;
//////    }
//////
//////    public void setId(Long id) {
//////        this.id = id;
//////    }
//////
//////    public String getName() {
//////        return name;
//////    }
//////
//////    public void setName(String name) {
//////        this.name = name;
//////    }
//////
//////    public String getDesc() {
//////        return desc;
//////    }
//////
//////    public void setDesc(String desc) {
//////        this.desc = desc;
//////    }
//////
//////    public String getAddress() {
//////        return address;
//////    }
//////
//////    public void setAddress(String address) {
//////        this.address = address;
//////    }
//////
////////    public Payment getPayment() {
////////        return payment;
////////    }
////////
////////    public void setPayment(Payment payment) {
////////        this.payment = payment;
////////    }
//////
//////    public LocalDate getUploadDate() {
//////        return uploadDate;
//////    }
//////
//////    public void setUploadDate(LocalDate uploadDate) {
//////        this.uploadDate = uploadDate;
//////    }
//////
//////    public LocalDate getUpdateDate() {
//////        return updateDate;
//////    }
//////
//////    public void setUpdateDate(LocalDate updateDate) {
//////        this.updateDate = updateDate;
//////    }
//////
//////    public String getPicturePath() {
//////        return picturePath;
//////    }
//////
//////    public void setPicturePath(String picturePath) {
//////        this.picturePath = picturePath;
//////    }
//////
//////    public Users getUser() {
//////        return user;
//////    }
//////
//////    public void setUser(Users user) {
//////        this.user = user;
//////    }
//////
//////    public Cities getCity() {
//////        return city;
//////    }
//////
//////    public void setCity(Cities city) {
//////        this.city = city;
//////    }
//////
//////    public List<ParkDesign> getParkDesigns() {
//////        return parkDesigns;
//////    }
//////
//////    public void setParkDesigns(List<ParkDesign> parkDesigns) {
//////        this.parkDesigns = parkDesigns;
//////    }
//////
//////    public List<Reports> getReports() {
//////        return reports;
//////    }
//////
//////    public void setReports(List<Reports> reports) {
//////        this.reports = reports;
//////    }
//////
//////    public List<Favorites> getFavorites() {
//////        return favorites;
//////    }
//////
//////    public void setFavorites(List<Favorites> favorites) {
//////        this.favorites = favorites;
//////    }
//////}
////package com.example.parks.model;
////
////import com.fasterxml.jackson.annotation.JsonIgnore;
////import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
////import jakarta.persistence.*;
////import jakarta.validation.constraints.NotBlank;
////
////
////import java.time.LocalDate;
////import java.util.List;
////
////@Entity
////public class Parks {
////
////    @Id
////    @GeneratedValue
////    private Long id;
////    @NotBlank(message = "You must enter the name of the park.")
////    private String name;
////
////    @NotBlank(message = "You must enter the description of the park..")
////    private String desc;
////
////    @NotBlank(message = "You must enter the address of the park.")
////    private String address;
////    private LocalDate uploadDate;
////    private LocalDate updateDate;
////    private String picturePath;
////
////    @ManyToOne
////    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
////    private Users user;
////
////    @ManyToOne
////    private Cities city;
////
////    // *** שימי לב לשם: parkDesigns (p קטן!) ***
////    @OneToMany(mappedBy = "park")
////    @JsonIgnore
////    private List<ParkDesign> parkDesigns;
////
////    @OneToMany(mappedBy = "park")
////    @JsonIgnore
////    private List<Reports> reports;
////
////    @OneToMany(mappedBy = "park")
////    @JsonIgnore
////    private List<Favorites> favorites;
////
////    public Parks() {
////    }
////
////    public Parks(Long id, String name, String desc, String address,
////                 LocalDate uploadDate, LocalDate updateDate,
////                 String picturePath, Users user, Cities city,
////                 List<ParkDesign> parkDesigns,
////                 List<Reports> reports,
////                 List<Favorites> favorites) {
////        this.id = id;
////        this.name = name;
////        this.desc = desc;
////        this.address = address;
////        this.uploadDate = uploadDate;
////        this.updateDate = updateDate;
////        this.picturePath = picturePath;
////        this.user = user;
////        this.city = city;
////        this.parkDesigns = parkDesigns;
////        this.reports = reports;
////        this.favorites = favorites;
////    }
////
////    // getters / setters
////
////    public Long getId() { return id; }
////    public void setId(Long id) { this.id = id; }
////
////    public String getName() { return name; }
////    public void setName(String name) { this.name = name; }
////
////    public String getDesc() { return desc; }
////    public void setDesc(String desc) { this.desc = desc; }
////
////    public String getAddress() { return address; }
////    public void setAddress(String address) { this.address = address; }
////
////    public LocalDate getUploadDate() { return uploadDate; }
////    public void setUploadDate(LocalDate uploadDate) { this.uploadDate = uploadDate; }
////
////    public LocalDate getUpdateDate() { return updateDate; }
////    public void setUpdateDate(LocalDate updateDate) { this.updateDate = updateDate; }
////
////    public String getPicturePath() { return picturePath; }
////    public void setPicturePath(String picturePath) { this.picturePath = picturePath; }
////
////    public Users getUser() { return user; }
////    public void setUser(Users user) { this.user = user; }
////
////    public Cities getCity() { return city; }
////    public void setCity(Cities city) { this.city = city; }
////
////    public List<ParkDesign> getParkDesigns() { return parkDesigns; }
////    public void setParkDesigns(List<ParkDesign> parkDesigns) { this.parkDesigns = parkDesigns; }
////
////    public List<Reports> getReports() { return reports; }
////    public void setReports(List<Reports> reports) { this.reports = reports; }
////
////    public List<Favorites> getFavorites() { return favorites; }
////    public void setFavorites(List<Favorites> favorites) { this.favorites = favorites; }
////}
////מכאן אני מתחילה את המזג אוויר ואת הgoogle maps בסיעתא דישמיא
//package com.example.parks.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Entity
//public class Parks {
//
//    @Id
//    @GeneratedValue
//    private Long id;
//
//    @NotBlank(message = "You must enter the name of the park.")
//    private String name;
//
//    @NotBlank(message = "You must enter the description of the park..")
//    private String desc;
//
//    @NotBlank(message = "You must enter the address of the park.")
//    private String address;
//
//    // 🆕 קואורדינטות למפה
//    private Double latitude;   // קו רוחב
//    private Double longitude;  // קו אורך
//
//    private LocalDate uploadDate;
//    private LocalDate updateDate;
//    private String picturePath;
//
//    @ManyToOne
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    private Users user;
//
//    @ManyToOne
//    private Cities city;
//
//    @OneToMany(mappedBy = "park")
//    @JsonIgnore
//    private List<ParkDesign> parkDesigns;
//
//    @OneToMany(mappedBy = "park")
//    @JsonIgnore
//    private List<Reports> reports;
//
//    @OneToMany(mappedBy = "park")
//    @JsonIgnore
//    private List<Favorites> favorites;
//
//    public Parks() {
//    }
//
//    public Parks(Long id,
//                 String name,
//                 String desc,
//                 String address,
//                 LocalDate uploadDate,
//                 LocalDate updateDate,
//                 String picturePath,
//                 Users user,
//                 Cities city,
//                 List<ParkDesign> parkDesigns,
//                 List<Reports> reports,
//                 List<Favorites> favorites,
//                 Double latitude,       // 🆕
//                 Double longitude) {    // 🆕
//        this.id = id;
//        this.name = name;
//        this.desc = desc;
//        this.address = address;
//        this.uploadDate = uploadDate;
//        this.updateDate = updateDate;
//        this.picturePath = picturePath;
//        this.user = user;
//        this.city = city;
//        this.parkDesigns = parkDesigns;
//        this.reports = reports;
//        this.favorites = favorites;
//        this.latitude = latitude;
//        this.longitude = longitude;
//    }
//
//    // ---------- getters / setters ----------
//
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
//
//    public String getDesc() { return desc; }
//    public void setDesc(String desc) { this.desc = desc; }
//
//    public String getAddress() { return address; }
//    public void setAddress(String address) { this.address = address; }
//
//    public LocalDate getUploadDate() { return uploadDate; }
//    public void setUploadDate(LocalDate uploadDate) { this.uploadDate = uploadDate; }
//
//    public LocalDate getUpdateDate() { return updateDate; }
//    public void setUpdateDate(LocalDate updateDate) { this.updateDate = updateDate; }
//
//    public String getPicturePath() { return picturePath; }
//    public void setPicturePath(String picturePath) { this.picturePath = picturePath; }
//
//    public Users getUser() { return user; }
//    public void setUser(Users user) { this.user = user; }
//
//    public Cities getCity() { return city; }
//    public void setCity(Cities city) { this.city = city; }
//
//    public List<ParkDesign> getParkDesigns() { return parkDesigns; }
//    public void setParkDesigns(List<ParkDesign> parkDesigns) { this.parkDesigns = parkDesigns; }
//
//    public List<Reports> getReports() { return reports; }
//    public void setReports(List<Reports> reports) { this.reports = reports; }
//
//    public List<Favorites> getFavorites() { return favorites; }
//    public void setFavorites(List<Favorites> favorites) { this.favorites = favorites; }
//
//    // 🆕 getters/setters לקואורדינטות
//    public Double getLatitude() { return latitude; }
//    public void setLatitude(Double latitude) { this.latitude = latitude; }
//
//    public Double getLongitude() { return longitude; }
//    public void setLongitude(Double longitude) { this.longitude = longitude; }
//}
package com.example.parks.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Parks {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "You must enter the name of the park.")
    private String name;

    @NotBlank(message = "You must enter the description of the park..")
    private String desc;

    @NotBlank(message = "You must enter the address of the park.")
    private String address;

    // קואורדינטות – חובה למזג האוויר ולמפה
    private Double latitude;
    private Double longitude;

    private LocalDate uploadDate;
    private LocalDate updateDate;
    private String picturePath;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Users user;

    @ManyToOne
    private Cities city;

    @OneToMany(mappedBy = "park")
    @JsonIgnore
    private List<ParkDesign> parkDesigns;

    @OneToMany(mappedBy = "park")
    @JsonIgnore
    private List<Reports> reports;

    @OneToMany(mappedBy = "park")
    @JsonIgnore
    private List<Favorites> favorites;

    public Parks() {}

    // getters & setters (הוספתי גם לקואורדינטות)

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDesc() { return desc; }
    public void setDesc(String desc) { this.desc = desc; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public LocalDate getUploadDate() { return uploadDate; }
    public void setUploadDate(LocalDate uploadDate) { this.uploadDate = uploadDate; }

    public LocalDate getUpdateDate() { return updateDate; }
    public void setUpdateDate(LocalDate updateDate) { this.updateDate = updateDate; }

    public String getPicturePath() { return picturePath; }
    public void setPicturePath(String picturePath) { this.picturePath = picturePath; }

    public Users getUser() { return user; }
    public void setUser(Users user) { this.user = user; }

    public Cities getCity() { return city; }
    public void setCity(Cities city) { this.city = city; }

    public List<ParkDesign> getParkDesigns() { return parkDesigns; }
    public void setParkDesigns(List<ParkDesign> parkDesigns) { this.parkDesigns = parkDesigns; }

    public List<Reports> getReports() { return reports; }
    public void setReports(List<Reports> reports) { this.reports = reports; }

    public List<Favorites> getFavorites() { return favorites; }
    public void setFavorites(List<Favorites> favorites) { this.favorites = favorites; }
}