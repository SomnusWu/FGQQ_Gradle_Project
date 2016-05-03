package com.llg.privateproject.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjg.lcc.jsonparser.ParseJson;
import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.llg.help.Fgqqdb;
import com.llg.help.GetProgressBar;
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.actvity.CommodityActivity;
import com.llg.privateproject.actvity.SearchActivity;
import com.llg.privateproject.adapter.CateListAdapter;
import com.llg.privateproject.adapter.SecondaryAdapter;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.utils.Constants;
import com.llg.privateproject.view.MyGridView;
import com.smartandroid.sa.view.AutoLoading;
import com.zxing.activity.CaptureActivity;

/**
 * 分类页
 * 
 * @author gongyibing
 * @version 1.0
 * @created 2015-05-07
 */
@SuppressLint("HandlerLeak")
public class CategoryFragment extends BaseFragment {
	int pics[] = { R.drawable.defaultpic, R.drawable.bkg, R.drawable.welcome };
	private static final String TAG = "CategoryFragment";
	/** 搜索框 */
	@ViewInject(R.id.home_search_id)
	private TextView home_search_id;
	/** 城市选择 */
	@ViewInject(R.id.head_city_id)
	private TextView homeCity;
	/** 分类数据 */
	private List<Map<String, Object>> classificationList1 = null;
	/** 二级分类 */
	private List<Map<String, Object>> SecondaryListArryay = null;
	/** 广告列表 */
	private List<List<Map<String, Object>>> adinfoList = null;
	private Activity mActivity;
	/** 全局Context */
	private AppContext appContext;
	/** 左侧分类ListView */
	@ViewInject(R.id.cate_listview)
	private ListView mCateListView;
	/** 右侧产品 MyGridView */
	@ViewInject(R.id.secondary_gridView_id)
	private MyGridView myGridView;
	/**  */
	@ViewInject(R.id.ly)
	private LinearLayout ly;
	/** 二级/三级分类容器 */
	@ViewInject(R.id.ll_second)
	private LinearLayout ll_second;
	/** 二级分类 */
	private String categoryId = "";
	/** 二级分类名 */
	private String name = "";

	// /** 网络请求失败界面 */
	// @ViewInject(R.id.wangluoqingqiushibai)
	// private LinearLayout wangluoqingqiushibai;

