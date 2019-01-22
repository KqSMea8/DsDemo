package com.dryseed.module_view.motionEvent;

import android.content.Context;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.dryseed.module_view.R;

/**
 * @author caiminming
 */
public class MyImageView extends AppCompatImageView {
    /**
     * 图片
     */
    Bitmap mBitmap;
    /**
     * 图片所在区域
     */
    RectF mBitmapRectF;
    /**
     * 控制图片的matrix
     */
    Matrix mBitmapMatrix;
    /**
     * 是否可拖拽
     */
    boolean mCanDrag = false;
    /**
     * 上一次的point
     */
    PointF mLastPoint = new PointF(0, 0);
    /**
     * 默认paint
     */
    Paint mDefaultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public MyImageView(Context context) {
        this(context, null);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 调整图片大小
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.outWidth = 960 / 2;
        options.outHeight = 800 / 2;

        mBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.qiqiu, options);
        mBitmapRectF = new RectF(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        mBitmapMatrix = new Matrix();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                // ▼ 判断是否是第一个手指 && 是否包含在图片区域内
                if (event.getPointerId(event.getActionIndex()) == 0 && mBitmapRectF.contains((int) event.getX(), (int) event.getY())) {
                    mCanDrag = true;
                    mLastPoint.set(event.getX(), event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                // ▼ 判断是否是第一个手指
                if (event.getPointerId(event.getActionIndex()) == 0) {
                    mCanDrag = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // 如果存在第一个手指，且这个手指的落点在图片区域内
                if (mCanDrag) {
                    // ▼ 注意 getX 和 getY
                    int index = event.findPointerIndex(0);
                    mBitmapMatrix.postTranslate(event.getX(index) - mLastPoint.x, event.getY(index) - mLastPoint.y);
                    mLastPoint.set(event.getX(index), event.getY(index));

                    mBitmapRectF = new RectF(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
                    mBitmapMatrix.mapRect(mBitmapRectF);

                    invalidate();
                }
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, mBitmapMatrix, mDefaultPaint);
    }
}
