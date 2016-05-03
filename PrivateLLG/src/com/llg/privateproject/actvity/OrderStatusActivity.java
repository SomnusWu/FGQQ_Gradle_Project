package com.llg.privateproject.actvity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.ComponentCallbacks2;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.bjg.lcc.jsonparser.ParseJson;
import com.bjg.lcc.privateproject.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.help.GetProgressBar;
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.adapters.MaybeYouLikerAdapter;
import com.llg.privateproject.adapters.OrderCommentAdapter;
import com.llg.privateproject.adapters.OrderStatusAdapter;
import com.llg.privateproject.adapters.OrderStatusAdapter.Callback;
import com.llg.privateproject.adapters.OrderStatusAdapter1;
import com.llg.privateproject.adapters.UnPaidOrderStautsAdapter.UnPaidorderCallback;
import com.llg.privateproject.entities.OrderComment;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.view.MyGridView;
import com.llg.privateproject.view.OrderStatusDialog;
import com.llg.privateproject.view.OrderStatusDialog.PicListener;
import com.readystatesoftware.viewbadger.BadgeView;
import com.smartandroid.sa.view.AutoLoading;
/**
 * 我的订单状态
 * @author Administrator
 *
 */
public class OrderStatusActivity extends BaseActivity implements PicListener,
		Callback, OnItemClickListener, UnPaidorderCallback,
		OnRefreshListener2<ListView> {
	/** 标题 */
	@ViewInject(R.id.head_tilte)
	private TextView head_tilte;
	/** 更多 */
	@ViewInject(R.id.more)
	private ImageView more;
	/** 选中状态 */
	Drawable top1 = null;
	Drawable top2 = null;
	Drawable top3 = null;
	Drawable top4 = null;
	/** 评价对话框 */
	static public OrderStatusDialog dialog1;
	/** 图片布局 */
	static LinearLayout ll_pic = null;
	/***/
	ImageView chuantupian;
	/** 待付款 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/** 待收货 */
	@ViewInject(R.id.title_tv1)
	private TextView title_tv1;
	/** 待评价 */
	@ViewInject(R.id.title_tv2)
	private TextView title_tv2;
	/** 全部订单 */
	@ViewInject(R.id.title_tv3)
	private TextView title_tv3;
	/** 指示剂 */
	@ViewInject(R.id.indicator)
	private RelativeLayout indicator;
	private int position = 0;
	/** 未支付订单状态listView */
	@ViewInject(R.id.lv_order_status)
	private ListView lv_order_status;
	/** 待收货listView */
	@ViewInject(R.id.lv2)
	private PullToRefreshListView lv2;
	/** 待评价listView */
	@ViewInject(R.id.lv3)
	private PullToRefreshListView lv3;
	/** 全部订单listView */
	@ViewInject(R.id.lv4)
	private PullToRefreshListView lv4;
	/** 加载页面 */
	@ViewInject(R.id.ly_load)
	private LinearLayout lyLoad;
	/** 没有商品时显示LinearLayout */
	@ViewInject(R.id.ll_noproduct)
	private LinearLayout ll_noproduct;
	/** 有商品时显示LinearLayout */
	@ViewInject(R.id.has_order)
	private LinearLayout has_order;
	@ViewInject(R.id.ll)
	private LinearLayout ll;
	@ViewInject(R.id.ll_root)
	private LinearLayout ll_root;
	/** 去逛逛按钮 */
	@ViewInject(R.id.quguangguang)
	private TextView quguangguang;
	/** 去逛逛按钮 */
	@ViewInject(R.id.re_indicator)
	private RelativeLayout relativeLayout;
	/** 待付款列表 */
	private List<Map<String, Object>> list1;
	/** 待收货列表 */
	private List<Map<String, Object>> list2;
	/** 待评价列表 */
	private List<OrderComment> list3;
	/** 全部订单列表 */
	private List<OrderComment> list4;
	private OrderStatusAdapter orderStatusadapter1;
	private OrderStatusAdapter1 orderStatusadapter2;
	private OrderCommentAdapter orderStatusadapter3;
	private OrderCommentAdapter orderStatusadapter4;
	private AutoLoading autoLoading;
	/** 猜你喜欢列表 */
	@ViewInject(R.id.mayby_youlike_lv)
	private MyGridView mayby_youlike_lv;
	/** 猜你喜欢列表 */
	private List<Map<String, Object>> list_cainixihuan;
	/** 猜你喜欢列表适配器 */
	private MaybeYouLikerAdapter youLikeAdapter;
	Intent intent;
	BadgeView badge1;
	BadgeView badge2;
	BadgeView badge3;
	BadgeView badge4;
	private int index2;// 待收货
	private int index3;
	private int index4 = 1;
	private int page = 1;
	private int listPosition;
	private String state;
	/** sd卡路径 */
	String path = null;
	Uri imageUri;
	int count = 0;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				Log.i("tag", list1.size() + "==========list1=======");
				if (list1.size() == 0) {
					Log.i("tag", "=======2222==========");
					ll_noproduct.setVisibility(View.VISIBLE);
					has_order.setVisibility(View.GONE);
					android.widget.LinearLayout.LayoutParams lp = new android.widget.LinearLayout.LayoutParams(
							android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
							1000);
					ll_noproduct.setLayoutParams(lp);
					Log.i("tag", (ll_noproduct.getVisibility() == View.VISIBLE)
							+ "=======ll_noproduct===Visibility()=======");
					Log.i("tag", has_order.getVisibility()
							+ "=======has_order===Visibility()=======");
					Log.d("my",
							"ll_noproduct.getheigt:" + ll_noproduct.getHeight());
				} else {
					ll_noproduct.setVisibility(View.GONE);
					has_order.setVisibility(View.VISIBLE);
				}
				ViewTreeObserver vto = ll_noproduct.getViewTreeObserver();
				vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						ll_noproduct.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
						Log.i("tag", ll_noproduct.getHeight()
								+ "======ll_noproduct.getHeight()=======");
						Log.i("tag", ll_noproduct.getWidth()
								+ "=======ll_noproduct.getWidth()=======");
					}
				});

				break;
			case 3:// 未收货
				if (list2.size() == 0) {
					has_order.setVisibility(View.GONE);
					ll_noproduct.setVisibility(View.VISIBLE);
				} else {
					has_order.setVisibility(View.VISIBLE);
					ll_noproduct.setVisibility(View.GONE);
					lv2.setVisibility(View.VISIBLE);
					lv_order_status.setVisibility(View.GONE);
					android.widget.LinearLayout.LayoutParams lp = new android.widget.LinearLayout.LayoutParams(
							android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
							android.widget.LinearLayout.LayoutParams.MATCH_PARENT);
					// android.view.ViewGroup.LayoutParams lp=
					// (android.view.ViewGroup.LayoutParams)lv2.getLayoutParams();

					// lv2.setLayoutParams(lp);
					lv2.setMinimumHeight(AppContext.getScreenHeight());
					orderStatusadapter2.notifyDataSetChanged();
				}
				break;

			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_order_status);
		ViewUtils.inject(this);
		autoLoading = new AutoLoading(this, ll_root);
		init();
	}

	/***/
	private void init() {
		ViewTreeObserver vto = ll.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				ll.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				Log.i("tag", ll.getHeight() + "======ll.getHeight()=======");
				Log.i("tag", ll.getWidth() + "=======ll.getWidth()=======");
			}
		});
		ViewTreeObserver vto1 = relativeLayout.getViewTreeObserver();
		vto1.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				ll.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				Log.i("tag", relativeLayout.getHeight()
						+ "======relativeLayout.getHeight()=======");
				Log.i("tag", relativeLayout.getWidth()
						+ "=======relativeLayout.getWidth()=======");
			}
		});
		more.setVisibility(View.GONE);
		
		intent = getIntent();
		position = intent.getBundleExtra("bundle").getInt("position");
		head_tilte.setText("商品订单");
		list1 = new ArrayList<Map<String, Object>>();
		list2 = new ArrayList<Map<String, Object>>();
		list3 = new ArrayList<OrderComment>();
		list4 = new ArrayList<OrderComment>();
		orderStatusadapter1 = new OrderStatusAdapter(this, list1, this);
		orderStatusadapter2 = new OrderStatusAdapter1(this, list2);
		orderStatusadapter3 = new OrderCommentAdapter(this, list3);
		orderStatusadapter4 = new OrderCommentAdapter(this, list4);
		lv_order_status.setAdapter(orderStatusadapter1);
		lv2.setAdapter(orderStatusadapter2);
		// lv3.setAdapter(orderStatusadapter3);
		lv4.setAdapter(orderStatusadapter4);
		setBadgeView();
		// orderstatus(1, 1);
		// testOrderstatus();

		mayby_youlike_lv.setOnItemClickListener(this);

		list_cainixihuan = new ArrayList<Map<String, Object>>();// 猜你喜欢
		youLikeAdapter = new MaybeYouLikerAdapter(this, list_cainixihuan, false);
		mayby_youlike_lv.setAdapter(youLikeAdapter);
		setIndicator(position, 4, indicator);
