package com.example.oauthtwitterdemo.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.oauthtwitterdemo.R;
import com.example.oauthtwitterdemo.app.OAuthTwitterDemoApp;
import com.example.oauthtwitterdemo.base.BaseListActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class MainActivity extends BaseListActivity {

	private static final int SIMPLE_LIST_ITEM1 = android.R.layout.simple_list_item_1;
	private OAuthTwitterDemoApp app;
	List<Status> homeTimeline;
	private ArrayAdapter<Status> tweetListAdaptor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (OAuthTwitterDemoApp) getApplication();

		setContentView(R.layout.activity_main);
		homeTimeline = new ArrayList<Status>(0);
		tweetListAdaptor = new ArrayAdapter<Status>(this, SIMPLE_LIST_ITEM1, homeTimeline);
		setListAdapter(tweetListAdaptor);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (isOffline()) {
			return;
		}
		if (app.isNotAuthorized()) {
			authorize();
			return;
		}
		loadTimelineIfNotLoaded();
	}

	private void authorize() {
		final Intent intent = new Intent(this, AuthorizationActivity.class);
		startActivity(intent);
	}

	private void loadTimelineIfNotLoaded() {
		new TwitterHomeTimelineAsyncTask().execute();
	}

	private void loadHomeTimeline() {
		tweetListAdaptor.notifyDataSetChanged();
	}

	class TwitterHomeTimelineAsyncTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showProgressDialog("");
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				Twitter twitter = app.getTwitter();
				homeTimeline.addAll(twitter.getHomeTimeline());
			} catch (TwitterException e) {
				Log.e(getClass().getSimpleName(), "Problem loading Twitter Home Timeline: " + e.getErrorMessage());
				throw new RuntimeException(e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			dismissProgressDialog();
			loadHomeTimeline();
		}
	}// class TwitterHomeTimelineAsyncTask
}