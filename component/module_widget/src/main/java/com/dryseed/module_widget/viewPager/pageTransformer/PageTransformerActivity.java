package com.dryseed.module_widget.viewPager.pageTransformer;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dryseed.module_widget.R;

/**
 * @author caiminming
 */
public class PageTransformerActivity extends Activity {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.text)
    TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_transformer_layout);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        mViewPager.setAdapter(new PageTransformerAdapter());
        //mViewPager.setPageTransformer(true, new DepthPageTransformer());
        mViewPager.setPageTransformer(true, new RotateDownPageTransformer());
    }
}
