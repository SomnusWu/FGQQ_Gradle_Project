package com.llg.privateproject.adapters;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.llg.help.GetProgressBar;
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.adapter.GalleryAdapter;
import com.llg.privateproject.entities.Commententity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/**
 * 评论列表适配器 yh 2015.6.23
 * */
public class CommentAdapter extends BaseAdapter {
	Context context;
	List<Commententity> list;
	LayoutInflater inflater;

	public CommentAdapter(Context context, List<Commententity> list) {
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
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.d("my", "getViewlist.size()" + list.size());
		ViewHolder holder;
		// View view=null;
		Commententity item = list.get(position);
		if (v == null) {
			holder = new ViewHolder();
			v = inflater.inflate(R.layout.product_detail_comment_gridview_item,
					null);

			holder.commenter_head = (ImageView) v
					.findViewById(R.id.commenter_head);
			holder.commenter_petname = (TextView) v
					.findViewById(R.id.commenter_petname);
			holder.commenter_grade = (ImageView) v
					.findViewById(R.id.commenter_grade);
			holder.content = (TextView) v.findViewById(R.id.content);
			holder.ll = (LinearLayout) v.findViewById(R.id.ll);
			// holder.comment_pics=(Gallery) v.findViewById(R.id.comment_pics);
			holder.time = (TextView) v.findViewById(R.id.time);
			holder.size = (TextView) v.findViewById(R.id.size);
			holder.color = (TextView) v.findViewById(R.id.color);
			holder.score = (TextView) v.findViewById(R.id.score);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		MyFormat.setBitmap(context, holder.commenter_head, item.imgHead, 0, 0);
		holder.commenter_petname.setText(MyFormat
				.replaceString(item.petName, 4));
		// holder.commenter_grade.setBackgroundResource(R.drawable.collect_selected);
		holder.content.setText(item.content);
		// 添加评价图片
		if (item.listimgs != null && item.listimgs.size() > 0) {
			// view=View.inflate(context,
			// R.layout.product_detail_comment_gallery_item, null);
			for (int i = 0; i < item.listimgs.size(); i++) {
				LayoutParams params = new LayoutParams(
						AppContext.getScreenWidth() / 3,
						AppContext.getScreenWidth() / 3);
				params.rightMargin = 5;
				params.bottomMargin = 5;
				params.leftMargin = 5;
				params.topMargin = 5;
				LinearLayout ll_item = new LinearLayout(context);
				ll_item.setOrientation(LinearLayout.VERTICAL);
				ll_item.setPadding(1, 1, 1, 1);

				ll_item.setBackgroundResource(R.drawable.stroke_lightgray);
				ll_item.setLayoutParams(params);
				ImageView iv = new ImageView(context);
				LayoutParams params1 = new LayoutParams(
						AppContext.getScreenWidth() / 3,
						AppContext.getScreenWidth() / 3);

				iv.setLayoutParams(params1);
				iv.setScaleType(ScaleType.FIT_XY);

				// GetProgressBar.setBitmap(context,iv, item.listimgs.get(i));
				ll_item.addView(iv);

				holder.ll.addView(ll_item);
			}
			// holder.comment_pics.setVisibility(View.VISIBLE);
			// if(item.imgs.length>1){
			// holder.comment_pics.setSelection(3);
		}
		// holder.comment_pics.setAdapter(new
		// ProductDetailCommentGalleryAdapter(context, item.imgs));}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		// System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		// holder.time.setText(new
		// SimpleDateFormat().format("yyyy-MM-dd HH:mm:ss").format(item.commentTime));
		holder.time.setText(item.commentTime);
		holder.size.setText(item.size);
		holder.color.setText(item.color);
		// holder.score.setText(item.score);
		return v;
	}

	class ViewHolder {
		ImageView commenter_head;
		TextView commenter_petname;
		ImageView commenter_grade;
		TextView content;
		LinearLayout ll;
		// Gallery comment_pics;
		TextView time;
		TextView size;
		TextView color;
		TextView score;

	}

}
