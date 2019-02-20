package com.dryseed.module_widget.viewPager.pagerSlidingTabStrip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dryseed.module_widget.R;
import com.easy.moduler.lib.utils.LogUtils;

/**
 * @author caiminming
 */
public class PagerSlidingTabStripActivity extends FragmentActivity {
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.tab_strip)
    PagerSlidingTabStrip mTabStrip;

    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_sliding_tab_strip_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        mAdapter = new PagerSlidingTabStripAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        // 注意：mTabStrip.setViewPager(mViewPager) 方法会覆盖掉mViewPager中设置的OnPageChangeListener
        // 所以如果要设置OnPageChangeListener，请使用mTabStrip.setOnPageChangeListener方法
        mTabStrip.setViewPager(mViewPager);
        mTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                LogUtils.d("mTabStrip onPageSelected", i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
