package com.llg.privateproject.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjg.lcc.jsonparser.ParseJson;
import com.bjg.lcc.privateproject.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.help.Fgqqdb;
import com.llg.help.GetProgressBar;
import com.llg.help.MyFormat;
import com.llg.help.SaveString;
import com.llg.help.SetListHeigt;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.actvity.CommodityActivity;
import com.llg.privateproject.actvity.ProductDetailActivity;
import com.llg.privateproject.actvity.SearchActivity;
import com.llg.privateproject.actvity.ShopActivity;
import com.llg.privateproject.actvity.WebAty;
import com.llg.privateproject.actvity.WebLoginActivity;
import com.llg.privateproject.adapter.HomeFenleiAdapter;
import com.llg.privateproject.adapters.FGQQShopAdapter;
import com.llg.privateproject.adapters.GuangGaoJiFenAdapter;
import com.llg.privateproject.adapters.HuoDongZhuanTiAdapter;
import com.llg.privateproject.adapters.LinLinGouZhuTiGuanAdapter;
import com.llg.privateproject.adapters.MaybeYouLikerAdapter;
import com.llg.privateproject.adapters.PinPaiTuiJianAdapter;
import com.llg.privateproject.entities.FollowModel;
import com.llg.privateproject.entities.MaybeYouLike;
import com.llg.privateproject.entities.ShopModel;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.utils.CommonUtils;
import com.llg.privateproject.utils.Constants;
import com.llg.privateproject.utils.StringUtils;
import com.llg.privateproject.view.ImageCycleView;
import com.llg.privateproject.view.ImageCycleView.ImageCycleViewListener;
import com.llg.privateproject.view.LineGridView;
import com.llg.privateproject.view.MyGridView;
import com.llg.privateproject.view.OrderStatusDialog;
import com.smartandroid.sa.view.AutoLoading;
import com.zxing.activity.CaptureActivity;

/**
 * 首页(商城)
 * 
 * @author gongyibing
 * @version 1.0
 * @created 2015-05-07
 */
@SuppressLint("HandlerLeak")
public class HomeFragment extends BaseFragment implements OnItemClickListener {
	public static final String TAG = "HomeFragment";
	/** 首页数据 */
	private Map<String, Object> homePageMap;
	/** 首页广告图片集合 */
	private List<Map<String, Object>> ListImg = null;
	/** 首页分类数据配置 */
	private List<Map<String, Object>> listFenlei = null;

	/** 首页广告积分数据配置 */
	private List<Map<String, Object>> listGuangGaoJiFen = null;
	/** 首页猜你喜欢数据配置 */
	private List<Map<String, Object>> listCaiNiXiHuan = null;
	/** 首页活动专题数据配置 */
	private List<Map<String, Object>> listHuoDongZhuanTi = null;
	/** 首页品牌推荐数据配置 */
	private List<Map<String, Object>> listPinPaiTuiJian = null;
	/** 首页主题馆数据配置 */
	private List<Map<String, Object>> listLinLinGouZhuTiGuan = null;
	/** 首页店铺数据配置 */
	private List<MaybeYouLike> listDianpu = null;
	/** 首页商品数据配置 */
	private List<ShopModel> listCommodity = null;
	private AutoLoading autoLoading;
	private View mainView;
	private WindowManager windowManager;
	/** 下来刷新自定义控件 */
	@ViewInject(R.id.homelist_id)
	private PullToRefreshScrollView mListView;
	// private SingleLayoutListView mListView;
	/** 城市选择 */
	@ViewInject(R.id.head_city_id)
	private TextView homeCity;
	/** 商品搜索 */
	@ViewInject(R.id.home_search_id)
	private TextView editTextSerch;
	/** 广告栏布局文件 */
	@ViewInject(R.id.home_ll)
	private LinearLayout home_ll;
	/** 积分专区布局 */
	@ViewInject(R.id.home_mainaditem_root)
	private LinearLayout home_mainaditem_root;
	/** 猜你喜欢 */
	@ViewInject(R.id.llike)
	private LinearLayout llike;
	/** 活动专题 */
	@ViewInject(R.id.home_mainaditem_rootl)
	private LinearLayout home_mainaditem_rootl;
	/** 品牌推荐 */
	@ViewInject(R.id.lgood)
	private LinearLayout lgood;
	/** 主题馆 */
	@ViewInject(R.id.llife)
	private LinearLayout llife;
	/** 推荐店铺标题 */

