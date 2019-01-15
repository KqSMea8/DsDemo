package com.dryseed.module_animation.viewAnimator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.dryseed.module_animation.R;
import com.dryseed.module_animation.viewAnimator.lib.ViewAnimator;
import com.easy.moduler.lib.utils.DPIUtil;

/**
 * Created by caiminming on 2017/12/21.
 */

public class TestViewAnimatorActivity extends Activity {

    @BindView(R.id.imageview)
    ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animator_layout);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button1)
    void onButton1Click() {
        ViewAnimator
                .animate(mImageView)
                .translationY(0, 1000)
                .alpha(1.0f, 0.1f)
                .duration(1000)
                .interpolator(new AccelerateDecelerateInterpolator())

                .thenAnimate(mImageView)
                .alpha(0.1f, 1.0f)
                .width(mImageView.getWidth(), DPIUtil.dip2px(100))
                .duration(2000)
                .decelerate()

                .thenAnimate(mImageView)
                .pulse()
                .repeatCount(-1)
                .start();

    }

    @OnClick(R.id.button2)
    void onButton2Click() {
        ViewAnimator
                .animate(mImageView)
                .scale(1f, 2f)
                .pivotX(0)
                .pivotY(mImageView.getHeight() / 2)
                .duration(2000)
                .interpolator(new AccelerateDecelerateInterpolator())
                .start();
    }
}
