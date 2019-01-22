package com.dryseed.module_view;

import com.easy.moduler.lib.BaseListActivity;

public class MainActivity extends BaseListActivity {

    private final String CATEGORY_ITEM_PATH = "com.dryseed.dsdemo.VIEW";

    @Override
    protected String getItemPath() {
        return CATEGORY_ITEM_PATH;
    }
}
