package com.example.oauthtwitterdemo.activity;

import com.example.oauthtwitterdemo.R;
import com.example.oauthtwitterdemo.app.OAuthTwitterDemoApp;
import com.example.oauthtwitterdemo.base.BaseActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends BaseActivity {

	private OAuthTwitterDemoApp app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (OAuthTwitterDemoApp) getApplication();

		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (app.isNotAuthorized()) {
			authorize();
		}
		loadTweetsIfNotLoadedAlready();
	}

	private void authorize() {
		final Intent intent = new Intent(this, AuthorizationActivity.class);
		startActivity(intent);
	}

	private void loadTweetsIfNotLoadedAlready() {
		// TODO: Pending Implementation
	}
}