package com.sharedmoney.app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.JavascriptInterface;
import android.print.PrintAttributes;
import android.print.PrintManager;
import android.print.PrintDocumentAdapter;
import android.content.Context;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.graphics.Color;
import android.os.Build;

public class MainActivity extends Activity {
    private WebView webView;

    private class AndroidBridge {
        @JavascriptInterface
        public void printPassbook(final String html) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final WebView printWebView = new WebView(MainActivity.this);
                    WebSettings printSettings = printWebView.getSettings();
                    printSettings.setJavaScriptEnabled(true);
                    printSettings.setDomStorageEnabled(true);
                    printWebView.setBackgroundColor(Color.WHITE);

                    printWebView.setWebViewClient(new WebViewClient() {
                        @Override
                        public void onPageFinished(WebView view, String url) {
                            super.onPageFinished(view, url);
                            PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
                            PrintDocumentAdapter adapter = printWebView.createPrintDocumentAdapter("SharedMoney-Passbook");
                            PrintAttributes attributes = new PrintAttributes.Builder()
                                    .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                                    .setResolution(new PrintAttributes.Resolution("shared_money", "Shared Money", 300, 300))
                                    .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                                    .build();
                            printManager.print("SharedMoney-Passbook", adapter, attributes);
                        }
                    });

                    printWebView.loadDataWithBaseURL(
                            "file:///android_asset/",
                            html,
                            "text/html",
                            "UTF-8",
                            null
                    );
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setStatusBarColor(Color.rgb(247, 240, 231));
        window.setNavigationBarColor(Color.WHITE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        webView = new WebView(this);
        webView.setBackgroundColor(Color.rgb(247, 240, 231));
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        settings.setLoadWithOverviewMode(false);
        settings.setUseWideViewPort(false);
        settings.setMediaPlaybackRequiresUserGesture(false);
        webView.addJavascriptInterface(new AndroidBridge(), "AndroidBridge");
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("file:///android_asset/index.html");
        setContentView(webView);
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
