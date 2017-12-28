package com.dc.ftp.activity.user;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
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


public class ForgotPwdActivity extends SPBaseActivity implements OnClickListener {

	private EditText phoneEditor;
	private EditText usernameEditor;
	private String phoneNum;
	private String username;
	private ImageView imgUname;
	private ImageView imgPhone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgot_pwd);
		super.init();
		setHeadTitle(true,"找回登录密码");
		findViewById(R.id.forget_pwd).setOnClickListener(this);
		phoneEditor = (EditText) findViewById(R.id.user_phone);
		usernameEditor = (EditText) findViewById(R.id.user_name);
		imgUname = (ImageView) findViewById(R.id.img_uname);
		imgPhone = (ImageView) findViewById(R.id.img_phone);


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

	private void dohttp() {

		JsonBuilder builder = new JsonBuilder();
		builder.put("user_name", username);
		builder.put("user_phone", phoneNum);
		BaseHttpClient.post(getBaseContext(), Default.FORGOT_PWD_0, builder, new JsonHttpResponseHandler() {

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
				Log.i("DD", "找回密码获取的json----------"+response.toString());
				try {

					if (statusCode == 200) {
						if (response.getInt("status") == 1) {
							Log.i("DD", "找回密码获取的json----------"+response.toString());
							showToast(response.getString("message"));
							Intent intent = new Intent(ForgotPwdActivity.this, ForgotPwd2Activity.class);
								 intent.putExtra("phone", phoneNum);
								 startActivity(intent);
								 finish();
						} else {
							 showToast("用户名与手机号不匹配！");
							}

					}else{
						  showToast(R.string.link_out_of_time);
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.forget_pwd:
			phoneNum = phoneEditor.getText().toString().trim();
			username = usernameEditor.getText().toString().trim();
			
			if (username.equals("")) {
				showToast("请输入用户名");
				return;
			}
			if (phoneNum.equals("")) {
				showToast("请输入您的手机号");
				return;
			}
			dohttp();

			break;
		default:
			break;
		}

	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
