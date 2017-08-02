package com.graduation.mygraduation.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.google.gson.Gson;
import com.graduation.mygraduation.R;
import com.graduation.mygraduation.entity.BeautyList;
import com.graduation.mygraduation.fragment.BeautyListFragment;
import com.graduation.mygraduation.net.NetCallback;
import com.graduation.mygraduation.net.NetClient;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 展示妹子图片
 * 使用magicindicator+viewpager实现的列表分组展示
 *
 */
public class BeautyGirlActivity extends AppCompatActivity {

    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.beauty_indicator)
    MagicIndicator magicIndicator;
    @Bind(R.id.super_beauty)
    SuperTextView superTextView;
    List<String> titleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty_girl);
        ButterKnife.bind(this);


        superTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        NetClient.getInstance().GetBeautyList(new NetCallback<String>() {

            @Override
            public void onSuccess(String response, int id) {
                if (!TextUtils.isEmpty(response)) {
                    BeautyList list = new Gson().fromJson(response, BeautyList.class);
                    for (int i = 0; i < list.getTngou().size(); i++) {
                        titleList.add(list.getTngou().get(i).getTitle());
                    }
                    init(titleList);
                }
            }

            @Override
            public void onError(Exception e, int id) {
                finish();
                Toast.makeText(BeautyGirlActivity.this, "接口出了问题，该功能暂时不可使用",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void init(final List<String> list) {
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        CommonNavigator navigator = new CommonNavigator(this);
        navigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return list == null ? 0 : list.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int i) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setSelectedColor(Color.WHITE);
                simplePagerTitleView.setText(list.get(i));
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
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setColors(Color.WHITE);
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(navigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    class SimpleFragmentPagerAdapter extends FragmentStatePagerAdapter {

        int[] ids = new int[]{1, 2, 3, 4, 5, 6, 7};

        public SimpleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new BeautyListFragment(ids[position]);
        }

        @Override
        public int getCount() {
            return ids.length;
        }
    }
}
