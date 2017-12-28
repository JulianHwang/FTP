package com.dc.ftp.activity.home;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.dc.ftp.R;
import com.dc.ftp.base.SPBaseActivity;
import com.dc.ftp.global.Default;
import com.dc.ftp.http.BaseHttpClient;
import com.dc.ftp.http.JsonBuilder;
import com.dc.ftp.http.JsonHttpResponseHandler;
import com.dc.ftp.utils.MyLog;


public class ShowBannerViewDetail extends SPBaseActivity  {

	private WebView contentWebView = null;
	private ArrayList<String> imageArray;
	private int id;
	private String content;
	private TextView titleView;
	private TextView tvShare;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_banner_detail);
        super.init();

	}



	@Override
	public void initView() {
		content = getIntent().getStringExtra("content");
		Log.i("HH", "url------"+content);
		imageArray = new ArrayList<String>();
		setHeadTitle(true,"详情页");
		contentWebView = (WebView) findViewById(R.id.myweb);
		contentWebView.getSettings().setJavaScriptEnabled(true);
		contentWebView.setWebViewClient(new MyWebViewClient());
	}

	@Override
	public void initData() {
		contentWebView.loadUrl(content);
	}

	@Override
	public void initEvent() {

	}




	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onPageFinished(WebView view, String url) {

			view.getSettings().setJavaScriptEnabled(true);

			super.onPageFinished(view, url);


		}



		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

			super.onReceivedError(view, errorCode, description, failingUrl);

		}
	}


}