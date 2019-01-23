package com.dryseed.module_uiautomator;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnitRunner;
import android.text.TextUtils;

/**
 * @author caiminming
 * <p>
 * adb command:
 * adb shell am instrument -w -r  -e procName "com.dryseed.module_uiautomator" -e debug false -e class 'com.dryseed.module_uiautomator.DsTest#testPressHome' com.dryseed.module_uiautomator.test/com.dryseed.module_uiautomator.MyAndroidJUnitRunner
 */
public class MyAndroidJUnitRunner extends AndroidJUnitRunner {
    private static String sTargetProcessName = "com.qiyi.video";
    private static int sTabCount = 3;

    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
        if (arguments != null) {
            String procName = arguments.getString("procName");
            if (!TextUtils.isEmpty(procName)) {
                sTargetProcessName = procName;
            }
        }
    }

    public static String getTargetProcessName() {
        return sTargetProcessName;
    }

    public static int getTabCount() {
        return sTabCount;
    }
}
