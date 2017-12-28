/**
 * shopmobile for tpshop
 * ============================================================================
 * 版权所有 2015-2099 深圳搜豹网络科技有限公司，并保留所有权利。
 * 网站地址: http://www.tp-shop.cn
 * ——————————————————————————————————————
 * 这不是一个自由软件！您只能在不用于商业目的的前提下对程序代码进行修改和使用 .
 * 不允许对程序代码以任何形式任何目的的再发布。
 * ============================================================================
 * Author: 飞龙  wangqh01292@163.com
 * Date: @date 2015年10月20日 下午7:52:58 
 * Description:Activity 基类
 * @version V1.0
 */
package com.dc.ftp.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dc.ftp.activity.user.SPLoginActivity;
import com.dc.ftp.dialog.MyAlertDialog;
import com.dc.ftp.dialog.SPDialogUtils;
import com.dc.ftp.R;
import com.dc.ftp.activity.web.SPWebViewActivity;
import com.dc.ftp.dialog.SPLoadingDialog;
import com.dc.ftp.global.Default;
import com.dc.ftp.utils.PixelUtil;

import org.json.JSONObject;


/**
 * @author Julian
 *
 */
public abstract class SPBaseActivity extends AppCompatActivity {

	private String TAG = "SPBaseActivity";
	public final int TITLE_HOME = 1;
	public final int TITLE_DEFAULT = 0;
	public final int TITLE_CATEGORY = 2;

	public JSONObject mDataJson;		//包含网络请求所有结果
	public SPLoadingDialog mLoadingDialog;

	private LinearLayout mBackBtn	;
	private TextView mTitleTxtv;



	
	/**
	 * activity初始化 //要在子类activity中的setContentView()之后调用
	 * 
	 */
	public void init(){


		//安卓4.4以上才能设置沉浸式布局（状态栏透明覆盖）
		/*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			//设置状态栏透明
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			//重新设置顶部tab栏高度（以适配顶部的状态栏）
			LinearLayout pageHead = (LinearLayout) findViewById(R.id.page_head);
			if(pageHead!=null){
				android.view.ViewGroup.LayoutParams params =  pageHead.getLayoutParams();
				params.height=(int)PixelUtil.dp2px(70);
				pageHead.setLayoutParams(params);
			}
		}*/

		mBackBtn = (LinearLayout)findViewById(R.id.back);
		mTitleTxtv = (TextView)findViewById(R.id.mtitle);
		initView();
		initData();
		initEvent();
	}


