package com.dryseed.module_tools.handlerThread;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import com.easy.moduler.lib.BaseActivity;
import com.easy.moduler.lib.utils.LogUtils;

/**
 * @author caiminming
 */
public class HandlerThreadActivity extends BaseActivity {

    HandlerThread mHandlerThread;
    Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandlerThread = new HandlerThread("HandlerThread");
        mHandlerThread.start();

        mHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //这个方法是运行在 handler-thread 线程中的 ，可以执行耗时操作
                LogUtils.d("MMM ", "消息： " + msg.what + "  线程： " + Thread.currentThread().getName());
            }
        };

        //方法1
        //04-02 15:40:55.259 23055-23092/com.dryseed.module_tools D/MMM:
        //(thread:HandlerThread|HandlerThreadActivity.java#handleMessage:34) 消息： 1  线程： HandlerThread
        mHandler.sendEmptyMessage(1);

        //方法2
        new Thread(new Runnable() {
            @Override
            public void run() {
                //在子线程给handler发送数据
                //04-02 15:40:55.259 23055-23092/com.dryseed.module_tools D/MMM:
                //(thread:HandlerThread|HandlerThreadActivity.java#handleMessage:34) 消息： 2  线程： HandlerThread
                mHandler.sendEmptyMessage(2);
            }
        }).start();

        //方法3
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //04-02 15:40:55.259 23055-23092/com.dryseed.module_tools D/MMM:
                //(thread:HandlerThread|HandlerThreadActivity.java#run:54) 消息3 -> 线程： HandlerThread
                LogUtils.d("MMM ", "消息3 -> 线程： " + Thread.currentThread().getName());
            }
        });
    }
}
