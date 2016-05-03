package com.llg.privateproject.view;

import java.util.List;
import java.util.Map;

import com.bjg.lcc.privateproject.R;
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;

import android.R.integer;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
/**店铺相册对话框
 * yh
 * 2015.11.20
 * */
public class ShopFrameDialog extends Dialog implements OnClickListener,OnItemSelectedListener{
List<String> list;
Gallery gallery;
Context context;
ImageView iv;
private int  index=0;
	public ShopFrameDialog(Context context,List<String> list) {
		super(context, R.style.agreemdialog);
		this.list=list;
		this.context=context;
	}
@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopphotos);
		gallery=(Gallery) findViewById(R.id.ll);
		iv=(ImageView) findViewById(R.id.iv);
		gallery.setAdapter(new ShopAdapter());

		gallery.setOnItemSelectedListener(this);
		iv.setOnClickListener(this);
		
	}
private class  ShopAdapter extends BaseAdapter{
private  ShopAdapter() {
	// TODO Auto-generated constructor stub
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;

		if (convertView == null) {
			viewHolder = new ViewHolder();
			// convertView=View.inflate(context, R.layout.gallery_item, null);
			convertView = View.inflate(context, R.layout.gallery_item, null);
			viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv);
//			viewHolder.iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// viewHolder.iv.setBackgroundResource(count[position]);

		MyFormat.setBitmap(context, viewHolder.iv, list.get(position).toString(),0,0);
		return convertView;
	}

	private class ViewHolder {
		ImageView iv;
	}
}



@Override
public void onClick(View v) {
	// TODO Auto-generated method stub

	gallery.setSelection(index);
}
@Override
public void onItemSelected(AdapterView<?> parent, View view, int position,
		long id) {
	// TODO Auto-generated method stub
if((list.size()-1)>position){
		
		index=(position+1);
	}else{
		index=0;
	}

}
@Override
public void onNothingSelected(AdapterView<?> parent) {
	// TODO Auto-generated method stub
	
}}
