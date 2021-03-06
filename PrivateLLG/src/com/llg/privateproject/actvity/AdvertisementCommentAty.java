package com.llg.privateproject.actvity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.ComponentCallbacks2;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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
import com.llg.privateproject.adapters.AdvertisementCommentAdapter;
import com.llg.privateproject.entities.AdvertiseComment;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;

public class AdvertisementCommentAty extends BaseActivity implements
		OnRefreshListener2<ListView> {
	private int page = 1;
	@ViewInject(R.id.ly_listview)
	private LinearLayout lyLoad;

	@ViewInject(R.id.empty)
	private LinearLayout lyEmpty;

	@ViewInject(R.id.listview)
	private PullToRefreshListView listView;

	@ViewInject(R.id.iv_head)
	private ImageView ivHead;
	@ViewInject(R.id.tv_name)
	private TextView tvName;
	@ViewInject(R.id.tv_time)
	private TextView tvTime;
	@ViewInject(R.id.tv_comment)
	private TextView tvComment;
	private AdvertisementCommentAdapter adapter;
	private List<AdvertiseComment> list;
	private String adInfoId;
	private int oldPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_advertisement_comment);
		ViewUtils.inject(this);
		initUI();
	}

	protected void onRestart() {
		super.onRestart();
		if (autoLoading.getVisibelyLoad()) {
			finish();
			return;
		}
	}

	private void initUI() {
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			adInfoId = bundle.getString("adInfoId");
		}
		ScreenManager.getScreenManager().pushActivity(this);
		list = new ArrayList<AdvertiseComment>();
		initAutoLoading(lyLoad);
		autoLoading.setClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				autoLoading.showLoadingLayout();
				requestAdvertisementComment(UserInformation.getAccess_token());
			}
		});
		adapter = new AdvertisementCommentAdapter(this, list);
		listView.setEmptyView(lyEmpty);
		listView.setAdapter(adapter);
		listView.setOnRefreshListener(this);
		requestAdvertisementComment(UserInformation.getAccess_token());
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

	/**
	 * 发起广告评论列表请求
	 */
	private void requestAdvertisementComment(String access_token) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", access_token);
		params.addQueryStringParameter("pageNo", page + "");
		params.addQueryStringParameter("adInfoId", adInfoId);
		params.addHeader("X-Requested-With", "XMLHttpRequest");
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/ad/getRedAndComment", params, new HttpCallback() {

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
							List<AdvertiseComment> mlist = ParseJson
									.getParseJson().parseAdvertiseComment(json);
							DealData(mlist);
						} else {
							if (map.get("errorCode").equals("NOT_LOGIN")) {
								// Log.i("tag", json.toString()
								// + "------监听上一句--------");
								setRefreshListtener(new Refresh() {
									@Override
									public void refreshRequst(
											String access_token) {
										// TODO Auto-generated method stub
										requestAdvertisementComment(access_token);
									}
								});
								RefeshToken();
							} else {
								Toast.makeText(AdvertisementCommentAty.this,
										json.optString("msg"),
										Toast.LENGTH_LONG).show();
							}
						}
					}

					private void DealData(List<AdvertiseComment> mlist) {
						if (page == 1) {
							list.clear();
						} 
						list.addAll(mlist);
						adapter.setList(list);
						autoLoading.hideAll();
						listView.onRefreshComplete();
						if (page != 1) {
							listView.getRefreshableView()
									.setSelectionFromTop(
											oldPosition,
											TRIM_MEMORY_BACKGROUND);
						}
					}
				});
	}

	// public void connectData(List<AdvertiseComment>mlist){
	// list.addAll(mlist);
	// }

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		page = 1;
		requestAdvertisementComment(UserInformation
				.getAccess_token());
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		page = page + 1;
		oldPosition = list.size();
		requestAdvertisementComment(UserInformation
				.getAccess_token());
	}

}
