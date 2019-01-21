package com.dryseed.module_recyclerview.itemTouchHelper.demo1;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;
import com.dryseed.module_recyclerview.R;
import com.dryseed.module_recyclerview.SimpleRecyclerViewAdapter;
import com.easy.moduler.lib.test.data.DataGenerator;

import java.util.Collections;
import java.util.List;

public class TestItemTouchHelperActivity extends Activity implements ItemTouchHelperListener {
    RecyclerView mRecyclerView;
    List<String> mData;
    SimpleRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_layout);

        initViews();
        initItemTouchHelper();

        mData = DataGenerator.generateStringData(20);
        mAdapter.setData(mData);

        test();
    }

    private void test() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mData.remove(3);
                mAdapter.notifyItemRemoved(3);
                Toast.makeText(TestItemTouchHelperActivity.this, "size : " + mData.size(), Toast.LENGTH_SHORT).show();
            }
        }, 2000);
    }

    private void initItemTouchHelper() {
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        //交换位置
        Collections.swap(mData, fromPosition, toPosition);
        mAdapter.notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDissmiss(int position) {
        //移除数据
        mData.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onItemFinished(int position) {

    }

    @Override
    public void onItemStart(int position) {

    }
}
