package com.dryseed.module_navigation.lib;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.dryseed.module_navigation.lib.adapter.IPagerAdapter;
import com.dryseed.module_navigation.lib.config.LoaderType;
import com.dryseed.module_navigation.lib.entity.TabEntity;
import com.dryseed.module_navigation.lib.loader.LoaderManager;
import com.dryseed.module_navigation.lib.widget.TopMenuBasePagerSlidingTabStrip;
import com.dryseed.module_navigation.lib.widget.TopMenuPagerSlidingTabStrip;

import java.util.List;

/**
 * @author caiminming
 */
public class TopMenuView extends LinearLayout {

    private TopMenuPagerSlidingTabStrip mTabStrip;

    private ViewPager mViewPager;

    private PagerAdapter mPagerAdapter;

    private TopMenuManager mTopMenuManager;

    private LoaderManager mLoaderManager;

    public TopMenuView(Context context) {
        this(context, null);
    }

    public TopMenuView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTopMenuManager = new TopMenuManager();
        mLoaderManager = new LoaderManager();
        mLoaderManager.setTopMenuManager(mTopMenuManager);
        mTopMenuManager.setLoaderManager(mLoaderManager);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount < 2) {
            throw new RuntimeException("please make sure TomMenuView has more than two children !!!");
        }
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (view instanceof TopMenuPagerSlidingTabStrip) {
                mTabStrip = (TopMenuPagerSlidingTabStrip) view;
            } else if (view instanceof ViewPager) {
                mViewPager = (ViewPager) view;
            }
            if (mTabStrip != null && mViewPager != null) {
                break;
            }
        }
        if (mTabStrip == null || mViewPager == null) {
            throw new RuntimeException("please make sure TomMenuView has TabStrip & ViewPager !!!");
        }

        mTopMenuManager.setViewPager(mViewPager);
        mTopMenuManager.setTabStrip(mTabStrip);
    }

    /**
     * 设置Url数据
     *
     * @param url
     */
    public void setUrlData(String url) {
        mTopMenuManager.setData(LoaderType.LOADER_TYPE_URL, url);
    }

    /**
     * 设置Json数据
     *
     * @param json
     */
    public void setJsonData(String json) {
        mTopMenuManager.setData(LoaderType.LOADER_TYPE_JSON, json);
    }

    /**
     * 设置List数据
     *
     * @param tabEntityList
     */
    public void setListData(List<TabEntity> tabEntityList) {
        mTopMenuManager.setData(LoaderType.LOADER_TYPE_LIST, tabEntityList);
    }

    /**
     * 设置本地文件数据
     *
     * @param fileName
     */
    public void setAssetsFileData(String fileName) {
        mTopMenuManager.setData(LoaderType.LOADER_TYPE_LOCAL_FILE, fileName);
    }

    /**
     * 设置ViewPager的Adapter
     *
     * @param adapter
     */
    public void setAdapter(PagerAdapter adapter) {
        if (!(adapter instanceof IPagerAdapter)) {
            throw new RuntimeException("please make sure the adapter is instanceof IPagerAdapter !!!");
        }
        mPagerAdapter = adapter;
        if (mViewPager != null && mTabStrip != null) {
            mTopMenuManager.setAdapter(mPagerAdapter);
            mViewPager.setAdapter(mPagerAdapter);
        }
    }

    public TopMenuPagerSlidingTabStrip getTabStrip() {
        return mTabStrip;
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }
}
