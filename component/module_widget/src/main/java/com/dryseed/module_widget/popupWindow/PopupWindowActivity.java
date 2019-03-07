package com.dryseed.module_widget.popupWindow;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.dryseed.module_widget.R;
import com.easy.moduler.lib.utils.DPIUtil;

/**
 * Created by caiminming on 2018/1/2.
 */

public class PopupWindowActivity extends AppCompatActivity {

    private PopupWindow mMenuPopupWindow;
    private View mMenuTipView;
    private View mMusicLocalAddLayout;
    private View mMusicUploadLayout;

    @BindView(R.id.button1)
    TextView mBtn1;

    @BindView(R.id.button2)
    TextView mBtn2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window_layout);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button1)
    void onBtn1Click() {
        showMenuPopupWindow();
    }

    @OnClick(R.id.button2)
    void onBtn2Click() {
        CategoryLottiePopupWindow categoryLottiePopupWindow = new CategoryLottiePopupWindow(PopupWindowActivity.this);
        categoryLottiePopupWindow.showLottiePopupWindow();
    }

    private void showMenuPopupWindow() {
        if (mMenuTipView == null) {
            mMenuTipView = LayoutInflater.from(this).inflate(R.layout.popup_window_menu, null);
            mMusicLocalAddLayout = mMenuTipView.findViewById(R.id.music_local_add_layout);
            mMusicUploadLayout = mMenuTipView.findViewById(R.id.music_upload_layout);
        }
        mMenuPopupWindow = new PopupWindow(mMenuTipView, DPIUtil.dip2px(139), DPIUtil.dip2px(84));
        mMenuPopupWindow.setFocusable(true);
        mMenuPopupWindow.setOutsideTouchable(true);
        //注意这里如果不设置，上面的setOutsideTouchable(true);允许点击外部消失会失效
        mMenuPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mMenuPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        mMusicLocalAddLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mMusicUploadLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mMenuPopupWindow.update();
        if (!isFinishing()) {
            mMenuPopupWindow.showAsDropDown(mBtn1, 0, 0);
        }
    }
}
