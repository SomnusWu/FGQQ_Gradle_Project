package com.llg.privateproject.fragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.bjg.lcc.jsonparser.ParseJson;
import com.bjg.lcc.privateproject.R;
import com.king.photo.util.Bimp;
import com.king.photo.util.ImageItem;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.llg.help.ScreenManager;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.actvity.LoginActivity;
import com.llg.privateproject.actvity.WebLoginActivity;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.utils.GetFileSize;
import com.llg.privateproject.utils.ImageTools;
import com.llg.privateproject.utils.StringUtils;
import com.llg.privateproject.view.CustomProgressSmall;
import com.smartandroid.sa.view.AutoLoading;

public class BaseActivity extends Activity {
	public AppContext appContext;
	private Resources res;
	private String mProgressMessage = "数据加载中...";
	private BitmapUtils bitmapUtils;
	private boolean isShow = true;
	private String imgID = "";// 图片id 一组图片
	private String imgName = ""; // 图片id , (上传时的key)
	public AutoLoading autoLoading;
	private String client_id = "mobile-client";
	private String client_secret = "mobile";
	private String refreshUrl = "http://192.168.0.220:8083/buyapp/oauth/token";
	private Refresh refresh;
	private RefreshImgID refreshImgID;
	public CustomProgressSmall customProgressSmall;
	public BaseActivity activity;
	Timer timer;
	// public Handler mHandler;
	public ProgressDialog mProgressDialog;

