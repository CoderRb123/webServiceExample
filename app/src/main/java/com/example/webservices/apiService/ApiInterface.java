package com.example.webservices.apiService;

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

import org.json.JSONException;

import java.io.IOException;

public interface ApiInterface {
    void login(LoginRequestModel loginRequestModel, ApiResponseInterface<LoginResponseModel> responseInterface) ;

    void signUp(SignupRequestModel signupRequestModel, ApiResponseInterface<SignupResponseModel> responseInterface);

    void fetchTweets(ApiResponseInterface<TweetResponseModel> responseInterface);

    void postTweet(NewTweetRequestModel newTweetRequestModel, ApiResponseInterface<NewTweetResponseModel> responseInterface);

    void delete(String id, ApiResponseInterface<DeleteTweetResponseModel> responseInterface);

    void updateTweet(UpdateTweetRequestModel updateTweetRequestModel, ApiResponseInterface<UpdateTweetResponseModel> responseInterface);

}
