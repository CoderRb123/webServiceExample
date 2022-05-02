package com.firebase.crudrbcoder.models.Login;

import androidx.annotation.NonNull;

public class LoginRequestModel {
    String email;
    String password;

    public LoginRequestModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NonNull
    @Override
    public String toString() {
        return "{" +
                "email:" + email  +
                ", password:" + password  +
                '}';
    }
}
