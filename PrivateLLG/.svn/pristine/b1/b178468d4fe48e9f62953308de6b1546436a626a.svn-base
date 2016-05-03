package com.llg.privateproject.adapter;

import java.util.List;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.BitmapUtils;
import com.llg.help.MyFormat;
import com.llg.privateproject.utils.CommonUtils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Gallery适配器 yh 2015.6.21
 * 
 * */
public class GalleryAdapter extends BaseAdapter {
	Context context;
	LayoutInflater infater;
	// int
	// count[]={R.drawable.defaultpic,R.drawable.bkg,R.drawable.welcome,R.drawable.bkg,R.drawable.welcome};
	List<String> list;

	public GalleryAdapter(Context context, List<String> list) {
		this.context = context;
		this.list = list;

		infater = LayoutInflater.from(context);
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;

		if (convertView == null) {
			viewHolder = new ViewHolder();
			// convertView=View.inflate(context, R.layout.gallery_item, null);
			convertView = infater.inflate(R.layout.gallery_item, parent, false);
			viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// viewHolder.iv.setBackgroundResource(count[position]);

		MyFormat.setBitmap(context, viewHolder.iv, list.get(position), 0, 0);
		return convertView;
	}

	class ViewHolder {
		ImageView iv;
	}
}
