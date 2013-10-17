package com.example.myimagesearch;

import com.loopj.android.image.SmartImageView;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class ImageDisplayActivity extends Activity {
	SmartImageView fullSizeImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		Bundle extras = getIntent().getExtras();
		ImageResult imageResult = (ImageResult) extras.get("imageResult");
		fullSizeImageView = (SmartImageView)findViewById(R.id.imageView1);
		fullSizeImageView.setImageUrl(imageResult.fullSizeImageUrl);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_display, menu);
		return true;
	}
	
	public boolean returnBack(View view) {
		// Create an intent and pass in data if needed
		finish();
		return true;
	}

}
