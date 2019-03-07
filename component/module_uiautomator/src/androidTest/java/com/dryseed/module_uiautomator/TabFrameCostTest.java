package com.dryseed.module_uiautomator;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.dryseed.module_uiautomator.shell.Shell;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author caiminming
 */
@RunWith(AndroidJUnit4.class)
public class TabFrameCostTest {

    private static final String[] sStartLineTag = {"Draw", "Prepare", "Process", "Execute"};
    private static final String[] sNameArray = {"Total", "Draw", "Prepare", "Process", "Execute"};

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

            //dumpsys gfxinfo
            Shell rootShell = new Shell.Builder().setRoot(true).build();
            String result = rootShell.execCommands(
                    Arrays.asList(
                            "dumpsys gfxinfo \"" + MyAndroidJUnitRunner.getTargetProcessName() + "\" reset",
                            "dumpsys gfxinfo \"" + MyAndroidJUnitRunner.getTargetProcessName() + "\""
                    )
            );

            processData(result);

            Thread.sleep(2000);
        }
    }

    private void processData(String result) {
        Map<String, Float> dataMap = new LinkedHashMap<>();

        BufferedReader bufferedReader = null;
        try {
            //bufferedReader = new BufferedReader(new FileReader(file));
            bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            new ByteArrayInputStream(
                                    result.getBytes(Charset.forName("utf8"))
                            ),
                            Charset.forName("utf8")
                    )
            );
            String line = null;
            boolean startTag = false;
            Float[] timeArray = {0f, 0f, 0f, 0f, 0f};
            int totalLineNum = 0;

            while ((line = bufferedReader.readLine()) != null) {
                // 显示行号
                line = line.trim();

                if ("".equals(line)) {
                    continue;
                } else if (line.startsWith("Draw")) {
                    String[] subStrings = line.split("\t");
                    if (Arrays.equals(subStrings, sStartLineTag)) {
                        startTag = true;
                    }
                } else if (startTag) {
                    String[] scores = line.split("\t");
                    if (scores.length < 4) {
                        continue;
                    }
                    Float temp1 = Float.parseFloat(scores[0]);
                    Float temp2 = Float.parseFloat(scores[1]);
                    Float temp3 = Float.parseFloat(scores[2]);
                    Float temp4 = Float.parseFloat(scores[3]);
                    if (Float.isNaN(temp1) || Float.isNaN(temp2) || Float.isNaN(temp3) || Float.isNaN(temp4)) {
                        continue;
                    }
                    //draw
                    timeArray[1] += temp1;
                    //Prepare
                    timeArray[2] += temp2;
                    //Process
                    timeArray[3] += temp3;
                    //Execute
                    timeArray[4] += temp4;
                    //total
                    timeArray[0] += temp1 + temp2 + temp3 + temp4;

                    totalLineNum++;
                }
            }

            for (int index = 0; index < timeArray.length; index++) {
                if (totalLineNum > 0) {
                    dataMap.put(sNameArray[index], timeArray[index] / totalLineNum);
                }
            }

            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                }
            }
        }

        Log.e("MMM", "dataMap " + dataMap);
    }
}
