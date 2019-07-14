package com.example.jiowhere;

import java.sql.Time;

public class RecommendationDetails {
    public String id;
    public String nameOfActivity;
    public String nearestMRT;
    public String address;
    public String timePeriod;
    //public boolean isPermanent;
    public String reviews;
    public String imageUrl;
    public String openingHours;
    //UploadImage uploadImage;
    String tags;

    public RecommendationDetails(String id, String nameOfActivity,
                                 String nearestMRT, String address,
                                 String timePeriod, String openingHours, String reviews,
                                 String tags, String imageUrl) {
        this.id = id;
        this.nameOfActivity = nameOfActivity;
        this.nearestMRT = nearestMRT;
        this.address = address;
        this.timePeriod = timePeriod;
        //this.isPermanent = isPermanent;
        this.reviews = reviews;
        this.tags = tags;
        this.imageUrl = imageUrl;
        this.openingHours = openingHours;
        //this.uploadImage = uploadImage;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
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
    /*
    public void setUploadImage(UploadImage uploadImage) {
        this.uploadImage = uploadImage;
    }

    public UploadImage getUploadImage() {
        return uploadImage;
    }
    */
}