//		initAutoLoadingNoShow(lyLoad);
		autoLoading.showLoadingLayout();
		autoLoading.setClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					orderstatus(1, 1);
					break;

				case 1:
					orderstatus(3, index2);
					break;
				case 2:
					findOrderCommentList(UserInformation.getAccess_token(),
							state, page);
					break;
				case 3:
					findOrderCommentList(UserInformation.getAccess_token(),
							state, index4);
					break;
				}

			}
		});
	}

	// @Override
	// public void onWindowFocusChanged(boolean hasFocus) {
	// // TODO Auto-generated method stub
	// super.onWindowFocusChanged(hasFocus);
	// if (hasFocus) {
	// Log.i("tag",
	// ll.getHeight()+"====onWindowFocusChanged===ll.getHeight()=======");
	// Log.i("tag",
	// relativeLayout.getHeight()+"====onWindowFocusChanged===relativeLayout.getHeight=======");
	// }
	// }

	/** 设置提示 */
	private void setBadgeView() {

		badge1 = new BadgeView(this, title_tv);
		badge2 = new BadgeView(this, title_tv1);
		badge3 = new BadgeView(this, title_tv2);
		badge4 = new BadgeView(this, title_tv3);
		// badge1.setBadgeMargin(10, 0);
		badge1.setBadgeMargin(1);
		badge1.setText("1");
		badge1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
		badge1.setTextSize(10);
		// badge1.show();

		badge2.setBadgeMargin(1);
		badge2.setText("1");
		badge2.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
		badge2.setTextSize(10);
		// badge2.setBackgroundResource(R.color.green1);
		// badge2.show();

		badge3.setBadgeMargin(1);
		badge3.setText("1");
		badge3.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
		badge3.setTextSize(10);
		// badge3.show();

		badge4.setBadgeMargin(1);
		badge4.setText("1");
		badge4.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
		badge4.setTextSize(10);
		// badge4.show();
	}

	/**
	 * 未付款订单 处理不停状态订单 1未付款，2已付款，3等待收货，4交易完成,6交易取消,7已删除 参数 orderState==订单状态8.
	 * pageNo==当前页码 备注 未付款的订单 无分页
	 * 
	 * @param orderState
	 *            订单状态 :1未支付,3待收货
	 */
	private void orderstatus(final int orderState, final int pageNo) {
		autoLoading.showLoadingLayout();
		RequestParams params = new RequestParams();

		params.addQueryStringParameter("access_token", UserInformation
				.getAccess_token());
		params.addBodyParameter("orderState", String.valueOf(orderState));
		if (orderState == 3) {
			params.addBodyParameter("pageNo", String.valueOf(pageNo));
		} else if (orderState == 4) {

		}
		// params.addBodyParameter("pageNo", String.valueOf(pageNo));
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/order/orderList", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						if (msg.equals(401)) {
							startActivity(new Intent(OrderStatusActivity.this,
									WebLoginActivity.class));
						}
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						autoLoading.hideAll();
						try {
							if (json.getBoolean("success")) {
								JSONObject object = json
										.getJSONObject("attributes");
								JSONArray array = new JSONArray();
								if (orderState == 1) {
									array = object
											.getJSONArray("orderBasePojos");// 未付款订单列表
									weifukuan(orderState, array);
								} else if (orderState == 3) {
									daishouhuo(object);
								}
							} else if (json.get(MyFormat.errorCode) != null
									&& json.getString(MyFormat.errorCode)
											.equals(MyFormat.NOT_LOGIN)) {
								setRefreshListtener(new Refresh() {

									@Override
									public void refreshRequst(
											String access_token) {
										// TODO Auto-generated method stub
										orderstatus(orderState, pageNo);
									}
								});
								RefeshToken();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					/*** 待收货 */
					private void daishouhuo(JSONObject object)
							throws JSONException {
						JSONArray array;
						array = object.getJSONArray("orderBaseBeanList");// 待收货订单
						if (array.length() > 0) {

							Map<String, Object> map;// 店铺
							for (int i = 0; i < array.length(); i++) {
								map = new HashMap<String, Object>();
								JSONObject object2 = array.getJSONObject(i);
								map.put("id", object2.getString("id"));
								map.put("businessName",
										object2.getString("businessName"));
								JSONArray children = object2
										.getJSONArray("children");
								if (children.length() > 0) {
									List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

									for (int j = 0; j < children.length(); j++) {
										Map<String, Object> map1 = new HashMap<String, Object>();// 商品
										JSONObject ob = children
												.getJSONObject(j);
										map1.put("prodName",
												ob.getString("prodName"));
										map1.put("img", ob.getString("imgUrl"));
										map1.put("finalPrice",
												ob.getString("finalPrice"));
										map1.put("amount",
												ob.getString("amount"));
										// map.put("orderStatus", "待收货");
										list.add(map1);// 商品列表添加商品
									}

									map.put("listproduct", list);// 店铺添加商品列表
									if (object2.get("orderState") != null) {
										if (object2.get("orderState").equals(
												"2")) {// 已支付未发货
											map.put("orderStatus", "待发货");
										} else if (object2.get("orderState")
												.equals("3")) {// 已支付已发货
											map.put("orderStatus", "待收货");
										}
									} else {
										// map.put("orderStatus",
										// object2.getString("待收货"));
									}
								}
								list2.add(map);
							}
							handler.sendEmptyMessage(3);
						}
					}

				});
	}

	/** 未付款订单 */
	private void weifukuan(final int orderState, JSONArray array)
			throws JSONException {
		if (array.length() > 0) {
			list1.clear();
			for (int i = 0; i < array.length(); i++) {
				JSONObject object2 = array.getJSONObject(i);// 订单列表第i个订单

				JSONArray array2 = new JSONArray();
				array2 = object2.getJSONArray("objects");// 订单下商品列表
				if (array2.length() > 0) {

					Map<String, Object> map;// 商品
					int count = 0;// 商品数量
					String phone = "";// 电话
					String address = "";// 地址
					String invoice = "";// 发票
					String allprice = "";// 商品总价
					String dsc = "";
					// 商品列表
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					for (int j = 0; j < array2.length(); j++) {// shang
						JSONArray array3 = new JSONArray();
						array3 = array2.getJSONArray(j);// 第j个商品
						if (array3.length() > 0) {

							// 0:付款ID,1:付款code,2:创建时间，3:总价格
							// 4:订单详细ID, 5:商品数量
							// 6:orderBase ID 7:收货人
							// 8：商品规格ID
							// 9:商品主表ID, 10:商品名称
							// 11：图片ID, 12:图片地址, 13商品单价
							// 14收货电话，15收货地址
							// ,16是否需要发票（N/Y），17发票抬头
							map = new HashMap<String, Object>();

							map.put("orderPayId", array3.getString(0));
							map.put("orderPayCode", array3.getString(1));
							map.put("createDate", array3.getString(2));
							map.put("allprice", array3.getString(3));// 总价
							allprice = array3.getString(3);
							map.put("detailId", array3.getString(4));
							count = count + array3.getInt(5);
							map.put("productCount", array3.getString(5));
							map.put("productDsc", array3.getString(10));
							dsc = dsc + array3.getString(10);
							map.put("img", array3.getString(12));
							map.put("productPrice", array3.getString(13));
							phone = array3.getString(14);// 电话

							map.put("phone", array3.getString(14));
							address = array3.getString(15);// 地址

							map.put("address", array3.getString(15));
							invoice = array3.getString(17);// 发票
							map.put("invoice", array3.getString(17));
							map.put("orderStatus", "未支付");
							myLogShow("my", "orderpayid"
									+ map.get("orderPayId").toString());
							list.add(map);

						}
					}
					Map<String, Object> map1 = new HashMap<String, Object>();
					map1.put("dsc", dsc);
					map1.put("listProducts", list);
					map1.put("shopProductCount", String.valueOf(count));// 点单数量
					map1.put("orderPayId", object2.getString("orderPayId"));// 点单编号
					map1.put("allprice", allprice);// 商品总价
					map1.put("orderPayCode", object2.getString("orderPayCode"));// 创建日期
					map1.put("createDate", object2.getString("createDate"));
					map1.put("consignee", object2.getString("consignee"));
					if (orderState == 1) {

						map1.put("orderStatus", "未支付");
					}
					list1.add(map1);

				}
				orderStatusadapter1.notifyDataSetChanged();

			}
		}
		handler.sendEmptyMessage(1);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		GetProgressBar.dismissMyProgressBar();
	}

	@OnClick({ R.id.turn, R.id.more, R.id.title_tv, R.id.title_tv1,
			R.id.title_tv2, R.id.title_tv3, R.id.quguangguang })
	public void myclick(View v) {
		switch (v.getId()) {
		case R.id.turn:// 返回
			finish();
			break;
		case R.id.more:// 更多
			Toast.makeText(this, "more", Toast.LENGTH_SHORT).show();
			break;
		case R.id.title_tv:// 待付款
			autoLoading.showLoadingLayout();
			position = 0;
			break;
		case R.id.title_tv1:// 待收货

			position = 1;

			break;
		case R.id.title_tv2:// 待评价
			position = 2;
			state = "1";
			autoLoading.showLoadingLayout();
			break;
		case R.id.title_tv3:// 全部订单
			position = 3;
			state = "";
			break;
		case R.id.quguangguang:// 去逛逛
			Toast.makeText(this, "去逛逛", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
		setIndicator(position, 4, indicator);
	}

	/** 设置指示剂位置 */
	private void setIndicator(int position, int wid, View v) {
		LayoutParams lp = (LayoutParams) v.getLayoutParams();
		int width = AppContext.getScreenWidth() / wid;
		lp.width = width;
		lp.leftMargin = position * width;
		v.setLayoutParams(lp);
		autoLoading.hideAll();
		switch (position) {
		case 0:

			ll_noproduct.setVisibility(View.GONE);
			lv_order_status.setVisibility(View.VISIBLE);
			lv2.setVisibility(View.GONE);
			lv3.setVisibility(View.GONE);
			lv4.setVisibility(View.GONE);
			badge1.hide();

			top1 = getResources().getDrawable(
					R.drawable.wdedingdan_daifukuan_orange);
			top1.setBounds(0, 0, top1.getMinimumWidth(),
					top1.getMinimumHeight());
			title_tv.setCompoundDrawables(null, top1, null, null);
			title_tv.setTextColor(getResources().getColor(R.color.orange1));

			top2 = getResources().getDrawable(
					R.drawable.wdedingdan_daishouhuo_gray);
			top2.setBounds(0, 0, top2.getMinimumWidth(),
					top2.getMinimumHeight());
			title_tv1.setCompoundDrawables(null, top2, null, null);
			title_tv1.setTextColor(getResources().getColor(R.color.black2));

			top3 = getResources().getDrawable(
					R.drawable.wdedingdan_daipingjia_gray);
			top3.setBounds(0, 0, top3.getMinimumWidth(),
					top3.getMinimumHeight());
			title_tv2.setCompoundDrawables(null, top3, null, null);
			title_tv2.setTextColor(getResources().getColor(R.color.black2));

			top4 = getResources().getDrawable(
					R.drawable.wdedingdan_wodedingdan_gray);
			top4.setBounds(0, 0, top4.getMinimumWidth(),
					top4.getMinimumHeight());
			title_tv3.setCompoundDrawables(null, top4, null, null);
			title_tv3.setTextColor(getResources().getColor(R.color.black2));
			if (list1.size() < 1) {
				orderstatus(1, 1);
			}
			break;
		case 1:

			ll_noproduct.setVisibility(View.VISIBLE);
			lv_order_status.setVisibility(View.GONE);
			lv2.setVisibility(View.VISIBLE);
			lv3.setVisibility(View.GONE);
			lv4.setVisibility(View.GONE);

			youLikeAdapter.notifyDataSetChanged();
			badge2.hide();

			top1 = getResources().getDrawable(
					R.drawable.wdedingdan_daifukuan_gray);
			top1.setBounds(0, 0, top1.getMinimumWidth(),
					top1.getMinimumHeight());
			title_tv.setCompoundDrawables(null, top1, null, null);
			title_tv.setTextColor(getResources().getColor(R.color.black2));

			top2 = getResources().getDrawable(
					R.drawable.wdedingdan_daishouhuo_orange);
			top2.setBounds(0, 0, top2.getMinimumWidth(),
					top2.getMinimumHeight());
			title_tv1.setCompoundDrawables(null, top2, null, null);
			title_tv1.setTextColor(getResources().getColor(R.color.orange1));

			top3 = getResources().getDrawable(
					R.drawable.wdedingdan_daipingjia_gray);
			top3.setBounds(0, 0, top3.getMinimumWidth(),
					top3.getMinimumHeight());
			title_tv2.setCompoundDrawables(null, top3, null, null);
			title_tv2.setTextColor(getResources().getColor(R.color.black2));

			top4 = getResources().getDrawable(
					R.drawable.wdedingdan_wodedingdan_gray);
			top4.setBounds(0, 0, top4.getMinimumWidth(),
					top4.getMinimumHeight());
			title_tv3.setCompoundDrawables(null, top4, null, null);
			title_tv3.setTextColor(getResources().getColor(R.color.black2));
			if (list2.size() < 1) {
				index2 = 1;
				orderstatus(3, index2);
			}
			break;
		case 2:
			ll_noproduct.setVisibility(View.GONE);
			lv_order_status.setVisibility(View.GONE);
			lv2.setVisibility(View.GONE);
			lv3.setVisibility(View.VISIBLE);
			lv4.setVisibility(View.GONE);
			badge3.hide();
			top1 = getResources().getDrawable(
					R.drawable.wdedingdan_daifukuan_gray);
			top1.setBounds(0, 0, top1.getMinimumWidth(),
					top1.getMinimumHeight());
			title_tv.setCompoundDrawables(null, top1, null, null);
			title_tv.setTextColor(getResources().getColor(R.color.black2));

			top2 = getResources().getDrawable(
					R.drawable.wdedingdan_daishouhuo_gray);
			top2.setBounds(0, 0, top2.getMinimumWidth(),
					top2.getMinimumHeight());
			title_tv1.setCompoundDrawables(null, top2, null, null);
			title_tv1.setTextColor(getResources().getColor(R.color.black2));

			top3 = getResources().getDrawable(
					R.drawable.wdedingdan_daipingjia_orange);
			top3.setBounds(0, 0, top3.getMinimumWidth(),
					top3.getMinimumHeight());
			title_tv2.setCompoundDrawables(null, top3, null, null);
			title_tv2.setTextColor(getResources().getColor(R.color.orange1));

			top4 = getResources().getDrawable(
					R.drawable.wdedingdan_wodedingdan_gray);
			top4.setBounds(0, 0, top4.getMinimumWidth(),
					top4.getMinimumHeight());
			title_tv3.setCompoundDrawables(null, top4, null, null);
			title_tv3.setTextColor(getResources().getColor(R.color.black2));
			if (list3.size() < 1) {
				state = "1";
				page = 1;
				findOrderCommentList(UserInformation.getAccess_token(), state,
						page);
			}
			break;
		case 3:
			ll_noproduct.setVisibility(View.GONE);
			lv_order_status.setVisibility(View.GONE);
			lv2.setVisibility(View.GONE);
			lv3.setVisibility(View.GONE);
			lv4.setVisibility(View.VISIBLE);
			badge4.hide();

			top1 = getResources().getDrawable(
					R.drawable.wdedingdan_daifukuan_gray);
			top1.setBounds(0, 0, top1.getMinimumWidth(),
					top1.getMinimumHeight());
			title_tv.setCompoundDrawables(null, top1, null, null);
			title_tv.setTextColor(getResources().getColor(R.color.black2));

			top2 = getResources().getDrawable(
					R.drawable.wdedingdan_daishouhuo_gray);
			top2.setBounds(0, 0, top2.getMinimumWidth(),
					top2.getMinimumHeight());
			title_tv1.setCompoundDrawables(null, top2, null, null);
			title_tv1.setTextColor(getResources().getColor(R.color.black2));

			top3 = getResources().getDrawable(
					R.drawable.wdedingdan_daipingjia_gray);
			top3.setBounds(0, 0, top3.getMinimumWidth(),
					top3.getMinimumHeight());
			title_tv2.setCompoundDrawables(null, top3, null, null);
			title_tv2.setTextColor(getResources().getColor(R.color.black2));
			top4 = getResources().getDrawable(
					R.drawable.wdedingdan_wodedingdan_orange);
			top4.setBounds(0, 0, top4.getMinimumWidth(),
					top4.getMinimumHeight());
			title_tv3.setCompoundDrawables(null, top4, null, null);
			title_tv3.setTextColor(getResources().getColor(R.color.orange1));
			Log.d("my", "list4.size()" + list4.size());
			if (list4.size() < 1) {
				state = "";
				index4 = 1;
				findOrderCommentList(UserInformation.getAccess_token(), state,
						index4);
			}
			break;

		default:
			break;
		}

	}

	@Override
	public void setDialog(OrderStatusDialog dialog1, LinearLayout ll_pic,
			ImageView chuantupian) {
		// TODO Auto-generated method stub

		OrderStatusActivity.dialog1 = dialog1;
		OrderStatusActivity.ll_pic = ll_pic;
		this.chuantupian = chuantupian;

	}

	@Override
	public void setPictrue(int type) {
		// TODO Auto-generated method stub
		// if(this.ll_pic==null)
		// this.ll_pic=ll_pic;
		// Log.d("my", "type"+type+"ll_pic"+ll_pic);
		Intent intent = new Intent();
		if (type == 1) {// 本地上传
			intent.setType("image/*");
			// 启用相册
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(intent, 1);
		} else if (type == 2) {// 拍照上传
			if (hasSDcard()) {
				File file = new File(Environment.getExternalStorageDirectory(),
						"/head.jpg");
				imageUri = Uri.fromFile(file);
				// Log.i("my","r.id.paizhao"+ imageUri.toString());
			}
			// 拍照
			intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra("return-data", false);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			intent.putExtra("outputFormat",
					Bitmap.CompressFormat.JPEG.toString());
			intent.putExtra("noFaceDetection", true);

			startActivityForResult(intent, 2);
		}

	}

	/** 判断是否有sd卡 */
	boolean hasSDcard() {

		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			path = Environment.getExternalStorageDirectory().toString();
			return true;
		}
		Toast.makeText(this, "false", Toast.LENGTH_LONG).show();
		return false;
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		// Log.i("my", "requestCode"+requestCode);
		// Log.i("my", "ll_pic"+ll_pic);
		Log.i("my", "dialog1" + dialog1);
		count++;
		Log.i("my", "count:" + count);
		if (count > 2) {
			chuantupian.setVisibility(View.INVISIBLE);
			Toast.makeText(this, "只能传两张图片", Toast.LENGTH_SHORT).show();
		}
		if (resultCode == RESULT_OK && count < 3) {

			ContentResolver cr = getContentResolver();
			switch (requestCode) {
			case 1:
				Uri uri = data.getData();
				try {
					// Bitmap
					// bitmap=BitmapFactory.decodeStream(cr.openInputStream(uri));
					Drawable dawble = Drawable.createFromResourceStream(null,
							null, cr.openInputStream(uri), null);

					ImageView iv = new ImageView(this);

					LayoutParams lp = new LayoutParams(
							AppContext.getScreenWidth() * 2 / 5,
							AppContext.getScreenWidth() * 4 / 5);
					// iv.setLayoutParams(lp);
					lp.rightMargin = 10;
					iv.setLayoutParams(lp);
					iv.setBackgroundDrawable(dawble);
					// iv.setImageBitmap(bitmap);
					if (ll_pic != null)
						ll_pic.addView(iv);
					// bitmap.recycle();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e("Exception", e.getMessage(), e);
				}

				break;
			case 2:
				try {
					File file = new File(
							Environment.getExternalStorageDirectory(),
							"/head.jpg");
					imageUri = Uri.fromFile(file);
					// Log.i("my", "2"+imageUri.toString());
					// Bitmap
					// bitmap=BitmapFactory.decodeStream(cr.openInputStream(imageUri));
					Drawable dawble = Drawable.createFromResourceStream(null,
							null, cr.openInputStream(imageUri), null);
					ImageView iv = new ImageView(this);

					LayoutParams lp = new LayoutParams(
							AppContext.getScreenWidth() * 2 / 5,
							AppContext.getScreenWidth() * 4 / 5);
					// iv.setLayoutParams(lp);
					lp.rightMargin = 10;
					iv.setBackgroundDrawable(dawble);
					iv.setLayoutParams(lp);

					// iv.setImageBitmap(bitmap);
					if (ll_pic != null)
						ll_pic.addView(iv);
					// bitmap.recycle();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e("Exception", e.getMessage(), e);
				}

				break;

			default:
				break;
			}
			// Log.e("my", "uri:"+uri.toString());
		} else {
			Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub

		super.onSaveInstanceState(outState);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		// ll_pic=(LinearLayout) savedInstanceState.get("ll_pic");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d("my", "onStop");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("my", "onDestroy");
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "item" + position, Toast.LENGTH_SHORT).show();

	}

	private void findOrderCommentList(String access_token, final String state,
			int page) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", access_token);

		params.addQueryStringParameter("pageNo", String.valueOf(page));
		params.addQueryStringParameter("state", state);
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/comment/list", params, new HttpCallback() {
					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						if (msg.equals(401)) {
							startActivity(new Intent(OrderStatusActivity.this,
									WebLoginActivity.class));
						}
						autoLoading.showExceptionLayout();
						// handler.sendEmptyMessage(2);
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						if (state == "1") {
							list3.clear();
						} else {
							list4.clear();
						}
						Map<String, Object> map = ParseJson.getParseJson()
								.parseIsSuccess(json);
						Log.i("tag", json.toString() + "----------成功了------");
						Log.i("tag", map.get("isSuccess")
								+ "---发送个人请求判断isSuccess------onBack------");

						if ((Boolean) map.get("isSuccess")) {
							List<OrderComment> list = ParseJson.getParseJson()
									.parseOrderComment(json);
							Log.i("tag", list.size() + "=====list.size====");
							if (list != null && list.size() > 0) {
								if (state == "1") {
									list3.addAll(list);
								} else {
									list4.addAll(list);
								}
							}
							handler2.sendEmptyMessage(1);

						} else if (map.get("errorCode").equals("NOT_LOGIN")) {
							setRefreshListtener(new Refresh() {
								@Override
								public void refreshRequst(String access_token) {
									// TODO Auto-generated method stub
									Log.i("tag", "------监听里-------");
								}
							});
							RefeshToken();
						} else if (!(Boolean) map.get("isSuccess")) {
							autoLoading.showExceptionLayout();
						}
					}
				});
	}

	Handler handler2 = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Log.i("tag", list3.size() + "=====tlist3.size====");
				if (state == "1") {
					if (list3.size() <= 0) {
						ll_noproduct.setVisibility(View.VISIBLE);
						has_order.setVisibility(View.GONE);
					} else {
						ll_noproduct.setVisibility(View.GONE);
						has_order.setVisibility(View.VISIBLE);
					}
					lv3.setVisibility(View.VISIBLE);
					lv3.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
							android.view.ViewGroup.LayoutParams.MATCH_PARENT, 800));
					lv3.setAdapter(orderStatusadapter3);
					orderStatusadapter3.setCommentButton(true);
					orderStatusadapter3.setList(list3);
