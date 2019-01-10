package com.easy.moduler.lib.recyclerview.viewholder;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.easy.moduler.lib.R;
import com.easy.moduler.lib.recyclerview.model.ChannelModel;
import com.easy.moduler.lib.recyclerview.recyclerlistadapter.RecyclerListAdapter;

/**
 * @author caiminming
 */
public class ChannelViewHolder extends RecyclerListAdapter.ViewHolder<ChannelModel> {

    private TextView title;

    public ChannelViewHolder(@NonNull ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_channel_item, parent, false));
        title = (TextView) itemView.findViewById(R.id.title);
    }

    @Override
    public void bind(ChannelModel item, int position) {
        title.setText(item.name);
    }
}
