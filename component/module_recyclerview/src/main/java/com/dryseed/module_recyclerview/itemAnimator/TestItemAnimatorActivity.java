package com.dryseed.module_recyclerview.itemAnimator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.dryseed.module_recyclerview.R;
import com.easy.moduler.lib.recyclerview.model.LabelModel;
import com.easy.moduler.lib.recyclerview.recyclerlistadapter.RecyclerListAdapter;
import com.easy.moduler.lib.recyclerview.viewholder.LabelViewHolder;
import com.easy.moduler.lib.test.data.DataGenerator;
import com.easy.moduler.lib.utils.LogUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by caiminming on 2018/1/4.
 */

public class TestItemAnimatorActivity extends Activity {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    RecyclerListAdapter mRecyclerListAdapter;
    List<LabelModel> mData = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_item_animator_layout);
        ButterKnife.bind(this);

        initData();
        initViews();
    }

    private void initData() {
        mData = DataGenerator.generateLabelModelData(5);
    }

    private void initViews() {
        mRecyclerListAdapter = new RecyclerListAdapter();
        mRecyclerListAdapter.addViewType(LabelModel.class, new RecyclerListAdapter.ViewHolderFactory<RecyclerListAdapter.ViewHolder>() {
            @Override
            public RecyclerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent) {
                return new LabelViewHolder(parent, new LabelViewHolder.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        removeItem(position);
                    }
                });
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mRecyclerListAdapter);
        //mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //mRecyclerView.setItemAnimator(new SlideInOutLeftItemAnimator(mRecyclerView));
        mRecyclerView.setItemAnimator(new SlideScaleInOutRightItemAnimator(mRecyclerView));
        mRecyclerListAdapter.setItemList(mData);
    }

    @OnClick(R.id.add)
    void onAddBtnClick() {
        int id;
        if (mRecyclerListAdapter.getItemCount() > 0) {
            LabelModel lastLabelModel = (LabelModel) mRecyclerListAdapter.getItem(mRecyclerListAdapter.getItemCount() - 1);
            id = lastLabelModel.id + 1;
        } else {
            id = 0;
        }
        mData.add(new LabelModel(id, id + " test "));
        mRecyclerListAdapter.notifyItemInserted(mData.size() - 1);
    }

    @OnClick(R.id.delete)
    void onDelBtnClick() {
        int position = mData.size() - 1;
        removeItem(position);
    }

    private void removeItem(int position) {
        if (position < 0 || position >= mData.size()) {
            return;
        }
        mData.remove(position);
        mRecyclerListAdapter.notifyItemRemoved(position);
        mRecyclerListAdapter.notifyItemRangeChanged(position, mData.size() - position);
    }

}
