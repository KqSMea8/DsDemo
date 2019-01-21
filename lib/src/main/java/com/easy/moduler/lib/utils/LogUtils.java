package com.easy.moduler.lib.utils;

import android.text.TextUtils;
import android.util.Log;
import com.easy.moduler.lib.Constants;
import com.easy.moduler.lib.okbus.BaseAppModuleApp;
import com.easy.moduler.lib.okbus.OkBus;


public class LogUtils {
    private static final String TAG = "MMM";
    private static boolean isDebug = true;

    public static boolean isDebug() {
        return true;
    }

    public static void d(String msg, Object... objects) {
        Log.d(TAG, String.format(msg, objects));
    }

    public static void d(String msg) {
        Log.d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void i(String msg) {
        Log.i(TAG, msg);
    }

    public static void i(String tag, String msg) {
        Log.i(tag, msg);
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void logOnUI(String tag, String msg) {
        if (isDebug) {
            if (tag == null || "".equalsIgnoreCase(tag.trim())) {
                tag = TAG;
            }
            Log.d(tag, msg);
            if (TextUtils.equals(tag, Constants.TAG)) {
                String processName;
                if (OkBus.getInstance().isModule()) {
                    processName = BaseAppModuleApp.getBaseApplication().getPackageName().split("_")[1];
                } else {
                    processName = "app";
                }
                OkBus.getInstance().onEvent(Constants.MODULE_PRINT_LOG, System.nanoTime() + " " +
                        processName + " : " + msg + "\n");
            }
        }
    }

    /**
     * 点击Log跳转到指定源码位置
     *
     * @param tag
     * @param msg
     */
    public static void showLog(String tag, String msg) {
        if (isDebug && !TextUtils.isEmpty(msg)) {
            if (TextUtils.isEmpty(tag)) tag = TAG;
            StackTraceElement[] stackTraceElement = Thread.currentThread().getStackTrace();
            int currentIndex = -1;
            for (int i = 0; i < stackTraceElement.length; i++) {
                if (stackTraceElement[i].getMethodName().compareTo("showLog") == 0) {
                    currentIndex = i + 1;
                    break;
                }
            }
            if (currentIndex >= 0) {
                String fullClassName = stackTraceElement[currentIndex].getClassName();
                String className = fullClassName.substring(fullClassName
                        .lastIndexOf(".") + 1);
                String methodName = stackTraceElement[currentIndex].getMethodName();
                String lineNumber = String
                        .valueOf(stackTraceElement[currentIndex].getLineNumber());

                Log.i(tag, msg + "\n  ---->at " + className + "." + methodName + "("
                        + className + ".java:" + lineNumber + ")");
            } else {
                Log.i(tag, msg);
            }
        }
    }
}
