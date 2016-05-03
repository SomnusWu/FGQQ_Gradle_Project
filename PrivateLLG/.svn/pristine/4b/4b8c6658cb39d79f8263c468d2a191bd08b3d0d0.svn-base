package com.llg.privateproject.view;

import com.bjg.lcc.privateproject.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 带边框的gridview
 * 
 * yh 2015.7.27
 * */
public class LineGridView extends MyGridView {
	public LineGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public LineGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LineGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		View localView1 = getChildAt(0);
		int column = 0;
		if (localView1 != null) {
			column = getWidth() / localView1.getWidth();
		} else {
			return;
		}
		int childCount = getChildCount();
		Paint localPaint;
		localPaint = new Paint();
		localPaint.setStrokeWidth(2);
		localPaint.setStyle(Paint.Style.STROKE);
		localPaint.setColor(getContext().getResources()
				.getColor(R.color.black3));
		for (int i = 0; i < childCount; i++) {
			View cellView = getChildAt(i);
			if ((i + 1) % column == 0) {

				canvas.drawLine(cellView.getLeft(), cellView.getBottom(),
						cellView.getRight(), cellView.getBottom(), localPaint);
				canvas.drawLine(cellView.getRight(), cellView.getTop(),
						cellView.getRight(), cellView.getBottom(), localPaint);
			} else if ((i + 1) > (childCount - (childCount % column))) {
				canvas.drawLine(cellView.getRight(), cellView.getTop(),
						cellView.getRight(), cellView.getBottom(), localPaint);
			} else {
				canvas.drawLine(cellView.getRight(), cellView.getTop(),
						cellView.getRight(), cellView.getBottom(), localPaint);
				canvas.drawLine(cellView.getLeft(), cellView.getBottom(),
						cellView.getRight(), cellView.getBottom(), localPaint);
			}
			if (i < column) {
				canvas.drawLine(cellView.getLeft(), cellView.getTop(),
						cellView.getRight(), cellView.getTop(), localPaint);
			}
			if (i % column == 0) {
				canvas.drawLine(cellView.getLeft(), cellView.getTop(),
						cellView.getLeft(), cellView.getBottom(), localPaint);
			}
		}
		if (childCount % column != 0) {
			for (int j = 0; j < (column - childCount % column); j++) {
				View lastView = getChildAt(childCount - 1);
				canvas.drawLine(lastView.getRight() + lastView.getWidth() * j,
						lastView.getTop(),
						lastView.getRight() + lastView.getWidth() * j,
						lastView.getBottom(), localPaint);
			}
		}
	}

}