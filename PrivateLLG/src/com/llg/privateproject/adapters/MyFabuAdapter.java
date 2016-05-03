package com.llg.privateproject.adapters;

import java.util.List;

import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.actvity.ModifyActivity;
import com.llg.privateproject.actvity.MyFabuActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MyFabuAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private Dialog dialog;
	private List<String> list;

	public MyFabuAdapter(Context context, Dialog dialog, List<String> list) {
		super();
		// TODO Auto-generated constructor stub
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.list = list;
		this.dialog = dialog;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
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
		ViewHolder holder = null;
		if (v == null) {
			holder = new ViewHolder();
			v = inflater.inflate(R.layout.listitem_myfabu, null);
			holder.tvUpdate = (TextView) v.findViewById(R.id.tv_update);
			holder.tvModify = (TextView) v.findViewById(R.id.tv_modify);
			holder.tvMore = (TextView) v.findViewById(R.id.tv_more);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		holder.tvModify.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, ModifyActivity.class);
				context.startActivity(intent);
			}
		});
		holder.tvUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.show();
			}
		});
		holder.tvMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.show();
			}
		});
		return v;
	}

	private class ViewHolder {
		private TextView tvUpdate;
		private TextView tvModify;
		private TextView tvMore;
	}

	public void setList(List<String> list) {
		this.list = list;
		notifyDataSetChanged();
	}
}
