package com.codepath.apps.codepathtweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.codepathtweets.R;
import com.codepath.apps.codepathtweets.TwitterApplication;
import com.codepath.apps.codepathtweets.TwitterClient;
import com.codepath.apps.codepathtweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Created by qiming on 8/9/2016.
 */
public class UserHeaderFragment extends Fragment {
    @BindView(R.id.ivProfileImage)
    ImageView ivProfileImage;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvScreenName)
    TextView tvScreenName;
    @BindView(R.id.tvFollowersCount)
    TextView tvFollowersCount;
    @BindView(R.id.tvFollowingCount)
    TextView tvFollowingCount;
//    R.layout.fragment_user_header
    private TwitterClient client;
    // unwrap user parcels

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();    // singleton client
//        populateTimeline();
    }

    public UserHeaderFragment() {
        this.setArguments(new Bundle());
    }

    private void populateTimeline(long uid) {
//        Toast.makeText(getApplicationContext(), "JSON request", Toast.LENGTH_SHORT).show();
        client.lock();
        client.getUserInfo(uid, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                client.unlock();
                Toast.makeText(getActivity(), "UserHeaderFragment JSON success", Toast.LENGTH_SHORT).show();
                User user = User.fromJSON(jsonObject);
                displayUser(user);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                client.unlock();
                Toast.makeText(getActivity(), "UserHeaderFragment JSON failure", Toast.LENGTH_SHORT).show();
                Log.d("TWITTER", errorResponse.toString());
            }
        });
    }

    private void displayUser(User user) {
        tvUserName.setText(user.getName());
        tvScreenName.setText(user.getScreeName());
        Glide.with(getActivity()).load(user.getProfileImageUrl()).into(ivProfileImage);
        tvFollowersCount.setText(user.getFollowersCount() + "  Followers");
        tvFollowingCount.setText(user.getFollowingsCount() + "  Followings");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View view = inflater.inflate(R.layout.fragment_user_header, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Long uid = getArguments().getLong("uid");
        if (uid == 0)
            getUerInfo();
        else
            populateTimeline(uid);
    }

    private void getUerInfo() {
        client.lock();
        client.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                client.unlock();
                Toast.makeText(getActivity(), "UserHeaderFragment JSON success", Toast.LENGTH_SHORT).show();
                User user = User.fromJSON(jsonObject);
                displayUser(user);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                client.unlock();
                Toast.makeText(getActivity(), "UserHeaderFragment JSON failure", Toast.LENGTH_SHORT).show();
                Log.d("TWITTER", errorResponse.toString());
            }
        });
    }
}
