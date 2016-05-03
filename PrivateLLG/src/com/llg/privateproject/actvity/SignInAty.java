package com.llg.privateproject.actvity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.view.DialogRegist;
import com.llg.privateproject.view.DialogUpload;
import com.llg.privateproject.view.PartTimePopuwindow;

/**
 * 签收信息
 * 
 * @author cc
 */
public class SignInAty extends BaseActivity {
	@ViewInject(R.id.relayout_top)
	private RelativeLayout relLayoutTop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_sign_in);
		ViewUtils.inject(this);
		initUI();
	}

	@OnClick({ R.id.iv_back, R.id.iv_popumenu, R.id.tv_upload })
	public void onClick(View v) {
		Log.i("tag", "++++++++++++++++++=----------------tag");
		switch (v.getId()) {
		case R.id.iv_back:
			Log.i("tag", "=----------------tag");
			finish();
			break;
		case R.id.tv_upload:
			break;
		case R.id.iv_popumenu:
			PartTimePopuwindow partTimePopuwindow = new PartTimePopuwindow(this);
			partTimePopuwindow.showAsDropDown(relLayoutTop);
			break;
		default:
			break;
		}
	}

	private void initUI() {
		DialogUpload dialogUpload = new DialogUpload(this);
		dialogUpload.show();
		// DialogRegist dialogRegist=new DialogRegist(this);
		// dialogRegist.show();

	}
}
