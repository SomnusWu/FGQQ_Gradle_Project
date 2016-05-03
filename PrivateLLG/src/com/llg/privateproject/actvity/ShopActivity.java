package com.llg.privateproject.actvity;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjg.lcc.alipay.pay.PayActivity;
import com.bjg.lcc.privateproject.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.help.GetProgressBar;
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.adapters.ShopHuodongAdapter;
import com.llg.privateproject.adapters.ShopProductHotAdapter;
import com.llg.privateproject.adapters.YouhuiquanAdapter;
import com.llg.privateproject.entities.ActivityBean;
import com.llg.privateproject.entities.Consumeentity;
import com.llg.privateproject.entities.SpotOrderMsgBean;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.utils.Constants;
import com.llg.privateproject.utils.LogManag;
import com.llg.privateproject.utils.StringUtils;
import com.llg.privateproject.utils.ValidateUtils;
import com.llg.privateproject.view.ActivityView;
import com.llg.privateproject.view.DialogDiscount;
import com.llg.privateproject.view.Gerenziliao_Dialog;
import com.llg.privateproject.view.Gerenziliao_Dialog.DianpufenleiListener;
import com.llg.privateproject.view.ProductDetailSizeDialog;
import com.llg.privateproject.view.ShopFrameDialog;
import com.smartandroid.sa.eventbus.EventBus;
import com.smartandroid.sa.tag.helper.StringUtil;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;
import com.zxing.activity.CaptureActivity;

/**
 * 店铺界面 yh 2015.07.13
 * 
 * */
