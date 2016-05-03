package com.llg.privateproject.actvity;

import java.util.ArrayList;
import java.util.List;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.adapters.MyFabuAdapter;
import com.llg.privateproject.adapters.MydialogAdatper;

public class MyFabuActivity extends FragmentActivity implements
		OnClickListener, OnItemClickListener {

	private FragmentTabHost mTabHost;
	private View line;
	/**
	 * 布局填充器
	 * 
	 */
	private LayoutInflater mLayoutInflater;

	/**
	 * 存放图片数组
	 * 
	 */
	private int mImageArray[] = { R.drawable.tab_home_btn,
			R.drawable.tab_message_btn, R.drawable.tab_selfinfo_btn, };

	/**
	 * 选修卡文字
	 * 
	 */
	private String mTextArray[] = { "已发布", "审核中", "已删除" };

	/**
	 * 
	 * 
	 */
	private int itemWidth;
	private int jianjuWidth;

	private ListView dialogListView;
	private Dialog dialog;
	@ViewInject(R.id.ly1)
	private LinearLayout ly1;
	@ViewInject(R.id.ly2)
	private LinearLayout ly2;
	@ViewInject(R.id.ly3)
	private LinearLayout ly3;

	@ViewInject(R.id.textview)
	private TextView textview1;
	@ViewInject(R.id.textview2)
	private TextView textview2;
	@ViewInject(R.id.textview3)
	private TextView textview3;

	@ViewInject(R.id.imageview)
	private ImageView iv;
	@ViewInject(R.id.imageview2)
	private ImageView iv2;
	@ViewInject(R.id.imageview3)
	private ImageView iv3;

	@ViewInject(R.id.line_green1)
	private View line_green1;
	@ViewInject(R.id.line_green2)
	private View line_green2;
	@ViewInject(R.id.line_green3)
	private View line_green3;

	@ViewInject(R.id.my_fabu_lv)
	private ListView listview;

	private MyFabuAdapter adapter;
	private int tvHeight;
	private TextView tvConfirm;
	private TextView tvCancel;
	private List<String> dialoglist;
	private List<String> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_my_fabu);
		ViewUtils.inject(this);
		initData();
		initView();
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
		final MydialogAdatper adapter = new MydialogAdatper(this, dialoglist);
		dialogListView.setAdapter(adapter);
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
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		initDialog();
		dialogListView.setOnItemClickListener(this);
		tvCancel.setOnClickListener(this);
		tvConfirm.setOnClickListener(this);
		adapter = new MyFabuAdapter(this, dialog, list);
		listview.setAdapter(adapter);
		ly1.setOnClickListener(this);
		ly2.setOnClickListener(this);
		ly3.setOnClickListener(this);
	}

	private void initData() {
		dialoglist = new ArrayList<String>();
		list = new ArrayList<String>();
		dialoglist.add("置顶");
		dialoglist.add("公开/保密");
		dialoglist.add("删除");
		dialoglist.add("联系客服");
		dialoglist.add("推广咨询热线");
		list.add("淘宝客服");
		list.add("淘宝客服");
		list.add("淘宝客服");
		list.add("淘宝客服");
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	private View getTabItemView(int index) {

		return null;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ly1:
			Log.i("tag", "-----进来了点击事件-------");
			iv.setImageResource(R.drawable.bbt_3_2_1_yifabu2);
			textview1.setTextColor(getResources().getColor(R.color.green2));
			line_green1.setVisibility(View.VISIBLE);

			iv2.setImageResource(R.drawable.bbt_3_2_2_shenhezhong1);
			textview2.setTextColor(getResources().getColor(R.color.black1));
			line_green2.setVisibility(View.GONE);

			iv3.setImageResource(R.drawable.bbt_3_2_3_delete1);
			textview3.setTextColor(getResources().getColor(R.color.black1));
			line_green3.setVisibility(View.GONE);
			break;
		case R.id.ly2:
			Log.i("tag", "-----进来了点击事件2-------");
			iv2.setImageResource(R.drawable.bbt_3_2_2_shenhezhong2);
			textview2.setTextColor(getResources().getColor(R.color.green2));
			line_green2.setVisibility(View.VISIBLE);

			iv.setImageResource(R.drawable.bbt_3_2_1_yifabu1);
			textview1.setTextColor(getResources().getColor(R.color.black1));
			line_green1.setVisibility(View.GONE);

			iv3.setImageResource(R.drawable.bbt_3_2_3_delete1);
			textview3.setTextColor(getResources().getColor(R.color.black1));
			line_green3.setVisibility(View.GONE);
			break;
		case R.id.ly3:
			Log.i("tag", "-----进来了点击事件3-------");
			iv3.setImageResource(R.drawable.bbt_3_2_3_delete2);
			textview3.setTextColor(getResources().getColor(R.color.green2));
			line_green3.setVisibility(View.VISIBLE);

			iv.setImageResource(R.drawable.bbt_3_2_1_yifabu1);
			textview1.setTextColor(getResources().getColor(R.color.black1));
			line_green1.setVisibility(View.GONE);

			iv2.setImageResource(R.drawable.bbt_3_2_2_shenhezhong1);
			textview2.setTextColor(getResources().getColor(R.color.black1));
			line_green2.setVisibility(View.GONE);
			break;
		case R.id.dialog_cancel:
			dialog.dismiss();
			break;
		case R.id.dialog_confirm:
			dialog.dismiss();
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
		// TODO Auto-generated method stub
		switch ((int) arg0.getItemIdAtPosition(arg2)) {
		case 0:
			Intent intent = new Intent(this, BuyTopActivity.class);
			startActivity(intent);
			break;
		case 1:
			Toast.makeText(this, "公开", Toast.LENGTH_SHORT).show();
			break;
		case 2:
			list.remove(arg2);
			adapter.setList(list);
			break;
		case 3:
			Toast.makeText(this, "公开", Toast.LENGTH_SHORT).show();
			break;
		case 4:
			Toast.makeText(this, "公开", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
		dialog.dismiss();
	}

}
