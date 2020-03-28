package com.Group6.checkup.Entities;

import java.util.List;
import java.util.Map;

public class DistanceMatrixResponse {

    private boolean allToAll;
    private List<Float> distance;
    private List<Float> time;
//    private List<String> locations;
//    private List<Map<String,Float>> displayLatLng;
//    private Map<String,Float> lng;
//    private Map<String,Float> lat;


    public DistanceMatrixResponse(boolean allToAll, List<Float> distance, List<Float> time) {
        this.allToAll = allToAll;
        this.distance = distance;
        this.time = time;
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
