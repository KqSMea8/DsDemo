package com.dryseed.module_widget.viewPager.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.easy.moduler.lib.utils.LogUtils;

/**
 * @author caiminming
 */
public class TestWeightActivity extends Activity {
    private final String[] TITLES = {"Cat", "Home", "Top Paid", "Top Free", "Top Grossing", "Top New Paid",
            "Top New Free", "Trending"};
    private LinearLayout mTabsContainer;

    private HorizontalScrollView mHorizontalScrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        test();
    }

    private void test() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                reset();
            }
        }, 1000);


        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mHorizontalScrollView.scrollTo(DPIUtil.dip2px(200), 0);
            }
        }, 1000);*/

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mHorizontalScrollView.scrollTo(DPIUtil.dip2px(400), 0);
            }
        }, 2000);*/
    }

    private void initView() {
        mHorizontalScrollView = new HorizontalScrollView(this);
        mHorizontalScrollView.setFillViewport(true);
        setContentView(mHorizontalScrollView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        mTabsContainer = new LinearLayout(this);
        mTabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        mTabsContainer.setLayoutParams(
                new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                )
        );
        mHorizontalScrollView.addView(mTabsContainer);

        for (int i = 0; i < TITLES.length; i++) {
            TextView tab = new TextView(this);
            tab.setText(TITLES[i]);
            tab.setGravity(Gravity.CENTER);
            tab.setSingleLine();
            addTab(i, tab);
        }
    }

    private void reset() {
        mTabsContainer.removeAllViews();
        for (int i = 0; i < TITLES.length; i++) {
            TextView tab = new TextView(this);
            tab.setText(TITLES[i]);
            tab.setGravity(Gravity.CENTER);
            tab.setSingleLine();
            addTab(i, tab);

            // addView之后，因为没有layout，所以获取到的getLeft都为0。
            LogUtils.d("left 111 : " + tab.getLeft());
            // View.post，如果view还没有attachedToWindow，则会在dispatchAttachedToWindow()被回调
            tab.post(new Runnable() {
                @Override
                public void run() {
                    LogUtils.d("left 222 : " + tab.getLeft());
                }
            });
        }
    }

    private void addTab(final int position, View tab) {
        tab.setFocusable(true);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(0, FrameLayout.LayoutParams.MATCH_PARENT, 1.0f);
        mTabsContainer.addView(tab, position, llp);
    }
}
