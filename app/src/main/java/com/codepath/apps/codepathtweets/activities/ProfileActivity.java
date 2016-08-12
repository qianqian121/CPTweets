package com.codepath.apps.codepathtweets.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.codepath.apps.codepathtweets.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        long uid = getIntent().getLongExtra("userId", 0);
//        UserTimelineFragment userTimelineFragment = (UserTimelineFragment) UserTimelineFragment.newInstance("", "");
//        android.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        FragmentManager mgr = this.getSupportFragmentManager();
        Fragment frg = mgr.findFragmentById(R.id.fragmentUserHeader);
        Bundle bdl = frg.getArguments();
        bdl.putLong("uid", uid);
        Fragment fragmentUserTimeline = mgr.findFragmentById(R.id.fragmentUserTimeline);
        Bundle bundleUserTimeline = fragmentUserTimeline.getArguments();
        bundleUserTimeline.putLong("uid", uid);
    }

    // fragment call back set actionbar title
}
