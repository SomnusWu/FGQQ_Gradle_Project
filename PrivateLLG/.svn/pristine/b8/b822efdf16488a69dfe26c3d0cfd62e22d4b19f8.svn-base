package com.llg.privateproject.adapters;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.adapters.MaybeYouLikerAdapter.ViewHolder;
import com.llg.privateproject.entities.MaybeYouLike;

public class ProductDetailMoreParmasAdapter extends BaseAdapter {

	Context context;
	List<MaybeYouLike> list;
	LayoutInflater inflater;

	/** 本店热销适配器 */
	public ProductDetailMoreParmasAdapter(Context context,
			List<MaybeYouLike> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
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
	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		MaybeYouLike entity = list.get(position);
		if (v == null) {
			holder = new ViewHolder();
			v = inflater.inflate(R.layout.product_detail_more_params_item,
					parent, false);
			// holder.iv=(ImageView) v.findViewById(R.id.iv);
			holder.name = (TextView) v.findViewById(R.id.name);
			holder.dsc = (TextView) v.findViewById(R.id.dsc);
			v.setTag(holder);

		} else {
			holder = (ViewHolder) v.getTag();
		}
		// LayoutParams params=new LayoutParams(parent.getWidth()/9-10,
		// LayoutParams.MATCH_PARENT);
		// v.setLayoutParams(params);

		holder.name.setText(entity.getName());
		Log.d("my", "dsc" + entity.getDsc());
		holder.dsc.setText(entity.getDsc());
		// Log.d("my", "productshop"+entity.title);
		return v;
	}

	class ViewHolder {

		TextView name;
		TextView dsc;

	}
}