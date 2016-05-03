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
import com.llg.help.GetProgressBar;
import com.llg.help.MyFormat;
import com.llg.privateproject.adapters.ProductDetailMoreParmasAdapter.ViewHolder;
import com.llg.privateproject.entities.MaybeYouLike;

public class ProductDetailMoreHotsAdapter extends BaseAdapter {
	Context context;
	List<MaybeYouLike> list;
	LayoutInflater inflater;

	/** 本店热销适配器 */
	public ProductDetailMoreHotsAdapter(Context context, List<MaybeYouLike> list) {
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
			v = inflater.inflate(R.layout.product_detail_more_hot, parent,
					false);
			holder.iv = (ImageView) v.findViewById(R.id.iv);
			holder.dsc = (TextView) v.findViewById(R.id.dsc);
			holder.price = (TextView) v.findViewById(R.id.price);
			v.setTag(holder);

		} else {
			holder = (ViewHolder) v.getTag();
		}
		// LayoutParams params=new LayoutParams(parent.getWidth()/9-10,
		// LayoutParams.MATCH_PARENT);
		// v.setLayoutParams(params);
		holder.dsc.setText(entity.getContent());
		MyFormat.setBitmap(context, holder.iv, entity.getImg(), 0, 0);
		// GetProgressBar.setBitmap(context, holder.iv,
		// "http://192.168.0.121:7777/upload/images/prod/d1c24d0b-3378-41d9-ae40-891dbc313aed.jpg");
		holder.price.setText(MyFormat.getPriceFormat(entity.getSource()));
		// Log.d("my", "productshop"+entity.title);
		return v;
	}

	class ViewHolder {
		ImageView iv;
		TextView dsc;
		TextView price;

	}
}