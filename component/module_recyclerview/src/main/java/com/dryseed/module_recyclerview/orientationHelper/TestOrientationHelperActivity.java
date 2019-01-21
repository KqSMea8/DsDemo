package com.dryseed.module_recyclerview.orientationHelper;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.dryseed.module_recyclerview.R;
import com.dryseed.module_recyclerview.SimpleRecyclerViewAdapter;
import com.easy.moduler.lib.test.data.DataGenerator;
import com.easy.moduler.lib.utils.LogUtils;

import java.util.List;
import java.util.Locale;

/**
 * @author caiminming
 */
public class TestOrientationHelperActivity extends Activity {
    private RecyclerView mRecyclerView;
    private List<String> mData;
    private SimpleRecyclerViewAdapter mAdapter;
    private OrientationHelper mVerticalHelper;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_layout);

        initViews();

        mData = DataGenerator.generateStringData(20);
        mAdapter.setData(mData);
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
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 界面显示的第一个item
                    View child = mLinearLayoutManager.getChildAt(0);
                    int height = mVerticalHelper.getDecoratedMeasurement(child);
                    int decoratedStart = mVerticalHelper.getDecoratedStart(child);
                    int decoratedEnd = mVerticalHelper.getDecoratedEnd(child);
                    int totalSpace = mVerticalHelper.getTotalSpace();
                    String log = String.format(Locale.getDefault(), "[height:%d][decoratedStart:%d][decoratedEnd:%d][totalSpace:%d]",
                            height, decoratedStart, decoratedEnd, totalSpace);
                    LogUtils.d(log);
                    Toast.makeText(TestOrientationHelperActivity.this, log, Toast.LENGTH_SHORT).show();
                }
            }
        });

        createOrientationHelper(mLinearLayoutManager);
    }

    private OrientationHelper createOrientationHelper(LinearLayoutManager layoutManager) {
        if (mVerticalHelper == null || mVerticalHelper.getLayoutManager() != layoutManager) {
            mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return mVerticalHelper;
    }
}
