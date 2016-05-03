package com.llg.privateproject.adapters;

import java.util.List;

import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.adapters.FabuAdapter.ViewHolder;
import com.llg.privateproject.entities.ThreeRegion;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ThreeRegionAdapter extends BaseAdapter {

	private List<ThreeRegion> list;
	private Context context;
	private LayoutInflater inflater;

	public ThreeRegionAdapter(Context context, List<ThreeRegion> list) {
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
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int arg0, View v, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (v == null) {
			holder = new ViewHolder();
			v = inflater.inflate(R.layout.listitem_select_city, null);
			holder.tvContent = (TextView) v.findViewById(R.id.tv_content);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		holder.tvContent.setText(list.get(arg0).getName());
		return v;
	}

	class ViewHolder {
		TextView tvContent;
	}

	public void setList(List<ThreeRegion> list) {
		Log.i("tag", list.size() + "传过去的list大小");
		this.list = list;
		notifyDataSetChanged();
	}

}
