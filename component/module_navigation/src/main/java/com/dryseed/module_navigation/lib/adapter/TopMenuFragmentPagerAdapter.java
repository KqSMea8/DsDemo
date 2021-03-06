package com.dryseed.module_navigation.lib.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.dryseed.module_navigation.lib.entity.TabEntity;

import java.util.ArrayList;

/**
 * @author caiminming
 */
public abstract class TopMenuFragmentPagerAdapter extends FragmentPagerAdapter implements IPagerAdapter {
    private ArrayList<TabEntity> mData = new ArrayList<>();

    public TopMenuFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public void setData(ArrayList<TabEntity> tabEntities) {
        mData.clear();
        mData.addAll(tabEntities);
        notifyDataSetChanged();
    }

    @Override
    public ArrayList<TabEntity> getData() {
        return mData;
    }
}
