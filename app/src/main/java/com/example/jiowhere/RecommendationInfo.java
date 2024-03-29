package com.example.jiowhere;

public class RecommendationInfo {
    /*
    Trying to see if I can use ArrayAdaptor instead of Custom Adaptor

    from RecommendationDetails (firebase) -> RecommendationInfo
     */

    private String location;
    private String time_period;
    private String name;
    private String tags;
    //private int image;
    private String image;

    public RecommendationInfo() {
        //empty
    }

    public RecommendationInfo(String location, String time_period, String name, String tags, String image) {
        this.location = location;
        this.time_period = time_period;
        this.name = name;
        this.image = image;
        this.tags = tags;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
