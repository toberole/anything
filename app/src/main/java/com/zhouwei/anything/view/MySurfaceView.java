package com.zhouwei.anything.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import java.util.Random;

/**
 * Created by zhouwei on 2017/12/11.
 */

public class MySurfaceView extends SurfaceViewTemplate {
    public MySurfaceView(Context context) {
        this(context, null);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void init() {
        super.init();

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
    }


    private int getRandomColor() {
        Random random = new Random(System.currentTimeMillis());
        return Color.argb(random.nextInt(255), random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    @Override
    protected void drawAnyThing(Canvas mCanvas) {
        super.drawAnyThing(mCanvas);

        mPaint.setColor(getRandomColor());
        mCanvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, mPaint);
    }
}
