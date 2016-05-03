package com.llg.privateproject.adapters;

import java.util.List;
import java.util.Map;

import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.bjg.lcc.alipay.pay.PayActivity;
import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.actvity.OrderStatuspingjia;
import com.llg.privateproject.actvity.OrderstatusTuikuan;
import com.llg.privateproject.actvity.WebLoginActivity;
import com.llg.privateproject.adapters.OrderAdapter.ViewHolder;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.view.Gerenziliao_Dialog;
import com.llg.privateproject.view.Gerenziliao_Dialog.InvoiceListener;
import com.llg.privateproject.view.Gerenziliao_Dialog.PostTimeListener;
import com.llg.privateproject.view.OrderStatusDialog.PicListener;
import com.llg.privateproject.view.OrderStatusDialog;
import com.rabbitmq.client.AMQP.Connection.Start;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/***
 * 订单状态适配器 yh 2015.8.1
 * */
public class OrderStatusAdapter extends BaseAdapter {
	Context context;
	List<Map<String, Object>> list;
	/** 评价对话框 */
	OrderStatusDialog dialog1;
	/** 评价对话框回调 */
	Callback callback;
AppContext appContext;
	/** 评价对话框回调 */
	public interface Callback {
		void setDialog(OrderStatusDialog dialog1, LinearLayout ll_pics,
					   ImageView chuantupian);
	}

	/**
	 * 订单状态列表适配器
	 * 
	 * @param dialog12
	 */
	public OrderStatusAdapter(Context context, List<Map<String, Object>> list,
			Callback callback) {
		super();
		this.context = context;
		this.list = list;
		this.callback = callback;
		appContext=(AppContext) ((Activity) context).getApplication();
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
		final Map<String, Object> map = list.get(position);
		List<Map<String, Object>> listProducts = (List<Map<String, Object>>) map
				.get("listProducts");
		ViewHolder holder = null;
		if (v == null) {
			OnClickListener listener = null;
			v = View.inflate(context, R.layout.order_status_list_item, null);

			holder = new ViewHolder();

			holder.shop_name = (TextView) v.findViewById(R.id.shop_name);
			holder.order_status = (TextView) v.findViewById(R.id.order_status);
			holder.product_count = (TextView) v
					.findViewById(R.id.product_count);
			holder.xiaoji = (TextView) v.findViewById(R.id.xiaoji);
			TextView tv= (TextView) v.findViewById(R.id._xiaoji);
			tv.setText("合计:");
			holder.ll_product = (LinearLayout) v.findViewById(R.id.ll_product);
			holder.xian_and_dian = (LinearLayout) v
					.findViewById(R.id.xian_and_dian);
			if (position == 0) {
				holder.xian_and_dian.setVisibility(View.INVISIBLE);
			}
			holder.pay = (TextView) v.findViewById(R.id.pay);
			holder.cancel = (TextView) v.findViewById(R.id.cancel);
			holder.contact = (TextView) v.findViewById(R.id.contact);
			holder.delete = (TextView) v.findViewById(R.id.delete);
			holder.query = (TextView) v.findViewById(R.id.query);
			holder.confirm = (TextView) v.findViewById(R.id.confirm);
			holder.pingjia = (TextView) v.findViewById(R.id.pingjia);
			holder.refund = (TextView) v.findViewById(R.id.refund);
			holder.return_status = (TextView) v
					.findViewById(R.id.return_status);

			listener = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					if(!appContext.isNetworkConnected()){
						Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
						return;
					}
					Intent intent=new Intent();
					if(!AppContext.isLogin){
						Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();
						intent.setClass(context, WebLoginActivity.class);
						context.startActivity(intent);
						return;
					}
					// TODO Auto-generated method stub
					switch (v.getId()) {
					case R.id.pay:// 立即支付
						intent.setClass(context, PayActivity.class);
						intent.putExtra("id", map.get("orderPayId").toString());
						intent.putExtra("code", map.get("orderPayCode").toString());
						intent.putExtra("name", map.get("dsc").toString());
						intent.putExtra("price", map.get("allprice").toString());
						context.startActivity(intent);
				
						break;
					case R.id.cancel:// 取消订单
						Toast.makeText(context, "取消订单", Toast.LENGTH_SHORT)
								.show();
						break;
					case R.id.contact:// 联系卖家
						Toast.makeText(context, "pay", Toast.LENGTH_SHORT)
								.show();
						break;
					case R.id.delete:// 删除订单
						Toast.makeText(context, "删除订单", Toast.LENGTH_SHORT)
								.show();
						break;
					case R.id.query:// 查看物流
						OrderStatusDialog dialog = new OrderStatusDialog(
								context, 1);
						dialog.show();
						Window window = dialog.getWindow();
						WindowManager.LayoutParams lp = dialog.getWindow()
								.getAttributes();
						lp.x = 0;
						lp.y = 0;
						lp.width = AppContext.getScreenWidth();
						lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
						window.setAttributes(lp);
						Toast.makeText(context, "查看物流", Toast.LENGTH_SHORT)
								.show();
						break;
					case R.id.confirm:// 确认收货
						OrderStatusDialog dialog3 = new OrderStatusDialog(
								context, 3);
						dialog3.show();
						Window window3 = dialog3.getWindow();
						WindowManager.LayoutParams lp3 = dialog3.getWindow()
								.getAttributes();
						// lp3.x=0;
						// lp3.y=0;
						lp3.width = AppContext.getScreenWidth() * 4 / 5;
						lp3.height = ViewGroup.LayoutParams.WRAP_CONTENT;
						window3.setAttributes(lp3);
						Toast.makeText(context, "确认收货", Toast.LENGTH_SHORT)
								.show();
						break;
					case R.id.pingjia:// 评价
						intent.setClass(context,
								OrderStatuspingjia.class);
						context.startActivity(intent);
				
						Toast.makeText(context, "评价", Toast.LENGTH_SHORT)
								.show();
						break;
					case R.id.refund:// 退货退款
						Intent intent1 = new Intent(context,
								OrderstatusTuikuan.class);
						context.startActivity(intent1);
						Toast.makeText(context, "退货退款", Toast.LENGTH_SHORT)
								.show();
						break;
					case R.id.return_status:// 退货进度
						OrderStatusDialog dialog7 = new OrderStatusDialog(
								context, 7);
						dialog7.show();
						Window window7 = dialog7.getWindow();
						WindowManager.LayoutParams lp7 = dialog7.getWindow()
								.getAttributes();
						lp7.x = 0;
						lp7.y = 0;
						lp7.width = AppContext.getScreenWidth();
						lp7.height = ViewGroup.LayoutParams.MATCH_PARENT;
						window7.setAttributes(lp7);
						Toast.makeText(context, "查看物流", Toast.LENGTH_SHORT)
								.show();
						break;
					default:
						break;
					}
				}
			};
			holder.pay.setTextColor(context.getResources().getColor(
					R.color.orange2));
			holder.pay.setBackgroundDrawable(context.getResources()
					.getDrawable(R.drawable.stroke_orange2_val));
			holder.pay.setOnClickListener(listener);
			if(map.get("orderStatus").toString().equals("未支付")){
				
				holder.cancel.setVisibility(View.GONE);
				holder.contact.setVisibility(View.GONE);
				holder.delete.setVisibility(View.GONE);
				holder.query.setVisibility(View.GONE);
				holder.confirm.setVisibility(View.GONE);
				holder.pingjia.setVisibility(View.GONE);
				holder.refund.setVisibility(View.GONE);
				holder.return_status.setVisibility(View.GONE);
			}
			// holder.rl_select_posttime=(RelativeLayout)
			// v.findViewById(R.id.rl_select_posttime);
			// holder.rl_select_bill=(RelativeLayout)
			// v.findViewById(R.id.rl_select_bill);
			// holder.et_message=(EditText) v.findViewById(R.id.et_message);
			holder.listProducts = (List<Map<String, Object>>) map
					.get("listProducts");
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		if(map.get("orderPayCode")==null){
			holder.shop_name.setText("付款编号:"+map.get("id").toString() );
		}else{
		holder.shop_name.setText("订单编号:"+map.get("orderPayCode").toString() + "\n"
				+"创建时间:"+ map.get("createDate").toString());}
		holder.order_status.setText(map.get("orderStatus").toString());
		if(map.get("shopProductCount")!=null){
			holder.product_count.setText("共"
					+ map.get("shopProductCount").toString() + "件");
		}else{
			holder.product_count.setVisibility(View.GONE);
		}
		if(map.get("shopProductCount")!=null){
			holder.xiaoji.setText("￥"
					+ MyFormat.getPriceFormat(map.get("allprice").toString()
							.trim()));
		}else{
			holder.xiaoji.setVisibility(View.GONE);
		}
		

