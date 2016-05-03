package com.llg.privateproject.adapters;

import java.util.List;

import com.llg.help.DataEntity;
import com.bjg.lcc.privateproject.R;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DateAdapter extends BaseAdapter {
	Context context;
	List<DataEntity> list;

	public DateAdapter(Context context, List<DataEntity> list) {
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
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		DataEntity data = list.get(position);
		if (view == null) {
			holder = new ViewHolder();
			view = View.inflate(context, R.layout.date_item, null);
			holder.tv = (TextView) view.findViewById(R.id.date_item);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.tv.setText(data.data);
		Log.d("my", "adaper" + data.data);
		return view;
	}

	class ViewHolder {
		private TextView tv;

	}

}
