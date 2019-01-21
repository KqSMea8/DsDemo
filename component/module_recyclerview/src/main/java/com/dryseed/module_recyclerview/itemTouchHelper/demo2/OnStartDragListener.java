package com.dryseed.module_recyclerview.itemTouchHelper.demo2;

import android.support.v7.widget.RecyclerView;

/**
 * @author caiminming
 */
public interface OnStartDragListener {

    /**
     * Called when a view is requesting a start of a drag.
     *
     * @param viewHolder The holder of the view to drag.
     */
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}