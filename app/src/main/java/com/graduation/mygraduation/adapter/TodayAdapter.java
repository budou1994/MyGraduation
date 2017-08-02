package com.graduation.mygraduation.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.graduation.mygraduation.R;
import com.graduation.mygraduation.entity.TodayOfHistoryBean;

import java.util.List;

/**
 * 历史百科的详情展示adapter
 */

public class TodayAdapter extends BaseQuickAdapter<TodayOfHistoryBean.ResultBean, BaseViewHolder> {

    public TodayAdapter() {
        super(R.layout.item_today);
    }

    @Override
    protected void convert(BaseViewHolder helper, TodayOfHistoryBean.ResultBean item) {
        helper.setText(R.id.tv_today_title, item.getTitle());
        helper.setText(R.id.tv_today_date, item.getDate());
        helper.addOnClickListener(R.id.ll_today_detail);

    }
}
