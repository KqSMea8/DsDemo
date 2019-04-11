package com.dryseed.module_navigation.lib.loader;

import com.dryseed.module_navigation.lib.entity.TabEntity;

import java.util.ArrayList;

/**
 * @author caiminming
 */
public class ListLoader<T> extends AbsLoader<T> {
    @Override
    public ArrayList<TabEntity> parseData() {
        if (!(mOriginData instanceof ArrayList)) {
            return new ArrayList<>();
        }
        ArrayList<TabEntity> originData = (ArrayList<TabEntity>) mOriginData;
        return originData;
    }
}
