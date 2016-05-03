package com.llg.privateproject.adapters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.entities.ShoppingCartEntity.Store;
import com.llg.privateproject.entities.ShoppingCartEntity.Store.CartSpec;
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
public class ShoppingCardAdapter0 extends BaseAdapter {
	Context context;
	List<Store> list;
	SetAllPrice setAllPrice;

	public interface SetAllPrice {
		/** 设置结算价格 */
		void setAllPrice(float allprice);

		void setShopId(boolean checked, String shopId);

		void setSpecIds(boolean checked, String specIds);

		void targetCount(boolean checked, String specId,
						 String targetCount);

		void remove(boolean checked, String specId);
	}

	public ShoppingCardAdapter0(Context context, List<Store> list) {
		this.context = context;
		this.list = list;
	}

	/** 设置总价监听 */
	public void setListener(SetAllPrice setAllPrice) {
		this.setAllPrice = setAllPrice;
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
		final Store item = list.get(position);
		final ViewHolder vH = new ViewHolder();

		v = View.inflate(context, R.layout.shoppingcard_item, null);
		vH.shop_name = (TextView) v.findViewById(R.id.shop_name);
		// vH.shop_economic = (TextView) v.findViewById(R.id.shop_economic);
		vH.shop_eco = (TextView) v.findViewById(R.id.shop_eco);
		vH.ll_product = (LinearLayout) v.findViewById(R.id.ll_product);
		// vH.lv_product = (ListView) v.findViewById(R.id.lv_product);
		final TextView shop_economic = (TextView) v
				.findViewById(R.id.shop_economic);
		shop_economic.setText("" + item.allPrice);
		CheckBox cb_shop = (CheckBox) v.findViewById(R.id.cb_shop);
		cb_shop.setChecked(item.checked);
		cb_shop.setOnCheckedChangeListener(new OnCheckedChangeListener() {// 店铺选中与否监听

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (setAllPrice != null) {
					setAllPrice.setShopId(isChecked, item.shopId);
					Log.d("my", " item.shopId" + item.shopId);
				}
				vH.shop_eco.setText("");

				item.checked = isChecked;
				item.allPrice = 0;
				for (CartSpec cartSpec : item.cartSpec) {
					cartSpec.checked = isChecked;
					if (cartSpec.checked) {
						cartSpec.allCheckedPrice = cartSpec.count
								* cartSpec.price;
					} else {
						cartSpec.allCheckedPrice = 0;
					}
					item.allPrice = item.allPrice + cartSpec.allCheckedPrice;
				}
				if (isChecked) {
					shop_economic.setText("" + item.allPrice);
				} else {
					shop_economic.setText("" + 0);
				}
				// notifyDataSetChanged();

				// setallPrice();
			}
		});
		if (item.shopName != null) {

			vH.shop_name.setText(item.shopName);
		}

