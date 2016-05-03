package com.llg.privateproject.actvity;

import java.util.Map;

import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bjg.lcc.jsonparser.ParseJson;
import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.help.MyFormat;
import com.llg.help.ScreenManager;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.view.Gerenziliao_Dialog;
import com.llg.privateproject.view.Gerenziliao_Dialog.AreaListener;

/**
 * 添加、修改地址界面 yh 2015.7.17
 * */
public class AddAddressActivity extends BaseActivity implements AreaListener {
	/***/
	@ViewInject(R.id.head_tilte)
	private TextView head_tilte;
	/** 收货人 */
	@ViewInject(R.id.name)
	private EditText name;
	/** 电话 */
	@ViewInject(R.id.telphone)
	private EditText telphone;
	/** 电话 */
	@ViewInject(R.id.ly_load)
	private LinearLayout lyLoad;
	/** 地区 */
	@ViewInject(R.id.area)
	public TextView area;
	/** 街道 */
	@ViewInject(R.id.street)
	private EditText street;
	/** 设置默认选中 */
	@ViewInject(R.id.cb)
	private CheckBox cb;
	/** 保存 */
	@ViewInject(R.id.save)
	private TextView save;
	private String mName;
	private String mTelphone;
	private String marea;
	private String mMail;
	private String mStreet;
	private String zone;
	boolean isCheck = false;
	boolean isEdit = false;
	private Bundle bundle;
	private String id;// 收货地址ID，修改地址会用到，保存地址不需要

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addaddress);
		ViewUtils.inject(this);
		ScreenManager.getScreenManager().pushActivity(this);
		initAutoLoadingNoShow(lyLoad);
		bundle = getIntent().getExtras();
		head_tilte.setText(bundle.getString("title"));
		isEdit = bundle.getBoolean("isEdit");
		if (bundle != null && isEdit) {
			name.setText(bundle.getString("name"));
			telphone.setText(bundle.getString("phone"));
			area.setText(bundle.getString("area"));
			street.setText(bundle.getString("street"));
			cb.setChecked(bundle.getBoolean("isCheckedaddress"));
			id = bundle.getString("id");
			zone = bundle.getString("zone");
		}
	}

	@OnClick({ R.id.turn, R.id.save, R.id.area, R.id.save })
	public void myclick(View v) {
		switch (v.getId()) {
		case R.id.turn:// 返回
			finish();
			break;
		case R.id.save:// 保存
			verifyData();
			requestModifyAddress(UserInformation
					.getAccess_token());
			Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
			break;
		case R.id.area:// 地区
			Gerenziliao_Dialog dialog = new Gerenziliao_Dialog(this, 6, this);
			dialog.show();
			WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
			lp.width = (AppContext.getScreenWidth());
			lp.height = (AppContext.getScreenHeight());
			dialog.getWindow().setAttributes(lp);
			Toast.makeText(this, "地区", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

	private void requestModifyAddress(String access_token) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", access_token);
		params.addQueryStringParameter("name", mName);
		params.addQueryStringParameter("phone", mTelphone);
		if (isCheck) {
			params.addQueryStringParameter("isDefault", "Y");
		} else {
			params.addQueryStringParameter("isDefault", "N");
		}
		params.addQueryStringParameter("address", mStreet);
		if (isEdit) {
			params.addQueryStringParameter("zone", zone);
			params.addQueryStringParameter("id", id);
		} else {
			params.addQueryStringParameter("zone", zone);
		}
		params.addHeader("X-Requested-With", "XMLHttpRequest");
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/revAddr/saveOrUpdateAddress", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						autoLoading.showExceptionLayout();
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						ParseJson parseJson = ParseJson.getParseJson();
						Map<String, Object> map = ParseJson.getParseJson()
								.parseIsSuccess(json);
						if ((Boolean) map.get("isSuccess")) {
							setResult(RESULT_OK);
							finish();
						} else if (map.get("errorCode").equals("NOT_LOGIN")) {
							setRefreshListtener(new Refresh() {
								@Override
								public void refreshRequst(String access_token) {
									// TODO Auto-generated method stub
									Log.i("tag", access_token
											+ "------监听里传过来的token值-------");
									// requestModifyAddress(access_token);
								}
							});
							RefeshToken();
						}
					}
				});
	}

	/**
	 * 验证数据格式
	 */
	private void verifyData() {
		mName = getStr(name);// 收货人
		mTelphone = getStr(telphone);// 电话
		marea = area.getText().toString();// 地区
		mStreet = getStr(street);// 街道
		if (mName == null || mName.length() == 0 || mName.contains(" ")) {
			Toast.makeText(this, "请输入正确的名字", Toast.LENGTH_LONG).show();
			return;
		}
		if (!MyFormat.isMobileNO(mTelphone)) {
			Toast.makeText(this, "请输入正确的电话", Toast.LENGTH_LONG).show();
			return;
		}
		if (marea == null || marea.length() == 0 || marea.contains(" ")) {
			Toast.makeText(this, "请选择地区", Toast.LENGTH_LONG).show();
			return;
		}
		if (mStreet == null || mStreet.length() == 0 || mStreet.contains(" ")) {
			Toast.makeText(this, "请输入正确的街道", Toast.LENGTH_LONG).show();
			return;
		}
		isCheck = cb.isChecked();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.llg.privateproject.view.Gerenziliao_Dialog.AreaListener#setAddress
	 * (java.lang.String) 回调设置地区地址
	 */
	@Override
	public void setAddress(String street, String zone) {
		// TODO Auto-generated method stub
		area.setText(street);
		this.zone = zone;
	}
}
