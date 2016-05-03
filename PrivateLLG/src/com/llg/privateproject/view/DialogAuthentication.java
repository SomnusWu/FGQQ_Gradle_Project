package com.llg.privateproject.view;

import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.actvity.LogisticsHandbookAty;
import com.llg.privateproject.actvity.OnlineTrainAty;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class DialogAuthentication extends Dialog implements
		android.view.View.OnClickListener {
	private TextView tvCancel;
	private TextView tvConfirm;
	private TextView tvTitle;
	private TextView tvMessage;
	private Context context;
	private onConfirmListener confirmListener;

	public DialogAuthentication(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		setContentView(R.layout.dialog_authentication);
	}

	public void initUI() {
		setCanceledOnTouchOutside(true);
//		WindowManager.LayoutParams Params = getWindow().getAttributes();
//		Params.width = (int) (AppContext.getScreenWidth() * 0.9);
//		Params.height = (int) (AppContext.getScreenHeight() * 0.30);
//		getWindow().setAttributes(Params);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		tvCancel = (TextView) findViewById(R.id.tv_cancel);
		tvConfirm = (TextView) findViewById(R.id.tv_confirm);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvMessage = (TextView) findViewById(R.id.tv_message);
		tvCancel.setOnClickListener(this);
		tvConfirm.setOnClickListener(this);
	}

	public void setTitle(String title) {
		tvTitle.setText(title);
	}

	public void setMessage(String message) {
		tvMessage.setText(message);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		dismiss();
		switch (v.getId()) {
		case R.id.tv_cancel:
			break;
		case R.id.tv_confirm:
			confirmListener.request();
			break;
		default:
			break;
		}
	}

	public interface onConfirmListener {
		void request();
	}

	public void setOnConfirmListener(onConfirmListener confirmListener) {
		this.confirmListener = confirmListener;
	}
}
