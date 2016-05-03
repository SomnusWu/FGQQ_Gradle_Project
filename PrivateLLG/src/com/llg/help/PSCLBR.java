package com.llg.help;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.actvity.GetRedPackage;
import com.llg.privateproject.actvity.GetRedPackage1;
import com.llg.privateproject.actvity.WelcomeActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class PSCLBR extends BroadcastReceiver {
	Context context;
	AppContext appContext;
	private String s;
	private Map<String, Object> map;
	// Intent intent;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				Intent it = new Intent(context, GetRedPackage1.class);
				it.putExtra("isDialog", true);
				if (map.get("imageUrl")!= null) {
					it.putExtra("img", map.get("imageUrl").toString());
				} else {
					it.putExtra("img", "");
				}
				if (map.get("objectId").toString() != null) {
					it.putExtra("objectId", map.get("objectId").toString());
				} else {
					it.putExtra("objectId", "");
				}
				if (map.get("objectType").toString() != null) {
					it.putExtra("objectType", map.get("objectType").toString());
				} else {
					it.putExtra("objectType", "");
				}
				if (map.get("adRedEnvelopId").toString() != null) {
					it.putExtra("adRedEnvelopId", map.get("adRedEnvelopId")
							.toString());
				} else {
					it.putExtra("adRedEnvelopId", "");
				}
				if (map.get("adForwardId").toString() != null) {
					it.putExtra("adForwardId", map.get("adForwardId")
							.toString());
				} else {
					it.putExtra("adForwardId", "");
				}
				if (map.get("adInfoId").toString() != null) {
					it.putExtra("adInfoId", map.get("adInfoId").toString());
				} else {
					it.putExtra("adInfoId", "");
				}

				it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				context.startActivity(it);
				break;

			}
		}
	};

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		// System.out.println(""+intent.getAction());
		if(!AppContext.isOpen){
			return;
		}
		this.context = context;
		if (intent.getAction().equals(
				TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
			// Toast.makeText(context, "电话状态改变", Toast.LENGTH_SHORT).show();
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Service.TELEPHONY_SERVICE);
			tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		} else if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
			// Toast.makeText(context, "电话拨出", Toast.LENGTH_SHORT).show();
			// Intent it=new Intent(context, WelcomeActivity.class);
			// it.putExtra("isDialog", true);
			// it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// context.startActivity(it);
		}

	}

	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	/**
	 * 参数:1.cusId 当前登录人cusId 2.(*) locationCode 广告位code （APP_START_PAGE开始页滑动广告/
	 * APP_PHONE_ADIN打电话弹窗广告） 3.lng 手机当前所在经度 4.lat 手机当前所在纬度
	 */
	private void getAdList(String locationCode) {
		RequestParams params = new RequestParams();
//		params.addQueryStringParameter("cusId", AppContext.userid);
		// TelephonyManager tManager=(TelephonyManager)
		// context.getSystemService(Context.TELEPHONY_SERVICE);
		// params.addQueryStringParameter("appId", tManager.getDeviceId());//
		// 手机标识码
		// params.addQueryStringParameter("appId", AppContext.userid);// 手机标识码
		params.addQueryStringParameter("locationCode", locationCode);
		params.addQueryStringParameter("lng",
				String.valueOf(AppContext.myLongitude));
		params.addQueryStringParameter("lat",
				String.valueOf(AppContext.myLatitude));
		params.addQueryStringParameter("cusId",
				String.valueOf(AppContext.userid));

		HttpUtils client = new HttpUtils(1000 * 5);
		/** 设置超时时间 */
		client.configSoTimeout(1000 * 5);
		// client.configCurrentHttpCacheExpiry(currRequestExpiry)
		client.configResponseTextCharset("UTF-8");
		client.send(HttpMethod.GET, AppContext.getHtmlUitls().getDataHttp()
				+ "ad/getAdList", params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				try {
					JSONObject json = new JSONObject(arg0.result);
					if (json.getBoolean("success")
							&& json.getJSONArray("obj") != null) {
						JSONArray obj = json.getJSONArray("obj");
						if (obj.length() > 0) {
							JSONObject object = obj.getJSONObject(0);
							if (map == null) {
								map = new HashMap<String, Object>();
							}
							map.clear();

							map.put("imageUrl", object.getString("img"));
							map.put("objectId", object.getString("objectId"));
							map.put("objectType",
									object.getString("objectType"));
							map.put("adRedEnvelopId",
									object.getString("adRedEnvelopId"));
							map.put("adForwardId",
									object.getString("adForwardId"));
							map.put("adInfoId", object.getString("id"));
						}else{
							return;
						}
						handler.sendEmptyMessage(1);

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}


	PhoneStateListener listener = new PhoneStateListener() {
		public void onCallStateChanged(int state, String incomingNumber) {
			// Intent it=new Intent(context, GetRedPackage.class);
			switch (state) {
			case TelephonyManager.CALL_STATE_IDLE:
				
			
			
				
				if (isNetworkConnected()) {
					// getAd();
					getAdList("APP_PHONE_ADIN");
				}

				break;
			case TelephonyManager.CALL_STATE_RINGING:
				// Toast.makeText(context, "响铃响起", Toast.LENGTH_SHORT).show();
				//
				// it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// context.startActivity(it);
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				// Toast.makeText(context, "接通电话", Toast.LENGTH_SHORT).show();
				break;

			}
		}
	};
}
