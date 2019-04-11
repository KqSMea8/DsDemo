package com.dryseed.module_navigation.demo;

import com.dryseed.module_navigation.lib.config.TabStyleConstants;
import com.dryseed.module_navigation.lib.entity.TabEntity;
import com.dryseed.module_navigation.lib.entity.TabStyleEntity;

import java.util.ArrayList;

/**
 * @author caiminming
 */
public class DataProvider {

    public static ArrayList<TabEntity> getListData() {
        ArrayList<TabEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TabEntity entity = new TabEntity();
            entity.setId(i + "");
            entity.setTitle("tab " + i);

            TabStyleEntity tabStyleEntity = new TabStyleEntity();
            tabStyleEntity.setIcon("https://upload-images.jianshu.io/upload_images/4123333-1a063bfe0c8dbc12.gif?imageMogr2/auto-orient/strip");
            tabStyleEntity.setShowStyle(TabStyleConstants.TAB_STYLE_RIGHT_IMAGE_LEFT_TEXT);
            entity.setTabStyleEntity(tabStyleEntity);

            list.add(entity);
        }
        return list;
    }

    public static String getJsonData() {
        return "[\n" +
                "  {\n" +
                "    \"id\": \"111\",\n" +
                "    \"title\": \"title1\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"222\",\n" +
                "    \"title\": \"title2\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"333\",\n" +
                "    \"title\": \"title3\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"444\",\n" +
                "    \"title\": \"title4\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"555\",\n" +
                "    \"title\": \"title5\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"666\",\n" +
                "    \"title\": \"title6\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"777\",\n" +
                "    \"title\": \"title7\"\n" +
                "  }\n" +
                "  \n" +
                "]";
    }
}
