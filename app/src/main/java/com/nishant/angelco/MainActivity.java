package com.nishant.angelco;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

    private WebView myWebView;
    private ProgressBar progressBar;
    private AdView mAdView;
    private FloatingActionButton mJobFab;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setIndeterminate(false);
        myWebView = findViewById(R.id.webview);
        mJobFab = findViewById(R.id.jobs_fab);

        myWebView.loadUrl("https://angel.co/");
        mJobFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myWebView.loadUrl("https://angel.co/jobs");
            }
        });
        WebSettings webSettings = myWebView.getSettings();
        myWebView.setWebChromeClient(new MyWebChromeClient());
        myWebView.setWebViewClient(new MyWebViewClient());
        webSettings.setJavaScriptEnabled(true);
        CookieManager.getInstance().setAcceptThirdPartyCookies(myWebView, true);
    }


    private class MyWebChromeClient extends WebChromeClient {
        public void onProgressChanged(WebView view, int progress) {
            progressBar.setProgress(progress);
            setProgress(progress * 100);

            if (progress == 100)
                progressBar.setVisibility(View.GONE);
            else {
                progressBar.setVisibility(View.VISIBLE);
            }
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
