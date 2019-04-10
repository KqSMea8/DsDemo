package com.dryseed.module_navigation.lib.loader;

import com.dryseed.module_navigation.lib.entity.TopMenuTabEntity;

import java.util.ArrayList;

/**
 * @author caiminming
 */
public interface Loader<T> {
    /**
     * 处理数据
     *
     * @param data
     * @param callback
     */
    void processData(T data, ILoaderListener callback);

    /**
     * 解析数据
     *
     * @return
     */
    ArrayList<TopMenuTabEntity> parseData();
}
