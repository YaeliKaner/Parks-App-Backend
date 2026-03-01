//package com.example.parks.service;
//
//import com.example.parks.model.Parks;
//import com.example.parks.model.Reports;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface ReportsRepository extends JpaRepository<Reports, Long> {
//
//    List<Reports> findReportsByParkId(Long id);
//}
package com.example.parks.service;

import com.example.parks.model.Parks;
import com.example.parks.model.Reports;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportsRepository extends JpaRepository<Reports, Long> {

    List<Reports> findReportsByParkId(Long id);

    List<Reports> findAllByOrderByReportingDateDescIdDesc();

    List<Reports> findAllByParkIdOrderByReportingDateDescIdDesc(Long id);
}
