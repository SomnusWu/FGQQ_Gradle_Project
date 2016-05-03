package com.llg.privateproject.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.util.StringUtils;
import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.adapters.TabListAdapter;
import com.llg.privateproject.entities.MenuItem;
import com.llg.privateproject.entities.Region;

public class TabViewTwo extends LinearLayout {

	private ListView listView1;
	private ListView listView2;
	private TabListAdapter listViewAdapter1;
	private TabListAdapter listViewAdapter2;
	private boolean secendisShow = false;
	private boolean firstrightShow = false;

	private List<com.llg.privateproject.entities.MenuItem> groups;
	private List<ArrayList<com.llg.privateproject.entities.MenuItem>> childrens;

	/** 当前显示的分�?2 */
	private List<com.llg.privateproject.entities.MenuItem> childrenItem = new ArrayList<com.llg.privateproject.entities.MenuItem>();

	private OnItemSelectListener mOnItemSelectListener;
	private int selectGroupResId, selectorGroupResId;
	private int selectChildrenResId, selectorChildrenResId;

	private int defaultGroupId = -1;
	private int defaultChildrenId = -1;

	private int defaultGroupPosition = 0;
	private int defaultChildrenPosition = 0;
	private int mselectPosition;
	private String showString;

	/**
	 * 
	 * 构造
	 * 
	 * @param context
	 *            上下文
	 * @param groups
	 *            第一个listView要显示的数据
	 * @param childrens
	 *            第二个listView要显示的数据
	 * @param defaultGroupId
	 *            //第一个listView默认选中条目ID 此ID不是Position 而是实体类中getID
	 * @param defaultChildrenId
	 *            第二个listView默认选中条目ID 此ID不是Position 而是实体类中getID
	 * @param selectGroupResId
	 *            第一个listView选中的背景色
	 * @param selectorGroupResId
	 *            第一个listView未选中的背景色
	 * @param selectChildrenResId
	 *            第二个listView选中的背景色
	 * @param selectorChildrenResId
	 *            第二个listView未选中的背景色
	 */
	public TabViewTwo(
			Context context,
			List<com.llg.privateproject.entities.MenuItem> groups,
			List<ArrayList<com.llg.privateproject.entities.MenuItem>> childrens,
			int defaultGroupId, int defaultChildrenId, int selectGroupResId,
			int selectorGroupResId, int selectChildrenResId,
			int selectorChildrenResId) {
		super(context);
		this.groups = groups;
		this.childrens = childrens;
		this.defaultGroupId = defaultGroupId;
		this.defaultChildrenId = defaultChildrenId;
		this.selectGroupResId = selectGroupResId;
		this.selectorGroupResId = selectorGroupResId;
		this.selectChildrenResId = selectChildrenResId;
		this.selectorChildrenResId = selectorChildrenResId;
		// init(context);
	}

	public TabViewTwo(Context context, AttributeSet attrs) {
		super(context, attrs);
		// init(context);
	}

	public void updateShowText(int groupId, int childId) {
		if (groupId == -1 || childId == -1) {
			return;
		}
		for (int i = 0; i < groups.size(); i++) {
			if (groups.get(i).getId() == groupId) {
				listViewAdapter1.setSelectedPosition(i);
				childrenItem.clear();
				if (i < childrens.size()) {
					childrenItem.addAll(childrens.get(i));
				}
				defaultGroupPosition = i;
				break;
			}
		}
		for (int j = 0; j < childrenItem.size(); j++) {
			if (childrenItem.get(j).getId() == childId) {
				listViewAdapter2.setSelectedPosition(j);
				defaultChildrenPosition = j;
				break;
			}
		}

		if (defaultChildrenPosition < childrenItem.size()) {
			showString = childrenItem.get(defaultChildrenPosition).getText();
		}

		setDefaultSelect();
	}

