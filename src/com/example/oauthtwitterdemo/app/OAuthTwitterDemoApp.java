package com.example.oauthtwitterdemo.app;

import android.app.Application;
import android.util.Log;

public class OAuthTwitterDemoApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(getClass().getSimpleName(), "onCreate() invoked..");
	}
}
