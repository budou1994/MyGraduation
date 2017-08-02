package com.graduation.mygraduation.activity;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.graduation.mygraduation.R;
import com.graduation.mygraduation.adapter.JokeAdapter;
import com.graduation.mygraduation.entity.JokeBean;
import com.graduation.mygraduation.net.NetCallback;
import com.graduation.mygraduation.net.NetClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 笑话接口 就是笑话展示
 */
public class JokeActivity extends AppCompatActivity {

    @Bind(R.id.superText)
    SuperTextView superTextView;
    @Bind(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.recycle)
    RecyclerView recycle;

    JokeAdapter adapter;
    List<JokeBean.result> mData;


    private int mCurrentCounter;
    private int mTotalCounter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        ButterKnife.bind(this);
        init();
    }

    private void init() {


        superTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new JokeAdapter();
        mData = new ArrayList<>();

        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        refreshLayout.setColorSchemeColors(Color.YELLOW, Color.BLUE, Color.DKGRAY);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateDate();
            }
        });

        recycle.setLayoutManager(new LinearLayoutManager(JokeActivity.this, LinearLayoutManager.VERTICAL, false));
        recycle.setAdapter(adapter);

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {

            @Override
            public void onLoadMoreRequested() {
                recycle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mCurrentCounter >= mTotalCounter) {
                            //数据加载完成
                            adapter.loadMoreEnd();
                        } else {

                            if (adapter.getItem(0) == null) {
                                return;
                            }

                            int unixtime = adapter.getItem(adapter.getItemCount() - 2).getUnixtime();
                            NetClient.getInstance().GetNowJokeData(unixtime + "", new NetCallback<JokeBean>() {
                                @Override
                                public void onSuccess(JokeBean response, int id) {
                                    List<JokeBean.result> data = response.getResult();
                                    adapter.addData(data);
                                    mCurrentCounter = mTotalCounter;
                                    mTotalCounter += 5;
                                    adapter.loadMoreComplete();

                                }

                                @Override
                                public void onError(Exception e, int id) {
                                    adapter.loadMoreFail();
                                }
                            });

                        }
                    }
                }, 1000);
            }
        });

        updateDate();

    }

    private void updateDate() {
        refreshLayout.setRefreshing(true);
        NetClient.getInstance().GetNowJokeData(1, 8, new NetCallback<JokeBean>() {
            @Override
            public void onSuccess(JokeBean response, int id) {
                adapter.setNewData(response.getResult());
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Exception e, int id) {
                refreshLayout.setRefreshing(false);
            }
        });
    }
}
