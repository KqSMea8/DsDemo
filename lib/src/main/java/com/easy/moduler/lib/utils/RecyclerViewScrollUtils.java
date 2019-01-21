package com.easy.moduler.lib.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;

/**
 * RecyclerView Scroll 工具类
 */
public class RecyclerViewScrollUtils {

    private RecyclerViewScrollUtils() {

    }

    /**
     * @param mRowViewGroup RecyclerView的某一行
     * @param topDiff       当前row 相对键盘的差值
     */
    public static void triggerMatchSmoothScroll(final View mRowViewGroup, final int topDiff) {
        if (topDiff < 0) {
            smoothScrollBy(mRowViewGroup, -topDiff);
        }
    }

    public static void smoothScrollBy(View mRowViewGroup, int offset) {
        if (mRowViewGroup == null) {
            return;
        }
        ViewParent parent = mRowViewGroup.getParent();
        if (parent instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) parent;
            recyclerView.smoothScrollBy(0, offset);
        }
    }

    public static void smoothScrollToPosition(View mRowViewGroup, int position) {
        if (mRowViewGroup == null) {
            return;
        }
        ViewParent parent = mRowViewGroup.getParent();
        if (parent instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) parent;
            recyclerView.smoothScrollToPosition(position);
        }
    }


    public static void smoothScrollToTop(RecyclerView recyclerView, int position) {
        if (recyclerView == null) {
            return;
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearSmoothScroller smoothScroller = new LinearSmoothScroller(
                    recyclerView.getContext()) {
                @Override
                protected int getVerticalSnapPreference() {
                    return LinearSmoothScroller.SNAP_TO_START;
                }

                @Override
                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    return 100f / displayMetrics.densityDpi;
                }
            };
            smoothScroller.setTargetPosition(position);
            layoutManager.startSmoothScroll(smoothScroller);
        }
    }

    /**
     * 按距离头部的比例滑动
     *
     * @param recyclerView 滑动的RecyclerView
     * @param position     移动的子View
     * @param rate         滑动比例 0 表示滑动到头部, 1 表示尾部， 0 - 1 表示目标View中心线距离顶部的位置
     */
    public static void smoothScrollToTopByRate(final RecyclerView recyclerView, int position,
                                               final int videoViewHeight, final float rate) {
        if (recyclerView == null) {
            return;
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearSmoothScroller smoothScroller = new LinearSmoothScroller(
                    recyclerView.getContext()) {
                @Override
                protected int getVerticalSnapPreference() {
                    if (rate <= 0 || rate > 1) {
                        return LinearSmoothScroller.SNAP_TO_START;
                    } else if (rate == 1) {
                        return LinearSmoothScroller.SNAP_TO_END;
                    } else {
                        return LinearSmoothScroller.SNAP_TO_ANY;
                    }
                }

                @Override
                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    return 100f / displayMetrics.densityDpi;
                }

                @Override
                public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
                    if (rate > 0 && rate < 1) {
                        int boxHeight = boxEnd - boxStart;
                        int viewHeight = videoViewHeight;
                        if (viewHeight <= 0) {
                            viewHeight = viewEnd - viewStart;
                        }
                        int targetStart = (int) (boxStart + boxHeight * rate - viewHeight / 2);
                        // 避免界面回退，保证只向上滚动
                        return viewStart < targetStart ? 0 : targetStart - viewStart;
                    } else if (rate > 1) {
                        int targetStart = (int) (boxStart + rate);
                        return viewStart < targetStart ? 0 : targetStart - viewStart;
                    } else {
                        return super.calculateDtToFit(viewStart, viewEnd, boxStart, boxEnd, snapPreference);
                    }
                }
            };
            smoothScroller.setTargetPosition(position);
            layoutManager.startSmoothScroll(smoothScroller);
        }
    }

    public static void smoothScrollDistanceToCenter(@NonNull RecyclerView mRecyclerView,
                                                    @NonNull View targetView, OrientationHelper helper) {
        final int childCenter = helper.getDecoratedStart(targetView)
                + (helper.getDecoratedMeasurement(targetView) / 2);
        final int containerCenter = helper.getEnd() / 2;
        mRecyclerView.smoothScrollBy(0, childCenter - containerCenter);
    }

    public static void scrollDistanceToCenter(@NonNull RecyclerView mRecyclerView,
                                              @NonNull View targetView, OrientationHelper helper) {
        final int childCenter = helper.getDecoratedStart(targetView)
                + (helper.getDecoratedMeasurement(targetView) / 2);
        final int containerCenter = helper.getEnd() / 2;
        mRecyclerView.scrollBy(0, childCenter - containerCenter);
    }

    /**
     * 将某一行露出展示
     *
     * @param mRowViewGroup RecyclerView的某一行
     * @param position      RecyclerView数据中的位置
     */
    public static void triggerPositionSmoothScroll(final View mRowViewGroup, int position) {
        try {
            View parentView = (View) mRowViewGroup.getParent();
            int parentHeight = parentView.getMeasuredHeight();
            if (mRowViewGroup.getTop() < 0 || mRowViewGroup.getBottom() > parentHeight) {
                smoothScrollToPosition(mRowViewGroup, position);
            }
        } catch (Exception e) {
            Log.e("RecyclerViewScrollUtils", e.getMessage());
        }
    }

    public static void smoothScrollToPositionWithOffsetY(RecyclerView recyclerView, int position, int offsetY) {
        if (offsetY != 0) {
            recyclerView.addOnScrollListener(new InnerScrollListener(offsetY));
        }
        recyclerView.smoothScrollToPosition(position);
    }


    static class InnerScrollListener extends RecyclerView.OnScrollListener {
        private int mOffsetY;
        private int mOffsetX;

        public InnerScrollListener(int offsetY) {
            super();
            mOffsetY = offsetY;
        }

        public InnerScrollListener(int offsetX, int offsetY) {
            super();
            mOffsetX = offsetX;
            mOffsetY = offsetY;
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (recyclerView != null && newState == RecyclerView.SCROLL_STATE_IDLE) {
                if (mOffsetX != 0 && mOffsetY != 0) {
                    recyclerView.smoothScrollBy(mOffsetX, mOffsetY);
                    mOffsetX = mOffsetY = 0;
                    recyclerView.removeOnScrollListener(this);
                } else if (mOffsetY != 0) {
                    recyclerView.smoothScrollBy(0, mOffsetY);
                    mOffsetY = 0;
                    recyclerView.removeOnScrollListener(this);
                } else if (mOffsetX != 0) {
                    recyclerView.smoothScrollBy(mOffsetX, 0);
                    mOffsetX = 0;
                    recyclerView.removeOnScrollListener(this);
                }
            }
        }
    }

}
