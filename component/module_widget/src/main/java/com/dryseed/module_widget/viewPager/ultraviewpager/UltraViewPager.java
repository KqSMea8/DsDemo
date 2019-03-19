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

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import com.dryseed.module_widget.R;
import com.dryseed.module_widget.viewPager.ultraviewpager.transformer.IBaseTransformer;
import com.easy.moduler.lib.utils.DPIUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mikeafc on 15/10/26.<br>
 * UltraViewPager is a super extension for ViewPager.<br>
 * It's actually a RelativeLayout in order to display indicator, UltraViewPager offers some usual
 * method delegate for ViewPager, you can also invoke more method by call getViewPager() and get the actual
 * ViewPager.
 * <p>
 * <p>
 * UltraViewPager修改自 https://github.com/alibaba/UltraViewPager <br />
 * 主要保留的feature有：自动轮播、无限循环、可配置Indicator<br />
 * </p>
 */
public class UltraViewPager extends RelativeLayout implements IUltraViewPagerFeature {
    private static final String TAG = UltraViewPager.class.getSimpleName();

    /**
     * 控件可见的阈值
     */
    private static final int DEFAULT_VISIBLE_THRESHOLD_PX = 10;

    private static final int DEFAULT_AUTO_SCROLL_DURATION = 800;
    private static final int DEFAULT_AUTO_SCROLL_INTERVAL = 7000;
    private int mIntervalInMillis = 7000;

    private UltraViewPagerView viewPager;
    private UltraViewPagerIndicator pagerIndicator;
    private UltraViewPagerAdapter viewPagerAdapter;

    private DelegateOnPageChangeListener mDelegateOnPageChangeListener = new DelegateOnPageChangeListener();
    private List<ViewPager.OnPageChangeListener> mOnPageChangeListeners = new ArrayList<>(2);

    private TimerHandler timer;
    private TimerHandler.ITimerCallback mITimerCallback = new TimerHandler.ITimerCallback() {
        @Override
        public void callBack() {
            scrollNextPage();
        }
    };

    /**
     * 在某些特殊场景下，自动轮播被暂时关闭（如数据源无数据）
     */
    private boolean mIsAutoScrollPaused;
    private ValueAnimator mAutoScrollAnimator;
    private AutoScrollAnimatorUpdateListener mAnimatorUpdateListener = new AutoScrollAnimatorUpdateListener();
    private int mAutoScrollAnimatorEndValue = 0;
    private int mAutoScrollDuration = 0;
    private int mAutoScrollInterval = 0;

    private int mLastX;
    private int mLastY;

    /**
     * For view bounds detection;
     */
    private Rect mRect = new Rect();
    private int mScreenWidth;

    public UltraViewPager(Context context) {
        super(context);
        initView(context, null);
    }

