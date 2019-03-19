/*
 *
 *  MIT License
 *
 *  Copyright (c) 2017 Alibaba Group
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 *
 */

package com.dryseed.module_widget.viewPager.ultraviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 * Created by mikeafc on 15/11/25.
 */
public class UltraViewPagerView extends ViewPager implements UltraViewPagerAdapter.IUltraViewPagerCenterListener {
    private static final String TAG = UltraViewPagerView.class.getSimpleName();

    private UltraViewPagerAdapter pagerAdapter;

    private PageTransformer mPageTransformer;

    /**
     * Internal state to schedule a new measurement pass.
     */
    private boolean needsMeasurePage;

    private boolean enableLoop;
    private boolean autoMeasureHeight;
    private int cachedHeightSpec;

    private int itemMarginLeft;
    private int itemMarginTop;
    private int itemMarginRight;
    private int itemMarginBottom;

    private float mPageRatio = Float.NaN;

    public UltraViewPagerView(Context context) {
        super(context);
        init(context, null);
    }

    public UltraViewPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @SuppressWarnings("unused")
    private void init(Context context, AttributeSet attrs) {
        setClipChildren(false);
        setOverScrollMode(OVER_SCROLL_NEVER);

        try {
            Field fieldMinimumVelocity = ViewPager.class.getDeclaredField("mMinimumVelocity");
            fieldMinimumVelocity.setAccessible(true);
            fieldMinimumVelocity.set(this, (int) (fieldMinimumVelocity.get(this)) / 3);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        onMeasurePage(widthMeasureSpec, heightMeasureSpec);
    }

    private void handleChildPadding(View child) {
        if (child == null) {
            return;
        }

        if ((child.getPaddingLeft() != itemMarginLeft || child.getPaddingTop() != itemMarginTop ||
                child.getPaddingRight() != itemMarginRight || child.getPaddingBottom() != itemMarginBottom)) {
            child.setPadding(itemMarginLeft, itemMarginTop, itemMarginRight, itemMarginBottom);
        }
    }

    protected void onMeasurePage(int widthMeasureSpec, int heightMeasureSpec) {
        if (pagerAdapter == null) {
            return;
        }

        View child = pagerAdapter.getViewAtPosition(getCurrentItem());
        if (child == null) {
            child = getChildAt(0);
        }
        if (child == null) {
            return;
        }
        for (int i = 0; i < getChildCount(); i++) {
            handleChildPadding(getChildAt(i));
        }

        ViewGroup.LayoutParams lp = child.getLayoutParams();
        final int childWidthSpec = getChildMeasureSpec(widthMeasureSpec, 0, lp.width);
        final int childHeightSpec = getChildMeasureSpec(heightMeasureSpec, 0, lp.height);

        int childWidth = (int) ((MeasureSpec.getSize(childWidthSpec) - getPaddingLeft() - getPaddingRight())
                * pagerAdapter.getPageWidth(getCurrentItem()));
        int childHeight = MeasureSpec.getSize(childHeightSpec) - getPaddingTop() - getPaddingBottom();

        boolean useCachedHeight = ((!needsMeasurePage && childHeight != 0) || (childWidth == 0 && childHeight == 0))
                || cachedHeightSpec > 0;
        if (useCachedHeight) {
            // As a RelativeLayout, UltraViewPager measures its child (UltraViewPagerView) twice
            // So we still need to setMeasureDimension using cached length during the second measure pass.
            if (cachedHeightSpec > 0) {
                needsMeasurePage = false;
                measureChildren(childWidthSpec, cachedHeightSpec);
                setMeasuredDimension(getMeasuredWidth(), MeasureSpec.getSize(cachedHeightSpec));
            }
            return;
        }

        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            View view = getChildAt(i);
            if (Float.compare(pagerAdapter.getPageWidth(getCurrentItem()), 1) != 0) {
                view.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            } else {
                view.measure(childWidthSpec, childHeightSpec);
            }
        }

        childHeight = itemMarginTop + child.getMeasuredHeight() + itemMarginBottom;

        if (!Float.isNaN(mPageRatio)) {
            int height = (int) (getMeasuredWidth() / mPageRatio);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
            setMeasuredDimension(getMeasuredWidth(), height);
            for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
                View view = getChildAt(i);
                view.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
            }
        } else {
            if (autoMeasureHeight) {
                cachedHeightSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
                setMeasuredDimension(getMeasuredWidth(), childHeight);
                needsMeasurePage = childHeight == itemMarginTop + itemMarginBottom;
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction() & MotionEvent.ACTION_MASK;

        // 由于在FakeDragging过程中调用resetTouch方法会导致后续的fakeDrag崩溃
        // （velocityTracker被释放），此处禁止正在fakeDrag时做处理
        if (action != MotionEvent.ACTION_CANCEL && action != MotionEvent.ACTION_UP) {
            return super.onInterceptTouchEvent(ev);
        }
        // 当且仅当没有进行中的FakeDragging时处理
        return !isFakeDragging() && super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // Schedule a new measurement pass as the dimensions have changed
        needsMeasurePage = true;
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        if (adapter == null) {
            super.setAdapter(null);
            return;
        }

        if (adapter instanceof UltraViewPagerAdapter) {
            pagerAdapter = (UltraViewPagerAdapter) adapter;
        } else {
            pagerAdapter = new UltraViewPagerAdapter(adapter);
        }

        pagerAdapter.setCenterListener(this);
        pagerAdapter.setEnableLoop(enableLoop);
        needsMeasurePage = true;
        cachedHeightSpec = 0;
        super.setAdapter(pagerAdapter);
    }

    int getItemPositionInLoop(int realPosition) {
        if (pagerAdapter != null && pagerAdapter.getCount() != 0 && pagerAdapter.isEnableLoop()) {
            realPosition = pagerAdapter.getCount() / 2 + realPosition % pagerAdapter.getRealCount();
        }
        return realPosition;
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item, false);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(getItemPositionInLoop(item), smoothScroll);
    }