		// vH.lv_product.setAdapter(adapter);
		// Log.d("my", "item"+item);
		// Log.d("my", "item.get(productId)"+item.get("productId"));
		// Log.d("my", "item.get(productId)"+item.size());
		// HashMap<String, Object> hashMap=(HashMap<String, Object>) item;
		vH.ll_product.removeAllViews();
		View view = null;
		if (item.cartSpec != null && item.cartSpec.size() > 0) {
			for (final CartSpec cartSpec : item.cartSpec) {
				view = View.inflate(context, R.layout.shopcard_lv_item, null);
				CheckBox cb = (CheckBox) view.findViewById(R.id.cb_shop);
				ImageView shopcard_lv_item_iv = (ImageView) view
						.findViewById(R.id.shopcard_lv_item_iv);
				TextView dsc = (TextView) view.findViewById(R.id.dsc);
				final TextView price = (TextView) view
						.findViewById(R.id.oldPrice);
				TextView guige = (TextView) view.findViewById(R.id.guige);
				final TextView allPrice = (TextView) view
						.findViewById(R.id.price);
				ImageView jian = (ImageView) view.findViewById(R.id.jian);
				final TextView bay_count = (TextView) view
						.findViewById(R.id.bay_count);
				ImageView plus = (ImageView) view.findViewById(R.id.plus);
				ImageView delete = (ImageView) view.findViewById(R.id.delete);

				cb.setChecked(cartSpec.checked);

				cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (setAllPrice != null) {
							setAllPrice.setSpecIds(isChecked, cartSpec.specId);
						}
						cartSpec.checked = isChecked;
						cartSpec.allCheckedPrice = 0;
						if (cartSpec.checked) {
							cartSpec.allCheckedPrice = cartSpec.price
									* cartSpec.count;
						}
						item.allPrice = 0;

						int k = 0;

						for (CartSpec cartSpec : item.cartSpec) {// 重设店铺选总价
							if (cartSpec.checked) {
								k++;
								item.allPrice = item.allPrice
										+ cartSpec.allCheckedPrice;
							}
						}
						if (k == 0) {// 如果商品都不选择让店铺不选中
							item.allPrice = 0;
							item.checked = false;
						} else {
							item.checked = true;
						}
						shop_economic.setText("" + item.allPrice);

						// notifyDataSetChanged();
						// setallPrice();
					}
				});
			
				LayoutParams params = (LayoutParams) shopcard_lv_item_iv
						.getLayoutParams();
				params.width = AppContext.getScreenWidth() / 5;
				params.height = AppContext.getScreenWidth() / 5;
				shopcard_lv_item_iv.setLayoutParams(params);

				MyFormat.setBitmap(context, shopcard_lv_item_iv,
						cartSpec.iconPath, params.width, params.height);
				dsc.setText(cartSpec.prodName);
				// oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
				allPrice.setText("合计:" + cartSpec.sum);
				if(cartSpec.status.equals("WUHUO")){
					
					allPrice.setText("该商品暂无货");
				}else{
				
				}
				// oldPrice.setVisibility(View.GONE);
				guige.setText(cartSpec.specName);
				price.setText(MyFormat.getPriceFormat(String
						.valueOf(cartSpec.price)) + "x" + cartSpec.count);
				jian.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (cartSpec.count > 1) {

							cartSpec.count = cartSpec.count - 1;
							if (setAllPrice != null) {
								setAllPrice.targetCount(true, cartSpec.specId,
										String.valueOf(cartSpec.count));
							}
						} else {
				
						}
						price.setText(MyFormat.getPriceFormat(String
								.valueOf(cartSpec.price))
								+ "x"
								+ cartSpec.count);
						bay_count.setText("" + cartSpec.count);
						cartSpec.sum = cartSpec.price * cartSpec.count;
						if (cartSpec.checked) {

							cartSpec.allCheckedPrice = cartSpec.sum;
						}
						item.allPrice = 0;
						for (CartSpec cartSpec : item.cartSpec) {// 重设店铺选总价
							if (cartSpec.checked) {
								item.allPrice = item.allPrice
										+ cartSpec.allCheckedPrice;
							}
						}
						allPrice.setText("合计:" + cartSpec.sum);
						notifyDataSetChanged();
						shop_economic.setText("" + item.allPrice);
						// setallPrice();
					}

				});

				bay_count.setText("" + cartSpec.count);
				plus.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (cartSpec.cnt > cartSpec.count) {

							cartSpec.count = cartSpec.count + 1;
							cartSpec.allCheckedPrice = cartSpec.count
									* cartSpec.price;
							if (setAllPrice != null) {
								setAllPrice.targetCount(true, cartSpec.specId,
										String.valueOf(cartSpec.count));
							}
						} else {
							Toast.makeText(context, "库存不足", Toast.LENGTH_SHORT)
									.show();
						}

						bay_count.setText("" + cartSpec.count);
						price.setText(MyFormat.getPriceFormat(String
								.valueOf(cartSpec.price))
								+ "x"
								+ cartSpec.count);
						cartSpec.sum = cartSpec.price * cartSpec.count;
						if (cartSpec.checked) {

							cartSpec.allCheckedPrice = cartSpec.sum;
						}
						item.allPrice = 0;
						for (CartSpec cartSpec : item.cartSpec) {// 重设店铺选总价
							if (cartSpec.checked) {
								item.allPrice = item.allPrice
										+ cartSpec.allCheckedPrice;
							}
						}
						allPrice.setText("合计:" + cartSpec.sum);
						allPrice.setText("合计:" + cartSpec.price
								* cartSpec.count);
						notifyDataSetChanged();
						shop_economic.setText("" + item.allPrice);
						// setallPrice();
					}
				});
				delete.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (setAllPrice != null) {
							setAllPrice.remove(true, cartSpec.specId);
						}

					}
				});
				view.setTag(cartSpec.specId);
				vH.ll_product.addView(view);
			}
		}

		return v;
	}

	public class ViewHolder {
		CheckBox cb_shop;
		TextView shop_name;
		TextView shop_eco;
		TextView shop_economic;
		ListView lv_product;
		LinearLayout ll_product;
		int buyCount;
	}
}
