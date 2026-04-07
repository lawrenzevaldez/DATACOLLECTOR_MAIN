package com.srs.datacollector_rcv.device;

import android.app.Activity;
import android.content.*;
import android.util.Log;
import android.webkit.WebView;
import com.srs.datacollector_rcv.R;
import com.srs.datacollector_rcv.web.WebViewHelper;

public class CS50BlueDevice implements IDataCollector {

    private com.ctk.blue.sdk.PosApiHelper posApiHelper;
    private BroadcastReceiver receiver;
    private Activity activity;
    private WebViewHelper webHelper;

    @Override
    public void init(Activity activity, WebView webView) {
        this.activity = activity;

        webView = activity.findViewById(R.id.webView);
        this.webHelper = new WebViewHelper(webView);

        posApiHelper = com.ctk.blue.sdk.PosApiHelper.getInstance();
    }

    @Override
    public void startScan() {

        Intent cfg = new Intent("ACTION_BAR_SCANCFG");
        cfg.putExtra("EXTRA_SCAN_POWER", 1);
        cfg.putExtra("EXTRA_TRIG_MODE", 0);
        cfg.putExtra("EXTRA_SCAN_MODE", 2);
        cfg.putExtra("EXTRA_SCAN_AUTOENT", 1);
        cfg.putExtra("EXTRA_SCAN_NOTY_SND", 1);
        cfg.putExtra("EXTRA_SCAN_NOTY_VIB", 1);
        cfg.putExtra("EXTRA_SCAN_NOTY_LED", 1);
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
            posApiHelper.PrintInit(2, 24, 24, 0);
            posApiHelper.PrintSetGray(5);
            posApiHelper.PrintSetFont((byte)16,(byte)16,(byte)0);

            posApiHelper.PrintStr(hd1);
            posApiHelper.PrintStr(hd2);
            posApiHelper.PrintStr(hd3);
            posApiHelper.PrintStr(hd4);
            posApiHelper.PrintStr(hd5);
            posApiHelper.PrintStr(hd6);
            posApiHelper.PrintStr(hd7);
            posApiHelper.PrintStr(hd8);
            posApiHelper.PrintStr(hd9);
            posApiHelper.PrintStr(hd10);
            posApiHelper.PrintStr(hd11);
            posApiHelper.PrintStr(hd12);
            posApiHelper.PrintStr(hd13);
            posApiHelper.PrintStr(hd14);
            posApiHelper.PrintStr(hd15);

            posApiHelper.PrintStart();
        }
    }

    @Override
    public void reprint(String program, String hd1, String hd2, String hd3, String hd4, String hd5, String hd6, String hd7, String hd8, String hd9, String hd10, String hd11, String hd12, String hd13, String hd14, String hd15, String nl, String hd16, String hd17) {
        try {
            if(program.equals("srsbo")) {
                posApiHelper.PrintInit(2, 24, 24, 0);
                posApiHelper.PrintSetGray(5);
                posApiHelper.PrintSetFont((byte)16,(byte)16,(byte)0);

                posApiHelper.PrintStr(hd1);
                posApiHelper.PrintStr(hd2);
                posApiHelper.PrintStr(hd3);
                posApiHelper.PrintStr(hd4);
                posApiHelper.PrintStr(hd5);
                posApiHelper.PrintStr(hd6);
                posApiHelper.PrintStr(hd7);
                posApiHelper.PrintStr(hd8);
                posApiHelper.PrintStr(hd9);
                posApiHelper.PrintStr(hd10);
                posApiHelper.PrintStr(hd11);
                posApiHelper.PrintStr(hd12);
                posApiHelper.PrintStr(hd13);
                posApiHelper.PrintStr(hd14);
                posApiHelper.PrintStr(hd17);

                posApiHelper.PrintStart();
            }
        } catch(Exception e) {
            Log.d("REPRINT ERROR:", e.getMessage());
        }
    }
}