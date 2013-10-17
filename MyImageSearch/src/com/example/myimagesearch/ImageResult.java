package com.example.myimagesearch;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

@SuppressWarnings("serial")
public class ImageResult implements Serializable {
	public String thumbnailImageUrl;
	public String fullSizeImageUrl;

	public static ArrayList<ImageResult> parseResults(JSONArray results) {
    		ArrayList<ImageResult> imageURLResults = new ArrayList<ImageResult>();
    		for (int i = 0;i < results.length(); i++) {
    			try {
    					ImageResult element = new ImageResult();
    					element.thumbnailImageUrl = results.getJSONObject(i).getString("tbUrl");
    					element.fullSizeImageUrl = results.getJSONObject(i).getString("url");
					imageURLResults.add(element);
				} catch (JSONException e) {
					e.printStackTrace();
				}
    		}
    		return imageURLResults;
    }

}
