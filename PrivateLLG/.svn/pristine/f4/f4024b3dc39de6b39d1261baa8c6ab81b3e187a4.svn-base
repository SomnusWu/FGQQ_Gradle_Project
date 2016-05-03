/**
 * 
 */
package com.llg.privateproject.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.AppContext;

/**
 * @author cc
 * @time 2016年4月20日 下午4:42:03
 * @description
 */
public class DialogDeleteView extends Dialog {
	private Context mContext;
	public android.view.View.OnClickListener mClick;
	TextView tv_delete;

	/**
	 * @param context
	 * @param cancelable
	 * @param cancelListener
	 */
	public DialogDeleteView(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
		init(context);
	}

	/**
	 * @param context
	 * @param theme
	 */
	public DialogDeleteView(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		init(context);
	}

	/**
	 * @param context
	 */
	public DialogDeleteView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public DialogDeleteView(Context context,
			android.view.View.OnClickListener click) {
		super(context);
		this.mClick = click;
		init(context);
	}

	private void init(Context mContext) {
		this.mContext = mContext;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_delete_layout);
		setCanceledOnTouchOutside(true);
		WindowManager.LayoutParams Params = getWindow().getAttributes();
		Params.width = (int) (AppContext.getScreenWidth() * 0.9);
		Params.height = (int) (AppContext.getScreenHeight() * 0.25);
		getWindow().setAttributes(Params);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);

		tv_delete = (TextView) findViewById(R.id.tv_delete);
		tv_delete.setOnClickListener(mClick);
	}

	public DialogDeleteView setTitle(String title) {
		tv_delete.setText(title);
		return this;
	}

	public DialogDeleteView dialogShow() {
		this.show();
		return this;
	}

	public DialogDeleteView dissmiss() {
		this.dismiss();
		return this;
	}

}
