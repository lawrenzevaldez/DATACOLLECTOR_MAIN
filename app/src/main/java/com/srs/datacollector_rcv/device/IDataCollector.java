package com.srs.datacollector_rcv.device;

import android.app.Activity;
import android.webkit.WebView;

public interface IDataCollector {
    void init(Activity activity, WebView webView);
    void startScan();
    void stopScan();
    void print(String hd1, String hd2);
}