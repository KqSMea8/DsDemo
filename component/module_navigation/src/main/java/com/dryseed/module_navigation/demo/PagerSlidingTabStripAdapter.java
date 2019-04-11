package com.dryseed.module_navigation.demo;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.dryseed.module_navigation.lib.adapter.TopMenuFragmentStatePagerAdapter;

/**
 * @author caiminming
 */
public class PagerSlidingTabStripAdapter extends TopMenuFragmentStatePagerAdapter {

    public PagerSlidingTabStripAdapter(FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return getData().get(position).getTitle();
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new BaseFragment(i);
        return fragment;
    }

    @Override
    public int getCount() {
        return getData().size();
    }
}
