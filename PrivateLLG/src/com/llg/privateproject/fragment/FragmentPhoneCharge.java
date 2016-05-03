package com.llg.privateproject.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.actvity.NewHomeActivity;
import com.llg.privateproject.actvity.PhoneActivity;
import com.llg.privateproject.actvity.PhoneRegistAty;
import com.llg.privateproject.actvity.WebLoginActivity;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity.Refresh;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.smartandroid.sa.sql.util.Log;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 电话充值
 * @author cc
 *
 */
public class FragmentPhoneCharge extends BaseFragment {
	private PhoneActivity context;
	@ViewInject(R.id.et_charge)
	private TextView et_charge;
	private TextView tvNet;
	@ViewInject(R.id.et_cart)
	private EditText et_cart;
	@ViewInject(R.id.et_password)
	private EditText et_password;

	@Override
	public String getFragmentName() {
		// TODO Auto-generated method stub
		return "FragmentPhoneCharge";
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
		View v = inflater.inflate(R.layout.phone_charge, null);
		ViewUtils.inject(this, v);
		tvNet = (TextView) v.findViewById(R.id.tv_net);
		init();
		registerReceiver();
		tvNet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
				startActivity(intent);
			}
		});
		return v;
	}

	private void init() {
		String str = getSharePrefence().getString("phone", "null");
		if (str != null) {
			et_charge.setText(str);
		}
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

	private void charge(String access_token) {
		if (!appContext.isNetworkConnected()) {
			toast("网络未连接");
			return;
		}
		customProgressSmall.setMessage("正在充值中");
		customProgressSmall.show();
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token",
				UserInformation.getAccess_token());
		params.addQueryStringParameter("tel", et_charge.getText().toString());
		params.addQueryStringParameter("pin", et_cart.getText().toString());
		params.addQueryStringParameter("password", et_password.getText()
				.toString());

		AppContext.getHtmlUitls().xUtilsm(context, HttpMethod.POST,
				"m/tel/charge", params, new HttpCallback() {

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
									}else{
										toast(json.optString("msg"));
									}
								}
							} else {
//								if (json.get("errorCode").equals("NOT_LOGIN")) {
//									// Log.i("tag", json.toString()
//									// + "------监听上一句--------");
//									setRefreshListtener(new Refresh() {
//										@Override
//										public void refreshRequst(
//												String access_token) {
//											// TODO Auto-generated method
//											// stub
//											charge(access_token);
//										}
//									});
//									RefeshToken();
//								} else {
//									customProgressSmall.dismiss();
//									toast("充值失败");
//								}
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

	@OnClick(R.id.tv_charge)
	private void myonclick(View v) {
		switch (v.getId()) {
		case R.id.tv_charge:
			if (!UserInformation.isLogin()) {
				startActivity(new Intent(getActivity(), WebLoginActivity.class));
				return;
			}
			if (et_charge.getText().toString().length() != 11) {
				toast("请输入正确的手机号" + et_cart.getText().toString());
				return;
			}
			if (et_cart.getText().toString().length() < 8) {
				toast("输入卡号有误！");
				return;
			}
			if (et_password.getText().toString().length() < 6) {
				toast("密码输入错误！");
				return;
			}
			charge(UserInformation.getAccess_token());
			break;

		default:
			break;
		}

	}

}
