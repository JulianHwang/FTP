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
 * Date: @date 2015年10月20日 下午7:13:14 
 * Description:
 * @version V1.0
 */
package com.dc.ftp.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.dc.ftp.R;
import com.dc.ftp.activity.user.SPLoginActivity;
import com.dc.ftp.dialog.SPDialogUtils;
import com.dc.ftp.dialog.SPLoadingDialog;
import com.dc.ftp.dialog.MyAlertDialog;

import org.json.JSONObject;

/**
 * @author admin
 *
 */
public abstract class SPBaseFragment extends Fragment  {

	SPLoadingDialog mLoadingDialog;

	JSONObject mDataJson;
	/**
	 * 跳转登录界面
	 */
	public void gotoLogin(){
		
	}

	public void init(View view){
		initView(view);
		initData();
		initEvent();
	}
	
	/**
	 * 取消网络请求
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param obj    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void cancelRequest(Object obj){
		
	}

	public void showToast(String msg){
		SPDialogUtils.showToast(getActivity(), msg);
	}

	public void showToast(int id) {
		SPDialogUtils.showToast(getActivity(), getResources().getString(id).toString());
	}

	public void showToastUnLogin(){
		showToast(getString(R.string.toast_person_unlogin));
	}
	public void  toLoginPage(){
		Intent loginIntent = new Intent(getActivity() , SPLoginActivity.class);
		getActivity().startActivity(loginIntent);
	}

	public void showLoadingToast(){
		showLoadingToast(null);
	}

	public void showLoadingToast(String title){
		mLoadingDialog = new SPLoadingDialog(getActivity() , title);
		mLoadingDialog.setCanceledOnTouchOutside(false);
		mLoadingDialog.show();
	}

	public void hideLoadingToast(){
		if(mLoadingDialog !=null){
			mLoadingDialog.dismiss();
		}
	}





	protected void showAlert(String msg) {

		showMyDialog(true, msg, "确定", new MyAlertDialog.OnMyClickListener() {

			@Override
			public void onClick(MyAlertDialog dialog) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});


	}



	protected void showConfirm(String msg,final Intent intent) {

		showMyDialog(true, msg, "确定", new MyAlertDialog.OnMyClickListener() {

			@Override
			public void onClick(MyAlertDialog dialog) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				getActivity().startActivity(intent);
				//第一个参数是目标页进入动画，第二个参数是本页结束动画
				getActivity().overridePendingTransition(R.anim.right_to_left, R.anim.to_left);
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

		final  MyAlertDialog dialog = new MyAlertDialog(getActivity());
		dialog.setTitle(R.string.systemNotify);
		dialog.setMessage(msg);
		dialog.setCancelable(cancelable);
		if(posListener!=null)dialog.setPositiveButton(posBtnName,posListener);

	}



	protected void showMyDialog(boolean cancelable, String msg, String posBtnName, final MyAlertDialog.OnMyClickListener posListener, String negBtnName, final MyAlertDialog.OnMyClickListener negListener) {
		final  MyAlertDialog dialog = new MyAlertDialog(getActivity());
		dialog.setTitle(R.string.systemNotify);
		dialog.setMessage(msg);
		dialog.setCancelable(cancelable);
		if(posListener!=null)dialog.setPositiveButton(posBtnName,posListener );//确定按钮
		if(negListener!=null)dialog.setNegativeButton(negBtnName,negListener);//取消按钮

	}


	protected void showMyDialog(boolean cancelable, String msg, String posBtnName, final MyAlertDialog.OnMyClickListener posListener, String centerBtnName, final MyAlertDialog.OnMyClickListener centerListener, String negBtnName, final MyAlertDialog.OnMyClickListener negListener) {
		final  MyAlertDialog dialog = new MyAlertDialog(getActivity());
		dialog.setTitle(R.string.systemNotify);
		dialog.setMessage(msg);
		dialog.setCancelable(cancelable);
		if(posListener!=null)dialog.setPositiveButton(posBtnName,posListener);//确定按钮
		if(centerListener!=null)dialog.setNeutralButton(centerBtnName,centerListener);//中间按钮
		if(negListener!=null)dialog.setNegativeButton(negBtnName,negListener);//取消按钮

	}






	/**
	 * 
	* @Description: 初始化子类视图 
	* @param view    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public abstract void initView(View view);
	
	public abstract void initEvent();

	public abstract void initData();


	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		getActivity().overridePendingTransition(R.anim.right_to_left,R.anim.to_left);
	}




}
