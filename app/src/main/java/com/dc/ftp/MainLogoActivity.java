package com.dc.ftp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dc.ftp.base.SPBaseActivity;
import com.dc.ftp.broadcast.NetWorkStatusBroadcastReceiver;
import com.dc.ftp.global.Default;
import com.dc.ftp.http.BaseHttpClient;
import com.dc.ftp.http.JsonBuilder;
import com.dc.ftp.http.JsonHttpResponseHandler;
import com.dc.ftp.utils.MyLog;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 程序入口activity
 */
public class MainLogoActivity extends SPBaseActivity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;
    private ImageView startPic;
    private Button mSendPhoneNum;
    private MainLogoActivity.TimeCount time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startPic = (ImageView) findViewById(R.id.startPic);

        mSendPhoneNum = (Button) findViewById(R.id.sendphonenum);
        mSendPhoneNum.setOnClickListener(this);
        mSendPhoneNum.setVisibility(View.INVISIBLE);
        mSendPhoneNum.setClickable(false);

        sharedPreferences = getSharedPreferences("lmq", 0);

        if (!sharedPreferences.contains("fist_into")) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("fist_into", true);
            editor.commit();
        }

        MyLog.e("手机型号:", android.os.Build.MODEL);
        Default.PHONE_MODEL = android.os.Build.MODEL;
        Default.OS_VERSION = android.os.Build.VERSION.RELEASE + "";
        MyLog.e("手机型号:", "" + Default.PHONE_MODEL + "Android版本:" + Default.OS_VERSION);

        Default.dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(Default.dm);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                //判断网络是否可用
                if (NetWorkStatusBroadcastReceiver.netWorkAviable) {
                    //联网获取启动图
                     doHttpGetHeadImage();

                }else{
                    //没网状态下直接进入主界面
                    stop();
                }
            }
        }, 700);


        Default.isAlive = true;




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


    public void stop() {
        // time.cancel();
        // time.purge();

        Intent intent = null;
        if (sharedPreferences.getBoolean("fist_into", false)) {
            intent = new Intent(MainLogoActivity.this, LoadIntroduceActivity.class);
        } else {
            intent = new Intent(MainLogoActivity.this, MainTabActivity.class);
        }

        // Intent ;
        startActivity(intent);
        MainLogoActivity.this.finish();
        overridePendingTransition(R.anim.light_to_dark, R.anim.dark_to_light);


        finish();
    }


    /**
     * 获取启动图
     */
    public void doHttpGetHeadImage() {

        JsonBuilder builder = new JsonBuilder();

        builder.put("type", "3");//type=1请求的是积分商城头部图片，2是首页顶部图片,3是启动图
        BaseHttpClient.post(this, Default.bannerHead, builder, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();

                // showLoadingDialogNoCancle("请稍后努力加载中...");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                // TODO Auto-generated method stub
                super.onSuccess(statusCode, headers, json);
                Log.i("BBB", "jjjjj-head--------"+json.toString());
                //dismissLoadingDialog();

                if (statusCode == 200) {
                    try {
                        if (json.getInt("status") == 1) {
                            JSONObject jsonObject = json.getJSONObject("0");
                            initHeadData(jsonObject);
                            Log.i("BBB", "执行了-----------------");

                        } else {
                            // showCustomToast(json.getString("message"));
                        }

                        //dismissLoadingDialog();
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                }
            }



            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // TODO Auto-generated method stub
                super.onFailure(statusCode, headers, throwable, errorResponse);

                startPic.setBackgroundResource(R.mipmap.b_back);
                startThread();
            }
        });

    }

    private void initHeadData(JSONObject json) {
        // TODO Auto-generated method stub
        try {
            String url=Default.ip+json.getString("pic").replace("\\/", "/");
            Log.i("BBB", "TUPIANDIZHI-------------"+url);
            Picasso.with(MainLogoActivity.this).load(url).into(startPic,new Callback(){



                @Override
                public void onError() {
                    startPic.setBackgroundResource(R.mipmap.b_back);
                    startThread();

                }

                @Override
                public void onSuccess() {
                    // TODO Auto-generated method stub
                    //startThread();
                    mSendPhoneNum.setVisibility(View.VISIBLE);
                    mSendPhoneNum.setClickable(true);
                    time = new MainLogoActivity.TimeCount(6000, 1000);
                    time.start();


                }



            });
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void startThread(){
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                stop();
            }
        }, 4000);

    }


    /* 倒计时类 */
    class TimeCount extends CountDownTimer {
        //用对象timeCount.start()&timeCount.cancel()控制开始结束

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {
            //mSendPhoneNum.setText("即将进入");
            //mSendPhoneNum.setClickable(true);
            time.cancel();
            stop();
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            //mSendPhoneNum.setClickable(false);
            mSendPhoneNum.setText("跳过("+(millisUntilFinished / 1000)+ "秒)");

        }
    }


    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        time.cancel();
        stop();
    }


}
