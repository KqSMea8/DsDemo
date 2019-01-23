package com.dryseed.module_view.dispatchEvent;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.easy.moduler.lib.utils.LogUtils;

/**
 * Created by caiminming on 2017/4/7.
 */

public class MyTextView extends AppCompatTextView {

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtils.d("MyTextView dispatchTouchEvent----->>ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.d("MyTextView dispatchTouchEvent----->>ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                LogUtils.d("MyTextView dispatchTouchEvent----->>ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtils.d("MyTextView onTouchEvent----->>ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.d("MyTextView onTouchEvent----->>ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                LogUtils.d("MyTextView onTouchEvent----->>ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }
}
