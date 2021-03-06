package com.dryseed.module_widget.viewPager.linkedViewPager.myViewPager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dryseed.module_widget.R;
import com.dryseed.module_widget.viewPager.linkedViewPager.baseViewPager.LinkedViewPagerAdapter;
import com.dryseed.module_widget.viewPager.ultraviewpager.UltraViewPager;
import com.easy.moduler.lib.utils.LogUtils;

/**
 * 两个ViewPager联动
 *
 * @author caiminming
 */
public class LinkedMyViewPagerActivity extends AppCompatActivity {

    @BindView(R.id.linked_view_pager)
    ViewPager mLinkedViewPager;

    @BindView(R.id.view_pager)
    UltraViewPager mViewPager;

    @BindView(R.id.scroll_bar)
    TextView mScrollBar;

    @BindView(R.id.scroll_txt)
    TextView mScrollTxt;

    private float mLastX;
    private LinkedViewPagerAdapter mLinkedAdapter;
    private LinkedViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linked_my_view_pager_fragment_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        mLinkedAdapter = new LinkedViewPagerAdapter();
        mLinkedViewPager.setAdapter(mLinkedAdapter);
        mLinkedViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //LogUtils.d("mLinkedViewPager [position:%d]", position);
            }

            @Override
            public void onPageSelected(int i) {
                LogUtils.d("mLinkedViewPager onPageSelected [position:%d]", i);
                if (i == 0) {
                    mLinkedViewPager.setCurrentItem(mLinkedAdapter.getCount() - 2, false);
                } else if (i == mLinkedAdapter.getCount() - 1) {
                    mLinkedViewPager.setCurrentItem(1, false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mLinkedViewPager.setOffscreenPageLimit(6);


        mAdapter = new LinkedViewPagerAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int pos;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int width = mLinkedViewPager.getWidth();
                int scrollX = (int) (width * position + width * positionOffset);
                mLinkedViewPager.scrollTo(scrollX, 0);
                //mLinkedViewPager.scrollTo(mViewPager.getViewPager().getScrollX(), 0);
                LogUtils.d("mViewPager [position:%d][scrollX:%d | %d]", position, scrollX, mViewPager.getViewPager().getScrollX());
            }

            @Override
            public void onPageSelected(int position) {
                pos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    mLinkedViewPager.setCurrentItem(pos, false);
                }
            }
        });
        mViewPager.setAutoMeasureHeight(true);
        mViewPager.setInfiniteLoop(true);
        mViewPager.setOffscreenPageLimit(6);
        //mViewPager.setAutoScroll(5000);


        mScrollBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        float dx = x - mLastX;
                        mLinkedViewPager.scrollBy((int) dx * 5, 0);
                        mScrollTxt.setText(mLinkedViewPager.getScrollX() + "");
                        break;
                    default:
                        break;
                }
                mLastX = x;
                return true;
            }
        });
    }

}
