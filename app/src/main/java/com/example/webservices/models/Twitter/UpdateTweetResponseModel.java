package com.example.webservices.models.Twitter;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateTweetResponseModel {
    @SerializedName("success")
    boolean success;
    @SerializedName("message")
    String message;


    public UpdateTweetResponseModel(boolean success, String message, List<TweetsModel> tweets) {
        this.success = success;
        this.message = message;
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

}
