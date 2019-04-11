package com.dryseed.module_navigation.lib.entity;

import com.dryseed.module_navigation.lib.config.TabStyleConstants;

import java.io.Serializable;

/**
 * @author caiminming
 */
public class TabStyleEntity implements Serializable {
    /**
     * Tab展示样式，see {@link com.dryseed.module_navigation.lib.config.TabStyleConstants}
     */
    private int showStyle = TabStyleConstants.TAB_STYLE_TEXT;

    /**
     * 图片
     */
    private String icon;

    public int getShowStyle() {
        return showStyle;
    }

    public void setShowStyle(int showStyle) {
        this.showStyle = showStyle;
    }

    public String getIcon() {
        return icon == null ? "" : icon;
    }

    public void setIcon(String image) {
        this.icon = image;
    }
}
