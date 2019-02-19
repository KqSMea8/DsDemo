package com.dryseed.module_widget.viewpager.viewpagerfragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.dryseed.module_widget.viewpager.BaseFragment;

/**
 * @author caiminming
 */
public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {
    public ViewPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new BaseFragment(i);
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
