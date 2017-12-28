package com.dc.ftp.http;

import com.dc.ftp.global.Default;

import org.json.JSONException;
import org.json.JSONObject;



public class JsonBuilder {

	private JSONObject json = null;

	public JsonBuilder() {
		json = new JSONObject();
		try {
			json.put("uid", Default.userId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public JSONObject getJson() {
		return json;
	}

	public void put(String name, boolean value) {
		try {
			json.put(name, value);
		} catch (JSONException e) {

			e.printStackTrace();
		}
	}

	public void put(String name, int value) {
		try {
			json.put(name, value);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void put(String name, long value) {
		try {
			json.put(name, value);
		} catch (JSONException e) {

			e.printStackTrace();
		}
	}

	public void put(String name, double value) {
		try {
			json.put(name, value);
		} catch (JSONException e) {

			e.printStackTrace();
		}
	}

	public void put(String name, Object value) {
		try {
			json.put(name, value);
		} catch (JSONException e) {

			e.printStackTrace();
		}
	}

	public void putOpt(String name, Object value) {
		try {
			json.putOpt(name, value);
		} catch (JSONException e) {

			e.printStackTrace();
		}
	}

	public String toJsonString() {
		return json.toString();
	}
}
