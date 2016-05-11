package com.llg.privateproject.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.actvity.PhoneActivity;
import com.llg.privateproject.actvity.WebLoginActivity;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;

/**
 * 打电话----余额
 * 
 * @author Administrator
 * 
 */
public class FragmentPhoneMoney extends BaseFragment {
	private PhoneActivity context;
	/** 账号 */
	@ViewInject(R.id.tel)
	private TextView tel;
	/** 话费余额 */
	@ViewInject(R.id.money)
	private TextView money;
	/** 刷新 */
	@ViewInject(R.id.btn_refresh)
	private TextView btn_refresh;
	private TextView tvNet;
	/** 有效期 */
	@ViewInject(R.id.lastdate)
	private TextView lastdate;
	private String account = "";
	private String mymoney = "";
	private String lasttime = "";

	/**充值模块**/
	@ViewInject(R.id.tv_phone_number)
	TextView tv_phone_number;

	@ViewInject(R.id.edit_card_number)
	EditText edit_card_number;
	@ViewInject(R.id.edit_card_pwd)
	EditText edit_card_pwd;




	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:

				tel.setText(account);
				money.setText(mymoney);
				lastdate.setText(lasttime);
				break;

			default:
				break;
			}
		}
	};

	@Override
	public String getFragmentName() {
		// TODO Auto-generated method stub
		return "FragmentPhoneMoney";
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		context = (PhoneActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.phonemoney, null);
		tvNet = (TextView) v.findViewById(R.id.tv_net);
		ViewUtils.inject(this, v);
		initCharge();
		// if (TextUtils.isEmpty(getSharePrefence().getString("phone", null))) {
		// toast("余额查询失败");
		// return v;
		// }
		searchBalance(UserInformation.getAccess_token());
		registerReceiver();
		tvNet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
				startActivity(intent);
			}
		});
		btn_refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				searchBalance(UserInformation.getAccess_token());
			}
		});
		return v;
	}

	@Override
	protected void doAction1() {
		// TODO Auto-generated method stub
		super.doAction1();
		tvNet.setVisibility(View.VISIBLE);
	}

	@Override
	protected void doAction2() {
		// TODO Auto-generated method stub
		super.doAction1();
		tvNet.setVisibility(View.GONE);
	}

	private void searchBalance(String access_token) {
		if (!appContext.isNetworkConnected()) {
			customProgressSmall.dismiss();
			toast("网络未连接");
			return;
		}
		customProgressSmall.setMessage("正在查询中");
		customProgressSmall.show();
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", access_token);
		params.addQueryStringParameter("tel",
				getSharePrefence().getString("phone", ""));

		AppContext.getHtmlUitls().xUtilsm(getActivity(), HttpMethod.POST,
				"m/tel/queryBalanceTel", params,

				new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						Log.i("tag", msg);
						customProgressSmall.dismiss();
						if (msg.equals("401")) {
							startActivity(new Intent(getActivity(),
									WebLoginActivity.class));
						} else {
							toast("余额查询失败");
						}
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						Log.i("tag", json.toString());
						try {
							if (json.getBoolean("success")) {
								customProgressSmall.dismiss();
								JSONArray infoCustomers = json.getJSONObject(
										"obj").getJSONArray("infoCustomers");
								if (infoCustomers.length() > 0) {
									JSONObject obj = infoCustomers
											.getJSONObject(0);
									account = obj.getString("account");
									mymoney = StringUtils.dealMoney(Double
											.parseDouble(obj.getString("money")));
									// mymoney = obj.getString("money");
									lasttime = ""
											+ new Date(obj.getLong("validTime"));// .getInt("validTime")));
									handler.sendEmptyMessage(1);
								}
							} else {
								customProgressSmall.dismiss();
								toast(json.getString("msg"));
								if (json.get("errorCode").equals("NOT_LOGIN")) {
									// Log.i("tag", json.toString()
									// + "------监听上一句--------");
									setRefreshListtener(new Refresh() {
										@Override
										public void refreshRequst(
												String access_token) {
											// TODO Auto-generated method
											// stub
											Log.i("tag", "------监听里-------");
											searchBalance(UserInformation
													.getAccess_token());
										}
									});
									RefeshToken();
								}

							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	/**
	 * 充值模块
	 */
	private void initCharge() {
		String str = getSharePrefence().getString("phone", "null");
		if (str != null) {
			tv_phone_number.setText(str);
		}
	}
	@OnClick(R.id.tv_recharge_add)
	private void onRecharge(View v){
		if (!UserInformation.isLogin()) {
			startActivity(new Intent(getActivity(), WebLoginActivity.class));
			return;
		}

		if (edit_card_number.getText().toString().length() < 8) {
			toast("输入卡号有误！");
			return;
		}
		if (edit_card_pwd.getText().toString().length() < 6) {
			toast("密码输入有误！");
			return;
		}
		charge();
	}

	private void charge() {
		if (!appContext.isNetworkConnected()) {
			toast("网络未连接");
			return;
		}
		customProgressSmall.setMessage("正在充值中");
		customProgressSmall.show();
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token",
				UserInformation.getAccess_token());
		params.addQueryStringParameter("tel", tv_phone_number.getText().toString());
		params.addQueryStringParameter("pin", edit_card_number.getText().toString());
		params.addQueryStringParameter("password", edit_card_pwd.getText()
				.toString());

		AppContext.getHtmlUitls().xUtilsm(context, HttpMethod.POST,
				"m/tel/rechargeTel", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						customProgressSmall.dismiss();
						if (msg.equals("401")) {
							startActivity(new Intent(getActivity(),
									WebLoginActivity.class));
						} else {
							toast(msg);
						}
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						try {
							if (json.getBoolean("success")) {

								customProgressSmall.dismiss();
								JSONObject obj = json.getJSONObject("obj");
								if (obj != null) {

									if (String.valueOf(obj.getInt("retCode"))
											.equals("-10079")) {
										toast("卡号或密码不对");
									} else if (String.valueOf(
											obj.getInt("retCode")).equals(
											"-10078")) {
										toast("卡号已使用");
									} else if (String.valueOf(
											obj.getInt("retCode")).equals("0")) {
										toast("充值成功");

										edit_card_number.setText("");
										edit_card_pwd.setText("");
										searchBalance(UserInformation.getAccess_token());
									}else{
										toast(json.optString("msg"));
									}
								}
							} else {
								customProgressSmall.dismiss();
								toast(json.getString("msg"));
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

}
