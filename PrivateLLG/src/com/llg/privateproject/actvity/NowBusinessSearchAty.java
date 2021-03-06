package com.llg.privateproject.actvity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.help.Util;
import com.llg.privateproject.adapters.SearchHistoryAdapter;
import com.llg.privateproject.entities.SearchHistory;
import com.llg.privateproject.fragment.BaseActivity;

public class NowBusinessSearchAty extends BaseActivity {
	@ViewInject(R.id.edt_search)
	private EditText edtSearch;
	@ViewInject(R.id.tv_search)
	private EditText tvSearch;
	@ViewInject(R.id.nowbusiness_search_listview)
	private ListView lv;
	private String strSearchHistroy;
	List<String> list;
	private SearchHistoryAdapter adapter;
	private DbUtils dbUtils;
	private LinearLayout linearLayout;// 清除历史记录

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_nowbusiness_search);
		ViewUtils.inject(this);
		// path = Environment.getExternalStorageDirectory().getPath()
		// + File.separator + "searchHistory";
		list = new ArrayList<String>();
		dbUtils = DbUtils.create(NowBusinessSearchAty.this);
		findAllDB();
		initUI();
	}

	/**
	 * 查询数据库的搜索记录
	 */
	private void findAllDB() {
		try {
			List<SearchHistory> mlist = dbUtils.findAll(SearchHistory.class);
			if (mlist != null && mlist.size() > 0 && mlist.get(0) != null) {
				for (int i = 0; i < mlist.size(); i++) {
					String strSearchName = mlist.get(i).getSearchHistory();
					list.add(strSearchName);
				}
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@OnClick({ R.id.iv_back, R.id.tv_delete_history, R.id.tv_search })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.tv_search:
			strSearchHistroy = getStr(edtSearch);
			if (!Util.isChinese(strSearchHistroy)) {
				toast("你输入的搜索关键字不是中文");
				return;
			}
			SearchHistory searchHistory = new SearchHistory();
			searchHistory.setSearchHistory(strSearchHistroy);
			try {
				dbUtils.saveBindingId(searchHistory);
			} catch (DbException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Intent intent = new Intent();
			intent.putExtra("search", strSearchHistroy);
			setResult(RESULT_OK, intent);
			finish();
			break;
		default:
			break;
		}
	}

	/**
	 * 初始化界面
	 */
	private void initUI() {
		// TODO Auto-generated method stub
		View view = View.inflate(this, R.layout.aty_delete_history, null);
		linearLayout = (LinearLayout) view.findViewById(R.id.ly_delete);
		linearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					dbUtils.deleteAll(SearchHistory.class);
					list.clear();
					linearLayout.setVisibility(View.GONE);
					adapter.notifyDataSetChanged();
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		lv.addFooterView(view);
		adapter = new SearchHistoryAdapter(this, list);
		lv.setAdapter(adapter);
		if (list.size() == 0) {
			linearLayout.setVisibility(View.GONE);
		}
	}

}
