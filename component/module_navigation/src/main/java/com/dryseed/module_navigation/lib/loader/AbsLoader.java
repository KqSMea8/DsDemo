package com.dryseed.module_navigation.lib.loader;

import com.dryseed.module_navigation.lib.entity.TopMenuTabEntity;

import java.util.ArrayList;

/**
 * @author caiminming
 */
public abstract class AbsLoader<T> implements Loader<T> {
    protected T mOriginData;

    protected ArrayList<TopMenuTabEntity> mTabEntities = new ArrayList<>();

    @Override
    public void processData(T data, ILoaderListener callback) {
        mOriginData = data;
        ArrayList<TopMenuTabEntity> list = parseData();
        if (list != null && list.size() > 0) {
            mTabEntities.clear();
            mTabEntities.addAll(list);
            callback.onSuccess(mTabEntities);
        } else {
            callback.onError(new RuntimeException("list is empty !!!"));
        }
    }
}
