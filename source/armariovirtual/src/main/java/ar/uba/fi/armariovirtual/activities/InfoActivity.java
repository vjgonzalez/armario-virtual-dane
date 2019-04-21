package ar.uba.fi.armariovirtual.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;

import ar.uba.fi.armariovirtual.R;

public class InfoActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        WebView browser = findViewById(R.id.webView);
        browser.clearCache(true);
        browser.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        browser.loadUrl("file:///android_asset/html/info.html");
        ImageButton btnAtrasConfig = findViewById(R.id.btn_atras_config);
        btnAtrasConfig.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                finish();
            }
        });

    }
}
