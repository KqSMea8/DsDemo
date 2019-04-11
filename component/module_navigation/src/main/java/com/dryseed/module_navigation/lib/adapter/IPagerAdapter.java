package com.dryseed.module_navigation.lib.adapter;

import com.dryseed.module_navigation.lib.entity.TabEntity;

import java.util.ArrayList;

/**
 * @author caiminming
 */
public interface IPagerAdapter {
    void setData(ArrayList<TabEntity> tabEntities);

    ArrayList<TabEntity> getData();
}
