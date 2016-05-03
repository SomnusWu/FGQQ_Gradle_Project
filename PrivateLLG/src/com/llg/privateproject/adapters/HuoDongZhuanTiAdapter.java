package com.llg.privateproject.adapters;

import java.util.List;
import java.util.Map;

import com.lidroid.xutils.BitmapUtils;
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.entities.HuoDongZhuanTiEntity;
import com.llg.privateproject.utils.CommonUtils;

import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * 活动专区适配器
 * */
public class HuoDongZhuanTiAdapter extends BaseAdapter {
	Context context;
	List<Map<String, Object>> list;

	public HuoDongZhuanTiAdapter(Context context, List<Map<String, Object>> list) {
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
			v = View.inflate(context, R.layout.home_huodongzhuanqu_item, null);
			holder.iv = (ImageView) v.findViewById(R.id.huodong_iv1);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		// "content": null,
		// "id": "7",
		// "source": 50,
		// "objectId": "402880eb4ef1347e014ef281224d0042",
		// "name": null,
		// "img": null,
		// "objectType": "2",
		// "url": "plug-in/accordion/images/back.png"

		holder.iv.setLayoutParams(new RelativeLayout.LayoutParams(AppContext
				.getScreenWidth() / 2, AppContext.getScreenWidth() / 6));

		MyFormat.setBitmap(context, holder.iv, entity.get("img").toString(),
				AppContext.getScreenWidth() / 2,
				AppContext.getScreenWidth() / 6);
		holder.iv.setBackgroundResource(R.drawable.defaultpic);
		return v;
	}

	class ViewHolder {
		ImageView iv;
	}
}
