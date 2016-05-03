package com.llg.privateproject.adapters;

import java.util.ArrayList;
import java.util.List;

import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.adapters.FabuAdapter.ViewHolder;
import com.llg.privateproject.entities.MenuItem;
import com.llg.privateproject.entities.Region;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchHistoryAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<String> list;
	private int selectPosition;

	public SearchHistoryAdapter(Context context, List<String> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return (list == null) ? 0 : list.size();
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
	public View getView(int position, View v, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (v == null) {
			holder = new ViewHolder();
			v = inflater.inflate(R.layout.popu_listitem_nearby, null);
			holder.tvContent = (TextView) v.findViewById(R.id.tv_content);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		holder.tvContent.setText(list.get(position));
		Log.i("tag", list.get(position) + "---------执行了getview------");
		return v;
	}

	class ViewHolder {
		private TextView tvContent;
	}

	public void setList(List<String> list) {
		this.list = list;
		notifyDataSetChanged();
	}
}
