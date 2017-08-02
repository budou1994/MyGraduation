package com.graduation.mygraduation.adapter;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.graduation.mygraduation.R;
import com.graduation.mygraduation.entity.JokeBean;
import com.graduation.mygraduation.entity.TodayOfHistoryDetailBean;

import java.util.List;

/**
 *  笑话adapter
 */

public class JokeAdapter extends BaseQuickAdapter<JokeBean.result, BaseViewHolder> {


    public JokeAdapter() {
        super(R.layout.item_joke);
    }

    @Override
    protected void convert(BaseViewHolder helper, JokeBean.result item) {
        helper.setText(R.id.tv_joke_content, item.getContent());
        helper.setText(R.id.tv_joke_date, item.getUpdatetime());
        helper.getConvertView().setOnClickListener(null);
    }



}
