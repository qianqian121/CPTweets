package com.codepath.apps.codepathtweets.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.codepath.apps.codepathtweets.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.fragmentUserHeader)
    FrameLayout fragmentUserHeader;
    @BindView(R.id.fragmentUserTimeline)
    FrameLayout fragmentUserTimeline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

//        UserTimelineFragment userTimelineFragment = (UserTimelineFragment) UserTimelineFragment.newInstance("", "");
//        android.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    }
}
