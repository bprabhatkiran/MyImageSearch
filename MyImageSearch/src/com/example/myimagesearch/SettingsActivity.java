package com.example.myimagesearch;

import android.net.Uri;
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
		i.putExtra("finalUrl", finalSettingsURL());
		setResult(RESULT_OK, i);
		finish();
		return true;
	}

	public String finalSettingsURL() {
		StringBuilder finalUrlStringBuilder = new StringBuilder();
		if(size.length() > 0) {
			finalUrlStringBuilder.append("&imgsz="+Uri.encode(size));
		}
		if(type.length() > 0) {
			finalUrlStringBuilder.append("&imgtype="+Uri.encode(type));
		}
		if(color.length() > 0) {
			finalUrlStringBuilder.append("&imgcolor="+Uri.encode(color));
		}
		if(site.getText().toString().length() > 0) {
			finalUrlStringBuilder.append("&as_sitesearch="+Uri.encode(site.getText().toString()));
		}
		return finalUrlStringBuilder.toString();
	}

}
