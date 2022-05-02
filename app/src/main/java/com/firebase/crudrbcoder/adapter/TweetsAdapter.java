package com.firebase.crudrbcoder.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.crudrbcoder.R;
import com.firebase.crudrbcoder.models.Twitter.TweetResponseModel;
import com.firebase.crudrbcoder.models.Twitter.TweetsModel;
import com.firebase.crudrbcoder.models.Twitter.tweetCardActionInterface;
import com.firebase.crudrbcoder.screens.NewTweet;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.MyViewHolder> {

    TweetResponseModel tweetResponseModel;
    Activity context;
    tweetCardActionInterface tweetCardActionInterface;

    public TweetsAdapter(TweetResponseModel tweetResponseModel, Activity context, tweetCardActionInterface tweetCardActionInterface) {
        this.tweetResponseModel = tweetResponseModel;
        this.context = context;
        this.tweetCardActionInterface = tweetCardActionInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TweetsModel tweetsModel = tweetResponseModel.getTweets().get(position);
        holder.delete.setVisibility(View.GONE);
        holder.userName.setText(tweetsModel.getUserName());

        holder.userEmail.setText(tweetsModel.getUserEmail());
        holder.userTweets.setText(tweetsModel.getTweet());
        SharedPreferences preferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        if (tweetsModel.getUserEmail().equals(preferences.getString("email", ""))) {
            holder.delete.setVisibility(View.VISIBLE);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tweetCardActionInterface.onDelete(String.valueOf(tweetsModel.getId()));
                }
            });
        }
        holder.cardMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewTweet.class);
                intent.putExtra("current", tweetsModel);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tweetResponseModel.getTweets().size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView userName, userEmail, userTweets;
        ImageButton delete;
        CardView cardMain;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userEmail = itemView.findViewById(R.id.userEmail);
            userName = itemView.findViewById(R.id.userName);
            userTweets = itemView.findViewById(R.id.tweet);
            delete = itemView.findViewById(R.id.deleteTweet);
            cardMain = itemView.findViewById(R.id.cardMain);
        }
    }
}
