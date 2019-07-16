package com.example.jiowhere;

public class Review {

    private String review;
    private String userEmail;
    private String username;

    public Review() {
        //no arg constructor
    }

    public Review(String review, String userEmail, String username) {
        this.review = review;
        this.userEmail = userEmail;
        this.username = username;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
