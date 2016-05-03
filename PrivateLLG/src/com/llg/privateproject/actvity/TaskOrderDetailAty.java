package com.llg.privateproject.actvity;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.privateproject.view.PartTimePopuwindow;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * @author cc 订单任务详情
 */
public class TaskOrderDetailAty extends Activity {
	@ViewInject(R.id.relayout_top)
	private RelativeLayout relLayoutTop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_task_order_detail);
		ViewUtils.inject(this);
		initUI();
	}

	// xtuil点击事件
	@OnClick({ R.id.iv_back, R.id.iv_popumenu })
	public void onClick(View v) {
		Log.i("tag", "++++++++++++++++++=----------------tag");
		switch (v.getId()) {
		case R.id.iv_back:
			Log.i("tag", "=----------------tag");
			finish();
			break;
		case R.id.iv_popumenu:
			PartTimePopuwindow partTimePopuwindow = new PartTimePopuwindow(this);
			partTimePopuwindow.showAsDropDown(relLayoutTop);
			break;
		default:
			break;
		}
	}

	// 初始化界面
	private void initUI() {

	}
}