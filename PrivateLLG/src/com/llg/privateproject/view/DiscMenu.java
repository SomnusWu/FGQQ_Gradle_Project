package com.llg.privateproject.view;

import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.view.DiscMenu.ActionEvent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/** 圆盘菜单 */
public class DiscMenu extends View {
	private Paint mPaint = new Paint();
	/** 圆盘中心x坐标 */
	private int cx;
	/** 圆盘中心y坐标 */
	private int cy;
	/** 圆盘中心距菜单距离 */
	private int r;
	/** 菜单个数 */
	private int count = 7;
	/** 菜单集合 */
	private MenuItem[] menuItems;
	/** 菜单间的角度 */
	private int intervalangle;
	private float x1;
	private float y1;
	// int pic[] = { R.drawable.test0, R.drawable.test1, R.drawable.test2,
	// R.drawable.test3, R.drawable.test4, R.drawable.test5,
	// R.drawable.test6 };
	Context context;

	/**
	 * @param context
	 * @param cx
	 *            菜单中心x坐标
	 * @param cy
	 *            菜单中心y坐标
	 * @param r
	 *            菜单距中心的距离
	 * 
	 * */
	public DiscMenu(Context context, int cx, int cy, int r) {
		super(context);
		// TODO Auto-generated constructor stub
		this.cx = cx;
		this.cy = cy;
		this.r = r;
		this.context = context;
		mPaint.setColor(Color.GREEN);
		mPaint.setStrokeWidth(2);
		mPaint.setTextSize(34);
		setBackgroundResource(R.drawable.ic_launcher);
		setMenuItem();
	}

	/** 初始化菜单及位置 */
	private void setMenuItem() {
		menuItems = new MenuItem[count];
		MenuItem menuItem;
		int angle = 0;
		intervalangle = 360 / count;
		for (int i = 0; i < count; i++) {
			menuItem = new MenuItem();
			menuItem.title = "title" + i;
			menuItem.angle = angle;
			angle += intervalangle;
			menuItem.actionEvent = new ActionEvent() {

				@Override
				public void action(String title) {
					// TODO Auto-generated method stub
					Log.d("my", "title" + title);
				}
			};

//			menuItem.bitmap = BitmapFactory.decodeResource(getResources(),
//					R.drawable.bjg_shouye_logo);
			menuItem.bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.aaaaa);

			menuItems[i] = menuItem;
		}
		computePosition();
	}

	/*** 重置菜单 ***/
	private void resetMenuItem(int x1, int y1, int x2, int y2) {
		int degree = resetMenuItemdegree(x2, y2);
		for (int i = 0; i < count; i++) {
			if (Math.abs(x1 - x2) > 10 && Math.abs(y1 - y2) > 10) {

				menuItems[i].angle = degree;
				degree += intervalangle;
			} else if (Math.abs(x2 - menuItems[i].x) <= 30
					&& Math.abs(y2 - menuItems[i].y) <= 30
					&& Math.abs(x1 - x2) < 10 && Math.abs(y1 - y2) < 10) {
				// Log.d("my", "x"+x+"tx"+menuItems[i].x);
				menuItems[i].actionEvent.action(menuItems[i].title);
			}
		}
		computePosition();
	}

	/** 计算第一个点的角度 */
	private int resetMenuItemdegree(float x, float y) {
		float distance = (float) (Math.sqrt((x - cx) * (x - cx) + (y - cy)
				* (y - cy)));
		int degree = (int) (Math.acos((x - cx) / distance) * 180 / Math.PI);// 当前位置的角度
		if (y < cy) {
			degree = -degree;
		}

		// Log.d("my", "x:"+x+",y:"+y+",degree:"+degree);
		return degree;
	}

	/** 计算菜单位置 */
	private void computePosition() {
		MenuItem item;

		for (int i = 0; i < count; i++) {
			item = menuItems[i];

			item.x = (int) (cx + r * Math.cos(item.angle * Math.PI / 180));
			item.y = (int) (cy + r * Math.sin(item.angle * Math.PI / 180));

		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method st

		float x2 = 0f;
		float y2 = 0f;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			x1 = event.getX();
			y1 = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			x2 = event.getX();
			y2 = event.getY();
			break;

		default:
			break;
		}
		resetMenuItem((int) x1, (int) y1, (int) event.getX(),
				(int) event.getY());

		invalidate();
		Log.d("my", "x1" + x1 + "y1" + y1);
		Log.d("my", "x2" + event.getX() + "y2" + event.getY());

		return true;
	}

	// @Override
	// public boolean dispatchTouchEvent(MotionEvent event) {
	// // TODO Auto-generated method stub
	// resetMenuItem((int) event.getX(), (int) event.getY());
	//
	// invalidate();
	// return true;
	// }

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		for (int i = 0; i < count; i++) {

			drawMenu(canvas, menuItems[i].bitmap, menuItems[i].x,
					menuItems[i].y, menuItems[i].title);
		}
		// invalidate();
	}

	/** 画菜单 */
	private void drawMenu(Canvas canvas, Bitmap bitmap, float lef, float top,
			String title) {

		canvas.drawBitmap(bitmap, lef - bitmap.getWidth() / 2,
				top - bitmap.getHeight() / 2, mPaint);
		// canvas.drawLine(cx, cy, lef, top, mPaint);
		canvas.drawText(title, lef - bitmap.getWidth() / 4,
				top + bitmap.getHeight(), mPaint);

	}

	interface ActionEvent {
		void action(String title);
	}
}

/*** 菜单项 */
class MenuItem {

	String title;

	Bitmap bitmap;
	int x;
	int y;
	int angle;
	ActionEvent actionEvent;
	boolean isVisible = true;

}
