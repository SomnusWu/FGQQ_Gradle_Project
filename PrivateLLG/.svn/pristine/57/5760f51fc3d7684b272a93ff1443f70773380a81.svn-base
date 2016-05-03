package com.llg.privateproject.adapter;

import java.util.List;

import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.entities.ProdSpecItemBean;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * 商品规格适配器
 * yh
 * 
 * */
public class FormatAdapter extends BaseAdapter{
	private Context context;
	private List<ProdSpecItemBean>list;
	private int index=0;
public FormatAdapter(Context context,
List<ProdSpecItemBean>list){
	this.context=context;
	this.list=list;
}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list==null?0:list.size();
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
		String st=list.get(position).getName();
		H h=new H();
		if(v==null){
			 v=View.inflate(context, R.layout.productdetail_size_item_item, null);
			h.tv=(TextView) v.findViewById(R.id.tv);
			v.setTag(h);
		}else {
		h=(H) v.getTag();	
		}
	h.tv.setText(st);
	if (index == position) {
		v.setBackgroundColor((context.getResources().getColor(
				R.color.orange1)));
		
	} else {
		
		v.setBackgroundColor(context.getResources().getColor(
				R.color.white));

	}
	
		return v;
	}
	/** 设置选中项 */
	public void setSelectedPos(int position) {
		this.index = position;
		notifyDataSetChanged();
	}
private class H{
	TextView tv;
}
}
