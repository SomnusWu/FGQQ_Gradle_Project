package com.llg.privateproject.view;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.actvity.PutAdvertisementAty;
import com.llg.privateproject.utils.ImageTools;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class DialogUploadPhoto extends Dialog implements
		android.view.View.OnClickListener {
	private Activity activity;
	private String timeString;

	public DialogUploadPhoto(Context context, boolean isCropPhoto,
			boolean isCropCamera) {
		super(context);
		// TODO Auto-generated constructor stub
		this.activity = (Activity) context;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_upload_photo);
		initUI();
	}

	private void initUI() {
		TextView tvPhoto = (TextView) findViewById(R.id.photo);
		TextView tvUpload = (TextView) findViewById(R.id.upload);
		tvPhoto.setOnClickListener(this);
		tvUpload.setOnClickListener(this);
		setCanceledOnTouchOutside(true);
		WindowManager manager = (WindowManager) activity
				.getSystemService(Context.WINDOW_SERVICE);
		WindowManager.LayoutParams Params = getWindow().getAttributes();
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		Params.width = (int) (manager.getDefaultDisplay().getWidth() * 0.9);
		Params.height = (int) (manager.getDefaultDisplay().getHeight() * 0.3);
		getWindow().setAttributes(Params);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// int REQUEST_CODE;
	// switch (v.getId()) {
	// case R.id.photo:// 照片
	// /**
	// * 刚开始，我自己也不知道ACTION_PICK是干嘛的，后来直接看Intent源码，
	// * 可以发现里面很多东西，Intent是个很强大的东西，大家一定仔细阅读下
	// */
	// Intent intent = new Intent(Intent.ACTION_PICK, null);
	// /**
	// * 下面这句话，与其它方式写是一样的效果，如果：
	// * intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	// * intent.setType(""image/*");设置数据类型
	// * 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
	// * 这个地方小马有个疑问，希望高手解答下：就是这个数据URI与类型为什么要分两种形式来写呀？有什么区别？
	// */
	// intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
	// "image/*");
	// dismiss();
	// activity.startActivityForResult(intent, 1);
	// break;
	// case R.id.upload:// 相机
	// /**
	// * 下面这句还是老样子，调用快速拍照功能，至于为什么叫快速拍照，大家可以参考如下官方
	// * 文档，you_sdk_path/docs/guide/topics/media/camera.html
	// * 我刚看的时候因为太长就认真看，其实是错的，这个里面有用的太多了，所以大家不要认为
	// * 官方文档太长了就不看了，其实是错的，这个地方小马也错了，必须改正
	// */
	// dismiss();
	// ((PutAdvertisementAty) activity).setCamera();
	//
	// // 下面这句指定调用相机拍照后的照片存储的路径
	// /*
	// * intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri .fromFile(new
	// * File(Environment .getExternalStorageDirectory()+"/DCIM",
	// * "testpic.jpg"))); startActivityForResult(intent, 2);
	// */
	//
	// break;
	//
	// default:
	// break;
	// }
	// }
	//
	// public void createSDCardDir() {
	// if (Environment.MEDIA_MOUNTED.equals(Environment
	// .getExternalStorageState())) {
	// // 创建一个文件夹对象，赋值为外部存储器的目录
	// File sdcardDir = Environment.getExternalStorageDirectory();
	// // 得到一个路径，内容是sdcard的文件夹路径和名字
	// String path = sdcardDir.getPath() + "/DCIM/Camera";
	// File path1 = new File(path);
	// if (!path1.exists()) {
	// // 若不存在，创建目录，可以在应用启动的时候创建
	// path1.mkdirs();
	//
	// }
	// }
	// }
}
