package com.dryseed.module_view.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.easy.moduler.lib.utils.DPIUtil;

/**
 * 二阶贝塞尔曲线
 *
 * @author caiminming
 */
public class BezierQuadView extends View {
    //开始点和结束点
    private int mStartXPoint;
    private int mStartYPoint;
    private int mEndXPoint;
    private int mEndYPoint;
    //控制点
    private int mConXPoint;
    private int mConYPoint;

    //路径和画笔
    private Path mPath;
    private Paint mPaint;


    //辅助线画笔,写字画笔
    private Paint mLinePaint;
    private Paint mTextPaint;

    public BezierQuadView(Context context) {
        super(context);
        init();
    }

    public BezierQuadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BezierQuadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 进行初始化的一些操作
     */
    private void init() {
        int screenHeight = DPIUtil.getHeight();
        int screenWidth = DPIUtil.getWidth();

        //设置各点的位置
        mStartXPoint = screenWidth / 4;
        mStartYPoint = screenHeight / 2;
        mEndXPoint = screenWidth * 3 / 4;
        mEndYPoint = screenHeight / 2;
        mConXPoint = screenWidth / 2;
        mConYPoint = screenHeight / 2 - 400;
        //路径,画笔设置
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8);

        //辅助线画笔
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.GRAY);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(3);

        //写字画笔
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextSize(20);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();

        //贝塞尔曲线
        mPath.moveTo(mStartXPoint, mStartYPoint);
        mPath.quadTo(mConXPoint, mConYPoint, mEndXPoint, mEndYPoint);
        canvas.drawPath(mPath, mPaint);

        //辅助线
        canvas.drawLine(mStartXPoint, mStartYPoint, mConXPoint, mConYPoint, mLinePaint);
        canvas.drawLine(mConXPoint, mConYPoint, mEndXPoint, mEndYPoint, mLinePaint);

        //文字
        canvas.drawPoint(mStartXPoint, mStartYPoint, mPaint);
        canvas.drawText("起始点", mStartXPoint, mStartYPoint + 30, mTextPaint);
        canvas.drawPoint(mEndXPoint, mEndYPoint, mPaint);
        canvas.drawText("结束点", mEndXPoint, mEndYPoint + 30, mTextPaint);
        canvas.drawPoint(mConXPoint, mConYPoint, mPaint);
        canvas.drawText("控制点", mConXPoint, mConYPoint - 30, mTextPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mConXPoint = (int) event.getX();
                mConYPoint = (int) event.getY();
                invalidate();
                break;
        }
        return true;
    }
}
