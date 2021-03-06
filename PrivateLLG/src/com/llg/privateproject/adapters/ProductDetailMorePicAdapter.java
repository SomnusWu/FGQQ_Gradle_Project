package com.llg.privateproject.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.entities.HuoDongZhuanTiEntity;

/** 产品详情图文详情 */
public class ProductDetailMorePicAdapter extends BaseAdapter {
	Context context;
	List<HuoDongZhuanTiEntity> list;

	/** 产品详情图文详情 */
	public ProductDetailMorePicAdapter(Context context,
			List<HuoDongZhuanTiEntity> list) {
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
		HuoDongZhuanTiEntity entity = list.get(position);
		if (v == null) {
			holder = new ViewHolder();
			v = View.inflate(context, R.layout.product_detail_more_pic_item,
					null);
			holder.iv = (ImageView) v.findViewById(R.id.huodong_iv1);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		// holder.iv.setLayoutParams(new
		// LayoutParams(AppContext.getScreenWidth()/10*2,
		// AppContext.getScreenWidth()/10*2));

		holder.iv.setBackgroundResource(R.drawable.bkg);
		return v;
	}

	class ViewHolder {
		ImageView iv;
	}
}