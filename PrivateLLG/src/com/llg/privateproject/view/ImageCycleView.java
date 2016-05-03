package com.llg.privateproject.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.llg.privateproject.AppContext;
import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.utils.CommonUtils;

/**
 * 广告图片自动轮播控件</br>
 * 
 * @author minking
 */
public class ImageCycleView extends LinearLayout {
	/** 上下文 */
	private Context mContext;
	/** 图片轮播视图 */
	private ViewPager mAdvPager = null;
	/** 滚动图片视图适配器 */
	private ImageCycleAdapter mAdvAdapter;
	/** 图片轮播指示器控件 */
	private LinearLayout linearLayout = null;
	/** 图片轮播指示器-个图 */
	private ImageView mImageView = null;
	/** 滚动图片指示器-视图列表 */
	private ImageView[] mImageViews = null;
	/** 图片滚动当前图片下标 */
	private int mImageIndex = 0;
	/** 手机密度 */
	private float mScale;
	/** 图片视图缓存列表 */
	private ArrayList<ImageView> mImageViewCacheList = null;

	/**
	 * @param context
	 */
	public ImageCycleView(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public ImageCycleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		mScale = context.getResources().getDisplayMetrics().density;
		LayoutInflater.from(context).inflate(R.layout.layout_slideshow, this);
		mAdvPager = (ViewPager) findViewById(R.id.viewPager);
		mAdvPager.setOnPageChangeListener(new GuidePageChangeListener());
		mAdvPager.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_UP:
					// 开始图片滚动
					startImageTimerTask();
					break;
				default:
					// 停止图片滚动
					stopImageTimerTask();
					break;
				}
				return false;
			}
		});
		// 滚动图片右下指示器视图
		linearLayout = (LinearLayout) findViewById(R.id.dotLayout);
	}

	/**
	 * 装填图片数据
	 * 
	 * @param imageUrlList
	 * @param imageCycleViewListener
	 */
	public void setImageResources(List<Map<String, Object>> list,
			ImageCycleViewListener imageCycleViewListener) {
		// 清除所有子视图
		linearLayout.removeAllViews();
		// 图片广告数量
		final int imageCount = list.size();

		mImageViews = new ImageView[imageCount];
		for (int i = 0; i < imageCount; i++) {
			mImageView = new ImageView(mContext);
			int imageParams = (int) (mScale * 20 + 0.5f);// XP与DP转换，适应不同分辨率
			int imagePadding = (int) (mScale * 5 + 0.5f);
			mImageView.setLayoutParams(new LayoutParams(imageParams,
					imageParams));
			mImageView.setPadding(imagePadding, imagePadding, imagePadding,
					imagePadding);
			mImageViews[i] = mImageView;
			/** 滚动圆点间距 */
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.leftMargin = 4;
			params.rightMargin = 4;
			if (i == 0) {
				CommonUtils.readBitMap(mContext, mImageViews[i],
						R.drawable.sy_banner_hd);
			} else {
				CommonUtils.readBitMap(mContext, mImageViews[i],
						R.drawable.sy_banner_bd);
			}
			linearLayout.addView(mImageViews[i], params);
		}
		mAdvAdapter = new ImageCycleAdapter(mContext, list,
				imageCycleViewListener);
		mAdvPager.setAdapter(mAdvAdapter);
	}

	/**
	 * 开始轮播(手动控制自动轮播与否，便于资源控制)
	 */
	public void startImageCycle() {
		startImageTimerTask();
	}

	/**
	 * 暂停轮播——用于节省资源
	 */
	public void pushImageCycle() {
		stopImageTimerTask();
	}

	/**
	 * 开始图片滚动任务
	 */
	private void startImageTimerTask() {
		stopImageTimerTask();
		// 图片每3秒滚动一次
		mHandler.postDelayed(mImageTimerTask, 3000);
	}

	/**
	 * 停止图片滚动任务
	 */
	private void stopImageTimerTask() {
		mHandler.removeCallbacks(mImageTimerTask);
		if (mImageViewCacheList != null && mImageViewCacheList.isEmpty()) {
			for (ImageView imageView : mImageViewCacheList) {
				releaseImageViewResouce(imageView);
				destoryBitmaps();
			}
		}
	}

	private Handler mHandler = new Handler();

	/**
	 * 图片自动轮播Task
	 */
	private Runnable mImageTimerTask = new Runnable() {

		@Override
		public void run() {
			if (mImageViews != null) {
				// 下标等于图片列表长度说明已滚动到最后一张图片,重置下标
				if ((++mImageIndex) == mImageViews.length) {
					mImageIndex = 0;
				}
				mAdvPager.setCurrentItem(mImageIndex);
			}
		}
	};

	/**
	 * 轮播图片状态监听器
	 * 
	 * @author minking
	 */
	private final class GuidePageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int state) {
			if (state == ViewPager.SCROLL_STATE_IDLE) {
				startImageTimerTask(); // 开始下次计时
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int index) {
			// 设置当前显示的图片下标
			mImageIndex = index;
			// 设置图片滚动指示器背景
			CommonUtils.readBitMap(mContext, mImageViews[index],
					R.drawable.sy_banner_hd);
			for (int i = 0; i < mImageViews.length; i++) {
				if (index != i) {
					CommonUtils.readBitMap(mContext, mImageViews[i],
							R.drawable.sy_banner_bd);
				}
			}

		}

	}

	private class ImageCycleAdapter extends PagerAdapter {
		/** 图片资源列表 */
		private List<Map<String, Object>> mAdList = null;
		/** 广告图片点击监听器 */
		private ImageCycleViewListener mImageCycleViewListener = null;
		private Context mContext = null;

		public ImageCycleAdapter(Context context,
				List<Map<String, Object>> adList,
				ImageCycleViewListener imageCycleViewListener) {
			this.mContext = context;
			this.mAdList = adList;
			this.mImageCycleViewListener = imageCycleViewListener;
			mImageViewCacheList = new ArrayList<ImageView>();
		}

		@Override
		public int getCount() {
			return mAdList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {

			// String imageUrl =
			// AppContext.getHtmlUitls().getImageHttp()+mAdList.get(position).get("img").toString().trim();
			String imageUrl = mAdList.get(position).get("img").toString()
					.trim();
			Log.d("my", "&&&&&imageUrl:" + imageUrl);
			ImageView imageView = null;
			if (mImageViewCacheList.isEmpty()) {
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			} else {
				imageView = mImageViewCacheList.remove(0);
			}
			// 设置图片点击监听
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mImageCycleViewListener.onImageClick(mAdList, position, v);
				}
			});
			imageView.setTag(imageUrl);
			container.addView(imageView);
			mImageCycleViewListener.displayImage(imageUrl, imageView);
			// imageView.setBackgroundResource(R.drawable.welcome);//链接后台后去掉
			return imageView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			ImageView view = (ImageView) object;
			container.removeView(view);
			mImageViewCacheList.add(view);
		}

	}

	/**
	 * 轮播控件的监听事件
	 * 
	 * @author minking
	 */
	public interface ImageCycleViewListener {
		/**
		 * 加载图片资源
		 * 
		 * @param imageURL
		 * @param imageView
		 */
		void displayImage(String imageURL, ImageView imageView);

		/**
		 * 单击图片事件
		 * 
		 * @param position
		 * @param imageView
		 */
		void onImageClick(List<Map<String, Object>> list, int position,
						  View imageView);
	}

	/**
	 * 销毁ImageView资源，回收内存
	 */
	private void destoryBitmaps() {
		for (int i = 0; i < mImageViews.length; i++) {
			BitmapDrawable drawable = (BitmapDrawable) mImageViews[i]
					.getDrawable();
			Bitmap bmp = drawable.getBitmap();
			if (null != bmp && !bmp.isRecycled()) {
				bmp.recycle();
				bmp = null;
			}
		}
		mImageViews.clone();
	}

	public void releaseImageViewResouce(ImageView imageView) {
		if (imageView == null)
			return;
		Drawable drawable = imageView.getDrawable();
		if (drawable != null && drawable instanceof BitmapDrawable) {
			BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
			Bitmap bitmap = bitmapDrawable.getBitmap();
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
			}
		}
	}
}
