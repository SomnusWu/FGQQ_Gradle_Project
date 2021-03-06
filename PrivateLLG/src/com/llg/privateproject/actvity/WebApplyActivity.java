package com.llg.privateproject.actvity;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.util.StringUtils;
import com.bjg.lcc.alipay.pay.PayActivity;
import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.help.Util;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.camera.PopupSelectImage;
import com.llg.privateproject.entities.PayInfoModel;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.utils.LogManag;
import com.smartandroid.sa.json.Gson;

/**
 * 申请商家页面
 * 
 * @author cc
 *
 */
public class WebApplyActivity extends BaseActivity {
	private String url = "";
	private String buy_server = "";
	@ViewInject(R.id.mwebView)
	private WebView webView;
	private ProgressDialog progressDialog;
	private static String imgId = "";
	private static final int REQUEST_SELECT_TX_IMG_ID = 1006;

	// @ViewInject(R.id.head_tilte)
	// private TextView head_tilte;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_weblogin);

		buy_server = getResources().getString(R.string.buyserver);
		url = buy_server + "m/applyShop";
		// url = "http://192.168.0.196:8082/llgweb/home/testAndroidLoadFile2";
//		url = "http://192.168.0.196:8083/buyapp/common/testAndroidLoadFile";
		// url = "file:///android_asset/testAndroidLoadFile.html";

		initWebviewSetting();
		// head_tilte.setText("申请商家");
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				// 需要跳转到 支付
				if (url.startsWith(buy_server + "pay/payPage")) {
					// Toast.makeText(WebLoginActivity.this, "注册成功",
					// Toast.LENGTH_SHORT).show();
					Map<String, String> mapRequest = Util.URLRequest(url);
					String order_id = mapRequest.get("orderPayId");// 取得状态码
					// 根据id来请求 支付所需的信息
					requestPayInfo(order_id);
				}

				 view.loadUrl(url);
				// webView.loadDataWithBaseURL(local, text, "text/html",
				// "utf-8", null);

