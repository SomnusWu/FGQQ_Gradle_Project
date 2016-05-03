package com.llg.privateproject.actvity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.bjg.lcc.privateproject.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.adapters.FinancialOderAdapter;
import com.llg.privateproject.entities.SMap;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.smartandroid.sa.aysnc.Log;
import com.smartandroid.sa.view.AutoLoading;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;

/** 财务订单 */
public class FinancialOrderAty extends BaseActivity implements
		OnCheckedChangeListener, OnItemClickListener {
	/** 订单号 */
	private String id;
	@ViewInject(R.id.lv)
	private PullToRefreshListView lv;
	@ViewInject(R.id.turn)
	private ImageView turn;
	@ViewInject(R.id.get_order)
	private TextView get_order;
	@ViewInject(R.id.title)
	private TextView title;
	@ViewInject(R.id.rg)
	private RadioGroup rg;
	@ViewInject(R.id.view)
	private View view;
	private FinancialOderAdapter adapter;
	List<SMap> list;
	private int pageNo = 1;
	private int totalPages = 2;
	private AutoLoading autoLoading;
	private int type = 1;
	private int orderState = 0;

	private Toast toast;

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
		view.setVisibility(View.VISIBLE);
		rg.setVisibility(View.VISIBLE);
		title.setText("财务订单");
		get_order.setVisibility(View.GONE);
		rg.setOnCheckedChangeListener(this);
		turn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		list = new ArrayList<SMap>();
		adapter = new FinancialOderAdapter(this, list);
		// test();
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
		lv.getRefreshableView().setDividerHeight(0);
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
				if (appContext.isNetworkConnected()) {
					getdata1("getYyOrderList");
					adapter.notifyDataSetChanged();
				} else {
					toast(R.string.t_Connection);
				}

			}
		});

		if (appContext.isNetworkConnected()) {
			getdata1("getYyOrderList");
			adapter.notifyDataSetChanged();
		} else {
			toast(R.string.t_Connection);
		}
	}

	private void getdata1(String method) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token",
				UserInformation.getAccess_token());
		params.addQueryStringParameter("pageNo", String.valueOf(pageNo));
		if (orderState > 0) {

			params.addQueryStringParameter("orderState",
					String.valueOf(orderState));
		}
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/order/" + method, params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						Log.d("my", "msg:" + msg);
						if (msg.equals("401")) {
							toast("未登录");
							startActivity(new Intent(FinancialOrderAty.this,
									WebLoginActivity.class));
						}
					}

					@Override
					public void onBack(JSONObject json) {
						Log.d("my", "json:" + json);
						try {
							if (json.getBoolean("success")) {
								if (json.get("obj") != null) {
									JSONObject obj = json.getJSONObject("obj");
									totalPages = obj.getInt("totalPages");
									if (obj.get("result") != null
											&& obj.getJSONArray("result")
													.length() > 0) {
										JSONArray result = obj
												.getJSONArray("result");
										
										Map<String, Object> map = null;
										for (int i = 0; i < result.length(); i++) {
											map = new HashMap<String, Object>();

											JSONObject ob = result
													.getJSONObject(i);
											map.put("BUSINESS_ID",
													ob.get("BUSINESS_ID"));//
											map.put("CUSTOMER_NAME",
													ob.get("CUSTOMER_NAME"));// CUSTOMER_NAME：用户名称
											if(!ob.get("OTHER_WEBSITE_CUS_NAME").equals(null)){
											map.put("OTHER_WEBSITE_CUS_NAME",
													ob.get("OTHER_WEBSITE_CUS_NAME"));// OTHER_WEBSITE_CUS_NAME：名
											}else{
												map.put("OTHER_WEBSITE_CUS_NAME","");
											}
											
											map.put("ORDER_NO",
													ob.get("ORDER_NO"));//
											map.put("PROD_ALL_PRICE",
													ob.get("PROD_ALL_PRICE"));// PROD_ALL_PRICE：商品总价

											map.put("PAY_STATE_STR",
													ob.get("PAY_STATE_STR"));// PAY_STATE_STR：支付状态中文
											map.put("PD_ALL_PRICE",
													ob.get("PD_ALL_PRICE"));// PD_ALL_PRICE：物流总价
																			// ALL_PRICE：应付款总价
											map.put("CUSTOMER_ID",
													ob.get("CUSTOMER_ID"));// CUSTOMER_ID：用户ID
																			// BUSINESS_ID：商户ID
											map.put("PAY_STATE",
													ob.get("PAY_STATE"));// *
																			// ORDER_STATE
											if(!ob.get("OTHER_WEBSITE_ORDERNO").equals(null)){
											map.put("OTHER_WEBSITE_ORDERNO",
													ob.get("OTHER_WEBSITE_ORDERNO"));// OTHER_WEBSITE_ORDERNO：回填订单号
											}else{
												map.put("OTHER_WEBSITE_ORDERNO",
														"")	;
											}
											map.put("BUSINESS_NAME",
													ob.get("BUSINESS_NAME"));// BUSINESS_NAME：商户名称
																				// CONSIGNEE：收货人
											if(!ob
													.get("OTHER_WEBSITE_PRICE").equals(null)){
											map.put("OTHER_WEBSITE_PRICE", ob
													.get("OTHER_WEBSITE_PRICE"));// OTHER_WEBSITE_PRICE：外链网站订单价格
											}else{
												map.put("OTHER_WEBSITE_PRICE","");
											}map.put("ORDER_STATE",
													ob.get("ORDER_STATE"));//
											// * ：订单状态（1未付款，2已付款，3等待收货，4
											// * 交易完成,6交易取消,7已删除）
											map.put("ID", ob.get("ID"));// ID：订单ID
																		// ORDER_NO：订单编号
											map.put("ORDER_STATE_STR",
													ob.get("ORDER_STATE_STR"));// ORDER_STATE_STR：订单状态中文
										
											if(!ob.get("PAY_DATE").equals(null)){
											map.put("PAY_DATE",
													ob.get("PAY_DATE"));// PAY_DATE：付款时间
											}else{
												map.put("PAY_DATE","");
											}
											if(!ob.get("OTHER_WEBSITE_CUSID").equals(null)){
											map.put("OTHER_WEBSITE_CUSID", ob
													.get("OTHER_WEBSITE_CUSID"));// OTHER_WEBSITE_CUSID：价格回填人
											}else{
												map.put("OTHER_WEBSITE_CUSID","");
											}
											
											map.put("ALL_PRICE",
													ob.get("ALL_PRICE"));//
											map.put("CREATE_DATE",
													ob.get("CREATE_DATE"));// CREATE_DATE：下单时间
											map.put("CONSIGNEE",
													ob.get("CONSIGNEE"));//
										
											list.add(new SMap(map));

										}
										adapter.notifyDataSetChanged();
									} else if (obj.get("result") != null
											&& obj.getJSONArray("result")
													.length() <= 0
											&& pageNo == 1) {

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
										getdata1("getYyOrderList");
									}
								});
								RefeshToken();
							} else {
								if (json.get("msg") != null) {
									toast(json.getString("msg"));
								}
							}
							lv.onRefreshComplete();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
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

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		if(list!=null){
			list.clear();
		}
		adapter.notifyDataSetChanged();
		switch (checkedId) {
		case R.id.rb_0:// 默认

			orderState = 0;
			break;
		case R.id.rb_1:// 未付款
			orderState = 1;

			break;
		case R.id.rb_2:// 已付款
			orderState = 2;

			break;
		case R.id.rb_3:// 待收货
			orderState = 3;
			break;
		case R.id.rb_4:// 交易完成
			orderState = 4;
			break;

		}
	
		if (appContext.isNetworkConnected()) {
			pageNo = 1;
			getdata1("getYyOrderList");

		} else {
			toast(R.string.t_Connection);
		}
	}

	Intent intent;

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if (intent == null) {
			intent = new Intent(this, FinanceOderDetail.class);
		}
Log.d("my", "list.size()"+list.size());
Log.d("my", "position"+position);
Bundle bundle=new Bundle();
bundle.putSerializable("map", list.get(position-1));
		intent.putExtra("bundle", bundle);
		startActivity(intent);
	}

}
