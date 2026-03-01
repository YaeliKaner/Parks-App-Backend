package com.example.parks.service;

import com.example.parks.Controller.CitiesController;
import com.example.parks.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
