package com.codepath.apps.codepathtweets.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by qiming on 8/2/2016.
 */

// Parse the JSON + store the data, encapsulate state logic or display logic
//@Table(name = "tweets", id = BaseColumns._ID)
public class Tweet extends Model implements Parcelable {
    // list out the attributes
    @Column(name = "body")
    private String body;
    @Column(name = "uid")
    private long uid;   // unique id for the tweet

    protected Tweet(Parcel in) {
        body = in.readString();
        uid = in.readLong();
        userId = in.readLong();
        createdAt = in.readLong();
    }

    public static final Creator<Tweet> CREATOR = new Creator<Tweet>() {
        @Override
        public Tweet createFromParcel(Parcel in) {
            return new Tweet(in);
        }

        @Override
        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Column(name = "userId")
    long userId;

    final static String format = "ddd MMM dd HH:mm:ss zzzz yyyy";
//    final static DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
//    DateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH);
//    inputFormat.setLenient(true);

    public Tweet(JSONObject object) {
        super();

        try {
//            this.userId = object.getString("user_id");
//            this.userHandle = object.getString("user_username");
//            this.timestamp = object.getString("timestamp");
            this.body = object.getString("text");
            this.uid = object.getLong("id");
//            tweet.createdAt = jsonObject.getString("created_at");
            this.user = User.fromJSON(object.getJSONObject("user"));

            String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
            SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
            sf.setLenient(true);
            try {
                this.createdAt = sf.parse(object.getString("created_at")).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Tweet() {
        super();
    }

    public User getUser() {
        return user;
    }

    private User user;

    public String getBody() {
        return body;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getUid() {
        return uid;
    }

    @Column(name = "createAt")
    private long createdAt;

    // Desearialize the JSON
    // Tweet.fromJSON => Tweet
    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        // Extract the values from the json, store there
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));

            String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
            SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
            sf.setLenient(true);
            try {
                tweet.createdAt = sf.parse(jsonObject.getString("created_at")).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;
    }

    public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

                Tweet tweet = new Tweet(tweetJson);
//                tweet.save();
                Log.d("TWITTER", tweet.getBody());
                tweets.add(tweet);
        }
        return tweets;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flag) {
        dest.writeString(this.body);
        dest.writeLong(this.uid);
        dest.writeLong(this.userId);
        dest.writeLong(this.createdAt);
    }
}
