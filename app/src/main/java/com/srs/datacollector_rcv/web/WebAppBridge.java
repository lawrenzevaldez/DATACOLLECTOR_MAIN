package com.srs.datacollector_rcv.web;

import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.srs.datacollector_rcv.device.DeviceManager;
import com.srs.datacollector_rcv.device.IDataCollector;

public class WebAppBridge {
    private Activity activity;
    private IDataCollector device;

    public WebAppBridge(Activity activity, WebView webView) {
        this.activity = activity;
    }

    public void initDevice(WebView webView) {
        device = DeviceManager.getDevice(activity);
        device.init(activity, webView);
    }

    @JavascriptInterface
    public void scan() {
        device.startScan();
    }

    @JavascriptInterface
    public void stopScan() {
        device.stopScan();
    }

    @JavascriptInterface
    public void print(String hd1, String hd2) {
        device.print(hd1, hd2);
    }
}