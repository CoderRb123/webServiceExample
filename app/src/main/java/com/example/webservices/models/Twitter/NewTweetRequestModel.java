package com.example.webservices.models.Twitter;

import com.google.gson.annotations.SerializedName;

public class NewTweetRequestModel {
    String tweets;
    String userName;
    String userEmail;

    public NewTweetRequestModel(String tweets, String userName, String userEmail) {
        this.tweets = tweets;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public String getTweets() {
        return tweets;
    }

    public void setTweets(String tweets) {
        this.tweets = tweets;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
