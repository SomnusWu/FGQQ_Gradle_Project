package com.llg.privateproject.adapters;

import java.util.List;
import java.util.Map;

import com.bjg.lcc.privateproject.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

/**
 * 商品规格加载适配器 yh 2015.09.18
 * */
public class ProductDetailSizeItemAdapter extends BaseAdapter {
	Context context;
	List<Map<String, Object>> list;

	/**
	 * 商品规格加载适配器
	 * */
	public ProductDetailSizeItemAdapter(Context context,
			List<Map<String, Object>> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder hd = null;
		Map<String, Object> map = list.get(position);
		if (convertView == null) {
			hd = new Holder();
			convertView = View.inflate(context,
					R.layout.product_detailsize_item, null);
			hd.title = (TextView) convertView.findViewById(R.id.guige_title);
			hd.gv = (GridView) convertView.findViewById(R.id.guige_gv);

			convertView.setTag(hd);
		} else {
			hd = (Holder) convertView.getTag();
		}
		hd.title.setText(map.get("").toString());
		return convertView;
	}

	private class Holder {
		TextView title;// 规格标题
		GridView gv;// 规格列表

	}

}
