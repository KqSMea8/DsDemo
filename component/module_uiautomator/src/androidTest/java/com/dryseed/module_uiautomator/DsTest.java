package com.dryseed.module_uiautomator;

import android.app.Instrumentation;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author caiminming
 */
@RunWith(AndroidJUnit4.class)
public class DsTest {
    public Instrumentation mInstrumentation;
    public UiDevice mUiDevice;

    @Before
    public void setUp() {
        mInstrumentation = InstrumentationRegistry.getInstrumentation();
        mUiDevice = UiDevice.getInstance(mInstrumentation);
    }

    @Test
    public void testPressHome() throws RemoteException {
        mUiDevice.pressRecentApps();
    }

    @Test
    public void testBySelector() {
        //mUiDevice.findObject(By.res("android:id/text1")).click();
        //mUiDevice.findObject(By.text("DsTest")).click();
        //mUiDevice.findObject(By.desc("DsTest")).click();
        //mUiDevice.findObject(By.desc("DsTest").checked(true)).click();
        //mUiDevice.findObject(By.desc("DsTest").focused(true)).click();
    }

    @Test
    public void testBasicOperator() {
        //滑动解锁，50ms
        mUiDevice.swipe(519, 1505, 519, 306, 10);
        sleep(1000);
        mUiDevice.click(660, 500);
        sleep(1000);
        mUiDevice.findObject(By.res("android:id/text1")).click();
        sleep(2000);
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
