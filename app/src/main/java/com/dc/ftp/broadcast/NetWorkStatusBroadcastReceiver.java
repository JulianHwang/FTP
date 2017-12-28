package com.dc.ftp.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.widget.Toast;

import com.dc.ftp.utils.MyLog;


/**
 * 网络状态广播
 *
 * @author zhaoshuai
 */
public class NetWorkStatusBroadcastReceiver extends BroadcastReceiver {

	private static final String TAG = "NetWorkStatusBroadcastReceiver";

	public static boolean netWorkAviable = true;

	public static boolean wifi = false;

	@Override
	public void onReceive(Context context, Intent intent) {
		MyLog.e(TAG, "网络状态改变");

		// 获得网络连接服务
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
		if (null != networkInfo) {
			if (networkInfo.isAvailable()) {
				State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState(); // 获取网络连接状态
				if (State.CONNECTED == state) { // 判断是否正在使用WIFI网络
					netWorkAviable = true;
					wifi = true;
					// Toast.makeText(context, "Wifi",
					// Toast.LENGTH_SHORT).show();
				}

				state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState(); // 获取网络连接状态
				if (State.CONNECTED == state) { // 判断是否正在使用GPRS网络
					netWorkAviable = true;
					wifi = false;
					// Toast.makeText(context, "GPRS",
					// Toast.LENGTH_SHORT).show();
				}

			} else {
				netWorkAviable = false;
				Toast.makeText(context, "您的网络连接已中断", Toast.LENGTH_LONG).show();
			}
		} else {
			netWorkAviable = false;
			Toast.makeText(context, "您的网络连接已中断,请检查你的网络设置", Toast.LENGTH_LONG).show();
		}

	}
}
