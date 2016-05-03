package com.llg.privateproject.actvity;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.common.constant.enums.COMMessage;
import com.bjg.lcc.privateproject.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;

/**
 * 联系我们 yh 2015.6.7
 * */
public class ContactUsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.contactus);
		setContentView(R.layout.contactus);
		ViewUtils.inject(this);
	}

	@OnClick({ R.id.turn, R.id.tv_telphone })
	public void myClick(View v) {
		switch (v.getId()) {
		case R.id.turn:
			finish();
			break;

		case R.id.tv_telphone:
			Intent intent = new Intent(Intent.ACTION_DIAL);
			intent.setData(Uri.parse("tel:" + COMMessage.TEL.getValue()));
			if (intent.resolveActivity(getPackageManager()) != null) {
				startActivity(intent);
			}
			break;
		}
	}
}
