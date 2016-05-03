package com.llg.privateproject.actvity;


import org.json.JSONObject;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.smartandroid.sa.aysnc.Log;
/**
 * 商品详情页面
 * yh  2015.11.30
 * */
public class WebAty1 extends BaseActivity implements OnClickListener{

	WebView wv;
	String url="";
	/** 返回按钮 */
	@ViewInject(R.id.turn)
	private ImageView turn;
	/** 刷新 */
	@ViewInject(R.id.fresh)
	private ImageView fresh;
	/** 标题 */
	@ViewInject(R.id.head_tilte)
	private TextView head_tilte;
	/** 更多 */
	@ViewInject(R.id.ellipsis)
	private ImageView ellipsis;
	private long time1;
	/** 规格id */
	String prodSpecId = "";
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        init();
	        wv=(WebView) findViewById(R.id.wv);
	        initWebviewSetting();
	        wv.setWebViewClient(new WebViewClient(){
	        	
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					// TODO Auto-generated method stub
					if(url.subSequence(0, 4).equals("taob")){
//						url="http"+url.substring(6, url.length());
						return true;
					}
				
					view.loadUrl(url);
					Uri uri=Uri.parse(url);
					
					return true;
				}
			
	        });
		}
	    @Override
	    protected void onPause() {
	    	// TODO Auto-generated method stub
	    	super.onPause();
	    	ms();
	    	finish();
	    }
	    /**在该页面停留时间*/
	    private void ms() {
	    	long time=System.currentTimeMillis()-time1;
	    	if(time<3000){
	    		return;
	    	}
	    	RequestParams params=new RequestParams();
	    	params.addQueryStringParameter("adInfoId", getIntent().getStringExtra("adInfoId"));
	    	params.addQueryStringParameter("cusId", AppContext.userid);
	    	params.addQueryStringParameter("ms", ""+time);
	    	params.addQueryStringParameter("type", ""+2);
	    	AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST, "common/ad/adStay", params, new HttpCallback() {
				
				@Override
				public void onError(String msg) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onBack(JSONObject json) {
					// TODO Auto-generated method stub
					
				}
			});
	    }
		void init() {
			   setContentView(R.layout.webaty);
			ViewUtils.inject(this);
			head_tilte.setText("");
			turn.setOnClickListener(this);
			fresh.setOnClickListener(this);
			fresh.setVisibility(View.GONE);
			ellipsis.setVisibility(View.GONE);
			ellipsis.setOnClickListener(this);
			prodSpecId = getIntent().getStringExtra("prodSpecId");
			url=prodSpecId;
			
				time1=System.currentTimeMillis();
				
			
			
		}

	    private void initWebviewSetting() {
		
			// 设置支持JavaScript脚本
			WebSettings webSettings = wv.getSettings();
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
			wv.loadUrl(url);
			
		}
	    
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.turn:
				finish();
				break;

			default:
				break;
			}
		}
}
