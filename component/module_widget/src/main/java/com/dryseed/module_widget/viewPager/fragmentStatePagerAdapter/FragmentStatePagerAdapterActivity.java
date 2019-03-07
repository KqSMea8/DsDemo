package com.dryseed.module_widget.viewPager.fragmentStatePagerAdapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dryseed.module_widget.R;
import com.dryseed.module_widget.viewPager.pagerSlidingTabStrip.PagerSlidingTabStrip;

/**
 * @author caiminming
 * <p>
 * 与FragmentPagerAdapter的区别：
 * 多了 onAttach -> onCreate
 * 多了 onDestroy -> onDetach
 * <p>
 * Log :
 * -- Page 1
 * -- ViewPager instantiateItem 0
 * -- ViewPager getItem 0
 * -- ViewPager instantiateItem 1
 * -- ViewPager getItem 1
 * -- RecyclerViewFragment 0 onAttach
 * -- RecyclerViewFragment 0 onCreate
 * -- RecyclerViewFragment 1 onAttach
 * -- RecyclerViewFragment 1 onCreate
 * -- RecyclerViewFragment 0 onCreateView
 * -- RecyclerViewFragment 1 onCreateView
 * -- Page 2
 * -- ViewPager instantiateItem 2
 * -- ViewPager getItem 2
 * -- RecyclerViewFragment 2 onAttach
 * -- RecyclerViewFragment 2 onCreate
 * -- RecyclerViewFragment 2 onCreateView
 * -- Page 3
 * -- ViewPager destroyItem 0
 * -- ViewPager instantiateItem 3
 * -- ViewPager getItem 3
 * -- RecyclerViewFragment 3 onAttach
 * -- RecyclerViewFragment 3 onCreate
 * -- RecyclerViewFragment 0 onDestroyView
 * -- RecyclerViewFragment 0 onDestroy
 * -- RecyclerViewFragment 0 onDetach
 * -- RecyclerViewFragment 3 onCreateView
 * -- Page 1
 * -- ViewPager instantiateItem 0
 * -- ViewPager getItem 0
 * -- RecyclerViewFragment 0 onAttach
 * -- RecyclerViewFragment 0 onCreate
 * -- RecyclerViewFragment 0 onCreateView
 * -- ViewPager destroyItem 2
 * -- ViewPager destroyItem 3
 * -- RecyclerViewFragment 2 onDestroyView
 * -- RecyclerViewFragment 2 onDestroy
 * -- RecyclerViewFragment 2 onDetach
 * -- RecyclerViewFragment 3 onDestroyView
 * -- RecyclerViewFragment 3 onDestroy
 * -- RecyclerViewFragment 3 onDetach
 */
public class FragmentStatePagerAdapterActivity extends AppCompatActivity {
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.tab_strip)
    PagerSlidingTabStrip mTabStrip;

    private MyFragmentStatePagerAdapter mFragmentStatePagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_fragment_state_pager_adapter_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        mFragmentStatePagerAdapter = new MyFragmentStatePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mFragmentStatePagerAdapter);
        mTabStrip.setViewPager(mViewPager);
    }
}
