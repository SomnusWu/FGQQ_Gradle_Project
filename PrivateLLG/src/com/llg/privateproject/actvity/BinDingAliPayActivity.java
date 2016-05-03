/**
 * 
 */
package com.llg.privateproject.actvity;

import org.json.JSONObject;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.util.StringUtils;
import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.help.GetProgressBar;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;

/**
 * @author cc
 * @time 2016年4月20日 上午10:26:05
 * @description
 */
public class BinDingAliPayActivity extends BaseActivity {
	@ViewInject(R.id.et_number)
	EditText et_number;
	@ViewInject(R.id.edit_name)
	EditText edit_name;
	@ViewInject(R.id.edit_code)
	EditText edit_code;
	@ViewInject(R.id.btn_code)
	Button btn_code;
	@ViewInject(R.id.tv_commit)
	TextView tv_commit;

	/*** 验证码 **/
	private TimeCount time;
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
		setContentView(R.layout.activity_binding_alipay);
		ViewUtils.inject(this);
		
		time = new TimeCount(60000, 1000);// 构造CountDownTimer对象

	}

	@OnClick({ R.id.btn_code, R.id.tv_commit })
	void click(View v) {
		switch (v.getId()) {
		case R.id.btn_code:
			time.start();// 开始计时
			requestGetCode();
			break;
		case R.id.tv_commit:
			if (StringUtils.isEmpty(getStr(et_number))) {
				toast("请输入支付宝账号");
				return;
			}
			if (StringUtils.isEmpty(getStr(edit_name))) {
				toast("请输入姓名");
				return;
			}
			if (StringUtils.isEmpty(getStr(edit_code))) {
				toast("请输入验证码");
				return;
			}
			requestBindingAliPay();
			break;
		default:
			break;
		}
	}
	/**
	 * 请求绑定
	 */
	private void requestBindingAliPay() {
		GetProgressBar.getProgressBar(this);
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", UserInformation.getAccess_token());
		params.addQueryStringParameter("aliPayRealName", getStr(et_number));
		params.addQueryStringParameter("aliPayAccount", getStr(edit_name));
		params.addQueryStringParameter("code", getStr(edit_code));

		AppContext.getHtmlUitls().xUtilsm(BinDingAliPayActivity.this,
				HttpMethod.POST, "m/customer/bindAliPay", params,
				new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						GetProgressBar.dismissMyProgressBar();
						if (!StringUtils.isEmpty(msg)) {
							toast(msg);
							
						}

					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						toast(json.optString("msg"));
						GetProgressBar.dismissMyProgressBar();
						if (json.optBoolean("success")) {
							finish();
						}
					}
				});
	}

	
	/**
	 * 短信验证码
	 */
	private void requestGetCode() {
		GetProgressBar.getProgressBar(this);
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", UserInformation.getAccess_token());
		AppContext.getHtmlUitls().xUtilsm(BinDingAliPayActivity.this,
				HttpMethod.POST, "m/customer/bindAliPaySendSms", params,
				new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						GetProgressBar.dismissMyProgressBar();
						toast(msg);
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						toast(json.optString("msg"));
						GetProgressBar.dismissMyProgressBar();
						if (json.optBoolean("success")) {
							//TODO  ....
						}
					}
				});

	}
	
	// timer Util
		/* 定义一个倒计时的内部类 */
	class TimeCount extends CountDownTimer {
			public TimeCount(long millisInFuture, long countDownInterval) {
				super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
			}

			@Override
			public void onFinish() {// 计时完毕时触发
				btn_code.setText("重新发送");
				btn_code.setClickable(true);
			}

			@Override
			public void onTick(long millisUntilFinished) {// 计时过程显示
				btn_code.setClickable(false);
				btn_code.setText(millisUntilFinished / 1000 + "秒");
			}
		}

}