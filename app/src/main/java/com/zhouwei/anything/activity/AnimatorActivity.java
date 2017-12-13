package com.zhouwei.anything.activity;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhouwei.anything.R;

public class AnimatorActivity extends AppCompatActivity {

    private Button btngo;
    private Button btntar;
    private Button btnani;
    private int height;
    private Button btncaptureScreen;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        this.iv = (ImageView) findViewById(R.id.iv);
        this.btncaptureScreen = (Button) findViewById(R.id.btn_captureScreen);
        this.btnani = (Button) findViewById(R.id.btn_ani);
        this.btntar = (Button) findViewById(R.id.btn_tar);
        this.btngo = (Button) findViewById(R.id.btn_go);

        height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());

        btngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator2();
            }
        });

        btncaptureScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = captureScreen(AnimatorActivity.this);
                iv.setImageBitmap(bitmap);
            }
        });
    }

    public static Bitmap captureScreen(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        Bitmap bmp = view.getDrawingCache();
        return bmp;
    }

    private void animator1(View v) {
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, 30);
        animation.setFillAfter(true);//停留在动画执行完毕的最终状态
        animation.setDuration(200);
        v.startAnimation(animation);
    }

    /**
     * 利用值动画慢慢的显示View
     */
    private void animator2() {
        btnani.setVisibility(View.VISIBLE);
        ValueAnimator animator = ValueAnimator.ofInt(0, height);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curr = (int) animation.getAnimatedValue();
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) btnani.getLayoutParams();
                params.height = curr;
                btnani.setLayoutParams(params);
            }
        });
        animator.start();
    }
}
