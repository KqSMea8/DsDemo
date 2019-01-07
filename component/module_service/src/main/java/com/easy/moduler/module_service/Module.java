package com.easy.moduler.module_service;

import com.easy.moduler.lib.Constants;
import com.easy.moduler.lib.okbus.BaseModule;
import com.easy.moduler.lib.okbus.IModule;
import com.google.auto.service.AutoService;

@AutoService(IModule.class)
public class Module extends BaseModule {
    @Override
    public void afterConnected() {

    }

    @Override
    public int getModuleId() {
        return Constants.MODULE_S;
    }
}
