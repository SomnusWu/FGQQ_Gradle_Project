package com.llg.privateproject.adapters;

import java.util.List;
import java.util.Map;

import com.lidroid.xutils.BitmapUtils;
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.entities.GuangGaoJiFenEntity;

import com.llg.privateproject.utils.CommonUtils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

/** 广告积分适配器 */
public class GuangGaoJiFenAdapter extends BaseAdapter {
	Context context;
	List<Map<String, Object>> list;
	LayoutInflater inflater;

	public GuangGaoJiFenAdapter(Context context, List<Map<String, Object>> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
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
	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		Map<String, Object> entity = list.get(position);
		if (v == null) {
			holder = new ViewHolder();
			v = inflater.inflate(R.layout.home_mainad_item, parent, false);
			holder.iv = (ImageView) v.findViewById(R.id.ad_iv1);
			holder.title = (TextView) v.findViewById(R.id.ad_title);
			holder.dsc = (TextView) v.findViewById(R.id.ad_dsc);
			v.setTag(holder);

		} else {
			holder = (ViewHolder) v.getTag();
		}
		LayoutParams params = new LayoutParams(
				AppContext.getScreenWidth() / 10 * 2,
				AppContext.getScreenWidth() / 10 * 2);
		holder.iv.setLayoutParams(params);
		// "content": null,
		// "id": null,
		// "source": null,
		// "objectId": null,
		// "name": "广告名称01",
		// "img": null,
		// "objectType": null,
		// "url": "plug-in/accordion/images/back.png"

		MyFormat.setBitmap(context, holder.iv, entity.get("img").toString(),
				AppContext.getScreenWidth() / 5,
				AppContext.getScreenWidth() / 5);
		holder.title.setText(entity.get("name").toString());
		holder.dsc.setText(entity.get("source").toString());
		// Log.d("my", "productshop"+entity.title);
		return v;
	}

	class ViewHolder {
		ImageView iv;
		TextView title;
		TextView dsc;

	}
}
