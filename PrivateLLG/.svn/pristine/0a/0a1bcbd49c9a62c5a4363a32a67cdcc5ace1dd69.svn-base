package com.llg.privateproject.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.exception.CustomException;
import com.llg.privateproject.utils.LogManag;

public class KeyboardViewItem extends LinearLayout {
	private Context mContext;

	// 键盘 数字
	private CharSequence mNumber;
	// 键盘字母
	private CharSequence mLetter;

	public KeyboardViewItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.KeyBoardView, defStyle, 0);

		mNumber = a.getText(R.styleable.KeyBoardView_numbers);
		mLetter = a.getText(R.styleable.KeyBoardView_letters);

		if (null == mNumber) {
			try {
				throw new CustomException("number未添加.");
			} catch (CustomException e) {
				e.printStackTrace();
			} finally {
				LogManag.i("KeyboardViewItem",
						KeyboardViewItem.class.getSimpleName() + " 出错");
			}
			a.recycle();
			return;
		}
		Log.d("my", "mNumber:" + mNumber.toString());
		Log.d("my", "mLetter:" + mLetter.toString());
		a.recycle();
		init(context);
	}

	public KeyboardViewItem(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public KeyboardViewItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	// private void init(Context context) {
	// this.mContext = context;
	// View view = LayoutInflater.from(mContext).inflate(
	// R.layout.view_keyborad_item, this);
	// }

	private void init(Context context) {
		mContext = context;
		// LayoutInflater inflater=(LayoutInflater)
		// context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// View view = inflater.inflate(R.layout.view_keyborad_item,this);
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.view_keyborad_item, this);
		TextView tv_key_borad_item = (TextView) view
				.findViewById(R.id.tv_key_borad_item);
		TextView tv_keyborad_bottom_item = (TextView) view
				.findViewById(R.id.tv_keyborad_bottom_item);
		tv_key_borad_item.setText(mNumber);
		tv_keyborad_bottom_item.setText(mLetter);
	}

}
