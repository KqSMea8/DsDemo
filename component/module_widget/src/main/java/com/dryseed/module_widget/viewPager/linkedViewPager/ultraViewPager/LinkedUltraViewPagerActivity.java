package com.dryseed.module_widget.viewPager.linkedViewPager.ultraViewPager;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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
public class LinkedUltraViewPagerActivity extends AppCompatActivity {

    @BindView(R.id.linked_view_pager)
    UltraViewPager mLinkedViewPager;

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
        setContentView(R.layout.activity_linked_ultra_view_pager_fragment_layout);
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
                Drawable drawable = mScrollTxt.getBackground();
                if (drawable instanceof ColorDrawable) {
                    int color = ((ColorDrawable) drawable).getColor();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mLinkedViewPager.setAutoMeasureHeight(true);
        mLinkedViewPager.setInfiniteLoop(true);
        mLinkedViewPager.setOffscreenPageLimit(4);


        mAdapter = new LinkedViewPagerAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int pos;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int width = mLinkedViewPager.getViewPager().getWidth();
                int scrollX = (int) (width * position + width * positionOffset);
                mLinkedViewPager.getViewPager().scrollTo(scrollX, 0);

                //mLinkedViewPager.getViewPager().scrollTo(mViewPager.getViewPager().getScrollX(), 0);

                LogUtils.d("mViewPager [position:%d][scrollX:%d | %d]", position, scrollX, mViewPager.getViewPager().getScrollX());
            }

            @Override
            public void onPageSelected(int position) {
                pos = position;
                LogUtils.d("mViewPager onPageSelected : %d", position);
                Drawable drawable = mScrollBar.getBackground();
                if (drawable instanceof ColorDrawable) {
                    int color = ((ColorDrawable) drawable).getColor();
                    String hexColor = String.format("#%06X", (0xFFFFFF & color));

                    //电影-更多专题 #2E2E30
                    String movieColor = String.format("#%06X", (0xFFFFFF & -13750736));
                    //电影-查看完整榜单 #2E2E30
                    String movieColor2 = String.format("#%06X", (0xFFFFFF & 3026480));

                    LogUtils.d("hexColor : %s | %s | %s", hexColor, movieColor, movieColor2);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    mLinkedViewPager.getViewPager().setCurrentItem(pos, false);
                }
            }
        });
        mViewPager.setAutoMeasureHeight(true);
        mViewPager.setInfiniteLoop(true);
        mViewPager.setOffscreenPageLimit(6);

        mScrollBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        float dx = x - mLastX;
                        mLinkedViewPager.getViewPager().scrollBy((int) dx * 5, 0);
                        mScrollTxt.setText(mLinkedViewPager.getViewPager().getScrollX() + "");
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
