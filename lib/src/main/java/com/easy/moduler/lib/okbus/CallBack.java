package com.easy.moduler.lib.okbus;

import android.os.Message;

public interface CallBack<T> {
    T onCall(Message msg);
}
