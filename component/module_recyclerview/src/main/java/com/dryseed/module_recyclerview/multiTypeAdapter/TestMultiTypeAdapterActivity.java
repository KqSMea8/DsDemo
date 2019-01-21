package com.dryseed.module_recyclerview.multiTypeAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dryseed.module_recyclerview.R;
import com.easy.moduler.lib.recyclerview.recyclerlistadapter.RecyclerListAdapter;
import com.easy.moduler.lib.test.bean.Post;
import com.easy.moduler.lib.test.data.DataGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caiminming on 2017/12/12.
 */

public class TestMultiTypeAdapterActivity extends Activity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private static final int SPAN_COUNT = 2;
    private RecyclerListAdapter mRecyclerListAdapter;
    private List<Object> mItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_multi_type_adapter_layout);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    public void initData() {
        List list = DataGenerator.generatePostsData();
        mItems.addAll(list);
    }

    private void initView() {
        //LayoutManager
        final GridLayoutManager layoutManager = new GridLayoutManager(this, SPAN_COUNT);
        mRecyclerView.setLayoutManager(layoutManager);

        //Items
        mRecyclerListAdapter = new RecyclerListAdapter();
        mRecyclerListAdapter.setItemList(mItems);

        //ViewType
        mRecyclerListAdapter.addViewType(
                Post.class, new RecyclerListAdapter.ViewHolderFactory<RecyclerListAdapter.ViewHolder>() {
                    @Override
                    public RecyclerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent) {
                        return new PostItem(parent);
                    }
                });

        //Adapter
        mRecyclerView.setAdapter(mRecyclerListAdapter);
    }
}
