package com.dryseed.module_view.motionEvent;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dryseed.module_view.R;

/**
 * @author caiminming
 */
public class TestMotionEventActivity extends Activity {

    @BindView(R.id.imageview)
    MyImageView mMyImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_event_layout);
        ButterKnife.bind(this);
    }
}