	/**
	 * 设置标题tab栏，子类activity的xml布局中要include page_head
	 *
	 */
	public void setHeadTitle(boolean backShow, String title){

	    //是否显示返回箭头
		if (backShow){
			mBackBtn.setVisibility(View.VISIBLE);
			mBackBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					SPBaseActivity.this.finish();
					overridePendingTransition(R.anim.left_to_right,R.anim.to_right);
				}
			});
		}else{
			if(mBackBtn!=null)mBackBtn.setVisibility(View.GONE);
		}

		if (title!=null)mTitleTxtv.setText(title);
	}


	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		Default.currentContext = this;

	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	public void bindClickListener(View v , View.OnClickListener listener){
		if(v != null && listener != null){
			v.setOnClickListener(listener);
		}

	}


	public void showToast(String msg){
		SPDialogUtils.showToast(this, msg);
	}

	public void showToast(int id) {
		SPDialogUtils.showToast(this, getResources().getString(id).toString());
	}







	public void showLoadingToast(){
		showLoadingToast(null);
	}

	public void showLoadingToast(String title){
		mLoadingDialog = new SPLoadingDialog(this , title);
		mLoadingDialog.setCanceledOnTouchOutside(false);
		mLoadingDialog.show();
	}

	public void hideLoadingToast(){
		if(mLoadingDialog !=null){
			mLoadingDialog.dismiss();
		}
	}


	public void showToastUnLogin(){
		showToast(getString(R.string.toast_person_unlogin));
	}

	public void  toLoginPage(){
		Intent loginIntent = new Intent(this , SPLoginActivity.class);
		startActivity(loginIntent);
	}


	/**
	 * 启动web Activity
	 * @param url
	 */
	public void startWebViewActivity(String url , String title){
		Intent shippingIntent = new Intent(this , SPWebViewActivity.class);
		shippingIntent.putExtra("url" ,url);
		shippingIntent.putExtra("title" , title);
		startActivity(shippingIntent);
	}





	/**
	 * 以下三个函数不需要再子类调用, 只需要在子类的
	 * onCrate()中调用:super.init()方法即可
	 * 基类函数,初始化界面
	 */
	abstract public void initView();
	
	/**
	 * 基类函数, 初始化数据
	 */
	abstract public void initData();
	
	/**
	 * 基类函数, 绑定事件
	 */
	abstract public void initEvent();

	/**
	 * 处理网络加载过的数据
	 */
	public void dealModel(){}




	protected void showAlert(String msg) {

		showMyDialog(true, msg, "确定", new MyAlertDialog.OnMyClickListener() {

			@Override
			public void onClick(MyAlertDialog dialog) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});


	}



	protected void showAlertThenFinish(String msg) {

		showMyDialog(false, msg, "确定", new MyAlertDialog.OnMyClickListener() {

			@Override
			public void onClick(MyAlertDialog dialog) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				finish();
			}
		});



	}


	protected void showConfirm(String msg,final Intent intent) {

		showMyDialog(true, msg, "确定", new MyAlertDialog.OnMyClickListener() {

			@Override
			public void onClick(MyAlertDialog dialog) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				startActivity(intent);
				//第一个参数是目标页进入动画，第二个参数是本页结束动画
				overridePendingTransition(R.anim.right_to_left, R.anim.to_left);
			}
		},"取消", new MyAlertDialog.OnMyClickListener() {

			@Override
			public void onClick(MyAlertDialog dialog) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});


	}





	//万能dialog--按钮数1-3
	protected void showMyDialog(boolean cancelable,String msg,String posBtnName,final MyAlertDialog.OnMyClickListener posListener) {

		final  MyAlertDialog dialog = new MyAlertDialog(this);
		dialog.setTitle(R.string.systemNotify);
		dialog.setMessage(msg);
		dialog.setCancelable(cancelable);
		if(posListener!=null)dialog.setPositiveButton(posBtnName,posListener);

	}



	protected void showMyDialog(boolean cancelable, String msg, String posBtnName, final MyAlertDialog.OnMyClickListener posListener, String negBtnName, final MyAlertDialog.OnMyClickListener negListener) {
		final  MyAlertDialog dialog = new MyAlertDialog(this);
		dialog.setTitle(R.string.systemNotify);
		dialog.setMessage(msg);
		dialog.setCancelable(cancelable);
		if(posListener!=null)dialog.setPositiveButton(posBtnName,posListener );//确定按钮
		if(negListener!=null)dialog.setNegativeButton(negBtnName,negListener);//取消按钮

	}


	protected void showMyDialog(boolean cancelable, String msg, String posBtnName, final MyAlertDialog.OnMyClickListener posListener, String centerBtnName, final MyAlertDialog.OnMyClickListener centerListener, String negBtnName, final MyAlertDialog.OnMyClickListener negListener) {
		final  MyAlertDialog dialog = new MyAlertDialog(this);
		dialog.setTitle(R.string.systemNotify);
		dialog.setMessage(msg);
		dialog.setCancelable(cancelable);
		if(posListener!=null)dialog.setPositiveButton(posBtnName,posListener);//确定按钮
		if(centerListener!=null)dialog.setNeutralButton(centerBtnName,centerListener);//中间按钮
		if(negListener!=null)dialog.setNegativeButton(negBtnName,negListener);//取消按钮

	}


	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.right_to_left,R.anim.to_left);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
		overridePendingTransition(R.anim.right_to_left,R.anim.to_left);
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.left_to_right,R.anim.to_right);
	}
}
