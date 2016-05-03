package com.llg.privateproject.actvity;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjg.lcc.jsonparser.ParseJson;
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
import com.llg.privateproject.utils.CommonUtils;
import com.llg.privateproject.utils.Constants;

/**
 * @author cc 我的余额
 */
public class MineBalanceAty extends BaseActivity {
	/** money[0]:总金额,money[1]冻结金额,money[2]可体现金额 */
	// String[] money;
	@ViewInject(R.id.tv_balance)
	private TextView tvBalance;

	@ViewInject(R.id.tv_drawBalance)
	private TextView tvDrawBalance;

	@ViewInject(R.id.tv_freezeBalance)
	private TextView tvFreezeBalance;

	@ViewInject(R.id.tv_co)
	private TextView tvCo;

	@ViewInject(R.id.ly_load)
	private LinearLayout lyLoad;

	public String EXOURL = "";
	/**
	 * 我的电话卡 （卡密）
	 */
	public String ephoneCardURL = "";
	/**
	 * 我的小组
	 */
	public String GROUPURl = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_mine_balance);
		ViewUtils.inject(this);
		initUI();

		EXOURL = AppContext.getHtmlUitls().getDataHttpm()
				+ "/m/exo?access_token=" + UserInformation.getAccess_token()
				+ "&t=" + System.currentTimeMillis();
		ephoneCardURL = AppContext.getHtmlUitls().getDataHttpm()
				+ "/m/ephoneCard?access_token="
				+ UserInformation.getAccess_token() + "&t="
				+ System.currentTimeMillis();
		GROUPURl = AppContext.getHtmlUitls().getDataHttpm()
				+ "/m/cusGroup?access_token="
				+ UserInformation.getAccess_token() + "&t="
				+ System.currentTimeMillis();
		getMyMoney(UserInformation.getAccess_token());

	}

	private void initUI() {
		initAutoLoading(lyLoad);
		autoLoading.setClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				autoLoading.showLoadingLayout();
				getMyMoney(UserInformation.getAccess_token());
			}
		});
		// money = getIntent().getStringArrayExtra("money");

	}

	@OnClick({ R.id.iv_back, R.id.tv_withdraw, R.id.tv_recharge,
			R.id.tv_withdraw_history, R.id.tv_balance1, R.id.tv_balance2,
			R.id.tv_exchange, R.id.tv_ephoneCard, R.id.tv_cusgroup })
	public void onClick(View v) {
		if (!UserInformation.isLogin()) {
			goWebLoginActivity();
			return;
		}
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		// 提现
		case R.id.tv_withdraw:
			startActivity(new Intent(this, WithdrawAty.class));
			break;
		// 充值
		case R.id.tv_recharge:
			startActivity(new Intent(this, RechargeAty.class));
			break;
		// 提现记录
		case R.id.tv_withdraw_history:
			startActivity(new Intent(this, WithdrawHistoryAty.class));
			break;
		case R.id.tv_balance1:// 收支明细
			// toast("收支明细");
			Intent it = new Intent(this, Balance.class);
			it.putExtra("type", 0);// 0收支明细
			startActivity(it);
			break;
		case R.id.tv_balance2:// 收益明细
			// Intent it1=new Intent(this, Balance.class);
			// it1.putExtra("type", 1);//1收益明细
			// startActivity(it1);
			// toast("收益明细");

			startActivity(new Intent(this, IncomeDetailActivity.class));
			break;
		case R.id.tv_exchange:
			Intent intent = new Intent();
			intent.setClass(this, WebExchangeActivity.class);
			intent.putExtra("url", EXOURL);
			startActivity(intent);
			break;
		case R.id.tv_ephoneCard:
			Intent intent1 = new Intent();
			intent1.setClass(this, WebExchangeActivity.class);
			intent1.putExtra("url", ephoneCardURL);
			startActivity(intent1);
			break;
		case R.id.tv_cusgroup:
			Intent groupIntent = new Intent();
			groupIntent.setClass(this, WebExchangeActivity.class);
			groupIntent.putExtra("url", GROUPURl);
			startActivity(groupIntent);
			break;
		default:
			break;
		}
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

	// 请求我的资产
	private void getMyMoney(String access_token) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", access_token);
		params.addHeader(MyFormat.HEADER_KEY, MyFormat.HEADER_VALUE);
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/assets/assets", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						autoLoading.showExceptionLayout();
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						ParseJson parseJson = ParseJson.getParseJson();
						Map<String, Object> map = parseJson
								.parseIsSuccess(json);
						try {
							if (json.getBoolean("success")) {
								Message msg = new Message();
								msg.what = 1;
								JSONObject jsonObject = json.getJSONObject(
										"attributes").getJSONObject(
										"attentionList");
								Double balance = jsonObject
										.optDouble("totalBalances");
								Double drawBalance = jsonObject
										.optDouble("canExtractBalances");
								Double freezeBalances = jsonObject
										.optDouble("freezeBalances");
								Integer co = jsonObject.optInt("coAmount");
								tvBalance.setText(balance + "元");
								tvDrawBalance.setText(drawBalance + "元");
								tvFreezeBalance.setText(freezeBalances + "元");
								tvCo.setText(co + "个");
								autoLoading.hideAll();
							} else if (map.get("errorCode").equals("NOT_LOGIN")) {
								setRefreshListtener(new Refresh() {
									@Override
									public void refreshRequst(
											String access_token) {
										// TODO Auto-generated method stub
										Log.i("tag", "------监听里-------");
										getMyMoney(access_token);
									}
								});
								RefeshToken();
							} else if (!(Boolean) map.get("success")) {
								autoLoading.showExceptionLayout();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}
}