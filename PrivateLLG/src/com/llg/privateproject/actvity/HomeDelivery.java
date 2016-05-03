package com.llg.privateproject.actvity;

import com.bjg.lcc.privateproject.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.privateproject.adapters.DeliveryAdapter;
import com.llg.privateproject.entities.Fabu;
import com.llg.privateproject.view.PartTimePopuwindow;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * @author cc 送货上门界面
 */
public class HomeDelivery extends Activity {
	@ViewInject(R.id.lv_parttime)
	private PullToRefreshListView listView;
	@ViewInject(R.id.relayout_top)
	private RelativeLayout relLayoutTop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_send_home);
		ViewUtils.inject(this);
		DeliveryAdapter adapter = new DeliveryAdapter(this);
		listView.setAdapter(adapter);
		Gson gson = new Gson();
		gson.fromJson("", Fabu.class);
	}

	@OnClick({ R.id.iv_back, R.id.iv_popumenu })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
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
}
