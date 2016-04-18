package com.buddysoft.tbtx_android.ui.activity.webview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.buddysoft.tbtx_android.R;
import com.buddysoft.tbtx_android.app.C;
import com.buddysoft.tbtx_android.ui.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebViewActivity extends BaseActivity {

    @Bind(R.id.webview)
    WebView mWebView;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
    }

    @Override
    protected void setUpView() {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                mWebView.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        WebSettings setting = mWebView.getSettings();
        setting.setJavaScriptEnabled(true);
        // 设置 缓存模式
        setting.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        setting.setDomStorageEnabled(true);
        setting.setPluginState(WebSettings.PluginState.ON);
        setting.setJavaScriptCanOpenWindowsAutomatically(true);
        setting.setAllowFileAccess(true);
        setting.setDefaultTextEncodingName("UTF-8");
        setting.setLoadWithOverviewMode(true);
        setting.setUseWideViewPort(true);
    }

    @Override
    protected void setUpData() {
        Intent intent = getIntent();
        if (intent != null) {
            String url = intent.getStringExtra(C.IntentKey.MESSAGE_EXTRA_KEY);
            mWebView.loadUrl(url);
        }
    }

    @Override
    protected void setupActivityComponent() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.loadData("<a></a>", "text/html", "utf-8");
    }
}
