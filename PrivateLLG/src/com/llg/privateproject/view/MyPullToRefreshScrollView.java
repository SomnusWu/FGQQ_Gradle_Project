package com.llg.privateproject.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.OverscrollHelper;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.R;

public class MyPullToRefreshScrollView extends PullToRefreshScrollView {
	public MyPullToRefreshScrollViewListener listener;

	public interface MyPullToRefreshScrollViewListener {
		void setScrollLoction(int deltaX, int deltaY, int scrollX,
							  int scrollY, int scrollRangeX, int scrollRangeY,
							  int maxOverScrollX, int maxOverScrollY);
	}

	public MyPullToRefreshScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyPullToRefreshScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyPullToRefreshScrollView(Context context, Mode mode) {
		super(context, mode);
	}

	public MyPullToRefreshScrollView(Context context, Mode mode,
			AnimationStyle style) {
		super(context, mode, style);
	}

	public void setListener(MyPullToRefreshScrollViewListener listener) {
		this.listener = listener;
	}

	@Override
	protected ScrollView createRefreshableView(Context context,
			AttributeSet attrs) {
		ScrollView scrollView;
		if (VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {
			scrollView = new InternalScrollViewSDK9(context, attrs);
		} else {
			scrollView = new ScrollView(context, attrs);
		}

		scrollView.setId(R.id.scrollview);
		return scrollView;
	}

	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
			int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
		// TODO Auto-generated method stub
		Log.d("my", "listener" + listener);
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY,
				scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY,
				isTouchEvent);
	}

	final class InternalScrollViewSDK9 extends ScrollView {

		public InternalScrollViewSDK9(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
				int scrollY, int scrollRangeX, int scrollRangeY,
				int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

			final boolean returnValue = super.overScrollBy(deltaX, deltaY,
					scrollX, scrollY, scrollRangeX, scrollRangeY,
					maxOverScrollX, maxOverScrollY, isTouchEvent);
			// Log.d("my", "listener"+listener);
			if (listener != null) {
				listener.setScrollLoction(deltaX, deltaY, scrollX, scrollY,
						scrollRangeX, scrollRangeY, maxOverScrollX,
						maxOverScrollY);
			}
			// Does all of the hard work...
			OverscrollHelper.overScrollBy(MyPullToRefreshScrollView.this,
					deltaX, scrollX, deltaY, scrollY, getScrollRange(),
					isTouchEvent);

			return returnValue;
		}

		/**
		 * Taken from the AOSP ScrollView source
		 */
		private int getScrollRange() {
			int scrollRange = 0;
			if (getChildCount() > 0) {
				View child = getChildAt(0);
				scrollRange = Math.max(0, child.getHeight()
						- (getHeight() - getPaddingBottom() - getPaddingTop()));
			}
			return scrollRange;
		}
	}

}
