package com.llg.privateproject.actvity;

import com.bjg.lcc.privateproject.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.privateproject.adapters.MyTaskAdapter;
import com.llg.privateproject.view.PartTimePopuwindow;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MineTask extends Activity {
	@ViewInject(R.id.textview1)
	private TextView textview1;
	@ViewInject(R.id.textview2)
	private TextView textview2;
	@ViewInject(R.id.textview3)
	private TextView textview3;

	@ViewInject(R.id.line_green1)
	private View line_green1;
	@ViewInject(R.id.line_green2)
	private View line_green2;
	@ViewInject(R.id.line_green3)
	private View line_green3;

	@ViewInject(R.id.ly1)
	private LinearLayout ly1;
	@ViewInject(R.id.ly2)
	private LinearLayout ly2;
	@ViewInject(R.id.ly3)
	private LinearLayout ly3;
	// 顶端容器
	@ViewInject(R.id.relayout_top)
	private RelativeLayout relLayoutTop;
	// 上拉下拉控件
	@ViewInject(R.id.lv_task)
	private PullToRefreshListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_my_task);
		ViewUtils.inject(this);
		initUI();
	}

	// 初始化界面
	private void initUI() {
		MyTaskAdapter adapter = new MyTaskAdapter(this);
		listview.setAdapter(adapter);
	}

	// xutil点击
	@OnClick({ R.id.iv_back, R.id.iv_popumenu, R.id.ly1, R.id.ly2, R.id.ly3, })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.ly1:
			textview1.setTextColor(getResources().getColor(R.color.orange1));
			line_green1.setVisibility(View.VISIBLE);

			textview2.setTextColor(getResources().getColor(R.color.black1));
			line_green2.setVisibility(View.GONE);

			textview3.setTextColor(getResources().getColor(R.color.black1));
			line_green3.setVisibility(View.GONE);
			break;
		case R.id.ly2:
			textview1.setTextColor(getResources().getColor(R.color.black2));
			line_green1.setVisibility(View.GONE);

			textview2.setTextColor(getResources().getColor(R.color.orange1));
			line_green2.setVisibility(View.VISIBLE);

			textview3.setTextColor(getResources().getColor(R.color.black2));
			line_green3.setVisibility(View.GONE);
			break;
		case R.id.ly3:
			textview1.setTextColor(getResources().getColor(R.color.black2));
			line_green1.setVisibility(View.GONE);

			textview2.setTextColor(getResources().getColor(R.color.black2));
			line_green2.setVisibility(View.GONE);

			textview3.setTextColor(getResources().getColor(R.color.orange1));
			line_green3.setVisibility(View.VISIBLE);
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
