package com.example.parks.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Cities {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "city")
    @JsonIgnore
    private List<Parks> parks;

    @OneToMany(mappedBy = "city")
    @JsonIgnore
    private List<Users> users;

    public Cities() {}

    public Cities(Long id, String name, List<Parks> parks, List<Users> users) {
        this.id = id;
        this.name = name;
        this.parks = parks;
        this.users = users;
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

    public List<Parks> getParks() {
        return parks;
    }

    public void setParks(List<Parks> parks) {
        this.parks = parks;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }
}
