package com.llg.privateproject.actvity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.fragment.SettingFragment;
import com.llg.privateproject.view.MyTabWidget;

/***
 * 个人中心  yh 2015.10.27
 * */
public class MyHome extends MyFragmentActivity {
	private SettingFragment mSettingFragment;
	public static FragmentManager mFragmentManager;
	// 全局Context
	private AppContext appContext;
	@ViewInject(R.id.tab_widget)
	private MyTabWidget tab_widget;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this);
		init();
	}

	private void init() {
		tab_widget.setVisibility(View.GONE);
		mFragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		if (null == mSettingFragment) {
			mSettingFragment = new SettingFragment();
			transaction.add(R.id.center_layout, mSettingFragment);
			//
		} else {
			transaction.show(mSettingFragment);
		}
		transaction.commitAllowingStateLoss();
	}
}
