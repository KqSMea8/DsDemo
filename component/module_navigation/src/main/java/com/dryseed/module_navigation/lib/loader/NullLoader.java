package com.dryseed.module_navigation.lib.loader;

import com.dryseed.module_navigation.lib.entity.TopMenuTabEntity;

import java.util.ArrayList;

/**
 * @author caiminming
 */
public class NullLoader<T> extends AbsLoader<T> {
    @Override
    public ArrayList<TopMenuTabEntity> parseData() {
        return new ArrayList<>();
    }
}
