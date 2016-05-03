package com.llg.privateproject.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;

public class MyRadioBtutton extends RadioButton {
	public MyRadioBtutton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyRadioBtutton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyRadioBtutton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	private Context context;

	private Drawable mButtonDrawable;
	private int mButtonResource;

	@Override
	public void setButtonDrawable(int resid) {
		if (resid != 0 && resid == mButtonResource) {
			return;
		}

		mButtonResource = resid;

		Drawable d = null;
		if (mButtonResource != 0) {
			d = getResources().getDrawable(mButtonResource);
		}
		setButtonDrawable(d);
	}

	@Override
	public void setButtonDrawable(Drawable d) {
		if (d != null) {
			if (mButtonDrawable != null) {
				mButtonDrawable.setCallback(null);
				unscheduleDrawable(mButtonDrawable);
			}
			d.setCallback(this);
			d.setState(getDrawableState());
			d.setVisible(getVisibility() == VISIBLE, false);
			mButtonDrawable = d;
			mButtonDrawable.setState(null);
			setMinHeight(mButtonDrawable.getIntrinsicHeight());
		}

		refreshDrawableState();
	}

	// 核心代码部分
	@Override
	protected void onDraw(Canvas canvas) {

		Drawable[] drawables = getCompoundDrawables();
		if (drawables != null) {
			Drawable drawableLeft = drawables[2];
			if (drawableLeft != null) {
				float textWidth = getPaint().measureText(getText().toString());
				int drawablePadding = getCompoundDrawablePadding();
				int drawableWidth = 0;
				drawableWidth = drawableLeft.getIntrinsicWidth();
				float bodyWidth = textWidth + drawableWidth + drawablePadding;
				setPadding(0, 0, (int) (getWidth() - bodyWidth), 0);
				canvas.translate((getWidth() - bodyWidth) / 2, 0);
			}
		}
		super.onDraw(canvas);
	}
}
