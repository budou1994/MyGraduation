package com.graduation.mygraduation.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.graduation.mygraduation.R;
import com.graduation.mygraduation.activity.NewsDataShowActivity;
import com.graduation.mygraduation.activity.WebShowActivity;
import com.graduation.mygraduation.adapter.NewsDataAdapter;
import com.graduation.mygraduation.entity.NewsDataBean;
import com.graduation.mygraduation.net.NetCallback;
import com.graduation.mygraduation.net.NetClient;
import com.graduation.mygraduation.weiget.ItemDivider;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *  新闻模块的列表详情
 *   作用于viewpager
 */
public class NewsDetailsFragment extends BaseFragment {


    @Bind(R.id.rv_new_detail)
    RecyclerView rvNewDetail;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;

    private NewsDataAdapter mAdapter;

    String type;

    public NewsDetailsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public NewsDetailsFragment(String type) {
        this.type = type;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news_details, null);
        ButterKnife.bind(this, view);
        mAdapter = new NewsDataAdapter();
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);

        /*************************** 设置下拉刷新 ***************************/
        srl.setColorSchemeColors(Color.RED, Color.RED);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateData();
            }
        });

        /*************************** recyclerView 初始化数据***************************/
        rvNewDetail.setAdapter(mAdapter);
        rvNewDetail.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvNewDetail.addItemDecoration(new ItemDivider()
                .setDividerColor(Color.parseColor("#DADADA"))
                .setDividerWith(1));
        rvNewDetail.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), WebShowActivity.class);
                intent.putExtra("url", ((NewsDataBean.ResultBean.DataBean) adapter.getItem(position)).getUrl());
                getActivity().startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void fetchData() {
        updateData();
    }

    public void updateData() {
        srl.setRefreshing(true);
        NetClient.getInstance().GetNewsData(type, new NetCallback<NewsDataBean>() {
            @Override
            public void onSuccess(NewsDataBean response, int id) {
                mAdapter.setNewData(response.getResult().getData());
                srl.setRefreshing(false);
            }

            @Override
            public void onError(Exception e, int id) {
                srl.setRefreshing(false);
            }
        });
    }
}
