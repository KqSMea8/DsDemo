package com.easy.moduler.lib.utils;

import android.text.TextUtils;
import android.util.Log;
import com.easy.moduler.lib.Constants;
import com.easy.moduler.lib.okbus.BaseAppModuleApp;
import com.easy.moduler.lib.okbus.OkBus;
import com.easy.moduler.lib.runtime.AppRuntime;


public class LogUtils {
    public static final String TAG = "MMM";
    public static boolean DEBUG = true;

    public static boolean isDebug() {
        return true;
    }

    public static void v(String msg, Object... objects) {
        String text = String.format(msg, objects);
        if (DEBUG) {
            String values = getFileLineMethod() + " " + (TextUtils.isEmpty(text) ? "" : text);
            Log.v(TAG, values);
        }
    }

    public static void d(String msg, Object... objects) {
        if (DEBUG) {
            String values = getFileLineMethod() + " " + (TextUtils.isEmpty(msg) ? "" : msg);
            Log.d(TAG, values);
        }
    }

    public static void d(String msg) {
        if (DEBUG) {
            String values = getFileLineMethod() + " " + (TextUtils.isEmpty(msg) ? "" : msg);
            Log.d(TAG, values);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            String values = getFileLineMethod() + " " + (TextUtils.isEmpty(msg) ? "" : msg);
            Log.d(tag, values);
        }
    }

    public static void i(String msg) {
        if (DEBUG) {
            String values = getFileLineMethod() + " " + (TextUtils.isEmpty(msg) ? "" : msg);
            Log.i(TAG, values);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            String values = getFileLineMethod() + " " + (TextUtils.isEmpty(msg) ? "" : msg);
            Log.i(tag, values);
        }
    }

    public static void w(String msg, Object... objects) {
        String text = String.format(msg, objects);
        if (DEBUG) {
            String values = getFileLineMethod() + " " + (TextUtils.isEmpty(text) ? "" : text);
            Log.w(TAG, values);
        }
    }

    public static void e(String tag, String msg, Throwable e) {
        if (DEBUG) {
            String values = getFileLineMethod() + " " + (TextUtils.isEmpty(msg) ? "" : msg);
            Log.e(tag, values, e);
        }
    }

    public static void e(String msg, Object... objects) {
        String text = String.format(msg, objects);
        if (DEBUG) {
            String values = getFileLineMethod() + " " + (TextUtils.isEmpty(text) ? "" : text);
            Log.w(TAG, values, null);
        }
    }

    public static void e(String msg) {
        if (DEBUG) {
            String values = getFileLineMethod() + " " + (TextUtils.isEmpty(msg) ? "" : msg);
            Log.w(TAG, values, null);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            String values = getFileLineMethod() + " " + (TextUtils.isEmpty(msg) ? "" : msg);
            Log.w(tag, values, null);
        }
    }

    public static void logOnUI(String tag, String msg) {
        if (DEBUG) {
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
        if (DEBUG && !TextUtils.isEmpty(msg)) {
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

    private static String getFileLineMethod() {
        if (DEBUG) {
            StackTraceElement element = new Exception().getStackTrace()[2];
            String processName = AppRuntime.getSubProcessName();
            if (processName != null) {
                int index = processName.indexOf(":");
                if (index > -1) {
                    processName = processName.substring(Math.min(index + 1, processName.length()));
                }
            }

            if (TextUtils.isEmpty(processName)) {
                processName = "main";
            }
            StringBuffer buffer = new StringBuffer()
                    .append("(")
                    //.append("process:")
                    //.append(processName)
                    //.append("|")
                    .append("thread:")
                    .append(Thread.currentThread().getName())
                    .append("|")
                    .append(element.getFileName())
                    .append("#")
                    .append(element.getMethodName())
                    .append(":")
                    .append(element.getLineNumber())
                    .append(")");
            return buffer.toString();
        }
        return null;
    }

}
