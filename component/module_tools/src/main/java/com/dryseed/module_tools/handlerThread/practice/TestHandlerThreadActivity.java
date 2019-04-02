package com.dryseed.module_tools.handlerThread.practice;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import com.blankj.utilcode.util.ToastUtils;
import com.easy.moduler.lib.BaseActivity;
import com.easy.moduler.lib.utils.LogUtils;

/**
 * 模拟场景：A操作为耗时操作，需要在工作线程执行，执行完了之后要执行在主线程执行的B操作
 *
 * @author caiminming
 */
public class TestHandlerThreadActivity extends BaseActivity {

    private HandlerThread mHandlerThread;

    private Handler mTimeCostHandler;

    private Handler mMainHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandlerThread = new HandlerThread("TestHandlerThreadActivity");
        mHandlerThread.start();

        mTimeCostHandler = new Handler(mHandlerThread.getLooper());

        mMainHandler = new Handler();

        mTimeCostHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    LogUtils.d("mTimeCostHandler run");

                    //A操作
                    Thread.sleep(1000);

                    mMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            LogUtils.d("mMainHandler run");

                            //B操作
                            ToastUtils.showShort("Hello World");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
