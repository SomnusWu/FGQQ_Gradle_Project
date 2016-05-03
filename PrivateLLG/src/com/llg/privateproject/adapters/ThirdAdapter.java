package com.llg.privateproject.adapters;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.utils.CommonUtils;

/**
 * 二级产品分类列表适配器
 * 
 * @author gongyibing
 */
public class ThirdAdapter extends BaseAdapter {
	private Context mContext;
	/** 分类数据 */
	private List<Map<String, Object>> list;
	private LayoutInflater mInflater;
	private WindowManager windowManager;

	// int pics[]={R.drawable.defaultpic,R.drawable.bkg,R.drawable.welcome};
	public ThirdAdapter(Context context, List<Map<String, Object>> list) {
		super();
		this.mContext = context;
		this.list = list;
		this.mInflater = LayoutInflater.from(context);
		windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
	}

	@Override
	public int getCount() {

		return (null == list) ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		return (null == list) ? null : list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == mContext) {
			return null;
		}
		if (null == list || list.size() == 0 || list.size() <= position) {
			return null;
		}
		final ViewHolder viewHolder;
		Map<String, Object> map = list.get(position);
		LayoutParams params = null;
		if (null == convertView) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.home_myg_item, parent,
					false);
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.home_img_id);
			params = (LayoutParams) viewHolder.imageView.getLayoutParams();
			int width = (AppContext.getScreenWidth() * 2 / 3) / 3;
			params.width = width;
			params.height = width;
			viewHolder.imageView.setLayoutParams(params);
			viewHolder.textView = (TextView) convertView
					.findViewById(R.id.Home_img_description);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// LayoutParams lp = viewHolder.imageView.getLayoutParams();
		// int width=windowManager.getDefaultDisplay().getWidth();
		// lp.width=width;
		// lp.height=width;
		// viewHolder.imageView.setLayoutParams(lp);
		// viewHolder.imageView.setScaleType(ScaleType.CENTER_CROP);
		viewHolder.textView.setText("");
		viewHolder.textView.setText(map.get("name").toString().trim());

		String imageURL = AppContext.getHtmlUitls().getImageHttp()
				+ map.get("img").toString().trim();
		MyFormat.setBitmap(mContext, viewHolder.imageView, imageURL,
				(AppContext.getScreenWidth() * 2 / 3) / 3,
				(AppContext.getScreenWidth() * 2 / 3) / 3);
		return convertView;
	}

	class ViewHolder {
		/** 产品图片 */
		ImageView imageView;
		/** 产品名称描述 */
		TextView textView;
	}

}
