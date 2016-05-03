package com.llg.privateproject.actvity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bjg.lcc.privateproject.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.llg.help.GetProgressBar;
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.adapters.ConsumeAdapter;
import com.llg.privateproject.entities.Consumeentity;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.smartandroid.sa.aysnc.Log;
import com.smartandroid.sa.view.AutoLoading;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;

/**
 * 现场消费点单 yh 2015.12.19
 * */
public class Consume extends BaseActivity {
	/** 订单号 */
	private String id;
	@ViewInject(R.id.lv)
	private PullToRefreshListView lv;
	@ViewInject(R.id.turn)
	private ImageView turn;
	// /** 收费订单*/
	// @ViewInject(R.id.get_order)
	// private TextView get_order;
	/** 我的店铺--收费订单 */
	@ViewInject(R.id.txt_MyShop)
	private TextView txt_MyShop;
	@ViewInject(R.id.rl_MyShop)
	private RelativeLayout rl_MyShop;
	/** 我的消费 */
	@ViewInject(R.id.txt_MySpending)
	private TextView txt_MySpending;
	@ViewInject(R.id.rl_MySpending)
	private RelativeLayout rl_MySpending;
	/** 区分商家和普通消费者的提示(只消费者-不显示;若商家-显示) */
	@ViewInject(R.id.ll_ClassificationOfPrompt)
	private LinearLayout ll_ClassificationOfPrompt;
	private ConsumeAdapter adapter;
	List<Consumeentity> list;
	private int pageNo = 1;
	private int totalPages = 4;
	private AutoLoading autoLoading;
	private int type = 1;
	private Toast toast;
	/** 没有消费订单时就显示这一部分布局 */
	@ViewInject(R.id.ll_noproduct)
	private LinearLayout ll_noproduct;
	private int respectively = 1;// 区分消费者还是消费者 (默认是商家)

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.consume);
		ViewUtils.inject(this);
		comehere();
		init();
	}

	private void init() {
		if ("Y".equals(AppContext.isShop)) {// AppContext.isShop.equals("Y")
			// get_order.setVisibility(View.VISIBLE);
			txt_MyShop.setTextColor(getResources().getColor(R.color.home_t));
			ll_ClassificationOfPrompt.setVisibility(View.VISIBLE);

			/** 消费者 */
			rl_MySpending.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					txt_MySpending.setTextColor(getResources().getColor(
							R.color.home_t));
					txt_MyShop.setTextColor(getResources().getColor(
							R.color.black));
					respectively = 2;
					addList();
				}
			});
			/** 店铺(商家) */
			rl_MyShop.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					txt_MyShop.setTextColor(getResources().getColor(
							R.color.home_t));
					txt_MySpending.setTextColor(getResources().getColor(
							R.color.black));
					respectively = 1;
					addList();
				}
			});

		} else {
			respectively = 2;
			ll_ClassificationOfPrompt.setVisibility(View.GONE);
		}

		turn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		addList();
	}

	/**
	 * 添加下面的list数据
	 */
	public void addList() {
		list = new ArrayList<Consumeentity>();
		adapter = new ConsumeAdapter(list, this, type);
		lv.setAdapter(adapter);
		lv.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				pageNo++;
				if (totalPages < pageNo) {
					toast("暂无更多");
					lv.onRefreshComplete();
					return;
				}
				if (respectively == 2) {
					if (appContext.isNetworkConnected()) {
						getdata1("getSpotList");
						adapter.notifyDataSetChanged();
					} else {
						toast(R.string.t_Connection);
					}
				} else if (respectively == 1) {
					if (appContext.isNetworkConnected()) {
						getdata1("getShopSpotList");
						adapter.notifyDataSetChanged();
					} else {
						toast(R.string.t_Connection);
					}
				}
			}
		});
		if (respectively == 2) {
			if (appContext.isNetworkConnected()) {
				getdata1("getSpotList");
				adapter.notifyDataSetChanged();
			} else {
				toast(R.string.t_Connection);
			}
		} else if (respectively == 1) {
			if (appContext.isNetworkConnected()) {
				getdata1("getShopSpotList");
				adapter.notifyDataSetChanged();
			} else {
				toast(R.string.t_Connection);
			}
		}

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (respectively == 2) {// 我的消费
					Consumeentity cousume = (Consumeentity) parent.getAdapter()
							.getItem(position);
					Intent intent = new Intent();
					intent.putExtra("Consumeentity", cousume);
					intent.setClass(Consume.this,
							FGQQShopEvaluateActivity.class);
					startActivity(intent);

				}
			}
		});

	}

	private void getdata1(String method) {
		GetProgressBar.getProgressBar(this);
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token",
				UserInformation.getAccess_token());
		params.addQueryStringParameter("pageNo", String.valueOf(pageNo));
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/spotOrder/" + method, params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						Log.d("my", "msg:" + msg);
						if (msg.equals("401")) {
							toast("未登录");
							startActivity(new Intent(Consume.this,
									WebLoginActivity.class));
						}
					}

					@Override
					public void onBack(JSONObject json) {
						Log.d("my", "json:" + json);

						try {
							if (json.getBoolean("success")) {

								GetProgressBar.dismissMyProgressBar();

								if (json.get("obj") != null) {
									JSONObject obj = json.getJSONObject("obj");
									totalPages = obj.getInt("totalPages");
									if (obj.get("result") != null
											&& obj.getJSONArray("result")
													.length() > 0) {
										ll_noproduct.setVisibility(View.GONE);
										JSONArray result = obj
												.getJSONArray("result");
										Gson gson = new Gson();
										Consumeentity item;
										for (int i = 0; i < result.length(); i++) {
											item = gson.fromJson(result
													.getJSONObject(i)
													.toString(),
													Consumeentity.class);
											list.add(item);
										}

										adapter.notifyDataSetChanged();

									} else if (obj.get("result") != null
											&& obj.getJSONArray("result")
													.length() <= 0
											&& pageNo == 1) {
									} else {

									}

								}
							} else if (json.get(MyFormat.errorCode) != null
									&& json.getString(MyFormat.errorCode)
											.equals(MyFormat.NOT_LOGIN)) {
								setRefreshListtener(new Refresh() {

									@Override
									public void refreshRequst(
											String access_token) {
										// TODO Auto-generated method stub
										if (respectively == 2) {
											getdata1("getSpotList");
										} else if (respectively == 1) {
											getdata1("getShopSpotList");
										}
									}
								});
								RefeshToken();
							}
							lv.onRefreshComplete();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		GetProgressBar.dismissMyProgressBar();
	}

	private void comehere() {
		XGPushClickedResult click = XGPushManager.onActivityStarted(this);
		if (click != null) {
			JSONObject obj;
			try {
				obj = new JSONObject(click.getCustomContent());
				if (obj.has("objectId") && obj.get("objectId") != null) {

					id = obj.get("objectId").toString();
				} else {
					finish();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
