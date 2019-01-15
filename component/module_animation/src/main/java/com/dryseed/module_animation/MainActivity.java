package com.dryseed.module_animation;

import com.easy.moduler.lib.BaseListActivity;

public class MainActivity extends BaseListActivity {
    private final String CATEGORY_ITEM_PATH = "com.dryseed.dsdemo.ANIMATION";

    @Override
    protected String getItemPath() {
        return CATEGORY_ITEM_PATH;
    }
}
