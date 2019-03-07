package com.easy.moduler.lib;

import android.app.Application;
import android.content.Context;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;
import com.easy.moduler.lib.runtime.AppRuntime;
import com.easy.moduler.lib.utils.DPIUtil;
import com.easy.moduler.lib.utils.LogUtils;

/**
 * @author caiminming
 */
public class CommonApplication extends Application {

    /**
     * A singleton instance of the application class for easy access in other places
     */
    protected static CommonApplication sInstance;

    /**
     * @return ApplicationController singleton instance
     */
    public static synchronized CommonApplication getInstance() {
        return sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sInstance = this;
        AppRuntime.init(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //DPIUtil初始化
        DPIUtil.setDensity(getResources().getDisplayMetrics().density);

        //ARouter初始化
        if (LogUtils.isDebug()) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);

        //AndroidUtilCode初始化
        Utils.init(this);

    }
}
