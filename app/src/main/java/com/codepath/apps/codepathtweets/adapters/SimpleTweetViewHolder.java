package com.codepath.apps.codepathtweets.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.codepathtweets.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiming on 8/9/2016.
 */
public class SimpleTweetViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.ivProfileImage)
    ImageView ivProfileImage;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvBody)
    TextView tvBody;
    @BindView(R.id.tvScreenName)
    TextView tvScreenName;
    @BindView(R.id.tvTimeLine)
    TextView tvTimeLine;
    //        R.layout.item_tweet
    ViewDataBinding mViewDataBinding;

    public SimpleTweetViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mViewDataBinding = DataBindingUtil.bind(itemView);
    }
}
