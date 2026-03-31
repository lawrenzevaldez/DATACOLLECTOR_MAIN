package com.srs.datacollector_rcv.device;

import android.app.Activity;
import android.content.*;
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

        posApiHelper.PrintInit(2, 24, 24, 0);
        posApiHelper.PrintSetGray(5);
        posApiHelper.PrintSetFont((byte)16,(byte)16,(byte)0);

        posApiHelper.PrintStr(hd1);
        posApiHelper.PrintStr(hd2);

        posApiHelper.PrintStart();
    }
}