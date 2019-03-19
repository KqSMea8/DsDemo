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
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.easy.moduler.lib.utils.DPIUtil;
import com.easy.moduler.lib.utils.LogUtils;

/**
 * Created by mikeafc on 15/11/25.
 *
 * UltraViewPagerIndicator必须在UltraViewPager中使用，确保它和关联的ViewPager在同一个RelativeLayout中
 *
 * Updated by Yang Huan on 18/1/4
 */
public class UltraViewPagerIndicator extends View implements ViewPager.OnPageChangeListener, IUltraIndicatorBuilder {

    private static final int DEFAULT_COLOR_NORMAL = Color.WHITE;
    private static final int DEFAULT_COLOR_FOCUS = Color.GRAY;
    private static final float DEFAULT_RADIUS_DP = 3.0F;

    private UltraViewPager mViewPager;

    /**
     * 点半径，只在build过程中使用，build完成后，统一使用相应Drawable的高宽
     */
    private int mRadius;

    /**
     * 普通状态点颜色，只在build过程中使用，build完成后，统一使用相应Drawable的高宽
     */
    private int mColorNormal = DEFAULT_COLOR_NORMAL;

    /**
     * 高亮状态点颜色，只在build过程中使用，build完成后，统一使用相应Drawable的高宽
     */
    private int mColorFocus = DEFAULT_COLOR_FOCUS;

    private Drawable mDrawableNormal;
    private Drawable mDrawableFocus;
    private int mIndicatorSpacing = -1;

    private int mOffsetHorizontal;
    private int mOffsetVertical;

    private int mScrollState;

    private boolean mIsOutside = true;
    private int mGravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;

    private IViewPagerIndicatorListener mIndicatorBuildListener;

    public UltraViewPagerIndicator(Context context) {
        super(context);
        init();
    }

