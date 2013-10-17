package com.example.myimagesearch;

import java.util.ArrayList;

import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context myContext;
	ArrayList<ImageResult> imageUrls;
	
	public ImageAdapter(Context c, ArrayList<ImageResult> imageURLs2) {
		// TODO Auto-generated constructor stub
		myContext = c;
		this.imageUrls = imageURLs2;
	}

	@Override
	public int getCount() {
		return imageUrls.size();
	}

	@Override
	public ImageResult getItem(int position) {
		// TODO Auto-generated method stub
		return imageUrls.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageResult imageResult = this.getItem(position);
		SmartImageView imageView;
		if(convertView == null) {
			imageView = new SmartImageView(myContext);
			imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);
			imageView.setImageResource(android.R.color.transparent);
		} else {
			imageView = (SmartImageView)convertView;
		}
		imageView.setImageUrl(imageResult.thumbnailImageUrl);
		return imageView;
	}

	public void clear() {
		imageUrls.clear();
	}

	public void addAll(ArrayList<ImageResult> arrayList) {
		imageUrls.addAll(arrayList);
	}
}
