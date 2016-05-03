package com.llg.privateproject.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjg.lcc.privateproject.R;
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.entities.ShopModel;

public class FGQQShopAdapter extends BaseAdapter {
	private Context mContext;
	private List<ShopModel> mList;

	public FGQQShopAdapter(Context mContext, List<ShopModel> mlist) {
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.mList = mlist;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder viewHolder;
		if (null == convertView) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.home_commodity_item2, parent, false);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.commodity_img = (ImageView) convertView
				.findViewById(R.id.commodity_logo_iv1);
		LayoutParams lp = (LayoutParams) viewHolder.commodity_img
				.getLayoutParams();
		int wid = AppContext.getScreenWidth();
		lp.width = wid / 2;
		lp.height = wid / 2;
		viewHolder.commodity_img.setLayoutParams(lp);

		ShopModel _shopModel = mList.get(position);
		viewHolder.commodity_dsc = (TextView) convertView
				.findViewById(R.id.commodity_name_tv);
		viewHolder.commodity_price = (TextView) convertView
				.findViewById(R.id.company_price);
		viewHolder.commodity_active1 = (TextView) convertView
				.findViewById(R.id.company_zhe1);
		viewHolder.commodity_activity2 = (TextView) convertView
				.findViewById(R.id.company_zhe2);
		viewHolder.commodity_gouwuche = (ImageView) convertView
				.findViewById(R.id.company_gouwuche);

		MyFormat.setBitmap(mContext, viewHolder.commodity_img,
				_shopModel.getImage(), lp.width, lp.height);
		viewHolder.commodity_dsc.setText(_shopModel.getName());
		viewHolder.commodity_price.setText("￥"+_shopModel.getPrice());
		viewHolder.commodity_active1.setVisibility(View.GONE);
		viewHolder.commodity_activity2.setVisibility(View.GONE);

		return convertView;
	}

	class ViewHolder {
		/** 产品图片 */
		ImageView commodity_img;
		/** 产品名称描述 */
		TextView commodity_dsc;
		/** 原价 */
		TextView commodity_oldprice;
		/** 产品好评 人数等 */
		TextView company_intro;
		/** 产品价格 */
		TextView commodity_price;
		/** 活动1 */
		TextView commodity_active1;
		/** 活动2 */
		TextView commodity_activity2;
		/** 购物车 */
		ImageView commodity_gouwuche;
	}

}
