package com.example.jiowhere;

public class RecommendationInfo {
    /*
    Trying to see if I can use ArrayAdaptor instead of Custom Adaptor
     */

    private String location;
    private String time_period;
    private String name;
    private int image;

    public RecommendationInfo(String location, String time_period, String name, int image) {
        this.location = location;
        this.time_period = time_period;
        this.name = name;
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getTime_period() {
        return time_period;
    }

    public void setTime_period(String time_period) {
        this.time_period = time_period;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
