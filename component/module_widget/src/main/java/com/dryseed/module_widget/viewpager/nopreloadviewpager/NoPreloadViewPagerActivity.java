package com.dryseed.module_widget.viewpager.nopreloadviewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dryseed.module_widget.R;
import com.dryseed.module_widget.viewpager.viewpagerfragment.ViewPagerFragmentAdapter;

/**
 * @author caiminming
 */
public class NoPreloadViewPagerActivity extends FragmentActivity {
    @BindView(R.id.view_pager)
    NoPreloadViewPager mViewPager;

    private FragmentPagerAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_fragment_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        mAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new NoPreloadViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    mViewPager.setOffscreenPageLimit(1);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
