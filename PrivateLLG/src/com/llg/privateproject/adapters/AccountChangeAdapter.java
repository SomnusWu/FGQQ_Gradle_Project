package com.llg.privateproject.adapters;

import java.util.List;

import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.adapters.AdvertisementAdapter.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class AccountChangeAdapter extends BaseAdapter {
	private List<String> list;
	private Context context;
	private LayoutInflater inflater;

	public AccountChangeAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 5;
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
			v = inflater.inflate(R.layout.listitem_account_change, null);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		return v;
	}

	class ViewHolder {
	}

}
