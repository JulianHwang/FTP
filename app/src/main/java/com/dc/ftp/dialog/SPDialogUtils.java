package com.dc.ftp.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dc.ftp.R;
import com.dc.ftp.base.SPBaseActivity;
import com.dc.ftp.utils.PixelUtil;

/**
 * Created by Julian on 2017/11/28.
 */

public class SPDialogUtils {
  /*  public static void showToast(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }*/

    /** 显示自定义Toast提示(来自String) **/
    public static void showToast(Context context,String text) {
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.common_toast, null);
        ((TextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0, PixelUtil.dp2px(65));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastRoot);
        toast.show();
    }
}