    @Override
    public int getCurrentItem() {
        if (pagerAdapter != null && pagerAdapter.getCount() != 0) {
            int position = super.getCurrentItem();
            return position % pagerAdapter.getRealCount();
        }
        return super.getCurrentItem();
    }

    /**
     * Set the currently selected page.
     *
     * @param item         Item index to select
     * @param smoothScroll True to smoothly scroll to the new item, false to transition immediately
     */
    void setCurrentItemFake(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    /**
     * Get the currently selected page.
     */
    int getCurrentItemFake() {
        return super.getCurrentItem();
    }

    public void setEnableLoop(boolean status) {
        if (enableLoop == status) {
            // Unchanged.
            return;
        }

        enableLoop = status;
        if (pagerAdapter != null) {
            pagerAdapter.setEnableLoop(enableLoop);
        }
    }

    public void setAutoMeasureHeight(boolean autoMeasureHeight) {
        this.autoMeasureHeight = autoMeasureHeight;
    }

    int getCachedHeightSpec() {
        return cachedHeightSpec;
    }

    public void setItemMargin(int left, int top, int right, int bottom) {
        itemMarginLeft = left;
        itemMarginTop = top;
        itemMarginRight = right;
        itemMarginBottom = bottom;
    }

    public void setPageRatio(float pageRatio) {
        mPageRatio = pageRatio;
    }

    @Override
    public void center() {
        setCurrentItem(0);
    }

    @Override
    public void resetPosition() {
        setCurrentItem(getCurrentItem());
    }

    @Override
    public void setPageTransformer(boolean reverseDrawingOrder, PageTransformer transformer, int pageLayerType) {
        mPageTransformer = transformer;
        super.setPageTransformer(reverseDrawingOrder, transformer, pageLayerType);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        // 部分Android 8.0设备在ViewPager构建子View时如果先addView()后调用子View的setOnClickListener()、
        // setOnLongClickListener()、setVisibility()等使用了View.setFlags()的方法，会提前触发一次
        // getChildDrawingOrder()，而此时ViewPager内部维护绘制顺序的View列表尚未更新，此List中元素个数比
        // getChildCount()少1，遍历时出现数组越界
        // ===
        // 外部可通过避免上述调用方式的方案解决问题
        // 在此处统一做try-catch保护
        // ===
        // http://pms.qiyi.domain/browse/APMQIYI-1841
        try {
            return super.getChildDrawingOrder(childCount, i);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return i;
    }

    void updateTransforming() {
        if (mPageTransformer != null) {
            final int scrollX = getScrollX();
            final int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = getChildAt(i);
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();

                if (lp.isDecor) {
                    continue;
                }
                final float transformPos = (float) (child.getLeft() - scrollX) / getClientWidthCopied();
                mPageTransformer.transformPage(child, transformPos);
            }
        }
    }

    private int getClientWidthCopied() {
        return getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    public PageTransformer getTransformer() {
        return mPageTransformer;
    }
}
