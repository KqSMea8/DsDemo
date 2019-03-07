package com.easy.moduler.lib.runtime;

import android.app.Application;
import android.text.TextUtils;
import com.easy.moduler.lib.utils.LinuxUtil;
import com.easy.moduler.lib.utils.LogUtils;


/**
 * Created by yaocheng on 2017/4/28.
 */

public class AppRuntime {
    @ProcessType.Process
    private static volatile int sProcessType = ProcessType.PROCESS_TYPE_OTHER;
    public static final String PROCESS_NAME;
    private static String sSubProcessName = null;

    static {
        PROCESS_NAME = LinuxUtil.getCurrentProcessName();
    }

    public static void init(Application app) {

        String packageName = app.getPackageName();
        if (TextUtils.isEmpty(PROCESS_NAME)) {
            throw new RuntimeException("process name = null?");
        }
        try {
            @SuppressWarnings("ConstantConditions") int index = PROCESS_NAME.indexOf(packageName);
            sSubProcessName = PROCESS_NAME.substring(index + packageName.length());
        } catch (Exception e) {
            LogUtils.e("yaocheng", "[cached]", e);
        }
        LogUtils.i("yaocheng", "" + sSubProcessName + " init");
        sProcessType = ProcessType.getProcessTypeByName(sSubProcessName);
    }

    public static String getSubProcessName() {
        return sSubProcessName;
    }

    public static
    @ProcessType.Process
    int getProcessType() {
        return sProcessType;
    }

    public static String getCurProcessName() {
        return PROCESS_NAME;
    }
}
