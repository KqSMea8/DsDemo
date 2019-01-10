package com.dryseed.module_recyclerview.sticky;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.dryseed.module_recyclerview.R;

import java.util.List;

/**
 * @author caiminming
 */
public abstract class SimpleRecyclerViewAdapter extends RecyclerView.Adapter<SimpleRecyclerViewAdapter.SimpleViewHolder> {

    List<String> mData;

    IItemClickListener mItemClickListener;

    public SimpleRecyclerViewAdapter() {
    }

    public SimpleRecyclerViewAdapter(List<String> data) {
        this.mData = data;
    }

    public void setData(List<String> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    protected abstract int getLayoutId();

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        SimpleViewHolder holder = new SimpleViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        if (mData == null || mData.size() <= 0) {
            return;
        }
        String text = mData.get(position);
        holder.textView.setText(text);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClickListener(holder.itemView, position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_content);
            textView.setTextSize(38.0f);
        }

    }

    public void setItemClickListener(IItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface IItemClickListener {
        void onItemClickListener(View view, int position);
    }
}