//					orderStatusadapter3.notifyDataSetChanged();
					autoLoading.hideAll();
					Log.i("tag", lv3.getLayoutParams().height
							+ "======lv3.getHeight()=====");
					Log.i("tag", lv3.getMeasuredHeight()
							+ "======lv3.getMeasuredHeight()=====");
					lv3.onRefreshComplete();
					lv3.getRefreshableView().setSelectionFromTop(listPosition,
							TRIM_MEMORY_BACKGROUND);
				} else {
					if (list4.size() <= 0) {
						ll_noproduct.setVisibility(View.VISIBLE);
						has_order.setVisibility(View.GONE);
					} else {
						ll_noproduct.setVisibility(View.GONE);
						has_order.setVisibility(View.VISIBLE);
					}
					lv4.setVisibility(View.VISIBLE);
					lv4.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
							android.view.ViewGroup.LayoutParams.MATCH_PARENT,
							800));
					lv4.setAdapter(orderStatusadapter4);
					orderStatusadapter4.setList(list4);
					orderStatusadapter4.notifyDataSetChanged();
					autoLoading.hideAll();

					lv4.onRefreshComplete();
					// lv4.getRefreshableView().setSelectionFromTop(listPosition,
					// OrderStatusActivity.this.TRIM_MEMORY_BACKGROUND);
				}
				break;

			case 2:
				break;
			}
		}
	};

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		if (state == "1") {
			page = 1;
			findOrderCommentList(UserInformation.getAccess_token(), state, page);
		} else if (state == "") {
			index4 = 1;
			findOrderCommentList(UserInformation.getAccess_token(), state, page);

		}
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		if (state == "1") {
			page = page + 1;
			listPosition = list3.size();
			findOrderCommentList(UserInformation.getAccess_token(), state, page);
		} else if (state == "") {
			findOrderCommentList(UserInformation.getAccess_token(), state,
					++index4);

		}
	}
}
