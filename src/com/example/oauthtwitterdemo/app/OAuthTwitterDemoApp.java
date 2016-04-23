package com.example.oauthtwitterdemo.app;

import com.example.oauthtwitterdemo.auth.OAuthTwitterHelper;

import android.app.Application;
import android.util.Log;

public class OAuthTwitterDemoApp extends Application {

	private OAuthTwitterHelper oAuthTwitterHelper;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(getClass().getSimpleName(), "onCreate() invoked..");
		oAuthTwitterHelper = new OAuthTwitterHelper();
	}

	public boolean isNotAuthorized() {
		return !isAuthorized();
	}

	public boolean isAuthorized() {
		return oAuthTwitterHelper.hasAccessToken();
	}
}
