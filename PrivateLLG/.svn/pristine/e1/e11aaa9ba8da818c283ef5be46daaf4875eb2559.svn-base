package com.llg.privateproject.view;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.llg.privateproject.AppContext;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class DialogUpload extends Dialog implements
		android.view.View.OnClickListener {
	private TextView tvCancel;
	private TextView tvConfirm;

	public DialogUpload(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_photo_upload);
		initUI();
	}

	private void initUI() {
		setCanceledOnTouchOutside(true);
		WindowManager.LayoutParams Params = getWindow().getAttributes();
		Params.width = (int) (AppContext.getScreenWidth() * 0.9);
		Params.height = (int) (AppContext.getScreenHeight() * 0.25);
		getWindow().setAttributes(Params);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		tvCancel = (TextView) findViewById(R.id.tv_cancel);
		tvConfirm = (TextView) findViewById(R.id.tv_confirm);
		tvCancel.setOnClickListener(this);
		tvConfirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		dismiss();
		switch (v.getId()) {
		case R.id.tv_cancel:
			break;
		case R.id.tv_confirm:
			break;

		default:
			break;
		}
	}
}
