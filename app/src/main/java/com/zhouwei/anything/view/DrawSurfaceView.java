package com.zhouwei.anything.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;


/**
 * Created by zhouwei on 2017/12/11.
 */

public class DrawSurfaceView extends SurfaceViewTemplate {
    public DrawSurfaceView(Context context) {
        this(context, null);
    }

    public DrawSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Path mPath;

    @Override
    protected void init() {
        super.init();
        mPath = new Path();

        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
    }

    @Override
    protected void drawAnyThing(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawPath(mPath, mPaint);
        Log.i("AAAA", "drawAnyThing");
    }

    //第二次执行时，第一次结束调用的坐标值将作为第二次调用的初始坐标值
    private int mX;
    private int mY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(event);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }

    //手指点下屏幕时调用
    private void touchDown(MotionEvent event) {

        //mPath.rewind();
        mPath.reset();
        float x = event.getX();
        float y = event.getY();

        mX = (int) x;
        mY = (int) y;

        //mPath绘制的绘制起点
        mPath.moveTo(x, y);
    }

    //手指在屏幕上滑动时调用
    private void touchMove(MotionEvent event) {
        final float x = event.getX();
        final float y = event.getY();

        final float previousX = mX;
        final float previousY = mY;

        final float dx = Math.abs(x - previousX);
        final float dy = Math.abs(y - previousY);

        //两点之间的距离大于等于3时，连接连接两点形成直线
        if (dx >= 3 || dy >= 3) {
            //两点连成直线
            mPath.lineTo(x, y);

            //第二次执行时，第一次结束调用的坐标值将作为第二次调用的初始坐标值
            mX = (int) x;
            mY = (int) y;
        }
    }
}
