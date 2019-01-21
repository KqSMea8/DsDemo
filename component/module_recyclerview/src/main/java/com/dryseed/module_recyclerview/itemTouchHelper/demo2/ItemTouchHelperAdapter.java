package com.dryseed.module_recyclerview.itemTouchHelper.demo2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.dryseed.module_recyclerview.R;

import java.util.List;

/**
 * @author caiminming
 */
public class ItemTouchHelperAdapter extends RecyclerView.Adapter<ItemTouchHelperAdapter.SimpleHolder> {

    List<String> mData;
    OnStartDragListener mOnStartDragListener;

    public ItemTouchHelperAdapter(List<String> data, OnStartDragListener onStartDragListener) {
        this.mData = data;
        this.mOnStartDragListener = onStartDragListener;
    }

    public void setData(List<String> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public SimpleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_touch_helper_adapter_layout, parent, false);
        SimpleHolder holder = new SimpleHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final SimpleHolder holder, int position) {
        if (holder == null) {
            return;
        }

        if (mData != null && mData.size() > 0) {
            String text = mData.get(position);
            holder.mTextView.setText(text);
        }

        holder.mImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnStartDragListener.onStartDrag(holder);
                return false;
            }
        });

        /*holder.mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(holder);
                }
                return false;
            }
        });*/
    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class SimpleHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private ImageView mImageView;

        public SimpleHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.title);
            mImageView = itemView.findViewById(R.id.menu);
        }

    }
}
