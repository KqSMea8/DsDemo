package com.dryseed.dsdemo.test;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.easy.moduler.lib.BaseActivity;

import java.util.List;

/**
 * @author caiminming
 */
public class TestGetTopActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ToastUtils.showShort(getAppPackageName(this));
    }

    public static String getAppPackageName(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = activityManager.getRunningTasks(2);
        if (taskInfo == null || taskInfo.size() <= 0) {
            return "";
        }
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        LogUtils.d("当前应用:" + componentInfo.getClassName());
        return componentInfo.getClassName();
    }
}
