package com.dryseed.module_widget.statusbar;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dryseed.module_widget.R;
import com.easy.moduler.lib.constants.RouterConfig;
import com.gyf.barlibrary.ImmersionBar;

/**
 * @author caiminming
 */
@Route(path = RouterConfig.ROUTER_MODULE_WIDGET_TESTSTATUSBARACTIVITY)
public class TestStatusBarActivity extends Activity {

    private final int DEMO_1 = 1;
    private final int DEMO_2 = 2;

    @BindView(R.id.button1)
    Button mButton1;
    @BindView(R.id.button2)
    Button mButton2;
    @BindView(R.id.status_bar)
    View mStatusBar;

    ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statusbar_layout);
        ButterKnife.bind(this);

        int demo = getIntent().getIntExtra("demo", -1);
        if (demo == DEMO_1) {
            demo1();
        } else if (demo == DEMO_2) {
            demo2();
        } else {
            mButton1.setVisibility(View.VISIBLE);
            mButton2.setVisibility(View.VISIBLE);
            return;
        }

        mButton1.setVisibility(View.GONE);
        mButton2.setVisibility(View.GONE);
    }

    /**
     * 透明状态栏
     */
    private void demo1() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.transparentBar().init();
    }

    /**
     * xml中定义一个view为状态栏
     */
    private void demo2() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarView(R.id.status_bar).init();
        mStatusBar.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.button1)
    public void onButton1Click() {
        ARouter.getInstance().build(RouterConfig.ROUTER_MODULE_WIDGET_TESTSTATUSBARACTIVITY)
                .withInt("demo", DEMO_1)
                .navigation();
    }

    @OnClick(R.id.button2)
    public void onButton2Click() {
        ARouter.getInstance().build(RouterConfig.ROUTER_MODULE_WIDGET_TESTSTATUSBARACTIVITY)
                .withInt("demo", DEMO_2)
                .navigation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
    }
}
