package com.codepath.apps.codepathtweets.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.codepathtweets.R;
import com.codepath.apps.codepathtweets.models.Tweet;
import com.codepath.apps.codepathtweets.models.User;

import java.util.List;

/**
 * Created by qiming on 8/9/2016.
 */
public class TweetsArrayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Tweet> mTweets;
    private View.OnClickListener mOnClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public TweetsArrayAdapter(List<Tweet> tweets) {
        mTweets = tweets;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        RecyclerView.ViewHolder tweetViewHolder = null;
        View itemView = null;
        if (viewType == 0) {
            itemView = inflater.inflate(R.layout.item_tweet, parent, false);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mOnClickListener.onClick(view);
//                }
//            });
            tweetViewHolder = new SimpleTweetViewHolder(itemView);
        }else {

        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickListener.onClick(view);
            }
        });
        return tweetViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);
        SimpleTweetViewHolder viewHolder = (SimpleTweetViewHolder) holder;
        viewHolder.tvBody.setText(tweet.getBody());
        User user = tweet.getUser();
        viewHolder.tvUserName.setText(user.getName());
        viewHolder.tvScreenName.setText("@" + user.getScreeName());
//        viewHolder.tvTimeLine.setText(tweet.getCreatedAt());
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        if (mTweets == null)
            return 0;
        else
            return mTweets.size();
    }

    public void addAll(List<Tweet> tweets) {
        mTweets.clear();
        mTweets.addAll(tweets);
        notifyDataSetChanged();
    }
}
