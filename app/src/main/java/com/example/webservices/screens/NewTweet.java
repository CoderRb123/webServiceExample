package com.example.webservices.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.webservices.R;
import com.example.webservices.apiService.ApiResponseInterface;
import com.example.webservices.apiService.ApiService;
import com.example.webservices.models.Twitter.NewTweetRequestModel;
import com.example.webservices.models.Twitter.NewTweetResponseModel;
import com.example.webservices.models.Twitter.TweetsModel;
import com.example.webservices.models.Twitter.UpdateTweetRequestModel;
import com.example.webservices.models.Twitter.UpdateTweetResponseModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class NewTweet extends AppCompatActivity {

    TextInputEditText tweet;
    Button tweetBtn;
    boolean isUpdate = false;
    TweetsModel tweetsModel;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tweet);
        getData();
        initUi();
        if (isUpdate) {
            tweet.setText(tweetsModel.getTweet());
            tweetBtn.setText("Update");
        }
        tweetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tweet.getText().toString().trim().isEmpty()) {
                    Toast.makeText(NewTweet.this, "Tweet Required *", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isUpdate) {
                    updateTweet();
                } else {
                    postTweet();
                }
            }
        });
    }

    private void getData() {
        tweetsModel = (TweetsModel) getIntent().getSerializableExtra("current");
        if (tweetsModel != null) {
            isUpdate = true;

        }
    }

    private void postTweet() {
        ApiService apiService = new ApiService();
        SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
        NewTweetRequestModel newTweetRequestModel = new NewTweetRequestModel(
                tweet.getText().toString(),
                preferences.getString("userName", ""),
                preferences.getString("email", "")
        );
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                apiService.postTweet(newTweetRequestModel, new ApiResponseInterface<NewTweetResponseModel>() {
                    @Override
                    public void onResponse(NewTweetResponseModel model) {
                        runOnUiThread(() -> {
                            Toast.makeText(NewTweet.this, model.getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(NewTweet.this, MainActivity.class);
                            startActivity(intent);
                        });

                    }

                    @Override
                    public void onError(Exception e) {
                        runOnUiThread(() -> Toast.makeText(NewTweet.this, e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                });
            }
        });
        thread.start();
    }

    private void updateTweet() {
        ApiService apiService = new ApiService();
        UpdateTweetRequestModel updateTweetRequestModel = new UpdateTweetRequestModel(
                tweet.getText().toString(),
                String.valueOf(tweetsModel.getId())
        );
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                apiService.updateTweet(updateTweetRequestModel, new ApiResponseInterface<UpdateTweetResponseModel>() {
                    @Override
                    public void onResponse(UpdateTweetResponseModel model) {
                        if (model.isSuccess()) {
                            runOnUiThread(() -> {
                                Toast.makeText(NewTweet.this, model.getMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                                Intent intent = new Intent(NewTweet.this, MainActivity.class);
                                startActivity(intent);
                            });
                        } else {
                            runOnUiThread(() -> {
                                Toast.makeText(NewTweet.this, model.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                        }

                    }

                    @Override
                    public void onError(Exception e) {
                        runOnUiThread(() -> Toast.makeText(NewTweet.this, e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                });
            }
        });
        thread.start();
    }

    private void initUi() {
        tweet = findViewById(R.id.tweetInput);
        tweetBtn = findViewById(R.id.tweetBtn);
        toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle( isUpdate ? "Update Tweet" :"New Tweet");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white,getTheme()));
    }
}