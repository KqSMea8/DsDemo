package com.easy.moduler.lib.mvp;

import android.app.Activity;

/**
 * @author caiminming
 */
public abstract class BaseMvpActivity<P extends IBasePresenter> extends Activity implements IBaseView {
    private P mPresenter;

    /**
     * 创建 Presenter
     *
     * @return
     */
    public abstract P onBindPresenter();

    /**
     * 获取 Presenter 对象，在需要获取时才创建`Presenter`，起到懒加载作用
     */
    public P getPresenter() {
        if (mPresenter == null) {
            mPresenter = onBindPresenter();
        }
        return mPresenter;
    }

    @Override
    public Activity getSelfActivity() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 在生命周期结束时，将 presenter 与 view 之间的联系断开，防止出现内存泄露
         */
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
