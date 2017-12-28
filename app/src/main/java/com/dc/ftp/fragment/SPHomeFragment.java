package com.dc.ftp.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dc.ftp.R;
import com.dc.ftp.activity.home.ShowBannerViewDetail;
import com.dc.ftp.base.SPBaseFragment;
import com.dc.ftp.bean.BannerItem;
import com.dc.ftp.dialog.MyAlertDialog;
import com.dc.ftp.global.Default;
import com.dc.ftp.http.BaseHttpClient;
import com.dc.ftp.http.JsonBuilder;
import com.dc.ftp.http.JsonHttpResponseHandler;
import com.dc.ftp.utils.MyLog;
import com.dc.ftp.view.address_popwindow.PopHelper;
import com.dc.ftp.view.mzbanner.MZBannerView;
import com.dc.ftp.view.mzbanner.holder.MZHolderCreator;
import com.dc.ftp.view.mzbanner.holder.MZViewHolder;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julian on 2017/11/28.
 */

public class SPHomeFragment extends SPBaseFragment implements View.OnClickListener{

    private View parentView;
    private MZBannerView mMZBannerView;
    private List<BannerItem> bannerItems;
    private SwipeRefreshLayout swipeRefreshLayout;
    Button button1;
    Button button2;
    private TextView textView1;
    private TextView textView2;
    private PopHelper popHelper;
    private boolean flag;//用于pop标记显示,避免多个地方显示却只填充一个textview的情况
    View.OnClickListener  onClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         parentView = inflater.inflate(R.layout.fragment_home, container, false);


        super.init(parentView);

        return parentView;


    }


    @Override
    public void initView(View view) {
        innitBannerView();
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(255,0,0),Color.rgb(0,255,0),Color.rgb(0,0,255), Color.rgb(120,120,0));


        textView1 = (TextView) view.findViewById(R.id.textView1);
        textView2 = (TextView) view.findViewById(R.id.textView2);
        button1 = (Button) view.findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button2 = (Button) view.findViewById(R.id.button2);
        button2.setOnClickListener(this);




    }

    @Override
    public void initData() {
        doHttpGetHeadPic();
    }

    @Override
    public void initEvent() {
        //设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

              doHttpGetHeadPic();

            }
        });

        /**
         * 实现popHelper中的item接口
         */
          onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_pop_address_cencel:
                        popHelper.colsePopupwindow();
                        break;
                    case R.id.btn_pop_address_enter:
                        String addressData = popHelper.getAddressData();
                        if (flag) {
                            textView1.setText(addressData);
                        } else {
                            textView2.setText(addressData);
                        }
                        popHelper.colsePopupwindow();
                        break;
                }
            }
        };





    }


    private void innitBannerView() {
        // TODO Auto-generated method stub
        //----------testMZBannerView--------------
        mMZBannerView = (MZBannerView) parentView.findViewById(R.id.my_banner);
        //footpointview = (LinearLayout) parentView.findViewById(R.id.footer_point);//轮播图下面的小圆点

        mMZBannerView.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {

            @Override
            public void onPageClick(View view, int position) {
                // TODO Auto-generated method stub
                Intent toDetailIntent = new Intent(getActivity(), ShowBannerViewDetail.class);
                toDetailIntent.putExtra("content", bannerItems.get(position).getContent());
                if((bannerItems.get(position).getContent()).indexOf("http")!=-1){
                    startActivity(toDetailIntent);
                }else {
                    showToast("暂无内容");
                }
            }
        });


        //-------------testEnd-----------
    }


    /**
     * 获取幻灯片
     */
    public void doHttpGetHeadPic() {

        JsonBuilder builder = new JsonBuilder();

        BaseHttpClient.post(getActivity(), Default.bannerPic, builder, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();

                showLoadingToast("努力加载中...");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                // TODO Auto-generated method stub
                super.onSuccess(statusCode, headers, json);
                MyLog.i("BBB", "首页轮播图json--------"+json.toString());
                hideLoadingToast();
                if(swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                MyLog.e("statusCode=" + statusCode + "--headers=" + headers.toString() + "--json=" + json.toString());

                if (statusCode == 200) {
                    try {
                        if (json.getInt("status") == 1) {
                            initBannerData(json);
                            setBanner(bannerItems);

                        } else {
                            // showCustomToast(json.getString("message"));
                        }


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

                hideLoadingToast();
            }
        });


    }

    //解析banana数据--------
    public void initBannerData(JSONObject json) {

        if (json != null) {
            //views.clear();
            //footPointViews.clear();
           // footpointview.removeAllViews();
            bannerItems = new ArrayList<BannerItem>();
            try {
                JSONArray array = json.getJSONArray("list");
                for (int i = 0; i < array.length(); i++) {
                    bannerItems.add(new BannerItem(array.getJSONObject(i)));

                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void setBanner(List<BannerItem> item){
        mMZBannerView.setPages(item, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });

        mMZBannerView.start();

    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.button1:
                popHelper = new PopHelper(getActivity().getApplicationContext());
                popHelper.showAddressPop(v, getActivity(), onClickListener);
                flag = true;
                break;
            case R.id.button2:
               /* popHelper = new PopHelper(getActivity().getApplicationContext());
                popHelper.showAddressPop(v, getActivity(), onClickListener);
                flag = false;*/
                showMyDialog(true, "点击了我", "确定", new MyAlertDialog.OnMyClickListener() {
                    @Override
                    public void onClick(MyAlertDialog dialog) {
                        dialog.dismiss();
                        popHelper = new PopHelper(getActivity().getApplicationContext());
                        popHelper.showAddressPop(v, getActivity(), onClickListener);
                        flag = false;
                    }
                });
                break;
        }
    }

    public static class BannerViewHolder implements MZViewHolder<BannerItem> {
        private ImageView mImageView;
        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.remote_banner_item,null);
            mImageView = (ImageView) view.findViewById(R.id.remote_item_image);
            return view;
        }

        @Override
        public void onBind(Context context,  final int i, BannerItem item) {
            MyLog.e("zhouwei","current position:"+i);
            Picasso.with(context).load(item.getPicPath()).into(mImageView);
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        mMZBannerView.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMZBannerView.pause();//暂停轮播
    }


}