    public UltraViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public UltraViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (isInEditMode()) {
            return;
        }
        mRadius = -1;
    }

    private int getItemCount() {
        if (mViewPager == null || mViewPager.getAdapter() == null) {
            return -1;
        }

        final int count = ((UltraViewPagerAdapter) mViewPager.getInternalAdapter()).getRealCount();
        if (count == 0) {
            return -1;
        }

        return count;
    }

    void attachToUltraViewPager(@NonNull UltraViewPager ultraViewPager) {
        ultraViewPager.removeView(this);
        ultraViewPager.addView(this, generateIndicatorLayoutParams());
    }

    @Nullable
    private RelativeLayout.LayoutParams generateIndicatorLayoutParams() {
        if (mViewPager == null) {
            return null;
        }

        int viewPagerId = mViewPager.getViewPager().getId();

        RelativeLayout.LayoutParams lp =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        int verticalGravity = mGravity & Gravity.VERTICAL_GRAVITY_MASK;
        if (verticalGravity == Gravity.TOP) {
            if (mIsOutside && LogUtils.isDebug()) {
                throw new IllegalArgumentException(
                        "Indicators with Gravity.TOP and outside set is not supported");
            } else {
                lp.addRule(RelativeLayout.ALIGN_TOP, viewPagerId);
                lp.topMargin = mOffsetVertical;
            }
        } else {
            // Will ignore Gravity.CENTER_VERTICAL
            if (mIsOutside) {
                lp.addRule(RelativeLayout.BELOW, viewPagerId);
                lp.topMargin = mOffsetVertical;
            } else {
                lp.addRule(RelativeLayout.ALIGN_BOTTOM, viewPagerId);
                lp.bottomMargin = mOffsetVertical;
            }
        }

        int horizontalGravity = mGravity & Gravity.HORIZONTAL_GRAVITY_MASK;
        switch (horizontalGravity) {
            case Gravity.LEFT:
                lp.addRule(RelativeLayout.ALIGN_LEFT, viewPagerId);
                lp.leftMargin = mOffsetHorizontal;
                break;
            case Gravity.RIGHT:
                lp.addRule(RelativeLayout.ALIGN_RIGHT, viewPagerId);
                lp.rightMargin = mOffsetHorizontal;
                break;
            default:
                // All other values are regarded as CENTER_HORIZONTAL
                lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                break;
        }

        return lp;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int count = getItemCount();
        if (count <= 0) {
            return;
        }

        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int contentHeight = getHeight() - paddingTop - paddingBottom;
        int focusY = paddingTop + (contentHeight - mDrawableFocus.getIntrinsicHeight()) / 2;
        int normalY = paddingTop + (contentHeight - mDrawableNormal.getIntrinsicHeight()) / 2;

        int x = getPaddingLeft();
        final float spacing = mIndicatorSpacing;
        final int currentPage = mViewPager.getCurrentItem();

        for (int iLoop = 0; iLoop < count; iLoop++) {
            Drawable d = (iLoop == currentPage) ? mDrawableFocus : mDrawableNormal;
            int y = (iLoop == currentPage) ? focusY : normalY;
            int width = d.getIntrinsicWidth();
            d.setBounds(x, y, x + width, y + d.getIntrinsicHeight());
            d.draw(canvas);
            x += spacing + width;
        }
    }

    public void setViewPager(UltraViewPager view) {
        if (view == null || mViewPager == view) {
            return;
        }

        mViewPager = view;
        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mScrollState = state;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if ((specMode == MeasureSpec.EXACTLY) || mViewPager == null || mViewPager.getAdapter() == null) {
            result = specSize;
        } else {
            final int count = mViewPager.getAdapter().getCount();
            result = getPaddingLeft() + getPaddingRight()
                    + mDrawableFocus.getIntrinsicWidth()
                    + mDrawableNormal.getIntrinsicWidth() * (count - 1)
                    + mIndicatorSpacing * (count - 1)
                    + 1;
            //Respect AT_MOST value if that was what is called for by measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            int maxHeight = Math.max(mDrawableFocus.getIntrinsicHeight(),
                    mDrawableNormal.getIntrinsicHeight());
            result = maxHeight + getPaddingTop() + getPaddingBottom() + 1;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    public boolean isOutside() {
        return mIsOutside;
    }

    public int getVerticalOffset() {
        return mOffsetVertical;
    }

    @Deprecated
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        //Deperecated, do nothing here.
    }

    public void setIndicatorBuildListener(IViewPagerIndicatorListener listener) {
        mIndicatorBuildListener = listener;
    }

    @Override
    public IUltraIndicatorBuilder setFocusColor(int focusColor) {
        mColorFocus = focusColor;
        return this;
    }

    @Override
    public IUltraIndicatorBuilder setNormalColor(int normalColor) {
        mColorNormal = normalColor;
        return this;
    }

    @Override
    public IUltraIndicatorBuilder setFocusDrawable(Drawable focusDrawable) {
        mDrawableFocus = focusDrawable;
        return this;
    }

    @Override
    public IUltraIndicatorBuilder setNormalDrawable(Drawable normalDrawable) {
        mDrawableNormal = normalDrawable;
        return this;
    }

    @Override
    public IUltraIndicatorBuilder setIndicatorSpacing(int indicatorSpacing) {
        mIndicatorSpacing = indicatorSpacing;
        return this;
    }

    @Override
    public IUltraIndicatorBuilder setRadius(int radius) {
        mRadius = radius;
        return this;
    }

    @Override
    public IUltraIndicatorBuilder setOffset(int horizontal, int vertical) {
        mOffsetHorizontal = horizontal;
        mOffsetVertical = vertical;
        return this;
    }

    /**
     * 设置为Outside的Indicator不会影响ViewPager中Page的高度，UltraViewPager在measure时会将indicator高度作为额外高度计算
     * 即：给定一个固定高度（layout_width="120dp"）的UltraViewPager，显示时实际高度会是120+indicator.getHeight()，
     * 其Page的测量会依据设置的高度（120dp）
     */
    @Override
    public IUltraIndicatorBuilder setOutside() {
        mIsOutside = true;
        return this;
    }

    @Override
    public IUltraIndicatorBuilder setInside() {
        mIsOutside = false;
        return this;
    }

    @Override
    public IUltraIndicatorBuilder setGravity(int gravity) {
        mGravity = gravity;
        return this;
    }

    @Override
    public void build() {
        buildDrawables();
        if (mIndicatorBuildListener != null) {
            mIndicatorBuildListener.build();
        }
    }

    public Drawable initDotDrawable(int color, int radius) {
        if (radius <= 0) {
            radius = DPIUtil.dip2px(DEFAULT_RADIUS_DP);
        }
        int size = radius * 2;
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.setIntrinsicHeight(size);
        shapeDrawable.setIntrinsicWidth(size);
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }

    private void buildDrawables() {
        if (mDrawableFocus == null) {
            mDrawableFocus = initDotDrawable(mColorFocus, mRadius);
        }
        if (mDrawableNormal == null) {
            mDrawableNormal = initDotDrawable(mColorNormal, mRadius);
        }
        if (mRadius < 0) {
            mRadius = Math.min(mDrawableFocus.getIntrinsicWidth(),
                    mDrawableNormal.getIntrinsicWidth()) / 2;
        }
        if (mIndicatorSpacing < 0) {
            mIndicatorSpacing = mRadius;
        }
    }

    interface IViewPagerIndicatorListener {
        /**
         * On indicator built.
         */
        void build();
    }
}
