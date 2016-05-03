package com.llg.privateproject.adapters;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjg.lcc.privateproject.R;
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.actvity.OrderStatuspingjia;
import com.llg.privateproject.entities.OrderComment;
import com.llg.privateproject.view.OrderStatusDialog;

public class OrderCommentAdapter extends BaseAdapter {
	private List<OrderComment> list;
	private Context context;
	private LayoutInflater inflater;
	private boolean isDisPlay = false;

	public OrderCommentAdapter(Context context, List<OrderComment> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.list = list;
	}
	public void setCommentButton(boolean isDisplay){
		this.isDisPlay = isDisplay;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.i("tag", list.size() + "=========执行了getCountList的大小");
		return (list == null) ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int arg0, View v, ViewGroup arg2) {
		// TODO Auto-generated method stub
		Log.i("tag", "=========执行了getview");
		final OrderComment item = list.get(arg0);
		ViewHolder holder;
		if (v == null) {
			holder = new ViewHolder();
			v = inflater.inflate(R.layout.order_gridview_item_item, null);
			holder.tvPrice = (TextView) v.findViewById(R.id.product_price);
			holder.iv = (ImageView) v.findViewById(R.id.product_img);
			holder.tvTitle = (TextView) v.findViewById(R.id.product_dsc);
			holder.tvComment = (TextView) v.findViewById(R.id.tv_comment);
//			holder.tvComment.setVisibility(View.VISIBLE);
			v.findViewById(R.id.product_img);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		if (isDisPlay) {
			holder.tvComment.setVisibility(View.VISIBLE);
		}else{
			holder.tvComment.setVisibility(View.GONE);
		}
		holder.tvTitle.setText(list.get(arg0).getPRODNAME());
		v.setPadding(0, 0, 100, 0);
		String img = list.get(arg0).getIMGURL();

		MyFormat.setBitmap(context, holder.iv, img, 0, 0);
		holder.tvComment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, OrderStatuspingjia.class);
				intent.putExtra("oid", item.getORDERBASEID());// 订单id

				intent.putExtra("odid", item.getORDERDETAILID());// 订单详情id
				intent.putExtra("pid", item.getPRODBASEID());// 商品id
				intent.putExtra("sid", item.getPRODSPECID());// 规格id
				intent.putExtra("img", item.getIMGURL());// 商品图片
				intent.putExtra("name", item.getPRODNAME());// 商品名

				context.startActivity(intent);
			}
		});
		v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("my", "item.getORDERBASEID()" + item.getORDERBASEID());
				OrderStatusDialog dialog2 = new OrderStatusDialog(context, 2,
						item.getORDERBASEID(), "2");

				dialog2.show();
				Window window = dialog2.getWindow();
				android.view.WindowManager.LayoutParams lp = window
						.getAttributes();
				lp.width = AppContext.getScreenWidth();
				lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
				dialog2.getWindow().setAttributes(lp);

			}
		});
		return v;
	}

	public void setList(List<OrderComment> list) {
		this.list = list;
		notifyDataSetChanged();
		Log.i("tag", list.size() + "=========执行了setList的大小");
	}

	class ViewHolder {
		ImageView iv;
		TextView tvTitle;
		TextView tvPrice;
		TextView tvCount;
		TextView tvComment;
	}
}
