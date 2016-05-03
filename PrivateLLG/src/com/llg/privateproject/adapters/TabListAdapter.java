package com.llg.privateproject.adapters;

import java.util.List;

import com.bjg.lcc.privateproject.R;
import com.llg.help.SetListHeigt;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.entities.MenuItem;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TabListAdapter extends ArrayAdapter<MenuItem> {

	private Context mContext;
	private List<MenuItem> mListData;
	private String[] mArrayData;
	private int selectedPos = -2;
	private int mselectedPos = -2;
	private String selectedText;
	private int normalResId;
	private int selectedResId;
	private OnClickListener onClickListener;
	private OnItemClickListener mOnItemClickListener;
	private boolean isSelected = false;

	public TabListAdapter(Context context, List<MenuItem> listData,
			int selectedResId, int normalResId) {
		super(context, -1, listData);
		mContext = context;
		mListData = listData;
		if (mListData.size() > 0) {
			Log.i("tag", mListData.get(0).getText() + "---------第1个位置的内容----");
		}
		this.normalResId = normalResId;
		this.selectedResId = selectedResId;
		init();
	}

	private void init() {
		onClickListener = new OnClickListener() {

			@Override
			public void onClick(View view) {
				isSelected = true;
				selectedPos = (Integer) view.getTag();
				setSelectedPosition(selectedPos);
				if (mOnItemClickListener != null) {
					mOnItemClickListener.onItemClick(view, selectedPos);
				}
			}
		};
	}

	/**
	 * 设置选中的position,并�?�知列表刷新
	 */
	public void setSelectedPosition(int pos) {
		if (mListData != null && pos < mListData.size()) {
			selectedPos = pos;
			selectedText = mListData.get(pos).getText();
			 
			notifyDataSetChanged();
			Log.i("tag", "---------------执行了setSelectedPosition");
		} else if (mArrayData != null && pos < mArrayData.length) {
			selectedPos = pos;
			selectedText = mArrayData[pos];
			notifyDataSetChanged();
		}

	}

	/**
	 * 设置选中的position,但不通知刷新
	 */
	public void setSelectedPositionNoNotify(int pos) {
		selectedPos = pos;
		if (mListData != null && pos < mListData.size()) {
			selectedText = mListData.get(pos).getText();
		} else if (mArrayData != null && pos < mArrayData.length) {
			selectedText = mArrayData[pos];
		}
	}

	/**
	 * 获取选中的position
	 */
	public int getSelectedPosition() {
		if (mArrayData != null && selectedPos < mArrayData.length) {
			return selectedPos;
		}
		if (mListData != null && selectedPos < mListData.size()) {
			return selectedPos;
		}

		return -1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view;
		if (convertView == null) {
			view = new TextView(mContext);
			Log.i("tag", "-----------执行了getview");
			// view.setDuplicateParentStateEnabled(true);
			view.setTextSize(13);
		} else {
			view = (TextView) convertView;
		}
		Log.i("tag", "-----------执行了getview下面");
		view.setClickable(true);
		view.setFocusable(true);
		view.setTextColor(mContext.getResources().getColor(R.color.black1));
		view.setTag(position);
		view.setBackgroundResource(normalResId);
		String mString = "";
		if (mListData != null) {
			if (position < mListData.size()) {
				mString = mListData.get(position).getText();
			}
		} else if (mArrayData != null) {
			if (position < mArrayData.length) {
				mString = mArrayData[position];
			}
		}
		view.setText(mString);
		Log.i("tag",AppContext.region+"==adapter==AppContext.region");
		Log.i("tag",mListData.get(position).getText()+"==adapter==text");
		if (selectedPos == position) {
			view.setTextColor(mContext.getResources().getColor(R.color.orange1));
			Log.i("tag", "-----------执行了改变文字颜色");
		}
		if (!TextUtils.isEmpty(AppContext.myAddress)
				&& AppContext.region
						.contains(mListData.get(position).getText())
				&& selectedPos == -2) {
			view.setTextColor(mContext.getResources().getColor(R.color.orange1));
			AppContext.position=position;
			Log.i("tag", "-----------执行了改变文字颜色---2");
		}
		Log.i("tag", selectedPos+"======selectedPos=====");
		Log.i("tag", position+"======position=====");
		if (selectedText != null && selectedText.equals(mString)) {
			// 设置选中的背景图�?
			// view.setBackgroundResource(selectedResId);
		} else {
			// 设置未�?�中状�?�背景图�?
			// view.setBackgroundResource(normalResId);
		}
		view.setPadding(20, 25, 20, 25);
		view.setOnClickListener(onClickListener);
		return view;
	}

	public void setOnItemClickListener(OnItemClickListener l) {
		mOnItemClickListener = l;
	}

	/**
	 * 重新定义菜单选项单击接口
	 */
	public interface OnItemClickListener {
		void onItemClick(View view, int position);
	}

	public void setList(List<MenuItem> mListData) {
		this.mListData = mListData;
	}
}