package com.firebase.crudrbcoder.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.crudrbcoder.R;
import com.firebase.crudrbcoder.apiService.ApiResponseInterface;
import com.firebase.crudrbcoder.apiService.ApiService;
import com.firebase.crudrbcoder.models.Login.LoginRequestModel;
import com.firebase.crudrbcoder.models.Login.LoginResponseModel;
import com.google.android.material.textfield.TextInputEditText;

public class LoginScreen extends AppCompatActivity {
    ApiService apiService = new ApiService();
    TextView newAccount;
    Button loginBtn;
    TextInputEditText emailInput;
    TextInputEditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        initUi();
        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreen.this, SignUpScreen.class);
                startActivity(intent);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validator(emailInput.getText().toString())) {
                    Toast.makeText(LoginScreen.this, "Email Required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (validator(passwordInput.getText().toString())) {
                    Toast.makeText(LoginScreen.this, "Password Required", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginProcess();
            }
        });
    }

    private void loginProcess() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                LoginRequestModel loginRequestModel = new LoginRequestModel(
                        emailInput.getText().toString(),
                        passwordInput.getText().toString()
                );
                apiService.login(loginRequestModel, new ApiResponseInterface<LoginResponseModel>() {
                    @Override
                    public void onResponse(LoginResponseModel model) {
                        if (model.isSuccess()) {
                            SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                            SharedPreferences.Editor edit = preferences.edit();
                            edit.putBoolean("isLogin", true);
                            edit.putString("email", model.getUser().getEmail());
                            edit.putString("userName", model.getUser().getUserName());
                            edit.apply();
                            Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginScreen.this,model.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                        Log.d("APISERVICE", e.getMessage());
                        Toast.makeText(LoginScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        thread.start();

    }

    private boolean validator(String input) {
        return input.trim().isEmpty();
    }

    private void initUi() {
        newAccount = findViewById(R.id.newAccount);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordinput);
        loginBtn = findViewById(R.id.loginBtn);
    }


}