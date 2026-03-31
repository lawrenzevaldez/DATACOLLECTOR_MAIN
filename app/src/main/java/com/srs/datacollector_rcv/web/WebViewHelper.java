package com.srs.datacollector_rcv.web;

import android.webkit.WebView;

public class WebViewHelper {

    private WebView webView;

    public WebViewHelper(WebView webView) {
        this.webView = webView;
    }

    public void sendScanResult(String value) {
        if (value == null) return;

        webView.post(() ->
                webView.evaluateJavascript(
                        "(function(){" +
                                "var el=document.getElementById('item_barcode');" +
                                "if(!el) return;" +
                                "el.value='" + value + "';" +
                                "el.dispatchEvent(new Event('input',{bubbles:true}));" +
                                "el.dispatchEvent(new KeyboardEvent('keyup',{key:'Enter',keyCode:13,which:13,bubbles:true}));" +
                                "})();",
                        null
                )
        );
    }
}