package com.firebase.crudrbcoder.models.SignUp;

import com.firebase.crudrbcoder.models.UserModel;

public class SignupResponseModel {

    boolean success;
    String message;
    UserModel user;

    public SignupResponseModel(boolean success, String message, UserModel user) {
        this.success = success;
        this.message = message;
        this.user = user;
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

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
