package com.dc.ftp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.dc.ftp.adapter.ViewPagerAdapter;
import com.dc.ftp.base.SPBaseActivity;


public class LoadIntroduceActivity extends SPBaseActivity implements OnClickListener, OnPageChangeListener {

	private ViewPager vp;
	private ViewPagerAdapter vpAdapter;
	private List<View> views;
	private SharedPreferences sharedPreferences;

	private static final int[] pics = { R.mipmap.load_intr_1, R.mipmap.load_intr_2, R.mipmap.load_intr_3};

	private int currentIndex;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load_introduce);

		sharedPreferences = getSharedPreferences("lmq", 0);
		views = new ArrayList<View>();

		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		for (int i = 0; i < pics.length; i++) {
			ImageView iv = new ImageView(this);
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setLayoutParams(mParams);
			iv.setImageResource(pics[i]);
			if (i == (pics.length-1)) {
				iv.setOnClickListener(this);
			}
			views.add(iv);
		}
		vp = (ViewPager) findViewById(R.id.viewpager);
		vpAdapter = new ViewPagerAdapter(views);
		vp.setAdapter(vpAdapter);
		vp.setOnPageChangeListener(this);


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

	/**
	 */
	private void setCurView(int position) {
		if (position < 0 || position >= pics.length) {
			return;
		}

		vp.setCurrentItem(position);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
	}

	@Override
	public void onClick(View v) {

		// showCustomToast("123");
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("fist_into", false);
		editor.commit();
		intoAPP();
	}

	public void intoAPP() {

		Intent	intent = new Intent(LoadIntroduceActivity.this, MainTabActivity.class);
		startActivity(intent);
		LoadIntroduceActivity.this.finish();
		overridePendingTransition(R.anim.light_to_dark, R.anim.dark_to_light);

		finish();

	}

}
