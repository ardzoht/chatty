package com.chatty.android.chatty.content;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chatty.android.chatty.LoginActivity;
import com.chatty.android.chatty.R;

/**
 * Created by Alejandro on 25/04/2016.
 */
public class Channel {

    private String name;
    private String description;
    public ProgressDialog bar;

    public Channel(String name, String description, Context mContext, Activity activity) {
        this.name = name;
        this.description = description;

        // We make channel connection

        bar = ProgressDialog.show(mContext, "Wait", "Loading...");

        WebView webView = (WebView) activity.findViewById(R.id.webView);
        webView.setWebViewClient(new BrowserCustom() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(bar.isShowing()) bar.dismiss();
            }
        });

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl("https://ancient-basin-56814.herokuapp.com/");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    class BrowserCustom extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
