package com.llg.privateproject.actvity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.util.StringUtils;
import com.bjg.lcc.privateproject.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.lidroid.xutils.view.annotation.event.OnItemLongClick;
import com.llg.help.GetProgressBar;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.adapter.CommodityAdapter;
import com.llg.privateproject.adapters.GuanzhuShopAdapter;
import com.llg.privateproject.entities.FollowModel;
import com.llg.privateproject.entities.MyHistoryModel;
import com.llg.privateproject.entities.MyShopModel;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.view.DialogDeleteView;
import com.llg.privateproject.view.MyGridView;

/**
 * 个人中心——我的关注
 * 
 * @author Administrator
 *
 */
public class GuanzhuActivity extends BaseActivity implements
		OnCheckedChangeListener, OnRefreshListener2<ScrollView>,
		OnItemLongClickListener {
	/** 标题 */
	@ViewInject(R.id.head_tilte)
	private TextView head_tilte;
	/** 关注编辑按钮 */
	@ViewInject(R.id.head_guanzhu)
	private CheckBox head_guanzhu;
	/** 关注的商品 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/** 关注的店铺 */
	@ViewInject(R.id.title_tv1)
	private TextView title_tv1;
	/** 浏览记录 */
	@ViewInject(R.id.title_tv2)
	private TextView title_tv2;
	/** 指示剂 */
	@ViewInject(R.id.indicator)
	private RelativeLayout indicator;
	private int position = 0;
	/** 商品数据集合 */
	private List<FollowModel> listData = null;
	/** 商品列表适配器 */
	private CommodityAdapter adapter = null;
	/** 浏览列表适配器 */
	private CommodityAdapter adapter_Product_HistoryAdapter = null;
	/** 商品列表 */
	@ViewInject(R.id.commodity_id)
	private MyGridView layoutListView;
	/** 浏览记录 */
	@ViewInject(R.id.gv_product_history)
	private MyGridView gv_product_history;
	/** 店铺数据集合 */
	private List<MyShopModel> listData_shop = null;
	/**
	 * 商品集合
	 */
	private List<MyShopModel> mShopList;
	/** 浏览记录列表 */
	private List<MyHistoryModel> listProduct_history = null;
	/** 店铺列表适配器 */
	private GuanzhuShopAdapter adapter_shop = null;
	/** 店铺列表 */
	@ViewInject(R.id.gv_shop)
	private MyGridView gv_shop;
	/** 商品页码 */
	private int pageNoC = 1;
	/** 店铺页码 */
	private int pageNoS = 1;
	/** 浏览记录页码 */
	private int pageNoH = 1;
	/** 如果没有关注信息 则显示这个布局 */
	@ViewInject(R.id.ll_noproduct)
	private LinearLayout ll_noproduct;

	@ViewInject(R.id.attention_pulls_v)
	private PullToRefreshScrollView attention_pulls_v;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			GetProgressBar.dismissMyProgressBar();
			switch (msg.what) {
			case 0:// 失败

				break;
			case 1:// 成功
				adapter.notifyDataSetChanged();
				adapter_shop.notifyDataSetChanged();
				adapter_Product_HistoryAdapter.notifyDataSetChanged();

				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guanzhu);
		ViewUtils.inject(this);

		init();
	}

	private void init() {
		head_tilte.setText("我的关注");
		head_guanzhu.setText("取消关注");
		head_guanzhu.setVisibility(View.VISIBLE);
		head_guanzhu.setOnCheckedChangeListener(this);
		// Log.d("my", "title_tv2.getWidth()" + title_tv2.getWidth());
		// Log.d("my", "title_tv2.getX()" + title_tv2.getX());
		// new Thread() {
		// public void run() {
		// try {
		// sleep(1000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// Log.d("my", "title_tv2.getWidth()" + title_tv2.getWidth());
		// Log.d("my", "title_tv2.getX()" + title_tv2.getX());
		//
		// };
		//
		// }.start();
		attention_pulls_v.setOnRefreshListener(this);

		/**
		 * 商品数据
		 */
		listData = new ArrayList<FollowModel>();
		adapter = new CommodityAdapter(this, listData, true, false);
		layoutListView.setAdapter(adapter);

		/**
		 * 店铺数据
		 */
		listData_shop = new ArrayList<MyShopModel>();
		adapter_shop = new GuanzhuShopAdapter(this, listData_shop, false);
		gv_shop.setAdapter(adapter_shop);

		/**
		 * 历史记录数据
		 */
		listProduct_history = new ArrayList<MyHistoryModel>();
		adapter_Product_HistoryAdapter = new CommodityAdapter(this,
				listProduct_history);
		gv_product_history.setAdapter(adapter_Product_HistoryAdapter);
		gv_product_history.setOnItemLongClickListener(this);

		// adapter_Product_HistoryAdapter = new CommodityAdapter(this,
		// listProduct_history, true, false);

		testCommodityData(1);
		// testCommodityData(2);
		// viewHistoryBeanList();

		setIndicator(position, 3, indicator);
	}

	@OnClick({ R.id.turn, R.id.title_tv, R.id.title_tv1, R.id.title_tv2 })
	public void myclick(View v) {
		switch (v.getId()) {
		case R.id.turn:// 返回
			finish();
			break;

		case R.id.title_tv:// 关注的商品
			position = 0;
			layoutListView.setVisibility(View.VISIBLE);
			gv_shop.setVisibility(View.GONE);
			gv_product_history.setVisibility(View.GONE);
			if (listData.size() <= 0) {
				ll_noproduct.setVisibility(View.VISIBLE);
			} else {
				ll_noproduct.setVisibility(View.GONE);
			}
			break;
		case R.id.title_tv1:// 关注的店铺
			position = 1;
			layoutListView.setVisibility(View.GONE);
			gv_shop.setVisibility(View.VISIBLE);
			gv_product_history.setVisibility(View.GONE);
			if (listData_shop.size() <= 0) {
				ll_noproduct.setVisibility(View.VISIBLE);
			} else {
				ll_noproduct.setVisibility(View.GONE);
			}
			testCommodityData(2);
			break;
		case R.id.title_tv2:// 浏览记录
			position = 2;
			layoutListView.setVisibility(View.GONE);
			gv_shop.setVisibility(View.GONE);
			gv_product_history.setVisibility(View.VISIBLE);
			if (listProduct_history.size() <= 0) {
				ll_noproduct.setVisibility(View.VISIBLE);
			} else {
				ll_noproduct.setVisibility(View.GONE);
			}
			viewHistoryBeanList();
			break;

		default:
			break;
		}
		setIndicator(position, 3, indicator);
	}

	/** 设置指示剂位置 */
	private void setIndicator(int position, int wid, View v) {
		LayoutParams lp = (LayoutParams) v.getLayoutParams();
		int width = AppContext.getScreenWidth() / wid;
		lp.width = width;
		lp.leftMargin = position * width;
		v.setLayoutParams(lp);
		switch (position) {
		case 0:// 关注的商品
			head_guanzhu.setVisibility(View.VISIBLE);
			title_tv.setTextColor(getResources().getColor(R.color.orange1));

			title_tv1.setTextColor(getResources().getColor(R.color.black2));

			title_tv2.setTextColor(getResources().getColor(R.color.black2));

			break;
		case 1:// 关注的店铺
			head_guanzhu.setVisibility(View.VISIBLE);

			title_tv.setTextColor(getResources().getColor(R.color.black2));

			title_tv1.setTextColor(getResources().getColor(R.color.orange1));

			title_tv2.setTextColor(getResources().getColor(R.color.black2));

			break;
		case 2:// 浏览记录
			head_guanzhu.setVisibility(View.GONE);
			title_tv.setTextColor(getResources().getColor(R.color.black2));

			title_tv1.setTextColor(getResources().getColor(R.color.black2));

			title_tv2.setTextColor(getResources().getColor(R.color.orange1));

			break;

		default:
			break;
		}

	}

	/***
	 * 关注的商品 type:1商品,2店铺
	 * 
	 */
	int _page = 1;

	private void testCommodityData(final int type) {

		GetProgressBar.getProgressBar(this);
		// Map<String, Object> map1 = null;
		Map<String, Object> map2 = null;
		if (type == 1) {
			_page = pageNoC++;
		} else if (type == 2) {
			_page = pageNoS++;
		}

		RequestParams params = new RequestParams();
		params.addQueryStringParameter("type", String.valueOf(type));// type=1关注的商品,type=2关注的店铺
		params.addQueryStringParameter("pageNo", String.valueOf(_page));// type=1关注的商品,type=2关注的店铺
		params.addQueryStringParameter("access_token", UserInformation.getAccess_token());
		params.addQueryStringParameter("time", "" + System.currentTimeMillis());
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.GET,
				"m/attention/findAttentions2", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						handler.sendEmptyMessage(0);

					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						// Log.d("my", "onBack" + json);
						try {
							if (json.getBoolean("success")) {

								JSONArray array = new JSONArray();
								array = json.getJSONObject("obj").getJSONArray(
										"result");
								if (array.length() > 0) {
									ll_noproduct.setVisibility(View.GONE);
								}
								if (type == 1) {
									FollowModel model = new FollowModel();
									if (_page == 1) {
										listData.clear();
									}
									listData.addAll(model.parseJson(array
											.toString()));
									adapter.notifyDataSetChanged();
								} else if (type == 2) {
									MyShopModel model = new MyShopModel();
									if (_page == 1) {
										listData_shop.clear();
									}
									listData_shop.addAll(model.parseJson(array
											.toString()));
								}
							}
							if (type == 1) {
								if (listData.size() < 0) {
									ll_noproduct.setVisibility(View.VISIBLE);
								}
							} else if (type == 2) {
								if (listData_shop.size() < 0) {
									ll_noproduct.setVisibility(View.VISIBLE);
								}
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						handler.sendEmptyMessage(1);
					}
				});

	}

	int histtorypage = 1;
	/**
	 * 浏览记录列表
	 * */
	private void viewHistoryBeanList() {
		RequestParams params = new RequestParams();
		histtorypage = pageNoH++;
		params.addQueryStringParameter("access_token", UserInformation.getAccess_token());
		params.addQueryStringParameter("time", "" + System.currentTimeMillis());
		params.addQueryStringParameter("pageNo", String.valueOf(histtorypage));
		
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"/m/viewhistory/findViewHistoryList2", params,
				new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub

						try {
							if (json.getBoolean("success")) {
								JSONArray array = json.getJSONObject("obj")
										.getJSONArray("result");
								if (array.length() > 0) {
									ll_noproduct.setVisibility(View.GONE);
								}
								MyHistoryModel model = new MyHistoryModel();
								List<MyHistoryModel> mlist = model
										.parseJson(array.toString());
								if (histtorypage == 1) {
									listProduct_history.clear();
								}
								listProduct_history.addAll(mlist);

								adapter_Product_HistoryAdapter
										.notifyDataSetChanged();

							}
							if (listProduct_history.size() <= 0) {
								ll_noproduct.setVisibility(View.VISIBLE);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

		adapter_Product_HistoryAdapter.notifyDataSetChanged();
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
		if (isChecked) {// 取消关注
			head_guanzhu.setText("取消关注");

			adapter.setIsguanzhu(false);
			adapter_shop.setIsguanzhu(false);

			StringBuffer sb = new StringBuffer();
			if (position == 0) {// 商品

				for (int i = 0; i < adapter.getCount(); i++) {
					FollowModel model = (FollowModel) adapter.getItem(i);
					if (model.isChecked()) {
						sb.append(model.getATTENTIONID() + ",");
					}
					// ---------------------------------------------------------------------------------------
					// if
					// (!Boolean.parseBoolean(listData.get(i).get("isGuanzhu")
					// .toString())) {
					// String attentionIds = listData.get(i).get("attID")
					// .toString();
					// if (sb.length() < 1) {
					//
					// sb.append(attentionIds);
					// } else if (sb.length() > 0) {
					// sb.append("," + attentionIds);
					//
					// }
					// listData.remove(i);
					// }

				}
			} else if (position == 1) {// 店铺

				for (int i = 0; i < adapter_shop.getCount(); i++) {
					MyShopModel model = (MyShopModel) adapter_shop.getItem(i);
					if (model.isChecked()) {
						sb.append(model.getATTENTIONID() + ",");
					}
				}
				// for (int i = 0; i < adapter_shop.getCount(); i++) {
				//
				// if (!Boolean.parseBoolean(listData_shop.get(i)
				// .get("isGuanzhu").toString())) {
				// String shopId = listData_shop.get(i).get("attID")
				// .toString();
				// if (sb.length() < 1) {
				//
				// sb.append(shopId);
				// } else if (sb.length() > 0) {
				// sb.append("," + shopId);
				//
				// }
				// listData_shop.remove(i);
				// }
				// }
			}
			cancelAttentions(position + 1, sb.toString());

		} else {
			head_guanzhu.setText("完成");
			adapter.setIsguanzhu(true);
			adapter_shop.setIsguanzhu(true);

		}
		adapter.notifyDataSetChanged();
		adapter_shop.notifyDataSetChanged();
		adapter_Product_HistoryAdapter.notifyDataSetChanged();

	}

	@OnItemClick({ R.id.commodity_id, R.id.gv_shop, R.id.gv_product_history })
	public void onItemClick(AdapterView<?> parent, View arg1, int position,
			long arg3) {
		Intent intent = new Intent();
		switch (parent.getId()) {
		case R.id.commodity_id:// 关注商品项

			String prodSpecId = listData.get(position).getPRODSPECID();
			intent.setClass(this, ProductDetailActivity.class);
			intent.putExtra("prodspecId", prodSpecId);

			break;
		case R.id.gv_shop:// 关注店铺项
			String shopId = listData_shop.get(position).getSHOPID().toString();
			intent.setClass(this, ShopActivity.class);
			intent.putExtra("shopId", shopId);
			break;
		case R.id.gv_product_history:
			String shop_id = listProduct_history.get(position).getProdspecid();
			intent.setClass(this, ProductDetailActivity.class);
			intent.putExtra("prodspecId", shop_id);
			break;

		}
		startActivity(intent);

	}

	/**
	 * 取消关注
	 * 
	 * 
	 * @param type
	 *            :1商品,2店铺
	 * */
	private void cancelAttentions(final int type, String attentionIds) {
		if (attentionIds.length() <= 0) {
			return;
		}
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", UserInformation.getAccess_token());
		params.addQueryStringParameter("attentionIds", attentionIds);
		params.addQueryStringParameter("time", "" + System.currentTimeMillis());
		params.addQueryStringParameter("type", String.valueOf(type));
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/attention/cancelAttentions", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

						Log.e("my", "取消关注" + msg);
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						try {
							if (json.getBoolean("success")) {
								pageNoC = 1;
								pageNoS = 1;
								if (type == 1) {// 商品
									testCommodityData(1);
								} else if (type == 2) {
									testCommodityData(2);
								}

								toast(json.getString("msg"));
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

	/**
	 * 下拉刷新
	 */
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
		// TODO Auto-generated method stub
		switch (position) {
		case 0:// 关注的商品
			pageNoC = 1;
			testCommodityData(1);
			break;

		case 1:// 关注的店铺
			pageNoS = 1;
			testCommodityData(2);
			break;
		case 2:// 浏览记录
			pageNoH = 1;
			viewHistoryBeanList();
			break;
		}

		attention_pulls_v.onRefreshComplete();
	}

	/**
	 * 上拉加载
	 */
	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
		// TODO Auto-generated method stub
		// if (listData.size()<0) {
		// ll_noproduct.setVisibility(View.VISIBLE);
		// }else if (listData_shop.size()<0) {
		// ll_noproduct.setVisibility(View.VISIBLE);
		// }else if(listProduct_history.size()<0){
		// ll_noproduct.setVisibility(View.VISIBLE);
		// }
		switch (position) {
		case 0:// 添加关注商品
			testCommodityData(1);

			break;
		case 1:// 添加关注店铺

			testCommodityData(2);
			break;

		case 2:// 添加浏览记录
			viewHistoryBeanList();
			break;
		}
		attention_pulls_v.onRefreshComplete();
	}

	DialogDeleteView dialog;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemLongClickListener#onItemLongClick(android
	 * .widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		final MyHistoryModel model = (MyHistoryModel) parent.getAdapter()
				.getItem(position);
		dialog = new DialogDeleteView(this, new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				// 删除
				requestDelete(model.getId());
			}
		});
		dialog.show();

		return true;
	}

	private void requestDelete(String id) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", UserInformation.getAccess_token());
		params.addQueryStringParameter("id", id);
		AppContext.getHtmlUitls().xUtilsm(GuanzhuActivity.this,
				HttpMethod.POST, "m/viewhistory/del", params,
				new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						if (!StringUtils.isEmpty(msg)) {
							toast(msg);
						}
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						try {
							toast(json.getString("msg"));
							if (json.optBoolean("success")) {
								// 刷新数据
								pageNoH = 1;
								viewHistoryBeanList();
							}
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}

					}
				});
	}
}
