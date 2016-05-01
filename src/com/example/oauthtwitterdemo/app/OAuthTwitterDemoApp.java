package com.example.oauthtwitterdemo.app;

import com.example.oauthtwitterdemo.auth.OAuthTwitterHelper;

import android.app.Application;
import android.util.Log;
import twitter4j.Twitter;
import twitter4j.auth.RequestToken;

public class OAuthTwitterDemoApp extends Application {

	private OAuthTwitterHelper oAuthTwitterHelper;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(getClass().getSimpleName(), "onCreate() invoked..");
		oAuthTwitterHelper = new OAuthTwitterHelper(this);
	}

	public RequestToken cookOAuthRequestToken() {
		return oAuthTwitterHelper.getOAuthRequestToken();
	}

	public boolean isNotAuthorized() {
		return !isAuthorized();
	}

	public boolean isAuthorized() {
		return oAuthTwitterHelper.hasAccessToken();
	}

	public void authorized(String oauthVerifier) {
		oAuthTwitterHelper.authorized(oauthVerifier);
	}

	public Twitter getTwitter() {
		return oAuthTwitterHelper.getTwitter();
	}

}
