package com.codepath.apps.codepathtweets.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codepath.apps.codepathtweets.fragments.HomeTimelineFragment;
import com.codepath.apps.codepathtweets.fragments.MentionsTimelineFragment;

/**
 * Created by qiming on 8/9/2016.
 */
public class TweetsPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = {"Home", "Mentions"};

    public TweetsPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new HomeTimelineFragment();
        } else if (position == 1) {
            return new MentionsTimelineFragment();
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    public String getFragmentTag(int viewPagerId, int fragmentPosition)
    {
        return "android:switcher:" + viewPagerId + ":" + fragmentPosition;
    }
}
