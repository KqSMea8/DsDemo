package com.dryseed.module_widget.viewPager.test;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import com.dryseed.module_widget.viewPager.advancedPagerSlidingTabStrip.AdvancedPagerSlidingTabStrip;

/**
 * @author caiminming
 */
public class MainPagerSlidingTabStrip extends AdvancedPagerSlidingTabStrip {
    public MainPagerSlidingTabStrip(Context context) {
        super(context);
    }

    public MainPagerSlidingTabStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainPagerSlidingTabStrip(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MainPagerSlidingTabStrip(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void notifyDataSetChanged() {
        // 不同点
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTabsContainer.removeAllViews();
                mTabCount = mPager.getAdapter().getCount();
                PagerAdapter adapter = mPager.getAdapter();
                for (int i = 0; i < mTabCount; i++) {
                    if (mIsCustomTab && mCustomTabProvider != null) {
                        View view = mCustomTabProvider.createTabView(i);
                        if (view != null) {
                            addTab(i, view);
                        }
                    } else if (adapter instanceof IconTabProvider) {
                        addIconTab(i, ((IconTabProvider) adapter).getPageIconResId(i));
                    } else {
                        addTextTab(i, String.valueOf(adapter.getPageTitle(i)));
                    }
                }

                updateTabStyles();

                if (mPager == null) {
                    return;
                }
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    //noinspection deprecation
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                updateInGlobalLayoutListener();
            }
        });
    }

}
