package com.llg.privateproject.actvity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.llg.privateproject.fragment.BaseActivity;

/**
 * 下载apk yh 2015.11.13
 * */
public class NewAppDown extends BaseActivity {
	@ViewInject(R.id.mwebView)
	private WebView webView;
	private String url = "http://sq.jd.com/Erx2rq";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_weblogin);
		url= getIntent().getStringExtra("url");
//		url="http://img.fgqqg.com/app/buy/android/fgqq_v_0.0.1";
		Log.i("my", "下载地址"+url);
		initWebviewSetting();
	}

	private void initWebviewSetting() {
		ViewUtils.inject(this);
		// 设置支持JavaScript脚本
		Log.i("tag", (webView == null) + "-------webView----");
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		// 设置可以访问文件
		webSettings.setAllowFileAccess(true);
		// 设置可以支持缩放
		webSettings.setSupportZoom(true);
		// 设置默认缩放方式尺寸是far
		webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
		// 设置出现缩放工具
		webSettings.setBuiltInZoomControls(true);
		webSettings.setDefaultFontSize(20);
		 Log.d("my", "url:"+url);
		 webView.setWebViewClient(new WebViewClient(){
		 @Override
		 public boolean shouldOverrideUrlLoading(WebView view, String url) {
		 // TODO Auto-generated method stub
		 super.shouldOverrideUrlLoading(view, url);
//		view.loadUrl(url);
		 Log.d("my", "jd"+url);
		 // NewAppDown.this.finish();
		 return false;
		 }
		 });
		 webView.loadUrl(url);
		
	}
}