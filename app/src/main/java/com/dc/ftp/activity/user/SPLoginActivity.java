package com.dc.ftp.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.dc.ftp.R;
import com.dc.ftp.base.SPBaseActivity;
import com.dc.ftp.global.Default;
import com.dc.ftp.http.BaseHttpClient;
import com.dc.ftp.http.JsonBuilder;
import com.dc.ftp.http.JsonHttpResponseHandler;
import com.dc.ftp.utils.MyLog;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/11/29.
 */

public class SPLoginActivity extends SPBaseActivity implements View.OnClickListener{

    private TextView tvLogin;
    private ToggleButton mCheckbox;
    private String mName;
    private String mPassword;
    private boolean mRemember=true;

    private EditText etName;
    private EditText etPwd;
    private TextView tvForgetPwd;
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_login);
        super.init();
        setHeadTitle(true,"用户登录");
    }

    @Override
    public void initView() {
        tvLogin = (TextView) findViewById(R.id.tv_login);
        mCheckbox = (ToggleButton) findViewById(R.id.remember);
         etName = (EditText) findViewById(R.id.et_name);
         etPwd = (EditText) findViewById(R.id.et_pwd);
         tvForgetPwd = (TextView) findViewById(R.id.forget_pwd);
        tvRegister = (TextView) findViewById(R.id.register);

        mCheckbox.setChecked(true);
        mCheckbox.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvForgetPwd.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    @Override
    public void initData() {
      if (getNP()){
          etName.setText(mName);
          etPwd.setText(mPassword);
          MyLog.i("DDD","getNP():mName="+mName+"mPassword="+mPassword);
      }
        mCheckbox.setChecked(mRemember ? true : false);
    }

    @Override
    public void initEvent() {

    }


    public void saveNP() {
        SharedPreferences sp = getSharedPreferences(Default.userPreferences, 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(Default.userName, mName);
        edit.putString(Default.userPassword, mPassword);
        edit.putBoolean(Default.userRemember, mRemember);
        MyLog.i("DDD","saveNP():mName="+mName+"mPassword="+mPassword);

        edit.commit();
    }

    public void clearNP() {
        SharedPreferences sp = getSharedPreferences(Default.userPreferences, 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(Default.userName, "");
        edit.putString(Default.userPassword, "");
        edit.putBoolean(Default.userRemember, false);
        edit.commit();
    }

    public boolean getNP() {
        SharedPreferences sp = getSharedPreferences(Default.userPreferences, 0);
        mName = sp.getString(Default.userName, "");
        mPassword = sp.getString(Default.userPassword, "");
        mRemember = sp.getBoolean(Default.userRemember, false);

        if (mName.equals("") || mPassword.equals("")) {
            return false;
        } else {
            return true;
        }
    }









    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.remember:
                if (mRemember) {
                    mRemember = false;
                    mCheckbox.setChecked(false);
                } else {
                    mRemember = true;
                    mCheckbox.setChecked(true);
                }


                break;
            case R.id.tv_login:
                mName=etName.getText().toString().trim();
                mPassword=etPwd.getText().toString().trim();

              // 是否保存密码
                if (mRemember) {
                    saveNP();
                } else {
                    clearNP();
                }

                doHttpLogin();

                break;
            case R.id.forget_pwd:
                startActivity(new Intent(this,ForgotPwdActivity.class));
                break;
            case R.id.register:
                startActivityForResult(new Intent(this,RegisterActivity.class),10);
                break;
            default:break;

        }

    }

    private void doHttpLogin() {
        JsonBuilder builder = new JsonBuilder();
        builder.put("sUserName", mName);
        builder.put("sPassword", mPassword);
        BaseHttpClient.post(this,Default.login,builder,new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                super.onStart();
                showLoadingToast();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                hideLoadingToast();
                MyLog.i("DDD","loginSuccess-----"+response.toString());

                if(statusCode==200) {
                    try {
                        if (response.getInt("status") == 1) {
                            initJson(response);
                            showToast(R.string.login_success);
                            finish();
                        }else{
                            showToast(response.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        showToast("登录异常");
                    }
                }else {
                    showToast(R.string.link_out_of_time);
                }

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });


    }

    public void initJson(JSONObject response) {
        try {
            Default.userId=response.getLong("uid");
            Default.userName=response.getString("username");
            Default.phone=response.getString("phone");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== Activity.RESULT_CANCELED){
            return;
        }

        if (requestCode==10&&resultCode == Default.RESULT_REGISTER_TO_LOGIN) {
            etName.setText(data.getExtras().getString("name"));
            etPwd.setText(data.getExtras().getString("password"));
        }
    }

}
