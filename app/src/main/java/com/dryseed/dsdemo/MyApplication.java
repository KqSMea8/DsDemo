package com.dryseed.dsdemo;

import android.app.Application;
import com.easy.moduler.lib.okbus.IModule;
import com.easy.moduler.lib.router.IRouterRulesCreator;
import com.easy.moduler.lib.router.Router;

import java.util.ServiceLoader;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //SPI自动注册路由
        ServiceLoader<IRouterRulesCreator> rules = ServiceLoader.load(IRouterRulesCreator.class);
        for (IRouterRulesCreator rule : rules) Router.addRouterRule(rule);

        //SPI自动注册服务
        ServiceLoader<IModule> modules = ServiceLoader.load(IModule.class);
        for (IModule module : modules) module.afterConnected();
    }
}
