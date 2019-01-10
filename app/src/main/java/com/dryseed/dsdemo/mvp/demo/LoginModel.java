package com.dryseed.dsdemo.mvp.demo;

/**
 * @author caiminming
 */
public class LoginModel implements ILoginContract.LoginModel {
    @Override
    public void login(String username, String password, HttpResponseListener callback) {
        if (callback != null) {
            callback.onSuccess();
        }
    }
}
