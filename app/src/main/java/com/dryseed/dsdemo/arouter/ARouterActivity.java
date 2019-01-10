package com.dryseed.dsdemo.arouter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import com.alibaba.android.arouter.launcher.ARouter;
import com.easy.moduler.lib.constants.RouterConfig;

/**
 * @author caiminming
 */
public class ARouterActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
        Button button = new Button(this);
        button.setText("click");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 跨module跳转
                 * jump to {@link com.dryseed.module_recyclerview.TestRecyclerViewActivity}
                 */
                ARouter.getInstance().build(RouterConfig.ROUTER_MODULERECYCLERVIEW_TESTRECYCLERVIEWACTIVITY)
                        .withString("path", "ARouter")
                        .withInt("position", 3)
                        .navigation();
            }
        });
        setContentView(button);
    }
}
