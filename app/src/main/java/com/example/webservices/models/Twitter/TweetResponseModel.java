package com.example.webservices.models.Twitter;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TweetResponseModel {
    @SerializedName("success")
    boolean success;
    @SerializedName("message")
    String message;
    @SerializedName("tweets")
    List<TweetsModel> tweets;

    public TweetResponseModel(boolean success, String message, List<TweetsModel> tweets) {
        this.success = success;
        this.message = message;
        this.tweets = tweets;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TweetsModel> getTweets() {
        return tweets;
    }

    public void setTweets(List<TweetsModel> tweets) {
        this.tweets = tweets;
    }
}
