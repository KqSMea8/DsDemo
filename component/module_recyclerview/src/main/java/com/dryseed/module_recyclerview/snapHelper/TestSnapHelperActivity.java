package com.dryseed.module_recyclerview.snapHelper;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import com.dryseed.module_recyclerview.R;
import com.dryseed.module_recyclerview.SimpleRecyclerViewAdapter;
import com.easy.moduler.lib.test.data.DataGenerator;

import java.util.List;

/**
 * @author caiminming
 */
public class TestSnapHelperActivity extends Activity {
    private RecyclerView mRecyclerView;
    List<String> mData;
    SimpleRecyclerViewAdapter mAdapter;

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        // important
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        //PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
    }

}
