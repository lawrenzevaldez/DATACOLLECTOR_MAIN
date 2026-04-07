package com.srs.datacollector_rcv.web;

import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.srs.datacollector_rcv.device.DeviceManager;
import com.srs.datacollector_rcv.device.IDataCollector;

import org.json.JSONException;
import org.json.JSONObject;

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
    public void print(String json) {
        JSONObject obj = null;
        try {
            obj = new JSONObject(json);

        device.print(obj.optString("program"),
                obj.optString("header1"),
                obj.optString("header2"),
                obj.optString("header3"),
                obj.optString("header4"),
                obj.optString("header5"),
                obj.optString("header6"),
                obj.optString("header7"),
                obj.optString("header8"),
                obj.optString("header9"),
                obj.optString("header10"),
                obj.optString("header11"),
                obj.optString("header12"),
                obj.optString("header13"),
                obj.optString("header14"),
                obj.optString("header15"),
                obj.optString("nl"),
                obj.optString("header16"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @JavascriptInterface
    public void reprint(String json) {
        JSONObject obj = null;
        try {
            obj = new JSONObject(json);

            device.reprint(obj.optString("program"),
                    obj.optString("header1"),
                    obj.optString("header2"),
                    obj.optString("header3"),
                    obj.optString("header4"),
                    obj.optString("header5"),
                    obj.optString("header6"),
                    obj.optString("header7"),
                    obj.optString("header8"),
                    obj.optString("header9"),
                    obj.optString("header10"),
                    obj.optString("header11"),
                    obj.optString("header12"),
                    obj.optString("header13"),
                    obj.optString("header14"),
                    obj.optString("header15"),
                    obj.optString("nl"),
                    obj.optString("header16"),
                    obj.optString("header17"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}