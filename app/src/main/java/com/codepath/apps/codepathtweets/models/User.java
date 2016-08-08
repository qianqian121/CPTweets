package com.codepath.apps.codepathtweets.models;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qiming on 8/2/2016.
 */
@Table(name = "users", id = BaseColumns._ID)
public class User extends Model {
    // list attributes
    @Column(name = "name")
    private String name;
    @Column(name = "uid", index = true)
    private long uid;
    @Column(name = "screeName")
    private String screeName;

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

    public static User findOrCreateFromJson(JSONObject json) {
        long rId = 0; // get just the remote id
        try {
            rId = json.getLong("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        User existingUser =
                new Select().from(User.class).where("uid = ?", rId).executeSingle();
        if (existingUser != null) {
            // found and return existing
            return existingUser;
        } else {
            // create and return new user
            User user = User.fromJSON(json);
            user.save();
            return user;
        }
    }

    public static User find(long rId) {
//        long rId = 0; // get just the remote id

        User existingUser =
                new Select().from(User.class).where("uid = ?", rId).executeSingle();

        return existingUser;
    }
}
