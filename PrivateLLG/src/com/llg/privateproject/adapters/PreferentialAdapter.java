package com.llg.privateproject.adapters;

import java.util.List;
import java.util.Map;

import com.bjg.lcc.privateproject.R;

import android.Manifest.permission;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 优惠券适配器 yh 2015.7.22
 * 
 * 
 * */
public class PreferentialAdapter extends BaseAdapter {
	Context context;
	List<Map<String, Object>> list;
	public int selcetPosition = -1;

	/** 优惠券适配器 */
	public PreferentialAdapter(Context context, List<Map<String, Object>> list) {
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
	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		Map<String, Object> map = list.get(position);
		if (v == null) {
			holder = new ViewHolder();
			v = View.inflate(context, R.layout.order_preferntial_lv_item, null);
			holder.man = (TextView) v.findViewById(R.id.man);
			holder.sheng = (TextView) v.findViewById(R.id.sheng);
			holder.iv_checked = (ImageView) v.findViewById(R.id.iv_checked);

			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();

		}
		holder.man.setText(map.get("man").toString());
		holder.sheng.setText(map.get("sheng").toString());
		if (selcetPosition == position) {
			holder.iv_checked
					.setBackgroundResource(R.drawable.gouwuche_xuanzhong);
		} else {
			holder.iv_checked
					.setBackgroundResource(R.drawable.gouwuche_weixuanzhong);

		}
		return v;
	}

	public void setSelectitem(int position) {
		this.selcetPosition = position;
		notifyDataSetChanged();

	}

	private class ViewHolder {
		TextView man;
		TextView sheng;
		ImageView iv_checked;
	}

}
