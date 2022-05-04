package com.firebase.crudrbcoder.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.crudrbcoder.R;
import com.firebase.crudrbcoder.adapter.TweetsAdapter;
import com.firebase.crudrbcoder.apiService.ApiResponseInterface;
import com.firebase.crudrbcoder.apiService.ApiService;
import com.firebase.crudrbcoder.models.Twitter.DeleteTweetResponseModel;
import com.firebase.crudrbcoder.models.Twitter.TweetResponseModel;
import com.firebase.crudrbcoder.models.Twitter.tweetCardActionInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton newTweet;
    TweetsAdapter tweetsAdapter;
    Toolbar toolbar;
    ApiService apiService = new ApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkLogin();
        initUi();
        fetchTweets();
        newTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewTweet.class);
                startActivity(intent);
            }
        });
    }


    private void initUi() {
        recyclerView = findViewById(R.id.recyclerView);
        newTweet = findViewById(R.id.newTweet);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Tweeter Demo");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white, getTheme()));
        toolbar.inflateMenu(R.menu.mainmenu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.logout) {
                   SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
                   sharedPreferences.edit().clear().apply();
                   Intent intent = new Intent(MainActivity.this,LoginScreen.class);
                   startActivity(intent);
                }

                return false;
            }
        });
    }

    private void fetchTweets() {
        ProgressDialog progressdialog = new ProgressDialog(this);
        progressdialog.setTitle("Fetching Tweets");
        progressdialog.setMessage("Please Wait....");
        progressdialog.show();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                apiService.fetchTweets(new ApiResponseInterface<TweetResponseModel>() {
                    @Override
                    public void onResponse(TweetResponseModel model) {
                        runOnUiThread(() -> {
                            setupAdapter(model);
                            progressdialog.dismiss();
                        });
                    }

                    @Override
                    public void onError(Exception e) {
                        runOnUiThread(() -> {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressdialog.dismiss();
                        });
                    }
                });
            }
        });
        thread.start();
    }

    private void setupAdapter(TweetResponseModel model) {
        tweetsAdapter = new TweetsAdapter(model, this, new tweetCardActionInterface() {
            @Override
            public void onDelete(String id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Alert")
                        .setMessage("Do you want to delete this tweet")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {

                                        apiService.delete(id, new ApiResponseInterface<DeleteTweetResponseModel>() {
                                            @Override
                                            public void onResponse(DeleteTweetResponseModel model) {
                                                runOnUiThread(MainActivity.this::fetchTweets);
                                            }

                                            @Override
                                            public void onError(Exception e) {
                                                runOnUiThread(() -> Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
                                            }
                                        });
                                    }
                                });
                                thread.start();
                            }
                        })
                .setNegativeButton("No ",null)
                .show();


            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tweetsAdapter);
    }

    private void checkLogin() {
        SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
        boolean isLogin = preferences.getBoolean("isLogin", false);
        if (!isLogin) {
            Intent intent = new Intent(MainActivity.this, LoginScreen.class);
            startActivity(intent);
        }
    }
}