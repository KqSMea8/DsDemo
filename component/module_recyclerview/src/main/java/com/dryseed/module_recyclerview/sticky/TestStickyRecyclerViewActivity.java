package com.dryseed.module_recyclerview.sticky;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dryseed.module_recyclerview.R;
import com.easy.moduler.lib.recyclerview.model.ChannelModel;
import com.easy.moduler.lib.recyclerview.model.LabelModel;
import com.easy.moduler.lib.recyclerview.recyclerlistadapter.RecyclerListAdapter;
import com.easy.moduler.lib.recyclerview.viewholder.ChannelViewHolder;
import com.easy.moduler.lib.recyclerview.viewholder.LabelViewHolder;
import com.easy.moduler.lib.utils.DPIUtil;
import com.easy.moduler.lib.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caiminming
 */
public class TestStickyRecyclerViewActivity extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerListAdapter mRecyclerListAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private List mData;
    private LinearLayout mSuspensionBar;
    private TextView mSuspensionTv;
    private int mCurrentPosition = 0;
    private int mSuspensionHeight = DPIUtil.dip2px(50);
    private int mCurrentLabelId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_recyclerview_layout);

        initDatas();
        initViews();
    }

    private void initViews() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerListAdapter = new RecyclerListAdapter();
        mRecyclerListAdapter.addViewType(ChannelModel.class, new RecyclerListAdapter.ViewHolderFactory<RecyclerListAdapter.ViewHolder>() {
            @Override
            public RecyclerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent) {
                return new ChannelViewHolder(parent);
            }
        });
        mRecyclerListAdapter.addViewType(LabelModel.class, new RecyclerListAdapter.ViewHolderFactory<RecyclerListAdapter.ViewHolder>() {
            @Override
            public RecyclerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent) {
                return new LabelViewHolder(parent);
            }
        });
        mRecyclerListAdapter.setItemList(mData);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mRecyclerListAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 两个label重叠时，修正mSuspensionBar位置
                if (mRecyclerListAdapter.getItemViewType(mCurrentPosition + 1) == mRecyclerListAdapter.getItemViewType(LabelModel.class)) {
                    View view = mLinearLayoutManager.findViewByPosition(mCurrentPosition + 1);
                    if (view != null) {
                        if (view.getTop() <= mSuspensionHeight) {
                            mSuspensionBar.setY(-(mSuspensionHeight - view.getTop()));
                        } else {
                            mSuspensionBar.setY(0);
                        }
                    }
                }

                int firstVisibleItemPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
                if (mCurrentPosition != firstVisibleItemPosition) {
                    LogUtils.d("mSuspensionBar.setY(0) : [CurrentPosition:%d][FirstVisibleItemPosition:%d]",
                            mCurrentPosition, firstVisibleItemPosition);
                    boolean isScrollDown = mCurrentPosition > firstVisibleItemPosition;
                    mCurrentPosition = firstVisibleItemPosition;
                    mSuspensionBar.setY(0);
                    //找到上一个label，判断是否是同一个，不是同一个就更新mSuspensionBar
                    boolean hasFoundLabel = false;
                    for (int i = mCurrentPosition; i >= 0; i--) {
                        if (mRecyclerListAdapter.getItemViewType(i) == mRecyclerListAdapter.getItemViewType(LabelModel.class)) {
                            hasFoundLabel = true;
                            LabelModel labelModel = (LabelModel) mRecyclerListAdapter.getItem(i);
                            if (mCurrentLabelId != labelModel.id) {
                                mCurrentLabelId = labelModel.id;
                                LogUtils.d("updateSuspensionBar : %d", mCurrentPosition);
                                updateSuspensionBar(labelModel, isScrollDown);
                                break;
                            }
                            break;
                        }
                    }
                    //没找到label，则影藏mSuspensionBar
                    if (!hasFoundLabel) {
                        mCurrentLabelId = -1;
                        updateSuspensionBar(null, false);
                    }
                }
            }
        });

        mSuspensionBar = findViewById(R.id.suspension_bar);
        mSuspensionTv = findViewById(R.id.title);
        if (mRecyclerListAdapter.getItemViewType(0) == mRecyclerListAdapter.getItemViewType(LabelModel.class)) {
            mSuspensionBar.setVisibility(View.VISIBLE);
        } else {
            mSuspensionBar.setVisibility(View.GONE);
        }
    }

    private void updateSuspensionBar(LabelModel labelModel, boolean isScrollDown) {
        if (labelModel == null) {
            mSuspensionBar.setVisibility(View.GONE);
            return;
        }

        if (isScrollDown) {
            mSuspensionTv.post(new Runnable() {
                @Override
                public void run() {
                    showSuspensionBar(labelModel);
                }
            });
        } else {
            showSuspensionBar(labelModel);
        }
    }

    private void showSuspensionBar(LabelModel labelModel) {
        mSuspensionTv.setText(labelModel.name);
        if (mSuspensionBar.getVisibility() == View.GONE) {
            mSuspensionBar.setVisibility(View.VISIBLE);
        }
    }

    private void initDatas() {
        mData = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            if (i % 5 == 0 && i != 0) {
                String labelStr;
                if (i == 5) {
                    labelStr = "最近访问 最多保存三个非常用频道";
                } else if (i == 10) {
                    labelStr = "影视综艺";
                } else {
                    labelStr = "Label" + i;
                }
                LabelModel labelModel = new LabelModel(i, labelStr);
                mData.add(labelModel);
            }
            ChannelModel channelModel = new ChannelModel("Channel " + i);
            mData.add(channelModel);
        }
    }
}
