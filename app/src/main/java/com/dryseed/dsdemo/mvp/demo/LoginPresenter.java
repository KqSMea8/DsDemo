package com.dryseed.dsdemo.mvp.demo;

import android.support.annotation.NonNull;
import com.easy.moduler.lib.mvp.BaseMvpPresenter;

/**
 * @author caiminming
 */
public class LoginPresenter extends BaseMvpPresenter<ILoginContract.ILoginView>
        implements ILoginContract.ILoginPresenter, HttpResponseListener {

    private ILoginContract.LoginModel mLoginModel;

    public LoginPresenter(@NonNull ILoginContract.ILoginView view) {
        super(view);
        mLoginModel = new LoginModel();
    }

    @Override
    public void login(String username, String password) {
        mLoginModel.login(username, password, this);
    }

    @Override
    public void onSuccess() {
        getView().loginSuccess();
    }

    @Override
    public void onFailure() {
        getView().loginFailure();
    }
}
