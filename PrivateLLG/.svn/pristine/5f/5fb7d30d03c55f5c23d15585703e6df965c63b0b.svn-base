package com.llg.privateproject.adapters;

import java.util.List;
import java.util.Map;

import com.llg.privateproject.AppContext;
import com.bjg.lcc.privateproject.R;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

/**
 * 店铺推荐商品/活动商品 yh 2015.07.29
 * */
public class ShopHuodongAdapter extends BaseAdapter {
	Context context;
	List<Map<String, Object>> list;

	public ShopHuodongAdapter(Context context, List<Map<String, Object>> list) {
		this.context = context;
		this.list = list;
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
	public View getView(final int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		Map<String, Object> map = list.get(position);
		if (v == null) {
			holder = new ViewHolder();
			v = View.inflate(context, R.layout.product_detail_more_pic_item,
					null);
			holder.iv = (ImageView) v.findViewById(R.id.huodong_iv1);
			holder.iv.setTag(position);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();

		}

		holder.iv.setLayoutParams(new LayoutParams(AppContext.getScreenWidth(),
				AppContext.getScreenWidth() / 5 * 2));
		map.get("productId");
		map.get("pic");
		holder.iv.setBackgroundResource(R.drawable.welcome);
		holder.iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "活动" + position, Toast.LENGTH_SHORT)
						.show();
			}
		});
		return v;
	}

	class ViewHolder {
		ImageView iv;
	}
}
