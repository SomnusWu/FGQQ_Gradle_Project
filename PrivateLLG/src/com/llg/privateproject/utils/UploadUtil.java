package com.llg.privateproject.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

public class UploadUtil {
	private static String path = "/sdcard/myHead/";// sd·��

	/**
	 * ����ϵͳ�Ĳü�
	 * 
	 * @param uri
	 */
	public static void cropPhoto(Uri uri, Activity activity) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 100);
		intent.putExtra("outputY", 100);
		intent.putExtra("return-data", true);
		activity.startActivityForResult(intent, 3);
	}

	public static void setPicToView(Bitmap mBitmap) {
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // ���sd�Ƿ����
			return;
		}
		FileOutputStream b = null;
		File file = new File(path);
		file.mkdirs();// �����ļ���
		String fileName = path + "advertisementhead.jpg";// ͼƬ����
		try {
			b = new FileOutputStream(fileName);
			mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// �����д���ļ�
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				// �ر���
				b.flush();
				b.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
