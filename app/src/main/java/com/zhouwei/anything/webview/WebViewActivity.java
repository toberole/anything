package com.zhouwei.anything.webview;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhouwei on 2017/11/18.
 */

public class WebViewActivity extends AppCompatActivity {
    protected String TAG = WebViewActivity.this.getClass().getSimpleName();

    public final static String PATH = "file:///android_asset/layout/";

    protected IJsBridge jsBridge;

    protected Map<String, String> additionalHttpHeaders = new HashMap<>();

    protected void addHttpHeaders(Map<String, String> httpHeaders) {
        additionalHttpHeaders.clear();
        additionalHttpHeaders.putAll(httpHeaders);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏
    }

    @SuppressLint("JavascriptInterface")
    protected void initWebView(WebView webView, IJsBridge jsBridge, String url) {
        this.jsBridge = jsBridge;
        WebSettings webSettings = webView.getSettings();
        // 设置支持js
        webSettings.setJavaScriptEnabled(true);

        //设置为false表示将图片调整为适合WebView的大小
        webSettings.setUseWideViewPort(false);

        // 设置不支持缩放
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSupportZoom(false);
        webSettings.setDisplayZoomControls(false);

        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        // webSettings.setAllowFileAccessFromFileURLs(true);

        //设置js支持数据库
        webSettings.setDatabaseEnabled(true);

        // 设置js支持window.localStorage,如果不设置,js中获取的window和localStorage都是null
        webSettings.setDomStorageEnabled(true);

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            // WebView加载之前调用的方法，可以控制是否需要加载
            // 返回true: 不使用webview处理。
            // 返回false: 使用当前 WebView 处理URL。
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制WebView去打开
                // 为false调用系统默认浏览器或第三方浏览器
                return WebViewActivity.this.shouldOverrideUrlLoading(view, url);
            }

            @Override
            //WebView加载完成会调用的方法
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                WebViewActivity.this.onPageFinished(view, url);
            }

            @Override
            //WebView开始加载调用的方法
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                WebViewActivity.this.onPageStarted(view, url, favicon);
            }

            @Override
            //WebView的界面可见时调用的方法
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                WebViewActivity.this.onPageCommitVisible(view, url);
            }

            @Override
            // 错误的回调
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }
        });

        // 添加java和js交互的接口,android字符串相当于一个Brige桥梁的作用
        // 安卓4.2以后增加了@JavascriptInterface接口,只有代码@JavascriptInterface注解的方法js才能调用
        // 之前是被注入的类和从父类继承的所有的public的方法都能访问,进一步保证APP的安全性.
        if (jsBridge != null) {
            webView.addJavascriptInterface(jsBridge, "android");
        }

        /**
         *  WebView只是用来处理一些html的页面内容，
         * 只用WebViewClient就行了，如果需要更丰富的处理效果，
         * 比如JS、进度条等，就要用到WebChromeClient。
         *
         * 当WebChromeClient中方法返回true的时候，
         * 说明WebChromeClient已经处理了这个事件，不需要再继续分发下去
         * 而当返回false的时候，则此事件会继续传递给WebView，由WebView来处理。
         */
        // 该设置保证加载界面时会自动调用系统的浏览器,而不是提示给用户让用户去选择哪个浏览器
        // 辅助WebView处理Javascript的对话框、网站Logo、网站title、load进度...
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            /**
             * 对应js中的alert()方法,可以重写该方法完成与js的交互
             * 返回值 true则会替换掉js的alert()
             */
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return WebViewActivity.this.onJsAlert(view, url, message, result);
            }

            @Override
            /**
             * 对应js中promt(),可以重写该方法完成与js的交互
             * 返回值 true则会替换掉js的promt()
             */
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return WebViewActivity.this.onJsPrompt(view, url, message, defaultValue, result);
            }

            @Override
            /**
             * 对应js中的confirm方法,可以重写该方法完成与js的交互
             * 返回值 true则会替换掉js的confirm()
             */
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return WebViewActivity.this.onJsConfirm(view, url, message, result);
            }

            @Override
            /**
             * 对应js中的console.log方法,可以重写该方法完成与js的交互
             * 返回值 true则会替换掉js的console.log()
             */
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                return WebViewActivity.this.onConsoleMessage(consoleMessage);
            }

            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
            }

            @Override
            public void onCloseWindow(WebView window) {
                super.onCloseWindow(window);
            }

            @Override
            /**
             * 加载进度改变的时候
             */
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
            }
        });

        // 加载网页 asserts目录下的
        webView.loadUrl(url);
    }

    protected boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (additionalHttpHeaders.size() > 0) {
            view.loadUrl(url, additionalHttpHeaders);
        } else {
            view.loadUrl(url);
        }
        return false;
    }

    protected void onPageCommitVisible(WebView view, String url) {
        Log.i(TAG, "onPageCommitVisible");
    }

    protected void onPageStarted(WebView view, String url, Bitmap favicon) {
        Log.i(TAG, "onPageStarted");
    }

    protected void onPageFinished(WebView view, String url) {
        Log.i(TAG, "onPageFinished");
    }

    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return false;
    }

    //对应js中promt(),可以重写该方法完成与js的交互
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        return false;
    }

    //对应js中的confirm方法,可以重写该方法完成与js的交互
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        return false;
    }

    //对应js中的console.log方法,可以重写该方法完成与js的交互
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        return false;
    }

}
