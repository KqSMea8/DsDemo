package com.dryseed.module_image;

import com.easy.moduler.lib.BaseListActivity;

/**
 * @author caiminming
 */
public class MainActivity extends BaseListActivity {

    private final String CATEGORY_ITEM_PATH = "com.dryseed.dsdemo.IMAGE";

    @Override
    protected String getItemPath() {
        return CATEGORY_ITEM_PATH;
    }
}