	public BaseActivity() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示标
		super.onCreate(savedInstanceState);
		int inputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
		getWindow().setSoftInputMode(inputMode);
		// overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
		// mHandler = new Handler(this);
		res = getResources(); // 通用资源缩写
		customProgressSmall = CustomProgressSmall.initDialog(this, "正在加载中",
				true, new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface arg0) {
						// TODO Auto-generated method stub
						customProgressSmall.dismiss();
					}
				});
		appContext = (AppContext) getApplication();
		if (appContext.isNetworkConnected()) {
			// temer();
		}
		ScreenManager.getScreenManager().pushActivity(this);

	}

	public void initAutoLoading(View view) {
		autoLoading = new AutoLoading(this, view);
		autoLoading.showLoadingLayout();
	}

	public void initAutoLoadingNoShow(View view) {
		autoLoading = new AutoLoading(this, view);
	}

	/** 自动登录 */
	public void temer() {
		if (timer == null)
			timer = new Timer();

		// timer.schedule(new TimerTask() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// if (appContext.isNetworkConnected()) {
		// SharedPreferences userPreferences = getSharedPreferences(
		// "userInformation1", Context.MODE_PRIVATE);
		// String str1 = userPreferences.getString("username", "");
		// String str2 = userPreferences.getString("password", "");
		// Log.i("tag", "-------------进入了计时器");
		// if (str1.length() > 1 && str2.length() > 1) {
		// login();
		// }
		// }
		// }
		// }, 0, 1000 * 20);

	}

	// class ConnectionChangeReceiver extends BroadcastReceiver {
	// @Override
	// public void onReceive(Context context, Intent intent) {
	// ConnectivityManager connectivityManager = (ConnectivityManager) context
	// .getSystemService(Context.CONNECTIVITY_SERVICE);
	// NetworkInfo mobNetInfo = connectivityManager
	// .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	// NetworkInfo wifiNetInfo = connectivityManager
	// .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	//
	// if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
	// // BSToast.showLong(context, "网络不可以用");
	// // 改变背景或者 处理网络的全局变量
	// doAction1();
	// } else {
	// // 改变背景或者 处理网络的全局变量
	// doAction2();
	// }
	// }
	// }
	public BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mobNetInfo = connectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			NetworkInfo wifiNetInfo = connectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

			if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
				// BSToast.showLong(context, "网络不可以用");
				// 改变背景或者 处理网络的全局变量
				doAction1();
			} else {
				// 改变背景或者 处理网络的全局变量
				doAction2();
			}
		}

	};

	public void registerReceiver() {
		IntentFilter filter = new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION);
		this.registerReceiver(receiver, filter);
	}

	protected void doAction1() {
		Log.i("tag", "执行了父");
	}

	protected void doAction2() {
		Log.i("tag", "执行了父");
	}

	public AppContext getAppContext() {
		if (appContext == null) {
			appContext = (AppContext) getApplication();
		}
		return appContext;
	}

	public void toast(int resId) {
		Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
	}

	public void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	public void doBack(View v) {
		finish();
	}

	public void myLogShow(String tag, String msg) {
		if (isShow) {
			Log.d(tag, msg);
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	public SpannableStringBuilder getSpannableStringBuilder(String text,
			String str) {
		String s = String.format(text, str);
		SpannableStringBuilder srb = new SpannableStringBuilder(s);
		int index = s.indexOf(str);
		// 设置字体颜色
		ForegroundColorSpan fcolor = new ForegroundColorSpan(getResources()
				.getColor(R.color.orange));
		srb.setSpan(fcolor, index, index + str.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		// 设置字体加粗
		srb.setSpan(new StyleSpan(Typeface.BOLD), index, index + str.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return srb;
	}

	/**
	 * 从资源获取字符串
	 * 
	 */
	public String getStr(int resId) {
		return res.getString(resId);
	}

	/** 获取资源颜色 */
	public int getMyColor(int resId) {
		return res.getColor(resId);
	}

	/** 获取资源尺寸 */
	public int getDimen(int resId) {
		return res.getDimensionPixelSize(resId);
	}

	/**
	 * 从EditText 获取字符
	 * 
	 * @param editText
	 * @return
	 */
	public String getStr(EditText editText) {
		return editText.getText().toString();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	public boolean isNull(String str) {
		return null == str || "".equals(str) || "null".equalsIgnoreCase(str);
	}

	public String getfilterString(String str) {
		String value = str.trim();
		if (isNull(str)) {
			value = " ";
		}
		return value;

	}

	// 上传单张图片
	public void uploadPhotoSign(final File file, String access_token,
			final Context context, final boolean isUpLoad) {
		customProgressSmall.show();
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", UserInformation.getAccess_token());

		params.addBodyParameter("img" + 0, file);

		params.addHeader("X-Requested-With", "XMLHttpRequest");
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/sys/saveIconToMore", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						if (msg.equals("404")) {
							toast("BaseActivity" + "-**-WebLoginActivity");
							Intent intent = new Intent(BaseActivity.this,
									WebLoginActivity.class);
							startActivity(intent);
							finish();
						} else {
							toast("上传失败,请重试!" + msg);
							return;
						}
						customProgressSmall.dismiss();
						// Toast.makeText(BaseActivity.this, msg,
						// Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						ParseJson parseJson = ParseJson.getParseJson();
						Map<String, Object> map = parseJson
								.parseIsSuccess(json);
						// customProgressSmall.dismiss();
						if ((Boolean) map.get("isSuccess")) {
							customProgressSmall.dismiss();
							try {
								if (isUpLoad) {
									imgID = json.getJSONArray("result")
											.getJSONObject(0).toString();
								} else {
									imgID = json.getJSONArray("result")
											.getJSONObject(0).getString("id");
								}

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							// imgName = parseJson.parseimgName(json);

							Log.d("tag", json.toString());
							Log.d("tag", imgID + "------");

							refreshImgID.refreshImgID(imgID, imgName);

						} else if (map.get("errorCode").equals("NOT_LOGIN")) {
							// Log.i("tag", json.toString()
							// + "------监听上一句--------");
							setRefreshListtener(new Refresh() {
								@Override
								public void refreshRequst(String access_token) {
									// TODO Auto-generated method stub
									uploadPhoto(file, access_token, context);
								}
							});
							RefeshToken();
						}
					}
				});
	}

	// 上传图片
	public void uploadPhoto(final File file, String access_token,
			final Context context) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", UserInformation.getAccess_token());
		List<ImageItem> mList = Bimp.tempSelectBitmap;
		// 封面广告图片
		if (Bimp.getmFMImgItem() != null
				&& !StringUtils.isEmpty(Bimp.getmFMImgItem().imagePath)) {
			File mFile = new File(Bimp.getmFMImgItem().imagePath);
			/*
			 * if(!checkFileSize(mFile)){ toast("封面图片大于3M,请上传小于3M的图片"); return;
			 * }
			 */
			params.addBodyParameter("img_fm", ImageTools.scal(mFile));
			// params.addBodyParameter("img_fm", mFile);
		}
		// 广告图片
		for (int i = 0; i < mList.size(); i++) {
			File mFile = new File(mList.get(i).getImagePath());
			/*
			 * if(!checkFileSize(mFile)){
			 * toast("内容图片第"+(i+1)+"张图片大于3M,请上传小于3M的图片."); return; }
			 */
			// params.addBodyParameter("img" + i, mFile);
			params.addBodyParameter("img" + i, ImageTools.scal(mFile));
		}

		params.addHeader("X-Requested-With", "XMLHttpRequest");
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/sys/saveIconToMore", params, 20 * mList.size() * 1000,
				new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						if (msg.equals("404")) {
							toast("BaseActivity" + "-**-WebLoginActivity");
							Intent intent = new Intent(BaseActivity.this,
									WebLoginActivity.class);
							startActivity(intent);
							finish();
						} else {
							customProgressSmall.dismiss();
							Toast.makeText(BaseActivity.this,
									"由于图片太大,网速太慢,上传图片超时,请稍后再试!" + msg,
									Toast.LENGTH_SHORT).show();
						}

					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						ParseJson parseJson = ParseJson.getParseJson();
						Map<String, Object> map = parseJson
								.parseIsSuccess(json);
						// customProgressSmall.dismiss();
						if ((Boolean) map.get("isSuccess")) {
							customProgressSmall.dismiss();
							// imgID = parseJson.parseimgId(json);
							Map img_map = parseJson.parseimgId(json);
							for (int i = 0; i < img_map.size(); i++) {

								if (img_map.size() == i + 1) {
									imgID += img_map.get("img" + i);
									break;
								} else {
									imgID += img_map.get("img" + i) + ",";
								}
							}
							imgName = parseJson.parseimgName(json);

							Log.d("tag", json.toString());
							// Log.d("tag", imgID + "------");
							// Log.d("tag", imgName + "----");

							refreshImgID.refreshImgID(imgID, imgName);

						} else {
							if (map.get("errorCode").equals("NOT_LOGIN")) {
								// Log.i("tag", json.toString()
								// + "------监听上一句--------");
								setRefreshListtener(new Refresh() {
									@Override
									public void refreshRequst(
											String access_token) {
										// TODO Auto-generated method stub
										uploadPhoto(file, access_token, context);
									}
								});
								RefeshToken();
							} else {
								toast("由于图片太大,网速太慢,上传图片超时,请稍后再试!"
										+ map.get("msg").toString());

							}
						}
					}
				});
	}

	/**
	 * 判断文件大小
	 * 
	 * @param file
	 */
	public boolean checkFileSize(File file) {
		boolean flag = true;
		GetFileSize g = new GetFileSize();
		try {
			long l = g.getFileSizes(file);
			if (l < 3 * 1024 * 1024) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public interface RefreshImgID {
		void refreshImgID(String imgID, String imgName);
	}

	public void setRefreshListener(RefreshImgID refreshImgID) {
		this.refreshImgID = refreshImgID;
	}

	/**
	 * 字符串是否是字符
	 * 
	 * @param str
	 * @return 为空返回true,不为空返回false
	 */
	public boolean isStr(String str) {
		return !isNull(str);
	}

	/**
	 * 描述：显示进度框.
	 */
	public void showProgressDialog() {
		showProgressDialog(null);
	}

	/**
	 * 描述：显示进度框.
	 * 
	 * @param message
	 *            the message
	 */
	public void showProgressDialog(String message) {
		// 创建一个显示进度的Dialog
		if (message != null) {
			mProgressMessage = message;
		}
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(this);
			// 设置点击屏幕Dialog不消失
			mProgressDialog.setCanceledOnTouchOutside(false);
		}
		mProgressDialog.setMessage(mProgressMessage);
		mProgressDialog.show();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		SharedPreferences myLoction = getSharedPreferences("myLoction",
				Context.MODE_PRIVATE);
		Editor editor = myLoction.edit();

		editor.putFloat("myLatitude", (float) AppContext.myLatitude);
		editor.putFloat("myLongitude", (float) AppContext.myLongitude);
		editor.putString("myCity", AppContext.myCity);
		editor.putString("myCityCode", AppContext.myCityCode);
		editor.commit();
	}

	/**
	 * @return 返回一个sharePrefences对象
	 */
	public SharedPreferences getSharePrefence() {
		return getSharedPreferences("userInformation1", Context.MODE_PRIVATE);
	}

	/**
	 * 描述：移除进度框.
	 */
	public void removeProgressDialog() {
		mProgressDialog.cancel();
	}

	/**
	 * 描述：获取进度框显示的文字.
	 * 
	 * @return the progress message
	 */
	public String getProgressMessage() {
		return mProgressMessage;
	}

	/**
	 * 描述：设置进度框显示的文字.
	 * 
	 * @param message
	 *            the new progress message
	 */
	public void setProgressMessage(String message) {
		this.mProgressMessage = message;
	}

	/**
	 * 描述：对话框dialog （确认，取消）.
	 * 
	 * @param title
	 *            对话框标题内容
	 * @param msg
	 *            对话框提示内容
	 * @param mOkOnClickListener
	 *            点击确认按钮的事件监听
	 */
	public void showDialog(String title, String msg,
			DialogInterface.OnClickListener mOkOnClickListener) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage(msg);
		builder.setTitle(title);
		builder.setPositiveButton("确认", mOkOnClickListener);
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	public void showDialog(String title, String msg, String btn) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage(msg);
		builder.setTitle(title);
		builder.setNegativeButton(btn, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	public AlertDialog showDialog(String title, View view,
			DialogInterface.OnClickListener mOkOnClickListener) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle(title);
		builder.setView(view);
		builder.setPositiveButton("确认", mOkOnClickListener);
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		AlertDialog mAlertDialog = builder.create();
		mAlertDialog.show();
		return mAlertDialog;
	}

	/**
	 * 描述：对话框dialog （无按钮）.
	 * 
	 * @param title
	 *            对话框标题内容
	 * @param msg
	 *            对话框提示内容
	 */
	public AlertDialog showDialog(String title, String msg) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage(msg);
		builder.setTitle(title);
		builder.create();
		AlertDialog mAlertDialog = builder.create();
		mAlertDialog.show();
		return mAlertDialog;
	}

	/**
	 * 描述：对话框dialog （无按钮）.
	 * 
	 * @param title
	 *            对话框标题内容
	 * @param view
	 *            对话框提示内容
	 */
	public AlertDialog showDialog(String title, View view) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle(title);
		builder.setView(view);
		builder.create();
		AlertDialog mAlertDialog = builder.create();
		mAlertDialog.show();
		return mAlertDialog;
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	public void startActivityClear(Class<?> cls) {
		Intent intent;
		intent = new Intent(this, cls);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		super.startActivity(intent);
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	public void startActivity(Class<?> cls) {
		Intent intent;
		intent = new Intent(this, cls);
		startActivity(intent);
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	@Override
	public void finish() {
		Log.i("tag",
				"=================================================================");
		super.finish();
		Log.i("tag",
				"**********************************************************************");
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	// /** 用view显示网址为URL的图片 */
	// public void setBitmap(View view, String url) {
	// if (bitmapUtils == null) {
	// bitmapUtils = new BitmapUtils(this, CommonUtils.createSDCardDir())
	// .configDefaultLoadFailedImage(
	// R.drawable.bjg_tupjiazai_jiazaishuabai)
	// .configDefaultLoadingImage(R.drawable.defaultpic);
	// }
	//
	// bitmapUtils.display(view, url);
	// }

	/**
	 * 如果连接网络才跳转到另一个Activity 传入intent=new Intent(this, Activity.class);
	 * */
	public void isNetworkConnectedJump(Intent intent) {
		if (appContext.isNetworkConnected()) {
			if (intent != null) {
				startActivity(intent);
			}
		} else {
			toast(R.string.t_Connection);
		}
	}

	/**
	 * 如果连接网络且登录才跳转到另一个Activity 传入intent=new Intent(this, Activity.class);
	 * 
	 * */
	public void isLoginJump(Intent intent) {
		if (UserInformation.isLogin()) {
			isNetworkConnectedJump(intent);
		} else {
			toast("亲,为保障您的账户安全请先登录");
			startActivity(new Intent(this, LoginActivity.class));
		}
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
						Log.i("tag", "----------登录失败----");
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
							Log.i("tag", "----------登录成功----");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}

	// public void MyRefeshToken() {
	// final Map<String, String> params = new HashMap<String, String>();
	// params.put("client_id", "mobile-client");
	// params.put("client_secret", "mobile");
	// params.put("grant_type", "refresh_token");
	// params.put("refresh_token", "9ceba613-ead4-4626-af02-32266f5be7ea");
	// final String body = "";
	// try {
	// new Thread() {
	// public void run() {
	// String body = (HttpXmlClient.post(
	// "http://192.168.0.220:8083/buyapp/oauth/token",
	// params));
	// Log.i("tag",
	// "------+++MyRefeshToken++++----RefeshToken----"
	// + body);
	// };
	// }.start();
	// } catch (Exception e) {
	// Log.i("tag",
	// "------+++MyRefeshToken++++----RefeshToken----"
	// + e.getMessage());
	// // TODO: handle exception
	// }
	//
	// Log.i("tag", "------+++MyRefeshToken++++----RefeshToken----" + body);
	//
	// }

	/**
	 * 刷新token
	 */
	public void RefeshToken() {
		AppContext.isLogin = false;
		// final SharedPreferences sharedPreferences = getSharedPreferences(
		// "userInformation1", Context.MODE_PRIVATE);
		String refresh_token = UserInformation
				.getRefreshToken();
		RequestParams params = new RequestParams();
		params.addBodyParameter("client_id", client_id);
		params.addBodyParameter("client_secret", client_secret);
		params.addBodyParameter("grant_type", "refresh_token");
		params.addHeader("X-Requested-With", "XMLHttpRequest");
		// Log.i("tag", "------+++RefeshToken++++----RefeshToken----");
		// if (refresh_token != null && refresh_token.length() > 0) {
		Log.i("tag", refresh_token
				+ "------+执行刷新token方法传入的++RefeshToken值++++-------");
		params.addBodyParameter("refresh_token", refresh_token);
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST, "oauth/token",
				params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						Log.i("tag", msg + "---------RefeshToken失败----");
						if (msg.equals("404")) {
							Intent intent = new Intent(ScreenManager
									.getScreenManager().currentActivity(),
									WebLoginActivity.class);
							startActivity(intent);
							if (autoLoading != null) {
								autoLoading.hideAll();
							}
						} else {
							if (autoLoading != null) {
								autoLoading.showExceptionLayout();
							}
						}
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						// Log.i("tag", I.toString()
						// + "------+++onBack++++----RefeshToken----");
						String access_token = json.optString("access_token");
						String refresh_token = json.optString("refresh_token");
						// Log.i("tag",
						// refresh_token
						// + "-----RefeshToken()++onback+--refresh_token值--");
						if (access_token != null && refresh_token != null
								&& access_token.length() > 0
								&& refresh_token.length() > 0) {
							getSharePrefence().edit()
									.putString("access_token", access_token)
									.putString("refresh_token", refresh_token)
									.commit();
							UserInformation.setAccess_token(access_token);
							UserInformation.setRefreshToken(refresh_token);
							refresh.refreshRequst(access_token);
							AppContext.isLogin = true;
							UserInformation.setLogin(true);
							// Log.i("tag", "---提交了两个token值到本地文件----");
						} else {
							// toast("BaseActivity" +
							// "-**-onback-WebLoginActivity");
							Log.i("tag", "---------onback-else---跳转-");
							Intent intent = new Intent(BaseActivity.this,
									WebLoginActivity.class);
							startActivity(intent);
						}
					}
				});
	}

	// }
	// /**
	// * 如果是页面显示出来后，再调的的接口去刷新调用这种
	// */
	// public void RefeshTokenSecend() {
	// // final SharedPreferences sharedPreferences = getSharedPreferences(
	// // "userInformation1", Context.MODE_PRIVATE);
	// String refresh_token = AppContext.getUserInformation()
	// .getRefreshToken();
	// RequestParams params = new RequestParams();
	// params.addBodyParameter("client_id", client_id);
	// params.addBodyParameter("client_secret", client_secret);
	// params.addBodyParameter("grant_type", "refresh_token");
	// params.addHeader("X-Requested-With", "XMLHttpRequest");
	// // Log.i("tag", "------+++RefeshToken++++----RefeshToken----");
	// if (refresh_token != null && refresh_token.length() > 0) {
	// Log.i("tag", refresh_token
	// + "------+执行刷新token方法传入的++RefeshToken值++++-------");
	// params.addBodyParameter("refresh_token", refresh_token);
	// AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
	// "oauth/token", params, new HttpCallback() {
	//
	// @Override
	// public void onError(String msg) {
	// // TODO Auto-generated method stub
	// Log.i("tag", msg + "---------RefeshToken失败----");
	// if (msg.equals("404")) {
	// toast("BaseActivity" + "-**-WebLoginActivity");
	// ScreenManager.getScreenManager().popActivity();
	// Intent intent = new Intent(ScreenManager
	// .getScreenManager().currentActivity(),
	// WebLoginActivity.class);
	// intent.putExtra("isFinishTop", false);
	// startActivity(intent);
	// if (autoLoading != null) {
	// autoLoading.hideAll();
	// }
	// } else {
	// if (autoLoading != null) {
	// autoLoading.showExceptionLayout();
	// }
	// }
	// }
	//
	// @Override
	// public void onBack(JSONObject json) {
	// // TODO Auto-generated method stub
	// Log.i("tag", json.toString()
	// + "------+++onBack++++----RefeshToken----");
	// String access_token = json
	// .optString("access_token");
	// String refresh_token = json
	// .optString("refresh_token");
	// Log.i("tag",
	// refresh_token
	// + "-----RefeshToken()++onback+--refresh_token值--");
	// if (access_token != null && refresh_token != null
	// && access_token.length() > 0
	// && refresh_token.length() > 0) {
	// getSharePrefence()
	// .edit()
	// .putString("access_token", access_token)
	// .putString("refresh_token",
	// refresh_token).commit();
	// UserInformation.setAccess_token(access_token);
	// UserInformation.setRefreshToken(refresh_token);
	// refresh.refreshRequst(access_token);
	// Log.i("tag", "---提交了两个token值到本地文件----");
	// } else {
	// toast("BaseActivity" + "-**-WebLoginActivity");
	// Intent intent = new Intent(BaseActivity.this,
	// WebLoginActivity.class);
	// startActivity(intent);
	// finish();
	// }
	// }
	// });
	// }
	// }

	// /**
	// * 刷新token
	// */
	// public void RefeshTokenSecend() {
	// // final SharedPreferences sharedPreferences = getSharedPreferences(
	// // "userInformation1", Context.MODE_PRIVATE);
	// String refresh_token = AppContext.getUserInformation()
	// .getRefreshToken();
	// RequestParams params = new RequestParams();
	// params.addBodyParameter("client_id", client_id);
	// params.addBodyParameter("client_secret", client_secret);
	// params.addBodyParameter("grant_type", "refresh_token");
	// params.addHeader("X-Requested-With", "XMLHttpRequest");
	// // Log.i("tag", "------+++RefeshToken++++----RefeshToken----");
	// if (refresh_token != null && refresh_token.length() > 0) {
	// Log.i("tag", refresh_token
	// + "------+执行刷新token方法传入的++RefeshToken值++++-------");
	// params.addBodyParameter("refresh_token", refresh_token);
	// AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
	// "oauth/token", params, new HttpCallback() {
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
	// Log.i("tag", json.toString()
	// + "------+++onBack++++----RefeshToken----");
	// String access_token = json
	// .optString("access_token");
	// String refresh_token = json
	// .optString("refresh_token");
	// Log.i("tag",
	// refresh_token
	// + "-----RefeshToken()++onback+--refresh_token值--");
	// if (access_token != null && refresh_token != null
	// && access_token.length() > 0
	// && refresh_token.length() > 0) {
	// getSharePrefence()
	// .edit()
	// .putString("access_token", access_token)
	// .putString("refresh_token",
	// refresh_token).commit();
	// UserInformation.setAccess_token(access_token);
	// UserInformation.setRefreshToken(refresh_token);
	// refresh.refreshRequst(access_token);
	// Log.i("tag", "---提交了两个token值到本地文件----");
	// } else {
	//
	// }
	// }
	// });
	// }
	// }

	public void setRefreshListtener(Refresh refresh) {
		this.refresh = refresh;
	}

	public interface Refresh {
		void refreshRequst(String access_token);
	}

	/**
	 * @param errorTime出现401错误的时间
	 * @param time
	 *            获取到token的时间后的time时间的数值
	 * @return 是否token失效引起的401错误
	 */
	protected boolean isTokenReson(Long errorTime, int timeNumber,
			Long tokenTime) {
		Long timeSection = getDate(timeNumber, tokenTime) - errorTime;
		return !(timeSection > 0 && timeSection < 50);
	}

	/**
	 * @param timeNumber
	 * @return 获取当前时间多少分钟后的时间戳
	 */
	public static Long getDate(int timeNumber, Long tokenTime) {
		// Date d = new Date();
		// d.setTime(tokenTime);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(tokenTime);
		cal.set(Calendar.HOUR, cal.get(Calendar.HOUR));
		cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + timeNumber);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long time = Long.parseLong(date2TimeStamp(sdf.format(cal.getTime())));
		return time;
	}

	/**
	 * 日期格式字符串转换成时间戳
	 * 
	 * @param date
	 *            字符串日期
	 * @param format
	 *            如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String date2TimeStamp(String date_str) {
		String format = "yyyy-MM-dd HH:mm:ss";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return String.valueOf(sdf.parse(date_str).getTime() / 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 取得当前时间戳（精确到秒）
	 * 
	 * @return
	 */
	public static String timeStamp() {
		long time = System.currentTimeMillis();
		String t = String.valueOf(time / 1000);
		return t;
	}

	public void goWebLoginActivity() {
		// toast("BaseActivity" + "-**-WebLoginActivity");
		Intent intent = new Intent(this, WebLoginActivity.class);
		startActivity(intent);
	}

	public void onReturn(View v) {
		finish();
	}
}
