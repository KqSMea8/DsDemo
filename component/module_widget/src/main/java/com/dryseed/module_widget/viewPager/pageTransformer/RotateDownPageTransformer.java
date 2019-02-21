package com.dryseed.module_widget.viewPager.pageTransformer;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * position变化规律：
 * <p>
 * 假设现在ViewPager在A页现在滑出B页，则:
 * A页的position变化就是( 0, -1]
 * B页的position变化就是[ 1 , 0 ]
 * <p>
 * 可以这样理解：上一屏的起始点为-1，当前屏的起始点为0，下一屏的起始点为1
 *
 * @author caiminming
 */
public class RotateDownPageTransformer implements ViewPager.PageTransformer {

    private static final float ROT_MAX = 20.0f;
    private float mRot;


    public void transformPage(View view, float position) {

        Log.e("TAG", view + " , " + position + "");

        if (position < -1) {
            // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setRotation(0);

        } else if (position <= 1) {
            // [-1,1]
            // a页滑动至b页 ； a页从 0.0 ~ -1 ；b页从1 ~ 0.0
            // Modify the default slide transition to shrink the page as well
            if (position < 0) {
                mRot = (ROT_MAX * position);
                view.setPivotX(view.getMeasuredWidth() * 0.5f);
                view.setPivotY(view.getMeasuredHeight());
                view.setRotation(mRot);
            } else {
                mRot = (ROT_MAX * position);
                view.setPivotX(view.getMeasuredWidth() * 0.5f);
                view.setPivotY(view.getMeasuredHeight());
                view.setRotation(mRot);
            }

            // Scale the page down (between MIN_SCALE and 1)

            // Fade the page relative to its size.

        } else {
            // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setRotation(0);
        }
    }
}
