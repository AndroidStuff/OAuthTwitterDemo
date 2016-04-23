package com.example.oauthtwitterdemo.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

public abstract class BaseActivity extends Activity {

	private ProgressDialog progressDialog;
	private boolean destroyed = false;

	protected void showProgressDialog(CharSequence message) {
		Log.d(getClass().getSimpleName(), "In showProgressDialog(..)");
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setIndeterminate(true);
		}
		progressDialog.setMessage(message);
		progressDialog.show();
	}

	protected void dismissProgressDialog() {
		Log.d(getClass().getSimpleName(), "In dismissProgressDialog()");
		if (progressDialog != null && !destroyed) {
			progressDialog.dismiss();
		}
	}

	/****************************************************************
	 *
	 * Activity Life-cycle methods below
	 *
	 ****************************************************************/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(getClass().getSimpleName(), "In onCreate(Bundle savedInstanceState)");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(getClass().getSimpleName(), "In onDestroy()");
		destroyed = true;
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		Log.d(getClass().getSimpleName(), "In onLowMemory()");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(getClass().getSimpleName(), "In onPause()");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(getClass().getSimpleName(), "In onResume()");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d(getClass().getSimpleName(), "In onSaveInstanceState(Bundle outState)");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(getClass().getSimpleName(), "In onStart()");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(getClass().getSimpleName(), "In onStop()");
	}

}
