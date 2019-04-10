package com.dryseed.module_navigation.lib.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.dryseed.module_navigation.lib.entity.TopMenuTabEntity;

import java.util.ArrayList;

/**
 * @author caiminming
 */
public abstract class TopMenuPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<TopMenuTabEntity> mData = new ArrayList<>();

    public TopMenuPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(ArrayList<TopMenuTabEntity> tabEntities) {
        mData.clear();
        mData.addAll(tabEntities);
        notifyDataSetChanged();
    }

    public ArrayList<TopMenuTabEntity> getData() {
        return mData;
    }
}
