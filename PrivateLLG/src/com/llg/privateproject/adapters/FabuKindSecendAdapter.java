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

public class FabuKindSecendAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<List<Fabu>> bigList;
	private Context context;
	private int selectPostion = 0;

	public FabuKindSecendAdapter(Context context, List<List<Fabu>> bigList) {
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.bigList = bigList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (bigList != null) {
			return bigList.size();
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
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (v == null) {
			holder = new ViewHolder();
			v = inflater.inflate(R.layout.listitem_fabu_kind_secend, null);
			holder.tvTile = (TextView) v.findViewById(R.id.tv_title);
			holder.gridView = (GridView) v
					.findViewById(R.id.secondary_gridView_id);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		holder.tvTile.setText(bigList.get(selectPostion).get(position)
				.getTitle());
		FabuKindGridAdapter adapter = new FabuKindGridAdapter(context, bigList
				.get(selectPostion).get(position));
		holder.gridView.setAdapter(adapter);
		return v;
	}

	private class ViewHolder {
		TextView tvTile;
		GridView gridView;
	}

	/** 设置选中项 */
	public void setSelectedPos(int position) {
		this.selectPostion = position;
		notifyDataSetChanged();
	}
}
