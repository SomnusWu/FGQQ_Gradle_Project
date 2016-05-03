package com.llg.privateproject.camera;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.smartandroid.sa.bitmap.BitmapInfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

public class CompressionUtil {

	// /**
	// * 将图片压缩
	// *
	// * @param originalPath
	// * 原件图片路径
	// * @param path
	// * 压缩之后要保存的路径
	// * @return String
	// */
	// public static void compressionBitmap(String originalPath, int height,
	// int width, Context context) {
	//
	// try {
	// File mfile = new File(FileManager.getCompressFilePath());
	// if (!mfile.exists()) {
	// mfile.mkdirs();
	// }
	// File originalFile = new File(originalPath);
	// Log.i("tag", "------originafile--------");
	// if (!originalFile.exists()) {
	// Log.i("tag", "------originafile-+++-------");
	// originalFile.createNewFile();
	// Log.i("tag", "------originafile-+++--3-----");
	// }
	// Bitmap bmp = decodeFile(originalFile);
	// BitmapInfo bitmapInfo = new BitmapInfo(context);
	// Bitmap bitmap = bitmapInfo.getBitmapZoom(bmp, width, height);
	// bmp.recycle();
	// FileOutputStream fOut = null;
	// fOut = new FileOutputStream(originalFile);
	// if (bitmap != null) {
	// bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
	// bitmap.recycle();
	// }
	// fOut.flush();
	// fOut.close();
	// // return f.getPath();
	// } catch (IOException e) {
	// e.printStackTrace();
	// // return null;
	// }
	// }
	//
	// public static void compressionBitmap(String originalPath, Context
	// context) {
	// try {
	// File mfile = new File(FileManager.getCompressFilePath());
	// if (!mfile.exists()) {
	// mfile.mkdirs();
	// }
	// File originalFile = new File(originalPath);
	// Log.i("tag", "------originafile--------");
	// if (!originalFile.exists()) {
	// Log.i("tag", "------originafile-+++-------");
	// originalFile.createNewFile();
	// Log.i("tag", "------originafile-+++--3-----");
	// }
	// Bitmap bmp = decodeFile(originalFile);
	// // BitmapInfo bitmapInfo = new BitmapInfo(context);
	// // Bitmap bitmap=bitmapInfo.getBitmapZoom(bmp, width, height);
	// // String newName = DigestUtils.md5(String.valueOf(SystemClock
	// // .currentThreadTimeMillis())) + ".jpg";
	// // String newName = "www" + ".jpg";
	// // File f = new File(FileManager.getCompressFilePath() + newName);
	// // if (f.exists()) {
	// // f.delete();
	// // }
	// // if (!f.exists()) {
	// // f.createNewFile();
	// // }
	// FileOutputStream fOut = null;
	// fOut = new FileOutputStream(originalFile);
	// if (bmp != null) {
	// bmp.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
	// bmp.recycle();
	// }
	// fOut.flush();
	// fOut.close();
	//
	// // return f.getPath();
	// } catch (IOException e) {
	// e.printStackTrace();
	// // return null;
	// }
	// }
	//
	// // decodes image and scales it to reduce memory consumption
	// private static Bitmap decodeFile(File f) throws FileNotFoundException {
	// // Decode image size
	// BitmapFactory.Options o = new BitmapFactory.Options();
	// o.inJustDecodeBounds = true;
	// BitmapFactory.decodeStream(new FileInputStream(f), null, o);
	// // The new size we want to scale to
	// final int REQUIRED_HEIGHT = 800;
	// final int REQUIRED_WIDTH = 480;
	// // Find the correct scale value. It should be the power of 2.
	// int width_tmp = o.outWidth, height_tmp = o.outHeight;
	// int scale = 1;
	// while (true) {
	// if (width_tmp / 2 < REQUIRED_WIDTH
	// && height_tmp / 2 < REQUIRED_HEIGHT)
	// break;
	// width_tmp /= 2;
	// height_tmp /= 2;
	// scale++;
	// }
	// // Decode with inSampleSize
	// BitmapFactory.Options o2 = new BitmapFactory.Options();
	// o2.inSampleSize = scale;
	// return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
	// }