				/*
				 * String local = "file:///android_asset";
				 * 
				 * try { InputStream in =
				 * getAssets().open("testAndroidLoadFile.html"); int size =
				 * in.available();// 取得数据流的数据大小 byte[] buffer = new byte[size];
				 * in.read(buffer); in.close(); String text = new
				 * String(buffer);
				 * 
				 * webView.loadDataWithBaseURL(local, text, "text/html",
				 * "utf-8", null); } catch (IOException e) { // TODO
				 * Auto-generated catch block e.printStackTrace(); }
				 */

				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				if (!progressDialog.isShowing()) { // 网页开始加载时，显示进度条。
					progressDialog.show();
				}
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				if (progressDialog.isShowing()) { // 加载完毕后，进度条不显示
					progressDialog.dismiss();
				}
			}

		});
		// 网页加载进度条
		progressDialog = ProgressDialog.show(this, null, "正在加载,请稍后...");

	}

	public class JavaScriptObject {
		Context mContxt;

		// sdk17版本以上加上注解
		public JavaScriptObject(Context mContxt) {
			this.mContxt = mContxt;
		}

		/** 自定义截取上传文件方法 */
		@JavascriptInterface
		public void runUploadImg() {
			runOnUiThread(new Runnable() {
				public void run() {
					// Toast.makeText(WebApplyActivity.this, "RunUploadimag",
					// Toast.LENGTH_SHORT).show();
					LogManag.d("runUploadImg", "执行runUploadImg方法");
					// webView.loadUrl("javascript:setUploadImgData('" + mes +
					// "')");
					Intent intent = new Intent(WebApplyActivity.this,
							PopupSelectImage.class);
					intent.putExtra("type_s", "002");
					startActivityForResult(intent, REQUEST_SELECT_TX_IMG_ID);
				}
			});

		}

		/**
		 * 返回按钮监听
		 */
		@JavascriptInterface
		public void backFun() {
			finish();
		}

		public void test() {
			webView.loadUrl("javascript:testJS()");
		}
	}

	@SuppressLint("JavascriptInterface")
	private void initWebviewSetting() {
		ViewUtils.inject(this);
		// 设置支持JavaScript脚本
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		// 设置可以访问文件
		webSettings.setAllowFileAccess(true);
		// 设置可以支持缩放
		webSettings.setSupportZoom(true);
		// 设置默认缩放方式尺寸是far
		webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
		// 设置出现缩放工具
		webSettings.setBuiltInZoomControls(false);
		webSettings.setDefaultFontSize(20);
		webView.addJavascriptInterface(new JavaScriptObject(this),
				"fgqqgUploadFile");
		 webView.loadUrl(url);
//		URL _url = null;
//		try {
//			_url = new URL(url);
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//		String local = "file:///android_asset";
//		//
//		try {
//			// InputStream in = getAssets().open("testAndroidLoadFile.html");
//			// int size = in.available();// 取得数据流的数据大小
//			// byte[] buffer = new byte[size];
//			// in.read(buffer);
//			// in.close();
//
//			InputStream in = _url.openStream();
//			int size = in.available();// 取得数据流的数据大小
//			byte[] buffer = new byte[size];
//			in.read(buffer);
//			in.close();
//			String text = new String(buffer);
//			webView.loadDataWithBaseURL(local, text, "text/html", "utf-8", null);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		// webView.loadUrl("http://192.168.0.188:8083/buyapp/common/back");

	}

	private void requestPayInfo(String order_id) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token",
				UserInformation.getAccess_token());
		params.addQueryStringParameter("id", order_id);
		params.addHeader("X-Requested-With", "XMLHttpRequest");
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/pay/getWaitingPay", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						finish();
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						try {
							if (json.getBoolean("success")) {
								finish();
								Gson gson = new Gson();
								PayInfoModel model = gson.fromJson(
										json.getString("obj"),
										PayInfoModel.class);
								if (model != null) {
									Intent intent = new Intent(
											WebApplyActivity.this,
											PayActivity.class);
									intent.putExtra("price", model.getPrice());
									intent.putExtra("id", model.getId());
									intent.putExtra("code", model.getCode());
									startActivity(intent);

								}
							} else {
								// showToast(json.getString("msg"));
								toast(json.getString("msg"));
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	public static void switchTo(Activity activity,
			Class<? extends Activity> target) {
		Intent intent = new Intent(activity, target);
		activity.startActivity(intent);

	}

	@OnClick(R.id.turn)
	private void eventOnClick(View view) {
		switch (view.getId()) {
		// case R.id.turn:
		// finish();
		// break;

		default:
			break;
		}
	}

	File file;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_SELECT_TX_IMG_ID && resultCode == RESULT_OK) {
			Log.i("Tag", "=======onActivityResult===");
			Bitmap bitmap = null;
			try {
				file = new File(data.getStringExtra("ImageFilePath"));
				bitmap = BitmapFactory.decodeFile(data
						.getStringExtra("ImageFilePath"));
			} catch (Exception e) {
				// TODO: handle exception
			}

			Log.i("tag", (file.getAbsolutePath() == null)
					+ "--file.getAbsolutePath()---");
			Log.i("tag", (file == null) + "--file---");
			setRefreshListener(new RefreshImgID() {

				@Override
				public void refreshImgID(String imgID, String name) {
					// TODO Auto-generated method stub
					if (!StringUtils.isEmpty(imgID)) {
						try {
							JSONObject json = new JSONObject(imgID);
							String imgId = json.getString("id");
							String iconPath = json.getString("iconPath");
							webView.loadUrl("javascript:loadUploadImgDataShow('"
									+ imgId + "','" + iconPath + "')");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						toast("上传图片成功!");
					} else {
						toast("上传失败请重试！");
					}

				}
			});
			customProgressSmall.setMessage("正在上传图片中...");
			customProgressSmall.show();
			uploadPhotoSign(file, UserInformation
					.getAccess_token(), this, true);
		}
	}
}
