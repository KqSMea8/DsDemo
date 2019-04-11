package com.dryseed.module_navigation.lib;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import com.dryseed.module_navigation.lib.adapter.IPagerAdapter;
import com.dryseed.module_navigation.lib.config.LoaderType;
import com.dryseed.module_navigation.lib.entity.TabEntity;
import com.dryseed.module_navigation.lib.loader.ILoaderListener;
import com.dryseed.module_navigation.lib.loader.LoaderManager;
import com.dryseed.module_navigation.lib.widget.TopMenuPagerSlidingTabStrip;

import java.util.ArrayList;

/**
 * @author caiminming
 */
public class TopMenuManager {

    PagerAdapter mPagerAdapter;

    LoaderManager mLoaderManager;

    ViewPager mViewPager;

    TopMenuPagerSlidingTabStrip mTabStrip;

    public TopMenuManager() {

    }

    /**
     * 设置数据
     *
     * @param loaderType 加载数据类型
     * @param data       要设置的数据
     * @param <T>        设置数据的不同类型
     */
    public <T> void setData(LoaderType loaderType, T data) {
        if (mLoaderManager == null) {
            return;
        }
        mLoaderManager.processData(loaderType, data, new ILoaderListener() {
            @Override
            public void onSuccess(ArrayList<TabEntity> tabEntities) {
                if (mPagerAdapter != null && mPagerAdapter instanceof IPagerAdapter) {
                    ((IPagerAdapter) mPagerAdapter).setData(tabEntities);
                }
                if (mTabStrip != null && mViewPager != null) {
                    mTabStrip.setViewPager(mViewPager);
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    public void setAdapter(PagerAdapter pagerAdapter) {
        mPagerAdapter = pagerAdapter;
    }


    public void setLoaderManager(LoaderManager loaderManager) {
        mLoaderManager = loaderManager;
    }

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
    }

    public void setTabStrip(TopMenuPagerSlidingTabStrip tabStrip) {
        mTabStrip = tabStrip;
    }

}
