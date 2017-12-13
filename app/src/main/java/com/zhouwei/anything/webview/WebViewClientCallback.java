package com.zhouwei.anything.webview;

import android.graphics.Bitmap;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

/**
 * Created by zhouwei on 2017/11/20.
 */

public class WebViewClientCallback {
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;// 返回值true表示不用系统的浏览器 返回false表示使用系统浏览器
    }

    public void onPageFinished(WebView view, String url) {
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
    }

    public void onPageCommitVisible(WebView view, String url) {
    }

    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
    }
}
