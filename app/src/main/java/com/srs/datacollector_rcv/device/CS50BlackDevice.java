package com.srs.datacollector_rcv.device;

import android.app.Activity;
import android.content.*;
import android.webkit.WebView;
import com.srs.datacollector_rcv.R;

import com.srs.datacollector_rcv.web.WebViewHelper;

public class CS50BlackDevice implements IDataCollector {
    private com.ctk.sdk.PosApiHelper posApiHelper;
    private BroadcastReceiver receiver;
    private Activity activity;
    private WebViewHelper webHelper;

    @Override
    public void init(Activity activity, WebView webView) {
        this.activity = activity;

        webView = activity.findViewById(R.id.webView);
        this.webHelper = new WebViewHelper(webView);

        posApiHelper = com.ctk.sdk.PosApiHelper.getInstance(activity);
        posApiHelper.connectPayService();
    }

    @Override
    public void startScan() {

        Intent cfg = new Intent("ACTION_BAR_SCANCFG");
        cfg.putExtra("EXTRA_SCAN_POWER", 1);
        cfg.putExtra("EXTRA_SCAN_MODE", 2);
        activity.sendBroadcast(cfg);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String data = intent.getStringExtra("EXTRA_SCAN_DATA");
                webHelper.sendScanResult(data, "cs50black");
            }
        };

        activity.registerReceiver(receiver, new IntentFilter("ACTION_BAR_SCAN"));

        activity.sendBroadcast(new Intent("ACTION_BAR_TRIGSCAN"));
    }

    @Override
    public void stopScan() {
        if (receiver != null) {
            activity.unregisterReceiver(receiver);
            receiver = null;
        }
    }

    @Override
    public void print(String program, String hd1, String hd2, String hd3, String hd4, String hd5, String hd6, String hd7, String hd8, String hd9, String hd10, String hd11, String hd12, String hd13, String hd14, String hd15, String nl, String hd16) {
        if(program.equals("srsbo")) {
            try {
                posApiHelper.printerOpt.PrnInit();
                posApiHelper.printerOpt.PrnSetGray(5);
                posApiHelper.printerOpt.PrnSetFont((byte)16,(byte)16,(byte)0);

                posApiHelper.printerOpt.PrnStr(hd1);
                posApiHelper.printerOpt.PrnStr(hd2);
                posApiHelper.printerOpt.PrnStr(hd3);
                posApiHelper.printerOpt.PrnStr(hd4);
                posApiHelper.printerOpt.PrnStr(hd5);
                posApiHelper.printerOpt.PrnStr(hd6);
                posApiHelper.printerOpt.PrnStr(hd7);
                posApiHelper.printerOpt.PrnStr(hd8);
                posApiHelper.printerOpt.PrnStr(hd9);
                posApiHelper.printerOpt.PrnStr(hd10);
                posApiHelper.printerOpt.PrnStr(hd11);
                posApiHelper.printerOpt.PrnStr(hd12);
                posApiHelper.printerOpt.PrnStr(hd13);
                posApiHelper.printerOpt.PrnStr(hd14);
                posApiHelper.printerOpt.PrnStr(hd16);

                posApiHelper.printerOpt.PrnStart();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void reprint(String program, String hd1, String hd2, String hd3, String hd4, String hd5, String hd6, String hd7, String hd8, String hd9, String hd10, String hd11, String hd12, String hd13, String hd14, String hd15, String nl, String hd16, String hd17) {
        if(program.equals("srsbo")) {
            try {
                posApiHelper.printerOpt.PrnInit();
                posApiHelper.printerOpt.PrnSetGray(5);
                posApiHelper.printerOpt.PrnSetFont((byte)16,(byte)16,(byte)0);

                posApiHelper.printerOpt.PrnStr(hd1);
                posApiHelper.printerOpt.PrnStr(hd2);
                posApiHelper.printerOpt.PrnStr(hd3);
                posApiHelper.printerOpt.PrnStr(hd4);
                posApiHelper.printerOpt.PrnStr(hd5);
                posApiHelper.printerOpt.PrnStr(hd6);
                posApiHelper.printerOpt.PrnStr(hd7);
                posApiHelper.printerOpt.PrnStr(hd8);
                posApiHelper.printerOpt.PrnStr(hd9);
                posApiHelper.printerOpt.PrnStr(hd10);
                posApiHelper.printerOpt.PrnStr(hd11);
                posApiHelper.printerOpt.PrnStr(hd12);
                posApiHelper.printerOpt.PrnStr(hd13);
                posApiHelper.printerOpt.PrnStr(hd14);
                posApiHelper.printerOpt.PrnStr(hd17);

                posApiHelper.printerOpt.PrnStart();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}