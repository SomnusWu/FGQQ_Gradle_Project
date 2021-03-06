package com.llg.privateproject.actvity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.privateproject.adapters.OnlineExamAdapter;
import com.llg.privateproject.fragment.BaseActivity;

public class OnlineExamAty extends BaseActivity {
	@ViewInject(R.id.listview)
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_online_exam);
		ViewUtils.inject(this);
		initUI();
	}

	private void initUI() {
		View view = LayoutInflater.from(this).inflate(
				R.layout.listitem_footview, null);
		LinearLayout lySumbmit = (LinearLayout) view
				.findViewById(R.id.ly_submit);
		listView.addFooterView(lySumbmit);
		OnlineExamAdapter adapter = new OnlineExamAdapter(this);
		listView.setAdapter(adapter);
	}

	@OnClick({ R.id.iv_back })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		default:
			break;
		}
	}
}
