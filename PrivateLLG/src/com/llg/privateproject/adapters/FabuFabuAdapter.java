package com.llg.privateproject.adapters;

import com.bjg.lcc.privateproject.R;
import com.llg.help.DataEntity;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.adapters.DateAdapter.ViewHolder;

import android.content.Context;
import android.text.style.ParagraphStyle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class FabuFabuAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;

	public FabuFabuAdapter(Context context) {
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 6;
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
			v = inflater.inflate(R.layout.listitem_fabu_fabu, null);
			holder.iv = (ImageView) v.findViewById(R.id.iv);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		LayoutParams params = (LayoutParams) holder.iv.getLayoutParams();
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		params.width = AppContext.getScreenWidth() / 5;
		params.height = AppContext.getScreenWidth() / 5;
		holder.iv.setLayoutParams(params);
		return v;
	}

	private class ViewHolder {
		private ImageView iv;
	}
}