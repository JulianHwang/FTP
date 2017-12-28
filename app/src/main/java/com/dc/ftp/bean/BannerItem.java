package com.dc.ftp.bean;

import com.dc.ftp.global.Default;

import org.json.JSONException;
import org.json.JSONObject;



public class BannerItem {

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	private int id;
	private String picPath;
	private String title;
	private String content;
		

	public BannerItem(JSONObject json) throws JSONException {

		if (json.has("id")) {
			setId(json.getInt("id"));
		}

		if (json.has("title")) {
			setTitle(json.getString("title"));

		}

		if (json.has("pic")) {
             
           setPicPath(Default.ip + json.getString("pic"));

		}
		if (json.has("content")) {
			setContent(json.getString("content"));
		}

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	
	

}
