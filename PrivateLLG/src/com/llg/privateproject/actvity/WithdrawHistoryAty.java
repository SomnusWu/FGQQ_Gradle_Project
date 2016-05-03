package com.llg.privateproject.actvity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ComponentCallbacks2;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.bjg.lcc.jsonparser.ParseJson;
import com.bjg.lcc.privateproject.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.help.ScreenManager;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.adapters.WithdrawHistoryAdapter;
import com.llg.privateproject.entities.DrawHistory;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.fragment.BaseActivity.Refresh;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;

/**
 * @author cc 提现记录
 */
public class WithdrawHistoryAty extends BaseActivity implements
		OnRefreshListener2<ListView> {

	@ViewInject(R.id.listview_withdraw_history)
	private PullToRefreshListView listView;
	@ViewInject(R.id.ly_load)
	private LinearLayout lyLoad;
	@ViewInject(R.id.empty)
	private LinearLayout lyEmpty;
	private int page = 1;
	private List<DrawHistory> tlist;
	private WithdrawHistoryAdapter adapter;
	private int oldPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_withdraw_history);
		ViewUtils.inject(this);
		initUI();
		requestDrawHistory(UserInformation.getAccess_token());
	}

	private void initUI() {
		initAutoLoading(lyLoad);
		autoLoading.setClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				autoLoading.showLoadingLayout();
				requestDrawHistory(UserInformation.getAccess_token());
			}
		});
		tlist = new ArrayList<DrawHistory>();
		adapter = new WithdrawHistoryAdapter(this, tlist);
		listView.setEmptyView(lyEmpty);
		listView.setAdapter(adapter);
		listView.setOnRefreshListener(this);
	}

	@OnClick({ R.id.iv_back })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
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

	/**
	 * 提现信息
	 */
	private void requestDrawHistory(String access_token) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", access_token);
		params.addQueryStringParameter("pageNo", page + "");
		params.addHeader("X-Requested-With", "XMLHttpRequest");
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/customer/getCashAdvanceHistory2", params,
				new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						// autoLoading.showExceptionLayout();
						autoLoading.showExceptionLayout();
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						ParseJson parseJson = ParseJson.getParseJson();
						Map<String, Object> map = parseJson
								.parseIsSuccess(json);
						if ((Boolean) map.get("isSuccess")) {
							// json.getJSONObject("");
							JSONArray arr = null;
							try {
								arr = json.getJSONObject("obj").getJSONArray(
										"result");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							List<DrawHistory> list = ParseJson.getParseJson()
									.parseDrawHistory(arr.toString());
							autoLoading.hideAll();
							if (list != null) {
								if (page == 1) {
									tlist.clear();
								}
							}
							tlist.addAll(list);
							adapter.setList(tlist);
							listView.onRefreshComplete();
							autoLoading.hideAll();
							listView.getRefreshableView()
									.setSelectionFromTop(
											oldPosition,
											TRIM_MEMORY_BACKGROUND);
						} else {
							if (map.get("errorCode").equals("NOT_LOGIN")) {
								setRefreshListtener(new Refresh() {
									@Override
									public void refreshRequst(
											String access_token) {
										// TODO Auto-generated method stub
										requestDrawHistory(access_token);
									}
								});
								RefeshToken();
							} else {
								Toast.makeText(WithdrawHistoryAty.this,
										json.optString("msg"),
										Toast.LENGTH_LONG).show();
							}
						}
					}
				});
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		page = 1;
		requestDrawHistory(UserInformation.getAccess_token());
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		page = page + 1;
		oldPosition = tlist.size();
		requestDrawHistory(UserInformation.getAccess_token());
	}
}
