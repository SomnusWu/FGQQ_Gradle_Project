package com.llg.privateproject.adapters;

import java.util.List;

import com.bjg.lcc.privateproject.R;
import com.finddreams.sortedcontact.ImageTextView;
import com.finddreams.sortedcontact.sortlist.PersonSortMode;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 拨打电话页面 自动搜索 匹配到联系人
 * 
 * @author cc
 * 
 */
public class PhoneSearchAdapter extends BaseAdapter {

	private List<PersonSortMode> list = null;
	private Context mContext;

	public PhoneSearchAdapter(Context mContext, List<PersonSortMode> personList) {
		this.mContext = mContext;
		this.list = personList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
	}

	@Override
	public PersonSortMode getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int id) {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(
					R.layout.phone_constacts_item, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
			viewHolder.tvNumber = (TextView) view.findViewById(R.id.number);
			viewHolder.icon = (ImageTextView) view.findViewById(R.id.icon);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		if (getCount() > 0) {
			final PersonSortMode personSortMode = list.get(position);
			viewHolder.tvLetter.setVisibility(View.GONE);

			viewHolder.tvTitle.setText(personSortMode.getName());
			viewHolder.icon.setText(personSortMode.getName());
			viewHolder.tvNumber.setText(personSortMode.getNumber());
			viewHolder.icon.setIconText(mContext, personSortMode.getName());
		}
		return view;
	}

	final static class ViewHolder {
		TextView tvLetter;
		TextView tvTitle;
		TextView tvNumber;
		ImageTextView icon;
	}

}
