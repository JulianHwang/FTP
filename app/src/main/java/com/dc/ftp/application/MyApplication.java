package com.dc.ftp.application;

import android.app.Application;
import com.mob.MobApplication;


/**
 * Created by Administrator on 2017/11/27.
 */

public class MyApplication extends MobApplication {
    private static MyApplication mInstance;

    public static MyApplication getInstance(){
        return mInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
       /* 这里不需要再用代码配置appKey和appSecret了，因为在mainfest文件中已经在继承了MobApplication的
         MyApplication节点下
         配置了<meta-data android:name="Mob-AppKey" android:value="2335fc9df62dc"/>
         和<meta-data android:name="Mob-AppSecret" android:value="4e80ea7d68a006be5fbd3b88c71b75f0"/>
         了 。两种配置方式择一即可，推荐在mainfest中配置。*/
        //MobSDK.init(mInstance,"2335fc9df62dc","4e80ea7d68a006be5fbd3b88c71b75f0");


    }



}
