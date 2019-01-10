package com.dryseed.module_recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.easy.moduler.lib.constants.RouterConfig;

/**
 * @author caiminming
 */
@Route(path = RouterConfig.ROUTER_MODULERECYCLERVIEW_TESTRECYCLERVIEWACTIVITY)
public class TestRecyclerViewActivity extends Activity {

    @Autowired
    public int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkARouter();
    }

    private void checkARouter() {
        ARouter.getInstance().inject(this);
        String path = getIntent().getStringExtra("path");
        Toast.makeText(this, "path : " + path + " | position : " + position, Toast.LENGTH_SHORT).show();
    }
}
