package com.dryseed.module_widget.popupWindow;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import com.airbnb.lottie.LottieAnimationView;
import com.dryseed.module_widget.R;
import com.easy.moduler.lib.utils.DPIUtil;

import java.lang.ref.WeakReference;

/**
 * @author caiminming
 */
public class CategoryLottiePopupWindow {
    private WeakReference<Activity> mActivityWeakReference;
    private PopupWindow mLottiePopupWindow;
    private FrameLayout mLottieRootView;
    private LottieAnimationView mLottieAnimationView;

    public CategoryLottiePopupWindow(Activity activity) {
        mActivityWeakReference = new WeakReference<Activity>(activity);
    }

    /**
     * show popup window
     */
    public void showLottiePopupWindow() {
        final Activity activity = mActivityWeakReference.get();
        if (activity == null) {
            return;
        }

        if (null == mLottieRootView) {
            mLottieRootView = (FrameLayout) LayoutInflater.from(activity).inflate(R.layout.popup_window_lottie, null);
            mLottieAnimationView = mLottieRootView.findViewById(R.id.lottie_animation_view);
        }
        mLottieAnimationView.setAnimation("guide.json");
        mLottieAnimationView.loop(true);

        mLottiePopupWindow = new PopupWindow(mLottieRootView, DPIUtil.dip2px(200), DPIUtil.dip2px(60));
        mLottiePopupWindow.setFocusable(true);
        mLottiePopupWindow.setOutsideTouchable(true);
        //注意这里如果不设置，上面的setOutsideTouchable(true);允许点击外部消失会失效
        mLottiePopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mLottiePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        mLottieRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mLottiePopupWindow.update();
        if (!activity.isFinishing()) {
            mLottiePopupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER_VERTICAL | Gravity.RIGHT, 0, 0);
            mLottieAnimationView.playAnimation();
        }
    }

    /**
     * hide popup window
     */
    private void dismiss() {
        if (mActivityWeakReference == null) {
            return;
        }
        final Activity activity = mActivityWeakReference.get();
        if (activity == null) {
            return;
        }
        if (null != mLottiePopupWindow && mLottiePopupWindow.isShowing()
                && null != mActivityWeakReference && !activity.isFinishing()) {
            mLottiePopupWindow.dismiss();
        }
    }
}
