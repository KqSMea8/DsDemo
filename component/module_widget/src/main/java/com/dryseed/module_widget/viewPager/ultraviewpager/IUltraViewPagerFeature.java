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

import android.support.v4.view.PagerAdapter;

/**
 * Created by mikeafc on 15/11/30.
 */
interface IUltraViewPagerFeature {
    /**
     * Constructs a indicator with no options. this indicator support set-Method in chained mode.
     * meanwhile focusColor and normalColor are necessary,or the indicator won't be show.
     */
    IUltraIndicatorBuilder initIndicator();

    /**
     * Remove indicator
     */
    void disableIndicator();

    /**
     * Enable auto-scroll mode
     *
     * @param intervalInMillis The interval time to scroll in milliseconds.
     */
    void setAutoScroll(int intervalInMillis);

    /**
     * Enable auto-scroll mode with duration
     */
    void setAutoScroll(int intervalInMillis, int scrollDurationInMillis);

    /**
     * Disable auto-scroll mode
     */
    void disableAutoScroll();

    /**
     * Resume auto-scroll with previous set interval
     */
    void resumeAutoScroll();

    /**
     * Same as {@link #disableAutoScroll()}
     */
    void pauseAutoScroll();

    /**
     * Set an infinite loop
     *
     * @param enable enable or disable
     */
    void setInfiniteLoop(boolean enable);

    boolean isInfiniteLoop();

    /**
     * Scroll to the next page, and return to the first page when the last page is reached.
     */
    void scrollNextPage();

    /**
     * <pre>
     *  Adjust the height of the ViewPager to the height of child automatically.
     *
     *  NOTE：如果ViewPager本身高度已经指定，而不是WRAP_CONTENT，则使用setAutoMeasureHeight(true)会导致UltraViewPager
     *  .onMeasure方法
     *        绕过RelativeLayout的onMeasure，导致宽度测量不准确
     * </pre>
     */
    void setAutoMeasureHeight(boolean status);

    void setPageMargin(int marginPixels);

    /**
     * <pre>
     *  Set item margin
     *
     *  Note：setItemMargin会抹除ViewPagerAdapter.instantiateItem()方法中生成的RootView中指定的padding
     * </pre>
     *
     * @param left the left margin in pixels
     * @param top the top margin in pixels
     * @param right the right margin in pixels
     * @param bottom the bottom margin in pixels
     */
    void setItemMargin(int left, int top, int right, int bottom);

    /**
     * Set margins for this ViewPager
     *
     * @param left the left margin in pixels
     * @param right the right margin in pixels
     */
    void setScrollMargin(int left, int right);

    /**
     * The items.size() would be scale to item.size()*infiniteRatio in fact
     */
    void setInfiniteRatio(int infiniteRatio);

    PagerAdapter getInternalAdapter();

    /**
     * 在无法确定page中图片大小时，可以通过配置宽高比，得到正确的图片高度
     * 配置后会忽略{@link #setAutoMeasureHeight(boolean)} 和 page layout中配置的图片高宽
     * Note：{@code widthFactor} 和 {@code heightFactor} 必须大于0
     *
     * @since 9.2.0
     */
    void setPageRatio(int widthFactor, int heightFactor);

    void updateTransforming();
}
