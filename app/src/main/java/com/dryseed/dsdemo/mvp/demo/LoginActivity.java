package com.dryseed.dsdemo.mvp.demo;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.Toast;
import com.easy.moduler.lib.mvp.BaseMvpActivity;

/**
 * @author caiminming
 */
public class LoginActivity extends BaseMvpActivity<ILoginContract.ILoginPresenter> implements ILoginContract.ILoginView {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getPresenter().login("ds", "123");
            }
        }, 1000);
    }

    @Override
    public void loginSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void loginFailure() {
        Toast.makeText(this, "登录失败", Toast.LENGTH_LONG).show();
    }

    @Override
    public ILoginContract.ILoginPresenter onBindPresenter() {
        return new LoginPresenter(this);
    }
}
