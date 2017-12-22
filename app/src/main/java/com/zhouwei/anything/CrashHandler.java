package com.zhouwei.anything;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by zhouwei on 2017/12/22.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = CrashHandler.class.getSimpleName();

    private Context context;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (!handleException(e) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(t, e);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告
     *
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable e) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(context).setTitle("提示")
                        .setMessage("程序崩溃了...").setNeutralButton("我知道了", null)
                        .create().show();
            }
        });

        return true;
    }

    public static CrashHandler getInstance() {
        return CrashHandlerHolder.instance;
    }

    public void init(Context context) {
        this.context = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    private CrashHandler() {
    }

    private static class CrashHandlerHolder {
        public static CrashHandler instance = new CrashHandler();
    }
}
