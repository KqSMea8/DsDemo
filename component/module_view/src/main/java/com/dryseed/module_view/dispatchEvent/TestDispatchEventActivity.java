package com.dryseed.module_view.dispatchEvent;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dryseed.module_view.R;
import com.easy.moduler.lib.utils.LogUtils;

/**
 * Created by caiminming on 2017/4/7.
 * <p>
 * 正常情况下输出的log：
 * -- MyTextView dispatchTouchEvent----->>ACTION_DOWN
 * -- MyTextView onTouchEvent----->>ACTION_DOWN
 * -- MyViewGroup dispatchTouchEvent------->>ACTION_DOWN
 * -- MyViewGroup onInterceptTouchEvent------->>ACTION_DOWN
 * -- MyViewGroup onInterceptTouchEvent false
 * -- MyCustomView dispatchTouchEvent----->>ACTION_DOWN
 * -- MyCustomView onTouch------->>ACTION_DOWN
 * -- MyCustomView onTouchEvent----->>ACTION_DOWN
 * -- MyViewGroup dispatchTouchEvent------->>ACTION_UP
 * -- MyViewGroup onInterceptTouchEvent------->>ACTION_UP
 * -- MyViewGroup onInterceptTouchEvent false
 * -- MyCustomView dispatchTouchEvent----->>ACTION_UP
 * -- MyCustomView onTouch------->>ACTION_UP
 * -- MyCustomView onTouchEvent----->>ACTION_UP
 * -- MyCustomView onClick
 */
public class TestDispatchEventActivity extends AppCompatActivity {

    @BindView(R.id.my_view_group)
    MyViewGroup mMyViewGroup;

    @BindView(R.id.my_custom_view)
    View mMyCustomView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dispatch_event_layout);
        ButterKnife.bind(this);

        // MyViewGroup
        mMyViewGroup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        LogUtils.d("MyViewGroup onTouch------->>ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        LogUtils.d("MyViewGroup onTouch------->>ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        LogUtils.d("MyViewGroup onTouch------->>ACTION_UP");
                        break;
                }
                return false;
            }
        });
        mMyViewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.d("MyViewGroup onClick");
            }
        });

        // MyCustomView
        mMyCustomView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        LogUtils.d("MyCustomView onTouch------->>ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        LogUtils.d("MyCustomView onTouch------->>ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        LogUtils.d("MyCustomView onTouch------->>ACTION_UP");
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        LogUtils.d("MyCustomView onTouch------->>ACTION_CANCEL");
                        break;
                }
                return false;
            }
        });
        mMyCustomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.d("MyCustomView onClick");
            }
        });

    }
}
