package com.dryseed.module_widget.viewPager.linkedViewPager.baseViewPager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dryseed.module_widget.R;

/**
 * 两个ViewPager联动
 * 参考：https://www.jianshu.com/p/a9518ec62640
 *
 * @author caiminming
 */
public class LinkedViewPagerActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.linked_view_pager)
    ViewPager mLinkedViewPager;

    private InfiniteLinkedViewPagerAdapter mAdapter;
    private InfiniteLinkedViewPagerAdapter mLinkedAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linked_view_pager_fragment_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        mAdapter = new InfiniteLinkedViewPagerAdapter();
        mViewPager.addOnPageChangeListener(new BaseLinkPageChangeListener(mViewPager, mLinkedViewPager));
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mAdapter);

        mLinkedAdapter = new InfiniteLinkedViewPagerAdapter();
        mLinkedViewPager.addOnPageChangeListener(new BaseLinkPageChangeListener(mLinkedViewPager, mViewPager));
        mLinkedViewPager.setOffscreenPageLimit(2);
        mLinkedViewPager.setAdapter(mLinkedAdapter);

        //不支持，因为scrollTo的计算会出现问题
        //mViewPager.setCurrentItem(500);
        //mLinkedViewPager.setCurrentItem(500);
    }

}
