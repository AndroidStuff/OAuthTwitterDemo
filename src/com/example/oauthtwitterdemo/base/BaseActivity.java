package com.example.oauthtwitterdemo.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public abstract class BaseActivity extends Activity {

	private ProgressDialog progressDialog;
	private boolean destroyed = false;
	private Toast noInternetToast;

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

	protected boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		} else {
			displayNoInternetMessage();
			return false;
		}
	}

	private void displayNoInternetMessage() {
		if (noInternetToast == null) {
			noInternetToast = Toast.makeText(this, "Oops, your device has no Internet connection!",
					Toast.LENGTH_LONG);
			noInternetToast.setGravity(Gravity.CENTER, 0, 0);
		}
		noInternetToast.show();
	}

	protected boolean isOffline() {
		return !isOnline();
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
