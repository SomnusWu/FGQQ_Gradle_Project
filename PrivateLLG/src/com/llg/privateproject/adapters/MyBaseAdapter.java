package com.llg.privateproject.adapters;

import java.util.List;
import java.util.Map;

import com.llg.privateproject.entities.SMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
/**
 * @author yh
 * 2016.1.29
 * */
public class MyBaseAdapter extends BaseAdapter{
	
	Context context;
	List<SMap> list;
	LayoutInflater inflater;


	/** 猜你喜欢列表适配器 */
	public MyBaseAdapter(Context context,
			List<SMap> list) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
