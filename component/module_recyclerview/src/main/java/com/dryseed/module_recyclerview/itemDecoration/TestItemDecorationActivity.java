package com.dryseed.module_recyclerview.itemDecoration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.dryseed.module_recyclerview.R;
import com.dryseed.module_recyclerview.SimpleRecyclerViewAdapter;
import com.easy.moduler.lib.test.data.DataGenerator;

import java.util.List;

/**
 * ItemDecoration
 * http://blog.csdn.net/briblue/article/details/70161917
 */
public class TestItemDecorationActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    List<String> mData;
    SimpleRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        GridItemDecoration gridItemDecoration = new GridItemDecoration(this, 5, 10);
        mRecyclerView.addItemDecoration(gridItemDecoration);
        mRecyclerView.setLayoutManager(gridLayoutManager);
    }
}
