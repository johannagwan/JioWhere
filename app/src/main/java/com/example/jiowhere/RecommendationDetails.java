package com.example.jiowhere;

import java.sql.Time;

public class RecommendationDetails {
    public String id;
    public String nameOfActivity;
    public String nearestMRT;
    public String address;
    public String timePeriod;
    public boolean isPermanent;
    public String reviews;

    public RecommendationDetails(String id, String nameOfActivity, String nearestMRT, String address, String timePeriod, boolean isPermanent, String reviews) {
        this.id = id;
        this.nameOfActivity = nameOfActivity;
        this.nearestMRT = nearestMRT;
        this.address = address;
        this.timePeriod = timePeriod;
        this.isPermanent = isPermanent;
        this.reviews = reviews;
    }

    public String getNameOfActivity() {
        return nameOfActivity;
    }

    public String getNearestMRT() {
        return nearestMRT;
    }

    public String getAddress() {
        return address;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public boolean getIsPermanent() {
        return isPermanent;
    }

    public String getReviews() {
        return reviews;
    }
}
