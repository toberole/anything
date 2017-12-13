package com.zhouwei.anything.webview;

import android.graphics.Bitmap;
import android.os.Message;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * WebView只是用来处理一些html的页面内容，
 * 只用WebViewClient就行了，如果需要更丰富的处理效果，
 * 比如JS、进度条等，就要用到WebChromeClient。
 * <p>
 * 该设置保证加载界面时会自动调用系统的浏览器,而不是提示给用户让用户去选择哪个浏览器
 * 辅助WebView处理Javascript的对话框、网站Logo、网站title、load进度...
 */

public class CustomWebChromeClient extends WebChromeClient {
    private CustomWebChromeClientCallback callback;

    public CustomWebChromeClient(CustomWebChromeClientCallback callback) {
        this.callback = callback;
    }

    @Override
    /**
     * 对应js中的alert()方法,可以重写该方法完成与js的交互
     * 返回值 true则会替换掉js的alert()
     */
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        if (callback != null) {
            return callback.onJsAlert(view, url, message, result);
        }
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    /**
     * 对应js中promt(),可以重写该方法完成与js的交互
     * 返回值 true则会替换掉js的promt()
     */
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        if (null != callback) {
            return callback.onJsPrompt(view, url, message, defaultValue, result);
        }
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    @Override
    /**
     * 对应js中的confirm方法,可以重写该方法完成与js的交互
     * 返回值 true则会替换掉js的confirm()
     */
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        if (null != callback) {
            return callback.onJsConfirm(view, url, message, result);
        }
        return super.onJsConfirm(view, url, message, result);
    }

    @Override
    /**
     * 对应js中的console.log方法,可以重写该方法完成与js的交互
     * 返回值 true则会替换掉js的console.log()
     */
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        if (callback != null) {
            return callback.onConsoleMessage(consoleMessage);
        }
        return super.onConsoleMessage(consoleMessage);
    }

    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        if (callback != null) {
            return callback.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
        }
        return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
    }

    @Override
    public void onCloseWindow(WebView window) {
        super.onCloseWindow(window);
        if (callback != null) {
            callback.onCloseWindow(window);
        }
    }

    @Override
    /**
     * 加载进度改变的时候
     */
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (callback != null) {
            callback.onProgressChanged(view, newProgress);
        }
    }

    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        super.onReceivedIcon(view, icon);
        if (callback != null) {
            callback.onReceivedIcon(view, icon);
        }
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        if (callback != null) {
            callback.onReceivedTitle(view, title);
        }
    }

    @Override
    public void onShowCustomView(View view, CustomViewCallback cb) {
        super.onShowCustomView(view, cb);
        if (callback != null) {
            callback.onShowCustomView(view, cb);
        }
    }
}
