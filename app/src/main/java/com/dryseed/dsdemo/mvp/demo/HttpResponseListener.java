package com.dryseed.dsdemo.mvp.demo;

/**
 * @author caiminming
 */
public interface HttpResponseListener<T> {
    /**
     * 网络请求成功
     */
    void onSuccess();

    /**
     * 网络请求失败
     */
    void onFailure();
}
