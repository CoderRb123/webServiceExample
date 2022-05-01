package com.example.webservices.models.Twitter;

public class UpdateTweetRequestModel {

    String tweets;
    String id;

    public UpdateTweetRequestModel(String tweets, String id) {
        this.tweets = tweets;
        this.id = id;
    }

    public String getTweets() {
        return tweets;
    }

    public void setTweets(String tweets) {
        this.tweets = tweets;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
