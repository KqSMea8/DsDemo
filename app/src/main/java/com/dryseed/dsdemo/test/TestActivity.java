package com.dryseed.dsdemo.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Choreographer;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dryseed.dsdemo.R;

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
    }
}
