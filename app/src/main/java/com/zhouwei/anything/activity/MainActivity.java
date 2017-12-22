package com.zhouwei.anything.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.zhouwei.anything.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
        Log.i("AAAA", "decorView: " + decorView.getChildCount());
        Log.i("AAAA", "decorView child: " + decorView.getChildAt(0));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("AAAA", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("AAAA", "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("AAAA", "onRestart");
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        Log.i("AAAA", "setVisible");
    }

}
