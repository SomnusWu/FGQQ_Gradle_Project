package com.llg.privateproject.actvity;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.util.StringUtils;
import com.bjg.lcc.jsonparser.ParseJson;
import com.bjg.lcc.privateproject.R;
import com.google.gson.JsonArray;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.help.MyFormat;
import com.llg.help.Util;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;

/**
 * @author cc 申请提现
 */
public class WithdrawAty extends BaseActivity {
	@ViewInject(R.id.edt_money)
	private EditText edtMoney;
	@ViewInject(R.id.edt_number)
	private EditText edtNumber;
	@ViewInject(R.id.ly_load)
	private LinearLayout lyLoad;

	@ViewInject(R.id.tv_lowmoney)
	private TextView tvLowMoney;
	@ViewInject(R.id.tv_time)
	private TextView tvTime;
	@ViewInject(R.id.tv_free)
	private TextView tvFree;

	@ViewInject(R.id.edt_number_secend)
	private EditText edtSecend;
	private Double bigDouble;

	private String userNumber;
	private String userName;
	@ViewInject(R.id.edt_name)
	EditText edt_name;
	/*** 验证码 **/
	private TimeCount time;
	@ViewInject(R.id.btn_send_code)
	Button btn_send_code;
	@ViewInject(R.id.edit_phone_code)
	EditText edit_phone_code;
	
