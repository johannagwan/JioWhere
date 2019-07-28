package com.example.jiowhere;

import java.sql.Time;

public class RecommendationDetails {
    public String id;
    public String nameOfActivity;
    public String nearestMRT;
    public String address;
    public String timePeriod;
    //public boolean isPermanent;
    public String imageUrl;
    public String openingHours;
    //UploadImage uploadImage;
    String tags;
    String cost;
    String description;

    public RecommendationDetails() {
        //no argument constructor
    }

    public RecommendationDetails(String id, String nameOfActivity,
                                 String nearestMRT, String address,
                                 String timePeriod, String openingHours,
                                 String tags, String cost, String imageUrl, String description) {
        this.id = id;
        this.nameOfActivity = nameOfActivity;
        this.nearestMRT = nearestMRT;
        this.address = address;
        this.timePeriod = timePeriod;
        //this.isPermanent = isPermanent;
        this.tags = tags;
        this.imageUrl = imageUrl;
        this.openingHours = openingHours;
        this.cost = cost;
        this.description = description;
        //this.uploadImage = uploadImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNameOfActivity() {
        return nameOfActivity;
    }

    public void setNameOfActivity(String nameOfActivity) {
        this.nameOfActivity = nameOfActivity;
    }

    public String getNearestMRT() {
        return nearestMRT;
    }

    public void setNearestMRT(String nearestMRT) {
        this.nearestMRT = nearestMRT;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    /*
    public void setPermanent(boolean permanent) {
        isPermanent = permanent;
    }
    */

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
