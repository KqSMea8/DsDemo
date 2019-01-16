package com.dryseed.module_animation.valueAnimator;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.dryseed.module_animation.R;

/**
 * @author caiminming
 */
public class TestValueAnimatorActivity extends Activity {

    @BindView(R.id.imageview)
    ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_animator_layout);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button1)
    void onButton1Click() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f, 0f, 1f);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                mImageView.setAlpha(currentValue);
            }
        });
        valueAnimator.start();
    }
}
