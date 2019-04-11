package com.dryseed.module_navigation.lib.loader;

import com.dryseed.module_navigation.lib.entity.TabEntity;
import com.dryseed.module_navigation.lib.utils.GsonUtils;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * @author caiminming
 */
public class JsonLoader<T> extends AbsLoader<T> {
    @Override
    public ArrayList<TabEntity> parseData() {
        if (!(mOriginData instanceof String)) {
            return new ArrayList<>();
        }

        String jsonArrayStr = (String) mOriginData;
        Type listType = new TypeToken<ArrayList<TabEntity>>() {
        }.getType();
        ArrayList<TabEntity> list = GsonUtils.fromJson(jsonArrayStr, listType);
        return list;
    }
}
