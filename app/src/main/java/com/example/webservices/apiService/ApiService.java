package com.example.webservices.apiService;

import android.util.Log;

import com.example.webservices.models.Login.LoginRequestModel;
import com.example.webservices.models.Login.LoginResponseModel;
import com.example.webservices.models.SignUp.SignupRequestModel;
import com.example.webservices.models.SignUp.SignupResponseModel;
import com.example.webservices.models.Twitter.DeleteTweetResponseModel;
import com.example.webservices.models.Twitter.NewTweetRequestModel;
import com.example.webservices.models.Twitter.NewTweetResponseModel;
import com.example.webservices.models.Twitter.TweetResponseModel;
import com.example.webservices.models.Twitter.UpdateTweetRequestModel;
import com.example.webservices.models.Twitter.UpdateTweetResponseModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiService implements ApiInterface {
    @Override
    public void login(LoginRequestModel loginRequestModel, ApiResponseInterface<LoginResponseModel> responseInterface) {
        try {
            JSONObject data = new JSONObject();
            data.put("email", loginRequestModel.getEmail());
            data.put("password", loginRequestModel.getPassword());
            String res = httpHandler("http://10.0.2.2:8000/api/login", "POST", data.toString());
            Gson gson = new Gson();
            LoginResponseModel loginResponseModel = gson.fromJson(res, new TypeToken<LoginResponseModel>() {
            }.getType());
            responseInterface.onResponse(loginResponseModel);
        } catch (IOException | JSONException ignored) {
            responseInterface.onError(ignored);
        }
    }

    @Override
    public void signUp(SignupRequestModel signupRequestModel, ApiResponseInterface<SignupResponseModel> responseInterface) {
        try {
            JSONObject data = new JSONObject();
            data.put("email", signupRequestModel.getEmail());
            data.put("password", signupRequestModel.getPassword());
            data.put("userName", signupRequestModel.getName());
            String res = httpHandler("http://10.0.2.2:8000/api/register", "POST", data.toString());
            Gson gson = new Gson();
            SignupResponseModel s = gson.fromJson(res, new TypeToken<SignupResponseModel>() {
            }.getType());
            responseInterface.onResponse(s);
        } catch (IOException | JSONException d) {
            responseInterface.onError(d);
        }
    }

    @Override
    public void fetchTweets(ApiResponseInterface<TweetResponseModel> responseInterface) {
        try {
            String res = httpHandler("http://10.0.2.2:8000/api/twittes", "GET", null);
            Gson gson = new Gson();
            TweetResponseModel s = gson.fromJson(res, new TypeToken<TweetResponseModel>() {
            }.getType());
            responseInterface.onResponse(s);
        } catch (IOException d) {
            responseInterface.onError(d);
        }
    }

    @Override
    public void postTweet(NewTweetRequestModel newTweetRequestModel, ApiResponseInterface<NewTweetResponseModel> responseInterface){
        try {
            JSONObject data = new JSONObject();
            data.put("tweets", newTweetRequestModel.getTweets());
            data.put("userName", newTweetRequestModel.getUserName());
            data.put("userEmail", newTweetRequestModel.getUserEmail());
            String res = httpHandler("http://10.0.2.2:8000/api/twittes", "POST", data.toString());
            Gson gson = new Gson();
            NewTweetResponseModel s = gson.fromJson(res, new TypeToken<NewTweetResponseModel>() {
            }.getType());
            responseInterface.onResponse(s);
        } catch (IOException | JSONException d) {
            responseInterface.onError(d);
        }
    }

    @Override
    public void delete(String id,ApiResponseInterface<DeleteTweetResponseModel> responseInterface) {
        try {
            String res = httpHandler("http://10.0.2.2:8000/api/twittes/"+id, "DELETE", null);
            Gson gson = new Gson();
            DeleteTweetResponseModel s = gson.fromJson(res, new TypeToken<DeleteTweetResponseModel>() {
            }.getType());
            responseInterface.onResponse(s);
        } catch (IOException d) {
            responseInterface.onError(d);
        }
    }

    @Override
    public void updateTweet(UpdateTweetRequestModel updateTweetRequestModel, ApiResponseInterface<UpdateTweetResponseModel> responseInterface) {
        try {
            JSONObject data = new JSONObject();
            data.put("tweets", updateTweetRequestModel.getTweets());
            data.put("id", updateTweetRequestModel.getId());
            String res = httpHandler("http://10.0.2.2:8000/api/twittes", "PUT", data.toString());
            Log.d("API", "updateTweet: "+res);
            Gson gson = new Gson();
            UpdateTweetResponseModel s = gson.fromJson(res, new TypeToken<UpdateTweetResponseModel>() {
            }.getType());
            responseInterface.onResponse(s);
        } catch (IOException | JSONException d) {
            responseInterface.onError(d);
        }
    }


    public String httpHandler(String url, String type, String inputs) throws IOException {
        final HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod(type);
        if (inputs != null && !inputs.isEmpty()) {
            conn.setFixedLengthStreamingMode(inputs.getBytes().length);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            OutputStream os = new BufferedOutputStream(conn.getOutputStream());
            os.write(inputs.getBytes());
            os.flush();
        }

        final InputStream inputStream = conn.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;
        StringBuilder json = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            json.append(inputLine);
        }
        return json.toString();
    }
}
