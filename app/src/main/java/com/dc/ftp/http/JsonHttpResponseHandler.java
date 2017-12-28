package com.dc.ftp.http;

import org.apache.http.Header;
import org.json.JSONObject;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dc.ftp.MainTabActivity;
import com.dc.ftp.activity.user.SPLoginActivity;
import com.dc.ftp.global.Default;




public class JsonHttpResponseHandler extends com.loopj.android.http.JsonHttpResponseHandler {


	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}




	@Override
	public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
		// TODO Auto-generated method stub

		Log.i("DD", "红包json-编码格式-----"+response.toString());
		if (statusCode == 200) {

			try {
				if (response != null && response.has("session_expired")) {
					if (response.getInt("session_expired") == 1) {
						Context a = Default.currentContext;
						if (a == null) {
							a = MainTabActivity.mainTabActivity;
						}
						Intent intent = new Intent(a, SPLoginActivity.class);
						intent.putExtra("exit", true);
						a.startActivity(intent);

					}

				} else {
					super.onSuccess(statusCode, headers, response);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
		// TODO Auto-generated method stub
		super.onFailure(statusCode, headers, responseString, throwable);
	}

}
