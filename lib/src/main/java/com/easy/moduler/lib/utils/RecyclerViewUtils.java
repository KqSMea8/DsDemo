package com.easy.moduler.lib.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * RecyclerView工具类
 */
public class RecyclerViewUtils {

    private RecyclerViewUtils() {
    }

    /**
     * 获取第一条展示的位置
     *
     * @param recyclerView the recycler view
     * @return the first visible position
     * @see android.widget.ListView#getFirstVisiblePosition() android.widget.ListView#getFirstVisiblePosition()android.widget
     * .ListView#getFirstVisiblePosition()
     * @see android.widget.AbsListView.OnScrollListener#onScroll(android.widget.AbsListView, int, int, int) android.widget
     * .AbsListView.OnScrollListener#onScroll(android.widget.AbsListView, int, int, int)android.widget .AbsListView
     * .OnScrollListener#onScroll(android.widget.AbsListView, int, int, int)
     */
    public static int getFirstVisiblePosition(RecyclerView recyclerView) {
        int position;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        } else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
            int[] lastPositions = layoutManager.findFirstVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMinPositions(lastPositions);
        } else {
            position = 0;
        }
        return position;
    }


    /**
     * 获取第一条展示的位置
     *
     * @param recyclerView the recycler view
     * @return the first completely visible position
     * @see android.widget.ListView#getFirstVisiblePosition() android.widget.ListView#getFirstVisiblePosition()android.widget
     * .ListView#getFirstVisiblePosition()
     * @see android.widget.AbsListView.OnScrollListener#onScroll(android.widget.AbsListView, int, int, int) android.widget
     * .AbsListView.OnScrollListener#onScroll(android.widget.AbsListView, int, int, int)android.widget .AbsListView
     * .OnScrollListener#onScroll(android.widget.AbsListView, int, int, int)
     */
    public static int getFirstCompletelyVisiblePosition(RecyclerView recyclerView) {
        int position;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        } else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
            int[] lastPositions = layoutManager.findFirstCompletelyVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMinPositions(lastPositions);
        } else {
            position = 0;
        }
        return position;
    }

    /**
     * 获得当前展示最小的position
     */
    private static int getMinPositions(int[] positions) {
        int minPosition = Integer.MAX_VALUE;
        for (int position : positions) {
            minPosition = Math.min(minPosition, position);
        }
        return minPosition;
    }

    /**
     * 获取最后一条展示的位置
     *
     * @param recyclerView the recycler view
     * @return the last visible position
     * @see android.widget.ListView#getFirstVisiblePosition() android.widget.ListView#getFirstVisiblePosition()android.widget
     * .ListView#getFirstVisiblePosition()
     * @see android.widget.AbsListView.OnScrollListener#onScroll(android.widget.AbsListView, int, int, int) android.widget
     * .AbsListView.OnScrollListener#onScroll(android.widget.AbsListView, int, int, int)android.widget .AbsListView
     * .OnScrollListener#onScroll(android.widget.AbsListView, int, int, int)
     */
    public static int getLastVisiblePosition(RecyclerView recyclerView) {
        int position;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
        } else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
            int[] lastPositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMaxPosition(lastPositions);
        } else {
            position = recyclerView.getLayoutManager().getItemCount() - 1;
        }
        return position;
    }

    /**
     * 获取最后一条展示的位置
     *
     * @param recyclerView the recycler view
     * @return the last completely visible position
     * @see android.widget.ListView#getFirstVisiblePosition() android.widget.ListView#getFirstVisiblePosition()android.widget
     * .ListView#getFirstVisiblePosition()
     * @see android.widget.AbsListView.OnScrollListener#onScroll(android.widget.AbsListView, int, int, int) android.widget
     * .AbsListView.OnScrollListener#onScroll(android.widget.AbsListView, int, int, int)android.widget .AbsListView
     * .OnScrollListener#onScroll(android.widget.AbsListView, int, int, int)
     */
    public static int getLastCompletelyVisiblePosition(RecyclerView recyclerView) {
        int position;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
        } else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
            int[] lastPositions = layoutManager.findLastCompletelyVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMaxPosition(lastPositions);
        } else {
            position = recyclerView.getLayoutManager().getItemCount() - 1;
        }
        return position;
    }

    /**
     * 获得最大的位置
     */
    private static int getMaxPosition(int[] positions) {
        int maxPosition = Integer.MIN_VALUE;
        for (int position : positions) {
            maxPosition = Math.max(maxPosition, position);
        }
        return maxPosition;
    }

    /**
     * 获得当前可见的item数量
     *
     * @param recyclerView the recycler view
     * @return the visible item count
     * @see android.widget.AbsListView.OnScrollListener#onScroll(android.widget.AbsListView, int, int, int) android.widget
     * .AbsListView.OnScrollListener#onScroll(android.widget.AbsListView, int, int, int)android.widget .AbsListView
     * .OnScrollListener#onScroll(android.widget.AbsListView, int, int, int)
     */
    public static int getVisibleItemCount(RecyclerView recyclerView) {
        return getLastVisiblePosition(recyclerView) - getFirstVisiblePosition(recyclerView) + 1;
    }

    /**
     * 获得所有的item数量
     *
     * @param recyclerView the recycler view
     * @return the total item count
     * @see android.widget.AbsListView.OnScrollListener#onScroll(android.widget.AbsListView, int, int, int) android.widget
     * .AbsListView.OnScrollListener#onScroll(android.widget.AbsListView, int, int, int)android.widget .AbsListView
     * .OnScrollListener#onScroll(android.widget.AbsListView, int, int, int)
     */
    public static int getTotalItemCount(RecyclerView recyclerView) {
        return recyclerView.getLayoutManager().getItemCount();
    }

    /**
     * 跳转到top+pos的位置
     *
     * @param recyclerView the recycler view
     * @param position     the position
     * @param top          the top
     * @see android.widget.ListView#setSelectionFromTop(int, int) android.widget.ListView#setSelectionFromTop(int, int)android
     * .widget.ListView#setSelectionFromTop(int, int)
     */
    public static void setSelectionFromTop(RecyclerView recyclerView, int position, int top) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(position, top);
        }
    }

    /**
     * 跳转到position位置
     *
     * @param recyclerView the recycler view
     * @param position     the position
     * @see android.widget.ListView#setSelection(int) android.widget.ListView#setSelection(int)android.widget
     * .ListView#setSelection(int)
     */
    public static void setSelection(RecyclerView recyclerView, int position) {
        recyclerView.scrollToPosition(position);
    }
}
