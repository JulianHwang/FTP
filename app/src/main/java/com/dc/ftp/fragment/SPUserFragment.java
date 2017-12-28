package com.dc.ftp.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DialerFilter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.dc.ftp.R;
import com.dc.ftp.activity.user.SPLoginActivity;
import com.dc.ftp.base.SPBaseFragment;
import com.dc.ftp.dialog.MyAlertDialog;
import com.dc.ftp.global.Default;
import com.dc.ftp.http.BaseHttpClient;
import com.dc.ftp.http.JsonBuilder;
import com.dc.ftp.http.JsonHttpResponseHandler;
import com.dc.ftp.utils.MyLog;
import com.dc.ftp.utils.SystemApi;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Julian on 2017/11/28.
 */

public class SPUserFragment extends SPBaseFragment implements View.OnClickListener {
    private LinearLayout lly_unLoginView;
    private LinearLayout lly_loginedView;
    private TextView userName;
    private TextView userPhone;
    private LocationManager locationManager;

    //应用高德地图定位
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private TextView tvLocation;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);

        super.init(view);

        return view;


    }

    @Override
    public void initView(View view) {
        lly_unLoginView = (LinearLayout) view.findViewById(R.id.lly_unLoginView);
        lly_loginedView = (LinearLayout) view.findViewById(R.id.lly_loginedView);
        view.findViewById(R.id.tv_login).setOnClickListener(this);
        view.findViewById(R.id.tv_logout).setOnClickListener(this);
        userName = (TextView) view.findViewById(R.id.user_name);
        userPhone = (TextView) view.findViewById(R.id.user_phone);

        view.findViewById(R.id.rly_share_logined).setOnClickListener(this);
        view.findViewById(R.id.rly_location_logined).setOnClickListener(this);
         tvLocation = (TextView) view.findViewById(R.id.tv_location);


    }

    @Override
    public void initData() {
        initAmap();
    }

    @Override
    public void initEvent() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                startActivity(new Intent(getActivity(), SPLoginActivity.class));
                break;
            case R.id.tv_logout:
                showExitDialog();
                break;
            case R.id.rly_share_logined:
                SystemApi.showShareView(getActivity().getApplicationContext(), "德成贷理财APP，随时随地掌握你的财富", "手机移动理财的指尖神器，帮您在“拇指时代”指点钱途，“掌握财富”。",
                        "https://www.dechengdai.com/m/pub/denglu.html");
                getActivity().overridePendingTransition(R.anim.pop_enter_anim, R.anim.light_to_dark);
                break;
            case R.id.rly_location_logined:
                //startLocate();
                tvLocation.setText("定位中...");
                initAmap();//初始化高德地图，执行定位

                break;
            default:
                break;
        }
    }


    private void showExitDialog() {
        showMyDialog(true, "是否退出当前账户？", "确定", new MyAlertDialog.OnMyClickListener() {
            @Override
            public void onClick(MyAlertDialog dialog) {
                dialog.dismiss();
                doHttpLogout();
            }

        }, "取消", new MyAlertDialog.OnMyClickListener() {
            @Override
            public void onClick(MyAlertDialog dialog) {
                dialog.dismiss();
            }
        });
    }


    private void doHttpLogout() {

        BaseHttpClient.post(getActivity(), Default.exit, null, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                showLoadingToast("正在注销...");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                hideLoadingToast();
                MyLog.i("DDD", "loginSuccess-----" + response.toString());

                if (statusCode == 200) {
                    try {
                        if (response.getInt("status") == 1) {
                            lly_loginedView.setVisibility(View.GONE);
                            lly_unLoginView.setVisibility(View.VISIBLE);
                            Default.userId = 0;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                } else {
                    showToast(R.string.link_out_of_time);
                }

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });


    }


    //初始化高德地图，执行定位
    private void initAmap() {
        //声明定位回调监听器
        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        //mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if(null != mLocationClient){
            mLocationClient.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
        /*//设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
          mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);*/
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
          mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);

        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
        // 如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);

        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption.setMockEnable(false);

        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);

        //关闭定位缓存机制
        mLocationOption.setLocationCacheEnable(false);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }


    public AMapLocationListener mLocationListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。

                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    amapLocation.getLatitude();//获取纬度
                    amapLocation.getLongitude();//获取经度
                    amapLocation.getAccuracy();//获取精度信息
                    amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    amapLocation.getCountry();//国家信息
                    String province = amapLocation.getProvince();//省信息
                    String city = amapLocation.getCity();//城市信息
                    amapLocation.getDistrict();//城区信息
                    amapLocation.getStreet();//街道信息
                    amapLocation.getStreetNum();//街道门牌号信息
                    amapLocation.getCityCode();//城市编码
                    amapLocation.getAdCode();//地区编码

                    //showToast(province + city);
                    tvLocation.setText(province + city);

             //获取定位时间

                } else {
                    //高德地图sdk定位需要申请key，填写打包信息(SHA1)，未打包的app无法定位
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    tvLocation.setText("无法获取到位置信息");

                   /* showToast("定位失败"+"location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());*/

                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }



    };


    //该方法采用的是系统的定位，在低版本手机上可能无法定位
    public void startLocate() {
        MyLog.i("DDD","startLocate()");
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
       // LocationProvider gpsProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);//1.通过GPS定位，较精确，也比较耗电
        LocationProvider netProvider = locationManager.getProvider(LocationManager.NETWORK_PROVIDER);
        MyLog.i("DDD","locationManager=null?"+locationManager==null?"null":"not null");

        if (netProvider != null&&locationManager.isProviderEnabled((LocationManager.NETWORK_PROVIDER))) {
            MyLog.i("DDD","if...");

               /*
            * 进行定位
                * provider:用于定位的locationProvider字符串:LocationManager.NETWORK_PROVIDER/LocationManager.GPS_PROVIDER
            * minTime:时间更新间隔，单位：ms
                * minDistance:位置刷新距离，单位：m
            * listener:用于定位更新的监听者locationListener
            *
            */
            String provider = LocationManager.NETWORK_PROVIDER;
            long minTime = 10000;
            long minDistance = 500;

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            locationManager.requestLocationUpdates(provider, minTime, minDistance, locationListener);
            MyLog.i("DDD","startlocate...");
        } else {
            //无法定位：1、提示用户打开定位服务；2、跳转到设置界面
            showToast("无法定位，请打开系统无线网络定位服务");
            Intent i = new Intent();
            i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(i);
        }
    }



    LocationListener locationListener =  new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {
            MyLog.i("DDD","onStatusChanged");
        }

        @Override
        public void onProviderEnabled(String provider) {
            MyLog.i("DDD","onProviderEnabled");
        }

        @Override
        public void onProviderDisabled(String provider) {
            MyLog.i("DDD","onProviderDisabled");
        }

        @Override
        public void onLocationChanged(Location location) {
            //如果位置发生变化,重新显示
            //得到纬度
            double latitude = location.getLatitude();
            //得到经度
            double longitude = location.getLongitude();

           MyLog.i("DDD","定位经纬度x="+latitude+" y="+longitude);

            if(latitude<=0||longitude<=0){
                return;
            }

            String address = SystemApi.decodeYX(latitude, longitude);
            //showToast(address);

        }
    };



    @Override
    public void onResume() {
        super.onResume();
        if(Default.userId==0){
            lly_unLoginView.setVisibility(View.VISIBLE);
            lly_loginedView.setVisibility(View.GONE);
        }else{
            lly_unLoginView.setVisibility(View.GONE);
            lly_loginedView.setVisibility(View.VISIBLE);
            userName.setText(Default.userName);
            userPhone.setText(Default.phone);

        }
    }
}
