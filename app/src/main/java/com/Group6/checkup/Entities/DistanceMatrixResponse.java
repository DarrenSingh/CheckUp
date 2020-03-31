package com.Group6.checkup.Entities;

import android.view.Display;

import java.util.List;

public class DistanceMatrixResponse {

    private boolean allToAll;
    private List<Float> distance;
    private List<Float> time;
    private List<Location> locations;

    public DistanceMatrixResponse(boolean allToAll, List<Float> distance, List<Float> time, List<Location> locations) {
        this.allToAll = allToAll;
        this.distance = distance;
        this.time = time;
        this.locations = locations;
    }

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

    public class Location{

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

    public class LatLong{
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

    //allToAll boolean
    //distance list<float>
    //locations list<map>
        //dragPoint
        //
        //
        //
    //time float
    //manyToOne boolean
    //info list<map>
        //statuscode string,int
        //copyright list<map>
            //imageAltText <string,string>
            //imageUrl <string,string>
            //text <string,string>
}
