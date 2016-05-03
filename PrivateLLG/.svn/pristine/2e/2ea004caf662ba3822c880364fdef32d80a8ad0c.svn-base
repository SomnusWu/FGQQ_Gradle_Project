package com.llg.privateproject.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bjg.lcc.privateproject.R;
import com.llg.help.MyFormat;
import com.llg.privateproject.entities.BalanceDetailModel.ObjBean.ResultBean;

/**
 * 收支明细适配器 yh
 * */
public class BalanceAdapter extends BaseAdapter {
	private List<ResultBean> list;
	private Context context;
	private List<com.llg.privateproject.entities.IncomeDetailModel.AttributesBean.PagesBean.ResultBean> mlist;
	private boolean flag;

	public BalanceAdapter(Context context, List<ResultBean> list) {
		this.list = list;
		this.context = context;
	}

	/**
	 * 收益明细
	 */
	public BalanceAdapter(
			Context context,
			List<com.llg.privateproject.entities.IncomeDetailModel.AttributesBean.PagesBean.ResultBean> list,
			boolean flag) {
		// TODO Auto-generated constructor stub
		this.mlist = list;
		this.context = context;
		this.flag = flag;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (flag)
			return mlist.size();
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (flag) {
			return mlist.get(position);
		}
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stu

		Holder holder;
		if (v == null) {
			holder = new Holder();
			v = View.inflate(context, R.layout.balance_list_item, null);
			holder.date = (TextView) v.findViewById(R.id.date);
			holder.dsc = (TextView) v.findViewById(R.id.dsc);
			holder.money = (TextView) v.findViewById(R.id.money);
			holder.type = (TextView) v.findViewById(R.id.type);

			v.setTag(holder);
		} else {
			holder = (Holder) v.getTag();
		}
		if (flag) {
			
			com.llg.privateproject.entities.IncomeDetailModel.AttributesBean.PagesBean.ResultBean _bean = mlist
					.get(position);
			holder.date.setText(_bean.getCreateDate());
			holder.dsc.setText(_bean.getDescription());
			if (Float.parseFloat(_bean.getMoney()) >= 0) {
				holder.money.setText("+" + _bean.getMoney());
			} else {
				holder.money.setTextColor(context.getResources().getColor(
						R.color.orange2));
				holder.money.setText(_bean.getMoney());

			}
			holder.type.setText(_bean.getType_str());
		} else {
			ResultBean bean = list.get(position);
			String time = MyFormat.getTimeFormat3(bean.getWater_date());
			holder.date.setText(time);
			holder.dsc.setText(bean.getDescription());
			if (Float.parseFloat(bean.getMoney()) >= 0) {
				holder.money.setText("+" + bean.getMoney());
			} else {
				holder.money.setTextColor(context.getResources().getColor(
						R.color.orange2));
				holder.money.setText(bean.getMoney());

			}
			holder.type.setText(bean.getType_str());
		}
		return v;
	}

	private class Holder {
		TextView date;
		TextView dsc;
		TextView money;
		TextView type;

	}
}
