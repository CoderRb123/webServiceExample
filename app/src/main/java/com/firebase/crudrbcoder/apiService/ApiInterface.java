package com.firebase.crudrbcoder.apiService;

import com.firebase.crudrbcoder.models.Login.LoginRequestModel;
import com.firebase.crudrbcoder.models.Login.LoginResponseModel;
import com.firebase.crudrbcoder.models.SignUp.SignupRequestModel;
import com.firebase.crudrbcoder.models.SignUp.SignupResponseModel;
import com.firebase.crudrbcoder.models.Twitter.DeleteTweetResponseModel;
import com.firebase.crudrbcoder.models.Twitter.NewTweetRequestModel;
import com.firebase.crudrbcoder.models.Twitter.NewTweetResponseModel;
import com.firebase.crudrbcoder.models.Twitter.TweetResponseModel;
import com.firebase.crudrbcoder.models.Twitter.UpdateTweetRequestModel;
import com.firebase.crudrbcoder.models.Twitter.UpdateTweetResponseModel;

public interface ApiInterface {
    void login(LoginRequestModel loginRequestModel, ApiResponseInterface<LoginResponseModel> responseInterface) ;

    void signUp(SignupRequestModel signupRequestModel, ApiResponseInterface<SignupResponseModel> responseInterface);

    void fetchTweets(ApiResponseInterface<TweetResponseModel> responseInterface);

    void postTweet(NewTweetRequestModel newTweetRequestModel, ApiResponseInterface<NewTweetResponseModel> responseInterface);

    void delete(String id, ApiResponseInterface<DeleteTweetResponseModel> responseInterface);

    void updateTweet(UpdateTweetRequestModel updateTweetRequestModel, ApiResponseInterface<UpdateTweetResponseModel> responseInterface);

}
