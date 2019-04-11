package com.dryseed.module_navigation.lib.loader;

import com.dryseed.module_navigation.lib.TopMenuManager;
import com.dryseed.module_navigation.lib.config.LoaderType;

import java.util.HashMap;

/**
 * @author caiminming
 */
public class LoaderManager {
    private TopMenuManager mTopMenuManager;
    private HashMap<LoaderType, Loader> mLoaderMap = new HashMap<>();
    private Loader mLoader = new NullLoader();

    public LoaderManager() {
        //registerLoader(LoaderType.LOADER_TYPE_URL, new UrlLoader());
        registerLoader(LoaderType.LOADER_TYPE_JSON, new JsonLoader());
        registerLoader(LoaderType.LOADER_TYPE_LIST, new ListLoader());
        registerLoader(LoaderType.LOADER_TYPE_LOCAL_FILE, new AssetsFileLoader());
    }

    /**
     * 注册Loader
     *
     * @param loaderType
     * @param loader
     */
    private void registerLoader(LoaderType loaderType, Loader loader) {
        mLoaderMap.put(loaderType, loader);
    }

    /**
     * 获取Loader
     *
     * @param loaderType
     * @return
     */
    public Loader getLoader(LoaderType loaderType) {
        return mLoaderMap.get(loaderType);
    }

    /**
     * 根据不同的Loader处理数据
     *
     * @param loaderType
     * @param data
     * @param callback
     * @param <T>
     */
    public <T> void processData(LoaderType loaderType, T data, ILoaderListener callback) {
        mLoader = getLoader(loaderType);
        if (mLoader == null) {
            throw new RuntimeException("please use the loaderType supported by the system !!!");
        }
        mLoader.processData(data, callback);
    }

    public void setTopMenuManager(TopMenuManager topMenuManager) {
        mTopMenuManager = topMenuManager;
    }
}
