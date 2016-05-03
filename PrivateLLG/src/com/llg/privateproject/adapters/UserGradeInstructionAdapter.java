package com.llg.privateproject.adapters;

import java.util.List;
import java.util.Map;

import com.bjg.lcc.privateproject.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 用户等级功能介绍适配器 yh 2015.08.21
 * */
public class UserGradeInstructionAdapter extends BaseAdapter {
	Context context;
	List<Map<String, Object>> list;
	LayoutInflater inflater;

	/**
	 * 用户等级功能介绍适配器
	 */
	public UserGradeInstructionAdapter(Context context,
			List<Map<String, Object>> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
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
		Map entity = list.get(position);
		if (v == null) {
			holder = new ViewHolder();
			v = inflater.inflate(R.layout.huiyuandengji_jieshao_list_item,
					parent, false);
			// holder.iv=(ImageView) v.findViewById(R.id.iv);
			holder.name = (TextView) v.findViewById(R.id.name);
			holder.dsc = (TextView) v.findViewById(R.id.dsc);
			v.setTag(holder);

		} else {
			holder = (ViewHolder) v.getTag();
		}
		// LayoutParams params=new LayoutParams(parent.getWidth()/9-10,
		// LayoutParams.MATCH_PARENT);
		// v.setLayoutParams(params);

		holder.name.setText(entity.get("name").toString());
		holder.dsc.setText(entity.get("dsc").toString());
//		 Log.d("my", "productshop"+entity.get("dsc").toString());
		return v;
	}

	class ViewHolder {

		TextView name;
		TextView dsc;

	}

}
