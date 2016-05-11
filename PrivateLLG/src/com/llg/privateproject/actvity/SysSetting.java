package com.llg.privateproject.actvity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.utils.CommonUtils;
import com.llg.privateproject.utils.VersionUtils;
import com.llg.privateproject.view.OrderStatusDialog;
import com.llg.privateproject.view.OrderStatusDialog.IsOpen;
import com.tencent.android.tpush.XGPushManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 系统设置 yh 2015.10.16
 * */
public class SysSetting extends BaseActivity implements
		OnCheckedChangeListener, IsOpen {
	/** 返回 */
	@ViewInject(R.id.turn)
	private TextView turn;
	/** 标题 */
	@ViewInject(R.id.head_tilte)
	private TextView head_tilte;
	/** 是否开启推送 */
	@ViewInject(R.id.ispush)
	private CheckBox ispush;
	/** 是否开启红包弹窗 */
	@ViewInject(R.id.ispopup)
	private CheckBox ispopup;
	/** 是否开启app欢迎页 */
	@ViewInject(R.id.cb_welcome)
	private CheckBox cb_welcome;
	/** 检查新版本 */
	@ViewInject(R.id.newversion)
	private TextView newversion;
	/** 关于我们 */
	@ViewInject(R.id.aboutus)
	private TextView aboutus;
	/** 退出当前用户 */
	@ViewInject(R.id.exit_currentuser)
	private TextView exit_currentuser;
	private PackageManager packageManager;
	private PackageInfo packageInfo;
	OrderStatusDialog dialog;
	/** (1必须强制更新，2提示更新，3检查更新，4无需更新) */
	private int checkResult;
	private String url = "";
	private String sign = "";
	private static final String WELCOMESTATUS = "appWelcomeImage";
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case 1:// 返回成功
					checkedResult(checkResult, sign);
					break;

				default:
					break;
			}

		}

	};

	private void checkedResult(int checkResult, String sign) {

		if (checkResult == 1) {// 必须强制更新代码
			toast("当前版本:" + packageInfo.versionName + ",当前版本存在异常,请及时更新");
			if (dialog == null) {
				dialog = new OrderStatusDialog(SysSetting.this, 10, sign, url);
			}
			dialog.show();
			Window window = dialog.getWindow();
			android.view.WindowManager.LayoutParams lp = window.getAttributes();
			lp.width = AppContext.getScreenWidth() * 4 / 5;
			lp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
			window.setAttributes(lp);
		} else if (checkResult == 2) {// 提示更新
			toast("当前版本:" + packageInfo.versionName + ",有新版本可更新");
			if (dialog == null) {
				dialog = new OrderStatusDialog(SysSetting.this, 10, sign);
			}
			dialog.show();
			Window window = dialog.getWindow();
			android.view.WindowManager.LayoutParams lp = window.getAttributes();
			lp.width = AppContext.getScreenWidth() * 4 / 5;
			lp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
			window.setAttributes(lp);

		} else if (checkResult == 3) {// 检查更新
			toast("当前版本:" + packageInfo.versionName);

		} else if (checkResult == 4) {// 无需更新
			toast("当前版本:" + packageInfo.versionName + ",当前已是最新版本,无需更新");
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.syssetting);
		ViewUtils.inject(this);
		init();
	}

	private void init() {
		head_tilte.setText("系统设置");
		String version = VersionUtils.getVersionName(this);
		aboutus.setText("V" + version);
		ispush.setOnCheckedChangeListener(this);
		ispopup.setOnCheckedChangeListener(this);
		cb_welcome.setOnCheckedChangeListener(this);
		if (!UserInformation.isLogin()) {
			// exit_currentuser.setVisibility(View.GONE);
		} else {
			exit_currentuser.setVisibility(View.VISIBLE);
		}

		boolean app_image_status = getSharedPreferences(WELCOMESTATUS,
				Context.MODE_PRIVATE).getBoolean("welComeImage", true);
		cb_welcome.setChecked(app_image_status);
	}

	@OnClick({ R.id.turn, R.id.exit_currentuser, R.id.newversion, R.id.aboutus,
			R.id.clean })
	public void myClick(View v) {
		switch (v.getId()) {
			case R.id.turn:// 返回
				finish();
				break;
			case R.id.newversion:// 检查新版本
				if (packageManager == null) {
					packageManager = getPackageManager();
				}
				if (packageInfo == null) {
					try {
						packageInfo = packageManager.getPackageInfo(
								getPackageName(), 0);
					} catch (NameNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				// UpdateVersion.getServiceVersion(this, );
				// UpdateManager.getUpdateManager().checkAppUpdate(this, false);
				versionCheck(packageInfo.versionCode);
				break;
			case R.id.aboutus:// 关于我们
				Intent intent = new Intent(this, MyDialog.class);
				intent.putExtra("type", 2);
				intent.putExtra("title", "关于我们");
				startActivity(intent);

				break;
			case R.id.clean:// 清除缓存
				toast("缓存已清除!");
				CommonUtils.cleanCustomCache(CommonUtils.createSDCardDir());
				break;
			case R.id.exit_currentuser:// 退出当前用户
				loginout();
				clearMessage();
				finish();
				break;

		}
	}

	/** 清除本地信息 */
	private void clearMessage() {
		UserInformation.setLogin(false);
		AppContext.isLogin = false;
		SharedPreferences sharedPreferences = getSharedPreferences(
				"userInformation1", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("isLogin", false);

		editor.putString("password", "");
		editor.putString("userType", "");
		editor.putString("appellation", "");
		editor.putString("access_token", "");
		editor.putString("refresh_token", "");
		editor.putLong("tokenTime", 1000);
		editor.clear();
		editor.commit();
		AppContext.userType = "0";
		AppContext.appellation = "";

		UserInformation.setAccess_token("");
		UserInformation.setRefreshToken("");
		AppContext.isLogin = false;

		editor.commit();
		toast("您已退出当前用户");
	}

	private void loginout() {
		WebView wv = null;
		if (wv == null) {
			wv = new WebView(this);
		}
		// 清楚cookie
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();
		// getResources().getString(R.string.test_data_server_urlm)
		String url = getResources().getString(R.string.test_data_server_urlm)
				+ "logout?access_token="
				+ UserInformation.getAccess_token();
		wv.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		wv.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				// Log.d("my", url);
				view.loadUrl(url);
				return true;
			}
		});

		wv.loadUrl(url);

	}

	/**
	 * 检查版本更新
	 *
	 * @param :version 返回:checkResult:1~4 (1必须强制更新，2提示更新，3检查更新，4无需更新)
	 * */
	private void versionCheck(int versionCode) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("version", packageInfo.versionName);
		params.addQueryStringParameter("osType", String.valueOf(2));
		AppContext.getHtmlUitls().xUtils(this, HttpMethod.GET, "version/check",
				params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub

						try {
							if (json.getBoolean("success")) {
								JSONObject obj = json.getJSONObject("obj");
								checkResult = obj.getInt("checkResult");
								if (obj.get("updateLog") != null
										&& obj.getString("updateLog").length() > 0) {
									sign = obj.getString("updateLog");
								}
								if (obj.has("url") && obj.get("url") != null) {

									url = obj.getString("url");
									Log.d("my", "url" + url);
								}

							} else {
								// toast("当前版本:" + packageInfo.versionCode);
								toast("当前版本:" + packageInfo.versionName);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						handler.sendEmptyMessage(1);
					}
				});
	}

	OrderStatusDialog dialog1;
	OrderStatusDialog dialog2;

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch (buttonView.getId()) {
			case R.id.ispush:
				if (isChecked) {
					tui(isChecked);
					return;
				}
				if (dialog2 == null) {

					dialog2 = new OrderStatusDialog(SysSetting.this, 14, this);
				}
				showMydialog(dialog2);

				break;

			case R.id.ispopup:// 是否开启弹窗广告

				// ispopup.setChecked(!isChecked);
				if (isChecked) {
					tan(isChecked);
					return;
				}
				if (dialog1 == null) {

					dialog1 = new OrderStatusDialog(SysSetting.this, 12, this);
				}
				showMydialog(dialog1);
				break;

			case R.id.cb_welcome:
				//默认true 默认是开启欢迎页
//			LogManag.d("app welcome", isChecked+"");
				/** app欢迎页 */
				getSharedPreferences("appWelcomeImage", Context.MODE_PRIVATE)
						.edit().putBoolean("welComeImage", isChecked).commit();
				break;
		}

	}

	private void showMydialog(OrderStatusDialog dialog1) {
		dialog1.show();
		Window window = dialog1.getWindow();
		android.view.WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = AppContext.getScreenWidth() * 4 / 5;
		lp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
		window.setAttributes(lp);
	}

	@Override
	public void isOpen(boolean isOpen, int type) {
		// TODO Auto-generated method stub
		if (type == 12) {// 弹窗
			ispopup.setChecked(isOpen);
			tan(isOpen);
			if (dialog1 == null) {

				dialog1.cancel();
			}
		} else if (type == 14) {// 推送
			if (isOpen) {
				ispush.setChecked(isOpen);
				tui(isOpen);

			} else {
				XGPushManager.unregisterPush(getApplicationContext());

				toast("以关闭推送");
			}

			if (dialog2 == null) {

				dialog2.cancel();
			}
		}
	}

	private void tan(boolean isOpen) {
		getSharedPreferences("userInformation1", Context.MODE_PRIVATE).edit()
				.putBoolean("isOpen", isOpen).commit();
		AppContext.isOpen = isOpen;
	}

	private void tui(boolean isOpen) {
		getSharedPreferences("userInformation1", Context.MODE_PRIVATE).edit()
				.putBoolean("ispush", isOpen).commit();
		AppContext.ispush = isOpen;
		XGPushManager.registerPush(getApplicationContext(), AppContext.userid);
		toast("已开启推送");
	}
}
