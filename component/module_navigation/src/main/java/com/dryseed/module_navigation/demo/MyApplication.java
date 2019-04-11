package com.dryseed.module_navigation.demo;

import com.easy.moduler.lib.okbus.BaseAppModuleApp;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * @author caiminming
 */
public class MyApplication extends BaseAppModuleApp {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
