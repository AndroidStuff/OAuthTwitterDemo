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

	public RequestToken cookOAuthRequestToken() {
		try {
			currentRequestToken = oAuthTwitterHelper.getTwitter().getOAuthRequestToken();
		} catch (TwitterException e) {
			currentRequestToken = null;
			e.printStackTrace();
		}
		return currentRequestToken;
	}

	public boolean isNotAuthorized() {
		return !isAuthorized();
	}

	public boolean isAuthorized() {
		return oAuthTwitterHelper.hasAccessToken();
	}

	public RequestToken getCurrentRequestToken() {
		return currentRequestToken;
	}

}
