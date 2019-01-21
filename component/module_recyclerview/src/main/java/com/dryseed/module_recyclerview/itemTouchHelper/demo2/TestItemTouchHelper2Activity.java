package com.dryseed.module_recyclerview.itemTouchHelper.demo2;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;
import com.dryseed.module_recyclerview.R;
import com.dryseed.module_recyclerview.itemTouchHelper.demo1.ItemTouchHelperListener;
import com.easy.moduler.lib.test.data.DataGenerator;

import java.util.Collections;
import java.util.List;

/**
 * @author caiminming
 */
public class TestItemTouchHelper2Activity extends Activity implements ItemTouchHelperListener, OnStartDragListener {
    RecyclerView mRecyclerView;
    List<String> mData;
    ItemTouchHelperAdapter mAdapter;
    ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_layout);

        mData = DataGenerator.generateStringData(20);

        initViews();
        initItemTouchHelper();
    }

    private void initItemTouchHelper() {
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(this);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void initViews() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new ItemTouchHelperAdapter(mData, this);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
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
        Toast.makeText(this, "onItemFinished", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemStart(int position) {
        Toast.makeText(this, "onItemStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder.getAdapterPosition() == 0) {
            return;
        }
        mItemTouchHelper.startDrag(viewHolder);
    }
}

