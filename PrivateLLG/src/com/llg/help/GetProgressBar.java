package com.llg.help;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.BitmapUtils;
import com.llg.privateproject.utils.CommonUtils;
import com.llg.privateproject.view.MyProgressBar;

/**
 * 进度条 yh. 2015.08.28
 * 
 * */
public class GetProgressBar {
	private static MyProgressBar myProgressBar;

	/** 显示进度条 */
	public static MyProgressBar getProgressBar(Context context) {
		if (myProgressBar == null) {
			myProgressBar = new MyProgressBar(context);
		}
		myProgressBar.show();
		final AnimationDrawable anim = (AnimationDrawable) myProgressBar
				.getImageView().getBackground();// 加载进度条
		anim.start();

		myProgressBar.setCanceledOnTouchOutside(false);// 点击空白处不消失
		return myProgressBar;
	}
	/** 显显示进度条 */
	public static MyProgressBar getProgressBar(Context context,int type) {
		if (myProgressBar == null) {
			myProgressBar = new MyProgressBar(context);
		}
		myProgressBar.show();
		ImageView iv=myProgressBar.getImageView();
		iv.setBackgroundResource(R.drawable.myprogressbar);
		final AnimationDrawable anim = (AnimationDrawable) iv.getBackground();// 加载进度条
		anim.start();
		
		myProgressBar.setCanceledOnTouchOutside(false);// 点击空白处不消失
		return myProgressBar;
	}

	/** 取消进度条 */
	public static void dismissMyProgressBar() {
		if (myProgressBar != null) {
			myProgressBar.dismiss();
			myProgressBar = null;

		}
	}

	public static BitmapUtils bitmapUtils;
	/** 用view显示网址为URL的图片 */
	// public static void setBitmap(Context context,View view,String url){
	// if(bitmapUtils==null){
	// bitmapUtils=new BitmapUtils(context,
	// CommonUtils.createSDCardDir()).configDefaultLoadFailedImage(R.drawable.bjg_tupjiazai_jiazaishuabai).configDefaultLoadingImage(R.drawable.defaultpic);
	// }
	// bitmapUtils.display(view, url);
	// }
}