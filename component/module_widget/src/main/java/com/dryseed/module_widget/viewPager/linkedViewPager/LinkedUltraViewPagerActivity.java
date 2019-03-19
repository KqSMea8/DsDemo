package com.dryseed.module_widget.viewPager.linkedViewPager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dryseed.module_widget.R;
import com.dryseed.module_widget.viewPager.ultraviewpager.UltraViewPager;
import com.easy.moduler.lib.utils.LogUtils;

/**
 * 两个ViewPager联动
 *
 * @author caiminming
 */
public class LinkedUltraViewPagerActivity extends AppCompatActivity {

    @BindView(R.id.linked_view_pager)
    UltraViewPager mLinkedViewPager;

    @BindView(R.id.view_pager)
    UltraViewPager mViewPager;

    private LinkedUltraViewPagerAdapter mLinkedAdapter;
    private LinkedUltraViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linked_ultra_view_pager_fragment_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        mLinkedAdapter = new LinkedUltraViewPagerAdapter();
        mLinkedViewPager.setAdapter(mLinkedAdapter);
        mLinkedViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LogUtils.d("mLinkedViewPager [position:%d]", position);
            }

            @Override
            public void onPageSelected(int i) {
                LogUtils.d("mLinkedViewPager onPageSelected [position:%d]", i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mLinkedViewPager.setInfiniteLoop(true);


        mAdapter = new LinkedUltraViewPagerAdapter();
        mViewPager.setAdapter(mAdapter);
        //mViewPager.setOnPageChangeListener(new BaseLinkUltraPageChangeListener(mViewPager, mLinkedViewPager));
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LogUtils.d("mViewPager [position:%d]", position);
                mLinkedViewPager.getViewPager().scrollTo(mViewPager.getViewPager().getScrollX(), 0);
            }

            @Override
            public void onPageSelected(int i) {
                mLinkedViewPager.getViewPager().setCurrentItem(i, false);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mViewPager.setInfiniteLoop(true);


    }

}
