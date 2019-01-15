package com.dryseed.module_animation.viewPropertyAnimator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.dryseed.module_animation.R;

/**
 * Created by caiminming on 2017/12/21.
 */

public class TestViewPropertyAnimatorActivity extends Activity {

    @BindView(R.id.imageview)
    ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_property_animator_layout);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button1)
    void onButton1Click() {
        mImageView.animate().scaleX(2f).scaleY(2f).setDuration(2000);
    }
}
