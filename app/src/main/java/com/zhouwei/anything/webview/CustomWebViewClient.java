package com.zhouwei.anything.webview;

import android.graphics.Bitmap;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by zhouwei on 2017/11/20.
 */

public class CustomWebViewClient extends WebViewClient {
    private WebViewClientCallback callback;

    public CustomWebViewClient(WebViewClientCallback callback) {
        this.callback = callback;
    }

    @Override
    //WebView加载之前调用的方法，可以控制是否需要加载
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        // 返回值是true的时候控制WebView去打开
        // 为false调用系统默认浏览器或第三方浏览器
        if (callback != null) {
            return callback.shouldOverrideUrlLoading(view, url);
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    //WebView加载完成会调用的方法
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (callback != null) {
            callback.onPageFinished(view, url);
        }
    }

    @Override
    //WebView开始加载调用的方法
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (callback != null) {
            callback.onPageStarted(view, url, favicon);
        }
    }

    @Override
    //WebView的界面可见时调用的方法
    public void onPageCommitVisible(WebView view, String url) {
        super.onPageCommitVisible(view, url);
        if (callback != null) {
            callback.onPageCommitVisible(view, url);
        }
    }

    @Override
    // 错误的回调
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        if (callback != null) {
            callback.onReceivedError(view, request, error);
        }
    }
}
