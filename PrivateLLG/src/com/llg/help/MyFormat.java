package com.llg.help;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bjg.lcc.alipay.pay.PayActivity;
import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.actvity.ProductDetailActivity;
import com.llg.privateproject.actvity.ShopActivity;
import com.llg.privateproject.actvity.WebApplyActivity;
import com.llg.privateproject.actvity.WebAty;
import com.llg.privateproject.actvity.WebAty1;
import com.llg.privateproject.entities.PayInfoModel;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.utils.CommonUtils;
import com.smartandroid.sa.json.Gson;

public class MyFormat {
	private static DecimalFormat dt = (DecimalFormat) DecimalFormat
			.getInstance(); // 获得格式化类对象

	/** 保留两位小数的价格 */
	public static String getPriceFormat(String price) {
		dt.applyPattern("0.00");// 设置小数点位数(两位) 余下的会四舍五入
		if (price == null) {

			return "0.00";
			// dt.format(Double.parseDouble(price.toString().trim()));
		}
		return dt.format(Double.parseDouble(price.toString().trim()));

	}

	public static String getPrice(String price) {
		BigDecimal bd = new BigDecimal(price);
		bd.setScale(2, BigDecimal.ROUND_DOWN);// 直接删除多余的小数位 11.116约=11.11
		if (price == null) {

			return "0.00";
			// dt.format(Double.parseDouble(price.toString().trim()));
		}
		return bd.setScale(2, BigDecimal.ROUND_DOWN) + "";

	}

	/** "yyyyMMddHHmmss" */
	public static String getTimeFormat0(Object time) {
		// SimpleDateFormat sdf=new SimpleDateFormat("", null);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");//

		return sdf.format(time);
	}

	/** "yyyy-MM-dd HH:mm:ss" */
	public static String getTimeFormat(Object time) {
		// SimpleDateFormat sdf=new SimpleDateFormat("", null);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//

		return sdf.format(time);
	}

	/** "yyyy-MM-dd" */
	public static String getTimeFormat1(Object time) {
		// SimpleDateFormat sdf=new SimpleDateFormat("", null);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(time);
	}

	/** "将时间戳格式化yyyy-MM-dd" */
	public static String getTimeFormat2(Object time) {
		// SimpleDateFormat sdf=new SimpleDateFormat("", null);
		Long mtime = Long.parseLong((String) time);
		Date d = new Date(mtime);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(mtime);
	}

	/** "将时间戳格式化yyyy-MM-dd HH:mm:ss" */
	public static String getTimeFormat3(Object time) {
		// SimpleDateFormat sdf=new SimpleDateFormat("", null);
		Long mtime = Long.parseLong((String) time);
		Date d = new Date(mtime);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(mtime);
	}

	/** content 后n位用星号代替 */
	public static String replaceString(String content, int n) {
		String sub = "";
		try {
			sub = content.substring(0, content.length() - n);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < n; i++) {
				sb = sb.append("*");
			}
			sub += sb.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sub;
	}

	/*** 如果是空 返回"" */
	public static String isNull(String text) {
		if (text == null || "null".equals(text))
			return "";
		return text;
	}

	/**
	 * 用view显示网址为URL的图片
	 * 
	 * @param:view 用于显示img
	 * @param:imgUrl 图片url
	 * @param: img_width 选择传入图片宽度
	 * @param: img_height 选择传入图片高度
	 * */
	public static void setBitmap(Context context, View view, String imgUrl,
			int img_width, int img_height) {
		img_height = 0;
		if (imgUrl == null) {
			imgUrl = "";
		}
		BitmapUtils bitmapUtils = null;
		if (bitmapUtils == null) {
			bitmapUtils = new BitmapUtils(context,
					CommonUtils.createSDCardDir())
					.configDefaultLoadFailedImage(
							R.drawable.bjg_tupjiazai_zhengzaijiazai)
					.configDefaultLoadingImage(R.drawable.defaultpic);
		}

		String httpStart = "";
		String newwid = "";
		String newhei = "";
		String newimgUrl = "";
		if (img_width > 0 && img_height > 0) {
			newwid = "img_" + String.valueOf(img_width) + "x";
			newhei = String.valueOf(img_height) + "/";
		}
		if (!isHttpStart(imgUrl)) {
			httpStart = context.getResources().getString(
					R.string.test_image_server_url);
			newimgUrl = httpStart + newwid + newhei + imgUrl;
		} else {
			httpStart = imgUrl
					.substring(
							0,
							context.getResources()
									.getString(R.string.test_image_server_url)
									.length());
			String imgUrlEnd = imgUrl.substring(context.getResources()
					.getString(R.string.test_image_server_url).length());
			newimgUrl = httpStart + newwid + newhei + imgUrlEnd;
		}
		bitmapUtils.display(view, newimgUrl);
	}

	/** 判断 url 是否一http开头 是true,否false */
	public static boolean isHttpStart(String url) {
		return url != null && url.length() > 3
				&& url.subSequence(0, 4).equals("http");
	}

