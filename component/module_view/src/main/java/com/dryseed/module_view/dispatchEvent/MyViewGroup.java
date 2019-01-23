package com.dryseed.module_view.dispatchEvent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import com.easy.moduler.lib.utils.LogUtils;

/**
 * Created by caiminming on 2017/10/20.
 */

public class MyViewGroup extends RelativeLayout {
    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtils.d("MyViewGroup dispatchTouchEvent------->>ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.d("MyViewGroup dispatchTouchEvent------->>ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                LogUtils.d("MyViewGroup dispatchTouchEvent------->>ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtils.d("MyViewGroup onTouchEvent------->>ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.d("MyViewGroup onTouchEvent------->>ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                LogUtils.d("MyViewGroup onTouchEvent------->>ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean isIntercept = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtils.d("MyViewGroup onInterceptTouchEvent------->>ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.d("MyViewGroup onInterceptTouchEvent------->>ACTION_MOVE");
                //return true; //测试 触发ACTION_CANCEL情景
            case MotionEvent.ACTION_UP:
                LogUtils.d("MyViewGroup onInterceptTouchEvent------->>ACTION_UP");
                break;
        }

        isIntercept = super.onInterceptTouchEvent(event);
        LogUtils.d("MyViewGroup onInterceptTouchEvent " + isIntercept);
        return isIntercept;
    }
}
