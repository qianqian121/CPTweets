package com.codepath.apps.codepathtweets.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.codepathtweets.R;
import com.codepath.apps.codepathtweets.adapters.TweetsArrayAdapter;
import com.codepath.apps.codepathtweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TweetsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TweetsListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
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

    private OnFragmentInteractionListener mListener;

    public TweetsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TweetsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TweetsListFragment newInstance(String param1, String param2) {
        TweetsListFragment fragment = new TweetsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // create adapter
        setupContent();
    }

    private void setupContent() {
        mTweets = new ArrayList<>();
        mTweetsArrayAdapter = new TweetsArrayAdapter(mTweets);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
    }

    // interface for parent activity to access adapter add data into adapter

    private void setupAdapter() {
        rvTweets.setAdapter(mTweetsArrayAdapter);

//        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLinearLayoutManager) {
//            @Override
//            public void onLoadMore(int page, final int totalItemsCount) {
//                mCursor.moveToLast();
//                long maxId = Tweet.fromCursor(mCursor).getUid();
//                client.getHomeTimeline(maxId, new JsonHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                        Toast.makeText(TimelineActivity.this, "Load more JSON success", Toast.LENGTH_SHORT).show();
//                        Log.d("TWITTER", response.toString());
//                        Tweet.fromJson(response);
////                mTweetCursorAdapter.swapCursor(mCursor);
//                        mTweetCursorAdapter.notifyDataSetChanged();
////                        mTweetCursorAdapter.notifyItemRangeChanged(totalItemsCount, mCursor.getCount());
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                        Toast.makeText(TimelineActivity.this, "Load more JSON failure", Toast.LENGTH_SHORT).show();
//                        Log.d("TWITTER", errorResponse.toString());
//                    }
//                });
//                mTweetCursorAdapter.notifyItemRangeChanged(totalItemsCount, totalItemsCount + 25);
//                return;
//            }
//        });

//        mTweetCursorAdapter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(TimelineActivity.this, DetailActivity.class);
//                Tweet tweet = Tweet.fromCursor(mTweetCursorAdapter.getCursor(rvTweets.getChildAdapterPosition(view)));
//                intent.putExtra("tweetDetail", Parcels.wrap(tweet));
//                startActivityForResult(intent, REQUEST_CODE);
//                return;
//            }
//        });
    }

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
        setupAdapter();
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                populateTimeline();
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public void addAll(List<Tweet> tweets) {
        mTweetsArrayAdapter.addAll(tweets);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
