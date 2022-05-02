package com.firebase.crudrbcoder.models.Twitter;

import com.google.gson.annotations.SerializedName;

public class NewTweetResponseModel  {
    @SerializedName("success")
    boolean success;
    @SerializedName("message")
    String message;
    @SerializedName("tweets")
    TweetsModel tweets;

    public NewTweetResponseModel(boolean success, String message,TweetsModel tweets) {
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

    public TweetsModel getTweets() {
        return tweets;
    }

    public void setTweets(TweetsModel tweets) {
        this.tweets = tweets;
    }
}
