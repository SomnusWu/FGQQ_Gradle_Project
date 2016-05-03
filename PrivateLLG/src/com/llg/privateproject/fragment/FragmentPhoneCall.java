package com.llg.privateproject.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.actvity.WebLoginActivity;
import com.llg.privateproject.adapters.PhoneCallAdapter;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;

/**
 * 拨号键盘
 * @author Administrator
 *
 */
public class FragmentPhoneCall extends BaseFragment implements
		OnItemClickListener {
	private GridView gridView;
	private TextView edtNumber;
	private ImageView ivClear;
	private ImageView ivCall;
	private List<String> list;
	private Context context;
	private TextView tvNet;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		context = activity;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (customProgressSmall.isShowing()) {
			customProgressSmall.dismiss();
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getActivity().unregisterReceiver(receiver);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fgt_call, null);
		gridView = (GridView) v.findViewById(R.id.gridview);
		edtNumber = (TextView) v.findViewById(R.id.edt_number);
		tvNet = (TextView) v.findViewById(R.id.tv_net);
		ivClear = (ImageView) v.findViewById(R.id.iv_clear);
		ivCall = (ImageView) v.findViewById(R.id.iv_call);
		list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		list.add("7");
		list.add("8");
		list.add("9");
		list.add("*");
		list.add("0");
		list.add("#");
		PhoneCallAdapter adapter = new PhoneCallAdapter(getActivity(), list);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(this);
		ivClear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int lengh = edtNumber.getText().length();
				if (lengh == 0) {
					return;
				}
				String text = edtNumber.getText().toString()
						.substring(0, lengh - 1);
				edtNumber.setText(text);
			}
		});
		ivCall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				if (edtNumber.getText().toString().trim().charAt(0)!=0&&edtNumber.getText().toString().length()<11) {
				if (edtNumber.getText().toString().length()<11) {
					toast("请输入正确的手机号");
					return;
				} 
				if (edtNumber.getText().toString().trim().charAt(0)==0||!TextUtils.isEmpty(edtNumber.getText().toString())) {
					customProgressSmall.setMessage("正在拨号中");
					call(UserInformation.getAccess_token());
				}
			}

		});
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

	// /** 打电话 */
	// private void call(String access_token) {
	// if (!appContext.isNetworkConnected()) {
	// toast("网络未连接");
	// return;
	// }
	// if (edtNumber.getText().toString().length() < 7) {
	// toast("请输入正确的电话号码");
	// return;
	// }
	// customProgressSmall.show();
	// RequestParams params = new RequestParams();
	// params.addQueryStringParameter("access_token", access_token);
	// params.addQueryStringParameter("tel",
	// getSharePrefence().getString("phone", "null"));
	// params.addQueryStringParameter("callerDisplayNumber", edtNumber
	// .getText().toString());
	// Log.i("tag", edtNumber.getText().toString() + "=======number");
	// // xUtils(getActivity(), HttpMethod.POST, null, null, new HttpCallback()
	// // {
	// //
	// // @Override
	// // public void onError(String msg) {
	// // // TODO Auto-generated method stub
	// //
	// // }
	// //
	// // @Override
	// // public void onBack(JSONObject json) {
	// // // TODO Auto-generated method stub
	// //
	// // }
	// // });
	//
	// AppContext.getHtmlUitls().xUtils(getActivity(), HttpMethod.POST,
	// "tel/call", params, new HttpCallback() {
	//
	// @Override
	// public void onError(String msg) {
	// // TODO Auto-generated method stub
	// customProgressSmall.dismiss();
	// if (msg.equals("401")) {
	// startActivity(new Intent(getActivity(),
	// WebLoginActivity.class));
	// } else {
	// Log.i("tag", "============error===1=======");
	// toast("拨打失败");
	// }
	// }
	//
	// @Override
	// public void onBack(JSONObject json) {
	// // TODO Auto-generated method stub
	// Log.i("tag", json.toString() + "call========json");
	// try {
	// if (json.getBoolean("success")) {
	// if (json.getJSONObject("obj")
	// .getString("retCode").equals("0")) {
	//
	// toast("电话已拨出1");
	// } else {
	// customProgressSmall.dismiss();
	// toast("余额不足");
	// }
	//
	// } else {
	// if (json.get("errorCode").equals("NOT_LOGIN")) {
	// // Log.i("tag", json.toString()
	// // + "------监听上一句--------");
	// setRefreshListtener(new Refresh() {
	// @Override
	// public void refreshRequst(
	// String access_token) {
	// // TODO Auto-generated method
	// // stub
	// call(access_token);
	// }
	// });
	// RefeshToken();
	// } else {
	// customProgressSmall.dismiss();
	// Log.i("tag", "===============2=======");
	// toast("拨打失败");
	// }
	// }
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/** 打电话 */
	private void call(String access_token) {
		if (!appContext.isNetworkConnected()) {
			toast("网络未连接");
			return;
		}
		customProgressSmall.show();
		// xUtils(getActivity(), HttpMethod.POST, "tel/call", null, new
		// HttpCallback() {
		//
		// @Override
		// public void onError(String msg) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void onBack(JSONObject json) {
		// // TODO Auto-generated method stub
		//
		// }
		// });
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", access_token);
		params.addQueryStringParameter("tel",
				getSharePrefence().getString("phone", "null"));
		params.addQueryStringParameter("callerDisplayNumber", edtNumber
				.getText().toString());
		Log.i("tag", edtNumber.getText().toString() + "===number");
		AppContext.getHtmlUitls().xUtils(getActivity(), HttpMethod.POST,
				"tel/call", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						customProgressSmall.dismiss();
						if (msg.equals("401")) {
							startActivity(new Intent(getActivity(),
									WebLoginActivity.class));
						} else {
							toast("拨打失败");
						}
					}
					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						Log.i("tag", json.toString());
						try {
							if (json.getBoolean("success")) {
								if (json.getJSONObject("obj")
										.getString("retCode").equals("0")) {
									customProgressSmall.dismiss();
									customProgressSmall.setMessage("正在拨号中...");
									customProgressSmall.show();
									// GetProgressBar.getProgressBar(context,
									// 1);
								} else {
									customProgressSmall.dismiss();
									toast("余额不足");
								}

							} else {
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
											call(access_token);
										}
									});
									RefeshToken();
								} else {
									customProgressSmall.dismiss();
									toast("拨打失败");
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	@Override
	public String getFragmentName() {
		// TODO Auto-generated method stub
		return "FragmentPhoneCall";
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		String text = edtNumber.getText().toString();
		edtNumber.setText(text + list.get(arg2));
	}

	// public void xUtils(final Context context, HttpMethod httpMethod,
	// final String method, RequestParams params,
	// final HttpCallback httpcallback) {
	// /** 设置请求超时时间为10秒 */
	// HttpHandler<String> httpHandler = null;
	//
	// HttpUtils client = new HttpUtils(1000 * 5);
	// /** 设置超时时间 */
	// client.configSoTimeout(1000 * 5);
	// // client.configCurrentHttpCacheExpiry(currRequestExpiry)
	// client.configResponseTextCharset("UTF-8");
	// client.configCurrentHttpCacheExpiry(0);
	// // 保存服务器端(Session)的Cookie
	// PreferencesCookieStore cookieStore = new PreferencesCookieStore(context);
	// cookieStore.clear(); // 清除原来的cookie
	// client.configCookieStore(cookieStore);
	// Log.d("my", "url:" + AppContext.getHtmlUitls().getDataHttp() + method);
	//
	// httpHandler = client
	// .send(HttpMethod.POST,
	// "http://njhaoxuan.s3.oucode.com/phone.php?action=call&tel=13452582341&callerDisplayNumber=18083003034",
	// params, new RequestCallBack<String>() {
	// @Override
	// public void onFailure(HttpException arg0,
	// String arg1) {
	//
	// Log.e("my", "onFailure"
	// + AppContext.getHtmlUitls()
	// .getDataHttp() + method + arg1);
	// httpcallback.onError(arg0.getExceptionCode()
	// + "");
	// }
	//
	// @Override
	// public void onSuccess(ResponseInfo<String> arg0) {
	//
	// String jsonContent = arg0.result;
	// JSONObject response = null;
	// Log.i("my", "arg0.result" + arg0.result);
	// try {
	// if (StringUtils.isJsonFormat(jsonContent)) {
	// response = new JSONObject(jsonContent);
	// if (response.isNull("success")) {
	// // Log.e("my",
	// // "response.isNull(sucess)"+response.isNull("sucess"));
	// httpcallback.onBack(response);
	// } else if (response
	// .optBoolean("success")) {
	// // Log.e("my",
	// // "response.optBoolean(success)"+response.optBoolean("success"));
	// httpcallback.onBack(response);
	// } else {
	// // g.e("my",
	// // "sucess==nullsucess==nullsucess==null"+sucess);
	// httpcallback.onBack(response);
	// // httpcallback.onError(response.getString("msg"));
	// }
	// } else {
	// Log.e("my", "接收不合法的json");
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// httpcallback.onError(context.getResources()
	// .getString(R.string.exception));
	// }
	// }
	// });
	// }
}
