package com.llg.privateproject.view;

import com.lidroid.xutils.view.annotation.event.OnScrollStateChanged;

import android.R.integer;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

/**
 * 自定义ScrollView yh 2015.6.16
 * */
public class CustomScrollView extends ScrollView {
	private GestureDetector mGestureDetector;
	private View.OnTouchListener mGestureListener;
	private static final String TAG = "CustomHScrollView";
	private IScrollChangeListener isChangeListener;

	/**
	 * @function CustomHScrollView constructor
	 * @param context
	 *            Interface to global information about an application
	 *            environment.
	 * 
	 */
	public CustomScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mGestureDetector = new GestureDetector(
				new HScrollDetector());
		setFadingEdgeLength(0);
	}

	/**
	 * @function CustomHScrollView constructor
	 * @param context
	 *            Interface to global information about an application
	 *            environment.
	 * @param attrs
	 *            A collection of attributes, as found associated with a tag in
	 *            an XML document.
	 */
	public CustomScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mGestureDetector = new GestureDetector(
				new HScrollDetector());
		setFadingEdgeLength(0);
	}

	/**
	 * @function CustomHScrollView constructor
	 * @param context
	 *            Interface to global information about an application
	 *            environment.
	 * @param attrs
	 *            A collection of attributes, as found associated with a tag in
	 *            an XML document.
	 * @param defStyle
	 *            style of view
	 */
	public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		mGestureDetector = new GestureDetector(new HScrollDetector());
		setFadingEdgeLength(0);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return super.onInterceptTouchEvent(ev)
				&& mGestureDetector.onTouchEvent(ev);
	}

	// Return false if we're scrolling in the y direction
	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy) {
		// TODO Auto-generated method stub
		super.onScrollChanged(x, y, oldx, oldy);

	}

	public interface IScrollChangeListener {
		void setLoction(int deltaX, int deltaY, int scrollX, int scrollY);
	}

	public void setIScrollChangedListener(IScrollChangeListener isChangeListener) {

		this.isChangeListener = isChangeListener;
	}

	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
			int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
		// TODO Auto-generated method stub

		if (isChangeListener != null) {
			isChangeListener.setLoction(deltaX, deltaY, scrollX, scrollY);
		}
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY,
				scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY,
				isTouchEvent);
	}

	class HScrollDetector extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			return Math.abs(distanceX) > Math.abs(distanceY);

		}
	}

}
