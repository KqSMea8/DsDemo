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
public class ImmersionBarDemo1Activity extends BaseActivity {

    @BindView(R.id.status_bar)
    View mStatusBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immersion_bar_demo1_layout);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        ImmersionBar.with(this)
                .statusBarView(mStatusBar)
                .statusBarDarkFont(true)
                .init();
    }
}
