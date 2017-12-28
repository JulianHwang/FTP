package com.dc.ftp;

import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.dc.ftp.base.SPBaseActivity;
import com.dc.ftp.broadcast.NetWorkStatusBroadcastReceiver;
import com.dc.ftp.dialog.NewVersionDialog;
import com.dc.ftp.fragment.SPHomeFragment;
import com.dc.ftp.fragment.SPInfoFragment;
import com.dc.ftp.fragment.SPLoanFragment;
import com.dc.ftp.fragment.SPUserFragment;
import com.dc.ftp.global.Default;
import com.dc.ftp.http.BaseHttpClient;
import com.dc.ftp.http.JsonBuilder;
import com.dc.ftp.http.JsonHttpResponseHandler;
import com.dc.ftp.utils.MyLog;
import com.dc.ftp.utils.PixelUtil;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainTabActivity extends SPBaseActivity {
	public static MainTabActivity mainTabActivity;
	private RadioGroup radioGroup;
	private RadioButton oneButton, twoButton, threeButton, fourButton;
	/**
	 */
	private int currentIndex = 1;
	/***
	 */
	private int lastIndex = 1;

	private boolean changeToHomeFlag = false;
	private int homeType = 0;

	LinearLayout myuserframe_sx;
	private boolean  first =true;

	private SPHomeFragment homeFragment;
	private SPLoanFragment loanFragment;
	private SPInfoFragment infoFragment;
	private SPUserFragment userFragment;
	private List<Fragment>  fragList;
	private Drawable myImage1;
	private Drawable myImage2;
	private Drawable myImage3;
	private Drawable myImage4;
	private NetWorkStatusBroadcastReceiver netWorkStatusBroadcastReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_tab);
		super.init();

			 
		mainTabActivity = this;
		myuserframe_sx = (LinearLayout) findViewById(R.id.myuserframe_sx);

		oneButton = (RadioButton) findViewById(R.id.tab_one);
		twoButton = (RadioButton) findViewById(R.id.tab_two);
		threeButton = (RadioButton) findViewById(R.id.tab_three);
		fourButton = (RadioButton) findViewById(R.id.tab_four);




		  Resources res = this.getResources();

			myImage1 = res.getDrawable(R.drawable.tab_item_home_selector);
			myImage2 = res.getDrawable(R.drawable.tab_item_category_selector);
			myImage3 = res.getDrawable(R.drawable.tab_item_shopcart_selector);
			myImage4 = res.getDrawable(R.drawable.tab_item_mine_selector);

		
		
		
		
		//设置底部tab四个按钮图片大小
		int width=PixelUtil.dp2px(23);//因为不同手机分辨率不同，所以要尽量以dp做单位标准，否则图片在分辨率低的手机上会显得很大。
		

		myImage1.setBounds(0, 0, width, width);//(x,y,width,height)搜索
		oneButton.setCompoundDrawables(null, myImage1, null, null);

		myImage2.setBounds(0, 0, width, width);//(x,y,width,height)搜索
		twoButton.setCompoundDrawables(null, myImage2, null, null);

		myImage3.setBounds(0, 0, width, width);//(x,y,width,height)搜索
		threeButton.setCompoundDrawables(null, myImage3, null, null);

		myImage4.setBounds(0, 0, width, width);//(x,y,width,height)搜索
		fourButton.setCompoundDrawables(null, myImage4, null, null);




		fragList = new ArrayList<Fragment>();
		homeFragment = new SPHomeFragment();
		loanFragment = new SPLoanFragment();
		infoFragment = new SPInfoFragment();
		userFragment = new SPUserFragment();
		fragList.add(homeFragment);
		fragList.add(loanFragment);
		fragList.add(infoFragment);
		fragList.add(userFragment);
		



		netWorkStatusBroadcastReceiver = new NetWorkStatusBroadcastReceiver();
		final IntentFilter filter = new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(netWorkStatusBroadcastReceiver, filter);


		/*getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragList.get(0)).commit();

		oneButton.setChecked(true);*/

		radioGroup = (RadioGroup) findViewById(R.id.rg_tab);


		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				/**
				 */

				int selectedId = 1;
				switch (checkedId) {
				case R.id.tab_one://首页
					setHeadTitle(false,"首页");
					selectedId = 1;
					break;
				case R.id.tab_two://借款
					setHeadTitle(false,"借款");
					selectedId = 2;
					break;
				case R.id.tab_three://相关
					setHeadTitle(false,"相关");
					selectedId = 3;
					break;
				case R.id.tab_four://账户
					setHeadTitle(false,"账户");
					selectedId = 4;
					break;


				default:
					break;
				}
				lastIndex = currentIndex;
				currentIndex = selectedId;
				switchFragment(currentIndex);
				


			}
		});

           //初始化home首页
            ClickButton(1);

		getVersion();

		checkNewVersion();

		
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


	public void switchFragment(int newIndex){
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

		//第一次进入加载homeFragment
		if(first&&lastIndex==1&&newIndex==1){
			transaction.add(R.id.content_frame,fragList.get(0));
			transaction.commitAllowingStateLoss();
			first=false;
			return;
		}


		if(fragList.get(newIndex-1)!=null&&fragList.get(lastIndex-1)!=null){
			if(fragList.get(newIndex-1).isAdded()){
			transaction.hide(fragList.get(lastIndex-1)).show(fragList.get(newIndex-1));
			}else{
				transaction.hide(fragList.get(lastIndex-1)).add(R.id.content_frame, fragList.get(newIndex-1));
			}
			transaction.commitAllowingStateLoss();
			
			
		}
		
	}
	
	

	



	/**
	 * 跳转到某一页
	 */
	private void ClickButton(int index) {
		switch (index) {
		case 1:
			oneButton.setChecked(true);
			//setHeadTitle(false,"首页");


			break;
		case 2:
			twoButton.setChecked(true);
			//setHeadTitle(false,"借款");

			break;
		case 3:
			threeButton.setChecked(true);
			//setHeadTitle(false,"相关");

			break;
		case 4:
			fourButton.setChecked(true);
			//setHeadTitle(false,"账户");

			break;


		default:
			break;
		}


	}




	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		// showCustomToast("ON STOP ");
		super.onStop();
		if (!isAppOnForeground()) {
			// app 进入后台

			// 全局变量isActive = false 记录当前已经进入后台
			Default.isActive = false;

		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		// showCustomToast("ON RESUME ");
		super.onResume();

		if (!Default.isActive) {
			// app 从后台唤醒，进入前台
			Default.isActive = true;
		}

		
	}

	
	
	
	

	public boolean isAppOnForeground() {
		// Returns a list     of application processes that are running on the
		// device

		android.app.ActivityManager activityManager = (android.app.ActivityManager) getApplicationContext()
				.getSystemService(Context.ACTIVITY_SERVICE);
		String packageName = getApplicationContext().getPackageName();

		List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		if (appProcesses == null) {
			return false;
		}

		for (RunningAppProcessInfo appProcess : appProcesses) {
			// The name of the process that this object is associated with.
			if (appProcess.processName.equals(packageName)
					&& appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return true;
			}
		}

		return false;
	}



	private long lastTime;
	private int closeNum;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
				closeNum++;
				if (System.currentTimeMillis() - lastTime > 5000) {
					closeNum = 0;
				}
				lastTime = System.currentTimeMillis();
				if (closeNum == 1) {
					finish();
					doHttp();
				} else {

					showToast(R.string.toast_exit_confirm);
				}
			}
			return false;
		}

		return super.onKeyDown(keyCode, event);
	}

	private void doHttp() {

		BaseHttpClient.post(getApplicationContext(), Default.exit, null, new JsonHttpResponseHandler() {



			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();

			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);

				try {

					if (statusCode == 200) {
						if (response.getInt("status") == 1) {
							MyLog.d("zzx", "exit成功");
							System.exit(0);

						} else {
							MyLog.d("zzx", "exit失败");
						}
					} else {
						MyLog.d("zzx", "exit失败");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, responseString, throwable);
			}

		});

	}

	private NewVersionDialog dialog;
	private String downloadURL = "";

	public void checkNewVersion() {

		dialog = new NewVersionDialog(MainTabActivity.this, "");
		
		dialog.setListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				switch (v.getId()) {
				// 下载更新
				case R.id.download_newapp:

					dialog.updateBtn.setEnabled(false);

//					dialog.exitbtn.setEnabled(false);
					dialog.showProgress();
					downloadNewVersionApp(dialog.getPath());
					//测试版本更新功能----------------------------------------
               

					break;
				//不更新强制退出app
				case R.id.newversion_exit:
					//doHttp();
					//finish();
					dialog.dismiss();
					break;

				default:
					break;
				}

			}

		});
		
		//dialog.show();// 测试版本更新功能----------------------------

		JsonBuilder jsonBuilder = new JsonBuilder();

		jsonBuilder.put("version", Default.curVersion);

		BaseHttpClient.post(getBaseContext(), Default.version, jsonBuilder, new JsonHttpResponseHandler() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();

			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Log.e("onSuccess", Default.curVersion + ";" + statusCode + response.toString());
				Log.i("HHH", "获取版本信息------json----"+response.toString());
				try {

					if (statusCode == 200) {
						MyLog.e(response.toString());
						if (response.getInt("status") == 1) {
							
						} else if (response.getInt("status") == 0) {

						String temp = dialog.versionTextView.getText().toString();
						dialog.versionTextView.setText(temp + "(" + response.getString("version") + ")");				
						dialog.versionInfo.setText(response.getString("up_ver_info").replace("。","\n\n"));						
						dialog.show();
						dialog.setPath(response.getString("path").replace("\\/", ""));
							

							Log.i("HH","onSuccess++++"+response.getString("path").replace("\\/", ""));
						} else {

						}
					} else {
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, responseString, throwable);
			}

		});

	}

	public void downloadNewVersionApp(String download_url) {
		Log.i("HHH", "--------运行到downloadNewVersionApp()这里了,安装包下载地址---"+download_url);
		String filePathStr = Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp";
		
		File file = new File(filePathStr);
		if(!file.exists()){
		  file.mkdirs();
		 }
		Log.i("HH", "--------运行到这里了文件存在否？true表示存在-----"+file.exists());
		File saveFile = null;
		try {
			saveFile = File.createTempFile("newVersion", ".apk", file);
			Log.i("HH", "saveFile!=null?----"+(saveFile!=null));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
          if(saveFile==null){
			
		    dialog.dismiss();
			Toast.makeText(MainTabActivity.this, "更新遇到问题，请到腾讯应用宝或官网下载最新版安装", Toast.LENGTH_LONG).show();	
		    return;
		}

		BaseHttpClient.downloadFile(MainTabActivity.this, download_url, new FileAsyncHttpResponseHandler(saveFile) {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				Log.i("HHH", "onStart----------->" + "onStart");

			}

			@Override
			public void onProgress(int bytesWritten, int totalSize) {
				// TODO Auto-generated method stub
				super.onProgress(bytesWritten, totalSize);

				int num = getPercent(bytesWritten, totalSize);
				dialog.progressBar.setProgress(num);
				dialog.showProgressView.setText(num + "%");

			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, File file) {
				// TODO Auto-generated method stub

				MyLog.e("SUCCESS");
				BaseHttpClient.httpClient.setTimeout(3 * 1000);
				dialog.updateBtn.setText("安裝");
				dialog.updateBtn.setTag(101);

				MyLog.e(file.getPath());
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(new File(file.getPath())),
						"application/vnd.android.package-archive");
				startActivity(intent);
				finish();

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, Throwable arg2, File arg3) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				Toast.makeText(MainTabActivity.this, "网络出错！请稍后再试，或者到腾讯应用宝或官网下载最新版安装", Toast.LENGTH_LONG).show();	
			}
		});

	}

	public int getPercent(int x, int total) {
		int result = 0;// 接受百分比的值
		double x_double = Double.parseDouble(x + "");
		int tempresult = (int) (x / Double.parseDouble(total + "") * 100);

		return tempresult;
	}

	public void getVersion() {
		try {
			Default.curVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;// curVersioncode
			Log.i("BBB", "当前版本名称---------"+Default.curVersion);
			// Default.curVersioncode =
			// getPackageManager().getPackageInfo(getPackageName(),
			// 0).versionCode;// curVersioncode
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(netWorkStatusBroadcastReceiver); // 取消监听
		
		super.onDestroy();
	}



	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// sm.toggle(true);

			return false;
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		Default.isAlive = false;
	}

		


}
