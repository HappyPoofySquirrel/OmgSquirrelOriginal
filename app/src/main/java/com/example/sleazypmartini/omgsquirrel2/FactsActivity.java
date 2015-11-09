package com.example.sleazypmartini.omgsquirrel2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.view.View;

public class FactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        WebView myWebView = (WebView) findViewById(R.id.myWebView);
        myWebView.loadUrl("file:///android_asset/Webview.html");


        myWebView.setBackgroundColor(0x00000000);

        getSupportActionBar().setDisplayShowHomeEnabled(true);  //add these 2 lines to display icon
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);


    }


}
