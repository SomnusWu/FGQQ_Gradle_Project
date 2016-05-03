/**
 * 
 */
package com.llg.privateproject.actvity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.util.StringUtils;
import com.bjg.lcc.privateproject.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.adapters.BalanceAdapter;
import com.llg.privateproject.entities.IncomeDetailModel;
import com.llg.privateproject.entities.IncomeDetailModel.AttributesBean.PagesBean.ResultBean;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;

/**
 * @author cc
 * @time 2016年4月21日 下午1:46:09
 * @description 收益明细
 */
public class IncomeDetailActivity extends BaseActivity implements
		OnRefreshListener2<ListView> {
	@ViewInject(R.id.title)
	private TextView title;
	@ViewInject(R.id.tv_hint)
	private TextView tv_hint;
	@ViewInject(R.id.listview_withdraw_history)
	private PullToRefreshListView listView;
	@ViewInject(R.id.ly_load)
	private LinearLayout lyLoad;
	@ViewInject(R.id.rg)
	private RadioGroup rg;
	@ViewInject(R.id.empty)
	private LinearLayout lyEmpty;
	private int page = 1;
	private List<ResultBean> mBalanceList;
	private BalanceAdapter adapter;
	private String currencyType = "";// (1是RMB,2是酷币)

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
		setContentView(R.layout.aty_withdraw_history);
		ViewUtils.inject(this);
		title.setText("收益明细");
		rg.setVisibility(View.GONE);
		mBalanceList = new ArrayList<IncomeDetailModel.AttributesBean.PagesBean.ResultBean>();
		adapter = new BalanceAdapter(this, mBalanceList, true);
		listView.setOnRefreshListener(this);
		listView.setAdapter(adapter);
		requestQueryProfit();
	}

	private void requestQueryProfit() {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token",
				UserInformation.getAccess_token());
		params.addQueryStringParameter("pageNo", page + "");
		params.addHeader("X-Requested-With", "XMLHttpRequest");
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/assets/queryProfit", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						if (!StringUtils.isEmpty(msg)) {
							toast(msg);
						}
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						IncomeDetailModel model = IncomeDetailModel
								.parseJson(json.toString());
						if (model.isSuccess()) {
							if (page == 1) {
								mBalanceList.clear();
							}
							mBalanceList.addAll(model.getAttributes()
									.getPages().getResult());
							adapter.notifyDataSetChanged();
						}else{
							toast(model.getMsg());
						}
						listView.onRefreshComplete();
					}
				});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2
	 * #onPullDownToRefresh
	 * (com.handmark.pulltorefresh.library.PullToRefreshBase)
	 */
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		page = 1;
		requestQueryProfit();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2
	 * #onPullUpToRefresh(com.handmark.pulltorefresh.library.PullToRefreshBase)
	 */
	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		page++;
		requestQueryProfit();
	}
}
