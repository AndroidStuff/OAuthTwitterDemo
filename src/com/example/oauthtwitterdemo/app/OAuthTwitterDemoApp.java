package com.example.oauthtwitterdemo.app;

import com.example.oauthtwitterdemo.auth.OAuthTwitterHelper;

import android.app.Application;
import android.util.Log;
import twitter4j.TwitterException;
import twitter4j.auth.RequestToken;

public class OAuthTwitterDemoApp extends Application {

	private OAuthTwitterHelper oAuthTwitterHelper;
	private RequestToken currentRequestToken;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(getClass().getSimpleName(), "onCreate() invoked..");
		oAuthTwitterHelper = new OAuthTwitterHelper(this);
	}

	private RequestToken cookOAuthRequestToken() {
		try {
			return oAuthTwitterHelper.getTwitter().getOAuthRequestToken();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean isNotAuthorized() {
		return !isAuthorized();
	}

	public boolean isAuthorized() {
		return oAuthTwitterHelper.hasAccessToken();
	}

	public String beginAuthorization() {
		String url = null;
		if (currentRequestToken == null) {
			currentRequestToken = cookOAuthRequestToken();
		}
		url = currentRequestToken.getAuthorizationURL();
		Log.i(getClass().getSimpleName(), "Authorization URL is " + url);
		return url;
	}
}
