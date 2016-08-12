package com.codepath.apps.codepathtweets.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.codepath.apps.codepathtweets.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String query = getIntent().getStringExtra("query");
        FragmentManager mgr = this.getSupportFragmentManager();
        Fragment frg = mgr.findFragmentById(R.id.fragmentSearchTimeline);
        Bundle bdl = frg.getArguments();
        bdl.putString("query", query);
    }
}
