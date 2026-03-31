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
                webHelper.sendScanResult(data);
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
    public void print(String hd1, String hd2) {
        try {
            posApiHelper.printerOpt.PrnInit();
            posApiHelper.printerOpt.PrnSetGray(5);
            posApiHelper.printerOpt.PrnSetFont((byte)16,(byte)16,(byte)0);

            posApiHelper.printerOpt.PrnStr(hd1);
            posApiHelper.printerOpt.PrnStr(hd2);

            posApiHelper.printerOpt.PrnStart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}