	@ViewInject(R.id.layout_code)
	LinearLayout layout_code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_withdraw);
		ViewUtils.inject(this);
		initUI();
		time = new TimeCount(60000, 1000);// 构造CountDownTimer对象
	}

	private void initUI() {
		initAutoLoading(lyLoad);
		requestDrawMoney(UserInformation.getAccess_token());
		
		

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		if (autoLoading.getVisibelyLoad()) {
			finish();
			return;
		}
	}

	@OnClick({ R.id.iv_back, R.id.tv_confirm })
	public void onClick(View v) {
		Log.i("tag", "++++++++++++++++++=----------------tag");
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;

		case R.id.tv_confirm:
			if (!MyFormat.isEmail(getStr(edtNumber))
					&& !MyFormat.isMobileNO(getStr(edtNumber))) {
				toast("请输入正确的支付宝账号");
				return;
			}
			if (!getStr(edtNumber).equals(getStr(edtSecend))) {
				toast("两次输入支付宝账号不一致");
				return;
			}
			if (StringUtils.isEmpty(getStr(edt_name))) {
				toast("输入支付宝账号关联姓名");
				return;
			}
			try {

				Double double2 = Double.parseDouble(getStr(edtMoney));
				if (!Util.isDouble(getStr(edtMoney))
						|| (double2 < 0 || double2 > bigDouble)) {
					toast("请输入正确的提现现金");
					return;
				}
			} catch (Exception e) {
				// TODO: handle exception
				toast("请输入正确的提现现金");
				return;
			}
			// String edit_phone_codeT = edit_phone_code.getText().toString()
			// .trim();
			// if (StringUtils.isEmpty(edit_phone_codeT)) {
			// toast("请输入验证码");
			// return;
			// }
			customProgressSmall.setMessage("正在提现中");
			customProgressSmall.show();
			requestConfirm(UserInformation.getAccess_token());
			break;
		default:
			break;
		}
	}

	/**
	 * 提现信息
	 */
	private void requestDrawMoney(String access_token) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", access_token);
		params.addHeader("X-Requested-With", "XMLHttpRequest");
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/customer/getCashAdvanceInfo", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						// autoLoading.showExceptionLayout();
						autoLoading.showExceptionLayout();
						Log.i("tag", msg + "--------进来了--msg------");
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						ParseJson parseJson = ParseJson.getParseJson();
						Map<String, Object> map = parseJson
								.parseIsSuccess(json);
						if ((Boolean) map.get("isSuccess")) {
							// json.getJSONObject("");
							try {
								JSONObject jsonObject = json
										.getJSONObject("obj");
								bigDouble = jsonObject.optDouble("asset");
								JSONArray argeement = jsonObject
										.optJSONArray("agreement");
								if (bigDouble != null) {
									edtMoney.setHint("本次最高提现" + bigDouble + "元");
								} else {
									edtMoney.setHint("本次最高提现0元");
								}
								userNumber = jsonObject
										.getString("aliPayAccount");
								userName = jsonObject
										.getString("aliPayRealName");

								String argStr = "";
								for (int i = 0; i < argeement.length(); i++) {
									argStr += argeement.optString(i) + "\n";
								}
								tvLowMoney.setText(argStr);
								edtNumber.setText(userNumber);
								edtSecend.setText(userNumber);
								edt_name.setText(userName);
								editTextChanged();

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							autoLoading.hideAll();
						} else {
							if (map.get("errorCode").equals("NOT_LOGIN")) {
								setRefreshListtener(new Refresh() {
									@Override
									public void refreshRequst(
											String access_token) {
										// TODO Auto-generated method stub
										requestDrawMoney(access_token);
									}
								});
								RefeshToken();
							} else {
								toast(json.optString("msg"));
								autoLoading.hideAll();
							}
						}
					}
				});
	}

	private void editTextChanged(){
		if (getStr(edtNumber).equals(userNumber)) {
			layout_code.setVisibility(View.GONE);
		}else{
			layout_code.setVisibility(View.VISIBLE);
		}
		edtNumber.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (!getStr(edtNumber).equals(userNumber)) {
					layout_code.setVisibility(View.VISIBLE);
				}else{
					layout_code.setVisibility(View.GONE);
				}
			}
		});
	}
	
	
	/**
	 * 提现信息
	 */
	private void requestConfirm(String access_token) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", access_token);
		params.addHeader("X-Requested-With", "XMLHttpRequest");
		params.addQueryStringParameter("accountType", "1");
		params.addQueryStringParameter("applyAccount", getStr(edtNumber));
		params.addQueryStringParameter("money", getStr(edtMoney));
		params.addQueryStringParameter("code", getStr(edit_phone_code));
		params.addQueryStringParameter("realName", getStr(edt_name));

		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/customer/saveCashAdvance", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						// autoLoading.showExceptionLayout();
						if (!StringUtils.isEmpty(msg)) {
							toast(msg);
						}

					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						ParseJson parseJson = ParseJson.getParseJson();
						Map<String, Object> map = parseJson
								.parseIsSuccess(json);
						if ((Boolean) map.get("isSuccess")) {
							toast(json.optString("msg"));
							finish();
						} else if (map.get("errorCode").equals("NOT_LOGIN")) {
							setRefreshListtener(new Refresh() {
								@Override
								public void refreshRequst(String access_token) {
									// TODO Auto-generated method stub
									requestConfirm(access_token);
								}
							});
							RefeshToken();
						} else if (!(Boolean) map.get("isSuccess")) {
							customProgressSmall.dismiss();
							toast(json.optString("msg"));
						}

					}
				});
	}

	/**
	 * 发送验证码
	 * 
	 * @param v
	 */
	@OnClick(R.id.btn_send_code)
	void phoneCodeClick(View v) {
		time.start();// 开始计时
		// 请求验证码
		requsetGetPhoneCode();

	}

	// timer Util
	/* 定义一个倒计时的内部类 */
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {// 计时完毕时触发
			btn_send_code.setText("重新发送");
			btn_send_code.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			btn_send_code.setClickable(false);
			btn_send_code.setText(millisUntilFinished / 1000 + "秒");
		}
	}

	private void requsetGetPhoneCode() {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token",
				UserInformation.getAccess_token());
		params.addHeader("X-Requested-With", "XMLHttpRequest");
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/customer/applyCashSendSms", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						toast(msg);
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						if (!StringUtils.isEmpty(json.toString())) {
							try {
								boolean isSuccess = json.getBoolean("success");
								if (isSuccess) {
									toast("发送成功!");
								} else {
									toast(json.getString("msg"));
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				});
	}
}
