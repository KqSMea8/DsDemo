package com.dryseed.module_widget.viewPager.fullScreenViewPager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;
import com.dryseed.module_widget.viewPager.BaseFragment;

/**
 * @author caiminming
 */
public class FullScreenViewPagerAdapter extends FragmentStatePagerAdapter {

    private final String[] TITLES = {"Cat", "Home", "Top Paid"};

    public FullScreenViewPagerAdapter(FragmentManager fm) {
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

    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        super.finishUpdate(container);
    }
}
