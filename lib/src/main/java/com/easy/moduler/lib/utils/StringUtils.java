package com.easy.moduler.lib.utils;

import java.util.Collection;

public class StringUtils {
    /**
     * 比较字符串不记大小写是否相同
     *
     * @param string1
     * @param string2
     * @return
     */
    public static boolean equalsIgnoreCase(String string1, String string2) {
        if (string1 == string2) return true;
        if (string1 == null) return false;
        return string1.equalsIgnoreCase(string2);
    }

    public static boolean isEmpty(String str) {
        if (null == str || "".equals(str) || "null".equals(str)) {
            return true;
        } else {
            return str.length() == 4 && str.toLowerCase().equals("null");
        }
    }

    public static String valueOf(Object msg) {
        if (msg instanceof Object[]) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Object o : ((Object[]) msg)) {
                stringBuilder.append(String.valueOf(o));
            }
            return stringBuilder.toString();
        } else if (msg instanceof Collection) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Object o : ((Collection) msg)) {
                stringBuilder.append(String.valueOf(o));
            }
            return stringBuilder.toString();
        }
        return String.valueOf(msg);
    }

    public static boolean isEmptyArray(Object array) {
        return null == array;
    }
}
