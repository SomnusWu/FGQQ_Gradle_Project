package com.llg.privateproject.adapters;

import java.util.List;

import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.AppContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MydialogAdatper extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private int height;
	private List<String> list;

	public MydialogAdatper(Context context, List<String> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View v, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (v == null) {
			holder = new ViewHolder();
			v = inflater.inflate(R.layout.listitem_dialog_fabu, null);
			holder.tv = (TextView) v.findViewById(R.id.tv);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		holder.tv.setText(list.get(position));
		holder.tv.setHeight(height);
		holder.tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			}
		});
		Log.i("tag", height + "+getview++++++++++");
		// if (position == 2) {
		// holder.tv.setBackgroundResource((R.drawable.bg_green_biankuang));
		// }else {
		// holder.tv.setBackgroundResource((android.R.color.transparent));
		// }
		return v;
	}

	private class ViewHolder {
		private TextView tv;
	}

	public void setTvHeitht(int height) {
		this.height = height;
		notifyDataSetChanged();
	}

	public void setList(List<String> list) {
		this.list = list;
		notifyDataSetChanged();
		Log.i("tag", list.size() + "----setlist------");
	}

}
