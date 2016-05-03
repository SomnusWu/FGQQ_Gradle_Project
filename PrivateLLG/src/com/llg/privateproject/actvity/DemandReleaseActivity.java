package com.llg.privateproject.actvity;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.llg.help.GetProgressBar;
import com.llg.privateproject.fragment.FabuFragmentFabu;
import com.llg.privateproject.fragment.FabuFragmentFenlei;
import com.llg.privateproject.fragment.FabuFragmentHome;
import com.llg.privateproject.fragment.FabuFragmentSet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

/**
 * 需求与发布 yh 2015.9.18
 */
public class DemandReleaseActivity extends FragmentActivity implements
		OnCheckedChangeListener {
	/** fragment容器 */
	@ViewInject(R.id.fubu_fl)
	private FrameLayout fubu_fl;
	FragmentManager fm;
	FragmentTransaction ft;
	/** 首页 */
	FabuFragmentHome fh;
	/** 分类 */
	FabuFragmentFenlei fl;
	/** 发布 */
	FabuFragmentFabu fb;
	/** 个人中心 */
	FabuFragmentSet fs;
	@ViewInject(R.id.rg)
	private RadioGroup rg;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.demandrelease);
		ViewUtils.inject(this);
		rg.setOnCheckedChangeListener(this);
		GetProgressBar.getProgressBar(this);
		init();
	}

	private void init() {
		fm = getSupportFragmentManager();
		ft = fm.beginTransaction();
		if (fh == null) {
			fh = new FabuFragmentHome();
			ft.add(R.id.fubu_fl, fh);
		} else {
			ft.show(fh);
		}
		ft.commitAllowingStateLoss();

	}

	/** 隐藏fragment */
	void hideFragments(FragmentTransaction ft) {
		if (fh != null) {
			ft.hide(fh);
		}
		if (fl != null) {
			ft.hide(fl);
		}
		if (fb != null) {
			ft.hide(fb);
		}
		if (fs != null) {
			ft.hide(fs);// 15213704749
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		FragmentTransaction ft = fm.beginTransaction();
		hideFragments(ft);
		switch (checkedId) {
		case R.id.rb_fh:// 发布首页
			if (fh == null) {
				fh = new FabuFragmentHome();
				ft.add(R.id.fubu_fl, fh);
			} else {
				ft.show(fh);
			}

			break;
		case R.id.rb_fl:// 发布分类

			if (fl == null) {
				fl = new FabuFragmentFenlei();
				ft.add(R.id.fubu_fl, fl);
			} else {
				ft.show(fl);
			}

			break;
		case R.id.rb_fb:// 发布
			if (fb == null) {
				fb = new FabuFragmentFabu();
				ft.add(R.id.fubu_fl, fb);
			} else {
				ft.show(fb);
			}

			break;
		case R.id.rb_fs:// 个人中心

			if (fs == null) {
				fs = new FabuFragmentSet();
				ft.add(R.id.fubu_fl, fs);
			} else {
				ft.show(fs);
			}
			break;

		default:
			break;
		}
		ft.commitAllowingStateLoss();
	}

}