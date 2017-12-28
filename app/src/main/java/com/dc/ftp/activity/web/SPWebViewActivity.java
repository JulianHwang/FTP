package com.dc.ftp.activity.web;


import com.dc.ftp.R;
import com.dc.ftp.base.SPBaseActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class SPWebViewActivity extends SPBaseActivity {
	ImageView iv_back;
	private WebView webView;
	private String title;
	private String url;;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		super.init();//初始化init那三个抽象方法&title&back

	}

	@Override
	public void initView() {
		webView = (WebView) findViewById(R.id.webView);
		WebSettings settings = webView.getSettings();
		settings.setUseWideViewPort(true);//设定支持viewport
		settings.setLoadWithOverviewMode(true);
		settings.setBuiltInZoomControls(true);
		settings.setSupportZoom(true);//设定支持缩放
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});

	}

	@Override
	public void initData() {
		Intent intent = getIntent();
		title = intent.getStringExtra("title");
		url = intent.getStringExtra("url");
		webView.loadUrl(url);
	}

	@Override
	public void initEvent() {

	}



}
