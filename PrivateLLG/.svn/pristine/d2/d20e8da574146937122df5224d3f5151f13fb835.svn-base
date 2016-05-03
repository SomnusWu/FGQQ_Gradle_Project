package com.llg.privateproject.adapters;

import java.util.List;

import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.entities.InformationItem;
import com.llg.privateproject.utils.LogManag;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 个人中心列表适配器
 * 
 * @author yh 2015.5.26
 */
public class InformationItemAdapter extends BaseAdapter {
	List<InformationItem> list;
	int images[];
	Context context;

	public InformationItemAdapter(Context context, List<InformationItem> list) {
		this.list = list;
		this.context = context;
		images = context.getResources().getIntArray(
				R.array.userinformationimages);
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
		InformationItem informationItem = list.get(position);
		if (view == null) {
			view = View.inflate(context, R.layout.myinfo_item, null);
			holder = new ViewHolder();
			holder.name = (TextView) view.findViewById(R.id.name);
			holder.dsc = (TextView) view.findViewById(R.id.dsc);
			holder.iv = (ImageView) view.findViewById(R.id.iv);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.name.setText(informationItem.name);
		holder.dsc.setText(informationItem.dsc);

		holder.iv.setBackgroundResource(informationItem.image);

		return view;
	}

	private class ViewHolder {
		private TextView name;
		private TextView dsc;
		private ImageView iv;
	}

}
