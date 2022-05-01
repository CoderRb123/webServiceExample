package com.example.webservices.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.webservices.R;
import com.example.webservices.apiService.ApiResponseInterface;
import com.example.webservices.apiService.ApiService;
import com.example.webservices.models.SignUp.SignupRequestModel;
import com.example.webservices.models.SignUp.SignupResponseModel;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpScreen extends AppCompatActivity {
    ApiService apiService = new ApiService();
    TextInputEditText emailInput, passwordInput, nameInput, confirmPasswordInput;
    Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        initUi();
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

    }

    private void signUp() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                SignupRequestModel signupRequestModel = new SignupRequestModel(
                        emailInput.getText().toString(),
                        passwordInput.getText().toString(),
                        nameInput.getText().toString()
                );

                apiService.signUp(signupRequestModel, new ApiResponseInterface<SignupResponseModel>() {
                    @Override
                    public void onResponse(SignupResponseModel model) {
                        if (model.isSuccess()) {
                            showToast(model.getMessage());
                            SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                            SharedPreferences.Editor edit = preferences.edit();
                            edit.putBoolean("isLogin", true);
                            edit.putString("email", model.getUser().getEmail());
                            edit.putString("userName", model.getUser().getUserName());
                            edit.apply();
                            Intent intent = new Intent(SignUpScreen.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            showToast(model.getMessage());
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                        Log.d("APISERVICE", e.getMessage());
                        showToast(e.getMessage());
                    }
                });
            }
        });
        thread.start();

    }

    public void showToast(final String toast) {
        runOnUiThread(() -> Toast.makeText(SignUpScreen.this, toast, Toast.LENGTH_SHORT).show());
    }

    private void initUi() {
        emailInput = findViewById(R.id.signupEmail);
        passwordInput = findViewById(R.id.signupPassword);
        nameInput = findViewById(R.id.signupName);
        confirmPasswordInput = findViewById(R.id.signupConfirmPassword);
        signupBtn = findViewById(R.id.signUpBtn);
    }
}