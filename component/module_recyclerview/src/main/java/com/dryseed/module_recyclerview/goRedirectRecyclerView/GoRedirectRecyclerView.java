package com.dryseed.module_recyclerview.goRedirectRecyclerView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.easy.moduler.lib.utils.DPIUtil;

/**
 * @author caiminming
 */
public class GoRedirectRecyclerView extends RecyclerView {
    private static final String TAG = "MallFloor_PanicRecyclerView";
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;

    private OnItemClickLitener mOnItemClickLitener;
    private boolean mFirstLayout = true;
    protected int mItemWidth;
    private boolean goRedirect = true;
    protected int FOOTER_WIDTH;
    float mLastX;
    float width;
    private static final float OFFSET_RADIO = 1.5f;
    private boolean checkForReset = false;
    private int mode = NONE;

    private OnScrollListener onScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                if (checkForReset) {
                    checkForReset();
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };

    public GoRedirectRecyclerView(Context context) {
        super(context);
        initView();
    }

    public GoRedirectRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        this.setOnScrollListener(onScrollListener);
        mItemWidth = DPIUtil.dip2px(275 + 5);
        FOOTER_WIDTH = DPIUtil.dip2px(8);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        if (!goRedirect) {
            return super.onTouchEvent(e);
        }

        if (mLastX == -1) {
            mLastX = e.getRawX();
        }

        int canJump = 0;

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = e.getRawX();
                mode = DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mLastX = e.getRawX();
                mode = ZOOM;
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = 0f;
                if (mode == DRAG) {
                    deltaX = e.getRawX() - mLastX;
                } else if (e.getPointerCount() == 2) {
                    float x1 = e.getX(0) - mLastX;
                    float x2 = e.getX(1) - mLastX;
                    deltaX = Math.min(x1, x2);
                }
                deltaX = e.getRawX() - mLastX;

                mLastX = e.getRawX();

                try {
                    if (null != this && null != this.getLayoutManager()) {
                        int lastCompletelyVisibleItemPosition = ((LinearLayoutManager) this.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                        if (lastCompletelyVisibleItemPosition == this.getAdapter().getItemCount() - 1) {
                            //到达最底部，最后一个item为footerView
                            width += -deltaX / OFFSET_RADIO;
                            if (width > FOOTER_WIDTH) {
                                width = FOOTER_WIDTH;
                            }
                            resetMoreView((int) width);
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                try {
                    if (null != this && null != this.getLayoutManager()) {
                        int lastCompletelyVisibleItemPosition2 = ((LinearLayoutManager) this.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                        if (lastCompletelyVisibleItemPosition2 == this.getAdapter().getItemCount() - 1) {
                            if (width >= FOOTER_WIDTH) {
                                canJump = 1;
                                jump();
                            }
                        }
                        resetMoreView(0);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                width = 1;
                mLastX = -1;
                break;
            case MotionEvent.ACTION_CANCEL:
                if (canJump == 1) {
                    jump();
                }
                resetMoreView(0);
            default:
                resetMoreView(0);
                mLastX = -1;
                width = 1;
                break;
        }
        return super.onTouchEvent(e);
    }

    private void jump() {
        if (null != mOnItemClickLitener) {
            mOnItemClickLitener.onItemClick();
        }
    }

    private void resetMoreView(final int width) {
//        post(new Runnable() {
//            @Override
//            public void run() {
//                if (getAdapter() != null && ((HeaderFooterRecyclerAdapter) getAdapter()).getFooterView() != null) {
//                    TextView blank = (TextView) ((HeaderFooterRecyclerAdapter) getAdapter()).getFooterView().findViewById(R.id.home_shop_more_blank);
//                    blank.setPadding(0, 0, width, 0);
//                }
//            }
//        });
    }

    private void checkForReset() {
        // 获取第一个Item的left
        int left = getChildAt(0).getLeft();
        if (left == 0)
            return;

        if (Math.abs(left) > mItemWidth >> 1) {
            int position = ((LinearLayoutManager) this.getLayoutManager()).findLastVisibleItemPosition();
            if (position < this.getAdapter().getItemCount() - 1) {
                smoothScrollBy(mItemWidth - Math.abs(left), 0);
            }
        } else {
            smoothScrollBy(-Math.abs(left), 0);
        }

    }

    public void setGoRedirect(boolean goRedirect) {
        this.goRedirect = goRedirect;
    }

    @Override
    protected void onAttachedToWindow() {
        if (mFirstLayout) {
            super.onAttachedToWindow();
        }
        mFirstLayout = false;
    }

    public interface OnItemClickLitener {
        void onItemClick();
    }

    public void setOnLastItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public void setCheckForReset(boolean checkForReset) {
        this.checkForReset = checkForReset;
    }
}
