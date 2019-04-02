package com.dryseed.module_widget.tabLayout;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dryseed.module_widget.R;
import com.dryseed.module_widget.viewPager.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caiminming on 2017/1/17.
 */
public class TabLayoutActivity extends FragmentActivity {
    @BindView(R.id.tl)
    TabLayout mTabLayout;

    @BindView(R.id.vp)
    ViewPager mViewPager;

    private FragmentAdapter mFragmentAdapter;
    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        mDatas = new ArrayList<>();
        mDatas.add("推荐");
        mDatas.add("爱上超模");
        mDatas.add("电视剧");
        mDatas.add("电影");
        mDatas.add("综艺");
        mDatas.add("动漫");
        mDatas.add("订阅");
        mDatas.add("北京");
        mDatas.add("资讯");
        mDatas.add("娱乐");
        mDatas.add("搞笑");
        mDatas.add("儿童");
        mDatas.add("原创");

        for (String str : mDatas) {
            mTabLayout.addTab(mTabLayout.newTab().setText(str));
        }

        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), mDatas);
        mViewPager.setAdapter(mFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    class FragmentAdapter extends FragmentPagerAdapter {

        private List<String> strings;

        public FragmentAdapter(FragmentManager fm, List<String> strings) {
            super(fm);
            this.strings = strings;
        }

        @Override
        public Fragment getItem(int position) {
            return new BaseFragment(position);
        }

        @Override
        public int getCount() {
            return strings.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return strings.get(position);
        }

    }
}
