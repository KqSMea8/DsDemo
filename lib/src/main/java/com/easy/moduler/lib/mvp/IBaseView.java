package com.easy.moduler.lib.mvp;

import android.app.Activity;

/**
 * @author caiminming
 */
public interface IBaseView {
    /**
     * 获取Activity对象
     *
     * @param <T>
     * @return
     */
    <T extends Activity> T getSelfActivity();
}
