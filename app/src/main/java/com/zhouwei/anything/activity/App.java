package com.zhouwei.anything.activity;

import android.app.Application;

import com.zhouwei.anything.xxorm.DBEngine;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zhouwei on 2017/12/21.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        try {
            final InputStream inputStream = getResources().getAssets().open("db.xml");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    DBEngine.getInstance().init(App.this);
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
