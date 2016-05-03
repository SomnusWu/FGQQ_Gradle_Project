package com.llg.privateproject.actvity;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.llg.common.constant.enums.COMMessage;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 用户协议对话框 yh 2015.6.11
 * */
public class MyDialog extends Activity implements
		android.view.View.OnClickListener {
	Context context;

	/** type=1:注册协议 type=2 :公司简介 */
	int type = 1;
	String title = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		init();
	}

	public void init() {
		type = getIntent().getIntExtra("type", 1);
		title = getIntent().getStringExtra("title");
		setFinishOnTouchOutside(false);
		setContentView(R.layout.dialog_agreement);
		ViewUtils.inject(this);
		reAgree(type, title);

	}

	/** type=1:风购全球注册协议, type=2 :公司简介 */
	public void reAgree(int type, String title) {
		TextView tv_agreement_title = (TextView) findViewById(R.id.agreement_title);
		TextView agreement_content = (TextView) findViewById(R.id.agreement_content);
		TextView agreem_bt = (TextView) findViewById(R.id.turn);

		tv_agreement_title.setText(title);
		agreement_content.setMovementMethod(ScrollingMovementMethod
				.getInstance());
		// StringBuffer sb=new StringBuffer();

		if (type == 1) {
			agreement_content.setText(COMMessage.AGREEMENT.getValue());
		} else if (type == 2) {
			// sb.append("我是公司简介内容");
			agreement_content.setText(COMMessage.ABOUT_US.getValue());
		}

		// agreement_content.setText(sb.toString());
		agreem_bt.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.turn:
			finish();
			break;

		default:
			break;
		}

	}

}