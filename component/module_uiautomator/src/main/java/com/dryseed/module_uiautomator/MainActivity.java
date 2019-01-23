package com.dryseed.module_uiautomator;

import com.easy.moduler.lib.BaseListActivity;

public class MainActivity extends BaseListActivity {

    private final String CATEGORY_ITEM_PATH = "com.dryseed.dsdemo.UIAUTOMATOR";

    @Override
    protected String getItemPath() {
        return CATEGORY_ITEM_PATH;
    }
}
