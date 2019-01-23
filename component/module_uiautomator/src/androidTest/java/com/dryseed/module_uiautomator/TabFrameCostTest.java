package com.dryseed.module_uiautomator;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.util.Log;
import android.widget.RelativeLayout;
import com.dryseed.module_uiautomator.shell.Shell;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

/**
 * @author caiminming
 */
@RunWith(AndroidJUnit4.class)
public class TabFrameCostTest {
    private Instrumentation mInstrumentation;
    private Context mContext;
    private UiDevice mUiDevice;

    @Before
    public void setUp() {
        mInstrumentation = InstrumentationRegistry.getInstrumentation();
        mContext = InstrumentationRegistry.getContext();
        mUiDevice = UiDevice.getInstance(mInstrumentation);
    }

    @Test
    public void testTabFrameCost() throws Exception {
        String processName = MyAndroidJUnitRunner.getTargetProcessName();

        //启动app
        final Intent intent = mContext.getPackageManager()
                .getLaunchIntentForPackage(processName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(intent);

        Thread.sleep(6000);

        for (int i = 1; i <= MyAndroidJUnitRunner.getTabCount(); i++) {
            //点击Tab
            UiObject2 view = mUiDevice.findObject(By.clazz(RelativeLayout.class).res("com.qiyi.video:id/navi" + i));
            if (view == null) {
                return;
            }
            view.click();
            Thread.sleep(2000);

            //dumpsys gfxinfo
            Shell rootShell = new Shell.Builder().setRoot(true).build();
            String result = rootShell.execCommands(
                    Arrays.asList(
                            "dumpsys gfxinfo \"" + MyAndroidJUnitRunner.getTargetProcessName() + "\" reset",
                            "dumpsys gfxinfo \"" + MyAndroidJUnitRunner.getTargetProcessName() + "\""
                    )
            );
            Log.e("MMM", result);
        }
    }
}
