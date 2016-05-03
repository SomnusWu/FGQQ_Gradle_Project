package com.llg.privateproject.adapters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.llg.privateproject.AppContext;
import com.bjg.lcc.privateproject.R;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 购物车列表适配器 yh 2015.7.8
 * 
 * */
public class ShoppingCardAdapter extends BaseAdapter {
	Context context;
	List<Map<String, Object>> list;

	public ShoppingCardAdapter(Context context, List<Map<String, Object>> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		// Log.d("my", "list.size()"+list.size());
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
		HashMap<String, Object> item = (HashMap<String, Object>) list
				.get(position);
		ViewHolder vH = null;
		if (v == null) {
			v = View.inflate(context, R.layout.shoppingcard_item, null);
			vH = new ViewHolder();
			vH.cb_shop = (CheckBox) v.findViewById(R.id.cb_shop);
			vH.shop_name = (TextView) v.findViewById(R.id.shop_name);
			vH.shop_economic = (TextView) v.findViewById(R.id.shop_economic);
			vH.ll_product = (LinearLayout) v.findViewById(R.id.ll_product);
			// vH.lv_product = (ListView) v.findViewById(R.id.lv_product);
			v.setTag(vH);
		} else {
			vH = (ViewHolder) v.getTag();
		}
		if (item.get("shopName") != null) {

			vH.shop_name.setText(item.get("shopName").toString());
		}
		if (item.get("activity1") != null) {

			vH.shop_economic.setText(item.get("activity1").toString());
		}
		// vH.lv_product.setAdapter(adapter);
		// Log.d("my", "item"+item);
		// Log.d("my", "item.get(productId)"+item.get("productId"));
		// Log.d("my", "item.get(productId)"+item.size());
		// HashMap<String, Object> hashMap=(HashMap<String, Object>) item;
		vH.ll_product.removeAllViews();
		View view = null;
		for (int i = 0; i < 1; i++) {
			view = View.inflate(context, R.layout.shopcard_lv_item, null);
			CheckBox cb = (CheckBox) view.findViewById(R.id.cb_shop);
			ImageView shopcard_lv_item_iv = (ImageView) view
					.findViewById(R.id.shopcard_lv_item_iv);
			TextView dsc = (TextView) view.findViewById(R.id.dsc);
			TextView oldPrice = (TextView) view.findViewById(R.id.oldPrice);
			TextView guige = (TextView) view.findViewById(R.id.guige);
			TextView price = (TextView) view.findViewById(R.id.price);
			ImageView jian = (ImageView) view.findViewById(R.id.jian);
			TextView bay_count = (TextView) view.findViewById(R.id.bay_count);
			ImageView plus = (ImageView) view.findViewById(R.id.plus);
			ImageView delete = (ImageView) view.findViewById(R.id.delete);
			cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					// TODO Auto-generated method stub
					Toast.makeText(context, "isChecked" + isChecked,
							Toast.LENGTH_SHORT).show();
				}
			});
			LayoutParams params = (LayoutParams) shopcard_lv_item_iv
					.getLayoutParams();
			params.width = AppContext.getScreenWidth() / 5;
			params.height = AppContext.getScreenWidth() / 5;
			shopcard_lv_item_iv.setLayoutParams(params);
			shopcard_lv_item_iv.setBackgroundResource(R.drawable.welcome);
			dsc.setText(item.get("dsc").toString());
			oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			oldPrice.setText(item.get("oldPrice").toString());
			guige.setText(item.get("guige").toString());
			price.setText(item.get("price").toString());
			jian.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(context, "减", Toast.LENGTH_SHORT).show();
				}
			});
			bay_count.setText(item.get("bayCount").toString());
			plus.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(context, "加", Toast.LENGTH_SHORT).show();

				}
			});
			delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(context, "delete", Toast.LENGTH_SHORT)
							.show();
				}
			});
			view.setTag(i);
			vH.ll_product.addView(view);
		}

		return v;
	}

	class ViewHolder {
		CheckBox cb_shop;
		TextView shop_name;
		TextView shop_economic;
		ListView lv_product;
		LinearLayout ll_product;
		int buyCount;
	}
}