public class ShopActivity extends BaseActivity implements
		OnCheckedChangeListener, DianpufenleiListener,
		android.widget.CompoundButton.OnCheckedChangeListener, TextWatcher {
	/** 返回 */
	@ViewInject(R.id.turn)
	private ImageView turn;
	/** 标题 */
	@ViewInject(R.id.head_tilte)
	private TextView head_tilte;
	/** 优惠说明 */
	@ViewInject(R.id.explain)
	private TextView explain;
	/** 刷新 */
	@ViewInject(R.id.fresh)
	private ImageView fresh;
	/** 更多 */
	@ViewInject(R.id.ellipsis)
	private ImageView ellipsis;
	/** 宝贝分类、店铺分享背景 */
	@ViewInject(R.id.ll_fenlei_share)
	private LinearLayout ll_fenlei_share;
	/** 宝贝分类、店铺分享 */
	@ViewInject(R.id.ll_share)
	private LinearLayout ll_share;
	/** 宝贝分类 */
	@ViewInject(R.id.baobeifenlei)
	private TextView baobeifenlei;
	/** 显示实付金额 */
	@ViewInject(R.id.show_can_user_co)
	private TextView show_can_user_co;
	/** 宝贝分类对话框 */
	private Gerenziliao_Dialog fenleidialog;
	/** 店铺分享 */
	@ViewInject(R.id.share)
	private TextView share;
	/** 店铺名称 */
	@ViewInject(R.id.shopname)
	private TextView shopname;
	/** 店铺头像 */
	@ViewInject(R.id.shop_head)
	private ImageView shop_head;
	/** 关注店铺按钮 */
	@ViewInject(R.id.care)
	private TextView care;
	/** 店铺相册 */
	@ViewInject(R.id.shopimg)
	private TextView shopimg;
	/** 现场消费按钮 */
	@ViewInject(R.id.tv_pay)
	private TextView tv_pay;
	/** 现场消费按钮 */
	@ViewInject(R.id.cb_h)
	private CheckBox cb_s;
	/** 显示支付金额 */
	@ViewInject(R.id.pay_price)
	private TextView pay_price;
	/** 输入消费金额 */
	@ViewInject(R.id.et_price)
	private EditText et_price;

	/** PullToRefreshScrollView */
	@ViewInject(R.id.homelist_id)
	private PullToRefreshScrollView homelist_id;
	/** 店铺背景 */
	@ViewInject(R.id.rl_shop_background)
	private RelativeLayout rl_shop_background;
	/** 店铺背景 */
	@ViewInject(R.id.iv_shop_background)
	private ImageView iv_shop_background;
	/** 首页、全部宝贝、店铺宝贝页面切换 */
	@ViewInject(R.id.rg)
	private RadioGroup rg;
	/** 全部宝贝 */
	@ViewInject(R.id.rb_allproduct)
	private RadioButton rb_allproduct;
	/** 首页 */
	@ViewInject(R.id.rb_home)
	private RadioButton rb_home;
	/** 首页布局文件 */
	@ViewInject(R.id.ll_home)
	private LinearLayout ll_home;
	/** 优惠比例 */
	@ViewInject(R.id.ll_hui)
	private LinearLayout ll_hui;
	/** 现场消费 */
	@ViewInject(R.id.ll_cash)
	private LinearLayout ll_allproduct;
	/** 指示剂 */
	@ViewInject(R.id.indictor)
	private LinearLayout indictor;
	/** 指示剂宽度位置 */
	private int position = 0;
	/** 设置单选按钮选择 */
	private int position1 = 0;
	/** 商品可抵co个数 */
	private int co = 0;
	/** 店铺简介页面 */
	private ProductDetailSizeDialog dialog;
	/** 指示剂宽度 */
	private int wid = AppContext.getScreenWidth() / 3;
	private String shopId = "";
	/** 可用co币占原价比例 */
	private int spotCoPercent = 0;
	/** 店铺返回co币比例 */
	private int spotCoBackPercent = 0;
	/*** 优惠券列表 */
	List<Map<String, Object>> list;
	/*** 店铺相册列表 */
	List<String> listImg;
	/** 店铺基本信息 */
	Map<String, Object> map;
	/** 优惠券gridview */
	@ViewInject(R.id.gv_youhuiquan)
	private GridView gv_youhuiquan;
	/** 优惠券适配器 */
	YouhuiquanAdapter youhuiquanAdapter;

	/** 默认使用co币 */
	private int userCo = 1;
	/** 活动列表适配器 */
	ShopHuodongAdapter tuijianAdapter;

	/** 爆款推荐商品gridview */
	@ViewInject(R.id.gv_hot)
	private GridView gv_hot;
	/*** 爆款推荐商品列表 */
	List<Map<String, Object>> list_hot;
	/** 爆款推荐商品列表适配器 */
	ShopProductHotAdapter shopProcuctAdapter;

	/** 全部商品gridview */
	// @ViewInject(R.id.gv_allproduct)
	// private GridView gv_allproduct;
	// /*** 全部商品列表 */
	// List<Map<String, Object>> list_allproduct;
	// /** 全部商品列表适配器 */
	// ShopProductHotAdapter shop_all_ProcuctAdapter;
	/** 当前页 */
	private int currentPage = 1;
	/** 我的co币 */
	private int myco = 0;
	/** 消费金额 */
	private float price = 0;
	/** 购买价格 */
	private float buycoprice = 0;
	/** 支付价格 */
	private float payprice = 0;
	private String code = "";
	private String name = "";
	private String pay = "";
	private String id = "";
	private ShopFrameDialog shopFrameDialog;
	AppContext appContext;

	SpotOrderMsgBean bean;
	/***/
	@ViewInject(R.id.layout_original_price)
	private LinearLayout layout_original_price;

	@ViewInject(R.id.layout_cool_deductible)
	private LinearLayout layout_cool_deductible;

	@ViewInject(R.id.layout_cool_complement)
	private LinearLayout layout_cool_complement;

	@ViewInject(R.id.layout_cool_return)
	private LinearLayout layout_cool_return;

	/*****/
	@ViewInject(R.id.tv_original_price)
	private TextView tv_original_price;

	@ViewInject(R.id.tv_cool_deductible)
	private TextView tv_cool_deductible;

	@ViewInject(R.id.tv_cool_complement)
	private TextView tv_cool_complement;

	@ViewInject(R.id.tv_cool_return)
	private TextView tv_cool_return;

	@ViewInject(R.id.tv_sum_price)
	private TextView tv_sum_price;
	/****/
	@ViewInject(R.id.view_activitys)
	private ActivityView view_activitys;

	List<ActivityBean> mBeanList;

	@ViewInject(R.id.tv_ok)
	private TextView tv_ok;
	@ViewInject(R.id.price_layout)
	private LinearLayout price_layout;

	@ViewInject(R.id.tv_discount)
	private TextView tv_discount;

	/** 根据标签来判断是哪个页面的跳转 */
	private String push_tag = "";

	private String unSalePrice = "";// 不享受优惠

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			GetProgressBar.dismissMyProgressBar();
			switch (msg.what) {
			case 0:// 加载失败
				Toast.makeText(ShopActivity.this, "链接异常", Toast.LENGTH_SHORT)
						.show();
				break;
			case 1:// 店铺信息加载成功
				if (map != null) {
					shopname.setText(map.get("name").toString());
					head_tilte.setText(map.get("name").toString());

					android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) shop_head
							.getLayoutParams();
					params.width = AppContext.getScreenWidth() / 20 * 3;
					params.height = AppContext.getScreenWidth() * 13 / 100;
					MyFormat.setBitmap(ShopActivity.this, shop_head,
							map.get("logo").toString(), params.width,
							params.height);
				}

				// if (mBeanList.size() > 0) {
				view_activitys.setData(mBeanList);
				// }
				getProduct(Constants.SHOPPRODUCT, shopId, currentPage, 1);
				homelist_id.getRefreshableView().smoothScrollTo(0, 0);// 回到顶部
				if (listImg.size() > 0) {
					shopimg.setVisibility(View.VISIBLE);
				} else {
					shopimg.setVisibility(View.GONE);
				}
				if (spotCoPercent > 0 && spotCoBackPercent > 0) {
					// ll_hui.setVisibility(View.VISIBLE);
					explain.setText("酷币可抵比例:" + spotCoPercent + "%,"
							+ "店铺返酷币比例" + spotCoBackPercent + "%");
				} else if (spotCoPercent > 0 && spotCoBackPercent <= 0) {
					// ll_hui.setVisibility(View.VISIBLE);
					explain.setText("酷可低比例:" + spotCoPercent + "%");
				} else if (spotCoPercent <= 0 && spotCoBackPercent > 0) {
					// ll_hui.setVisibility(View.VISIBLE);
					explain.setText("店铺返酷比例" + spotCoBackPercent + "%");
				} else {
					ll_hui.setVisibility(View.GONE);
				}
				break;
			case 2:// 我的co币查询成功
				show_can_user_co.setText("我有酷币:" + myco + "个");
				break;
			case 3:// 支付
				Intent intent = new Intent(ShopActivity.this, PayActivity.class);
				intent.putExtra("price", String.valueOf(pay));
				intent.putExtra("id", id);
				intent.putExtra("name", name);
				// code=""+System.currentTimeMillis();
				intent.putExtra("code", code);
				Log.d("my", "code:" + code);
				Log.d("my", "price:" + String.valueOf(pay));
				ShopActivity.this.startActivity(intent);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop);
		ViewUtils.inject(this);
		ll_hui.setVisibility(View.GONE);
		init();
		if (null != getIntent()) {
			push_tag = getIntent().getStringExtra("push");
			if (null != push_tag && push_tag.equals("BusinessListAty")) {// 如果是特惠商家过来的
				// 默认选中现场消费
				rb_allproduct.performClick();
				// rb_allproduct.setChecked(true);
			}
		}

		EventBus.getDefault().register(this);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.llg.privateproject.fragment.BaseActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	/**
	 * 如果支付成功就跳转评价
	 * 
	 * @param isFlag
	 */
	public void onEventMainThread(String isFlag) {
		LogManag.d("ShopActivity", "收到支付返回..");
		if (!StringUtils.isEmpty(isFlag) && isFlag.equals("1")) {
			Consumeentity cousume = new Consumeentity();
			cousume.SHOP_NAME = name;
			cousume.ORIG_PRICE = bean.getOrigPrice();
			cousume.PRICE = bean.getPayPrice();
			cousume.CO = bean.getBuyCoPrice();
			cousume.BACK_CO = bean.getBackCo();
			cousume.CUS_IMG = map.get("logo").toString();
			cousume.ID = id;
			cousume.SHOP_ID = shopId;
			cousume.isPayID = true;

			Intent intent = new Intent();
			intent.putExtra("Consumeentity", cousume);
			intent.setClass(ShopActivity.this, FGQQShopEvaluateActivity.class);
			startActivity(intent);
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		comehere(shopId);

		// Log.d("my", "click.getContent()" + click.getContent());
		// 序列化后:

		// #Intent;action=cn.eben.bookshelf.VIEW;launchFlags=0x10000000;end
	}

	private void comehere(String shopId) {
		XGPushClickedResult click = XGPushManager.onActivityStarted(this);
		if (click != null) {
			Log.d("my", "title" + click.getTitle());
			Log.d("my", "click.getContent()" + click.getContent());
			Log.d("my", "click.getCustomContent()" + click.getCustomContent());
			Log.d("my", "click.getActivityName()" + click.getActivityName());
			JSONObject obj;
			try {
				obj = new JSONObject(click.getCustomContent());
				if (obj.has("objectId") && obj.get("objectId") != null) {
					shopId = obj.get("objectId").toString();
				} else {
					finish();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Intent intent = new Intent("cn.eben.bookshelf.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		String intnetUri = intent.toURI();
		Log.d("my", "intnetUri" + intnetUri);
		Log.d("my", "getAction" + new Intent(this, ProductDetailActivity.class));
		Log.d("my",
				"getAction"
						+ new Intent(this, ProductDetailActivity.class)
								.getAction());
		Log.d("my",
				"intnetUri"
						+ new Intent(this, ProductDetailActivity.class).toURI());
		try {
			Log.d("my",
					"i"
							+ Intent.parseUri(new Intent(this,
									ProductDetailActivity.class).toURI(), 0));
			Log.d("my", "i" + Intent.parseUri(intnetUri, 0));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** 初始化控件 */
	private void init() {

		listImg = new ArrayList<String>();
		fresh.setVisibility(View.INVISIBLE);
		ellipsis.setBackgroundResource(R.drawable.home_scan);
		if (listImg.size() > 0) {
			shopimg.setVisibility(View.VISIBLE);
		} else {
			shopimg.setVisibility(View.GONE);
		}
		appContext = (AppContext) getApplication();
		ll_allproduct.setVisibility(View.INVISIBLE);
		shopId = getIntent().getStringExtra("shopId");

		et_price.addTextChangedListener(this);
		android.widget.LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) rl_shop_background
				.getLayoutParams();
		lp.width = AppContext.getScreenWidth();
		lp.height = AppContext.getScreenWidth() * 2 / 7;

		rl_shop_background.setLayoutParams(lp);
		iv_shop_background.setBackgroundResource(R.drawable.bkg);

		android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) shop_head
				.getLayoutParams();
		params.width = AppContext.getScreenWidth() / 20 * 3;
		params.height = AppContext.getScreenWidth() * 13 / 100;
		shop_head.setLayoutParams(params);

		android.widget.LinearLayout.LayoutParams flp = (android.widget.LinearLayout.LayoutParams) ll_share
				.getLayoutParams();
		flp.width = AppContext.getScreenWidth() / 7 * 2;
		ll_share.setLayoutParams(flp);

		wid = AppContext.getScreenWidth() / 3;
		setIndictor(position);

		// 优惠券
		list = new ArrayList<Map<String, Object>>();
		youhuiquanAdapter = new YouhuiquanAdapter(this, list);
		gv_youhuiquan.setAdapter(youhuiquanAdapter);
		// 推荐
		// list_tuijian = new ArrayList<Map<String, Object>>();
		// tuijianAdapter = new ShopHuodongAdapter(this, list_tuijian);
		// gv_tuijian.setAdapter(tuijianAdapter);
		// 商品列表
		list_hot = new ArrayList<Map<String, Object>>();
		shopProcuctAdapter = new ShopProductHotAdapter(this, list_hot, false);
		gv_hot.setAdapter(shopProcuctAdapter);

		homelist_id.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				// TODO Auto-generated method stub
				setIndictor(0);
				getProduct(Constants.SHOPPRODUCT, shopId, currentPage, 0);
				// ll_home.setVisibility(View.GONE);
				// ll_allproduct.setVisibility(View.VISIBLE);
				// rb_allproduct.setChecked(true);
				homelist_id.onRefreshComplete();
			}
		});
		cb_s.setOnCheckedChangeListener(this);
		rg.setOnCheckedChangeListener(this);
		// testYouhuiquan();
		// testTuijian();
		// testProductHot();
		checkNet();
		setClickListenerMethod();

	}

	// private boolean flag = true;
	DialogDiscount dialogDiscount;

	private void setClickListenerMethod() {
		/**
		 * 不享受优惠金额 按钮
		 */
		tv_discount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String et_priceStr = et_price.getText().toString();
				if (StringUtils.isEmpty(et_priceStr)) {
					toast("请先输入消费金额");
					return;
				}
				if (dialogDiscount == null) {
					dialogDiscount = new DialogDiscount(ShopActivity.this);
				} 
				dialogDiscount.show();

				dialogDiscount.getConfirmButton().setOnClickListener(
						new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialogDiscount.dismiss();
								unSalePrice = dialogDiscount
										.getEditTextContent();
								// TODO Auto-generated method stub
								if (StringUtils.isEmpty(unSalePrice)) {
									tv_discount.setText("输入不享受优惠金额");
								} else {
									if (Integer.parseInt(dialogDiscount
											.getEditTextContent()) > Integer
											.parseInt(et_priceStr)) {
										toast("优惠金额不能大于消费总额");
										return;
									}
									tv_discount.setText("不享受优惠金额:" + unSalePrice);
								}
								getSpotOrderMsg(shopId, et_price.getText()
										.toString().trim(), "1");
							}
						});

			}
		});
		/**
		 * 计算消费总额按钮
		 */
		tv_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				if (appContext.isNetworkConnected()) {
					String price_ET = et_price.getText().toString().trim();
					if (ValidateUtils.IsPrice(price_ET)
							&& Double.parseDouble(price_ET) > 0) {

						GetProgressBar.getProgressBar(ShopActivity.this);
						getSpotOrderMsg(shopId, et_price.getText().toString()
								.trim(), "1");
					} else {
						et_price.setText("");
						Toast.makeText(ShopActivity.this,
								"消费总额只能是两位小数且大于0,请重新输入!", Toast.LENGTH_SHORT)
								.show();
					}

				} else {
					Toast.makeText(ShopActivity.this, "网络不可用",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	/** 检查网络是否可用 */
	private void checkNet() {
		if (appContext.isNetworkConnected()) {
			getInfomation();
		} else {
			Toast.makeText(this, "网络不可用", Toast.LENGTH_SHORT).show();
		}
	}

	/** 获取店铺信息 */
	private void getInfomation() {
		GetProgressBar.getProgressBar(this);
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("shopId", shopId);// ("shopId", shopId);
		LogManag.d("my", "shopId" + shopId);
		AppContext.getHtmlUitls().xUtils(this, HttpMethod.GET, "shopInfo",
				params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						handler.sendEmptyMessage(0);
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						Log.d("my", "" + json.toString());
						try {
							if (json.getBoolean("success")) {
								if (map == null) {
									map = new HashMap<String, Object>();
								}
								JSONObject sObject = json.getJSONObject(
										"attributes").getJSONObject("shopBean");
								if (sObject.has("id")) {
									shopId = sObject.getString("id");
								}
								if (sObject.get("shopImage") != null) {
									JSONArray ar = sObject
											.getJSONArray("shopImage");
									if (ar.length() > 0) {
										for (int i = 0; i < ar.length(); i++) {
											listImg.add(ar.getString(i));
										}
									}
								}
								map.put("id", sObject.getString("id"));
								map.put("name", sObject.getString("name"));
								map.put("contact", sObject.getString("contact"));
								map.put("phone", sObject.getString("phone"));

								map.put("address", sObject.getString("address"));
								map.put("url", sObject.getString("url"));
								map.put("logo", sObject.getString("logo"));
								map.put("integration",
										sObject.getString("integration"));
								map.put("information",
										sObject.getString("information"));// 商品描述评分
								map.put("informationYoY",
										sObject.getString("informationYoY"));
								map.put("serve", sObject.getString("serve"));// 服务评分
								map.put("serveYoY",
										sObject.getString("serveYoY"));
								map.put("logistics",
										sObject.getString("logistics"));// 物流评分
								map.put("logisticsYoY",
										sObject.getString("logisticsYoY"));
								map.put("remarks", sObject.getString("remarks"));// 描述
								map.put("mainSell",
										sObject.getString("mainSell"));// 主营
								// map.put("spotCoPercent",
								// sObject.getInt("spotCoPercent"));// 可使用co比例
								// map.put("spotCoBackPercent",
								// sObject.getString("spotCoBackPercent"));//
								// 店铺返回co比例
								map.put("createDate", MyFormat
										.getTimeFormat2(sObject
												.getString("createDate")));// 创建时间

								if (sObject.get("spotCoPercent") != null
										&& !sObject.getString("spotCoPercent")
												.equals("null")) {
									spotCoPercent = sObject
											.getInt("spotCoPercent");
								}
								if (sObject.get("spotCoBackPercent") != null
										&& !sObject.getString(
												"spotCoBackPercent").equals(
												"null")) {
									spotCoBackPercent = sObject
											.getInt("spotCoBackPercent");
								}
								JSONArray _object = json.getJSONObject(
										"attributes").getJSONArray("actList");

								Gson gson = new Gson();
								Type type = new TypeToken<List<ActivityBean>>() {
								}.getType();

								mBeanList = gson.fromJson(_object.toString(),
										type);
								handler.sendEmptyMessage(1);
							} else {
								toast(json.getString("msg"));
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	@OnClick({ R.id.turn, R.id.fresh, R.id.head_tilte, R.id.ellipsis,
			R.id.care, R.id.shopimg, R.id.baobeifenlei, R.id.share,
			R.id.ll_fenlei_share, R.id.tv_pay })
	public void myclick(View v) {
		switch (v.getId()) {
		case R.id.turn:// 返回
			this.finish();
			break;
		case R.id.fresh:// 刷新

			// Toast.makeText(this, "刷新", Toast.LENGTH_SHORT).show();
			break;
		case R.id.head_tilte:// 搜索按钮
			startActivity(new Intent(this, SearchActivity.class));
			Toast.makeText(this, "收索页面", Toast.LENGTH_SHORT).show();
			break;
		case R.id.ellipsis:// 更多
			ellipsis.setBackgroundResource(R.drawable.home_scan);
			startActivity(new Intent(this, CaptureActivity.class));
			// ll_fenlei_share.setVisibility(View.VISIBLE);
			// Toast.makeText(this, "more", Toast.LENGTH_SHORT).show();
			break;
		case R.id.ll_fenlei_share:// 宝贝分类、店铺分享背景
			ll_fenlei_share.setVisibility(View.GONE);
			break;
		case R.id.baobeifenlei:// 宝贝分类
			ll_fenlei_share.setVisibility(View.GONE);
			if (fenleidialog == null) {
				fenleidialog = new Gerenziliao_Dialog(this, 10, this);
			}
			fenleidialog.show();
			Window window = fenleidialog.getWindow();
			WindowManager.LayoutParams lp = window.getAttributes();
			window.setGravity(Gravity.TOP);

			lp.x = 0;
			lp.y = 0;
			lp.width = AppContext.getScreenWidth();
			// lp.height=AppContext.getScreenHeight();
			lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
			window.setAttributes(lp);
			break;
		case R.id.share:// 店铺分享
			ll_fenlei_share.setVisibility(View.GONE);
			break;
		case R.id.care:// 关注
			if (appContext.isNetworkConnected()) {
				attion();
			} else {
				toast(R.string.t_Connection);
			}
			// Toast.makeText(this, "关注店铺", Toast.LENGTH_SHORT).show();
			break;
		case R.id.tv_pay:// 提交现场消费订单
			if (appContext.isNetworkConnected()) {
				// payprice = Float.parseFloat(pay_price.getText().toString());
				if (et_price.getText().toString().length() < 1) {
					toast("请输入消费金额");
					return;
				}
				if (Double.parseDouble(et_price.getText().toString()) > 0) {

					createSpotOrder(shopId,
							String.valueOf(et_price.getText().toString()),
							String.valueOf(userCo));
				} else {
					toast("消费金额需大于零");
				}
			} else {
				toast(R.string.t_Connection);
			}

			break;
		case R.id.shopimg:// 店铺相册
			if (shopFrameDialog == null) {
				shopFrameDialog = new ShopFrameDialog(this, listImg);
			}
			shopFrameDialog.show();
			Window widnWindow = shopFrameDialog.getWindow();
			WindowManager.LayoutParams lps = widnWindow.getAttributes();
			lps.width = AppContext.getScreenWidth();
			lps.height = AppContext.getScreenWidth();
			widnWindow.setAttributes(lps);

			break;
		}
	}

	/**
	 * 生成现场消费订单
	 * 
	 * @param shopId
	 *            店铺id,
	 * @param origPrice
	 *            消费金额
	 * @param userCo
	 *            是否用co币,0不用,1使用
	 */
	private void createSpotOrder(final String shopId, final String origPrice,
			final String userCo) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token",
				UserInformation.getAccess_token());
		params.addQueryStringParameter("shopId", shopId);
		params.addQueryStringParameter("origPrice", origPrice);
		params.addQueryStringParameter("userCo", userCo);
		params.addQueryStringParameter("unSalePrice",unSalePrice);
		params.addHeader(MyFormat.HEADER_KEY, MyFormat.HEADER_VALUE);
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/spotOrder/createSpotOrder", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						Log.d("my", "生成现场消费订单" + json);
						try {
							if (json.getBoolean("success")) {
								code = json.getJSONObject("obj").getString(
										"code");
								id = json.getJSONObject("obj").getString("id");

								name = json.getJSONObject("obj").getString(
										"name");
								pay = json.getJSONObject("obj").getString(
										"price");
								handler.sendEmptyMessage(3);
							} else {
								if (json.getString("errorCode").equals(
										MyFormat.NOT_LOGIN)) {
									toast(json.getString("msg"));
									// ShopActivity.this.startActivity(new
									// Intent(
									// ShopActivity.this,
									// WebLoginActivity.class));

									setRefreshListtener(new Refresh() {

										@Override
										public void refreshRequst(
												String access_token) {
											// TODO Auto-generated method stub
											createSpotOrder(shopId, origPrice,
													userCo);
										}
									});
									RefeshToken();

								} else {
									toast("访问失败");
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

	}

	/** 获取我的资产 */
	private void assets() {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token",
				UserInformation.getAccess_token());
		params.addHeader(MyFormat.HEADER_KEY, MyFormat.HEADER_VALUE);
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/assets/assets", params, new HttpCallback() {

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						LogManag.d("json-- >", json.toString());
						try {
							if (json.getBoolean("success")) {
								myco = json.getJSONObject("attributes")
										.getJSONObject("attentionList")
										.getInt("coAmount");
								handler.sendEmptyMessage(2);
							} else if (json.getString("errorCode") != null
									&& json.getString("errorCode").equals(
											MyFormat.NOT_LOGIN)) {
								toast(json.getString("msg"));
								setRefreshListtener(new Refresh() {

									@Override
									public void refreshRequst(
											String access_token) {
										// TODO Auto-generated method stub
										assets();
									}
								});
								RefeshToken();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

					}
				});
	}

	/*** 关注 */
	private void attion() {
		if (!AppContext.isLogin) {
			Toast.makeText(this, "关注前请先登录", Toast.LENGTH_SHORT).show();
			startActivity(new Intent(this, WebLoginActivity.class));
			return;
		}
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token",
				UserInformation.getAccess_token());
		Log.d("my", "accesstoken" + UserInformation.getAccess_token());
		Log.d("my", "accesstoken" + shopId);
		params.addQueryStringParameter("type", String.valueOf(2));
		params.addQueryStringParameter("objectId", shopId);// objectId
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/attention/addAttention", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

						if (msg.equals(401)) {
							toast("未登录");
							setRefreshListtener(new Refresh() {

								@Override
								public void refreshRequst(String access_token) {
									// TODO Auto-generated method stub
									attion();
								}
							});
							RefeshToken();
						}
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						try {
							if (json.getBoolean("success")) {
								toast("已经关注");

							} else if (json.get(MyFormat.errorCode).equals(
									MyFormat.NOT_LOGIN)) {
								setRefreshListtener(new Refresh() {

									@Override
									public void refreshRequst(
											String access_token) {
										// TODO Auto-generated method stub
										attion();
									}
								});
								RefeshToken();
							} else {
								toast(json.getString("msg"));

							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int id) {
		// TODO Auto-generated method stub
		switch (id) {
		case R.id.rb_home:// 首页
			position = 0;
			ll_home.setVisibility(View.VISIBLE);
			ll_allproduct.setVisibility(View.GONE);
			getProduct(Constants.SHOPPRODUCT, shopId, currentPage, 1);
			homelist_id.getRefreshableView().smoothScrollTo(0, 0);// 回到顶部
			break;
		case R.id.rb_allproduct:// 现场消费
			position = 1;
			ll_home.setVisibility(View.GONE);
			ll_allproduct.setVisibility(View.VISIBLE);
			// getProduct(Constants.SHOPPRODUCT, shopId, currentPage, 2);
			if (appContext.isNetworkConnected()) {
				assets();
			} else {
				toast(R.string.t_Connection);
			}
			homelist_id.getRefreshableView().smoothScrollTo(0, 0);// 回到顶部
			break;
		case R.id.rb_shop_datail:// 店铺简介
			// position=2;
			if (map == null) {
				toast("获取数据失败");
			}
			if (dialog == null) {
				dialog = new ProductDetailSizeDialog(this, 2, map);

			}
			dialog.show();
			Window window = dialog.getWindow();
			WindowManager.LayoutParams lp = window.getAttributes();
			window.setGravity(Gravity.LEFT | Gravity.TOP);
			lp.x = AppContext.getScreenWidth() / 5;
			lp.y = 0;
			lp.width = AppContext.getScreenWidth() * 4 / 5;
			// lp.height=AppContext.getScreenHeight();
			lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
			window.setAttributes(lp);
			break;

		default:
			break;
		}
		position1 = position;
		if (position1 == 0) {
			rb_home.setChecked(true);
		} else if (position1 == 1) {
			rb_allproduct.setChecked(true);
		}
		setIndictor(position);
	}

	/**
	 * 查询店铺商品 type:1 刷新,2 添加新数据
	 * */
	private void getProduct(String methodName, String shopId, int currentPage,
			int type) {
		if (appContext.isNetworkConnected()) {
			if (type == 1) {
				this.currentPage = 1;
				currentPage = 1;

			}
			RequestParams params = new RequestParams();
			params.addQueryStringParameter("shopId", shopId);
			params.addQueryStringParameter("currentPage",
					String.valueOf(currentPage));
			Log.d("my", "name:" + methodName + "shopId:" + shopId
					+ "currentPage" + currentPage);
			// methodName=methodName+"?"+"&shopId="+shopId+"&currentPage="+String.valueOf(currentPage);

			AppContext.getHtmlUitls().xUtils(this, HttpMethod.GET, methodName,
					params, new HttpCallback() {

						@Override
						public void onError(String msg) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onBack(JSONObject json) {
							// TODO Auto-generated method stub
							try {
								if (json.getBoolean("success")) {
									JSONArray jsonArray = json.getJSONObject(
											"attributes").getJSONArray(
											"AppProdSpecBeanList");
									Map<String, Object> map = null;
									Log.d("my", "jsonArray.length()"
											+ jsonArray.length());
									if (jsonArray.length() < 1) {
										return;
									}
									if (ShopActivity.this.currentPage == 1) {
										list_hot.clear();
									}
									for (int i = 0; i < jsonArray.length(); i++) {
										map = new HashMap<String, Object>();
										JSONObject js = (JSONObject) jsonArray
												.get(i);

										map.put("shopId",
												js.getString("shopId"));
										map.put("commodityId",
												js.getString("commodityId"));
										map.put("comodityName",
												js.getString("comodityName"));
										map.put("pic", js.getString("pic"));
										map.put("price", js.getString("price"));
										map.put("categoryId",
												js.getString("categoryId"));
										map.put("prodspecId",
												js.getString("prodspecId"));
										list_hot.add(map);
										// list_allproduct.add(map);
									}
									ShopActivity.this.currentPage++;
									// shop_all_ProcuctAdapter
									// .notifyDataSetChanged();
									shopProcuctAdapter.notifyDataSetChanged();

								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
		} else {
			Toast.makeText(this, R.string.t_Connection, Toast.LENGTH_SHORT)
					.show();
		}
	}

	/** 设置指示剂的宽度 */
	private void setIndictor(int position) {
		FrameLayout.LayoutParams lp = (android.widget.FrameLayout.LayoutParams) indictor
				.getLayoutParams();
		lp.width = wid;
		lp.leftMargin = wid * position;
		indictor.setLayoutParams(lp);
	}

	@Override
	public void setDianpufenlei(String one, String two) {
		// TODO Auto-generated method stub
		Log.d("my", "" + one + two);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		GetProgressBar.dismissMyProgressBar();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if (isChecked) {
			userCo = 1;

		} else {
			userCo = 0;

		}

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		price_layout.setVisibility(View.GONE);
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

		// if (s.length() < 1) {
		// price = 0;
		// show_can_user_co.setText("我的酷币:" + myco);
		// return;
		// } else {
		// price = Float.parseFloat(s.toString());
		// }
		// if (userCo == 1) {// 用co币
		// co = (int) price * spotCoPercent / 100;
		// if (myco >= co) {
		// payprice = (price - co);
		// if (co > 0) {
		// pay_price.setText("" + payprice + "元" + ",其中酷币抵扣:" + co
		// + "元");
		// } else {
		// pay_price.setText("" + payprice + "元");
		// }
		// } else if (myco < co) {
		// buycoprice = ((float) (co - myco)) / 10;
		// payprice = (price - co + buycoprice);
		// Log.d("my", "payprice" + payprice);
		// Log.d("my", "price" + price);
		// Log.d("my", "co" + co);
		// Log.d("my", "buycoprice" + buycoprice);
		// pay_price.setText("" + payprice + "元" + ",其中酷币抵扣:" + co + "元"
		// + ",购买酷币花费" + buycoprice + "元");
		// }
		// if (spotCoPercent > 0 && spotCoBackPercent > 0) {
		// show_can_user_co.setText("我有酷币:" + myco + ",本次可用" + co + "酷币抵:"
		// + co + "元,店铺返酷币:" + (int) price * spotCoBackPercent
		// / 100);
		// } else if (spotCoPercent > 0 && spotCoBackPercent <= 0) {
		// show_can_user_co.setText("我有酷币:" + myco + ",本次可用" + co + "酷币抵:"
		// + co + "元");
		//
		// } else if (spotCoPercent <= 0 && spotCoBackPercent > 0) {
		// show_can_user_co.setText("我有酷币:" + myco + ",店铺返酷币:"
		// + (int) price * spotCoBackPercent / 100);
		// } else if (spotCoPercent <= 0 && spotCoBackPercent <= 0) {
		// show_can_user_co.setText("我的酷币:" + myco);
		// }
		// pay = payprice;
		// } else if (userCo == 0) {// 不用co币
		// pay = price;
		// pay_price.setText("" + price + "元");
		// Log.d("my", "" + pay);
		// }
		// Log.d("my", "payprice" + payprice);
		// 设置一个标记 等到上一个请求请求完成之后 才能执行请求
		// if (flag) {
		// // 请求实付金额
		// getSpotOrderMsg(shopId, s.toString(), "1");
		// }

	}

	private void getSpotOrderMsg(String shop_id, String origPrice, String userCo) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", UserInformation.getAccess_token());
		params.addQueryStringParameter("shopId", shop_id);
		params.addQueryStringParameter("origPrice", origPrice);
		params.addQueryStringParameter("userCo", userCo);
		params.addQueryStringParameter("unSalePrice", unSalePrice);
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/spotOrder/getSpotOrderMsg", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						GetProgressBar.dismissMyProgressBar();
						if (!StringUtils.isEmpty(msg)) {
							toast(msg);
						}
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						GetProgressBar.dismissMyProgressBar();
						try {
							LogManag.d("my", "" + json.toString());
							if (json.getBoolean("success")) {
								String json_obj = json.getString("obj");
								Gson gson = new Gson();
								bean = gson.fromJson(json_obj,
										SpotOrderMsgBean.class);
								setPriceView(bean);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}

	private void setPriceView(SpotOrderMsgBean bean) {

		// 显示布局
		price_layout.setVisibility(View.VISIBLE);

		/** 原价 */
		tv_original_price.setText(bean.getOrigPrice() + "元");

		// 酷币抵扣
		if (bean.getDiCoPrice().equals("0")) {
			// 隐藏
			layout_cool_deductible.setVisibility(View.GONE);
		} else {
			layout_cool_deductible.setVisibility(View.VISIBLE);
		}
		tv_cool_deductible.setText(bean.getDiCoPrice() + "元");

		/** 酷币补足 */
		if (bean.getBuyCoPrice().equals("0")) {
			layout_cool_complement.setVisibility(View.GONE);
		} else {
			layout_cool_complement.setVisibility(View.VISIBLE);
		}
		tv_cool_complement.setText(bean.getBuyCoPrice() + "元");

		// 酷币返还
		if (bean.getBackCo().equals("0")) {
			layout_cool_return.setVisibility(View.GONE);
		} else {
			layout_cool_return.setVisibility(View.VISIBLE);
		}
		tv_cool_return.setText(bean.getBackCo() + "个");

		/** 订单总价 */
		tv_sum_price.setText(bean.getPayPrice() + "元");
	}
}