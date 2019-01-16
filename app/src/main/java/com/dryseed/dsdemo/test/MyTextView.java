package com.dryseed.dsdemo.test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.Random;

/**
 * @author caiminming
 */
public class MyTextView extends android.support.v7.widget.AppCompatTextView {
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        scrollTo(0, new Random().nextInt(15));
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {

        }
        setText("" + System.currentTimeMillis());
        requestLayout();
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {

        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
