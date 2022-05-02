package com.firebase.crudrbcoder.models.Twitter;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TweetsModel implements Serializable {
    @SerializedName("id")
    int id;
    @SerializedName("tweets")
    String tweet;
    @SerializedName("userName")
    String userName;
    @SerializedName("userEmail")
    String userEmail;
    @SerializedName("created_at")
    String createAt;
    @SerializedName("updated_at")
    String updatedAt;

    public TweetsModel(int id, String tweet, String userName, String userEmail, String createAt, String updatedAt) {
        this.id = id;
        this.tweet = tweet;
        this.userName = userName;
        this.userEmail = userEmail;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
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

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
