package com.llg.privateproject.adapter;

import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.bjg.lcc.privateproject.R;
import com.llg.help.MyFormat;

/**
 * 首页功能数据适配器
 * 
 * @author gongyibing
 */
public class HomeFenleiAdapter extends BaseAdapter {

	private Context mContext;
	private List<Map<String, Object>> list;
	private LayoutInflater mInflater;
	private WindowManager windowManager;

	public HomeFenleiAdapter(Context context, List<Map<String, Object>> list) {
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

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
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
		if (null == convertView) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.home_myg_item, parent,
					false);
			
			convertView.setLayoutParams(new AbsListView.LayoutParams(
					parent.getWidth() / 4, LayoutParams.WRAP_CONTENT));
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.home_img_id);
			viewHolder.textView = (TextView) convertView
					.findViewById(R.id.Home_img_description);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
			convertView.setLayoutParams(new AbsListView.LayoutParams(
					parent.getWidth() / 4, LayoutParams.WRAP_CONTENT));
		}

		// "funcType": "1",
		// "id": "1",
		// "orderNo": null,
		// "status": null,
		// "name": "test1",
		// "versionId": null,
		// "picId": "plug-in/imgUpload/mainFunc/gongneng_03.png",
		// "href": "1"

		android.widget.LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) viewHolder.imageView.getLayoutParams();
		 int width=windowManager.getDefaultDisplay().getWidth();
		 lp.width=width/8;
		 lp.height=width/7;
//		 Log.d("my", "width"+lp.width);
//		 Log.d("my", "height"+lp.height);
		 viewHolder.imageView.setLayoutParams(lp);
		 viewHolder.imageView.setScaleType(ScaleType.CENTER_CROP);
		// String imageURL = map.get("img").toString().trim();
		MyFormat.setBitmap(mContext, viewHolder.imageView, map.get("picId").toString(), 0, 0);
//		new BitmapUtils(mContext, CommonUtils.createSDCardDir())
//				.configDefaultLoadFailedImage(R.drawable.quanbu).display(
//						viewHolder.imageView, map.get("picId").toString());
		viewHolder.textView.setTextSize(MyFormat.px2dip(mContext, 60));
		viewHolder.textView.setText(map.get("name").toString().trim());

		// viewHolder.imageView.setBackground((Drawable) map.get("pic"));
		return convertView;
	}

	class ViewHolder {
		ImageView imageView;
		TextView textView;
	}

}
