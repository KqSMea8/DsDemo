package com.dryseed.module_widget.statusbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dryseed.module_widget.R;
import com.easy.moduler.lib.BaseActivity;
import com.gyf.barlibrary.ImmersionBar;

/**
 * @author caiminming
 */
public class ImmersionBarDemo2Activity extends BaseActivity {

    @BindView(R.id.title_bar)
    View mTitleBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immersion_bar_demo2_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        //TitleBar会遮盖掉StatusBar，并自动添加了paddingTop值为状态栏高度
        ImmersionBar.with(this)
                .titleBar(mTitleBar)
                .init();
    }
}
