package com.llg.privateproject.actvity;

import java.io.File;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.view.DialogUploadPhoto;

public class SumbitDataAty extends BaseActivity {
	@ViewInject(R.id.ly_photo)
	private RelativeLayout lyPhoto;
	@ViewInject(R.id.iv_photo)
	private ImageView ivPhoto;

	private DialogUploadPhoto dialogUploadPhoto;
	private static String path = "/sdcard/myHead/";// sd路径
	private Bitmap head;// 头像Bitmap

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_submit_data);
		ViewUtils.inject(this);
		initUI();
	}

	private void initUI() {
		AppContext.getScreenWidth();
		LayoutParams layoutParams = new LayoutParams(
				AppContext.getScreenWidth() * 2 / 5,
				AppContext.getScreenWidth() * 2 / 5);
		lyPhoto.setLayoutParams(layoutParams);
		// BitmapUtils bitmapUtils = new BitmapUtils(this);
		// bitmapUtils.display(ivPhoto, path + "head.jpg");
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		dialogUploadPhoto.dismiss();
		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {
				com.llg.privateproject.utils.UploadUtil.cropPhoto(
						data.getData(), this);// 裁剪图片
			}
			break;
		case 2:
			if (resultCode == RESULT_OK) {
				File temp = new File(Environment.getExternalStorageDirectory()
						+ "/head.jpg");
				com.llg.privateproject.utils.UploadUtil.cropPhoto(
						Uri.fromFile(temp), this);// 裁剪图片
			}
			break;
		case 3:
			if (data != null) {
				Log.i("tag", "-----来到了data.getExtras()-----");
				Bundle extras = data.getExtras();
				head = extras.getParcelable("data");
				if (head != null) {
					/**
					 * 上传服务器代码
					 */
					com.llg.privateproject.utils.UploadUtil.setPicToView(head);// 保存在SD卡中
					lyPhoto.setVisibility(View.GONE);
					ivPhoto.setVisibility(View.VISIBLE);
					ivPhoto.setImageBitmap(head);// 用ImageView显示出来
				}
			}
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@OnClick({ R.id.iv_back, R.id.ly_photo })
	public void onClick(View v) {
		Log.i("tag", "++++++++++++++++++=----------------tag");
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.ly_photo:
			dialogUploadPhoto = new DialogUploadPhoto(this, true, true);
			dialogUploadPhoto.show();
			break;
		default:
			break;
		}
	}
}