	// /**
	// * 将图片压缩
	// *
	// * @param originalPath
	// * 原件图片路径
	// * @param path
	// * 压缩之后要保存的路径
	// * @return String
	// */
	// public static String compressionBitmap(String originalPath) {
	// try {
	// File originalFile = new File(originalPath);
	// Bitmap bmp = decodeFile(originalFile);
	// String newName =
	// DigestUtils.md5(String.valueOf(SystemClock.currentThreadTimeMillis())) +
	// ".jpg";
	// File f = new File(FileManager.getCompressFilePath() + newName);
	// f.createNewFile();
	// FileOutputStream fOut = null;
	// fOut = new FileOutputStream(f);
	// if (bmp != null)
	// bmp.compress(Bitmap.CompressFormat.JPEG, 50, fOut);
	// fOut.flush();
	// fOut.close();
	// return f.getPath();
	// } catch (IOException e) {
	// e.printStackTrace();
	// return null;
	// }
	// }
	//
	// // decodes image and scales it to reduce memory consumption
	// private static Bitmap decodeFile(File f) throws FileNotFoundException {
	// // Decode image size
	// BitmapFactory.Options o = new BitmapFactory.Options();
	// o.inJustDecodeBounds = true;
	// BitmapFactory.decodeStream(new FileInputStream(f), null, o);
	// // The new size we want to scale to
	// final int REQUIRED_HEIGHT = 800;
	// final int REQUIRED_WIDTH = 480;
	// // Find the correct scale value. It should be the power of 2.
	// int width_tmp = o.outWidth, height_tmp = o.outHeight;
	// int scale = 1;
	// while (true) {
	// if (width_tmp / 2 < REQUIRED_WIDTH && height_tmp / 2 < REQUIRED_HEIGHT)
	// break;
	// width_tmp /= 2;
	// height_tmp /= 2;
	// scale++;
	// }
	// // Decode with inSampleSize
	// BitmapFactory.Options o2 = new BitmapFactory.Options();
	// o2.inSampleSize = scale;
	// return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
	// }

	/**
	 * 将图片压缩
	 * 
	 * @param originalPath
	 *            原件图片路径
	 * @param path
	 *            压缩之后要保存的路径
	 * @return String
	 */
	public static String compressionBitmap(String originalPath, Context context) {
		try {
			File originalFile = new File(originalPath);
			Bitmap bmp = decodeFile(originalFile);
			String newName = DigestUtils.md5(String.valueOf(SystemClock
					.currentThreadTimeMillis())) + ".jpg";
			File f = new File(FileManager.getCompressFilePath() + newName);
			f.createNewFile();
			FileOutputStream fOut = null;
			fOut = new FileOutputStream(f);
			if (bmp != null)
				bmp.compress(Bitmap.CompressFormat.JPEG, 50, fOut);
			fOut.flush();
			fOut.close();
			if (bmp!=null) {
				bmp.recycle();
			}
			return f.getPath();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	// decodes image and scales it to reduce memory consumption
	private static Bitmap decodeFile(File f) throws FileNotFoundException {
		// Decode image size
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(new FileInputStream(f), null, o);
		// The new size we want to scale to
		final int REQUIRED_HEIGHT = 800;
		final int REQUIRED_WIDTH = 480;
		// Find the correct scale value. It should be the power of 2.
		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		while (true) {
			if (width_tmp / 2 < REQUIRED_WIDTH
					&& height_tmp / 2 < REQUIRED_HEIGHT)
				break;
			width_tmp /= 2;
			height_tmp /= 2;
			scale++;
		}
		// Decode with inSampleSize
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		o.inJustDecodeBounds = false;
		return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
	}

	

}
