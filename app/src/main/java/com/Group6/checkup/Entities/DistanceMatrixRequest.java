package com.Group6.checkup.Entities;

import java.util.List;
import java.util.Map;

public class DistanceMatrixRequest {

    //Attributes
    List<String> locations;
    Map<String,Boolean> options;

    //Constructors
    public DistanceMatrixRequest(List<String> locations, Map<String, Boolean> options) {
        this.locations = locations;
        this.options = options;
    }


    //Getters & Setters
    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public Map<String, Boolean> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Boolean> options) {
        this.options = options;
    }
}
