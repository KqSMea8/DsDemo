package com.dryseed.module_uiautomator;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import com.dryseed.module_uiautomator.shell.Shell;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.Arrays;

/**
 * @author caiminming
 */
@RunWith(AndroidJUnit4.class)
public class ShellTest {
    @Test
    public void testSuCommand() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        File dir = context.getExternalCacheDir();
        Shell shell = new Shell.Builder().setRoot(true).setBaseDir(dir).build();
        String result = shell.execCommand("dumpsys gfxinfo \"com.qiyi.video\"");
        Log.e("Test", "result: " + result);
    }

    @Test
    public void testCommand() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        File dir = appContext.getExternalCacheDir();
        Shell shell = new Shell.Builder().setRoot(false).setBaseDir(dir).build();
        String result = shell.execCommands(Arrays.asList(
                "ls -la", "touch hi", "ls -la", "rm hi", "ls -la"
        ));
        Log.e("Test", "result: " + result);
    }
}
