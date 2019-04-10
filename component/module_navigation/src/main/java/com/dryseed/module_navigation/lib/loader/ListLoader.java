package com.dryseed.module_navigation.lib.loader;

import com.dryseed.module_navigation.lib.entity.TopMenuTabEntity;

import java.util.ArrayList;

/**
 * @author caiminming
 */
public class ListLoader<T> extends AbsLoader<T> {
    @Override
    public ArrayList<TopMenuTabEntity> parseData() {
        if (!(mOriginData instanceof ArrayList)) {
            return new ArrayList<>();
        }
        ArrayList<TopMenuTabEntity> originData = (ArrayList<TopMenuTabEntity>) mOriginData;
        return originData;
    }
}
