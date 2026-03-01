package com.example.parks.service;

import com.example.parks.dto.WeatherDTO;
import com.example.parks.model.Parks;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

@Service
public class WeatherService {

    private final ParksRepository parksRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    private static final String OPEN_METEO_URL =
            "https://api.open-meteo.com/v1/forecast" +
                    "?latitude=%s&longitude=%s" +
                    "&current=temperature_2m,apparent_temperature,relative_humidity_2m,weather_code,wind_speed_10m" +
                    "&timezone=auto";

    public WeatherService(ParksRepository parksRepository) {
        this.parksRepository = parksRepository;
    }

    public WeatherDTO getWeatherForPark(Long parkId) {

        Parks park = parksRepository.findById(parkId).orElse(null);

        if (park == null) {
            System.err.println("❌ לא נמצא פארק עם ID: " + parkId);
            return null;
        }

        if (park.getLatitude() == null || park.getLongitude() == null) {
            System.err.println("❌ לפארק " + parkId + " אין קואורדינטות");
            return null;
        }

        String url = String.format(Locale.US, OPEN_METEO_URL,
                park.getLatitude(),
                park.getLongitude());

        System.out.println("🌍 Weather URL: " + url);

        try {
            String json = restTemplate.getForObject(url, String.class);
            System.out.println("📥 Weather JSON: " + json);

            JsonNode root = mapper.readTree(json);
            JsonNode current = root.path("current");

            if (current.isMissingNode()) {
                System.err.println("❌ ה-API לא החזיר current");
                return null;
            }

            WeatherDTO dto = new WeatherDTO();
            dto.setTemperature(current.path("temperature_2m").asDouble());
            dto.setFeelsLike(current.path("apparent_temperature").asDouble());
            dto.setHumidity(current.path("relative_humidity_2m").asInt());
            dto.setDescription(translateWeatherCode(current.path("weather_code").asInt()));

            return dto;

        } catch (Exception e) {
            System.err.println("❌ שגיאה בקבלת מזג אוויר לפארק " + parkId + ": " + e.getMessage());
            return null;
        }
    }

    private String translateWeatherCode(int code) {
        return switch (code) {
            case 0 -> "שמיים בהירים";
            case 1, 2 -> "מעונן חלקית";
            case 3 -> "מעונן";
            case 45, 48 -> "ערפל";
            case 51, 53, 55 -> "טפטוף";
            case 61, 63, 65 -> "גשם";
            case 71, 73, 75 -> "שלג";
            case 95, 96, 99 -> "סופת רעמים";
            default -> "מזג אוויר נעים";
        };
    }
}
