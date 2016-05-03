package com.llg.privateproject.adapters;

import java.util.List;

import com.android.util.SharedPreferecesUtils;
import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.actvity.LoginActivity;
import com.llg.privateproject.actvity.MineTask;
import com.llg.privateproject.actvity.OnlineExamAty;
import com.llg.privateproject.actvity.OrderDetailAty;
import com.llg.privateproject.actvity.WebLoginActivity;
import com.llg.privateproject.view.DialogAuthentication;
import com.llg.privateproject.view.DialogAuthenticationHint;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DeliveryAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private Context context;

	public DeliveryAdapter(Context context) {
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View v, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (v == null) {
			holder = new ViewHolder();
			v = inflater.inflate(R.layout.listitem_delivery, null);
			holder.iv = (ImageView) v.findViewById(R.id.iv);
			holder.tvBuyOrder = (TextView) v.findViewById(R.id.tv_buy_order);
			holder.tvOrderDetail = (TextView) v
					.findViewById(R.id.tv_order_detail);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		holder.tvBuyOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SharedPreferences preferences = context.getSharedPreferences(
						"userInformation1", Context.MODE_PRIVATE);
				if (preferences.getBoolean("isLogin", false)
						&& preferences.getBoolean("isPartimeLogin", false)) {
					Intent intent = new Intent(context, MineTask.class);
					context.startActivity(intent);
				} else if (preferences.getBoolean("isLogin", false)
						&& !preferences.getBoolean("isPartimeLogin", false)) {
					DialogAuthenticationHint dialog = new DialogAuthenticationHint(
							context);
					dialog.show();
				} else if (!preferences.getBoolean("isLogin", false)) {
					Intent intent = new Intent(context, WebLoginActivity.class);
					context.startActivity(intent);
				}
			}
		});
		holder.tvOrderDetail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, OrderDetailAty.class);
				context.startActivity(intent);
			}
		});
		return v;
	}

	private class ViewHolder {
		private ImageView iv;
		private TextView tvOrderDetail;
		private TextView tvBuyOrder;
	}
}
