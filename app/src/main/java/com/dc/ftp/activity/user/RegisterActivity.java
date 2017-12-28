package com.dc.ftp.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dc.ftp.R;
import com.dc.ftp.base.SPBaseActivity;
import com.dc.ftp.global.Default;
import com.dc.ftp.http.BaseHttpClient;
import com.dc.ftp.http.JsonBuilder;
import com.dc.ftp.http.JsonHttpResponseHandler;
import com.dc.ftp.utils.SystemApi;

import org.apache.http.Header;
import org.json.JSONObject;


public class RegisterActivity extends SPBaseActivity implements OnClickListener {
	private EditText mName, mPassw, mPassw2, mPhone, mPhoneNum, register_people;
	private String info[];
	private TimeCount time;
	private Button registerContextBtn, mSendPhoneNum;
	private boolean mRemember = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		super.init();
		setHeadTitle(true,"注 册");

		TextView register = (TextView) findViewById(R.id.register);
		register.setOnClickListener(this);


		findViewById(R.id.show_context).setOnClickListener(this);
		registerContextBtn = (Button) findViewById(R.id.register_flag);
		registerContextBtn.setOnClickListener(this);

		mSendPhoneNum = (Button) findViewById(R.id.sendphonenum);
		mSendPhoneNum.setOnClickListener(this);

		
		mName = (EditText) findViewById(R.id.editname);
		mPassw = (EditText) findViewById(R.id.editpassw);
		mPassw2 = (EditText) findViewById(R.id.editpassw2);
		mPhone = (EditText) findViewById(R.id.editem_phone);
		mPhoneNum = (EditText) findViewById(R.id.editem_phonenum);
		register_people = (EditText) findViewById(R.id.editem_people);

		time = new TimeCount(60000, 1000);

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



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register:
	     /*//模拟注册成功-------------------------
			Intent intent = new Intent();
			intent.putExtra("name", "饥渴的蜗牛");
			intent.putExtra("password", "hjl555");	
			setResult(Default.RESULT_REGISTER_TO_LOGIN, intent);
			finish();	*/
			
			info = getInfo();
			if (info == null) {
				return;
			}
			doHttp();
			break;
		case R.id.sendphonenum:
			time.start();
			String phone = mPhone.getText().toString().trim();
			if (phone.equals("")) {
				showToast("手机号不能为空");
				return;
			}
			if (!SystemApi.isMobileNO(phone)) {
				showToast("请输入合法的手机号");
				return;
			}
			doHttpSendPhone(phone);
			break;
		case R.id.register_flag:
			if (mRemember) {
				mRemember = false;
				registerContextBtn.setBackgroundResource(R.mipmap.b_chech_1_0);
			} else {
				mRemember = true;
				registerContextBtn.setBackgroundResource(R.mipmap.b_chech_1_1);
			}
			break;
		case R.id.show_context:

			startActivity(new Intent(RegisterActivity.this, ShowRegisterContextActivity.class));
			break;
		}

	}

	public String[] getInfo() {
		String name = mName.getText().toString().trim();
		String passw = mPassw.getText().toString().trim();
		String passw2 = mPassw2.getText().toString().trim();
		String phone = mPhone.getText().toString().trim();
		String phonenum = mPhoneNum.getText().toString().trim();
		String em_people = register_people.getText().toString().trim();
		if (name.equals("")) {
			showToast("请输入用户名");
			return null;
		} else if (SystemApi.ByteLenth(name) < 4 || SystemApi.ByteLenth(name) > 20) {
			showToast("用户名应为4-20个字母、数字、汉字、下划线");
			return null;
		}


		if (passw.equals("")) {
			showToast("请输入密码");

			return null;
		} else if (passw.length() < 6) {
			showToast("密码最低6位字符");
			return null;
		} else if (passw.length() > 16) {
			showToast("密码最多不超过16位字符");
			return null;
		}

		if(passw2.equals("")){
			showToast("请再次输入密码");
			return null;
		}

		if (!passw.equals(passw2)) {
			showToast("两次密码输入不一致");
			return null;
		}


		if (phone.equals("")||!SystemApi.isMobileNO(phone)) {
			showToast("请输入合法的手机号");

			return null;
		}

		if (phonenum.equals("")) {
			showToast("请输入短信验证码");

			return null;
		}


		if (!mRemember) {
			showToast("同意注册协义才可以注册");
			return null;
		}

		return new String[] { name, passw, phone, phonenum, em_people };
	}

	public void doHttp() {

		JsonBuilder builder = new JsonBuilder();
		builder.put("name", info[0]);
		builder.put("password", info[1]);
		builder.put("tel", info[2]);
		builder.put("tel2", info[3]);
		builder.put("register_rec", info[4]);

		BaseHttpClient.post(getBaseContext(), Default.register, builder, new JsonHttpResponseHandler() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				showLoadingToast();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, json);
				hideLoadingToast();
				try {

					if (statusCode == 200) {
						if (json.getInt("status") == 1) {

							showToast("注册成功！");

							Intent intent = new Intent();
							intent.putExtra("name", info[0]);
							intent.putExtra("password", info[1]);

							Default.userId = json.getLong("uid");

							setResult(Default.RESULT_REGISTER_TO_LOGIN, intent);
							finish();
						} else {
							showToast(json.getString("message"));
						}
					} else {
						showToast(R.string.link_out_of_time);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, responseString, throwable);
				hideLoadingToast();
				showToast(responseString);

			}

		});

	}

	public void doHttpSendPhone(String phone) {

		JsonBuilder builder = new JsonBuilder();
		builder.put("phone", phone);

		BaseHttpClient.post(getBaseContext(), Default.registerPhone, builder, new JsonHttpResponseHandler() {

			@Override
			public void onStart() {
				super.onStart();
				showLoadingToast();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
				super.onSuccess(statusCode, headers, json);
				hideLoadingToast();
				try {
					String msg = json.getString("message");
					showToast(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				hideLoadingToast();
				showToast(responseString);

			}

		});

	}

	/* 倒计时类 */
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {
			mSendPhoneNum.setText("发送");
			mSendPhoneNum.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			mSendPhoneNum.setClickable(false);
			mSendPhoneNum.setText(millisUntilFinished / 1000 + "秒");
		}

	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		time.cancel();
	}
}
