package com.dryseed.module_uiautomator;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private static final String[] sStartLineTag = {"Draw", "Prepare", "Process", "Execute"};
    private static final String[] sNameArray = {"Total", "Draw", "Prepare", "Process", "Execute"};

    @Test
    public void processGfxinfoData() {
        File file = new File("src/test/java/com/dryseed/module_uiautomator/1.txt");
        Assert.assertTrue(file.exists());

        Map<String, Float> dataMap = new LinkedHashMap<>();

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
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

        System.out.println("dataMap " + dataMap);
    }
}