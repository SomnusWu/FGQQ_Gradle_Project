package com.llg.privateproject.adapters;

import java.text.DecimalFormat;
import java.util.List;

import com.bjg.lcc.privateproject.R;
import com.llg.help.MyFormat;
import com.llg.help.Util;
import com.llg.privateproject.actvity.LoginActivity;
import com.llg.privateproject.actvity.MineTask;
import com.llg.privateproject.actvity.OrderDetailAty;
import com.llg.privateproject.entities.AdvertiseComment;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.view.DialogAuthenticationHint;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdvertisementCommentAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private Context context;
	private List<AdvertiseComment> list;

	public AdvertisementCommentAdapter(Context context,
			List<AdvertiseComment> list) {
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return (list == null) ? 0 : list.size();
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
			v = inflater.inflate(R.layout.listitem_advertisement_comment, null);
			holder.ivHead = (ImageView) v.findViewById(R.id.iv_head);
			holder.tvName = (TextView) v.findViewById(R.id.tv_name);
			holder.tvComment = (TextView) v.findViewById(R.id.tv_comment);
			holder.tvMoney = (TextView) v.findViewById(R.id.tv_money);
			holder.tvTime = (TextView) v.findViewById(R.id.tv_time);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		try {
			MyFormat.setBitmap(context, holder.ivHead, list.get(arg0)
					.getPICTURE_URL(), 0, 0);
			holder.tvName.setText(list.get(arg0).getAPPELLATION());
			Double money = list.get(arg0).getMONEY();
			Log.i("tag", money+"-----money-");
			if (money != null) {
				Log.i("tag", money+"-----money-");
				holder.tvMoney.setText(MyFormat.getPriceFormat(money+"") + "元");
			}else {
				holder.tvMoney.setText("");
			}
			holder.tvComment.setText(list.get(arg0).getCOMMON_TEXT());
			Long time = list.get(arg0).getCREATE_DATE();
			if (time != null) {
				String mtime=Util.format(time);
				holder.tvTime.setText(mtime);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return v;
	}

	private class ViewHolder {
		private ImageView ivHead;
		private TextView tvName;
		private TextView tvTime;
		private TextView tvMoney;
		private TextView tvComment;
	}

	public void setList(List<AdvertiseComment> list) {
		this.list = list;
		notifyDataSetChanged();
	}

}
