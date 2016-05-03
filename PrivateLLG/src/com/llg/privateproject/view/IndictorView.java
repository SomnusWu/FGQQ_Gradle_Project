package com.llg.privateproject.view;

import java.util.List;
import java.util.Map;

import com.bjg.lcc.privateproject.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

/**
 * 指示器
 * 
 * @author Mrper
 *
 */
public class IndictorView extends View {

	public static final int STYLE_CIRCLE = 0;// 圆形指示�?
	public static final int STYLE_SQUARE = 1;// 矩形指示�?

	private int indictorViewWidth = 0;// 指示器的标准尺寸

	private Paint paint = new Paint();// 画笔
	private int gravity = Gravity.CENTER;
	private int index;
	private List<Map<String, Object>> list;

	public void setData(List<Map<String, Object>> list) {// 设置列表
		this.list = list;
		index=0;
	}

	private ImageView iv;

	public void setImageView(ImageView iv) {
		this.iv = iv;
	}

	public void setGravity(int gravity) {
		if (gravity != Gravity.LEFT || gravity != Gravity.CENTER
				|| gravity != Gravity.RIGHT)
			return;
		this.gravity = gravity;
		this.invalidate();
	}

	public int getGravity() {
		return gravity;
	}

	private int indictorCount = 1;// 指示器数�?

	public void setIndictorCount(int count) {
		if (count < 1)
			count = 1;
		indictorCount = count;
		this.invalidate();
	}

	public int getIndictorCount() {
		return this.indictorCount;
	}

	private int indictorSize = 5;// 指示器尺�?

	public void setIndictorSize(int size) {
		if (size < 5)
			size = 5;
		indictorSize = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, size, getContext().getResources()
						.getDisplayMetrics());
		this.invalidate();
	}

	public int getIndictorSize() {
		return this.indictorSize;
	}

	private int indictorStyle = STYLE_CIRCLE;// 指示器样�?

	public void setIndictorStyle(int style) {
		if (style != 0 || style != 1)
			style = 0;
		indictorStyle = style;
		this.invalidate();
	}

	private int indictorMargin = 4;// 默认的指示器间距

	public void setIndictorMargin(int margin) {
		if (margin < 4)
			margin = 4;
		indictorMargin = margin;

		this.invalidate();
	}

	public int getIndictorMargin() {
		return indictorMargin;
	}

