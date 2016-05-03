package com.llg.privateproject.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjg.lcc.privateproject.R;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.adapters.FabuAdapter;
import com.llg.privateproject.utils.CommonUtils;
import com.llg.privateproject.utils.StringUtils;
import com.llg.privateproject.view.ImageCycleView;
import com.llg.privateproject.view.MyListView;
import com.llg.privateproject.view.ImageCycleView.ImageCycleViewListener;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

public class FabuFragmentHome extends Fragment {
	Context context;
	private PullToRefreshScrollView pulltoRefreshScrollView;
	private View view;
	private ImageCycleView imageCycleView;
	private List<Map<String, Object>> imglist;
	private WindowManager windowManager;
	private LinearLayout fabuLy;
	private LinearLayout fabuLy1;
	private LinearLayout fabuLy2;
	private LinearLayout fabuLy3;
	private LinearLayout fabuLy4;
	private LinearLayout fabuLyOne;
	private LinearLayout fabuLySecend;
	private ImageView fabuIv1;
	private ImageView fabuIv2;
	private ImageView fabuIv3;
	private ImageView fabuIv4;
	private MyListView fabuListView;
	private boolean hasMeasure1 = false;
	private boolean hasMeasure2 = false;
	private boolean hasMeasure3 = false;
	private boolean hasMeasure4 = false;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		context = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fabu_fragment_home, container, false);
		windowManager = (WindowManager) getActivity().getSystemService(
				Context.WINDOW_SERVICE);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		init();
		FabuAdapter adapter = new FabuAdapter(getActivity());
		fabuListView.setAdapter(adapter);
	}

	private void init() {
		// TODO Auto-generated method stub
		pulltoRefreshScrollView = (PullToRefreshScrollView) view
				.findViewById(R.id.fabu_homelist_id);
		imageCycleView = (ImageCycleView) view
				.findViewById(R.id.imageCycleView);
		fabuLy = (LinearLayout) view.findViewById(R.id.fabu_ly);
		fabuIv1 = (ImageView) view.findViewById(R.id.fabu_iv_1);
		fabuIv2 = (ImageView) view.findViewById(R.id.fabu_iv_2);
		fabuIv3 = (ImageView) view.findViewById(R.id.fabu_iv_3);
		fabuIv4 = (ImageView) view.findViewById(R.id.fabu_iv_4);

		fabuLy1 = (LinearLayout) view.findViewById(R.id.fabu_ly1);
		fabuLy2 = (LinearLayout) view.findViewById(R.id.fabu_ly2);
		fabuLy3 = (LinearLayout) view.findViewById(R.id.fabu_ly3);
		fabuLy4 = (LinearLayout) view.findViewById(R.id.fabu_ly4);

		fabuLyOne = (LinearLayout) view.findViewById(R.id.fabu_ly_one);
		fabuLySecend = (LinearLayout) view.findViewById(R.id.fabu_ly_secend);
		fabuListView = (MyListView) view.findViewById(R.id.fabu_listview);
		Log.i("tag", "params.width");
		ViewTreeObserver vto1 = fabuIv1.getViewTreeObserver();
		vto1.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

			@Override
			public boolean onPreDraw() {
				// TODO Auto-generated method stub
				if (!hasMeasure1) {

					int width = fabuIv1.getMeasuredWidth();
					fabuIv1.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
							width, width * 3 / 5));
					hasMeasure1 = true;
				}
				return true;
			}
		});
		ViewTreeObserver vto2 = fabuIv1.getViewTreeObserver();
		vto2.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

			@Override
			public boolean onPreDraw() {
				// TODO Auto-generated method stub
				if (!hasMeasure2) {

					int width = fabuIv2.getMeasuredWidth();
					fabuIv2.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
							width, width * 3 / 5));
					hasMeasure2 = true;
				}
				return true;
			}
		});
		ViewTreeObserver vto3 = fabuIv1.getViewTreeObserver();
		vto3.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

			@Override
			public boolean onPreDraw() {
				// TODO Auto-generated method stub
				if (!hasMeasure3) {

					int width = fabuIv3.getMeasuredWidth();
					fabuIv3.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
							width, width * 3 / 5));
					hasMeasure3 = true;
				}
				return true;
			}
		});
		ViewTreeObserver vto4 = fabuIv1.getViewTreeObserver();
		vto4.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

			@Override
			public boolean onPreDraw() {
				// TODO Auto-generated method stub
				if (!hasMeasure4) {
					int width = fabuIv4.getMeasuredWidth();
					fabuIv4.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
							width, width * 3 / 5));
					hasMeasure4 = true;
				}
				return true;
			}
		});
		fabuLy.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
				AppContext.getScreenWidth(),
				AppContext.getScreenWidth() * 7 / 16));
		imglist = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String, Object> map3 = new HashMap<String, Object>();
		map.put("img",
				"http://img0.imgtn.bdimg.com/it/u=1401299621,2372602159&fm=21&gp=0.jpg");
		map1.put("img",
				"http://img9.mypsd.com.cn/20130507/Mypsd_13918_201305071452420005B.jpg");
		map2.put("img",
				"http://img4.imgtn.bdimg.com/it/u=83712730,2297424990&fm=21&gp=0.jpg");
		map3.put("img",
				"http://img.taopic.com/uploads/allimg/121208/267869-12120Q9342041.jpg");
		imglist.add(map1);
		imglist.add(map);
		imglist.add(map2);
		imglist.add(map3);
		imageCycleView.setImageResources(imglist, imageCycleViewListener);
	}

	/** 广告轮播回调事件 */
	private ImageCycleViewListener imageCycleViewListener = new ImageCycleViewListener() {
		@SuppressWarnings("deprecation")
		public void displayImage(String imageURL, ImageView imageView) {
			LayoutParams lp = imageView.getLayoutParams();
			int width = windowManager.getDefaultDisplay().getWidth();
			lp.width = width;
			lp.height = width * 28 / 64;
			Log.i("tag", width + "--width--");
			Log.i("tag", lp.height + "--height--");
			imageView.setLayoutParams(lp);
			imageView.setScaleType(ScaleType.FIT_XY);
			/** 加载网络图片 */
			if (StringUtils.isEmpty(imageURL)) {
				// imageView.setImageResource(R.drawable.default_products_);
			} else {

				new BitmapUtils(getActivity(), CommonUtils.createSDCardDir())
						.configDefaultLoadingImage(
								R.drawable.bjg_tupjiazai_zhengzaijiazai)
						.configDefaultLoadFailedImage(
								R.drawable.bjg_tupjiazai_zhengzaijiazai)
						.display(imageView, imageURL);
				Log.i("tag", "----------------------------");
			}
		}

		public void onImageClick(List<Map<String, Object>> list, int position,
				View imageView) {
			/** 广告id */
			// String id = list.get(position).get("id").toString().trim();

			// Toast.makeText(mActivity,
			// "you clicked button Advertisement! " + id,
			// Toast.LENGTH_SHORT).show();
		}
	};
}
