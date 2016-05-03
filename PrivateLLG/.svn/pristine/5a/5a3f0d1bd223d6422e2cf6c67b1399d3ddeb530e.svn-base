package com.llg.privateproject.adapters;

import com.bjg.lcc.privateproject.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class OnlineExamAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;

	public OnlineExamAdapter(Context context) {
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View v, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (v == null) {
			holder = new ViewHolder();
			v = inflater.inflate(R.layout.listitem_online_exam, null);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		return v;
	}

	private class ViewHolder {
	}
}
