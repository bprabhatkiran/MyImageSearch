package com.example.myimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends Activity {
	GridView gridImageView;
	EditText inputEditText;
	ImageAdapter urlToImageAdapter;

	int currentPageIndex = 1;

	// This is going to store the Image URLs
	ArrayList<ImageResult> imageURLs = new ArrayList<ImageResult>();
	String advancedSetting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gridImageView = (GridView) findViewById(R.id.imageGridView);
		inputEditText = (EditText)findViewById(R.id.input_text);
		urlToImageAdapter = new ImageAdapter(this, imageURLs);
		gridImageView.setAdapter(urlToImageAdapter);
		gridImageView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// Use arg2 to get the position and have a new activity display the full size image
				ImageResult imageResult = urlToImageAdapter.getItem(arg2);
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				i.putExtra("imageResult", imageResult);
				startActivityForResult(i, 0);
			}
		});
		gridImageView.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// Whatever code is needed to append new items to your AdapterView
				// probably sending out a network request and appending items to your adapter. 
				// Use the page or the totalItemsCount to retrieve correct data.
				searchImages(++currentPageIndex); 
				// or customLoadMoreDataFromApi(totalItemsCount); 
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean openSettings(MenuItem item) {
		Intent i = new Intent(getBaseContext(), SettingsActivity.class);
		startActivityForResult(i, 0);
		return true;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				Bundle extras = data.getExtras();
				advancedSetting = extras.getString("finalUrl");
			}
		}
	}

	public boolean searchImages(View view) {
		urlToImageAdapter.clear();
		searchImages(currentPageIndex);
		return true;
	}

	public void searchImages(int page) {
		StringBuilder finalUrlStringBuilder = new StringBuilder();
		finalUrlStringBuilder.append("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=");
		finalUrlStringBuilder.append(Uri.encode(inputEditText.getText().toString()));
		finalUrlStringBuilder.append("&start=" + Integer.toString(page));
		if(advancedSetting != null) {
			finalUrlStringBuilder.append(advancedSetting);
		}
		connect(finalUrlStringBuilder.toString());
	}

	// https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=
	public void connect(String url) {
		// Toast.makeText(getBaseContext(), url, Toast.LENGTH_SHORT).show();
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new JsonHttpResponseHandler() {
			public void onSuccess(JSONObject response) {
				try {
					JSONArray results = response.getJSONObject("responseData").getJSONArray("results");
					// Strip the URLs from the result
					urlToImageAdapter.addAll(ImageResult.parseResults(results));
					((BaseAdapter)gridImageView.getAdapter()).notifyDataSetChanged();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
