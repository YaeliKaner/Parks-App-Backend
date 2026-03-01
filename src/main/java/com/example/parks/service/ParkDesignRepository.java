package com.example.parks.service;

import com.example.parks.model.ParkDesign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkDesignRepository extends JpaRepository<ParkDesign, Long> {
    ParkDesign findTopByParkIdAndFeatureIdOrderByUpdateDateDesc(Long parkId, Long featureId);

    List<ParkDesign> findByParkIdOrderByUpdateDateDescIdDesc(Long parkId);
}
