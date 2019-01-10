package com.dryseed.dsdemo.mvp.demo;

import com.easy.moduler.lib.mvp.IBasePresenter;
import com.easy.moduler.lib.mvp.IBaseView;

/**
 * @author caiminming
 */
public interface ILoginContract {
    /**
     * view 层接口
     */
    interface ILoginView extends IBaseView {
        /**
         * 登录成功
         */
        void loginSuccess();

        /**
         * 登录失败
         */
        void loginFailure();
    }

    /**
     * presenter 层接口
     */
    interface ILoginPresenter extends IBasePresenter {
        /**
         * 登陆
         *
         * @param username
         * @param password
         */
        void login(String username, String password);
    }

    /**
     * model 层接口
     */
    interface LoginModel {
        void login(String username, String password, HttpResponseListener callback);
    }
}
