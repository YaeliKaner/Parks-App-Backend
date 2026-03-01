package com.example.parks.Controller;

import com.example.parks.dto.WeatherDTO;
import com.example.parks.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/park/{parkId}")
    public ResponseEntity<WeatherDTO> getWeatherForPark(@PathVariable Long parkId) {
        try {
            WeatherDTO dto = weatherService.getWeatherForPark(parkId);

            // ❗ אם אין נתוני מזג אוויר → מחזירים 404
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(dto);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
