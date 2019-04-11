/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dryseed.module_widget.viewPager.morePagerSlidingTabStrip;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.ToastUtils;
import com.dryseed.module_widget.R;
import com.dryseed.module_widget.viewPager.advancedPagerSlidingTabStrip.AdvancedPagerSlidingTabStrip;
import com.easy.moduler.lib.utils.DPIUtil;

@SuppressWarnings("unused")
public class MoreAdvancedPagerSlidingTabStrip2 extends AdvancedPagerSlidingTabStrip {

    /**
     * 尾部View
     */
    protected View mFooterView;
    /**
     * 尾部文字
     */
    protected TextView mFooterTextView;
    /**
     * 拖拽最大距离
     */
    private static final int DRAG_JUMP_MAX_DISTANCE = DPIUtil.dip2px(43);
    /**
     * 可以跳转的拖拽最小距离
     */
    private static final int DRAG_CAN_JUMP_DISTANCE = DRAG_JUMP_MAX_DISTANCE / 2;
    /**
     * 上一次X
     */
    private float mLastX = 0;
    /**
     * X偏移量
     */
    private float deltaX = 0;
    /**
     * 是否能跳转
     */
    private boolean canJump;
    /**
     * 是否能拖拽
     */
    private boolean canDrag;
    /**
     * 是否开启Footer
     */
    private boolean enableFooter;

    public MoreAdvancedPagerSlidingTabStrip2(Context context) {
        this(context, null);
    }

    public MoreAdvancedPagerSlidingTabStrip2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoreAdvancedPagerSlidingTabStrip2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MoreAdvancedPagerSlidingTabStrip2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void addFooterView() {
        mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.item_more_pager_sliding_tab_strip_layout, null);
        mFooterTextView = mFooterView.findViewById(R.id.text);
        mTabsContainer.addView(mFooterView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("MMM", "ACTION_DOWN");
                mLastX = e.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                int maxScrollX = getChildAt(0).getMeasuredWidth() - getMeasuredWidth() + getPaddingLeft();
                if (maxScrollX == getScrollX()) {
                    canDrag = true;
                }
                if (mLastX == 0) {
                    mLastX = e.getRawX();
                    break;
                }
                float mMoveX = e.getRawX() - mLastX;
                Log.d("MMM", "mMoveX : " + mMoveX);

                //手指向左滑
                if (canDrag) {
                    //moveX应为负数
                    deltaX = deltaX - mMoveX;
                    if (deltaX > DRAG_JUMP_MAX_DISTANCE) {
                        deltaX = DRAG_JUMP_MAX_DISTANCE;
                    }
                    if (deltaX > DRAG_CAN_JUMP_DISTANCE) {
                        mFooterTextView.setText("松手查看更多");
                    } else {
                        mFooterTextView.setText("左滑查看更多");
                    }

                    Log.d("MMM", "111 maxScroll : " + (getChildAt(0).getMeasuredWidth() - getMeasuredWidth()) +
                            " | scrollX : " + getScrollX() + " | deltaX : " + deltaX);

                    resetMoreView((int) deltaX);
                }
                mLastX = e.getRawX();
                break;
            case MotionEvent.ACTION_UP:
                if (canDrag && deltaX >= DRAG_CAN_JUMP_DISTANCE) {
                    canJump = true;
                    jump();
                    mFooterTextView.setText("左滑查看更多");
                }
                resetMoreView(0);
                mLastX = 0;
                deltaX = 0;
                canJump = false;
                canDrag = false;
                break;
            case MotionEvent.ACTION_CANCEL:
                if (canJump) {
                    jump();
                }
                resetMoreView(0);
                break;
            default:
                mLastX = 0;
                deltaX = 0;
                canJump = false;
                canDrag = false;
                break;
        }
        return super.onTouchEvent(e);
    }

    public void resetMoreView(int padding) {
        Log.d("MMM", "====> resetMoreView : " + padding);
        LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(padding + DPIUtil.dip2px(45), ViewGroup.LayoutParams.WRAP_CONTENT);
        mFooterView.setLayoutParams(rlp);
    }

    private void jump() {
        ToastUtils.showShort("jump");
    }
}
