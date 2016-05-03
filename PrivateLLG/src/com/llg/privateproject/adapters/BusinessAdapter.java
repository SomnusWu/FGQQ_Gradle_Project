package com.llg.privateproject.adapters;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.util.StringUtils;
import com.bjg.lcc.privateproject.R;
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.entities.NowBuyBusiness;

public class BusinessAdapter extends BaseAdapter {
	private List<NowBuyBusiness> list;
	private Context context;
	private LayoutInflater inflater;

	public BusinessAdapter(Context context, List<NowBuyBusiness> list) {
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
	public NowBuyBusiness getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (v == null) {
			holder = new ViewHolder();
			v = inflater.inflate(R.layout.listitem_business, null);
			holder.ivHead = (ImageView) v.findViewById(R.id.iv);
			holder.tvName = (TextView) v.findViewById(R.id.tv_name);
			holder.tvGrade = (TextView) v.findViewById(R.id.tv_grade);
			holder.tvPeople = (TextView) v.findViewById(R.id.tv_people);
			holder.tvAddress = (TextView) v.findViewById(R.id.tv_address);
			holder.tvCobi = (TextView) v.findViewById(R.id.tv_kubi);
			holder.ratingBar = (RatingBar) v.findViewById(R.id.ratingbar);
			holder.tvDistance = (TextView) v.findViewById(R.id.tv_distance);

			holder.relayLayout = (RelativeLayout) v.findViewById(R.id.ry);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		int margin = dip2px(context, 10);
		int width = (AppContext.getScreenWidth()) / 3 - (2 * margin);
		android.widget.RelativeLayout.LayoutParams layoutParams = new android.widget.RelativeLayout.LayoutParams(
				width, width);
		layoutParams.rightMargin = margin;
		holder.ivHead.setLayoutParams(layoutParams);
		MyFormat.setBitmap(context, holder.ivHead, list.get(position).getIMG(),
				0, 0);
		holder.relayLayout.getLayoutParams().height = width;
		// new BitmapUtils(context, CommonUtils.createSDCardDir())
		// .configDefaultLoadFailedImage(R.drawable.defaultpic).display(
		// holder.ivHead, list.get(position).getIMG());
		holder.tvAddress.setText(list.get(position).getADDRESS());// 商家地址
		holder.tvName.setText(list.get(position).getNAME());// 商家名字
		int grade = 0;
		if (list.get(position).getAVG_SCORE() != null) {
			grade = (int) (list.get(position).getAVG_SCORE() / (0.5)) / 2;
			holder.ratingBar.setRating(grade);
			holder.tvGrade.setText(grade + "分");// 评分
		} else {
			holder.ratingBar.setRating(0);
			holder.tvGrade.setText(0 + "分");// 评分
		}
		if (list.get(position).getCOUNT_SCORE() != null) {
			holder.tvPeople.setText("(" + list.get(position).getCOUNT_SCORE()+ ")人");// 评分人数
		} else {
			holder.tvPeople.setVisibility(View.GONE);
		}
		DecimalFormat decimalFormat = new DecimalFormat("#0.00");
		String coPercent = list.get(position).getSPOT_CO_PERCENT()+"";
//		if (coPercent != null && coPercent.length() > 0) {
		if(!StringUtils.isEmpty(coPercent) && !coPercent.equals("null")){
			holder.tvCobi.setVisibility(View.VISIBLE);
			holder.tvCobi.setText("最多可用酷币抵" + coPercent + "分点");// 最多可抵总价的百分比
		} else {
			holder.tvCobi.setVisibility(View.GONE);
		}
		if (list.get(position).getDISTANCE() != null) {
			int _distance = list.get(position).getDISTANCE();
			if (_distance > 1000) {
				holder.tvDistance.setText(_distance / 1000 + "km");
			} else {
				holder.tvDistance.setText(_distance + "m");
			}
		} else {
			holder.tvDistance.setVisibility(View.GONE);
		}
		return v;
	}

	public int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	class ViewHolder {
		private ImageView ivHead;
		private TextView tvName;
		private TextView tvGrade;
		private TextView tvPeople;
		private TextView tvAddress;
		private TextView tvCobi;
		private TextView tvDistance;
		private RatingBar ratingBar;
		private RelativeLayout relayLayout;
	}

	public void setList(List<NowBuyBusiness> list) {
		this.list = list;
		notifyDataSetChanged();
	}
}
