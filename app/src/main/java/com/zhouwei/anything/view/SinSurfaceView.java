package com.zhouwei.anything.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

/**
 * Created by zhouwei on 2017/12/11.
 * <p>
 * 画正弦曲线
 */

public class SinSurfaceView extends SurfaceViewTemplate {
    public SinSurfaceView(Context context) {
        this(context, null);
    }

    public SinSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SinSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void init() {
        super.init();
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPath = new Path();
        mPath.moveTo(0, 400);
    }

    @Override
    protected void drawAnyThing(Canvas mCanvas) {
        super.drawAnyThing(mCanvas);

        x += 1;
        y = (int) (100 * Math.sin(x * 2 * Math.PI / 180) + 400);
        mPath.lineTo(x, y);
        // 去掉上一次canvas上的内容
        mCanvas.drawColor(Color.WHITE);
        mCanvas.drawPath(mPath, mPaint);
    }

    private int x;
    private int y;
    private Path mPath;

}
