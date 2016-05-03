package com.llg.privateproject.actvity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bjg.lcc.privateproject.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.privateproject.adapters.AccountChangeAdapter;
import com.llg.privateproject.fragment.BaseActivity;

/**
 * @author cc 账号变更明细
 */
public class AccountChangeAty extends BaseActivity {

	@ViewInject(R.id.listview_account_change)
	private PullToRefreshListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_account_change);
		ViewUtils.inject(this);
		initUI();
	 
		  
	}

	private void initUI() {
		AccountChangeAdapter adapter = new AccountChangeAdapter(this);
		listView.setAdapter(adapter);
	}

	@OnClick({ R.id.iv_back })
	public void onClick(View v) {
		Log.i("tag", "++++++++++++++++++=----------------tag");
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		default:
			break;
		}
	}
}
