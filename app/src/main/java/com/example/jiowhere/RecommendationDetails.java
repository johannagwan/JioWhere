package com.example.jiowhere;

import java.sql.Time;

public class RecommendationDetails {
    public String nameOfActivity;
    public String nearestMRT;
    public String address;
    public String timePeriod;
    public boolean isPermanent;
    public String reviews;

    public RecommendationDetails(String nameOfActivity, String nearestMRT, String address, String timePeriod, boolean isPermanent, String reviews) {
        this.nameOfActivity = nameOfActivity;
        this.nearestMRT = nearestMRT;
        this.address = address;
        this.timePeriod = timePeriod;
        this.isPermanent = isPermanent;
        this.reviews = reviews;
    }
}