		// Log.d("my", "holder.listProducts.size()"+holder.listProducts.size());
		View view = null;
		holder.ll_product.removeAllViews();
		holder.dsc="";
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
			MyFormat.setBitmap(context, product_img, map2.get("img").toString(), MyFormat.dip2px(context, 50), MyFormat.dip2px(context, 50));
			product_dsc.setText(map2.get("productDsc").toString());
			product_price.setText(MyFormat.getPriceFormat(map2
					.get("productPrice").toString().trim()));
			product_count.setText("X" + map2.get("productCount").toString());
			view.setTag(i);
			
			holder.ll_product.addView(view);
			holder.dsc=holder.dsc+map2.get("productDsc").toString()+",";
		}

		v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		if(!appContext.isNetworkConnected()){
			Toast.makeText(context, context.getResources().getString(R.string.t_Connection), Toast.LENGTH_SHORT).show();
		return;
		}
		if(!UserInformation.isLogin()){
			context.startActivity(new Intent(context, WebLoginActivity.class));
			Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();
			return;
		}
				OrderStatusDialog dialog2 = new OrderStatusDialog(context, 2,map.get("orderPayId").toString(),"1");
				
				dialog2.show();
				Window window = dialog2.getWindow();
				android.view.WindowManager.LayoutParams lp = window
						.getAttributes();
				lp.width = AppContext.getScreenWidth();
				lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
				dialog2.getWindow().setAttributes(lp);
			}

		});
		//

		return v;
	}

	private class ViewHolder {
		String dsc;
		/** 店铺名 */
		TextView shop_name;
		/** 订单状态 */
		TextView order_status;
		/** 虚线 */
		LinearLayout xian_and_dian;
		/** 动态加载商品布局 */
		LinearLayout ll_product;
		/** 商品数量 */
		TextView product_count;
		/** 小计 */
		TextView xiaoji;
		/** 立即付款 */
		TextView pay;
		/** 取消订单 */
		TextView cancel;
		/** 联系卖家 */
		TextView contact;
		/** 删除订单 */
		TextView delete;
		/** 查看物流 */
		TextView query;
		/** 确认收货 */
		TextView confirm;
		/** 评价 */
		TextView pingjia;
		/** 退货退款 */
		TextView refund;
		/** 退货状态 */
		TextView return_status;

		List<Map<String, Object>> listProducts = null;
	}

}