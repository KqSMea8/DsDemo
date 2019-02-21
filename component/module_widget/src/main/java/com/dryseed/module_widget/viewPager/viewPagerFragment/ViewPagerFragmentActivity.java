package com.dryseed.module_widget.viewPager.viewPagerFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dryseed.module_widget.R;

/**
 * @author caiminming
 */
public class ViewPagerFragmentActivity extends FragmentActivity {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.text1)
    TextView mTextView1;
    @BindView(R.id.text2)

    TextView mTextView2;

    @BindView(R.id.text3)
    TextView mTextView3;

    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_fragment_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        mAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mTextView1.setText("position : " + position);
                mTextView2.setText("positionOffset : " + positionOffset);
                mTextView3.setText("positionOffsetPixels : " + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