	private void init(Context context) {
	 
		this.setOrientation(LinearLayout.HORIZONTAL);
		listView1 = new ListView(context);
		listView1.setCacheColorHint(Color.parseColor("#00000000"));
		listView1.setDivider(new ColorDrawable(Color.parseColor("#D3D3D3")));
		listView1.setDividerHeight(1);
		listView1.setHorizontalScrollBarEnabled(false);
		listView1.setVerticalScrollBarEnabled(false);
		listView1.setBackgroundResource(R.color.white);
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		int width = (int) (manager.getDefaultDisplay().getHeight() * 0.6);
		this.addView(listView1, new LayoutParams(0, width, 1));
		/*
		 * View line = new View(context);
		 * line.setBackgroundColor(Color.parseColor("#ebebeb"));
		 * this.addView(line,new LayoutParams(1,LayoutParams.MATCH_PARENT));
		 */

		listView2 = new ListView(context);
		listView2.setCacheColorHint(Color.parseColor("#00000000"));
		listView2.setDivider(null);
		// listView2.setDividerHeight(1);
		listView2.setHorizontalScrollBarEnabled(false);
		listView2.setVerticalScrollBarEnabled(false);
		listView2.setBackgroundResource(R.color.choose_eara_item_press_color);
		this.addView(listView2, new LayoutParams(0, width, 1));

		listViewAdapter1 = new TabListAdapter(context, groups,
				selectGroupResId, selectorGroupResId);

		if (defaultGroupId != -1) {
			for (int i = 0; i < groups.size(); i++) {
				if (groups.get(i).getId() == defaultGroupId) {
					// listViewAdapter1.setSelectedPositionNoNotify(i);
					break;
				}
			}
		}
		listViewAdapter2 = new TabListAdapter(context, childrenItem,
				selectChildrenResId, selectorChildrenResId);
		listView2.setAdapter(listViewAdapter2);
		
		// listViewAdapter1.setSelectedPositionNoNotify(defaultGroupPosition);
		listView1.setAdapter(listViewAdapter1);
		listViewAdapter1
				.setOnItemClickListener(new TabListAdapter.OnItemClickListener() {
					@Override
					public void onItemClick(View view, int position) {
						
						if (position < groups.size()) {
							childrenItem.clear();							
							if (childrens.size() > position) {
								childrenItem.addAll(childrens.get(position));
							}
							mOnItemSelectListener.itemLeftSelected(position);
							listViewAdapter1.notifyDataSetChanged();
							listViewAdapter2.notifyDataSetChanged();
						}
					}
				});
		
//		listView1.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				// TODO Auto-generated method stub
//				mOnItemSelectListener.itemLeftSelected(position);
//			}
//		});

		if (defaultChildrenId != -1) {
			if (defaultGroupId == -1) {

			} else {
				for (int i = 0; i < childrens.get(defaultGroupPosition).size(); i++) {
					if (childrens.get(i).get(defaultGroupPosition).getId() == defaultChildrenId) {
						defaultChildrenPosition = i;
						// listViewAdapter2
						// .setSelectedPositionNoNotify(defaultChildrenPosition);
						break;
					}
				}
			}
		}

		if (defaultChildrenPosition < childrens.size()) {
			if (!TextUtils.isEmpty(AppContext.myAddress)
					&& AppContext.selectCityCode.equals("00")
					|| AppContext.selectBaiduCode == AppContext.myCityCode) {
				childrenItem.addAll(childrens.get(getDingweiPosition()));
			} else {
				childrenItem.addAll(childrens.get(defaultChildrenPosition));
			}
		}
		
		listViewAdapter2
				.setOnItemClickListener(new TabListAdapter.OnItemClickListener() {

					@Override
					public void onItemClick(View view, final int position) {
						showString = childrenItem.get(position).getText();
						if (mOnItemSelectListener != null) {
							mOnItemSelectListener.itemSelected(position);
						}

					}
				});

		if (defaultChildrenPosition < childrenItem.size()) {
			showString = childrenItem.get(defaultChildrenPosition).getText();
		}
		setDefaultSelect();
	}

	public void setDefaultSelect() {
		listView2.setSelection(0);
		if (firstrightShow) {
			listView2.setSelection(defaultChildrenPosition);
		}
	}

	public String getShowText() {
		return showString;
	}

	public void setOnItemSelectListener(
			OnItemSelectListener onItemSelectListener) {
		mOnItemSelectListener = onItemSelectListener;
	}

	public interface OnItemSelectListener {
		void itemSelected(int position);

		void itemLeftSelected(int position);
	}

	public void notifyDataSetChangedGroup() {
		listViewAdapter1.notifyDataSetChanged();
	}

	public void notifyDataSetChangedChildren() {
		listViewAdapter2.notifyDataSetChanged();
	}

	public void setList(List<ArrayList<MenuItem>> childrens,
			List<MenuItem> groups, Context context, int mselectPosition) {
		this.childrens = childrens;
		this.groups = groups;
		this.mselectPosition = mselectPosition;
		if (this.childrens.get(0).size() > 0) {
			this.childrens.get(0).get(0).getText();
			Log.i("tag", this.childrens.get(0).get(0).getText()
					+ "传递过来的第1个位置内容");
		}

		if (mselectPosition == 1) {
			if (!firstrightShow) {
				init(context);
				firstrightShow = true;
			}
		} else if (mselectPosition == 0) {
			if (!secendisShow) {
				this.removeAllViews();
				init(context);
				secendisShow = true;
			}
		}
	}

	private int getDingweiPosition() {
		for (int i = 0; i < groups.size(); i++) {
			String text = groups.get(i).getText();
			if (!TextUtils.isEmpty(AppContext.myAddress)
					&& AppContext.region.contains(text)) {
				return i;
			}
		}
		return -3;
	}
}
