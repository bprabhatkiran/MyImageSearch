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
	
	String size;
	String color;
	String type;
	String site;
	
	// This is going to store the Image URLs
	// Setup adapter for this
	ArrayList<ImageResult> imageURLs = new ArrayList<ImageResult>();
	
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
    			size = extras.getString("size");
    			color = extras.getString("color");
    			type = extras.getString("type");
    			site = extras.getString("site");
    		}
    	}
    }
    
    public boolean searchImages(View view) {
    		String input = inputEditText.getText().toString();
    		connect(new StringBuilder().append("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=").append(Uri.encode(input)).toString());
    		return true;
    }
    
    // https://www.google.com/search?hl=en&q=interesting&tbm=isch
    // https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=
    public void connect(String url) {
    		Toast.makeText(getBaseContext(), url, Toast.LENGTH_SHORT).show();
    		AsyncHttpClient client = new AsyncHttpClient();
    		client.get(url, new JsonHttpResponseHandler() {
    			public void onSuccess(JSONObject response) {
    				// Lets clear out the existing contents
    				urlToImageAdapter.clear();
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