	private TextView title_shop;
	/** 店铺 */
	// @ViewInject(R.id.lshop)
	private LinearLayout lshop;

	// /** 网络请求失败 */
	// @ViewInject(R.id.wangluoqingqiushibai)
	// private LinearLayout wangluoqingqiushibai;

	/** 扫一扫 */
	@ViewInject(R.id.home_sweep_id)
	private TextView home_Sweep;
	/** 广告轮播 */
	private ImageCycleView mAdView;
	/** 功能区 */
	private MyGridView gridView = null;

	/** 广告积分区 */
	private MyGridView guangGaoJiFenGridView = null;
	/** 猜你喜欢 */
	@ViewInject(R.id.mayby_youlike_lv)
	private GridView caiNixihuanGridView;
	/** 活动专题 */
	private MyGridView huoDongZhuanTiGridView = null;
	/** 品牌推荐 */
	private MyGridView pinPaiTuiJianGridView = null;
	/** 邻邻狗主题馆推荐 */
	private LineGridView linLinGouZhuTiGuanGridView = null;
	/** 商品列表 */
	private MyGridView gv_product = null;
	/** 店铺推荐布局 */
	private LinearLayout ll_dianpu = null;

	/** 全局Context */
	private AppContext appContext;
	private Activity mActivity = null;
	/** 分类专区 */
	private HomeFenleiAdapter adapter;

	/** 活动专题适配器 */
	private HuoDongZhuanTiAdapter huoDongZhuanTiAdapter;

	/** 猜你喜欢适配器 */
	private MaybeYouLikerAdapter caiNixihuanadapter;
	/** 品牌推荐适配器 */
	private PinPaiTuiJianAdapter pinPaiTuiJianAdapter;
	/** 邻邻狗主题馆适配器 */
	private LinLinGouZhuTiGuanAdapter linlinGouZhuTiGuanAdapter;

	private GuangGaoJiFenAdapter guangGaoJiFenAdapter;

