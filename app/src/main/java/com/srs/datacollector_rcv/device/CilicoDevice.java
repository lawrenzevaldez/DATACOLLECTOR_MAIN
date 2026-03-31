package com.srs.datacollector_rcv.device;

import android.app.Activity;
import android.content.*;
import android.webkit.WebView;
import com.srs.datacollector_rcv.R;
import Printer.PrintHelper;
import com.srs.datacollector_rcv.web.WebViewHelper;

public class CilicoDevice implements IDataCollector {

    private PrintHelper printer;
    private Activity activity;
    private WebViewHelper webHelper;

    @Override
    public void init(Activity activity, WebView webView) {
        this.activity = activity;

        webView = activity.findViewById(R.id.webView);
        this.webHelper = new WebViewHelper(webView);

        printer = new PrintHelper();
        printer.Open(activity);

        registerReceiver();
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter("com.barcode.sendBroadcast");

        activity.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String data = intent.getStringExtra("BARCODE");
                webHelper.sendScanResult(data);
            }
        }, filter);
    }

    @Override
    public void startScan() {
        activity.sendBroadcast(new Intent("com.barcode.sendBroadcastScan"));
    }

    @Override
    public void stopScan() {}

    private void printMultiline(String text) {
        if (text == null) return;

        for (String line : text.split("\n")) {
            printer.PrintLineInit(16);
            printer.PrintLineString(line, 16, 0, false);
            printer.PrintLineEnd();
        }
    }

    @Override
    public void print(String hd1, String hd2) {
        printMultiline(hd1);
        printMultiline(hd2);
    }
}