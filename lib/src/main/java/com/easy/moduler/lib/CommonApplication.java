package com.easy.moduler.lib;

import android.app.Application;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;
import com.easy.moduler.lib.utils.DPIUtil;
import com.easy.moduler.lib.utils.LogUtils;

/**
 * @author caiminming
 */
public class CommonApplication extends Application {
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
