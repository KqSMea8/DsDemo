package com.dryseed.module_widget.statusbar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.dryseed.module_widget.R;
import com.easy.moduler.lib.utils.UIUtil;
import com.gyf.barlibrary.OSUtils;

/**
 * @author caiminming
 * https://github.com/gyf-dev/ImmersionBar
 */
public class MyStatusBarActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_status_bar_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;  //防止系统栏隐藏时内容区域大小发生变化
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !OSUtils.isEMUI3_1()) {
                /**
                 *  V >= 5.0 可以调用 window.setStatusBarColor(@ColorInt int color) 来修改状态栏颜色，
                 *  但是让这个方法生效有一个前提条件：你必须给window添加FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS并且取消FLAG_TRANSLUCENT_STATUS
                 */
                uiFlags |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN; //Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态栏遮住.
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);  //需要设置这个才能设置状态栏颜色
                getWindow().setStatusBarColor(Color.TRANSPARENT); //默认设置成透明色，使用用户自定义的布局作为状态栏
            } else {
                // 透明状态栏 4.4 <= V < 5.0 :
                // 设置了之后，相当于隐藏了状态栏，下面的布局会填补状态栏。
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                // 创建一个占位状态栏
                //setupStatusBarView();
                //TODO:
            }
            getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        }

        /**
         * 通过状态栏高度动态设置状态栏布局
         */
        setStatusBarView();
    }

    private void setStatusBarView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View statusBarView = findViewById(R.id.status_bar);
            ViewGroup.LayoutParams params = statusBarView.getLayoutParams();
            params.height = UIUtil.getStatusBarHeight(this);
            statusBarView.setLayoutParams(params);
        }
    }
}
