package com.example.personal_diary_2022551022;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WebSearch_View extends AppCompatActivity {

    ImageButton rtnHomeBtn;
    WebView webView;
    EditText urlInput;
    Button searchBtn;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_search);

        rtnHomeBtn = findViewById(R.id.btn_rtnHome_web);
        rtnHomeBtn.setOnClickListener(v -> {
            finish();
        });
        searchBtn = findViewById(R.id.btn_enter_web);
        urlInput = findViewById(R.id.edit_url);
        webView = findViewById(R.id.webView_browser);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl("http://www.google.com");
        urlInput.setText("http://www.google.com");

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = urlInput.getText().toString();
                if(!address.startsWith("http://")){ //editText 입력된 브라우저의 주소에 "http://" 없으면 붙쳐준다.
                    address = "http://" +address;
                }
                Toast.makeText(WebSearch_View.this, "Loading...", Toast.LENGTH_SHORT).show();

                webView.loadUrl(urlInput.getText().toString());
            }
        });

        webView.setWebViewClient(webViewClient);

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view,url,favicon);
            try {
                urlInput.setText(url);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return false; // then it is not handled by default action
        }
    };
}