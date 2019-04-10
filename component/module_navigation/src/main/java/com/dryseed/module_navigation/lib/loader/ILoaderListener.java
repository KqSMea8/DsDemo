package com.dryseed.module_navigation.lib.loader;

import com.dryseed.module_navigation.lib.entity.TopMenuTabEntity;

import java.util.ArrayList;

/**
 * @author caiminming
 */
public interface ILoaderListener {
    void onSuccess(ArrayList<TopMenuTabEntity> tabEntities);

    void onError(Exception e);
}
