package com.srs.datacollector_rcv.device;

import android.app.Activity;
import android.content.*;
import android.util.Log;
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
                webHelper.sendScanResult(data, "cilico");
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
    public void print(String program, String hd1, String hd2, String hd3, String hd4, String hd5, String hd6, String hd7, String hd8, String hd9, String hd10, String hd11, String hd12, String hd13, String hd14, String hd15, String nl, String hd16) {
        if(program.equals("srsbo")) {
            // ==============================
            // HEADERS (FROM JS)
            // ==============================
            printMultiline(hd1);   // RETURNS and DISPOSAL <branch>
            printMultiline(hd2);   // Return to Supplier Slip No XXXX
            printMultiline(hd3);   // Supplier
            printMultiline(hd4);   // Date
            printMultiline(hd5);   // U.COST
            printMultiline(hd6);   // Product + QTY header

            // ==============================
            // ITEMS (ALREADY LOOPED IN JS)
            // ==============================
            printMultiline(hd7);   // Items list (product / qty / cost / uom)

            // ==============================
            // DIVIDER + TOTALS
            // ==============================
            printMultiline(hd8);   // dashed line
            printMultiline(hd9);   // TOTAL QTY
            printMultiline(hd10);  // TOTAL AMOUNT + dashed line

            // ==============================
            // SIGNATURES
            // ==============================
            printMultiline(hd11);  // Prepared by
            printMultiline(hd12);  // Checked by
            printMultiline(hd13);  // Received by
            printMultiline(hd14);  // signature line

            // ==============================
            // COPIES
            // ==============================
            printMultiline(hd15);  // Supplier Copy

            // ==============================
            // FEED PAPER
            // ==============================
            printMultiline("\n");
        }
    }

    public void reprint(String program, String hd1, String hd2, String hd3, String hd4, String hd5, String hd6, String hd7, String hd8, String hd9, String hd10, String hd11, String hd12, String hd13, String hd14, String hd15, String nl, String hd16, String hd17) {
        if(program.equals("srsbo")) {
            // ==============================
            // HEADERS (FROM JS)
            // ==============================
            printMultiline(hd1);   // RETURNS and DISPOSAL <branch>
            printMultiline(hd2);   // Return to Supplier Slip No XXXX
            printMultiline(hd3);   // Supplier
            printMultiline(hd4);   // Date
            printMultiline(hd5);   // U.COST
            printMultiline(hd6);   // Product + QTY header

            // ==============================
            // ITEMS (ALREADY LOOPED IN JS)
            // ==============================
            printMultiline(hd7);   // Items list (product / qty / cost / uom)

            // ==============================
            // DIVIDER + TOTALS
            // ==============================
            printMultiline(hd8);   // dashed line
            printMultiline(hd9);   // TOTAL QTY
            printMultiline(hd10);  // TOTAL AMOUNT + dashed line

            // ==============================
            // SIGNATURES
            // ==============================
            printMultiline(hd11);  // Prepared by
            printMultiline(hd12);  // Checked by
            printMultiline(hd13);  // Received by
            printMultiline(hd14);  // signature line

            // ==============================
            // COPIES
            // ==============================
            printMultiline(hd17);  // Supplier Copy

            // ==============================
            // FEED PAPER
            // ==============================
            printMultiline("\n");
        }
    }
}