package com.example.jiowhere;

public class LocationAndNumber {
    //number of activities for that particular location
    int numberOfActivities;
    String nameOfLocation;

    public LocationAndNumber(String nameOfLocation, int numberOfActivities) {
        this.nameOfLocation = nameOfLocation;
        this.numberOfActivities = numberOfActivities;
    }

    public void setNameOfLocation(String nameOfLocation) {
        this.nameOfLocation = nameOfLocation;
    }

    public String getNameOfLocation() {
        return nameOfLocation;
    }

    public int getNumberOfActivities() {
        return numberOfActivities;
    }

    public void setNumberOfActivities(int numberOfActivities) {
        this.numberOfActivities = numberOfActivities;
    }
}
