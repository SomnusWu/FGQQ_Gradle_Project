package com.llg.privateproject.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.view.Gerenziliao_Dialog;
import com.llg.privateproject.view.Gerenziliao_Dialog.PostTimeListener;
import com.llg.privateproject.view.Gerenziliao_Dialog.InvoiceListener;
import com.smartandroid.sa.sql.query.Set;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 订单列表适配器 yh. 2015.07.14
 * */
public class OrderAdapter extends BaseAdapter {
	Context context;
	List<Map<String, Object>> list;

	/** 订单列表适配器 */
	public OrderAdapter(Context context, List<Map<String, Object>> list,
			ChangeInvoice changeInvoice) {
		super();
		this.context = context;
		this.list = list;
		this.changeInvoice = changeInvoice;
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
		// Log.d("my", "position"+position);
		Map<String, Object> map = list.get(position);
		List<Map<String, Object>> listProducts = (List<Map<String, Object>>) map
				.get("listProducts");
		ViewHolder holder = null;
		if (v == null) {
			v = View.inflate(context, R.layout.order_gridview_item, null);
			holder = new ViewHolder();
			holder.shop_name = (TextView) v.findViewById(R.id.shop_name);
			holder.product_count = (TextView) v
					.findViewById(R.id.product_count);
			holder.xiaoji = (TextView) v.findViewById(R.id.xiaoji);
			holder.ll_product = (LinearLayout) v.findViewById(R.id.ll_product);
			holder.rl_select_posttime = (RelativeLayout) v
					.findViewById(R.id.rl_select_posttime);
			holder.rl_select_bill = (RelativeLayout) v
					.findViewById(R.id.rl_select_bill);
			holder.et_message = (EditText) v.findViewById(R.id.et_message);
			holder.posttime = (TextView) v.findViewById(R.id.posttime);
			holder.payment = (TextView) v.findViewById(R.id.payment);
			holder.select_bill = (TextView) v.findViewById(R.id.select_bill);
			holder.select_billcategory = (TextView) v
					.findViewById(R.id.select_billcategory);
			// holder.listProducts=(List<Map<String, Object>>)
			// map.get("listProducts");
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		holder.shop_name.setText(map.get("shopName").toString());
		holder.product_count.setText("共"
				+ map.get("shopProductCount").toString() + "件");
		holder.xiaoji.setText("￥"
				+ MyFormat.getPriceFormat(map.get("shopTotalprice").toString()
						.trim()));
		holder.posttime.setText(map.get("postTime").toString());
		holder.payment.setText(map.get("payment").toString());
		if(map.get("invoiceType").toString().equals("1")){
			holder.select_bill.setText("个人");
		}else if(map.get("invoiceType").toString().equals("2")){
			holder.select_bill.setText("公司");
		}else {
			holder.select_bill.setText("不开发票");
		}
		if (position < list.size() - 1) {
			// holder.rl_select_posttime.setVisibility(View.INVISIBLE);
			// holder.rl_select_bill.setVisibility(View.INVISIBLE);
			holder.et_message.setVisibility(View.INVISIBLE);
		} else if (position == (list.size() - 1)) {
			holder.rl_select_posttime.setVisibility(View.VISIBLE);
			holder.rl_select_bill.setVisibility(View.GONE);
			holder.et_message.setVisibility(View.INVISIBLE);

		}
		if (!map.get("invoiceHead").toString().equals("不开发票")
				&& map.get("invoiceCategory").toString().length() > 1) {
			// Log.d("my",
			// "map.get(invoiceHead).toString()"+map.get("invoiceHead").toString());
			holder.select_billcategory.setVisibility(View.VISIBLE);
			holder.select_billcategory.setText(map.get("invoiceHead")
					.toString());
		} else {
			holder.select_billcategory.setText("");
		}
		// Log.d("my", "holder.listProducts.size()"+holder.listProducts.size());
		View view = null;
		holder.ll_product.removeAllViews();
		for (int i = 0; i < listProducts.size(); i++) {
			Map<String, Object> map2 = listProducts.get(i);
			// }
			// for (Map<String, Object> map2 : holder.listProducts){
			view = View.inflate(context, R.layout.order_gridview_item_item,
					null);
			ImageView product_img = (ImageView) view
					.findViewById(R.id.product_img);
			TextView product_dsc = (TextView) view
					.findViewById(R.id.product_dsc);
			TextView product_price = (TextView) view
					.findViewById(R.id.product_price);
			TextView product_count = (TextView) view
					.findViewById(R.id.product_count);

//			product_img.setBackgroundResource(R.drawable.welcome);
			MyFormat.setBitmap(context, product_img, map2.get("productImg")
					.toString(), MyFormat.dip2px(context, 50), MyFormat.dip2px(
					context, 50));

			product_dsc.setText(map2.get("productDsc").toString());
			product_price.setText(MyFormat.getPriceFormat(map2
					.get("productPrice").toString().trim()));
			product_count.setText("X" + map2.get("productCount").toString());
			view.setTag(i);

			holder.ll_product.addView(view);
		}

		holder.rl_select_posttime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// Gerenziliao_Dialog dialog=new Gerenziliao_Dialog(context, 7,
				// new PostTimeListener() {
				//
				// @Override
				// public void setPosttimeandPayment(String postTime, String
				// payment) {
				// // TODO Auto-generated method stub
				//
				// list.get(position).put("postTime", postTime);
				// list.get(position).put("payment", payment);
				// notifyDataSetChanged();
				// }
				// });
				// dialog.show();
				// LayoutParams lp=dialog.getWindow().getAttributes();
				// lp.width=AppContext.getScreenWidth();
				// lp.height=AppContext.getScreenHeight();
				// dialog.getWindow().setAttributes(lp);

			}
		});
		holder.rl_select_bill.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Gerenziliao_Dialog dialog = new Gerenziliao_Dialog(context, 8,
						new InvoiceListener() {

							@Override
							public void setInvoice(String invoiceHead,
									String invoiceCategory, String invoiceType) {
								// TODO Auto-generated method stub
								Log.d("my", invoiceHead);//开头名称(xxx公司/xx人)
								Log.d("my", invoiceCategory);//发票类型对应的名称(公司/个人/)
								Log.d("my", invoiceType);//发票类型
								if (changeInvoice != null) {
									changeInvoice.getInvoiceType(invoiceHead,
											invoiceCategory, invoiceType);
								}
								list.get(position).put("invoiceHead",
										invoiceHead);
								list.get(position).put("invoiceType",
										invoiceType);
								// list.get(position).put("invoiceType", type);
								list.get(position).put("invoiceCategory",
										invoiceCategory);

								notifyDataSetChanged();
							}

						});
				dialog.show();
				LayoutParams lp = dialog.getWindow().getAttributes();
				lp.width = AppContext.getScreenWidth();
				lp.height = AppContext.getScreenHeight();
				dialog.getWindow().setAttributes(lp);
				Toast.makeText(context, "选择发票", Toast.LENGTH_SHORT).show();
			}
		});

		return v;
	}

	private ChangeInvoice changeInvoice;

	public interface ChangeInvoice {
		void getInvoiceType(String invoiceHead, String invoiceCategory,
							String invoiceType);

	}

	public class ViewHolder {
		TextView shop_name;
		TextView product_count;
		TextView xiaoji;
		TextView posttime;
		TextView payment;
		TextView select_bill;
		TextView select_billcategory;
		LinearLayout ll_product;
		RelativeLayout rl_select_posttime;
		RelativeLayout rl_select_bill;
		EditText et_message;
		List<Map<String, Object>> listProducts = null;
	}

}
