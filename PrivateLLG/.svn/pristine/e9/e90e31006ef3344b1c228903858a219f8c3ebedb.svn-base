package com.llg.privateproject.adapters;

import java.util.List;

import com.bjg.lcc.privateproject.R;
import com.llg.help.MyFormat;
import com.llg.privateproject.adapters.FabuAdapter.ViewHolder;
import com.llg.privateproject.entities.DrawHistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WithdrawHistoryAdapter extends BaseAdapter {

	private List<DrawHistory> list;
	private Context context;
	private LayoutInflater inflater;

	public WithdrawHistoryAdapter(Context context, List<DrawHistory> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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
		ViewHolder holder;
		if (v == null) {
			holder = new ViewHolder();
			v = inflater.inflate(R.layout.listitem_withdraw_history, null);
			holder.tvState = (TextView) v.findViewById(R.id.tv_state);
			holder.tvMoney = (TextView) v.findViewById(R.id.tv_money);
			holder.tvTime = (TextView) v.findViewById(R.id.tv_time);
			holder.tvAccount = (TextView) v.findViewById(R.id.tv_account);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		String state = list.get(arg0).getAuditStatus();
		if (state != null) {
			if (state.equals("1")) {
				holder.tvState.setText("审核中");
			} else if (state.equals("0")) {
				holder.tvState.setText("未审核");
			} else if (state.equals("2")) {
				holder.tvState.setText("审核失败");
			} else if (state.equals("3")) {
				holder.tvState.setText("已完成");
			}
		}
		try {
			String time = MyFormat.getTimeFormat2(list.get(arg0).getApplyTime());
			holder.tvTime.setText("申请提现时间: " + time);
		} catch (Exception e) {
			// TODO: handle exception
		}
		holder.tvMoney.setText("+" + list.get(arg0).getApplyMoney());
		if (list.get(arg0).getAccountType().equals("1")) {
			holder.tvAccount.setText(list.get(arg0).getApplyAccount());
		}
		return v;
	}

	class ViewHolder {
		private TextView tvMoney;
		private TextView tvTime;
		private TextView tvAccount;
		private TextView tvState;
	}

	public void setList(List<DrawHistory> list) {
		this.list = list;
		notifyDataSetChanged();
	}
}
