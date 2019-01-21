package com.dryseed.module_recyclerview.multiTypeAdapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.dryseed.module_recyclerview.R;
import com.easy.moduler.lib.recyclerview.recyclerlistadapter.RecyclerListAdapter;
import com.easy.moduler.lib.test.bean.Post;

/**
 * Created by caiminming on 2017/12/12.
 */

public class PostItem extends RecyclerListAdapter.ViewHolder<Post> {

    private ImageView cover;
    private TextView title;

    public PostItem(@NonNull ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_layout, parent, false));
        cover = (ImageView) itemView.findViewById(R.id.cover);
        title = (TextView) itemView.findViewById(R.id.title);
    }

    @Override
    public void bind(Post item, int position) {
        cover.setImageResource(item.coverResId);
        title.setText(item.title);
    }
}
