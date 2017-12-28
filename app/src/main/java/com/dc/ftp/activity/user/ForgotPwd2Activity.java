package com.dc.ftp.activity.user;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dc.ftp.R;
import com.dc.ftp.base.SPBaseActivity;
import com.dc.ftp.global.Default;
import com.dc.ftp.http.BaseHttpClient;
import com.dc.ftp.http.JsonBuilder;
import com.dc.ftp.http.JsonHttpResponseHandler;

public class ForgotPwd2Activity extends SPBaseActivity implements OnClickListener {

	private EditText userYzm;
	private String strYzm;
	private EditText userPwd, userPwd2;
	private String strPwd;

	private TextView mText;
	private String phoneNum;

	private Handler mhandle = new Handler();
	private Runnable runnbale = new Runnable() {

		@Override
		public void run() {
			changeSendMessageBtn();
		}
	};

	private int timeMax = 60;
	private int time;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgot_pwd2);
		super.init();
	    setHeadTitle(true,"找回登录密码");

		findViewById(R.id.forget_pwd).setOnClickListener(this);

		userYzm = (EditText) findViewById(R.id.user_yzm);
		userPwd = (EditText) findViewById(R.id.user_pwd);
		userPwd2 = (EditText) findViewById(R.id.user_pwd2);

		mText = (TextView) findViewById(R.id.send);
		mText.setOnClickListener(this);
		mText.setEnabled(true);

		Intent intent = getIntent();
		phoneNum = intent.getExtras().getString("phone");
		TextView text = (TextView) findViewById(R.id.phone);
		text.setText(phoneNum);


	}

	@Override
	public void initView() {

	}

	@Override
	public void initData() {

	}

	@Override
	public void initEvent() {

	}



	//获取短信验证码
	private void dohttp() {

		JsonBuilder builder = new JsonBuilder();
		builder.put("phone", phoneNum);
		BaseHttpClient.post(getBaseContext(), Default.FORGOT_PWD_1, builder, new JsonHttpResponseHandler() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				showLoadingToast();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				hideLoadingToast();
				try {

					if (statusCode == 200) {
						if (response.getInt("status") == 1) {
							showToast(response.getString("message"));

							mhandle.postDelayed(runnbale, 1000);
						} else {
							showToast(response.getString("message"));
						}


					}else {
						showToast(R.string.link_out_of_time);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, responseString, throwable);
				hideLoadingToast();
			}
		});

	}

	//提交新密码跟验证码执行修改密码操作
	private void dohttp2() {

		JsonBuilder builder = new JsonBuilder();
		builder.put("code", strYzm);
		builder.put("password", strPwd);
		BaseHttpClient.post(getBaseContext(), Default.FORGOT_PWD_3, builder, new JsonHttpResponseHandler() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				showLoadingToast();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				hideLoadingToast();
				try {

					if (statusCode == 200) {
						if (response.getInt("status") == 1) {
							showToast(response.getString("message"));
							finish();
						} else {
							showToast(response.getString("message"));
						}

					}else{
						showToast(R.string.link_out_of_time);
					}

				} catch (Exception e) {
					// TODO: handle exception
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, responseString, throwable);
				hideLoadingToast();
			}
		});

	}

	private void changeSendMessageBtn() {
		time = time + 1000;

		if (time < 1000 * timeMax) {
			mText.setEnabled(false);
			mText.setText((1000 * timeMax - time) / 1000 + "秒后重试");
			mhandle.postDelayed(runnbale, 1000);			
		} else {
			time = 0;
			mText.setText("获取验证码");
			mText.setEnabled(true);
			stop();
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.send:
           Log.i("DD", "点击发送了验证码--------");
			dohttp();
			//mText.setEnabled(false);
			break;

		case R.id.forget_pwd:
			strYzm = userYzm.getText().toString().trim();
			strPwd = userPwd.getText().toString().trim();
			String pwd2 = userPwd2.getText().toString().trim();
			if (strYzm.equals("")) {
				showToast("请输入短信验证码");
				return;
			}

			if (strPwd.equals("")) {
				showToast("请输入新登录密码");
				return;
			}
			if (!strPwd.equals(pwd2)) {
				showToast("两次输入密码不一样，请重新输入");
				return;
			}

			dohttp2();

			break;

		default:
			break;
		}

	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		stop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		stop();
	}

	public void stop() {
		mhandle.removeCallbacks(runnbale);
	}

	
	
}
