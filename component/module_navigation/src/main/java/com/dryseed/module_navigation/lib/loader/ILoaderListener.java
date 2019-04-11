package com.dryseed.module_navigation.lib.loader;

import com.dryseed.module_navigation.lib.entity.TabEntity;

import java.util.ArrayList;

/**
 * @author caiminming
 */
public interface ILoaderListener {
    void onSuccess(ArrayList<TabEntity> tabEntities);

    void onError(Exception e);
}
