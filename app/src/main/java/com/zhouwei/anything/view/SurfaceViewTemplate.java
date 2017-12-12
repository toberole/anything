package com.zhouwei.anything.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import static android.R.attr.track;
import static android.R.attr.x;
import static android.R.attr.y;

/*

    在xml属性里set了 android:backgrand 属性后，surfaceview的绘图失效了（其实是被覆盖了）。

    网上的普遍解决方案是
        sfv.setZOrderOnTop(true);
        sfv.getHolder().setFormat(PixelFormat.TRANSPARENT);

    虽能解决问题，但是同时也衍生了新的问题。surfaceview会置于最顶层，
    采取framelayout布局且与surfaceview处于同一个区域的组件会被遮挡掉。


    解决方法：
        在surfaceCreated方法里面绘制背景

*/

/**
 * <p>
 * 扩展SurfaceView的基础模版代码
 * SurfaceViewTemplate
 */

public class SurfaceViewTemplate extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    protected SurfaceHolder mHolder;
    protected Canvas mCanvas;
    protected volatile boolean mIsDrawing; // 用于控制子线程

    protected Paint mPaint;

    public SurfaceViewTemplate(Context context) {
        this(context, null);
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    protected void init() {
        mHolder = getHolder();
        mHolder.addCallback(this);

        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        /**
         * 绘制背景
         */
        Canvas canvas = mHolder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        mHolder.unlockCanvasAndPost(canvas);

        mIsDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
    }


    public void stopDraw() {
        mIsDrawing = false;
    }


    protected long interval = 100;// 两次绘制动作之间的间隔时间

    @Override
    public void run() {
        while (mIsDrawing) {
            long start = System.currentTimeMillis();
            draw();
            long end = System.currentTimeMillis();
            // 最快100毫秒绘制一次 避免过于频繁的绘制 interval时间可以自己调正
            if (end - start < interval) {
                try {
                    Thread.sleep(interval - (end - start));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void draw() {
        try {
            mCanvas = mHolder.lockCanvas();
            // TODO  draw anything
            drawAnyThing(mCanvas);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);   // 解锁并且提交绘制结果
            }
        }
    }

    /**
     * 子类重写该方法 绘制自定义内容
     *
     * @param canvas
     */
    protected void drawAnyThing(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, mPaint);
    }
}