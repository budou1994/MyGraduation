package com.graduation.mygraduation.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allen.library.SuperTextView;
import com.graduation.mygraduation.R;
import com.graduation.mygraduation.activity.BeautyGirlActivity;
import com.graduation.mygraduation.activity.JokeActivity;
import com.graduation.mygraduation.activity.ScrollingActivity;
import com.graduation.mygraduation.activity.TodayActivity;
import com.graduation.mygraduation.utils.ActivityUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 发现 页面  可以算上是工具
 * <p>
 * 一些功能可以展示一下
 */
public class ToolsFragment extends Fragment {

    @Bind(R.id.meizhi)
    SuperTextView meizhi;
    @Bind(R.id.duanzi)
    SuperTextView daunzi;
    @Bind(R.id.history)
    SuperTextView history;
    ActivityUtils activityUtils;

    public ToolsFragment() {
        // Required empty  public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tools, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    public void init() {
        activityUtils = new ActivityUtils(getActivity());
        meizhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityUtils.startActivity(BeautyGirlActivity.class);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityUtils.startActivity(TodayActivity.class);
            }
        });
        daunzi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityUtils.startActivity(JokeActivity.class);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
