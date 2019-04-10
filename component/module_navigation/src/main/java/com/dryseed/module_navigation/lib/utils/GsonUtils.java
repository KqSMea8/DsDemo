package com.dryseed.module_navigation.lib.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * @author caiminming
 */
public class GsonUtils {
    private static Gson sGson = new Gson();

    private GsonUtils() {
    }

    public static <T> T fromJson(String json, Type type) {
        try {
            return sGson.fromJson(json, type);
        } catch (Exception e) {
        }
        return null;
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return sGson.fromJson(json, clazz);
        } catch (Exception e) {
        }
        return null;
    }

    public static String toJson(Object src) {
        return sGson.toJson(src);
    }
}
