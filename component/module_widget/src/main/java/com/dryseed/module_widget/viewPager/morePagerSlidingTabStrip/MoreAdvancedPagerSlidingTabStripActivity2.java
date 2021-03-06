package com.dryseed.module_widget.viewPager.morePagerSlidingTabStrip;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dryseed.module_widget.R;
import com.dryseed.module_widget.viewPager.pagerSlidingTabStrip.PagerSlidingTabStripAdapter;

/**
 * @author caiminming
 */
public class MoreAdvancedPagerSlidingTabStripActivity2 extends FragmentActivity {
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.tab_strip)
    MoreAdvancedPagerSlidingTabStrip2 mTabStrip;

    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_advanced_pager_sliding_tab_strip_layout2);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        mAdapter = new PagerSlidingTabStripAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTabStrip.setViewPager(mViewPager);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewPager.setCurrentItem(mAdapter.getCount() - 1);
                mTabStrip.resetMoreView(0);
            }
        }, 1000);
    }
}
