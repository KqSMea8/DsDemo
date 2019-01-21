package com.easy.moduler.lib.recyclerview.viewholder;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.easy.moduler.lib.R;
import com.easy.moduler.lib.recyclerview.model.LabelModel;
import com.easy.moduler.lib.recyclerview.recyclerlistadapter.RecyclerListAdapter;

/**
 * @author caiminming
 */
public class LabelViewHolder extends RecyclerListAdapter.ViewHolder<LabelModel> {

    private TextView mTitle;
    private OnItemClickListener mOnItemClickListener;

    public LabelViewHolder(@NonNull ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_label_item, parent, false));
        mTitle = (TextView) itemView.findViewById(R.id.title);
    }

    public LabelViewHolder(@NonNull ViewGroup parent, OnItemClickListener onItemClickListener) {
        this(parent);
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void bind(LabelModel item, int position) {
        mTitle.setText(item.name);
        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
