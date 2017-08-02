package com.graduation.mygraduation.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.graduation.mygraduation.R;
import com.graduation.mygraduation.commons.Constants;
import com.graduation.mygraduation.entity.BeautyPhoList;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 展示妹子图片adapter
 */

public class BeautyListAdapter extends BaseQuickAdapter<BeautyPhoList.tngou, BaseViewHolder> {

    public BeautyListAdapter() {
        super(R.layout.beauty_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, BeautyPhoList.tngou item) {
//        helper.setText(R.id.text_beauty_list, item.getTitle());
        Glide.with(mContext)
//                .load(Constants.URL_IMGIP + item.getImg()+"_180*120")
                .load(Constants.URL_IMGIP + item.getImg())
                .placeholder(R.drawable.his)
                .error(R.mipmap.ic_error)
                .into((ImageView) helper.getView(R.id.img_beauty_list));
//        Picasso.with(mContext)
//                .load(Constants.URL_IMGIP + item.getImg())
//                .placeholder(R.drawable.beauty)
//                .error(R.mipmap.ic_error)
//                .into((ImageView) helper.getView(R.id.img_beauty_list));

        helper.addOnClickListener(R.id.rea_beauty);
    }
}
