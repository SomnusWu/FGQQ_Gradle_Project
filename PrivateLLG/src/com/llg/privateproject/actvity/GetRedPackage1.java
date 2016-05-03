package com.llg.privateproject.actvity;

import org.json.JSONObject;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.smartandroid.sa.aysnc.Log;

public class GetRedPackage1 extends BaseActivity{
	@ViewInject(R.id.iv)private ImageView iv;
	@ViewInject(R.id.tv1)private TextView tv1;
	@ViewInject(R.id.tv2)private TextView tv2;
	private String adInfoId;
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.getredpackage1);
		ViewUtils.inject(this);
		init();
	}
	private void init(){
		adInfoId=getIntent().getStringExtra("adInfoId");
		MyFormat.setBitmap(this, iv, getIntent().getStringExtra("img"), 0, 0);
		new CountDownTimer(5 * 1000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				Log.d("my", "millisUntilFinished:"+millisUntilFinished);
				tv2.setText(millisUntilFinished/1000+"s后自动关闭");
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				finish();
			}
		}.start();
	}

	
	@OnClick({  R.id.tv1,R.id.tv3})
public void myClick(View v) {
		switch (v.getId()) {
		case R.id.tv1:
			saveAttentionAd();
			finish();
			break;
		case R.id.tv3:
			Intent intent=new Intent(this, GetRedPackage.class);
			intent.putExtra("adInfoId", adInfoId);
			intent.putExtra("type", 1);
			startActivity(intent);
			finish();
			break;
		}
	}
	/** 收藏广告 */
	private void saveAttentionAd() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("adInfoId",
				adInfoId);
		params.addBodyParameter("adForwardId",
				getIntent().getStringExtra("adForwardId"));
		params.addQueryStringParameter("access_token", UserInformation.getAccess_token());
		params.addQueryStringParameter("time",""+ System.currentTimeMillis());
		params.addHeader("X-Requested-With", "XMLHttpRequest");
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/ad/saveAttentionAd", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						// myLogShow("my", "json:"+json);
					}
				});
	}
}
