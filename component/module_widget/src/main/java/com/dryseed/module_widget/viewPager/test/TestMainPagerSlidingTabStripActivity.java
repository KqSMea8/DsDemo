package com.dryseed.module_widget.viewPager.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dryseed.module_widget.R;
import com.dryseed.module_widget.viewPager.pagerSlidingTabStrip.PagerSlidingTabStripAdapter;
import com.easy.moduler.lib.utils.LogUtils;

/**
 * @author caiminming
 */
public class TestMainPagerSlidingTabStripActivity extends FragmentActivity {
    @BindView(R.id.text)
    TextView mResetBtn;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.tab_strip)
    MainPagerSlidingTabStrip mTabStrip;

    private FragmentPagerAdapter mAdapter;

    private int showTimes = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pager_sliding_tab_strip_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        LogUtils.d("initViews");
        mAdapter = new PagerSlidingTabStripAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTabStrip.setViewPager(mViewPager);
        mViewPager.setCurrentItem(5);

        mResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter = new PagerSlidingTabStripAdapter(getSupportFragmentManager());
                mViewPager.setAdapter(mAdapter);
                //trigger notifyDataSetChanged()
                mTabStrip.setViewPager(mViewPager);
                mViewPager.setCurrentItem(7);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.d("onResume");

        if (++showTimes > 1) {
            mResetBtn.performClick();
        }
    }
}
