package com.dryseed.module_recyclerview;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.easy.moduler.lib.BaseListActivity;
import com.easy.moduler.lib.constants.RouterConfig;

/**
 * @author caiminming
 */
@Route(path = RouterConfig.ROUTER_MODULERECYCLERVIEW_TESTRECYCLERVIEWACTIVITY)
public class TestRecyclerViewActivity extends BaseListActivity {

    private final String CATEGORY_ITEM_PATH = "com.dryseed.dsdemo.RECYCLERVIEW";

    @Override
    protected String getItemPath() {
        return CATEGORY_ITEM_PATH;
    }
}
