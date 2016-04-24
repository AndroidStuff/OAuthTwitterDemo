package com.example.oauthtwitterdemo.auth;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.example.oauthtwitterdemo.R;

import android.content.Context;
import android.util.Log;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class OAuthTwitterHelper {

	private static final String CONSUMER_SECRET_KEY = "consumer_secret_key";
	private static final String CONSUMER_KEY = "consumer_key";

	private String accessToken;
	private Twitter twitter;
	private Context context;
	private String consumerSecretKey;
	private String consumerKey;

	public OAuthTwitterHelper(Context context) {
		this.context = context;
		cookAppsConsumerKeys();
		twitter = cookTwitterInstance();
	}

	private Twitter cookTwitterInstance() {
		Twitter twitter = TwitterFactory.getSingleton();
		twitter.setOAuthConsumer(consumerKey, consumerSecretKey);
		return twitter;
	}

	private void cookAppsConsumerKeys() {
		Properties props = new Properties();
		InputStream is = context.getResources().openRawResource(R.raw.oauth);
		try {
			props.load(is);
			consumerKey = (String) props.get(CONSUMER_KEY);
			consumerSecretKey = (String) props.get(CONSUMER_SECRET_KEY);
		} catch (IOException e) {
			Log.e(getClass().getSimpleName(), e.getMessage());
			throw new RuntimeException("Unable to load App's Consumer Keys", e);
		}
	}

	public boolean hasAccessToken() {
		return accessToken != null;
	}

	public Twitter getTwitter() {
		return twitter;
	}
}
