package com.graduation.mygraduation.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.graduation.mygraduation.R;
import com.graduation.mygraduation.adapter.TodayAdapter;
import com.graduation.mygraduation.entity.TodayOfHistoryBean;
import com.graduation.mygraduation.net.NetCallback;
import com.graduation.mygraduation.net.NetClient;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 历史百科列表页
 */
public class TodayActivity extends AppCompatActivity {

    @Bind(R.id.super_text)
    SuperTextView superTextView;
    @Bind(R.id.recycle_today)
    RecyclerView recyclerView;
    @Bind(R.id.floatButton)
    FloatingActionButton floatButton;


    TodayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        ButterKnife.bind(this);
        init();
    }

    private void init() {
        adapter = new TodayAdapter();
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        //悬浮按钮设置点击事件
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerView != null) {
                    recyclerView.scrollToPosition(0);
                }
            }
        });

        superTextView.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onSuperTextViewClick() {
                super.onSuperTextViewClick();
                finish();
            }
        });

        //recyclerView初始化
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(TodayActivity.this, TodayDetailActivity.class);
                intent.putExtra("e_id", ((TodayOfHistoryBean.ResultBean) adapter.getItem(position)).getE_id());
                startActivity(intent);
            }
        });

        //获得当前的日期
        Calendar calendar = Calendar.getInstance();
        final int month = calendar.get(Calendar.MONTH) + 1;
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
//        recyclerView.setTitle("历史上的今天 (" + month + "月" + day + "日)");

        //初次加载数据
        NetClient.getInstance().GetTodayOfHistoryData(month, day,
                new NetCallback<TodayOfHistoryBean>() {
                    @Override
                    public void onSuccess(TodayOfHistoryBean response, int id) {

                        List<TodayOfHistoryBean.ResultBean> result = response.getResult();
                        adapter.addData(result);
                    }

                    @Override
                    public void onError(Exception e, int id) {

                    }
                });
    }
}
