package com.dc.ftp.global;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Administrator on 2017/11/29.
 */

public class Default {


    /**
     * 静态变量
     */
    public static long userId = 0;
    public static String userName = "游客";
    public static String phone = "";
    public static final String userPassword = "password";
    public static final String userRemember = "remember";

    public static final String userPreferences = "ftp";

    public static String ip = "https://www.dechengdai.com";
    public static Context currentContext;
    public static boolean showLog = true;//是否显示log日志

    public static boolean isActive=true;
    public static boolean isAlive=true;
    //当前app版本
    public static String curVersion="1.0";

    //当前手机Android系统版本
    public static String OS_VERSION="";
    //当前手机型号
     public static String PHONE_MODEL="";
     //DisplayMetrics 屏幕分辨率
     public static DisplayMetrics dm;

    /**
     * end-----------------------
     */

    public static final int RESULT_REGISTER_TO_LOGIN = 100;



    /**
     * 接口-----------------------
     */

      public static final String version = "/Main/version";

   //type=1请求的是积分商城头部图片，2是首页顶部图片,3是启动图
    public static final String bannerHead = "/main/advertlist";
    //首页轮播图
    public static final String bannerPic = "/main/bnlist";
  //轮播图点击详情页
    public static final String bannerPicDetail = "/main/bnedit";
    //登录
    public static final String login = "/member/Mobilecommon/actlogin";
    //退出登录
    public static final String exit = "/Member/Mobilecommon/mactlogout";

    public static final String FORGOT_PWD_0 = "/member/common/phtous";// 判断用户名和手机号是否匹配
    public static final String FORGOT_PWD_1 = "/main/getpassword";// 获取手机验证码
    public static final String FORGOT_PWD_3 = "/main/repreatphone";// 修改密码

    public static final String registerPhone = "/member/Mobilecommon/commitphone";
    public static final String register = "/member/Mobilecommon/regaction";

    public static final String GET_REGISTER_CONTEXT = "/api/ruleserver";// 注册协议

    /**
     * end-----------------------
     */


}
