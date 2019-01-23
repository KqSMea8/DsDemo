package com.dryseed.module_uiautomator.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CommonUtils {

    public static boolean isEmpty(Object str) {
        return str == null || str instanceof CharSequence && ((CharSequence) str).length() == 0;
    }

    public static String encode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
