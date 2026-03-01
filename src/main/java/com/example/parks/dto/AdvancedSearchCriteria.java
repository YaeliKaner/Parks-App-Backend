package com.example.parks.dto;

import java.util.List;

public class AdvancedSearchCriteria {

    private String city;
    private List<String> features;  // יכול להיות null – זה בסדר
    private String minShade;       // "רב" / "בינוני" / "מועט" או null

    // קונסטרקטור ריק – חובה ל-Jackson
    public AdvancedSearchCriteria() {
    }

    // קונסטרקטור מלא
    public AdvancedSearchCriteria(String city, List<String> features, String minShade) {
        this.city = city;
        this.features = features;
        this.minShade = minShade;
    }

    // Getters ו-Setters
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public String getMinShade() {
        return minShade;
    }

    public void setMinShade(String minShade) {
        this.minShade = minShade;
    }

    // נוח לדעת אם אין בכלל קריטריונים
    public boolean isEmpty() {
        boolean cityEmpty = (city == null || city.isBlank());
        boolean featuresEmpty = (features == null || features.isEmpty());
        boolean shadeEmpty = (minShade == null || minShade.isBlank());
        return cityEmpty && featuresEmpty && shadeEmpty;
    }

    @Override
    public String toString() {
        return "AdvancedSearchCriteria{" +
                "city='" + city + '\'' +
                ", features=" + features +
                ", minShade='" + minShade + '\'' +
                '}';
    }
}
