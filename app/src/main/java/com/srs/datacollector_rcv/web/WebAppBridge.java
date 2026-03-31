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
    public void print(String hd1, String hd2, String hd3, String hd4, String hd5, String hd6, String hd7, String hd8, String hd9, String hd10, String hd11, String hd12, String hd13, String hd14, String hd15, String nl, String hd16) {
        device.print(hd1, hd2, hd3, hd4, hd5, hd6, hd7, hd8, hd9, hd10, hd11, hd12, hd13, hd14, hd15, nl, hd16);
    }
}