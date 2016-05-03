package com.llg.privateproject.actvity;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;

public class MyFragmentActivity extends FragmentActivity {
	AppContext appContext;
	Timer timer;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		appContext = (AppContext) getApplication();
		// if(appContext.isNetworkConnected()){
		// temer();
		// }else{
		// Toast.makeText(this, R.string.t_Connection,
		// Toast.LENGTH_SHORT).show();
		// }
	}

	/** 自动登录 */
	public void temer() {
		if (timer == null)
			timer = new Timer();
		if (appContext == null) {
			appContext = (AppContext) getApplication();
		}
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (appContext.isNetworkConnected()) {
					SharedPreferences userPreferences = getSharedPreferences(
							"userInformation1", Context.MODE_PRIVATE);
					String str1 = userPreferences.getString("username", "");
					String str2 = userPreferences.getString("password", "");

					if (str1.length() > 1 && str2.length() > 1) {
						login();
					}
				}
			}
		}, 0, 1000 * 20);

	}

	/** 登录 */
	private void login() {

		SharedPreferences userPreferences = getSharedPreferences(
				"userInformation1", Context.MODE_PRIVATE);
		// XGPushManager.registerPush(getApplicationContext(),userPreferences.getString("username",
		// ""));

		RequestParams params = new RequestParams();
		params.addBodyParameter("client_id", "mobile-client");
		params.addBodyParameter("client_secret", "mobile");
		params.addBodyParameter("grant_type", "password");
		params.addBodyParameter("username",
				userPreferences.getString("username", ""));
		params.addBodyParameter("password",
				userPreferences.getString("password", ""));
		params.addBodyParameter("scope", "read+write");

		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				AppContext.setLogurl(), params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

						UserInformation.setLogin(false);
						AppContext.isLogin = false;
						getSharedPreferences("userInformation1",
								Context.MODE_PRIVATE).edit()
								.putBoolean("isLogin", false).commit();
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub

						try {
							UserInformation.setAccess_token(
									json.get("access_token").toString());
							UserInformation.setLogin(true);
							AppContext.isLogin = true;
							getSharedPreferences("userInformation1",
									Context.MODE_PRIVATE).edit()
									.putBoolean("isLogin", true).commit();
							getSharedPreferences("userInformation1",
									Context.MODE_PRIVATE)
									.edit()
									.putString("access_token",
											json.get("access_token").toString())
									.commit();

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (timer != null)
			timer.cancel();
	}
}
