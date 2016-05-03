package com.llg.privateproject.actvity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bjg.lcc.jsonparser.ParseJson;
import com.bjg.lcc.privateproject.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.adapters.BusinessAdapter;
import com.llg.privateproject.entities.Citys;
import com.llg.privateproject.entities.MenuItem;
import com.llg.privateproject.entities.NowBuyBusiness;
import com.llg.privateproject.entities.Region;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.utils.LogManag;
import com.llg.privateproject.view.ExpandTabView;
import com.llg.privateproject.view.ExpandTabView.OnButtonClickListener;
import com.llg.privateproject.view.OrderStatusDialog;
import com.llg.privateproject.view.TabViewOne;
import com.llg.privateproject.view.TabViewTwo;
import com.zxing.activity.CaptureActivity;

/**
 * 特惠商家
 * 
 * @author Administrator
 * 
 */
public class BusinessListAty extends BaseActivity implements
		OnRefreshListener2<ListView> {
	@ViewInject(R.id.ratingbar)
	private RatingBar ratingBar;

	@ViewInject(R.id.listview_business)
	private PullToRefreshListView lvBusiness;

	// @ViewInject(R.id.tv_nearby)
	// private TextView tvNearby;
	@ViewInject(R.id.ly_load)
	private LinearLayout lyLoad;

	@ViewInject(R.id.tv_search)
	private TextView tvSearch;

	@ViewInject(R.id.edt_search)
	private EditText edtSearch;
	@ViewInject(R.id.home_scan)
	private ImageView home_scan;

	@ViewInject(R.id.empty)
	private LinearLayout lyEmpty;

	private BusinessAdapter adapter;
	// private String path;// 城市数据库在本地的路径
	private String id = "402880fb4fdf2e7b014fdf31bd420000";// 商圈ID
	// private String id;// 商圈ID
	private List<NowBuyBusiness> tlist;// 总数据
	private int pageNo = 1;// 页数
	// private String name;// 搜索的商家名字

	private ExpandTabView expandTabView;
	private ArrayList<View> mViewArray = new ArrayList<View>();
	private TabViewTwo tabView1;
	private TabViewOne tabView2;
	private TabViewOne tabView3;

	private List<MenuItem> groups;// 左边的list
	private List<ArrayList<MenuItem>> childrens;// 右边的list
	private List<MenuItem> categoryItems;
	private List<MenuItem> orderByItems;
	private List<MenuItem> orderByList;
	private List<Region> regionList;
	private int leftPosition = 0;
	private int rightPosition;
	private int mselectPosition;
	private String shopCategory;
	private String orderBy = "";
	private List<Map<String, String>> kindList;
	private List<MenuItem> kindNameList;
	private List<MenuItem> kindIDList;
	private String code = "5001";
	private String baidu_code;
	private boolean isModify = false;
	private boolean regionIsFirstShow = true;
	private boolean kindIsFirstShow = true;
	private boolean orderByShow = true;

	private boolean recommend = true;// 是不是推荐

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_business_list);
		ViewUtils.inject(this);
		initUI();
		expandTabView = (ExpandTabView) findViewById(R.id.xcc_expandTabView);
		initData();
		setOnClickListener();
		/**
		 * 入驻商家提示
		 */
		// hint();

		new findCityAsyncTask().execute();
	}

	protected void onRestart() {
		super.onRestart();
		if (autoLoading.getVisibelyLoad()) {
			finish();
			return;
		}
	}

	private class findCityAsyncTask extends
			AsyncTask<Integer, Integer, Integer> {

		@Override
		protected Integer doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			if (baidu_code != null && !baidu_code.equals("0")) {
				String path = Environment.getExternalStorageDirectory()
						.getPath() + File.separator + "Citys";

				DbUtils dbUtils = DbUtils.create(BusinessListAty.this, path,
						"city");
				try {
					Citys city = dbUtils.findFirst(Selector.from(Citys.class)
							.where("baidu_code", "=", baidu_code + ""));
					if (city != null) {
						UserInformation.code = city.getCode();
					}
				} catch (DbException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			requestBusinessList();
			super.onPostExecute(result);
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				autoLoading.hideAll();
				adapter.setList(tlist);
				if (customProgressSmall.isShowing()) {
					customProgressSmall.dismiss();
				}
				// /**
				// * 入驻商家提示
				// */
				// hint();
				lvBusiness.onRefreshComplete();
				break;

			default:
				break;
			}
		}
	};

	@OnClick({ R.id.iv_back, R.id.home_scan })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.home_scan:
			startActivity(new Intent(this, CaptureActivity.class));
			break;
		}
	}

	private void find() {

	}

	/** 入驻商品汇,特惠商家提示 */
	private void hint() {
		final OrderStatusDialog dialog = new OrderStatusDialog(this, 13);
		dialog.show();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (dialog != null) {
					dialog.cancel();
				}
			}
		}, 4000);
	}

	/**
	 * 请求商圈的商家列表
	 */
	private void requestBusinessList() {
		RequestParams params = new RequestParams();
		// 没有手动选择城市
		if (AppContext.selectCityCode.equals("00")) {
			if (recommend) {
				isModify = false;
				params.addQueryStringParameter("recommend", "Y");
			}
			// 开启了定位
			if (AppContext.myLongitude != 0 && AppContext.myLatitude != 0) {
				// 手动选择了商圈
				if (isModify) {
					params.addQueryStringParameter("mapCircleId", id);
					Log.i("tag", id + "------mapCircleId");
				}
				params.addQueryStringParameter("lng", AppContext.myLongitude
						+ "");
				params.addQueryStringParameter("lat", AppContext.myLatitude
						+ "");
				params.addQueryStringParameter("regionId", UserInformation.code);

			}
		} else {
			if (isModify) {
				params.addQueryStringParameter("mapCircleId", id);
			}
			if (recommend) {
				isModify = false;
				params.addQueryStringParameter("recommend", "Y");
			}
			params.addQueryStringParameter("regionId",
					AppContext.selectCityCode);
		}

		params.addQueryStringParameter("cusId", AppContext.userid);
		if (shopCategory != null) {
			params.addQueryStringParameter("shopCategory", shopCategory);
		}
		params.addQueryStringParameter("pageNo", pageNo + "");
		params.addQueryStringParameter("orderByField", orderBy);

		AppContext.getHtmlUitls().xUtils(this, HttpMethod.POST, "findSpotShop",
				params, new HttpCallback() {
					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						autoLoading.showExceptionLayout();
						lvBusiness.onRefreshComplete();
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						Map<String, Object> map = ParseJson.getParseJson()
								.parseIsSuccess(json);
						if ((Boolean) map.get("isSuccess")) {
							List<NowBuyBusiness> list = ParseJson
									.getParseJson().parseNowBuyBusiness(json);
							if (list != null) {
								if (pageNo == 1) {
									tlist.clear();
								}
								tlist.addAll(list);
							}
							handler.sendEmptyMessage(1);
						} else {

							Toast.makeText(BusinessListAty.this,
									json.optString("msg"), Toast.LENGTH_SHORT)
									.show();

						}
					}
				});
	}

	/**
	 * 初始化界面
	 */
	private void initUI() {

		initAutoLoading(lyLoad);
		tlist = new ArrayList<NowBuyBusiness>();
		adapter = new BusinessAdapter(this, tlist);
		lvBusiness.setAdapter(adapter);
		lvBusiness.setEmptyView(lyEmpty);
		lvBusiness.setOnRefreshListener(this);
		lvBusiness.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(BusinessListAty.this,
						ShopActivity.class);
				intent.putExtra("push", "BusinessListAty");
				intent.putExtra("shopId", tlist.get(arg2 - 1).getID());
				startActivity(intent);
			}
		});
		autoLoading.setClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				autoLoading.showLoadingLayout();
				requestBusinessList();
			}
		});
		// edtSearch.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// Intent intent = new Intent(BusinessListAty.this,
		// NowBusinessSearchAty.class);
		// startActivityForResult(intent, 1);
		// }
		// });
	}

	private void setOnClickListener() {

		// 点选第一个列表
		tabView1.setOnItemSelectListener(new TabViewTwo.OnItemSelectListener() {

			// 第二级子列表
			@Override
			public void itemSelected(int position) {
				onRefreshTab(tabView1, tabView1.getShowText());
				rightPosition = position;
				try {
					id = regionList.get(leftPosition-1).getList()
							.get(rightPosition).getId();
					isModify = true;
					recommend = false;
					pageNo = 1;
					requestBusinessList();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

			// 一级列表
			@Override
			public void itemLeftSelected(int position) {
				// TODO Auto-generated method stub
				leftPosition = position;
				if (position == 0) {
					recommend = true;
					onRefreshTab(tabView1, groups.get(0).getText());
					pageNo = 1;
					requestBusinessList();

				} else {
					recommend = false;
				}

			}
		});
		tabView2.setOnItemSelectListener(new TabViewOne.OnItemSelectListener() {

			@Override
			public void itemSelected(int position) {
				pageNo = 1;
				onRefreshTab(tabView2, tabView2.getShowText());
				shopCategory = kindList.get(position).get("id");
				requestBusinessList();
			}
		});

		tabView3.setOnItemSelectListener(new TabViewOne.OnItemSelectListener() {

			@Override
			public void itemSelected(int position) {
				pageNo = 1;
				onRefreshTab(tabView3, tabView3.getShowText());
				orderBy = orderByList.get(position).getRegionID();
				requestBusinessList();
			}
		});
	}

	private void initData() {
		kindList = new ArrayList<Map<String, String>>();
		kindNameList = new ArrayList<MenuItem>();
		baidu_code = AppContext.myCityCode;
		kindList = new ArrayList<Map<String, String>>();
		groups = new ArrayList<MenuItem>();
		childrens = new ArrayList<ArrayList<MenuItem>>();
		// 第二个tab的数据
		categoryItems = new ArrayList<MenuItem>();

		// 第三个Tab的数据
		orderByItems = new ArrayList<MenuItem>();
		orderByItems.add(new MenuItem(1, "距离由近到远"));
		orderByItems.add(new MenuItem(2, "销量由多到少"));

		initTabView();

		mViewArray.add(tabView1);
		mViewArray.add(tabView2);
		mViewArray.add(tabView3);

		// 设置参数
		expandTabView.setValue(mViewArray, R.drawable.expand_tab_selector);
		// 设置默认标题
		if (!TextUtils.isEmpty(AppContext.myAddress)) {
			if (AppContext.selectCityCode.equals("00")
					|| AppContext.selectBaiduCode == AppContext.myCityCode) {
				int length = AppContext.myCity.length();
				AppContext.region = AppContext.myAddress.substring(length,
						AppContext.myAddress.length());
				// expandTabView.setTitle(AppContext.region, 0, 1);
				expandTabView.setTitle("推荐", 0, 1);
			} else {

			}
		} else {
			expandTabView.setTitle("全部商圈", 0, 1);
		}
		expandTabView.setTitle("全部类别", 1, 1);
		expandTabView.setTitle("默认排序", 2, 1);
		expandTabView.setOnButtonClickListener(new OnButtonClickListener() {

			@Override
			public void onClick(int selectPosition, PopupWindow popupWindow) {
				// TODO Auto-generated method stub
				if (selectPosition == 0) {
					mselectPosition = selectPosition;
					if (regionIsFirstShow) {
						requestRegion(selectPosition);
					}
				} else if (selectPosition == 1) {
					mselectPosition = selectPosition;
					if (kindIsFirstShow) {
						requestKind();
					}
				} else if (selectPosition == 2) {
					mselectPosition = selectPosition;
					if (orderByShow) {
						requestOrderBy();
					}

				}
			}
		});
	}

	private void updateRegionData() {
		groups = new ArrayList<MenuItem>();
		ArrayList<MenuItem> item1 = null;
		childrens = new ArrayList<ArrayList<MenuItem>>();
		for (int i = 0; i < regionList.size(); i++) {
			groups.add(new MenuItem(1, regionList.get(i).getName()));
			item1 = new ArrayList<MenuItem>();
			for (int j = 0; j < regionList.get(i).getList().size(); j++) {
				regionList.get(i).getList().get(j).getName();
				item1.add(new MenuItem(j + 1, regionList.get(i).getList()
						.get(j).getName()));
			}
			childrens.add(item1);
		}

		MenuItem tempItem = new MenuItem();
		tempItem.setText("推荐");
		groups.add(0, tempItem);
		ArrayList<MenuItem> firstChildrens = new ArrayList<MenuItem>();
		childrens.add(0, firstChildrens);

		expandTabView.setTitle(groups.get(0).getText(), 0, 1);

	}

	/**
	 * 请求地区
	 * 
	 * @param selectPosition
	 */
	private void requestRegion(int selectPosition) {
		RequestParams params = new RequestParams();
		if (AppContext.selectCityCode != "00") {
			params.addQueryStringParameter("code", AppContext.selectCityCode);
		} else {
			params.addQueryStringParameter("code", UserInformation.code);
		}
		AppContext.getHtmlUitls().xUtils(this, HttpMethod.POST,
				"findSysMapList", params, new HttpCallback() {
					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						// handler.sendEmptyMessage(2);
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						Map<String, Object> map = ParseJson.getParseJson()
								.parseIsSuccess(json);
						if ((Boolean) map.get("isSuccess")) {
							regionList = ParseJson.getParseJson().parseRegion(
									json);
							updateRegionData();
							tabView1.setList(childrens, groups,
									BusinessListAty.this, mselectPosition);
							regionIsFirstShow = false;
						}
					} 
				});
	}

	/**
	 * 请求分类
	 */
	private void requestKind() {
		AppContext.getHtmlUitls().xUtils(this, HttpMethod.GET,
				"getShopCategory", null, new HttpCallback() {
					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						// handler.sendEmptyMessage(2);
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						Map<String, Object> map = ParseJson.getParseJson()
								.parseIsSuccess(json);
						Log.i("tag", "----------成功了------");
						Log.i("tag", map.get("isSuccess")
								+ "---发送个人请求判断isSuccess------onBack------");
						if ((Boolean) map.get("isSuccess")) {
							kindNameList.clear();
							kindList = ParseJson.getParseJson().parseKindList(
									json);
							Log.i("tag", kindList.size()
									+ "----------regionList------");
							for (int i = 0; i < kindList.size(); i++) {
								kindNameList.add(new MenuItem(i, kindList
										.get(i).get("name")));
								// kindIDList.add(new
								// MenuItem(i,kindList.get(i).get("id")));
							}
							tabView2.setList(kindNameList);
							kindIsFirstShow = false;
						}
					}
				});
	}

	/**
	 * 排序规则
	 */
	private void requestOrderBy() {
		orderByList = new ArrayList<MenuItem>();
		MenuItem distanceItem = new MenuItem();
		distanceItem.setId(1);
		distanceItem.setText(ORDERBY.DISTANCE.getVlaue());
		distanceItem.setRegionID(ORDERBY.DISTANCE.getKey());
		orderByList.add(distanceItem);

		MenuItem discountItem = new MenuItem();
		discountItem.setId(2);
		discountItem.setText(ORDERBY.DISCOUNT.getVlaue());
		discountItem.setRegionID(ORDERBY.DISCOUNT.getKey());
		orderByList.add(discountItem);
		tabView3.setList(orderByList);
		orderByShow = false;
	}

	public enum ORDERBY {

		DISTANCE("distance", "距离排序"), DISCOUNT("discount", "优惠排序");
		private String key;
		private String vlaue;

		/**
		 * @param key
		 * @param vlaue
		 */
		ORDERBY(String key, String vlaue) {
			this.key = key;
			this.vlaue = vlaue;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getVlaue() {
			return vlaue;
		}

		public void setVlaue(String vlaue) {
			this.vlaue = vlaue;
		}

	}

	private void initTabView() {
		// 3个TAB
		tabView1 = new TabViewTwo(this, groups, childrens, -1, -1,
				R.drawable.choose_item_selected,
				R.drawable.choose_eara_item_selector,
				R.drawable.choose_item_right,
				R.drawable.choose_plate_item_selector);
		// tabView2 = new TabViewTwo(this, groups, childrens, -1, -1,
		// R.drawable.choose_item_selected,
		// R.drawable.choose_eara_item_selector,
		// R.drawable.choose_item_right,
		// R.drawable.choose_plate_item_selector);

		tabView2 = new TabViewOne(this, categoryItems, -1,
				R.drawable.choose_item_right,
				R.drawable.choose_eara_item_selector);
		tabView3 = new TabViewOne(this, orderByItems, -1,
				R.drawable.choose_item_right,
				R.drawable.choose_eara_item_selector);
	}

	// // 下拉刷新
	// @Override
	// public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
	// {
	// // TODO Auto-generated method stub
	// page = 1;
	// requestBusinessList();
	// }

	//
	// // 上拉加载
	// @Override
	// public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
	// // TODO Auto-generated method stub
	// page = page + 1;
	// requestBusinessList();
	// }

	/**
	 * 设置TAB标题
	 * 
	 * @param view
	 * @param showText
	 */
	private void onRefreshTab(View view, String showText) {

		expandTabView.onPressBack();
		int position = getPositon(view);
		if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
			expandTabView.setTitle(showText, position, 2);
		}
	}

	private int getPositon(View tView) {
		for (int i = 0; i < mViewArray.size(); i++) {
			if (mViewArray.get(i) == tView) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void onBackPressed() {
		if (!expandTabView.onPressBack()) {
			finish();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2
	 * #onPullDownToRefresh
	 * (com.handmark.pulltorefresh.library.PullToRefreshBase)
	 */
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		pageNo = 1;
		requestBusinessList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2
	 * #onPullUpToRefresh(com.handmark.pulltorefresh.library.PullToRefreshBase)
	 */
	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		pageNo++;
		requestBusinessList();
	}

}