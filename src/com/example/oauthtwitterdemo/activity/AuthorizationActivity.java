package com.example.oauthtwitterdemo.activity;

import com.example.oauthtwitterdemo.R;
import com.example.oauthtwitterdemo.app.OAuthTwitterDemoApp;
import com.example.oauthtwitterdemo.base.BaseActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class AuthorizationActivity extends BaseActivity {

	private OAuthTwitterDemoApp app;
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authorization);
		app = (OAuthTwitterDemoApp) getApplication();
		webView = (WebView) findViewById(R.id.webview);
	}

	@Override
	protected void onResume() {
		super.onResume();
		String authURL = app.beginAuthorization();
		webView.loadUrl(authURL);
	}
}
