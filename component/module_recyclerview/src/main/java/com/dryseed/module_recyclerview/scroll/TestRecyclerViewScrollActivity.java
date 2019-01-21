package com.dryseed.module_recyclerview.scroll;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.dryseed.module_recyclerview.R;
import com.dryseed.module_recyclerview.SimpleRecyclerViewAdapter;
import com.easy.moduler.lib.test.data.DataGenerator;
import com.easy.moduler.lib.utils.DPIUtil;
import com.easy.moduler.lib.utils.RecyclerViewScrollUtils;

import java.util.List;

/**
 * @author caiminming
 */
public class TestRecyclerViewScrollActivity extends Activity {
    private RecyclerView mRecyclerView;
    List<String> mData;
    SimpleRecyclerViewAdapter mAdapter;
    LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_layout);

        initViews();

        mData = DataGenerator.generateStringData(20);
        mAdapter.setData(mData);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollToPositionWithOffsetY();
            }
        }, 1000);

    }

    /**
     * 带偏移量滚动到某个item
     */
    private void scrollToPositionWithOffsetY() {
        int position = 5;
        RecyclerViewScrollUtils.smoothScrollToPositionWithOffsetY(mRecyclerView, position, DPIUtil.dip2px(100));
    }

    private void initViews() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new SimpleRecyclerViewAdapter() {
            @Override
            protected int getLayoutId() {
                return R.layout.item_textview_layout;
            }
        };
        mRecyclerView.setAdapter(mAdapter);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        // 设置滑动到屏幕中央的item点击事件
        mAdapter.setItemClickListener(new SimpleRecyclerViewAdapter.IItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                View targetView = mLinearLayoutManager.findViewByPosition(position);
                OrientationHelper orientationHelper = OrientationHelper.createVerticalHelper(mLinearLayoutManager);
                RecyclerViewScrollUtils.smoothScrollDistanceToCenter(mRecyclerView, targetView, orientationHelper);
            }
        });
    }

}
