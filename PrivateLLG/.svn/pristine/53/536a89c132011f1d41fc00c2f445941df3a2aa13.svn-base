package com.llg.privateproject.adapters;

import java.util.List;
import java.util.Map;

import com.lidroid.xutils.BitmapUtils;
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.entities.LinLinGouZhuTiGuanEntity;

import com.llg.privateproject.utils.CommonUtils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class LinLinGouZhuTiGuanAdapter extends BaseAdapter {
	Context context;
	List<Map<String, Object>> list;

	public LinLinGouZhuTiGuanAdapter(Context context,
			List<Map<String, Object>> list) {
		this.context = context;
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
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		Map<String, Object> entity = list.get(position);
		if (v == null) {
			holder = new ViewHolder();
			v = View.inflate(context, R.layout.home_linlingouzhutiguan_item,
					null);
			holder.iv = (ImageView) v.findViewById(R.id.home_img_id);
			holder.tv = (TextView) v.findViewById(R.id.Home_img_description);
			holder.iv.setScaleType(ScaleType.CENTER_INSIDE);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		holder.iv.setLayoutParams(new LinearLayout.LayoutParams(AppContext
				.getScreenWidth() / 4, AppContext.getScreenWidth() / 5));
		holder.tv.setText(entity.get("name").toString());

		MyFormat.setBitmap(context, holder.iv, entity.get("img").toString(),
				AppContext.getScreenWidth() / 4,
				AppContext.getScreenWidth() / 5);
		return v;
	}

	class ViewHolder {
		ImageView iv;
		TextView tv;
	}
}
