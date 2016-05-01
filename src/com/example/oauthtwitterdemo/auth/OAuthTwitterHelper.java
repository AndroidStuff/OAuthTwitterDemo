package com.example.oauthtwitterdemo.auth;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.example.oauthtwitterdemo.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class OAuthTwitterHelper {

	private static final String CONSUMER_SECRET_KEY = "consumer_secret_key";
	private static final String CONSUMER_KEY = "consumer_key";
	private static final String AUTH_KEY = "auth_key";
	private static final String AUTH_SECRET_KEY = "auth_secret_key";

	private AccessToken accessToken;
	private Twitter twitter;
	private RequestToken requestToken;
	private Context context;
	private String consumerSecretKey;
	private String consumerKey;
	private SharedPreferences sharedPreferences;

	public OAuthTwitterHelper(Context context) {
		this.context = context;
		cookAppsConsumerKeys();
		sharedPreferences = context.getSharedPreferences("OAuthTwitterDemo", Context.MODE_PRIVATE);
		twitter = cookTwitterInstance();
	}

	public Twitter cookTwitterInstance() {
		twitter = TwitterFactory.getSingleton(); //new TwitterFactory().getInstance();
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

	public RequestToken getOAuthRequestToken(boolean tryAgain) {
		try {
			if (twitter == null) {
				twitter = cookTwitterInstance();
				requestToken = null;
			}
			if (requestToken == null) {
				requestToken = twitter.getOAuthRequestToken();
			}
		} catch (TwitterException e) {
			if (tryAgain) {
				Log.e(getClass().getSimpleName(),
						"Trying again..Exception occurred during getOAuthRequestToken(..) invocation. "
								+ e.getErrorMessage());
				return getOAuthRequestToken(false);
			} else {
				throw new RuntimeException(e);
			}
		}
		return requestToken;
	}

	public RequestToken getOAuthRequestToken() {
		return getOAuthRequestToken(true);
	}

	public AccessToken getOAuthAccessToken(String oauthVerifier) {
		// if (twitter == null) {
		// twitter = cookTwitterInstance();
		// }
		try {
			return twitter.getOAuthAccessToken(requestToken, oauthVerifier);
		} catch (TwitterException e) {
			throw new RuntimeException(e);
		}
	}

	public void authorized(String oauthVerifier) {
		Log.d(getClass().getSimpleName(), "Authorizing..");
		accessToken = getOAuthAccessToken(oauthVerifier);
		storeAccessToken(accessToken);
	}

	private void storeAccessToken(AccessToken accessToken) {
		Log.d(getClass().getSimpleName(), "Storing Access Token..");
		final Editor editor = sharedPreferences.edit();
		editor.putString(AUTH_KEY, accessToken.getToken());
		editor.putString(AUTH_SECRET_KEY, accessToken.getTokenSecret());
		editor.commit();
		this.accessToken = accessToken;
	}
}
