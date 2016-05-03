package com.llg.privateproject.actvity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.bjg.lcc.jsonparser.ParseJson;
import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.adapters.AddressListViewAdapter;
import com.llg.privateproject.adapters.AddressListViewAdapter.PostAddress;
import com.llg.privateproject.entities.Address;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 添加收货地址界面 yh 2015.07.16
 * 
 * */
public class OrderAddressListActivity extends BaseActivity implements OnItemClickListener{

	/** 返回按钮 */
	@ViewInject(R.id.turn)
	private ImageView turn;
	
	/** 返回按钮 */
	@ViewInject(R.id.ly_load)
	private LinearLayout lyLoad;
	
	/** 空view */
	@ViewInject(R.id.empty)
	private LinearLayout lyEmpty;
	
	// **线、点线条*/
	// @ViewInject(R.id.xian_dian)private LinearLayout xian_dian;
	/** 界面标题 */
	@ViewInject(R.id.head_tilte)
	private TextView head_tilte;
	
	/** 地址列表ListView */
	@ViewInject(R.id.add_lv)
	ListView add_lv;
	
	/** 地址列表 */
	List<Address> list;
	/** 地址列表适配器 */
	AddressListViewAdapter adapter;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_address_list);
		ViewUtils.inject(this);
		init();
		adapter.setPosition(getIntent().getIntExtra("position", -1));
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// if(list.size()==0){
		// xian_dian.setVisibility(View.VISIBLE);
		// }else{
		//
		// xian_dian.setVisibility(View.GONE);
		// }
	}

	void init() {
		intent = getIntent();
		initAutoLoading(lyLoad);
		autoLoading.setClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				autoLoading.showLoadingLayout();
				requestAddressList(UserInformation
						.getAccess_token());
			}
		});
		head_tilte.setText("收货地址");
		list = new ArrayList<Address>();
		adapter = new AddressListViewAdapter(this, list);
		adapter.setPostAddress(new PostAddress() {

			@Override
			public void setAddress(String name, String phone, String address,
					String id) {
				// TODO Auto-generated method stub
				intent.putExtra("name", name);
				intent.putExtra("phone", phone);
				intent.putExtra("address", address);
				intent.putExtra("id", id);
				OrderAddressListActivity.this.setResult(2, intent);
			}
		});
		add_lv.setAdapter(adapter);
		
		add_lv.setOnItemClickListener(this);
		add_lv.setEmptyView(lyEmpty);
		requestAddressList(UserInformation.getAccess_token());
	}

	@OnClick({ R.id.turn, R.id.add_address })
	public void myclick(View v) {
		switch (v.getId()) {
		case R.id.turn:// 返回
			finish();
			break;
		case R.id.add_address:// 添加收货地址
			Intent intent = new Intent(this, AddAddressActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("title", "新增收货地址");
			intent.putExtras(bundle);
			intent.getExtras().getString("title");
			intent.getExtras().putBoolean("isEdit", false);
			startActivityForResult(intent, 1);
			intent = null;
			bundle = null;
			Toast.makeText(this, "添加收货地址", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if ((requestCode == 1 || requestCode == 2) && resultCode == RESULT_OK) {
			requestAddressList(UserInformation
					.getAccess_token());
		}
	}

	private void requestAddressList(String access_token) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", access_token);
		params.addHeader("X-Requested-With", "XMLHttpRequest");
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/revAddr/getAddressList", params, new HttpCallback() {

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
						// customProgressSmall.dismiss();
						if ((Boolean) map.get("isSuccess")) {
							list = ParseJson.getParseJson().parseAddressList(
									json);
							adapter.setList(list);
							autoLoading.hideAll();
						} else if (map.get("errorCode").equals("NOT_LOGIN")) {
							// Log.i("tag", json.toString()
							// + "------监听上一句--------");
							setRefreshListtener(new Refresh() {
								@Override
								public void refreshRequst(String access_token) {
									// TODO Auto-generated method stub
									requestAddressList(access_token);
								}
							});
							RefeshToken();
						}
					}
				});
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		intent.putExtra("position", arg2);
		intent.putExtra("region", adapter.getItem(arg2).getZoneName());
		intent.putExtra("street", adapter.getItem(arg2).getAddress());
		intent.putExtra("id", adapter.getItem(arg2).getId());
		setResult(RESULT_OK, intent);
		finish();
	}

}
