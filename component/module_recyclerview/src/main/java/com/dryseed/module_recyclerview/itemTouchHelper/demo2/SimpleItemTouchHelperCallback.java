package com.dryseed.module_recyclerview.itemTouchHelper.demo2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Vibrator;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.dryseed.module_recyclerview.itemTouchHelper.demo1.ItemTouchHelperListener;
import com.easy.moduler.lib.utils.DPIUtil;
import com.easy.moduler.lib.viewanimator.ViewAnimator;

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ItemTouchHelperListener mItemTouchHelperListener;

    public SimpleItemTouchHelperCallback(ItemTouchHelperListener itemTouchHelperListener) {
        mItemTouchHelperListener = itemTouchHelperListener;
    }

    /**
     * 该方法返回true时，表示如果用户触摸并左右滑动了View，那么可以执行滑动删除操作，即可以调用到onSwiped()方法。默认是返回true。
     *
     * @return
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    /**
     * 该方法返回true时，表示支持长按拖动，即长按ItemView后才可以拖动，我们遇到的场景一般也是这样的。默认是返回true。
     *
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    /**
     * 该方法用于返回可以滑动的方向，比如说允许从右到左侧滑，允许上下拖动等。
     * 我们一般使用makeMovementFlags(int,int)或makeFlag(int, int)来构造我们的返回值。
     *
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        Log.d("MMM", "getMovementFlags " + viewHolder.getAdapterPosition());
        return makeMovementFlags(
                (ItemTouchHelper.UP | ItemTouchHelper.DOWN),
                ItemTouchHelper.START | ItemTouchHelper.END);
    }

    /**
     * 当用户拖动一个Item进行上下移动从旧的位置到新的位置的时候会调用该方法，在该方法内，我们可以调用Adapter的notifyItemMoved方法来交换两个ViewHolder的位置，
     * 最后返回true，表示被拖动的ViewHolder已经移动到了目的位置。所以，如果要实现拖动交换位置，可以重写该方法（前提是支持上下拖动）
     *
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @SuppressLint("MissingPermission")
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Log.d("MMM", "onMove " + viewHolder.getAdapterPosition() + "|" + target.getAdapterPosition());
        if (target.getAdapterPosition() == 0) {
            return false;
        }
        if (null != mItemTouchHelperListener) {
            mItemTouchHelperListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }
        Vibrator vibrator = (Vibrator) recyclerView.getContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(50);
        return true;
    }

    /**
     * 当用户左右滑动Item达到删除条件时，会调用该方法，一般手指触摸滑动的距离达到RecyclerView宽度的一半时，再松开手指，
     * 此时该Item会继续向原先滑动方向滑过去并且调用onSwiped方法进行删除，否则会反向滑回原来的位置。
     *
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.d("MMM", "onSwiped " + viewHolder.getAdapterPosition());
        if (null != mItemTouchHelperListener) {
            mItemTouchHelperListener.onItemDissmiss(viewHolder.getAdapterPosition());
        }
    }

    /**
     * 从静止状态变为拖拽或者滑动的时候会回调该方法，参数actionState表示当前的状态。
     *
     * @param viewHolder
     * @param actionState actionState One of {@link ItemTouchHelper#ACTION_STATE_IDLE},
     *                    {@link ItemTouchHelper#ACTION_STATE_SWIPE} or
     *                    {@link ItemTouchHelper#ACTION_STATE_DRAG}.
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        Log.d("MMM", "onSelectedChanged " + actionState);
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            //aminScaleView(viewHolder.itemView, 1.1f);

            ViewCompat.setTranslationZ(viewHolder.itemView, DPIUtil.dip2px(3));

            if (null != mItemTouchHelperListener) {
                mItemTouchHelperListener.onItemStart(viewHolder.getAdapterPosition());
            }
        }
    }

    /**
     * 当用户操作完毕某个item并且其动画也结束后会调用该方法，一般我们在该方法内恢复ItemView的初始状态，防止由于复用而产生的显示错乱问题。
     *
     * @param recyclerView
     * @param viewHolder
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        //aminScaleView(viewHolder.itemView, 1);

        //ViewCompat.setTranslationZ(viewHolder.itemView, 0);

        if (null != mItemTouchHelperListener) {
            mItemTouchHelperListener.onItemFinished(viewHolder.getAdapterPosition());
        }
    }

    private void aminScaleView(View itemView, float scale) {
        ViewAnimator
                .animate(itemView)
                .scale(scale)
                .duration(300)
                .interpolator(new AccelerateDecelerateInterpolator())
                .start();
    }
}