    public UltraViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public UltraViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        mScreenWidth = DPIUtil.getWidth();
        viewPager = new UltraViewPagerView(getContext());
        addView(viewPager, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        viewPager.removeOnPageChangeListener(mDelegateOnPageChangeListener);
        viewPager.addOnPageChangeListener(mDelegateOnPageChangeListener);

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.UltraViewPager);
            setAutoScroll(ta.getInt(R.styleable.UltraViewPager_upv_autoscroll, 0));
            setInfiniteLoop(ta.getBoolean(R.styleable.UltraViewPager_upv_infiniteloop, false));
            setAutoMeasureHeight(ta.getBoolean(R.styleable.UltraViewPager_upv_automeasure, false));
            ta.recycle();
        }
        viewPager.setId(R.id.ultraviewpager);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (viewPager.getCachedHeightSpec() > 0) {
            if (viewPager.getCachedHeightSpec() == heightMeasureSpec) {
                viewPager.measure(widthMeasureSpec, heightMeasureSpec);
                setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                        plusIndicatorHeight(MeasureSpec.getSize(heightMeasureSpec)));
            } else {
                int constrainLengthSize = MeasureSpec.getSize(viewPager.getCachedHeightSpec());
                int constrainLengthMode = MeasureSpec.getMode(viewPager.getCachedHeightSpec());
                super.onMeasure(widthMeasureSpec,
                        MeasureSpec.makeMeasureSpec(plusIndicatorHeight(constrainLengthSize), constrainLengthMode));
            }
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int expectedHeight = plusIndicatorHeight(viewPager.getMeasuredHeight());
            if (getMeasuredHeight() < expectedHeight) {
                setMeasuredDimension(getMeasuredWidth(), expectedHeight);
            }
        }

        // 更新自动滑动的Animator起止范围
        int scrollEnd = computeAnimatorEndValue();
        if (scrollEnd != mAutoScrollAnimatorEndValue && mAutoScrollAnimator != null) {
            mAutoScrollAnimatorEndValue = scrollEnd;
            mAutoScrollAnimator.setIntValues(0, mAutoScrollAnimatorEndValue);
        }
    }

    private int plusIndicatorHeight(int originHeight) {
        if (pagerIndicator != null && pagerIndicator.isOutside()) {
            return originHeight + pagerIndicator.getMeasuredHeight() + pagerIndicator.getVerticalOffset();
        }
        return originHeight;
    }

    private int computeAnimatorEndValue() {
        return viewPager.getMeasuredWidth() - viewPager.getPaddingLeft() + (viewPager.getPageMargin());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startTimer();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopTimer();
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            startTimer();
        } else {
            stopTimer();
        }
    }

    @Override
    public void onStartTemporaryDetach() {
        super.onStartTemporaryDetach();
        stopTimer();
    }

    @Override
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        startTimer();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                if (timer != null) {
                    stopTimer();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                if (Math.abs(deltaY) > Math.abs(deltaX)) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (timer != null) {
                    startTimer();
                }
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            final float offsetX = getScrollX() - viewPager.getLeft();
            final float offsetY = getScrollY() - viewPager.getTop();
            event.offsetLocation(offsetX, offsetY);
            boolean result = viewPager.dispatchTouchEvent(event);
            event.offsetLocation(-offsetX, -offsetY);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewPager.dispatchTouchEvent(event);
    }

    @Override
    public IUltraIndicatorBuilder initIndicator() {
        disableIndicator();
        pagerIndicator = new UltraViewPagerIndicator(getContext());
        pagerIndicator.setViewPager(this);
        pagerIndicator.setIndicatorBuildListener(new UltraViewPagerIndicator.IViewPagerIndicatorListener() {
            @Override
            public void build() {
                pagerIndicator.attachToUltraViewPager(UltraViewPager.this);
            }
        });
        return pagerIndicator;
    }

    @Override
    public void disableIndicator() {
        if (pagerIndicator != null) {
            removeView(pagerIndicator);
            pagerIndicator = null;
        }
    }

    public IUltraIndicatorBuilder getIndicator() {
        return pagerIndicator;
    }

    @Override
    public void setAutoScroll(int intervalInMillis) {
        mIntervalInMillis = intervalInMillis;
        setAutoScroll(intervalInMillis, DEFAULT_AUTO_SCROLL_DURATION);
    }

    @Override
    public void setAutoScroll(int intervalInMillis, int scrollDurationInMillis) {
        if (0 < intervalInMillis && 0 < scrollDurationInMillis) {
            if (timer != null) {
                disableAutoScroll();
            }
            initAutoScrollAnimator();
            if (mAutoScrollDuration != scrollDurationInMillis) {
                mAutoScrollDuration = scrollDurationInMillis;
                mAutoScrollAnimator.setDuration(mAutoScrollDuration);
            }

            mAutoScrollInterval = intervalInMillis;
            timer = new TimerHandler(mITimerCallback, intervalInMillis);
            startTimer();
        } else {
            stopCurrentScrollAnimation();
        }
    }

    public void stopCurrentScrollAnimation() {
        if (mAutoScrollAnimator != null) {
            mAutoScrollAnimator.cancel();
        }
    }

    private void onAnimatorEnd() {
        if (viewPagerAdapter != null && viewPagerAdapter.getRealCount() > 0) {
            if (viewPager.isFakeDragging()) {
                viewPager.endFakeDrag();
            }
        }
        mAnimatorUpdateListener.reset();
    }

    private void initAutoScrollAnimator() {
        if (mAutoScrollAnimator == null) {
            if (mAutoScrollAnimatorEndValue == 0) {
                mAutoScrollAnimatorEndValue = computeAnimatorEndValue();
            }
            mAutoScrollAnimator = ValueAnimator.ofInt(0, mAutoScrollAnimatorEndValue);
            mAutoScrollAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    // Unnecessary
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    onAnimatorEnd();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    onAnimatorEnd();
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    // Unnecessary
                }
            });

            mAutoScrollAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            mAutoScrollAnimator.addUpdateListener(mAnimatorUpdateListener);
            mAutoScrollAnimator.setDuration(mAutoScrollDuration);
        }
    }

    @Override
    public void disableAutoScroll() {
        stopTimer();
        timer = null;
    }

    @Override
    public void resumeAutoScroll() {
        setAutoScroll(mIntervalInMillis);
    }

    @Override
    public void pauseAutoScroll() {
        disableAutoScroll();
    }

    public boolean isAutoScrollEnabled() {
        return timer != null;
    }

    @Override
    public void setInfiniteLoop(boolean enableLoop) {
        viewPager.setEnableLoop(enableLoop);
    }

    @Override
    public boolean isInfiniteLoop() {
        return viewPagerAdapter != null && viewPagerAdapter.isEnableLoop();
    }

    /**
     * 设置page间距
     *
     * @param marginPixels
     */
    @Override
    public void setPageMargin(int marginPixels) {
        viewPager.setPageMargin(marginPixels);
    }

    @Override
    public void scrollNextPage() {
        if (viewPager != null && viewPager.getAdapter() != null
                && viewPager.getAdapter().getCount() > 0
                && viewPager.getChildCount() > 0) {
            animatePagerTransition();
        }
    }

    @Override
    public void setAutoMeasureHeight(boolean status) {
        viewPager.setAutoMeasureHeight(status);
    }

    @Override
    public void setItemMargin(int left, int top, int right, int bottom) {
        viewPager.setItemMargin(left, top, right, bottom);
    }

    @Override
    public void setScrollMargin(int left, int right) {
        viewPager.setPadding(left, 0, right, 0);
    }

    /**
     * 注意: 该接口会清除全部之前已设置的OnPageChangeListener，
     * 推荐使用{@link #addOnPageChangeListener(ViewPager.OnPageChangeListener)}
     *
     * @see #addOnPageChangeListener(ViewPager.OnPageChangeListener)
     * @see #removeOnPageChangeListener(ViewPager.OnPageChangeListener)
     * @see #clearOnPageChangeListeners()
     */
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        if (listener instanceof UltraViewPagerIndicator) {
            return;
        }
        clearOnPageChangeListeners();
        addOnPageChangeListener(listener);
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        if (listener instanceof UltraViewPagerIndicator) {
            return;
        }

        if (listener != null) {
            mOnPageChangeListeners.remove(listener);
            mOnPageChangeListeners.add(listener);
        }
    }

    public void removeOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        if (listener != null) {
            mOnPageChangeListeners.remove(listener);
        }
    }

    public void clearOnPageChangeListeners() {
        mOnPageChangeListeners.clear();
    }

    int getItemPositionInLoop(int realPosition) {
        return viewPager.getItemPositionInLoop(realPosition);
    }

    /**
     * delegate viewpager
     */
    public void setAdapter(PagerAdapter adapter) {
        // Stop running animator
        stopCurrentScrollAnimation();

        if (adapter == null || adapter.getCount() <= 0) {
            // 没有数据时，禁用自动轮播
            disableAutoScroll();
            mIsAutoScrollPaused = true;
        }

        viewPager.setAdapter(adapter);
        if (adapter != null) {
            viewPagerAdapter = (UltraViewPagerAdapter) viewPager.getAdapter();

            //关联ViewPager和Adapter，更新数据时取消动画
            viewPagerAdapter.setViewPager(this);
            if (mIsAutoScrollPaused && adapter.getCount() > 0) {
                setAutoScroll(mAutoScrollInterval, mAutoScrollDuration);
                mIsAutoScrollPaused = false;
            }
        } else {
            viewPagerAdapter = null;
        }
    }

    public void setOffscreenPageLimit(int limit) {
        viewPager.setOffscreenPageLimit(limit);
    }

    public PagerAdapter getAdapter() {
        return viewPager.getAdapter() == null ? null : ((UltraViewPagerAdapter) viewPager.getAdapter()).getAdapter();
    }

    public void setCurrentItem(int item) {
        viewPager.setCurrentItem(item);
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        viewPager.setCurrentItem(item, smoothScroll);
    }

    public int getCurrentItem() {
        return viewPager.getCurrentItem();
    }

    public void setPageTransformer(boolean reverseDrawingOrder, IBaseTransformer transformer) {
        viewPager.setPageTransformer(reverseDrawingOrder, transformer);
    }

    @Override
    public void setClipChildren(boolean clipChildren) {
        super.setClipChildren(clipChildren);
        viewPager.setClipChildren(clipChildren);
    }

    @Override
    public void setClipToPadding(boolean clipToPadding) {
        super.setClipToPadding(clipToPadding);
        viewPager.setClipToPadding(clipToPadding);
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    private void startTimer() {
        if (timer != null) {
            timer.setListener(mITimerCallback);
            timer.trigger();
        }
    }

    private void stopTimer() {
        if (timer != null) {
            timer.setListener(null);
            timer.stop();
        }
    }

    @Override
    public void setInfiniteRatio(int infiniteRatio) {
        if (viewPager.getAdapter() != null
                && viewPager.getAdapter() instanceof UltraViewPagerAdapter) {
            ((UltraViewPagerAdapter) viewPager.getAdapter()).setInfiniteRatio(infiniteRatio);
        }
    }

    /**
     * 判断当前焦点图是否在可见区域，不在不滚动，
     *
     * @deprecated 由使用方自行决定如何判断是否可见
     */
    @Deprecated
    public boolean isVisible() {
        boolean ret = true;
        mRect.set(0, 0, 0, 0);
        try {
            getGlobalVisibleRect(mRect);
            if (mRect.left >= mScreenWidth - DEFAULT_VISIBLE_THRESHOLD_PX || mRect.right <= DEFAULT_VISIBLE_THRESHOLD_PX) {
                ret = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 由于使用Wrap方式封装了开发者传入的Adapter，使用开发者自己持有的Adapter实例无法正确
     * 调用notifyDataSetChanged()更新数据源，如果需要notifyDataSetChange，请在更新数据后，
     * 使用这个方法返回的Adapter进行notifyDataSetChange, 或者直接使用
     * {@code UltraViewPager.notifyDataSetChanged()}
     */
    @Override
    public PagerAdapter getInternalAdapter() {
        return viewPager.getAdapter();
    }

    public void notifyDataSetChanged() {
        if (viewPager.getAdapter() != null) {
            viewPager.getAdapter().notifyDataSetChanged();
        }
    }

    public void updateIndicator() {
        if (pagerIndicator != null) {
            pagerIndicator.requestLayout();
        }
    }

    public void animatePagerTransition() {
        if (mAutoScrollAnimator != null && !mAutoScrollAnimator.isRunning()) {
            if (viewPager.beginFakeDrag()) {
                mAutoScrollAnimator.start();
            }
        }
    }

    /**
     * 在无法确定page中图片大小时，可以通过配置宽高比，得到正确的图片高度
     * 配置后会忽略{@link #setAutoMeasureHeight(boolean)} 和 page layout中配置的图片高宽
     * Note：{@code widthFactor} {@code heightFactor} 必须大于0
     *
     * @since 9.2.0
     */
    @Override
    public void setPageRatio(int widthFactor, int heightFactor) {
        if (widthFactor == 0 || heightFactor == 0) {
            viewPager.setPageRatio(Float.NaN);
        } else {
            viewPager.setPageRatio((float) widthFactor / (float) heightFactor);
        }
    }

    @Override
    public void updateTransforming() {
        viewPager.updateTransforming();
    }

    public ViewPager.PageTransformer getTransformer() {
        return viewPager.getTransformer();
    }

    private class AutoScrollAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {
        private int oldDragPosition = 0;

        void reset() {
            oldDragPosition = 0;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            int dragPosition = (Integer) animation.getAnimatedValue();
            int dragOffset = dragPosition - oldDragPosition;
            oldDragPosition = dragPosition;
            if (viewPager.getChildCount() > 0) {
                viewPager.fakeDragBy(-dragOffset);
            }
        }
    }

    /**
     * A delegate <code>OnPageChangeListener</code> to make sure all positions are converted properly
     */
    private class DelegateOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private boolean check() {
            return viewPagerAdapter != null &&
                    (!mOnPageChangeListeners.isEmpty() || pagerIndicator != null);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (check()) {
                position = viewPagerAdapter.getRealPosition(position);
                int size = mOnPageChangeListeners == null ? 0 : mOnPageChangeListeners.size();
                for (int i = 0; i < size; i++) {
                    ViewPager.OnPageChangeListener onPageChangeListener = mOnPageChangeListeners.get(i);
                    onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
                if (pagerIndicator != null) {
                    pagerIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (check()) {
                position = viewPagerAdapter.getRealPosition(position);
                for (ViewPager.OnPageChangeListener l : mOnPageChangeListeners) {
                    l.onPageSelected(position);
                }
                if (pagerIndicator != null) {
                    pagerIndicator.onPageSelected(position);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (check()) {
                for (ViewPager.OnPageChangeListener l : mOnPageChangeListeners) {
                    l.onPageScrollStateChanged(state);
                }
                if (pagerIndicator != null) {
                    pagerIndicator.onPageScrollStateChanged(state);
                }
            }
        }
    }
}
