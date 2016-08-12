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
 * Created by qiming on 8/8/2016.
 */
public class HomeTimelineFragment extends TweetsListFragment{
    private TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();    // singleton client
        populateTimeline(0);
    }

    protected void populateTimeline(long maxId) {
//        Toast.makeText(getApplicationContext(), "JSON request", Toast.LENGTH_SHORT).show();
        client.lock();
        client.getHomeTimeline(maxId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                client.unlock();
                Toast.makeText(getActivity(), "HomeTimeline Fragment JSON success", Toast.LENGTH_SHORT).show();
                Log.d("TWITTER", response.toString());
                List<Tweet> tweets = Tweet.fromJson(response);
                addAll(tweets);
//                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                client.unlock();
                Toast.makeText(getActivity(), "HomeTimeline Fragment JSON failure", Toast.LENGTH_SHORT).show();
                Log.d("TWITTER", errorResponse.toString());
            }
        });
    }
}
