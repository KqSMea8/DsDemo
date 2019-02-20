package com.dryseed.module_widget.viewPager.advancedPagerSlidingTabStrip;

import android.os.Bundle;
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
public class AdvancedPagerSlidingTabStripActivity extends FragmentActivity {
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.tab_strip)
    AdvancedPagerSlidingTabStrip mTabStrip;

    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_pager_sliding_tab_strip_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        mAdapter = new PagerSlidingTabStripAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTabStrip.setViewPager(mViewPager);
    }
}