	// 判断手机格式是否正确
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^(1)[0-9]{10}$");// ^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$
		Matcher m = p.matcher(mobiles);

		return m.matches();
	}

	/**
	 * 得到本地或者网络上的bitmap url - 网络或者本地图片的绝对路径,比如:
	 * 
	 * A.网络路径: url="http://blog.foreverlove.us/girl2.png" ;
	 * 
	 * B.本地路径:url="file://mnt/sdcard/photo/image.png";
	 * 
	 * C.支持的图片格式 ,png, jpg,bmp,gif等等
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap GetLocalOrNetBitmap(String url) {
		Bitmap bitmap = null;
		InputStream in = null;
		BufferedOutputStream out = null;
		try {
			in = new BufferedInputStream(new URL(url).openStream());
			final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
			out = new BufferedOutputStream(dataStream);
			copy(in, out);
			out.flush();
			byte[] data = dataStream.toByteArray();
			bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			data = null;
			return bitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void copy(InputStream in, OutputStream out)
			throws IOException {
		byte[] b = new byte[1024];
		int read;
		while ((read = in.read(b)) != -1) {
			out.write(b, 0, read);
		}
	}

	// 判断email格式是否正确
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}

	// 判断是否全是数字
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		return isNum.matches();
	}

	// 邮编格式验证
	public static boolean isZipNO(String zipString) {
		String str = "^[1-9][0-9]{5}$";
		return Pattern.compile(str).matcher(zipString).matches();
	}

	/** dip转像素 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/** 像素转dip */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f) - 15;
	}

	/**
	 * 到店铺/商品也/专题/URL等 time1:进入初始时间
	 * */

	public static void toSomeWhere(Context context, String objectType,
			String objectId, String forwardId, String chain, String adInfoId) {
		if (objectType.equals("1") && objectId != null
				&& !objectId.equals("null") && objectId.length() > 0) {// 跳转到店铺
			Intent intent = new Intent(context, ShopActivity.class);
			intent.putExtra("shopId", objectId);
			if (adInfoId != null) {
				intent.putExtra("adInfoId", adInfoId);
			}
			context.startActivity(intent);
		} else if (objectType.equals("2") && objectId != null
				&& !objectId.equals("null") && objectId.length() > 0) {// 跳转到商品

			Intent intent = new Intent(context, ProductDetailActivity.class);
			if (forwardId != null) {
				intent.putExtra("forwardId", forwardId);
			}
			if (chain != null) {
				intent.putExtra("chain", chain);

			}
			if (adInfoId != null) {
				intent.putExtra("adInfoId", adInfoId);
			}
			intent.putExtra("prodspecId", objectId);
			intent.putExtra("objectType", objectType);
			context.startActivity(intent);
		} else if (objectType.equals("xcxfPay") && objectId != null
				&& !objectId.equals("null") && objectId.length() > 0) {// 现场消费
			Intent intent = new Intent(context, ShopActivity.class);
			intent.putExtra("prodspecId", objectId);
			if (forwardId != null) {

				intent.putExtra("forwardId", forwardId);
			}
			if (chain != null) {
				intent.putExtra("chain", chain);

			}
			if (adInfoId != null) {
				intent.putExtra("adInfoId", adInfoId);
			}
			context.startActivity(intent);
		} else if (objectType.equals("3") && objectId != null
				&& !objectId.equals("null") && objectId.length() > 0) {// 专题

		} else if (objectType.equals("4") && objectId != null
				&& !objectId.equals("null") && objectId.length() > 0) {// url
			Intent to_more = new Intent(context, WebAty1.class);
			if (adInfoId != null) {
				to_more.putExtra("adInfoId", adInfoId);
			}
			to_more.putExtra("prodSpecId", objectId);
			context.startActivity(to_more);
		} else if (objectType.equals("6") && objectId != null
				&& !objectId.equals("null") && objectId.length() > 0) {// 其他
			requestPayInfo(context, objectId);
		}
	}

	static void requestPayInfo(final Context context, String order_id) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token",
				UserInformation.getAccess_token());
		params.addQueryStringParameter("id", order_id);
		params.addHeader("X-Requested-With", "XMLHttpRequest");
		AppContext.getHtmlUitls().xUtilsm(context, HttpMethod.POST,
				"m/pay/getWaitingPay", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						try {
							if (json.getBoolean("success")) {
								Gson gson = new Gson();
								PayInfoModel model = gson.fromJson(
										json.getString("obj"),
										PayInfoModel.class);
								if (model != null) {
									Intent intent = new Intent(context,
											PayActivity.class);
									intent.putExtra("price", model.getPrice());
									intent.putExtra("id", model.getId());
									intent.putExtra("code", model.getCode());
									context.startActivity(intent);

								}

							} else {
								// showToast(json.getString("msg"));
								Toast.makeText(context, json.getString("msg"),
										Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	public static String HEADER_KEY = "X-Requested-With";
	public static String HEADER_VALUE = "XMLHttpRequest";
	public static String errorCode = "errorCode";
	public static String NOT_LOGIN = "NOT_LOGIN";
}