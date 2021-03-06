package com.llg.privateproject.actvity;

import java.util.ArrayList;
import java.util.List;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.adapters.MydialogAdatper;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.view.Gerenziliao_Dialog;
import com.llg.privateproject.view.Gerenziliao_Dialog.AreaListener;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author cc 修改简历
 */
public class ModifyActivity extends BaseActivity implements AreaListener {
	private ListView dialogListView;
	private Dialog dialog;
	private int tvHeight;
	private TextView tvConfirm;
	private TextView tvCancel;
	private List<String> dataList;
	private int scrollX;
	private int scrollY;
	// private List<String> list;
	private MydialogAdatper adapter;
	@ViewInject(R.id.modify_tv_site)
	private TextView tvSite;
	private int selectPosition = 30;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_modify);
		ViewUtils.inject(this);
		initDialog();
	}

	@OnClick({ R.id.ly_birthday, R.id.ly_education, R.id.ly_money,
			R.id.ly_site, R.id.ly_work_name, R.id.ly_work_years })
	public void onClick(View v) {
		List<String> list = new ArrayList<String>();
		list.clear();
		switch (v.getId()) {
		case R.id.ly_birthday:
			for (int i = 1950; i < 1999; i++) {
				list.add(i + "");
			}
			updateData(list);
			dialog.show();
			break;
		case R.id.ly_education:
			Log.i("tag", list.size() + "----list.size-------");
			list.add("初中及以下");
			list.add("高中");
			list.add("中专/技校");
			list.add("大专");
			list.add("本科");
			list.add("研究生");
			list.add("硕士及以上");
			updateData(list);
			dialog.show();
			Log.i("tag", list.size() + "----list.size-------");
			break;
		case R.id.ly_money:
			list.add("面议");
			list.add("1000元以下");
			list.add("1000-2000元");
			list.add("1000-2000元");
			list.add("2000-3000元");
			list.add("3000-5000元");
			list.add("5000-8000元");
			list.add("8000-12000元");
			list.add("12000-20000");
			list.add("20000元以上");
			updateData(list);
			dialog.show();
			break;
		case R.id.ly_site:
			Gerenziliao_Dialog siteDialog = new Gerenziliao_Dialog(this, 6,
					this);
			siteDialog.show();
			WindowManager.LayoutParams lp = siteDialog.getWindow()
					.getAttributes();
			lp.width = AppContext.getScreenWidth();
			lp.height = AppContext.getScreenHeight();
			siteDialog.getWindow().setAttributes(lp);
			break;
		case R.id.ly_work_name:

			break;
		case R.id.ly_work_years:
			list.add("无经验");
			list.add("在读学生");
			list.add("应届毕业生");
			list.add("一年内");
			list.add("1-2年");
			list.add("2-3年");
			list.add("3-5年");
			list.add("5年及以上");
			updateData(list);
			dialog.show();
			break;

		default:
			break;
		}
		Log.i("tag", list.size() + "----list.size-------");

	}

	private void initDialog() {
		dialog = new Dialog(this);
		View dialogView = LayoutInflater.from(this).inflate(
				R.layout.dialog_fabu, null);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(dialogView);
		dialogListView = (ListView) dialogView
				.findViewById(R.id.dialog_listview);
		tvConfirm = (TextView) dialogView.findViewById(R.id.dialog_confirm);
		tvCancel = (TextView) dialogView.findViewById(R.id.dialog_cancel);
		WindowManager.LayoutParams Params = dialog.getWindow().getAttributes();
		Params.width = (int) (AppContext.getScreenWidth() * 0.8);
		Params.height = (int) (AppContext.getScreenWidth() * 0.8);
		dialog.getWindow().setAttributes(Params);
		adapter = new MydialogAdatper(this, null);
		dialogListView.setAdapter(adapter);
		dialogListView.setSelection(selectPosition);
		ViewTreeObserver vto2 = dialogListView.getViewTreeObserver();
		vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				dialogListView.getViewTreeObserver()
						.removeGlobalOnLayoutListener(this);
				tvHeight = dialogListView.getHeight() / 5;
				Log.i("tag", tvHeight + "+++++hjhjhjhjh++++++");
				adapter.setTvHeitht(tvHeight);
			}
		});
		// 监听listview的滚动
		dialogListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView arg0, int scrollState) {
				// TODO Auto-generated method stub
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					selectPosition = dialogListView.getSelectedItemPosition();
				}
			}

			// 滚动时调用
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}
		});
		dialogListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void updateData(List<String> dataList) {
		Log.i("tag", "-------updatedata-------");
		adapter.setList(dataList);
	}

	@Override
	public void setAddress(String area, String zone) {
		// TODO Auto-generated method stub
		tvSite.setTextColor(getResources().getColor(R.color.black1));
		tvSite.setText(area);
	}
}
