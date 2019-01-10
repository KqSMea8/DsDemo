package com.easy.moduler.lib.mvp;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

/**
 * @author caiminming
 */
public class BaseMvpPresenter<V extends IBaseView> implements IBasePresenter {
    /**
     * 防止 Activity 不走 onDestory() 方法，所以采用弱引用来防止内存泄漏
     */
    private WeakReference<V> mViewRef;

    public BaseMvpPresenter(@NonNull V view) {
        attachView(view);
    }

    private void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    public V getView() {
        return mViewRef.get();
    }

    @Override
    public boolean isViewAttach() {
        return mViewRef != null && mViewRef.get() != null;
    }

    @Override
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
