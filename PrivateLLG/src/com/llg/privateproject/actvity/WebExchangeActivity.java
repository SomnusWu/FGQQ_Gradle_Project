/**
 * 
 */
package com.llg.privateproject.actvity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bjg.lcc.alipay.pay.PayActivity;
import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.entities.PayInfoModel;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.utils.CommonUtils;
import com.llg.privateproject.utils.VersionUtils;
import com.smartandroid.sa.json.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author cc
 * @time 2016年4月28日 上午9:38:46
 * @description 兑换币
 */
public class WebExchangeActivity extends BaseActivity {

	@ViewInject(R.id.mwebView)
	WebView webView;
	private String url = "";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.llg.privateproject.fragment.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_weblogin);
		
		url = getIntent().getStringExtra("url");
		initWebviewSetting();
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
	}

	private void initWebviewSetting() {
		ViewUtils.inject(this);
		// 设置支持JavaScript脚本
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		// 设置可以访问文件
		webSettings.setAllowFileAccess(true);
		// 设置可以支持缩放
		webSettings.setSupportZoom(false);
		// 设置默认缩放方式尺寸是far
		webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
		// 设置出现缩放工具
		webSettings.setBuiltInZoomControls(false);
		webSettings.setDefaultFontSize(20);
//		打开页面时， 自适应屏幕：
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		webView.addJavascriptInterface(new JavaScriptObject(this), "fgqqg");
		//设置UserAgent
		String ua = webView.getSettings().getUserAgentString();
		webSettings.setUserAgentString(ua+" fgqqg " + VersionUtils.getVersionName(this));
		webView.loadUrl(url);
	}

	public class JavaScriptObject {
		Context mContxt;

		// sdk17版本以上加上注解
		public JavaScriptObject(Context mContxt) {
			this.mContxt = mContxt;
		}

		/** 自定义截取上传文件方法 */
		@JavascriptInterface
		public void pay( final String id) {
			runOnUiThread(new Runnable() {
				public void run() {
					// Toast.makeText(WebApplyActivity.this, "RunUploadimag",
					// Toast.LENGTH_SHORT).show();
					// webView.loadUrl("javascript:setUploadImgData('" + mes +
					// "')");
					// Intent intent = new Intent(WebExchangeActivity.this,
					// PopupSelectImage.class);
					// intent.putExtra("type_s", "002");
					// startActivityForResult(intent, REQUEST_SELECT_TX_IMG_ID);
					requestPayInfo(id);
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

		@JavascriptInterface
		public void downLoadImage(String imgUrl){
			new downloadImgThread(imgUrl).start();
		}
	}

	/**
	 * 下载图片文件
	 */
	private class downloadImgThread extends Thread {
		/*
		 * (non-Javadoc)
		 *
		 * @see java.lang.Thread#run()
		 */

		private String url = "";

		public downloadImgThread(String url) {
			// TODO Auto-generated constructor stub
			this.url = url;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			CommonUtils.downLoadWebImage(WebExchangeActivity.this,this.url);
		}
	}

	private void requestPayInfo(String order_id) {
		
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token",
				UserInformation.getAccess_token());
		params.addQueryStringParameter("id", order_id);
		params.addHeader("X-Requested-With", "XMLHttpRequest");
		AppContext.getHtmlUitls().xUtilsm(WebExchangeActivity.this,
				HttpMethod.POST, "m/pay/getWaitingPay", params,
				new HttpCallback() {

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
											WebExchangeActivity.this,
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
//	按返回键时， 不退出程序而是返回上一浏览页面：
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if ((keyCode == KeyEvent.KEYCODE_BACK) &&   webView.canGoBack()) {
//			webView.goBack();
//			return true;
//		}
//		return super.onKeyDown(keyCode, event);
//	}

}
