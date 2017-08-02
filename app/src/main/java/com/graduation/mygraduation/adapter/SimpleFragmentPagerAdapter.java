package com.graduation.mygraduation.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.graduation.mygraduation.fragment.NewsDetailsFragment;

/**
 *  主页viewpager的适配器
 */
//
public class SimpleFragmentPagerAdapter extends FragmentStatePagerAdapter {

    String[] types;

    public SimpleFragmentPagerAdapter(FragmentManager fm, String[] types) {

        super(fm);
        this.types = types;
    }

    @Override
    public Fragment getItem(int position) {
        return new NewsDetailsFragment(types[position]);

    }

    @Override
    public int getCount() {
        return types.length;
    }
}
