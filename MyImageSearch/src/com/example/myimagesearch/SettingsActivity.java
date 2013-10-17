package com.example.myimagesearch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingsActivity extends Activity {
	// Encapsulate this later to a serializable type
	String size;
	String color;
	String type;
	String filterSite;
	
	EditText site;
	Spinner sizeSpinner;
	Spinner colorSpinner;
	Spinner typeSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		site = (EditText) findViewById(R.id.editText1);
		sizeSpinner = (Spinner) findViewById(R.id.spinner1);
		colorSpinner = (Spinner) findViewById(R.id.Spinner01);
		typeSpinner = (Spinner) findViewById(R.id.Spinner02);
		
		sizeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				size = (String)sizeSpinner.getItemAtPosition(arg2);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				size = (String)sizeSpinner.getItemAtPosition(0);
			}
			
		});
		
		typeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				type = (String)typeSpinner.getItemAtPosition(arg2);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				type = (String)typeSpinner.getItemAtPosition(0);
			}
			
		});
		
		colorSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				color = (String)colorSpinner.getItemAtPosition(arg2);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				color = (String)colorSpinner.getItemAtPosition(0);
				
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
	public boolean submit(View view) {
		Intent i = new Intent();
		i.putExtra("size", size);
		i.putExtra("color", color);
		i.putExtra("type", type);
		i.putExtra("site", site.getText().toString());
		finish();
		return true;
	}

}
