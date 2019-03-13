package com.dryseed.module_recyclerview.addAnimationOnItem;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dryseed.module_recyclerview.R;
import com.easy.moduler.lib.BaseActivity;
import com.easy.moduler.lib.test.data.DataGenerator;

import java.util.List;

/**
 * @author caiminming
 */
public class RecyclerViewAddAnimationOnItemActivity extends BaseActivity {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    List<String> mData;
    RecyclerViewAddAnimationOnItemAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_add_animation_on_item_layout);
        ButterKnife.bind(this);

        initViews();

        mData = DataGenerator.generateStringData(20);
        mAdapter.setData(mData);
    }

    private void initViews() {
        mAdapter = new RecyclerViewAddAnimationOnItemAdapter();
        mRecyclerView.setAdapter(mAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING && mAdapter != null) {
                    mAdapter.stopAnimation();
                }
            }
        });
    }
}
