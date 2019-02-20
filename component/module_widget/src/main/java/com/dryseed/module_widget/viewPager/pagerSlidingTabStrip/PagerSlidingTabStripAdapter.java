package com.dryseed.module_widget.viewPager.pagerSlidingTabStrip;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.dryseed.module_widget.viewPager.BaseFragment;

/**
 * @author caiminming
 */
public class PagerSlidingTabStripAdapter extends FragmentPagerAdapter {

    private final String[] TITLES = {"Cat", "Home", "Top Paid", "Top Free", "Top Grossing", "Top New Paid",
            "Top New Free", "Trending"};

    public PagerSlidingTabStripAdapter(FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new BaseFragment(i);
        return fragment;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }
}
