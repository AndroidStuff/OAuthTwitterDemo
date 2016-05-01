package com.example.oauthtwitterdemo.activity;

import com.example.oauthtwitterdemo.R;
import com.example.oauthtwitterdemo.app.OAuthTwitterDemoApp;
import com.example.oauthtwitterdemo.base.BaseActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AuthorizationActivity extends BaseActivity {

	private OAuthTwitterDemoApp app;
	private WebView webView;
	private WebViewClient webViewClient = new WebViewClient() {
		private static final String WEB_VIEW_CLIENT = "WebViewClient";
		@Override
		public void onLoadResource(WebView view, String url) {
			Uri uri = Uri.parse(url);
			if (uri.getHost().equals("codonomics.com")) {
				final String oauth_verifier = uri.getQueryParameter("oauth_verifier");
				if (oauth_verifier != null) {
					webView.setVisibility(View.INVISIBLE);
					new OAuthorizeUser4TwitterAsyncTask().execute(oauth_verifier);
				} else {
					// TODO: Ask user to try again
					Log.e(WEB_VIEW_CLIENT,
							"We got a strange case where oauth_token is not found in callback URL. We wonder why??..");
				}
			} else {
				super.onLoadResource(view, url);
			}
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

	private void goToPreviousScreen() {
		finish();
	}

	class GetOAuthURL4TwitterAsyncTask extends AsyncTask<Void, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showProgressDialog("");
		}

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
			dismissProgressDialog();
			loadUrl(authURL);
		}
	}// class GetOAuthURL4TwitterAsyncTask

	class OAuthorizeUser4TwitterAsyncTask extends AsyncTask<String, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showProgressDialog("");
		}

		@Override
		protected Void doInBackground(String... params) {
			app.authorized(params[0]);
			Log.d("OAuthorizeUser4TwitterAsyncTask", "Authorization successful..");
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			dismissProgressDialog();
			goToPreviousScreen();
		}
	}// class OAuthorizeUser4TwitterAsyncTask

}
