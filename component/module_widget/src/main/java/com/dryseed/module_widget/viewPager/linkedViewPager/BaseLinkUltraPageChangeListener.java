package com.dryseed.module_widget.viewPager.linkedViewPager;

import android.support.v4.view.ViewPager;
import com.dryseed.module_widget.viewPager.ultraviewpager.UltraViewPager;
import com.easy.moduler.lib.utils.LogUtils;

/**
 * 实现viewpager的联动
 *
 * @author caiminming
 */
public class BaseLinkUltraPageChangeListener implements ViewPager.OnPageChangeListener {

    private UltraViewPager linkViewPager;
    private UltraViewPager selfViewPager;

    private int pos;

    public BaseLinkUltraPageChangeListener(UltraViewPager selfViewPager, UltraViewPager linkViewPager) {
        this.linkViewPager = linkViewPager;
        this.selfViewPager = selfViewPager;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        int marginX = ((selfViewPager.getViewPager().getWidth() + selfViewPager.getViewPager().getPageMargin()) * position + positionOffsetPixels)
//                * (linkViewPager.getViewPager().getWidth() + linkViewPager.getViewPager().getPageMargin())
//                / (selfViewPager.getViewPager().getWidth() + selfViewPager.getViewPager().getPageMargin());
        int marginX = ((selfViewPager.getViewPager().getWidth() + selfViewPager.getViewPager().getPageMargin()) * position + positionOffsetPixels);
        LogUtils.d("[position:%d][marginX:%d][selfViewPagerScrollX:%d][linkViewPagerScrollX:%d]",
                position, marginX, selfViewPager.getViewPager().getScrollX(), linkViewPager.getViewPager().getScrollX());
        if (linkViewPager.getViewPager().getScrollX() != marginX) {
            //linkViewPager.getViewPager().scrollTo(marginX, 0);
            linkViewPager.getViewPager().scrollTo(selfViewPager.getViewPager().getScrollX(), 0);
        }
    }

    @Override
    public void onPageSelected(int position) {
        this.pos = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            linkViewPager.setCurrentItem(pos);
        }
    }
}
