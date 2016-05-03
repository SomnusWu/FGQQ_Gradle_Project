package com.llg.privateproject.adapters;

import com.llg.privateproject.AppContext;
import com.bjg.lcc.privateproject.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

public class ProductDetailCommentGalleryAdapter extends BaseAdapter {
	Context context;
	int count[];

	public ProductDetailCommentGalleryAdapter(Context context, int count[]) {
		this.context = context;
		this.count = count;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return count == null ? 0 : count.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return count[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (v == null) {
			holder = new ViewHolder();
			v = View.inflate(context,
					R.layout.product_detail_comment_gallery_item, null);

			LayoutParams params = new LayoutParams(
					AppContext.getScreenWidth() / 3,
					AppContext.getScreenWidth() / 3);
			holder.iv = (ImageView) v.findViewById(R.id.iv);

			holder.iv.setLayoutParams(params);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		holder.iv.setBackgroundResource(count[position]);
		return v;
	}

	class ViewHolder {
		ImageView iv;
	}
}
