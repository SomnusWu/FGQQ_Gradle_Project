package com.llg.privateproject.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjg.lcc.privateproject.R;

/**
 * 分类列表适配器
 * 
 * @author gongyibing
 */
public class CateListAdapter extends BaseAdapter {
	private Context mContext;
	/** 分类数据 */
	private List<Map<String, Object>> list;
	private LayoutInflater mInflater;
	/** 设置默认选中 */
	private int mSelectedPos = 0;

	public CateListAdapter(Context context, List<Map<String, Object>> list) {
		super();
		this.mContext = context;
		this.list = list;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {

		return (null == list) ? 0 : list.size();

	}

	@Override
	public Object getItem(int position) {
		return (null == list) ? null : list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == mContext) {
			return null;
		}
		if (null == list || list.size() == 0 || list.size() <= position) {
			return null;
		}
		final ViewHolder viewHolder;
		Map<String, Object> map = list.get(position);
		if (null == convertView) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.cate_list_item, parent,
					false);
			viewHolder.textView = (TextView) convertView
					.findViewById(R.id.cate_tv);
			viewHolder.rl = (RelativeLayout) convertView.findViewById(R.id.rl);
			viewHolder._view = convertView.findViewById(R.id._view);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.textView.setText(map.get("name").toString());

		if (mSelectedPos == position) {
			viewHolder.textView.setTextColor(mContext.getResources().getColor(
					R.color.orange1));
			Drawable drawable = mContext.getResources().getDrawable(
					R.drawable.erji_xiafan_yellow);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());
			viewHolder.textView
					.setCompoundDrawables(null, null, drawable, null);
			viewHolder.rl.setBackgroundColor(Color.parseColor("#ffffff"));

			convertView.setBackgroundColor(Color.parseColor("#ffffff"));
			// viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(null,
			// null,
			// mContext.getResources().getDrawable(R.drawable.erji_xiafan_yellow),
			// null);
		} else {
			viewHolder.textView.setTextColor(mContext.getResources().getColor(
					R.color.white));
			Drawable drawable = mContext.getResources().getDrawable(
					R.drawable.erji_xiafan_white);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());
			viewHolder.textView
					.setCompoundDrawables(null, null, drawable, null);
			viewHolder.rl.setBackgroundColor(Color.parseColor("#ff9d45"));
			convertView.setBackgroundColor(Color.parseColor("#ff9d45"));

			// viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(null,
			// null,
			// mContext.getResources().getDrawable(R.drawable.erji_xiafan_white),
			// null);
		}
		if ((position + 1) <= list.size() && mSelectedPos == (position + 1)) {
			viewHolder._view.setVisibility(View.INVISIBLE);
		} else {
			viewHolder._view.setVisibility(View.VISIBLE);
		}
		return convertView;
	}

	class ViewHolder {
		TextView textView;
		RelativeLayout rl;
		View _view;
	}

	/** 设置选中项 */
	public void setSelectedPos(int position) {
		this.mSelectedPos = position;
		notifyDataSetChanged();
	}
}
