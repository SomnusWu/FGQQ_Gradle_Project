package com.llg.privateproject.net;

import android.content.Context;
import android.util.Log;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.PreferencesCookieStore;

public class ApiTool {
	public static ApiTool apiTool;

	public static ApiTool getInstance() {
		if (apiTool == null) {
			apiTool = new ApiTool();
		}
		return apiTool;
	}

	public void getApi(Context context,final String mothed, String url, final ApiLisener apiLisener) {
		HttpUtils httpUtils = new HttpUtils();
		/** 设置超时时间 */
		httpUtils.configSoTimeout(1000 * 10);
		httpUtils.configResponseTextCharset("UTF-8");
		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				apiLisener.onFail(arg0, arg1);
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String json = arg0.result;
				apiLisener.onSuccess(json,mothed);
			}
		});
	}

	public void postApi(Context context,final String mothed, String url, RequestParams params,
			final ApiLisener apiLisener) {
		HttpUtils httpUtils = new HttpUtils();
		/** 设置超时时间 */
		httpUtils.configSoTimeout(1000 * 10);
		// client.configCurrentHttpCacheExpiry(currRequestExpiry)
		httpUtils.configResponseTextCharset("UTF-8");

		// 保存服务器端(Session)的Cookie
		PreferencesCookieStore cookieStore = new PreferencesCookieStore(context);
		cookieStore.clear(); // 清除原来的cookie
//		httpUtils.configCookieStore(cookieStore);
		Log.i("tag", url+"=============url");
		httpUtils.send(HttpMethod.POST, url+mothed, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						apiLisener.onFail(arg0, arg1);
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						String json = arg0.result;
						apiLisener.onSuccess(json,mothed);
					}

					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
						apiLisener.onPrepare();
					}
				});
	}
}
