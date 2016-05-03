package com.llg.privateproject.adapters;

import java.util.List;

import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.entities.Fabu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class FabuKindGridAdapter extends BaseAdapter {
	private Fabu fabu;
	private LayoutInflater inflater;

	public FabuKindGridAdapter(Context context, Fabu fabu) {
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
		this.fabu = fabu;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (fabu.getList() != null) {
			return fabu.getList().size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View v, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (v == null) {
			holder = new ViewHolder();
			v = inflater.inflate(R.layout.griditem_fabu_kind, null);
			holder.tvContent = (TextView) v.findViewById(R.id.tv_content);
			holder.line_vertical_gray = v.findViewById(R.id.line);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		if (position != 0 && position % 2 == 0) {
			holder.line_vertical_gray.setVisibility(View.GONE);
		}
		holder.tvContent.setText(fabu.getList().get(position));
		return v;
	}

	private class ViewHolder {
		TextView tvContent;
		View line_vertical_gray;
	}
}
