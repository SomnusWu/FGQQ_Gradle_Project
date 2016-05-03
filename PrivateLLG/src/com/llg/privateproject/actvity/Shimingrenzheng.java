package com.llg.privateproject.actvity;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import com.bjg.lcc.privateproject.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 实名认证 yh 2015.6.8
 * 
 * */
public class Shimingrenzheng extends Activity {

	@ViewInject(R.id.myid)
	private EditText myid;
	@ViewInject(R.id._cb_isagree)
	private CheckBox _cb_isagree;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shimingrenzheng);
		ViewUtils.inject(this);
		myid.setHintTextColor(getResources().getColor(R.color.black3));

	}

	@OnClick({ R.id.tv_agreement, R.id.tv_commit, R.id.turn })
	public void myClick(View v) {
		switch (v.getId()) {
		case R.id.tv_agreement:
			startActivity(new Intent(this, MyDialog.class));
			break;
		case R.id.turn:
			finish();
			break;
		case R.id.tv_commit:
			Toast.makeText(this, "提交", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}

	}

}