//	private int indictorColor = Color.WHITE;// 指示器颜�?
	private int indictorColor = Color.RED;// 指示器颜�?
	

	public void setIndictorColor(int color) {
		indictorColor = color;
		this.invalidate();
	}

	public int getIndictorColor() {
		return indictorColor;
	}

	public int indictorRed = Color.RED;// 红色
	public int indictorYellow = Color.YELLOW;// 黄色

	private int indictorSelectedColor = Color.BLUE;// 被�?�中的指示器的颜�?

	public void setIndictorSelectedColor(int color) {
		indictorSelectedColor = color;
		this.invalidate();
	}

	public int getIndictorSelectedColor() {
		return indictorSelectedColor;
	}

	private int indictorSelectedIndex = 0;// 选中的指示器的索�?

	public void setIndictorSelectedIndex(int index) {
		this.index=index;
		if (index < 0 || index > indictorCount - 1)
			return;
		indictorSelectedIndex = index;
		this.invalidate();
	}

	public int getIndictorSelectedIndex() {
		return indictorSelectedIndex;
	}

	public IndictorView(Context context) {
		super(context);
		init(context, null);
	}

	public IndictorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public IndictorView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	/**
	 * 初始化操�?
	 * 
	 * @param context
	 */
	public void init(Context context, AttributeSet attrs) {
		int indictor_size = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 8, context.getResources()
						.getDisplayMetrics());
		int indictor_margin = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 5, context.getResources()
						.getDisplayMetrics());
		if (attrs != null) {
			TypedArray ta = context.obtainStyledAttributes(attrs,
					R.styleable.IndictorView);
			indictorCount = ta.getInteger(
					R.styleable.IndictorView_indictorCount, 3);
			indictorSize = ta.getInteger(R.styleable.IndictorView_indictorSize,
					indictor_size);
			indictorStyle = ta.getInteger(
					R.styleable.IndictorView_indictorStyle, STYLE_CIRCLE);
			indictorMargin = ta.getInteger(
					R.styleable.IndictorView_indictorMargin, indictor_margin);
			indictorColor = ta.getColor(R.styleable.IndictorView_indictorColor,
					Color.RED);
			indictorSelectedColor = ta.getColor(
					R.styleable.IndictorView_indictorColor, Color.BLUE);
			ta.recycle();// 资源回收
		} else {
			indictorSize = indictor_size;
			indictorMargin = indictor_margin;
		}
	
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(index==0||index==list.size()-1){
//			return;
		}
		paint.reset();// 重置画笔
		paint.setAntiAlias(true);
		int start_x = 0, // 起始绘制坐标
		width = getMeasuredWidth();// 测量出的控件宽度
		switch (gravity) {
		case Gravity.CENTER:
			start_x = (width - indictorViewWidth) / 2;
			break;
		case Gravity.RIGHT:
			start_x = width - indictorViewWidth;
			break;
		}
		for (int index = 1; index < indictorCount-1; index++) {
			int type = 0;

			paint.setColor(index == indictorSelectedIndex ? indictorSelectedColor
					: indictorColor);// 设置指示器颜�?
			if (list != null && list.get(index) != null
					&& list.get(index).get("type") != null) {

				type = Integer.parseInt(list.get(index).get("type").toString());
				if (type == 1 || type == 3) {
					paint.setColor(indictorRed);
//					paint.setColor(getResources().getColor(
//							R.color.transparentred));
				} else if (type == 2) {
					paint.setColor(indictorYellow);
//					paint.setColor(getResources().getColor(
//							R.color.transparentyellow));
				} else {
					paint.setColor(indictorColor);
				}
			}
			// Log.d("my", "indictorMargin"+indictorMargin);
			switch (indictorStyle) {
			case STYLE_SQUARE:
				canvas.drawRect(new Rect(start_x + indictorSize * index
						+ indictorMargin * (index + 1), indictorMargin, start_x
						+ indictorSize * (index + 1) + indictorMargin
						* (index + 1), indictorSize + indictorMargin), paint);
				break;
			case STYLE_CIRCLE:

				if (index == indictorSelectedIndex && type == 1 && iv != null) {
					iv.setVisibility(View.VISIBLE);
					iv.setBackgroundColor(Color.RED);

				} else if (index == indictorSelectedIndex && type == 2
						&& iv != null) {
					iv.setVisibility(View.VISIBLE);
					iv.setBackgroundColor(Color.YELLOW);
				} else if (index != indictorSelectedIndex && type == 0
						&& iv != null) {
					iv.setVisibility(View.VISIBLE);
					iv.setBackgroundResource(R.drawable.ic_launcher);
				}
				paint.setStyle(index == indictorSelectedIndex ? Paint.Style.FILL
						: Paint.Style.STROKE);

				canvas.drawCircle(start_x + indictorSize * (index * 2 + 1)
						+ indictorMargin * (index + 1), indictorSize
						+ indictorMargin, indictorSize / 2, paint);
				break;
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec), widthSpec = indictorMargin
				* (indictorCount + 1) + indictorCount * indictorSize;
		indictorViewWidth = indictorStyle != STYLE_SQUARE ? indictorMargin
				* (indictorCount + 1) + indictorCount * indictorSize * 2
				: widthSpec;
		int mode = MeasureSpec.getMode(widthMeasureSpec);
		switch (mode) {
		case MeasureSpec.AT_MOST:
			width = widthMeasureSpec;
			break;
		case MeasureSpec.EXACTLY:
			break;
		case MeasureSpec.UNSPECIFIED:
			if (indictorStyle != STYLE_SQUARE)// 如果是圆
				widthSpec = indictorViewWidth;
			if (width < widthSpec)
				width = widthSpec;
			break;
		}
		int height = heightMeasureSpec, heightSpec = indictorMargin * 2
				+ indictorSize;
		if (indictorStyle != STYLE_SQUARE)
			heightSpec = (indictorMargin + indictorSize) * 2;
		height = heightSpec;
		super.onMeasure(width, height);
		setMeasuredDimension(width, height);
	}

}
