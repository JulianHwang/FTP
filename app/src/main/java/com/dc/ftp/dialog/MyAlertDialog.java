package com.dc.ftp.dialog;

import com.dc.ftp.R;
import com.dc.ftp.utils.PixelUtil;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MyAlertDialog {
	Context context;
	Dialog ad;
	TextView titleView;
	TextView messageView;
	LinearLayout buttonLayout;

	public MyAlertDialog(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		//ad = new android.app.AlertDialog.Builder(context).create();
		ad = new Dialog(context, R.style.NobackDialog);
		ad.show();
		// 关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
		Window window = ad.getWindow();
		window.setContentView(R.layout.zdydialog);
		
		titleView = (TextView) window.findViewById(R.id.title);
		messageView = (TextView) window.findViewById(R.id.message);
		buttonLayout = (LinearLayout) window.findViewById(R.id.buttonLayout);
	}

	public void setTitle(int resId) {
		titleView.setText(resId);
	}

	public void setTitle(String title) {
		titleView.setText(title);
	}

	public void setMessage(int resId) {
		messageView.setText(resId);
	}

	public void setMessage(String message) {
		messageView.setText(message);
	}
	
	public void setCancelable(boolean cancelable) {
		ad.setCancelable(cancelable);
	}

	/**
	 * 设置按钮
	 * 
	 * @param text
	 * @param listener
	 */
	
	//自定义dialog接口
	public interface OnMyClickListener{
		public void onClick(MyAlertDialog dialog);
	}
	
	
	public void setPositiveButton(String text, final OnMyClickListener listener) {
		TextView tv = new TextView(context);
		
		int padding = PixelUtil.dp2px(12);
		int margin = PixelUtil.dp2px(7);
		int margin2 = PixelUtil.dp2px(9);
		
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.width=0;
		params.weight=1;
		tv.setLayoutParams(params);
		tv.setBackgroundResource(R.drawable.dialog_button_selector);
		tv.setPadding(margin, margin2, margin, margin2);
		tv.setText(text);
		tv.setGravity(Gravity.CENTER);
		tv.setTextColor(Color.WHITE);
		tv.setTextSize(PixelUtil.sp2px(5));
		tv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				listener.onClick(MyAlertDialog.this);
			}
		});
		buttonLayout.addView(tv);
	}
	
	
	
	
	/**
	 * 设置中间按钮
	 * 
	 * @param text
	 * @param listener
	 */
	public void setNeutralButton(String text, final OnMyClickListener listener) {
		TextView tv2 = new TextView(context);
		
		int padding = PixelUtil.dp2px(12);
		int margin = PixelUtil.dp2px(7);
		int margin2 = PixelUtil.dp2px(9);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.width=0;
		params.weight=1;
		tv2.setLayoutParams(params);
		tv2.setGravity(Gravity.CENTER);
		tv2.setPadding(margin, margin2, margin, margin2);
		tv2.setBackgroundResource(R.drawable.dialog_button_selector);
		tv2.setText(text);
		tv2.setTextColor(Color.WHITE);
		tv2.setTextSize(PixelUtil.sp2px(5));
		tv2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				listener.onClick(MyAlertDialog.this);	
			}
		});
		
		if (buttonLayout.getChildCount() > 0) {
			params.setMargins(margin, 0, 0, 0);
			tv2.setLayoutParams(params);
			buttonLayout.addView(tv2, 1);
		} else {
			tv2.setLayoutParams(params);
			buttonLayout.addView(tv2);
		}
	}

	
	
	
	
	
	

	/**
	 * 设置按钮
	 * 
	 * @param text
	 * @param listener
	 */
	
	public void setNegativeButton(String text, final OnMyClickListener listener) {
		TextView tv2 = new TextView(context);
		
		int padding = PixelUtil.dp2px(12);
		int margin = PixelUtil.dp2px(7);
		int margin2 = PixelUtil.dp2px(9);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.width=0;
		params.weight=1;
		tv2.setLayoutParams(params);
		tv2.setGravity(Gravity.CENTER);
		tv2.setPadding(margin, margin2, margin, margin2);
		tv2.setBackgroundResource(R.drawable.dialog_button_gray_selector);
		tv2.setText(text);
		tv2.setTextColor(Color.WHITE);
		tv2.setTextSize(PixelUtil.sp2px(5));
		tv2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				listener.onClick(MyAlertDialog.this);
			}
		});
		
		if (buttonLayout.getChildCount() ==1) {
			params.setMargins(padding, 0, 0, 0);
			tv2.setLayoutParams(params);
			buttonLayout.addView(tv2, 1);
		} else if(buttonLayout.getChildCount()==2){
			params.setMargins(margin, 0, 0, 0);
			tv2.setLayoutParams(params);
			buttonLayout.addView(tv2, 2);
		}else{		
			tv2.setLayoutParams(params);
			buttonLayout.addView(tv2);
		}
	}

	/**
	 * 关闭对话框
	 */
	public void dismiss() {
		ad.dismiss();
	}
}
