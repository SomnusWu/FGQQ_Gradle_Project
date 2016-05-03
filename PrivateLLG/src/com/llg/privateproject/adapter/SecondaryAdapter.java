package com.llg.privateproject.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.actvity.CommodityActivity;
import com.llg.privateproject.adapters.ThirdAdapter;
import com.llg.privateproject.view.MyGridView;

/**
 * 二级产品分类列表适配器
 * 
 * @author gongyibing
 */
public class SecondaryAdapter extends BaseAdapter {
	private Context mContext;
	/** 分类数据 */
	private List<Map<String, Object>> list;
	private LayoutInflater mInflater;
	private WindowManager windowManager;
	AppContext appContext;

	// int pics[]={R.drawable.defaultpic,R.drawable.bkg,R.drawable.welcome};
	public SecondaryAdapter(Context context, List<Map<String, Object>> list) {
		super();
		this.mContext = context;
		this.list = list;
		appContext = (AppContext) context.getApplicationContext();
		this.mInflater = LayoutInflater.from(context);
		windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
	}

	@Override
	public int getCount() {

		return (null == list) ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		return (null == list) ? null : list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == mContext) {
			return null;
		}
		if (null == list || list.size() == 0 || list.size() <= position) {
			return null;
		}
		final Holder holder;
		Map<String, Object> map = list.get(position);
		if (null == convertView) {
			holder = new Holder();
			convertView = mInflater.inflate(R.layout.third_gridview, parent,
					false);
			holder.llLayout = (LinearLayout) convertView.findViewById(R.id.ll);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.thidGridView = (MyGridView) convertView
					.findViewById(R.id.third_gridView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.title.setText(map.get("name").toString());
		final List<Map<String, Object>> thirdList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list = (List<Map<String, Object>>) map
				.get("thirdList");

		Map<String, Object> map2 = null;
		for (int i = 0; i < list.size(); i++) {
			map2 = new HashMap<String, Object>();
			map2.put("id", list.get(i).get("id"));
			map2.put("brands", list.get(i).get("brands"));
			map2.put("pId", list.get(i).get("pId"));
			map2.put("pIdNames", list.get(i).get("pIdNames"));
			map2.put("name", list.get(i).get("name"));
			// map2.put("children", list.get(i).get("children"));
			map2.put("hot", list.get(i).get("hot"));
			map2.put("pIds", list.get(i).get("pIds"));
			map2.put("ads", list.get(i).get("ads"));
			map2.put("url", list.get(i).get("url"));
			map2.put("img", list.get(i).get("img"));

			/** 添加二级级分类 */
			thirdList.add(map2);
		}
		ThirdAdapter thirdAdapter = new ThirdAdapter(mContext, thirdList);
		holder.thidGridView.setAdapter(thirdAdapter);

		holder.thidGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (appContext.isNetworkConnected()) {
					Map<String, Object> map3 = thirdList.get(position);

					Intent intent = new Intent(mContext,
							CommodityActivity.class);
					intent.putExtra("parentId", map3.get("id").toString());

					mContext.startActivity(intent);
				} else {
					Toast.makeText(mContext, R.string.t_Connection,
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		// ll_second.addView(convertView);

		return convertView;
	}

	private class Holder {
		TextView title;
		MyGridView thidGridView;
		LinearLayout llLayout;
	}

}