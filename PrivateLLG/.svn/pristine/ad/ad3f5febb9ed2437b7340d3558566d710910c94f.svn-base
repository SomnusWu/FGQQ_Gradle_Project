package com.llg.privateproject.adapters;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.entities.MaybeYouLike;
import com.llg.privateproject.utils.CommonUtils;

/**
 * 猜你喜欢列表适配器 yh 2015.6.16
 * 
 * */
public class MaybeYouLikerAdapter extends BaseAdapter {
	Context context;
	List<Map<String, Object>> list;
	LayoutInflater inflater;
	boolean hasFrame;

	/** 猜你喜欢列表适配器 */
	public MaybeYouLikerAdapter(Context context,
			List<Map<String, Object>> list, boolean hasFrame) {
		this.context = context;
		this.list = list;
		this.hasFrame = hasFrame;
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
		android.view.ViewGroup.LayoutParams lp;
		if (v == null) {

			lp = parent.getLayoutParams();
			lp.width = (list.size() * (AppContext.getScreenWidth()) / 4 + (list
					.size() - 1) * 2);
			parent.setLayoutParams(lp);
			holder = new ViewHolder();
			if (!hasFrame) {
				v = inflater.inflate(R.layout.product_detail_maybyyoulike_item,
						parent, false);
			} else {
				v = inflater.inflate(R.layout.product_detail_maybyyoulike_item,
						parent, false);
			}

			// android.widget.RelativeLayout.LayoutParams
			// lp1=(android.widget.RelativeLayout.LayoutParams)
			// v.holder.iv.getLayoutParams();
			// lp1.width=AppContext.getScreenWidth()/4;
			// lp1.height=AppContext.getScreenWidth()/2;
			// holder.iv.setLayoutParams(lp1);
			holder.iv = (ImageView) v.findViewById(R.id.iv);
			holder.dsc = (TextView) v.findViewById(R.id.dsc);
			holder.price = (TextView) v.findViewById(R.id.price);

			v.setTag(holder);

		} else {
			holder = (ViewHolder) v.getTag();
		}
		// "price": "101.000000",
		// "comodityName": "A7A2015夏季女装韩版宽松休闲印花字母短袖T恤衫女",
		// "shopName": "拉夏贝尔官方旗舰店",
		// "shopId": "402880e54e1e3fd4014e1e5b1f9a0001",
		// "pic": "upload/images/prod/2015-06-15/20152515102541fiAU7OMS.jpg",
		// "commodityId": "402880e54df4ee01014df5171ef7116a",
		// "dsc": null

		LayoutParams params = new LayoutParams(AppContext.getScreenWidth() / 4,
				LayoutParams.MATCH_PARENT);
		v.setLayoutParams(params);
		android.widget.RelativeLayout.LayoutParams lps = (android.widget.RelativeLayout.LayoutParams) holder.iv
				.getLayoutParams();
		lps.width = AppContext.getScreenWidth() / 4;
		lps.height = AppContext.getScreenWidth() / 4;
		holder.iv.setLayoutParams(lps);

		MyFormat.setBitmap(context, holder.iv, entity.get("pic").toString(),
				AppContext.getScreenWidth() / 4,
				AppContext.getScreenWidth() / 4);
		holder.dsc.setText(entity.get("comodityName").toString());
		holder.price.setText("￥"
				+ MyFormat.getPriceFormat(entity.get("price").toString()));
		return v;
	}

	class ViewHolder {
		ImageView iv;
		TextView dsc;
		TextView price;

	}
}