	/** 一级分类界面界面 */
	@ViewInject(R.id.fenlei)
	private RelativeLayout fenlei;
	/** 二级级分类界面界面 */
	@ViewInject(R.id.feilei2)
	private ScrollView feilei2;
	/** 右侧广告图片 */
	@ViewInject(R.id.iv_banner)
	private ImageView iv_banner;
	private AutoLoading autoLoading;
	/** 分类列表 */
	private CateListAdapter mCateListAdapter;
	/** 产品列表 */
	private SecondaryAdapter secondaryAdapter;
	private Fgqqdb fgqqdb;
	Thread thread;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			GetProgressBar.dismissMyProgressBar();
			switch (msg.what) {
			case Constants.SUCCESS:
				/** 刷新分类 */
				setVisible();
				mCateListAdapter.notifyDataSetInvalidated();
				if (classificationList1.size() > 0) {

					String parentId = classificationList1.get(0).get("id")
							.toString().trim();
					name = classificationList1.get(0).get("name").toString()
							.trim();
					getClassificationGoodsData(parentId, 0);
					if (adinfoList != null && adinfoList.size() > 0
							&& adinfoList.get(0).size() > 0) {

						String img = adinfoList.get(0).get(0).get("img")
								.toString();
						MyFormat.setBitmap(mActivity, iv_banner, img,
								AppContext.getScreenWidth() * 3 / 4,
								AppContext.getScreenWidth() * 3 / 8);
					}

				}
				break;
			case Constants.REFRESH:
				/** 刷新分类产品列表 */
				secondaryAdapter.notifyDataSetInvalidated();

				break;
			case Constants.FAILED:
				/** 提示显示 */
				Toast.makeText(mActivity, msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	public static CategoryFragment newInstance() {
		CategoryFragment categoryFragment = new CategoryFragment();
		return categoryFragment;
	}

	private void addThird() {

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_category, container,
				false);

		ViewUtils.inject(this, view);
		fgqqdb = new Fgqqdb();
		autoLoading = new AutoLoading(mActivity, ly);
		autoLoading.setClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getClassificationData();
			}
		});
		homeCity.setText("");
		android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) iv_banner
				.getLayoutParams();

		params.width = AppContext.getScreenWidth() * 3 / 4;
		params.height = AppContext.getScreenWidth() * 3 / 8;

		iv_banner.setLayoutParams(params);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		home_search_id.setHintTextColor(getActivity().getResources().getColor(
				R.color.black3));
		classificationList1 = new ArrayList<Map<String, Object>>();

		SecondaryListArryay = new ArrayList<Map<String, Object>>();
		adinfoList = new ArrayList<List<Map<String, Object>>>();
		initDisplay();
		mCateListAdapter = new CateListAdapter(mActivity, classificationList1);
		mCateListView.setAdapter(mCateListAdapter);
		secondaryAdapter = new SecondaryAdapter(mActivity, SecondaryListArryay);
		myGridView.setAdapter(secondaryAdapter);
		// testOneclassifydata();
		// testTwoclassifydata();
		try {

			if (fgqqdb.selectData("b").length() > 10) {
				JSONObject obj = new JSONObject(fgqqdb.selectData("b"));

				getdata(obj);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// thread.stop();
	}

	@Override
	public String getFragmentName() {
		return TAG;
	}

	/** 网络验证数据请求 */
	private void initDisplay() {
		appContext = (AppContext) mActivity.getApplication();

		if (appContext.isNetworkConnected()) {
			getClassificationData();
		} else {
			Message _Msg = mHandler.obtainMessage(Constants.FAILED,
					getResources().getString(R.string.t_Connection));
			mHandler.sendMessage(_Msg);
		}
	}

	/** 
	 * 获取分类数据
	 * 
	 * */
	private void getClassificationData() {
		if (!appContext.isNetworkConnected()) {
			toast(R.string.t_Connection);
			return;
		}
		autoLoading.showLoadingLayout();
		AppContext.getHtmlUitls().xUtils(mActivity, HttpMethod.GET,
				Constants.APP_CLASSIFICATION, null, new HttpCallback() {
			@Override
			public void onBack(JSONObject json) {
				autoLoading.hideAll();
				/** 获取分类数据 */
				JSONArray jsonArray = null;
				try {
					getdata(json);

				} catch (Exception e) {
					Message _Msg = mHandler.obtainMessage(
							Constants.FAILED,
							getResources().getString(
									R.string.data_Exception));
					mHandler.sendMessage(_Msg);
				}
			}

			@Override
			public void onError(String msg) {
				autoLoading.showExceptionLayout();
				Message _Msg = mHandler.obtainMessage(
						Constants.FAILED,
						getResources().getString(
								R.string.connection_Exception));
				mHandler.sendMessage(_Msg);
			}
		});
	}
	/**
	 * 解析数据
	 * @param json
	 * @throws JSONException
	 */
	private void getdata(JSONObject json) throws JSONException {
		JSONArray jsonArray;
		if (json.getBoolean("success")) {
			jsonArray = json.getJSONObject("attributes").getJSONArray(
					"carBeanList");
			Map<String, Object> map = null;// 装载一级列表项数据
			if (jsonArray.length() > 0) {
				classificationList1.clear();
			}
			for (int i = 0; i < jsonArray.length(); i++) {

				JSONObject map1 = jsonArray.getJSONObject(i);

				map = new HashMap<String, Object>();

				map.put("id", map1.get("id").toString());

				map.put("name", map1.get("name").toString());

				JSONArray secondArray = map1.getJSONArray("children");
				if (secondArray.length() > 0) {
					List<Map<String, Object>> secondList = new ArrayList<Map<String, Object>>();

					//					for (int j = 0; j < secondArray.length(); j++) {
					//						JSONObject map2 = secondArray.getJSONObject(j);
					JSONObject map2 = secondArray.getJSONObject(0);
					Map<String, Object> map3 = new HashMap<String, Object>();// 装载二级列表项

					map3.put("id", map2.get("id").toString());

					map3.put("name", map2.get("name").toString());
					map3.put("children", map2.getJSONArray("children"));

					List<Map<String, Object>> thirdList = new ArrayList<Map<String, Object>>();
					if (map2.getJSONArray("children") != null
							&& map2.getJSONArray("children").length() > 0) {
						for (int k = 0; k < map2.getJSONArray("children")
								.length(); k++) {
							JSONObject third = map2
									.getJSONArray("children")
									.getJSONObject(k);
							Map<String, Object> map4 = new HashMap<String, Object>();
							map4.put("id", third.get("id").toString());

							map4.put("name", third.get("name").toString());

							if (third.get("img") == null) {
								map4.put("img", "");
							} else {
								map4.put("img", third.get("img").toString());
							}

							// 添加三级列表
							thirdList.add(map4);
						}
						map3.put("thirdList", thirdList);
					}
					/** 添加二级分类 */
					secondList.add(map3);
					//					}

					map.put("children", secondList);
				}
				/** 添加一级分类 */
				classificationList1.add(map);

			}
			if (!json.getJSONObject(// 广告列表
					"attributes").get("adinfoList").equals(null)
					&& json.getJSONObject("attributes")
					.getJSONArray("adinfoList").length() > 0) {
				JSONArray jsonArray1 = json.getJSONObject(// 广告列表
						"attributes").getJSONArray("adinfoList");

				for (int i = 0; i < jsonArray1.length(); i++) {
					if (classificationList1.get(i) != null) {

						List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
						JSONObject jsonObject = jsonArray1.getJSONObject(i);

						ParseJson.getParseJson()
						.setDataList(
								classificationList1.get(i).get("id")
								.toString(), jsonObject, list);

						adinfoList.add(list);
						list = null;
					}
				}
			}
			Message _Msg = mHandler.obtainMessage(Constants.SUCCESS,
					classificationList1);
			mHandler.sendMessage(_Msg);
			fgqqdb.update(json.toString(), "b");
		} else {
			Message _Msg = mHandler.obtainMessage(Constants.FAILED, "访问异常");
			mHandler.sendMessage(_Msg);
		}
	}

	/***
	 * 查询二级分类商品信息
	 * 
	 * @param parentId
	 *            一级分类id
	 */
	private void getClassificationGoodsData(final String parentId, int position) {

		SecondaryListArryay.clear();

		Map<String, Object> map = classificationList1.get(position);

		List<Map<String, Object>> list = (List<Map<String, Object>>) map
				.get("children");

		Map<String, Object> map2 = null;
		for (int i = 0; i < list.size(); i++) {
			map2 = new HashMap<String, Object>();
			map2.put("id", list.get(i).get("id"));
			// map2.put("brands", list.get(i).get("brands"));
			// map2.put("pId", list.get(i).get("pId"));
			// map2.put("pIdNames", list.get(i).get("pIdNames"));
			map2.put("name", list.get(i).get("name"));
			map2.put("thirdList", list.get(i).get("thirdList"));
			// map2.put("hot", list.get(i).get("hot"));
			// map2.put("pIds", list.get(i).get("pIds"));
			// map2.put("ads", list.get(i).get("ads"));
			map2.put("url", list.get(i).get("url"));
			map2.put("img", list.get(i).get("img"));

			/** 添加二级级分类 */
			SecondaryListArryay.add(map2);
		}
		Message _Msgs = mHandler.obtainMessage(Constants.REFRESH,
				SecondaryListArryay);
		mHandler.sendMessage(_Msgs);

	}

	@OnItemClick(R.id.cate_listview)
	// 一级分类列表项
	public void onItemClick(AdapterView<?> parent, View arg1, int position,
			long arg3) {

		mCateListAdapter.setSelectedPos(position);
		try {
			int size = mCateListView.getHeaderViewsCount();
			name = classificationList1.get(position).get("name").toString()
					.trim();
			String parentId = classificationList1.get(position).get("id")
					.toString().trim();

			getClassificationGoodsData(parentId, position);
			if (adinfoList.get(position).size() > 0) {
				if (adinfoList.get(position).get(0).size() > 0) {
					String img = adinfoList.get(position).get(0).get("img")
							.toString();

					MyFormat.setBitmap(mActivity, iv_banner, img,
							AppContext.getScreenWidth() * 3 / 4,
							AppContext.getScreenWidth() * 3 / 8);
				}
			} else {

				iv_banner.setBackgroundResource(pics[position % 3]);
			}
			//

		} catch (Exception e) {
			LogUtils.e("获取分类产品异常", e);
		}
	}

	/** 设置显示分类还是显示网络请求失败提示 */
	private void setVisible() {
		if (classificationList1.size() > 0) {
			// wangluoqingqiushibai.setVisibility(View.GONE);
			fenlei.setVisibility(View.VISIBLE);
			feilei2.setVisibility(View.VISIBLE);
		} else {
			// wangluoqingqiushibai.setVisibility(View.VISIBLE);
			fenlei.setVisibility(View.GONE);
			feilei2.setVisibility(View.GONE);
		}

	}

	@OnItemClick(R.id.secondary_gridView_id)
	// 二级分类列表项
	public void onItemClicks(AdapterView<?> parent, View arg1, int position,
			long arg3) {
		try {
			String parentId = SecondaryListArryay.get(position).get("id")
					.toString().trim();
			Intent intent = new Intent(mActivity, CommodityActivity.class);
			intent.putExtra("parentId", parentId);

			startActivity(intent);
		} catch (Exception e) {
			LogUtils.e("分类跳转异常", e);
		}
	}

	@OnClick({ R.id.head_city_id, R.id.home_sweep_id, R.id.iv_banner,
		R.id.home_search_id })
	public void clickMethod(View v) {
		switch (v.getId()) {
		/** 城市选择 */
		case R.id.head_city_id:
			mActivity.finish();
			break;
			/** 扫一扫 */
		case R.id.home_search_id:
			Intent intent1 = new Intent(mActivity, SearchActivity.class);
			intent1.putExtra("name", name);
			intent1.putExtra("categoryId", "");
			mActivity.startActivity(intent1);

			break;
			/** 扫一扫 */
		case R.id.home_sweep_id:
			Intent intent = new Intent(getActivity(), CaptureActivity.class);
			getActivity().startActivity(intent);
			break;
			/** 广告图片 */
		case R.id.iv_banner:
			Toast.makeText(mActivity, "广告图片", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		GetProgressBar.dismissMyProgressBar();
	}

}
