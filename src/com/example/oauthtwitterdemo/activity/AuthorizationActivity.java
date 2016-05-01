package com.example.oauthtwitterdemo.activity;

import com.example.oauthtwitterdemo.R;
import com.example.oauthtwitterdemo.app.OAuthTwitterDemoApp;
import com.example.oauthtwitterdemo.base.BaseActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AuthorizationActivity extends BaseActivity {

	private OAuthTwitterDemoApp app;
	private WebView webView;
	private WebViewClient webViewClient = new WebViewClient() {
		@Override
		public void onLoadResource(WebView view, String url) {
			super.onLoadResource(view, url);
			Log.i(getClass().getSimpleName(), "callback url = " + url);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authorization);
		app = (OAuthTwitterDemoApp) getApplication();
		setupWebView();
	}

	private void setupWebView() {
		webView = (WebView) findViewById(R.id.webview);
		webView.setWebViewClient(webViewClient);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (isOffline()) {
			finish();
		}
		new GetOAuthURL4TwitterAsyncTask().execute();
	}

	private void loadUrl(String url) {
		webView.loadUrl(url);
	}

	class GetOAuthURL4TwitterAsyncTask extends AsyncTask<Void, Void, String> {
		@Override
		protected String doInBackground(Void... params) {
			String url = null;
			url = app.cookOAuthRequestToken().getAuthorizationURL();
			Log.i(getClass().getSimpleName(), "Authorization URL is " + url);
			return url;
		}

		@Override
		protected void onPostExecute(String authURL) {
			super.onPostExecute(authURL);
			loadUrl(authURL);
		}
	}// class GetOAuthURL4TwitterAsyncTask

}
