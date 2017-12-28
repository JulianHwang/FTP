package com.dc.ftp.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dc.ftp.R;


public class NewVersionDialog extends BaseDialog {

	public Button updateBtn, exitbtn;
	private View.OnClickListener mListener;
	public ProgressBar progressBar;
	// private EditText mEditText;
	private String downloadNewAppuUrl;
	public TextView showProgressView, versionTextView;
	private LinearLayout layout;

	private String path;
	public TextView versionInfo;

	public NewVersionDialog(Context context, String downloadNewAppuUrl) {
		super(context);
		super.setCanceledOnTouchOutside(false);
		super.setCancelable(false);
		init();
	}

	private void init() {
		setContentView(R.layout.newversion_dialog);
		updateBtn = (Button) findViewById(R.id.download_newapp);

		exitbtn = (Button) findViewById(R.id.newversion_exit);
		versionTextView = (TextView) findViewById(R.id.name);
		versionInfo = (TextView) findViewById(R.id.versionInfo);
		
		
		showProgressView = (TextView) findViewById(R.id.show_progress);
		progressBar = (ProgressBar) findViewById(R.id.content_view_progress);

		layout = (LinearLayout) findViewById(R.id.lay1);

	}

	public void setListener(View.OnClickListener listener) {
		mListener = listener;
		updateBtn.setOnClickListener(mListener);
		exitbtn.setOnClickListener(mListener);
	}

	@Override
	public void dismiss() {
		super.dismiss();
	}

	public void showProgress() {
		layout.setVisibility(View.VISIBLE);
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
