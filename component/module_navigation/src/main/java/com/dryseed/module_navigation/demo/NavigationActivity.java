package com.dryseed.module_navigation.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.dryseed.module_navigation.R;
import com.dryseed.module_navigation.lib.TopMenuView;
import com.easy.moduler.lib.BaseActivity;

/**
 * @author caiminming
 */
public class NavigationActivity extends BaseActivity {

    TopMenuView mTopMenuView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_layout);

        mTopMenuView = findViewById(R.id.top_menu);

        //step1 : set adapter
        mTopMenuView.setAdapter(new PagerSlidingTabStripAdapter(getSupportFragmentManager()));

        //step2 : set data
        /*{
            //1. list data
            ArrayList<TopMenuTabEntity> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                TopMenuTabEntity entity = new TopMenuTabEntity();
                entity.setId(i + "");
                entity.setTitle("tab " + i);
                list.add(entity);
            }
            mTopMenuView.setListData(list);
        }*/

        /*{
            //2. json data
            String jsonArrayStr =
                    "[\n" +
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
            mTopMenuView.setJsonData(jsonArrayStr);
        }*/

        {
            mTopMenuView.setAssetsFileData("data.json");
        }
    }
}
