package com.graduation.mygraduation.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.utils.ImageUtils;
import com.blankj.utilcode.utils.NetworkUtils;
import com.blankj.utilcode.utils.SDCardUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.graduation.mygraduation.R;
import com.graduation.mygraduation.activity.MainActivity;
import com.graduation.mygraduation.adapter.SimpleFragmentPagerAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 新闻数据展示 fragment
 */
public class NewsFragment extends Fragment {

    @Bind(R.id.magic_indicator)
    MagicIndicator indicator; //顶部选择栏
    @Bind(R.id.viewPager)
    ViewPager viewPager;//viewpager
    private String[] type;   //英文数字 获取数据传递
    private String[] typesCN;       //顶部 tab 中文内容数组
    View view;
    SimpleFragmentPagerAdapter simpleFragmentPagerAdapter;

    public NewsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    //初始化indicator   viewpager
    private void init() {
        type = getResources().getStringArray(R.array.news_type);
        typesCN = getResources().getStringArray(R.array.news_type_cn);
        simpleFragmentPagerAdapter = new SimpleFragmentPagerAdapter(getActivity().getSupportFragmentManager(), type);
        viewPager.setAdapter(simpleFragmentPagerAdapter);
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return typesCN == null ? 0 : typesCN.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int i) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(typesCN[i]);
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#e94220"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(i);
                    }
                });

                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setColors(Color.BLUE);
                return indicator;
            }
        });
        indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(indicator, viewPager);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
