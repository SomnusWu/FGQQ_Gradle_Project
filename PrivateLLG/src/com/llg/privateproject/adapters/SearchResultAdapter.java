package com.llg.privateproject.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.actvity.CommodityActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 搜索结果适配器 yh 2015.7.6
 * 
 * */
public class SearchResultAdapter extends BaseAdapter {
	Context context;
	List<Map<String, Object>> list;
	List<String> listHistory;
	ArrayAdapter<String> historyAdapter;

	public SearchResultAdapter(Context context, List<Map<String, Object>> list,
			List<String> listHistory, ArrayAdapter<String> historyAdapter) {
		this.context = context;
		this.list = list;
		this.listHistory = listHistory;
		this.historyAdapter = historyAdapter;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		// Log.d("my", "position"+position);
		Map map = list.get(position);
		if (v == null) {
			v = View.inflate(context, R.layout.search_result_item, null);
			holder = new ViewHolder();
			holder.tv = (TextView) v.findViewById(R.id.name);
			holder.gv = (GridView) v.findViewById(R.id.gv);
			v.setTag(holder);

			v.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					listHistory.add("历史搜索" + position);
					historyAdapter.notifyDataSetChanged();

					notifyDataSetChanged();
					context.startActivity(new Intent(context,
							CommodityActivity.class));
					Toast.makeText(context, "search_result_gv_item" + position,
							Toast.LENGTH_SHORT).show();
				}
			});
		} else {
			holder = (ViewHolder) v.getTag();
		}
		HashMap<String, Object> map0 =  (HashMap<String, Object>) map.get("result" + position);
		if (map0 != null) {
			if (map0.get("title") != null) {
				holder.tv.setText((String) map0.get("title"));
			}
			holder.gv.setNumColumns(map0.size() - 1);
			List<String> strs = null;
			strs = new ArrayList<String>();
			if (map0.get("name1") != null) {
				strs.add((String) map0.get("name1"));
			}
			if (map0.get("name2") != null) {
				strs.add((String) map0.get("name2"));
			}
			if (map0.get("name3") != null) {
				strs.add((String) map0.get("name3"));
			}
			holder.gv.setAdapter(new ArrayAdapter<String>(context,
					R.layout.search_hot_item, strs));
			holder.gv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					listHistory.add("历史搜索" + position);
					historyAdapter.notifyDataSetChanged();
					notifyDataSetChanged();
					context.startActivity(new Intent(context,
							CommodityActivity.class));
					Toast.makeText(context, "item" + position,
							Toast.LENGTH_SHORT).show();
				}
			});
		}

		return v;
	}

	private class ViewHolder {
		TextView tv;
		GridView gv;
	}

}
