package com.srs.datacollector_rcv.device;

import android.app.Activity;
import android.webkit.WebView;

public interface IDataCollector {
    void init(Activity activity, WebView webView);
    void startScan();
    void stopScan();
    void print(String program, String hd1, String hd2, String hd3, String hd4, String hd5, String hd6, String hd7, String hd8, String hd9, String hd10, String hd11, String hd12, String hd13, String hd14, String hd15, String nl, String hd16);
    void reprint(String program, String hd1, String hd2, String hd3, String hd4, String hd5, String hd6, String hd7, String hd8, String hd9, String hd10, String hd11, String hd12, String hd13, String hd14, String hd15, String nl, String hd16, String hd17);
}