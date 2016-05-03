/**
 * 
 */
package com.llg.privateproject.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.AppContext;

/**
 * @author cc
 * @time 2016年3月30日 上午9:30:47
 * @description
 */
public class FGQQSelectShareDialog extends Dialog {
	private Context mContext;
	private android.view.View.OnClickListener mClickListener;

	/**
	 * @param context
	 * @param cancelable
	 * @param cancelListener
	 */
	public FGQQSelectShareDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
		init(context);
	}

	/**
	 * @param context
	 * @param theme
	 */
	public FGQQSelectShareDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		init(context);
	}

	/**
	 * @param context
	 */
	public FGQQSelectShareDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public FGQQSelectShareDialog(Context context,
			android.view.View.OnClickListener click) {
		super(context);
		mClickListener = click;
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.share_dialog_layout);
		setCanceledOnTouchOutside(true);
		WindowManager.LayoutParams Params = getWindow().getAttributes();
		Params.width = (int) (AppContext.getScreenWidth() * 0.9);
		Params.height = (int) (AppContext.getScreenHeight() * 0.25);
		getWindow().setAttributes(Params);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		setShareClick();
	}
 

	public void setShareClick() {
		Button btn_cancel = (Button) findViewById(R.id.btn_cancel);
		Button btn_ok = (Button) findViewById(R.id.btn_ok);

		btn_cancel.setOnClickListener(mClickListener);
		btn_ok.setOnClickListener(mClickListener);
	}

	public String getEditText() {
		EditText edit = (EditText) findViewById(R.id.edit_red_money);
		return edit.getText().toString();
	}

}
