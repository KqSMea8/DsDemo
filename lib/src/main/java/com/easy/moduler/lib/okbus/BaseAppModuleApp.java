package com.easy.moduler.lib.okbus;

import android.content.Intent;
import com.easy.moduler.lib.CommonApplication;
import com.easy.moduler.lib.Constants;
import com.easy.moduler.lib.router.IRouterRulesCreator;
import com.easy.moduler.lib.router.Router;
import com.easy.moduler.lib.utils.LogUtils;

import java.util.ServiceLoader;

/**
 * 组件的独立运行期间的Application,最终打包时根本没有这个类
 */
public class BaseAppModuleApp extends CommonApplication {

    public BaseModule mBaseModule;
    public static BaseAppModuleApp mBaseAppModuleApp;

    /**
     * 只有当是组建单独运行时，才当Application运行，才会走onCreate,最终打包时根本没有这个类
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mBaseAppModuleApp = this;

        //自动注册路由器
        ServiceLoader<IRouterRulesCreator> loader = ServiceLoader.load(IRouterRulesCreator.class);
        for (IRouterRulesCreator aLoader : loader) Router.addRouterRule(aLoader);

        //自动注册服务器
        ServiceLoader<IModule> modules = ServiceLoader.load(IModule.class);
        mBaseModule = (BaseModule) modules.iterator().next();

        //模块初始化
        mBaseModule.init();

        //连接服务
        connectService();
    }

    /**
     * 连接服务器
     */
    public void connectService() {
        Intent intent = new Intent(MessengerService.class.getCanonicalName());// 5.0+ need explicit intent
        intent.setPackage(Constants.SERVICE_PACKAGE_NAME); // the package name of Remote Service
        boolean mIsBound = bindService(intent, mBaseModule.mConnection, BIND_AUTO_CREATE);
        LogUtils.i(Constants.TAG + " connectService", " ServiceConnection-->bindService  mIsBound: " + mIsBound);
    }

    public static BaseAppModuleApp getBaseApplication() {
        return mBaseAppModuleApp;
    }
}