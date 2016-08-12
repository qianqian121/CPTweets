package com.codepath.apps.codepathtweets.fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.codepathtweets.TwitterApplication;
import com.codepath.apps.codepathtweets.TwitterClient;
import com.codepath.apps.codepathtweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by qiming on 8/9/2016.
 */
public class UserTimelineFragment extends TweetsListFragment{
    private TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();    // singleton client
        populateTimeline(0);
    }

    // static newInstance

    protected void populateTimeline(long maxId) {
//        Toast.makeText(getApplicationContext(), "JSON request", Toast.LENGTH_SHORT).show();
        client.lock();
        client.getUserTimeline(null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                client.unlock();
                Toast.makeText(getActivity(), "UserTimelineFragment JSON success", Toast.LENGTH_SHORT).show();
                Log.d("TWITTER", response.toString());
                List<Tweet> tweets = Tweet.fromJson(response);
                addAll(tweets);
                notifyItemRangeChanged();
//                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                client.unlock();
                Toast.makeText(getActivity(), "UserTimelineFragment JSON failure", Toast.LENGTH_SHORT).show();
                Log.d("TWITTER", errorResponse.toString());
            }
        });
    }
}
