package com.graduation.mygraduation.fragment;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.google.gson.Gson;
import com.graduation.mygraduation.R;
import com.graduation.mygraduation.adapter.BeautyListAdapter;
import com.graduation.mygraduation.adapter.NewsDataAdapter;
import com.graduation.mygraduation.entity.BeautyList;
import com.graduation.mygraduation.entity.BeautyPhoList;
import com.graduation.mygraduation.net.NetCallback;
import com.graduation.mygraduation.net.NetClient;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 图片展示的一个列表
 */
public class BeautyListFragment extends BaseFragment {

    private int id;

    @Bind(R.id.recycle_beauty)
    RecyclerView recyclerView;
    @Bind(R.id.swipe_beauty)
    SwipeRefreshLayout swipe_beauty;

    BeautyListAdapter mAdapter;
    int page = 1;

    private int mCurrentCounter;
    private int mTotalCounter = 6;

    public BeautyListFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public BeautyListFragment(int id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beauty_list, container, false);
        ButterKnife.bind(this, view);

        init();
        return view;

    }

    private void init() {

        mAdapter = new BeautyListAdapter();
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        updateData();
        swipe_beauty.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE);
        swipe_beauty.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                updateData();
                Log.i("ss", mAdapter.getData().size() + "");
            }
        });


        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                BeautyPhoList.tngou ta = (BeautyPhoList.tngou) adapter.getItem(position);
                Toast.makeText(getActivity(), ta.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {


                page++;
                NetClient.getInstance().GetBeautyPhoList(id, page, 6, new NetCallback<String>() {
                    @Override
                    public void onSuccess(String response, int id) {
                        BeautyPhoList beautyPhoList = new Gson().fromJson(response, BeautyPhoList.class);
                        mAdapter.addData(beautyPhoList.getTngou());
                        mCurrentCounter = mTotalCounter;
                        mTotalCounter += 6;
                        mAdapter.loadMoreComplete();
                    }

                    @Override
                    public void onError(Exception e, int id) {
                        mAdapter.loadMoreFail();
                    }
                });
//                recyclerView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (mCurrentCounter >= mTotalCounter) {
//                            //数据加载完成
//                            mAdapter.loadMoreEnd();
//                        } else {
//
//                            if (mAdapter.getItem(0) == null) {
//                                return;
//                            }
//
//
//                        }
//                    }
//                }, 500);
//                Toast.makeText(getActivity(), "现在 再刷新", Toast.LENGTH_SHORT).show();
//updateData();
                Log.i("ss", mAdapter.getData().size() + "");
            }
        });
    }


    public void updateData() {
        swipe_beauty.setRefreshing(true);
        NetClient.getInstance().GetBeautyPhoList(id, page, 6, new NetCallback<String>() {

            @Override
            public void onSuccess(String response, int id) {
                if (!TextUtils.isEmpty(response)) {
                }
                BeautyPhoList beautyPhoList = new Gson().fromJson(response, BeautyPhoList.class);
                mAdapter.setNewData(beautyPhoList.getTngou());
                swipe_beauty.setRefreshing(false);
            }

            @Override
            public void onError(Exception e, int id) {
                swipe_beauty.setRefreshing(false);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void fetchData() {
//        updateData();
    }
}
