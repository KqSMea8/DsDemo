# TODO
* 顶导航Tab样式


# 接入
### Step1 : 布局文件
* 在自定义控件TopMenuView中添加两个子View：AdvancedPagerSlidingTabStrip、ViewPager
    ```
    <?xml version="1.0" encoding="utf-8"?>
    <com.dryseed.module_navigation.lib.TopMenuView
            xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:strip="http://schemas.android.com/apk/res-auto"
            android:id="@+id/top_menu"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical">

        <com.dryseed.module_navigation.lib.widget.AdvancedPagerSlidingTabStrip
                android:background="#cccccc"
                android:id="@+id/tab_strip"
                android:layout_width="match_parent"
                android:layout_height="48dip"
                android:textColor="#000000"
                android:textSize="15dp"
                strip:pstsDividerColor="#00000000"
                strip:pstsIndicatorColor="#f02b2b"
                strip:pstsIndicatorHeight="3dp"
                strip:pstsScrollToCenter="true"
                strip:pstsShouldExpand="false"
                strip:pstsTabBackground="@android:color/transparent"
                strip:pstsTabPaddingLeftRight="11dp"
                strip:pstsUnderlineColor="#00000000"
                strip:pstsUnderlineHeight="5dp"
                strip:selectedTabTextColor="#f02b2b"/>

        <android.support.v4.view.ViewPager
                android:background="#222222"
                android:id="@+id/view_pager"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"/>
    </com.dryseed.module_navigation.lib.TopMenuView>
    ```

### Step2 : 设置Adapter
* 创建并设置ViewPager的Adapter，需继承TopMenuPagerAdapter。
    ```
    TopMenuView mTopMenuView = findViewById(R.id.top_menu);
    mTopMenuView.setAdapter(new PagerSlidingTabStripAdapter(getSupportFragmentManager()));
    ```

### Step3 : 设置数据源
* 设置字典列表（ArrayList<TopMenuTabEntity>）
    ```
    mTopMenuView.setListData(list);
    ```
* 设置Json数据
    ```
    mTopMenuView.setJsonData(jsonArrayStr);
    ```
* 设置Assets文件，文件内部存储json数据
    ```
    mTopMenuView.setAssetsFileData("<assets file name>");
    ```
* 设置请求Url（TODO）
    ```
    mTopMenuView.setUrlData(url);
    ```





























