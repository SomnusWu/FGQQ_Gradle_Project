package com.llg.privateproject.adapters;

import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.actvity.MineTask;
import com.llg.privateproject.actvity.OrderDetailAty;
import com.llg.privateproject.actvity.SignInAty;
import com.llg.privateproject.actvity.TaskOrderDetailAty;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyTaskAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private Context context;

	public MyTaskAdapter(Context context) {
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
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
			v = inflater.inflate(R.layout.listitem_task, null);
			holder.tvPhoto = (TextView) v.findViewById(R.id.tv_photo);
			holder.tvOrderDetail = (TextView) v
					.findViewById(R.id.tv_order_detail);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		holder.tvPhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, SignInAty.class);
				context.startActivity(intent);
			}
		});
		holder.tvOrderDetail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, TaskOrderDetailAty.class);
				context.startActivity(intent);
			}
		});
		return v;
	}

	private class ViewHolder {
		private TextView tvOrderDetail;
		private TextView tvPhoto;
	}
}
