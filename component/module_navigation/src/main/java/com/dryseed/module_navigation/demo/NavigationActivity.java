package com.dryseed.module_navigation.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.dryseed.module_navigation.R;
import com.dryseed.module_navigation.lib.TopMenuView;
import com.dryseed.module_navigation.lib.entity.TabEntity;
import com.easy.moduler.lib.BaseActivity;

import java.util.List;

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

        {
            //1. list data
            List<TabEntity> list = DataProvider.getListData();
            mTopMenuView.setListData(list);
        }
        /*{
            //2. json data
            String jsonArrayStr = DataProvider.getJsonData();
            mTopMenuView.setJsonData(jsonArrayStr);
        }*/

        /*{
            //3. assets file data
            String assetsFileName = "data.json";
            mTopMenuView.setAssetsFileData(assetsFileName);
        }*/
    }
}
