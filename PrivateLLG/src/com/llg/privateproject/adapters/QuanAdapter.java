package com.llg.privateproject.adapters;

import java.util.List;

import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.entities.Quan;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 我的优惠券ListView适配器 yh 2015.06.09
 * */
public class QuanAdapter extends BaseAdapter {
	Context context;
	List<Quan> list;

	/**
	 * 我的优惠券ListView适配器
	 */
	public QuanAdapter(Context context, List<Quan> list) {
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
		Viewholder holder = null;
		Quan quan = list.get(position);
		if (view == null) {
			holder = new Viewholder();
			view = View.inflate(context, R.layout.quanitem, null);
			holder.num = (TextView) view.findViewById(R.id.bianhao);
			holder.money = (TextView) view.findViewById(R.id.mianzhi);
			holder.status = (TextView) view.findViewById(R.id.zhuangtai);
			holder.effective = (TextView) view.findViewById(R.id.youxiaoqi);
			view.setTag(holder);
		} else {
			holder = (Viewholder) view.getTag();
		}
		// Log.i("my", "holder.num"+holder.num);
		// Log.i("my", "quan.getnum"+quan.getNum());
		holder.num.setText(quan.getNum());
		holder.money.setText(quan.getMoney());
		
		holder.status.setText(quan.getStatus());
		holder.effective.setText(quan.getEffective());
		return view;
	}

	class Viewholder {
		TextView num;
		TextView money;
		TextView status;
		TextView effective;
	}

}
