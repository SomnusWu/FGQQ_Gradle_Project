package com.llg.privateproject.actvity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.help.Util;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;

/**
 * 电话注册
 * 
 * @author Administrator
 * 
 */
public class PhoneRegistAty extends BaseActivity {
	@ViewInject(R.id.edt_regist)
	private EditText edtRegist;

	@ViewInject(R.id.tv_regist)
	private TextView tvRegist;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				startActivity(new Intent(PhoneRegistAty.this,
						PhoneActivity.class));
				finish();
				break;

			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_phone_regist);
		ViewUtils.inject(this);
		edtRegist.setText(getSharePrefence().getString("phone", ""));
	}

	@OnClick({ R.id.iv_back, R.id.tv_regist })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_regist:
			// if (!MyFormat.isMobileNO(edtRegist.getText().toString())) {
			// toast("你输入的手机号不正确");
			// return;
			// }
			if (edtRegist.getText().toString().length() != 11) {
				toast("请输入11位手机号");
				return;
			}
			if (!UserInformation.isLogin()) {
				toast("请登录");
				return;
			}
			customProgressSmall.show();
			requestRegist(UserInformation.getAccess_token());
			break;
		case R.id.iv_back:
			finish();
			break;
		default:
			break;
		}
	}

	private void requestRegist(String access_token) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("mobile", edtRegist.getText().toString());
		params.addQueryStringParameter("appid", Util.appId(this));
		params.addQueryStringParameter("access_token", access_token);
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST, "m/tel/reg",
				params,

				new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub\
						customProgressSmall.dismiss();
						if (msg.equals("401")) {
							startActivity(new Intent(PhoneRegistAty.this,
									WebLoginActivity.class));
						} else {
							toast("注册失败");
						}
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						String obj = json.optString("obj");
						try {
							if (json.getBoolean("success")) {
								customProgressSmall.dismiss();
								getSharePrefence()
										.edit()
										.putString("phone",
												edtRegist.getText().toString())
										.commit();
								toast("注册成功");
								handler.sendEmptyMessage(1);
							} else {
								if (json.get("errorCode").equals("NOT_LOGIN")) {
									// Log.i("tag", json.toString()
									// + "------监听上一句--------");
									setRefreshListtener(new Refresh() {
										@Override
										public void refreshRequst(
												String access_token) {
											// TODO Auto-generated method
											// stub
											requestRegist(access_token);
										}
									});
									RefeshToken();
								} else {
									customProgressSmall.dismiss();
//									toast("注册失败");
									String message  =  (String) json.get("msg");
									toast(message);
								}
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

}