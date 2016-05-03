package com.llg.privateproject.adapters;

import java.util.List;
import java.util.Map;

import com.lidroid.xutils.BitmapUtils;
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.entities.PinPaiTuiJianEntity;
import com.llg.privateproject.utils.CommonUtils;
import com.llg.privateproject.utils.Constants;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

/**
 * 首页品牌推荐适配器 yh
 * */
public class PinPaiTuiJianAdapter extends BaseAdapter {
	Context context;
	List<Map<String, Object>> list;

	public PinPaiTuiJianAdapter(Context context, List<Map<String, Object>> list) {
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
		holder.iv.setLayoutParams(new RelativeLayout.LayoutParams(AppContext
				.getScreenWidth() / 3, AppContext.getScreenWidth() / 9));
		MyFormat.setBitmap(context, holder.iv, entity.get("img").toString(),
				AppContext.getScreenWidth() / 3,
				AppContext.getScreenWidth() / 9);
		return v;
	}

	class ViewHolder {
		ImageView iv;
	}
}
