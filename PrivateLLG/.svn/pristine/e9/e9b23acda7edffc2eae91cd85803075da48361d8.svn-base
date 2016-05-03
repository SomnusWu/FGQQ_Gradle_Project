package com.llg.privateproject.adapters;

import java.util.List;

import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.entities.Region;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NearbyRightAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<Region> list;
	private int selectPositon = 0;

	// private NearbyPopuWindow nearbyPopuWindow;

	public NearbyRightAdapter(Context context, List<Region> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (list == null) {
			return 0;
		} else if (list != null && list.size() == 0) {
			return 0;
		}
		return list.get(selectPositon).getList().size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(selectPositon).getList().get(arg0).getId();
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View v, ViewGroup arg2) {
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
		holder.tvContent.setText(list.get(selectPositon).getList()
				.get(position).getName());
		// v.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// nearbyPopuWindow.dismiss();
		// nearbyPopuWindow.setID(getItemId(position) + "");
		// }
		// });
		return v;
	}

	class ViewHolder {
		private TextView tvContent;
	}

	public void setSelectPosition(int mposition) {
		this.selectPositon = mposition;
		notifyDataSetChanged();
		// Log.i("tag", (nearbyPopuWindow == null) +
		// "--------nearbyPopu是否为空-----");
	}

	// public void setNearbyPopuwindow(NearbyPopuWindow nearbyPopuWindow) {
	// this.nearbyPopuWindow = nearbyPopuWindow;
	// }

	public void setList(List<Region> list) {
		this.list = list;
		notifyDataSetChanged();
	}
}
