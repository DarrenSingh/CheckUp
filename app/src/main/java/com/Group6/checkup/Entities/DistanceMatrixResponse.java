package com.Group6.checkup.Entities;

import java.util.List;

public class DistanceMatrixResponse {

    //Attributes
    private boolean allToAll;
    private List<Float> distance;
    private List<Float> time;
    private List<Location> locations;

    //Constructors
    public DistanceMatrixResponse(boolean allToAll, List<Float> distance, List<Float> time, List<Location> locations) {
        this.allToAll = allToAll;
        this.distance = distance;
        this.time = time;
        this.locations = locations;
    }

    //Getters & Setters
    public boolean isAllToAll() {
        return allToAll;
    }

    public void setAllToAll(boolean allToAll) {
        this.allToAll = allToAll;
    }

    public List<Float> getDistance() {
        return distance;
    }

    public void setDistance(List<Float> distance) {
        this.distance = distance;
    }

    public List<Float> getTime() {
        return time;
    }

    public void setTime(List<Float> time) {
        this.time = time;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    //Helper Classes

    //Location class to parse distance matrix response json location objects
    public class Location {

        private LatLong latLng;

        public Location(LatLong latLng) {
            this.latLng = latLng;
        }

        public LatLong getLatLng() {
            return latLng;
        }

        public void setLatLng(LatLong latLng) {
            this.latLng = latLng;
        }
    }

    //LatLong class to parse distance matrix response json latLong objects
    public class LatLong {
        float lng;
        float lat;

        public LatLong(float lng, float lat) {
            this.lng = lng;
            this.lat = lat;
        }

        public float getLng() {
            return lng;
        }

        public void setLng(float lng) {
            this.lng = lng;
        }

        public float getLat() {
            return lat;
        }

        public void setLat(float lat) {
            this.lat = lat;
        }
    }

}
