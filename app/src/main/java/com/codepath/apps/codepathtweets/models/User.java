package com.codepath.apps.codepathtweets.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qiming on 8/2/2016.
 */
//@Table(name = "users", id = BaseColumns._ID)
public class User implements Parcelable {
    // list attributes
    @Column(name = "name")
    private String name;
    @Column(name = "uid", index = true)
    private long uid;
    @Column(name = "screeName")
    private String screeName;

    public User() {
        super();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getScreeName() {
        return screeName;
    }

    public long getUid() {
        return uid;
    }

    @Column(name = "profileImageUrl")
    private String profileImageUrl;

    String tagline;

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFollowingsCount() {
        return followingsCount;
    }

    public String getTagline() {
        return tagline;
    }

    int followersCount;
    int followingsCount;

    //
    public static User fromJSON(JSONObject jsonObject) {
        User u = new User();
        try {
            u.name = jsonObject.getString("name");
            u.uid = jsonObject.getLong("id");
            u.screeName = jsonObject.getString("screen_name");
            u.profileImageUrl = jsonObject.getString("profile_image_url");
            u.tagline = jsonObject.getString("description");
            u.followersCount = jsonObject.getInt("followers_count");
            u.followingsCount = jsonObject.getInt("friends_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(this.name);
        dest.writeLong(this.uid);
        dest.writeString(this.screeName);
        dest.writeString(this.profileImageUrl);
        dest.writeString(this.tagline);
        dest.writeInt(this.followersCount);
        dest.writeInt(this.followingsCount);
    }

    protected User(Parcel in) {
        name = in.readString();
        uid = in.readLong();
        screeName = in.readString();
        profileImageUrl = in.readString();
        tagline = in.readString();
        followersCount = in.readInt();
        followingsCount = in.readInt();
    }
}
