//package com.llg.privateproject.view;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.json.JSONObject;
//
//import com.bjg.lcc.jsonparser.ParseJson;
//import com.bjg.lcc.privateproject.R;
//import com.lidroid.xutils.http.RequestParams;
//import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
//import com.llg.privateproject.AppContext;
//import com.llg.privateproject.actvity.BusinessListAty;
//import com.llg.privateproject.adapters.NearbyAdapter;
//import com.llg.privateproject.adapters.NearbyRightAdapter;
//import com.llg.privateproject.entities.MenuItem;
//import com.llg.privateproject.entities.Region;
//import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
//
//import android.content.Context;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Handler;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.LinearLayout;
//import android.widget.LinearLayout.LayoutParams;
//import android.widget.ListView;
//import android.widget.PopupWindow;
//
//public class NearbyPopuWindow extends PopupWindow {
//	// 屏幕宽度
//	public int mScreenWidth;
//	// 屏幕宽度
//	public int mScreenHeight;
//	private Context context;
//	private List<MenuItem> groups;
//	private List<ArrayList<MenuItem>> childrens;
//	private List<Region> list;
//	private NearbyAdapter adapter;
//	private NearbyRightAdapter rightAdapter;
//
//	public NearbyPopuWindow(Context context, int width, int height) {
//		// TODO Auto-generated constructor stub
//		this.context = context;
//		list = new ArrayList<Region>();
//		// 设置可以获得焦点
//		setFocusable(true);
//		// 设置弹窗内可点击
//		setTouchable(true);
//		// 设置弹窗外可点击
//		setOutsideTouchable(true);
//
//		// 获得屏幕的宽度和高度
//		mScreenWidth = AppContext.getScreenWidth();
//		mScreenHeight = AppContext.getScreenHeight();
//
//		// 设置弹窗的宽度和高度
//		setWidth(width);
//		setHeight(height);
//
//		setBackgroundDrawable(new BitmapDrawable());
//
//		// 设置弹窗的布局界面
//		setContentView(LayoutInflater.from(context).inflate(
//				R.layout.popu_nearby, null));
//		initUI();
////		requestRegion();
//	}
//
//	private void initUI() {
//		int height = (int) (AppContext.getScreenHeight() * 0.6);
//		LinearLayout linearLayout = (LinearLayout) getContentView()
//				.findViewById(R.id.ly_listview);
//		linearLayout.setLayoutParams(new LayoutParams(
//				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//		ListView listViewLeft = (ListView) getContentView().findViewById(
//				R.id.lv_left);
//		ListView listViewRight = (ListView) getContentView().findViewById(
//				R.id.lv_right);
//		LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
//				height, 1);
//		listViewLeft.setLayoutParams(layoutParams);
//		listViewRight.setLayoutParams(layoutParams);
//		View view = getContentView().findViewById(R.id.view);
//		view.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				dismiss();
//			}
//		});
//		adapter = new NearbyAdapter(context, list);
//		rightAdapter = new NearbyRightAdapter(context, list);
//		listViewLeft.setAdapter(adapter);
//		listViewRight.setAdapter(rightAdapter);
//		listViewLeft.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				rightAdapter.setSelectPosition(arg2);
//				adapter.notifyDataSetChanged();
//				adapter.setPosition(arg2);
//				;
//			}
//		});
//		listViewRight.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				dismiss();
//				((BusinessListAty) context).setID((String) arg0
//						.getItemAtPosition(arg2));
//			}
//		});
//
//		getContentView().findViewById(R.id.lv_right);
//	}
//
//	private void requestRegion() {
//		Log.i("tag", "----------执行了requestRegion()------");
//		RequestParams params = new RequestParams();
//		params.addQueryStringParameter("code", "5001");
//		AppContext.getHtmlUitls().xUtils(context, HttpMethod.POST,
//				"findSysMapList", params, new HttpCallback() {
//					@Override
//					public void onError(String msg) {
//						// TODO Auto-generated method stub
//						Log.i("tag", msg
//								+ "-------getCities--getCities-msg------");
//						// handler.sendEmptyMessage(2);
//					}
//
//					@Override
//					public void onBack(JSONObject json) {
//						// TODO Auto-generated method stub
//						Map<String, Object> map = ParseJson.getParseJson()
//								.parseIsSuccess(json);
//						Log.i("tag", "----------成功了------");
//						Log.i("tag", (Boolean) map.get("isSuccess")
//								+ "---发送个人请求判断isSuccess------onBack------");
//						if ((Boolean) map.get("isSuccess")) {
//							list = ParseJson.getParseJson().parseRegion(json);
//							Log.i("tag", list.size() + "----------成功了------");
//							handler.sendEmptyMessage(1);
//						}
//					}
//				});
//	}
//
//	Handler handler = new Handler() {
//		public void handleMessage(android.os.Message msg) {
//			if (msg.what == 1) {
//				adapter.setList(list);
//				rightAdapter.setList(list);
//			}
//			// rightAdapter.setNearbyPopuwindow(NearbyPopuWindow.this);
//		};
//	};
//
//}
