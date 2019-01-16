package com.dryseed.module_animation.objectAnimator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.dryseed.module_animation.R;

/**
 * @author caiminming
 */
public class TestObjectAnimatorActivity extends Activity {

    @BindView(R.id.imageview)
    ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_animator_layout);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button1)
    void onButton1Click() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mImageView, "scaleY", 1f, 3f, 1f);
        animator.setDuration(5000);
        animator.start();
    }

    @OnClick(R.id.button2)
    void onButton2Click() {
        ObjectAnimator moveIn = ObjectAnimator.ofFloat(mImageView, "translationX", -500f, 0f);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(mImageView, "rotation", 0f, 360f);
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(mImageView, "alpha", 1f, 0f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(rotate).with(fadeInOut).after(moveIn);
        animSet.setDuration(2000);
        animSet.start();
    }
}