	/** 商品列表适配器 */
	// private CommodityAdapter commodityAdapter;
	FGQQShopAdapter mShopAdapter;
	private SaveString saveString;
	private Fgqqdb fgqqdb;
	private SQLiteDatabase db;
	private ScrollView scrollView;
	private Runnable runnable;
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			GetProgressBar.dismissMyProgressBar();
			switch (msg.what) {
			case 4:// 返回商品数据成功
				mShopAdapter.notifyDataSetChanged();
				if (currentPage == 1) {
					mHandler.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							// sv.fullScroll(ScrollView.FOCUS_UP);
							// sv.scrollTo(0, 0);
							scrollView.smoothScrollTo(0, 0);
						}
					});

				}
				mListView.onRefreshComplete(); // 下拉刷新完成
				break;
			case Constants.SUCCESS:

				mAdView.setImageResources((List<Map<String, Object>>) msg.obj,
						imageCycleViewListener);
				mListView.onRefreshComplete(); // 下拉刷新完成
				break;
			case Constants.FAILED:
				setVisible();
				Toast.makeText(mActivity,
						getResources().getString(R.string.t_Connection),
						Toast.LENGTH_SHORT).show();
				mListView.onRefreshComplete(); // 下拉刷新完成
				break;
			case 3:

				if (home_ll != null && ListImg.size() > 0) {
					home_ll.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
							AppContext.getScreenWidth(), AppContext
									.getScreenWidth() * 2 / 5));
				}
				mAdView.setImageResources(ListImg, imageCycleViewListener);
				// adapter = new HomeFenleiAdapter(getActivity(), ListImg);
				// gridView.setAdapter(adapter);

				if (listFenlei == null) {
					listFenlei = new ArrayList<Map<String, Object>>();
				}
				if (homePageMap.get("mainFunclist") != null) {
					listFenlei = (List<Map<String, Object>>) homePageMap
							.get("mainFunclist");

				}
				if (listFenlei.size() > 0) {
					gridView.setVisibility(View.VISIBLE);
				} else {
					gridView.setVisibility(View.GONE);
				}
				adapter = new HomeFenleiAdapter(getActivity(), listFenlei);
				gridView.setAdapter(adapter);
				new SetListHeigt().setlistHeight(gridView);
				setVisible();
				// if(adapter!=null){
				// adapter.notifyDataSetChanged();
				// }
				if (homePageMap.get("adinfoList") != null) {

					listGuangGaoJiFen = (List<Map<String, Object>>) homePageMap
							.get("adinfoList");
				}
				if (listGuangGaoJiFen.size() > 0) {
					home_mainaditem_root.setVisibility(View.VISIBLE);
				} else {
					home_mainaditem_root.setVisibility(View.GONE);
				}
				guangGaoJiFenAdapter = new GuangGaoJiFenAdapter(getActivity(),
						listGuangGaoJiFen);// 广告积分适配器
				guangGaoJiFenGridView.setAdapter(guangGaoJiFenAdapter);
				// if(guangGaoJiFenAdapter!=null)
				// guangGaoJiFenAdapter.notifyDataSetChanged();
				if (listCaiNiXiHuan == null) {
					listCaiNiXiHuan = new ArrayList<Map<String, Object>>();
				}
				if (homePageMap
						.get("AppProdSpecList") != null) {

					listCaiNiXiHuan = (List<Map<String, Object>>) homePageMap
							.get("AppProdSpecList");
				}
				if (listCaiNiXiHuan.size() > 0) {
					llike.setVisibility(View.VISIBLE);
				} else {
					llike.setVisibility(View.GONE);
				}
				caiNixihuanGridView.setNumColumns(listCaiNiXiHuan.size());
				caiNixihuanadapter = new MaybeYouLikerAdapter(getActivity(),
						listCaiNiXiHuan, true);
				caiNixihuanGridView.setAdapter(caiNixihuanadapter);
				if (homePageMap
						.get("AdinfoActivityList") != null) {
					listHuoDongZhuanTi = (List<Map<String, Object>>) homePageMap
							.get("AdinfoActivityList");
				}
				if (listHuoDongZhuanTi.size() > 0) {
					home_mainaditem_rootl.setVisibility(View.VISIBLE);
				} else {
					home_mainaditem_rootl.setVisibility(View.GONE);

				}
				huoDongZhuanTiAdapter = new HuoDongZhuanTiAdapter(
						getActivity(), listHuoDongZhuanTi);
				huoDongZhuanTiGridView.setAdapter(huoDongZhuanTiAdapter);
				if (homePageMap
						.get("AdinfoBrandList") != null) {

					listPinPaiTuiJian = (List<Map<String, Object>>) homePageMap
							.get("AdinfoBrandList");

				}
				if (listPinPaiTuiJian != null && listPinPaiTuiJian.size() > 0) {
					lgood.setVisibility(View.VISIBLE);
				} else {
					lgood.setVisibility(View.GONE);
				}
				pinPaiTuiJianAdapter = new PinPaiTuiJianAdapter(getActivity(),
						listPinPaiTuiJian);
				pinPaiTuiJianGridView.setAdapter(pinPaiTuiJianAdapter);
				if (homePageMap
						.get("adinfoThemeList") != null) {
					listLinLinGouZhuTiGuan = (List<Map<String, Object>>) homePageMap
							.get("adinfoThemeList");

				}
				if (listLinLinGouZhuTiGuan.size() > 0) {
					llife.setVisibility(View.VISIBLE);
				} else {
					llife.setVisibility(View.GONE);

				}
				linlinGouZhuTiGuanAdapter = new LinLinGouZhuTiGuanAdapter(
						getActivity(), listLinLinGouZhuTiGuan);
				linLinGouZhuTiGuanGridView
						.setAdapter(linlinGouZhuTiGuanAdapter);
				if (homePageMap
						.get("adinfoShopList") != null) {
					testDianpu((List<Map<String, Object>>) homePageMap
							.get("adinfoShopList"));
					lshop.setVisibility(View.VISIBLE);
				} else {
					lshop.setVisibility(View.GONE);
				}
				setVisible();
				break;
			}
		}
	};

	public static HomeFragment newInstance() {
		HomeFragment homeFragment = new HomeFragment();
		return homeFragment;
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
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		// 在使用注解绑定控件的时候，一定记得在onCreate中调用ViewUtils.inject(this);
		ViewUtils.inject(this, view);

		// homeCity.setText(AppContext.myCity);
		windowManager = (WindowManager) mActivity
				.getSystemService(Context.WINDOW_SERVICE);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initViews(view);
		initDisplay();

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	/** 获取首页配置界面 */
	private void initViews(View view) {
		fgqqdb = new Fgqqdb();

		if (homePageMap == null) {
			homePageMap = new HashMap<String, Object>();
		}

		ListImg = new ArrayList<Map<String, Object>>();
		if (listFenlei == null) {
			listFenlei = new ArrayList<Map<String, Object>>();
		}

		if (listGuangGaoJiFen == null) {
			listGuangGaoJiFen = new ArrayList<Map<String, Object>>();
		}

		if (listCaiNiXiHuan == null) {
			listCaiNiXiHuan = new ArrayList<Map<String, Object>>();
		}
		if (listHuoDongZhuanTi == null) {
			listHuoDongZhuanTi = new ArrayList<Map<String, Object>>();
		}

		listLinLinGouZhuTiGuan = new ArrayList<Map<String, Object>>();
		listDianpu = new ArrayList<MaybeYouLike>();
		listCommodity = new ArrayList<ShopModel>();
		editTextSerch.setHintTextColor(getActivity().getResources().getColor(
				R.color.black3));
		CommonUtils.hideSoftKeybord(getActivity());

		/** 获取首页配置界面 */
		mainView = mActivity.getLayoutInflater().inflate(R.layout.home_middle,
				null);

		setVisible();
//		hint();
		mListView.addView(mainView);
		scrollView = mListView.getRefreshableView();
		mListView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				// 执行刷新函数
				getCommodityData();
			}
		});
		autoLoading = new AutoLoading(mActivity, mainView);
		autoLoading.setClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				test();
			}
		});
		mAdView = (ImageCycleView) mainView.findViewById(R.id.slideshowView);// 广告栏
		home_ll = (LinearLayout) mainView.findViewById(R.id.home_ll);
		gridView = (MyGridView) mainView.findViewById(R.id.GridView_id);// 分类
		gridView.setOnItemClickListener(this);

		home_mainaditem_root = (LinearLayout) mainView
				.findViewById(R.id.home_mainaditem_root);
		llike = (LinearLayout) mainView.findViewById(R.id.llike);
		home_mainaditem_rootl = (LinearLayout) mainView
				.findViewById(R.id.home_mainaditem_rootl);
		lgood = (LinearLayout) mainView.findViewById(R.id.lgood);
		llife = (LinearLayout) mainView.findViewById(R.id.llife);
		lshop = (LinearLayout) mainView.findViewById(R.id.lshop);
		title_shop = (TextView) mainView.findViewById(R.id.title_shop);

		guangGaoJiFenGridView = (MyGridView) mainView
				.findViewById(R.id.guanggaojifen_gv);// 广告积分、
		guangGaoJiFenGridView.setOnItemClickListener(this);

		caiNixihuanGridView = (GridView) mainView
				.findViewById(R.id.mayby_youlike_lv);// 猜你喜欢
		caiNixihuanGridView.setOnItemClickListener(this);

		huoDongZhuanTiGridView = (MyGridView) mainView
				.findViewById(R.id.huodongzhuanti_gv);// 活动专题
		huoDongZhuanTiGridView.setOnItemClickListener(this);

		pinPaiTuiJianGridView = (MyGridView) mainView
				.findViewById(R.id.pinpiantuijian_gv);// 品牌推荐
		pinPaiTuiJianGridView.setOnItemClickListener(this);

		linLinGouZhuTiGuanGridView = (LineGridView) mainView
				.findViewById(R.id.linlingouzhutiguan_gv);// 主题馆
		linLinGouZhuTiGuanGridView.setOnItemClickListener(this);

		ll_dianpu = (LinearLayout) mainView.findViewById(R.id.ll_dianpu);// 店铺推荐

		gv_product = (MyGridView) mainView.findViewById(R.id.gv_allproduct);// 商品列表
		gv_product.setOnItemClickListener(this);

		// commodityAdapter = new ShopHuodongAdapter(getActivity(),
		// listCommodity);
		// commodityAdapter = new CommodityAdapter(getActivity(),
		// listCommodity,false);
		mShopAdapter = new FGQQShopAdapter(getActivity(), listCommodity);
		gv_product.setAdapter(mShopAdapter);
		ViewGroup.LayoutParams params = caiNixihuanGridView.getLayoutParams();
		int itemWidth = AppContext.getScreenWidth() / 4;
		int spacingWidth = 2;

		params.width = itemWidth * listCaiNiXiHuan.size()
				+ (listCaiNiXiHuan.size() - 1) * spacingWidth;
		caiNixihuanGridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH); // 设置为禁止拉伸模式
		caiNixihuanGridView.setNumColumns(listCaiNiXiHuan.size());
		caiNixihuanGridView.setHorizontalSpacing(spacingWidth);
		caiNixihuanGridView.setColumnWidth(itemWidth);
		caiNixihuanGridView.setLayoutParams(params);

		try {

			if (fgqqdb.selectData("a").length() > 10) {
				JSONObject obj = new JSONObject(fgqqdb.selectData("a"));

				getdata(obj);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/** 入驻商品汇,特惠商家提示 */
	private void hint() {
		final OrderStatusDialog dialog = new OrderStatusDialog(mActivity, 13);
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

	/** 网络验证数据请求 */
	private void initDisplay() {

		appContext = (AppContext) mActivity.getApplication();
		if (appContext.isNetworkConnected()) {
			test();
			getCommodityData();
		} else {
			// autoLoading.showInternetOffLayout();
			Message _Msg = mHandler.obtainMessage(Constants.FAILED, ListImg);
			mHandler.sendMessage(_Msg);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public String getFragmentName() {
		return TAG;
	}

	/** 查询当前页 默认为1 */
	private int currentPage = 0;
	/** 总页码默认为3 */
	private int totalPages = 3;

	/** 获取分类产品 */

	private void getCommodityData() {
		if (currentPage > totalPages) {
			toast("暂无更多商品");
			mListView.onRefreshComplete();
			return;
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				RequestParams params = new RequestParams();
				// params.addQueryStringParameter("categoryId", parentId);

				params.addQueryStringParameter("sortby", "" + 0);
				params.addQueryStringParameter("categoryId", "");
				params.addQueryStringParameter("pageNo",
						String.valueOf(++currentPage));
				params.addQueryStringParameter("time",
						"" + System.currentTimeMillis());
				AppContext.getHtmlUitls().xUtilsm(mActivity, HttpMethod.GET,
						Constants.APP_CHILDS_MERCHANDISE, params,
						new HttpCallback() {
							@Override
							public void onBack(JSONObject json) {
								/** 获取分类商品数据 */
								Log.d("my", "网络:" + json);
								autoLoading.hideAll();
								try {
									if (json.getBoolean("success")) {
										// totalPages=json.getJSONObject("obj").getInt("totalPages");
										// ParseJson
										// .getParseJson()
										// .setDataList(
										// "result",
										// json.getJSONObject("obj"),
										// listCommodity);
										String followJson = json.getJSONObject(
												"obj").getString("result");
										ShopModel model = new ShopModel();
										listCommodity.addAll(model
												.parseJson(followJson));
										mHandler.sendEmptyMessage(4);

									}
								} catch (JSONException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

								//
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
		}).start();
	}

	/** 首页测试数据 */
	private void test() {
		autoLoading.showLoadingLayout();
		RequestParams params = new RequestParams();

		try {
			params.addQueryStringParameter("version", String.valueOf(mActivity
					.getPackageManager().getPackageInfo(
							mActivity.getPackageName(), 0).versionCode));
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.addHeader("X-Requested-With", "XMLHttpRequest");
		AppContext.getHtmlUitls().xUtils(getActivity(), HttpMethod.GET,
				"homepage", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						// Log.d("my", "onErrortest:"+msg);
						mHandler.sendEmptyMessage(Constants.FAILED);
						autoLoading.showExceptionLayout();
						Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT)
								.show();
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						// if(saveString==null){
						// saveString=new SaveString();
						// }
						// saveString.saveAsFileWriter(json.toString());

						getdata(json);
						fgqqdb.update(json.toString(), "a");

					}

				});
	}

	/** 解析网络数据 */
	private void getdata(JSONObject json) {
		if (json.length() < 5) {
			return;
		}
		autoLoading.hideAll();
		if (homePageMap == null) {
			homePageMap = new HashMap<String, Object>();
		}
		ParseJson.getParseJson().gethomePageObject(json, homePageMap);
		if (homePageMap.get("AdinfoActivityList") != null) {
			listHuoDongZhuanTi = (List<Map<String, Object>>) homePageMap
					.get("AdinfoActivityList");
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (homePageMap.get("AdinfoFlashviewList") != null) {

			list = (List<Map<String, Object>>) homePageMap
					.get("AdinfoFlashviewList");
		}
		Map<String, Object> adObject = null;
		for (int i = 0; i < list.size(); i++) {
			ListImg.add(list.get(i));
		}
		mHandler.sendEmptyMessage(3);
	}

	/** 店铺测试数据 */
	private void testDianpu(List<Map<String, Object>> list) {
		MaybeYouLike youLike = null;
		View v = null;
		ImageView iv = null;
		LinearLayout ll = null;
		TextView name = null;
		if (list.size() < 1) {
			lshop.setVisibility(View.GONE);
			title_shop.setVisibility(View.GONE);
		} else {
			title_shop.setVisibility(View.VISIBLE);
			lshop.setVisibility(View.VISIBLE);
		}
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			youLike = new MaybeYouLike(map.get("content").toString(), map.get(
					"id").toString(), map.get("source").toString(), map.get(
					"objectId").toString(), map.get("name").toString(), map
					.get("img").toString(), map.get("objectType").toString(),
					map.get("url").toString());
			listDianpu.add(youLike);
			v = View.inflate(getActivity(), R.layout.home_dianpu_item, null);
			android.widget.RelativeLayout.LayoutParams params = new android.widget.RelativeLayout.LayoutParams(
					AppContext.getScreenWidth() / 4,
					AppContext.getScreenWidth() * 3 / 16);
			v.setLayoutParams(params);
			ll = (LinearLayout) v.findViewById(R.id.ll);
			if (i == 0) {
				ll.setPadding(1, 1, 1, 1);
			} else {
				ll.setPadding(0, 1, 1, 1);
			}
			iv = (ImageView) v.findViewById(R.id.iv);
			android.widget.LinearLayout.LayoutParams lp = new android.widget.LinearLayout.LayoutParams(
					AppContext.getScreenWidth() / 4,
					AppContext.getScreenWidth() / 3);

			iv.setLayoutParams(lp);
			name = (TextView) v.findViewById(R.id.name);
			MyFormat.setBitmap(getActivity(), iv, youLike.getImg(),
					AppContext.getScreenWidth() / 4,
					AppContext.getScreenWidth() / 3);
			name.setText(youLike.getName());
			v.setTag(youLike.getObjectId());
			ll_dianpu.addView(v);
			v.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					if (appContext.isNetworkConnected()) {
						Intent intent = new Intent(getActivity(),
								ShopActivity.class);
						intent.putExtra("shopId", v.getTag().toString());
						startActivity(intent);
					} else {

						Toast.makeText(mActivity, "网络异常", Toast.LENGTH_SHORT)
								.show();
					}
				}
			});
		}

	}

	/** 广告轮播回调事件 */
	private ImageCycleViewListener imageCycleViewListener = new ImageCycleViewListener() {
		@SuppressWarnings("deprecation")
		public void displayImage(String imageURL, ImageView imageView) {
			LayoutParams lp = imageView.getLayoutParams();
			int width = windowManager.getDefaultDisplay().getWidth();
			lp.width = width * 2 / 5;
			lp.height = width * 2 / 5 * 4 / 5;
			imageView.setLayoutParams(lp);

			imageView.setScaleType(ScaleType.FIT_XY);
			/** 加载网络图片 */
			if (StringUtils.isEmpty(imageURL)) {
				// imageView.setImageResource(R.drawable.default_products_);
			} else {

				MyFormat.setBitmap(getActivity(), imageView, imageURL,
						lp.width, lp.height);
			}
		}

		public void onImageClick(List<Map<String, Object>> list, int position,
				View imageView) {
			/** 广告id */
			String objectId = list.get(position).get("objectId").toString()
					.trim();
			String objectType = list.get(position).get("objectType").toString()
					.trim();
			toSomeWhere(objectType, objectId);

		}
	};

	@OnClick({ R.id.head_city_id, R.id.home_search_id, R.id.home_sweep_id })
	public void clickMethod(View v) {
		switch (v.getId()) {
		/** 城市选择 */
		case R.id.head_city_id:
			mActivity.finish();

			break;
		/** 跳转到搜索框 */
		case R.id.home_search_id:
			getActivity().startActivity(
					new Intent(getActivity(), SearchActivity.class));
			break;
		/** 扫一扫 */
		case R.id.home_sweep_id:
			Intent intent = new Intent(getActivity(), CaptureActivity.class);
			getActivity().startActivity(intent);

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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		GetProgressBar.dismissMyProgressBar();
		parent.getAdapter().getItem(position);
		if (!appContext.isNetworkConnected()) {
			toast(R.string.t_Connection);
			return;
		}
		switch (parent.getId()) {
		case R.id.guanggaojifen_gv:// 广告积分
			getJifen(position);

			break;
		case R.id.mayby_youlike_lv:// 猜你喜欢
			String shopId = listCaiNiXiHuan.get(position).get("shopId")
					.toString();
			// String objectId=
			// listCaiNiXiHuan.get(position).get("objectId").toString();
			toSomeWhere("1", shopId);
			break;
		case R.id.huodongzhuanti_gv:// 活动专题

			objectType = listPinPaiTuiJian.get(position).get("objectType")
					.toString();
			objectId = listPinPaiTuiJian.get(position).get("objectId")
					.toString();
			toSomeWhere(objectType, objectId);
			break;
		case R.id.pinpiantuijian_gv:// 品牌推荐

			objectType = listPinPaiTuiJian.get(position).get("objectType")
					.toString();
			objectId = listPinPaiTuiJian.get(position).get("objectId")
					.toString();
			toSomeWhere(objectType, objectId);
			break;
		case R.id.linlingouzhutiguan_gv:// 主题馆

			objectType = listLinLinGouZhuTiGuan.get(position).get("objectType")
					.toString();
			objectId = listLinLinGouZhuTiGuan.get(position).get("objectId")
					.toString();
			toSomeWhere(objectType, objectId);
			break;
		case R.id.GridView_id:// 功能搜索板块
			String categoryId = listFenlei.get(position).get("categoryId")
					.toString();
			String name = listFenlei.get(position).get("name").toString();

			Intent intent = new Intent(mActivity, CommodityActivity.class);
			intent.putExtra("parentId", categoryId);
			intent.putExtra("name", name);
			mActivity.startActivity(intent);
			break;
		case R.id.gv_allproduct:// 商品
			// String specId = listCommodity.get(position).get("specId")
			// .toString();
			String specId = listCommodity.get(position).getSpecId();
			Intent intent1 = new Intent(mActivity, ProductDetailActivity.class);
			intent1.putExtra("prodspecId", specId);
			mActivity.startActivity(intent1);
			break;
		}
	}

	/** 获取积分 */
	private void getJifen(final int position) {
		String method = "m/co/getco";
		RequestParams params = new RequestParams();
		Map<String, Object> map = listGuangGaoJiFen
				.get(position);

		objectId = map.get("objectId").toString();
		objectType = map.get("objectType").toString();
		params.addBodyParameter("adInfoBeanId", map.get("objectId").toString());
		params.addQueryStringParameter("access_token", UserInformation.getAccess_token());
		AppContext.getHtmlUitls().xUtilsm(getActivity(), HttpMethod.POST,
				method, params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						Toast.makeText(getActivity(), "登录可获取酷币",
								Toast.LENGTH_SHORT).show();
						getActivity().startActivity(
								new Intent(getActivity(),
										WebLoginActivity.class));
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						try {
							if (json.getBoolean("success")) {

								Toast.makeText(getActivity(), "积分获取成功",
										Toast.LENGTH_SHORT).show();
								toSomeWhere(objectType, objectId);
							} else if (json.get(MyFormat.errorCode) != null
									&& json.getString(MyFormat.errorCode)
											.equals(MyFormat.NOT_LOGIN)) {
								setRefreshListtener(new Refresh() {

									@Override
									public void refreshRequst(
											String access_token) {
										// TODO Auto-generated method stub
										getJifen(position);
									}
								});
								RefeshToken();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	/** 网页加载失败时显示加载失败提示 */
	void setVisible() {
		if (listFenlei.size() > 0) {
			// wangluoqingqiushibai.setVisibility(View.GONE);
			mListView.setVisibility(View.VISIBLE);
			// Log.d("my", "************************************");
		} else {
			// Log.d("my", "***###################*******************");
			// wangluoqingqiushibai.setVisibility(View.VISIBLE);
			mListView.setVisibility(View.GONE);
		}
	}

	String objectType;
	String objectId;

	/**
	 * 到店铺/商品也/专题/URL等
	 * 
	 * */
	private void toSomeWhere(String objectType, String objectId) {

		if (objectType.equals("1") && objectId != null
				&& !objectId.equals("null") && objectId.length() > 0) {// 跳转到店铺
			Intent intent = new Intent(mActivity, ShopActivity.class);
			intent.putExtra("shopId", objectId);

			startActivity(intent);
		} else if (objectType.equals("2") && objectId != null
				&& !objectId.equals("null") && objectId.length() > 0) {// 跳转到商品
			Intent intent = new Intent(mActivity, ProductDetailActivity.class);
			intent.putExtra("prodspecId", objectId);
			startActivity(intent);
		} else if (objectType.equals("3") && objectId != null
				&& !objectId.equals("null") && objectId.length() > 0) {// 专题

		} else if (objectType.equals("4") && objectId != null
				&& !objectId.equals("null") && objectId.length() > 0) {// url
			Intent to_more = new Intent(mActivity, WebAty.class);

			to_more.putExtra("prodSpecId", objectId);
			startActivity(to_more);
		} else {// 其他

		}
	}
}
