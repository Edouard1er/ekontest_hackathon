package com.example.ekontest_hackathon;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;


public class MoncashGateway extends AppCompatActivity {
    WebView webview;
    ProgressBar progressBar;
    static Activity MG;

    public MoncashGateway() {
//        this.webview = webview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_moncash_gateway);
        this.setProgressBarVisibility(true);

        MG = this;

        webview = findViewById(R.id.webview);
        progressBar = findViewById(R.id.progressBar);

        String url = String.valueOf(getIntent().getStringExtra("url"));
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MonCash.progressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            System.out.println("The page load has started...");
            System.out.println("URL: " + url);
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            System.out.println("The page load is done...");
            System.out.println("URL: " + url);
            progressBar.setVisibility(View.GONE);
        }
    }

    public static void  finishActivityy() {
        MG.finish();
    }
}