package com.example.parks.service;

import com.example.parks.model.Cities;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.parks.model.Parks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CitiesRepository extends JpaRepository<Cities, Long> {

    Cities findByName(String name);

    Optional<Cities> findById(Long id);

    List<Cities> findAllByOrderByName();

    List<Cities> findByNameContainingIgnoreCase(String text);

    Cities findByNameIgnoreCase(String city);
}