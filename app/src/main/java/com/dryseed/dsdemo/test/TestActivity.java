package com.dryseed.dsdemo.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Choreographer;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dryseed.dsdemo.R;
import com.easy.moduler.lib.utils.LogUtils;

import java.lang.reflect.Field;

/**
 * @author caiminming
 */
public class TestActivity extends Activity {

    @BindView(R.id.textview)
    MyTextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        ButterKnife.bind(this);

        try {
            Field field = Choreographer.class.getDeclaredField("SKIPPED_FRAME_WARNING_LIMIT");
            field.setAccessible(true);
            field.set(Choreographer.class, 0);
        } catch (Throwable e) {

        }

        //demo1 : 0 0
        LogUtils.d("demo1 : " + mTextView.getWidth() + " " + mTextView.getHeight());

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                //demo2 : 200 100
                LogUtils.d("demo2 : " + mTextView.getWidth() + " " + mTextView.getHeight());
            }
        });
    }

}
