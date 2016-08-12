package com.codepath.apps.codepathtweets.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.codepathtweets.R;
import com.codepath.apps.codepathtweets.activities.DetailActivity;
import com.codepath.apps.codepathtweets.activities.ProfileActivity;
import com.codepath.apps.codepathtweets.adapters.EndlessRecyclerViewScrollListener;
import com.codepath.apps.codepathtweets.adapters.TweetsArrayAdapter;
import com.codepath.apps.codepathtweets.models.Tweet;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public abstract class TweetsListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_CODE = 20;
    @BindView(R.id.rvTweets)
    RecyclerView rvTweets;
//    R.layout.fragment_tweets_list
    SwipeRefreshLayout swipeContainer;
    TweetsArrayAdapter mTweetsArrayAdapter;
    LinearLayoutManager mLinearLayoutManager;
    List<Tweet> mTweets;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TweetsListFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment TweetsListFragment.
//     */
    // TODO: Rename and change types and number of parameters
//    public static TweetsListFragment newInstance(String param1, String param2) {
//        TweetsListFragment fragment = new TweetsListFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // create adapter
        mNoMore = false;
        setupContent();
    }

    private void setupContent() {
        mTweets = new ArrayList<>();
        mTweetsArrayAdapter = new TweetsArrayAdapter(mTweets);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
    }

    // interface for parent activity to access adapter add data into adapter

    private void setupAdapter() {
        mTweetsArrayAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Tweet tweet = mTweets.get(rvTweets.getChildAdapterPosition(view));
                intent.putExtra("tweetDetail", Parcels.wrap(tweet));
                getActivity().startActivityForResult(intent, REQUEST_CODE);
                return;
            }
        });

        mTweetsArrayAdapter.setOnProfileImageViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra("userId", (long)view.getTag());
                startActivity(intent);
            }
        });

        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int page, final int totalItemsCount) {
                if (mNoMore)
                    return;
                mSize = mTweets.size();
                Tweet lastTweet = mTweets.get(mTweets.size() - 1);
                long maxId = lastTweet.getUid() - 1;
                TweetsListFragment.this.populateTimeline(maxId);
//                mTweetsArrayAdapter.notifyItemRangeChanged(totalItemsCount, totalItemsCount + 25);
                return;
            }
        });
    }

    private int mSize;
    protected void notifyItemRangeChanged() {
//        mTweetsArrayAdapter.notifyItemRangeChanged(mSize, mSize + 25);
    }

    protected abstract void populateTimeline(long maxId);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        ButterKnife.bind(this, view);

        // setup RecycleView Adapter
        setupView();
        return view;
    }

    private void setupView() {
        rvTweets.setLayoutManager(mLinearLayoutManager);
        rvTweets.setAdapter(mTweetsArrayAdapter);
        setupAdapter();
//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
////                populateTimeline();
//            }
//        });
//
//        // Configure the refreshing colors
//        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);
    }

    boolean mNoMore;
    public void addAll(List<Tweet> tweets) {
        if (tweets.size() < 25)
            mNoMore = true;
        if (tweets.size() > 0)
            mTweetsArrayAdapter.addAll(tweets);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void insert(Tweet tweet, int position) {
        mTweets.add(0, tweet);
        mTweetsArrayAdapter.notifyDataSetChanged();
    }
}
