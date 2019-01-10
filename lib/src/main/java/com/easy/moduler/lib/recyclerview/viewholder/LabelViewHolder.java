package com.easy.moduler.lib.recyclerview.viewholder;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.easy.moduler.lib.R;
import com.easy.moduler.lib.recyclerview.model.LabelModel;
import com.easy.moduler.lib.recyclerview.recyclerlistadapter.RecyclerListAdapter;

/**
 * @author caiminming
 */
public class LabelViewHolder extends RecyclerListAdapter.ViewHolder<LabelModel> {

    private TextView title;

    public LabelViewHolder(@NonNull ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_label_item, parent, false));
        title = (TextView) itemView.findViewById(R.id.title);
    }

    @Override
    public void bind(LabelModel item, int position) {
        title.setText(item.name);
    }
}
