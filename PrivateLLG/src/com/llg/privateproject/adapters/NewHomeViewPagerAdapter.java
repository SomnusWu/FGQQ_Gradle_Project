package com.llg.privateproject.adapters;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

/**
 * 新首页广告加载适配器 yh 2015.9.15
 * 
 * */
public class NewHomeViewPagerAdapter extends PagerAdapter {
	private Context context;
	private List<Map<String, Object>> list2;

	// private List<View>list;
	/**
	 * 新首页广告加载数据适配器
	 * */
	public NewHomeViewPagerAdapter(Context context,
			List<Map<String, Object>> list) {
		this.context = context;
		this.list2 = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		// return list2 == null ? 0 : list2.size();
		return list2 == null ? 0 : list2.size();
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {// 这个方法用来实例化页卡
		// TODO Auto-generated method stub
		container.addView((View) list2.get(position).get("iv"), 0);// 添加页卡
		return list2.get(position).get("iv");
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		// super.destroyItem(container, position, object);
		if (list2.size() - 1 >= position) {
			container.removeView((View) list2.get(position).get(
					"iv"));// 删除页卡
		}
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

}