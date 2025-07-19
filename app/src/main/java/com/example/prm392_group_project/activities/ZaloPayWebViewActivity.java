package com.example.prm392_group_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_group_project.R;

public class ZaloPayWebViewActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zalopay_webview);

        webView = findViewById(R.id.webViewZaloPay);

        String url = getIntent().getStringExtra("url");
        if (url != null && !url.isEmpty()) {
            webView.setWebViewClient(new WebViewClient() {
                // Khi thanh toán hoàn tất (ví dụ bạn có thể xác định qua URL thành công)
                @Override
                public void onPageFinished(WebView view, String url) {
                    if (url.contains("zalopay.vn") && url.contains("returnCode=1")) {
                        // giả định thành công khi có returnCode=1 trong URL
                        Intent intent = new Intent(ZaloPayWebViewActivity.this, OrderHistoryActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });

            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(url);
        }
    }
}
