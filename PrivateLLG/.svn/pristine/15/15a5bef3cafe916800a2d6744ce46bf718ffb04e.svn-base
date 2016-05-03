package com.llg.privateproject.actvity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.privateproject.fragment.BaseActivity;

public class OnlineTrainAty extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_online_train);
		initUI();
	}

	private void initUI() {

	}

	@OnClick({ R.id.iv_back, R.id.tv_exam, R.id.tv_handbook })
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.tv_exam:
			intent = new Intent(this, OnlineTrainAty.class);
			startActivity(intent);
			break;
		case R.id.tv_handbook:
			intent = new Intent(this, LogisticsHandbookAty.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
