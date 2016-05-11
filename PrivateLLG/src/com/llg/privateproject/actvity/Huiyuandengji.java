package com.llg.privateproject.actvity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bjg.lcc.privateproject.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.entities.PersonDetailListModel;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.utils.CommonUtils;
import com.llg.privateproject.utils.LogManag;
import com.llg.privateproject.view.DialogDeleteView;
import com.llg.privateproject.view.OrderStatusDialog;
import com.tencent.mm.sdk.modelmsg.GetMessageFromWX;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * 邀请好友
 * 
 * 会员等级 yh // * 2015.08.20
 * */
public class Huiyuandengji extends BaseActivity {
	int QR_WIDTH = 400;
	int QR_HEIGHT = 400;
	String text = null;
	private IWXAPI api;
	Bundle bundle;
	/** 标题 */
	@ViewInject(R.id.head_tilte)
	private TextView head_tilte;
	/** 当前功能 */
	@ViewInject(R.id.current_value)
	private TextView current_value;
	/** 下级功能 */
	@ViewInject(R.id.next_value)
	private TextView next_value;
	/** 下级功能标题 */
	@ViewInject(R.id.next)
	private TextView next;
	/***/
	@ViewInject(R.id.ll_update)
	private LinearLayout ll_update;
	// @ViewInject(R.id.ll_1)
	// private LinearLayout ll_1;
	// @ViewInject(R.id.ll_2)
	// private LinearLayout ll_2;
	// @ViewInject(R.id.ll_3)
	// private LinearLayout ll_3;
	// @ViewInject(R.id.ll_4)
	// private LinearLayout ll_4;
	// @ViewInject(R.id.ll_5)
	// private LinearLayout ll_5;
	// @ViewInject(R.id.ll_6)
	// private LinearLayout ll_6;
	// @ViewInject(R.id.ll_7)
	// private LinearLayout ll_7;
	// @ViewInject(R.id.ll_8)
	// private LinearLayout ll_8;
	/** 会员 */
	@ViewInject(R.id.huiyuan)
	private LinearLayout ll_huiyuan;
	@ViewInject(R.id.tv_huiyuan)
	private TextView huiyuan;
	// @ViewInject(R.id.tv_2)
	// private TextView tv_2;
	// @ViewInject(R.id.tv_3)
	// private TextView tv_3;
	// @ViewInject(R.id.tv_4)
	// private TextView tv_4;
	// @ViewInject(R.id.tv_5)
	// private TextView tv_5;
	// @ViewInject(R.id.tv_6)
	// private TextView tv_6;
	// @ViewInject(R.id.tv_7)
	// private TextView tv_7;
	// @ViewInject(R.id.tv_8)
	// private TextView tv_8;
	/** vip会员 */
	@ViewInject(R.id.ll_vip)
	private LinearLayout ll_vip;
	@ViewInject(R.id.vip)
	private TextView vip;
	/** vip会员 */
	@ViewInject(R.id.tv_hui)
	private TextView tv_hui;
	/** 企业版会员 */
	@ViewInject(R.id.chuangyezhe)
	private LinearLayout ll_chuangyezhe;
	@ViewInject(R.id.tv_chuangyezhe)
	private TextView chuangyezhe;
	/** 我邀请的会员数 */
	@ViewInject(R.id.my_member)
	private TextView my_member;
	/** 我邀请的有效会员数 */
	@ViewInject(R.id.my_success_member)
	private TextView my_success_member;
	/** 区域平台 */
	@ViewInject(R.id.quyupingtai)
	private LinearLayout ll_quyupingtai;
	@ViewInject(R.id.tv_quyupingtai)
	private TextView quyupingtai;

	/** 付款升级说明 */
	@ViewInject(R.id.pay_instruction)
	private TextView pay_instruction;
	/** 复制邀请码 */
	@ViewInject(R.id.copemycode)
	private TextView copemycode;
	// @ViewInject(R.id.tv_1)
	// private TextView tv_1;
	/** 我的邀请码 */
	@ViewInject(R.id.mycode)
	private TextView mycode;
	/** 显示二维码给好友 */
	@ViewInject(R.id.iv_zxing)
	private ImageView iv_zxing;
	/** 显示上级二维码 */
	@ViewInject(R.id.iv_zxing2)
	private ImageView iv_zxing2;
	@ViewInject(R.id.tv_leader_invite_description)
	private TextView tv_leader_invite_description;
	
	private String leader_invite_descriptionStr = "";

	int type = 1;
	ClipboardManager cbm;

	private String leaderInviteUrl = "";// 上级推荐url
	private String allCount = "0";// 邀请会员数
	private String inviteUrl = "http://www.fgqqg.com/register?inviteCode=admin";// 默认推荐注册url
	private String inviteCode = "请登录获取邀请码";// 默认推荐码
	private String effCount = "0";// 默认有效会员数
	private String userType = "0";// 默认用户类型
	private SharedPreferences sp;
	private String one = "购买商品、发展会员,在合作商家享受风购全球会员打折消费, 广告窗口向下3层会员可看 ,可获得下线会员消费酷币提成(100酷币以上部分可兑换人民币), 可在PC端注册成为商家上传商品";
	private String two = "拥有名下普通会员消费返利和公告平台做广告收入的返利及引入奖励，广告窗口向下七层会员可看,获取多项强大推广系统（例如三级分销功能等）";
	private String three = "拥有商家功能版的所有功能和更高的返佣权限,注册商家,上传商品,拥有商家版及以下普通会员升级为商家版功能板企业版的招商奖励，强大的多项推广系统,得到平台的不断推新和完善，拥有精准投放功能，可向名下无限层所有商家版进行推送";
	private String four = "拥有企业版的所有功能,也拥有自己名下所有企业版及以下的会员的消费返利,所有普通会员升级为商家功能板企业版的间接招商奖励，并可向名下所有企业版及以下所有会员展示广告内容";
	Editor editor;
	private Map<String, Object> map;

	List<PersonDetailListModel> personList;
	@ViewInject(R.id.ll_layout_person_detail)
	private LinearLayout ll_layout_person_detail;

	@ViewInject(R.id.tv_apply_shop)
	TextView tv_apply_shop;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1://
				my_member.setText(allCount);
				my_success_member.setText(effCount);
				mycode.setText(inviteCode);
				Log.d("my", "inviteurl:" + inviteUrl);
				if (UserInformation.isLogin()) {
					createImage(inviteUrl, iv_zxing);
				}
				if (leaderInviteUrl.length() > 5) {
					tv_hui.setVisibility(View.VISIBLE);
					iv_zxing2.setVisibility(View.VISIBLE);
					tv_leader_invite_description.setVisibility(View.VISIBLE);
					createImage(leaderInviteUrl, iv_zxing2);
					leader_invite_descriptionStr = leader_invite_descriptionStr.replace("\\n", "\n");
					tv_leader_invite_description.setText(leader_invite_descriptionStr);
					startAlphaAnim();
					
				}
				ll_layout_person_detail.removeAllViews();
				if (personList != null && personList.size() > 0) {
					for (int i = 0; i < personList.size(); i++) {
						PersonDetailListModel model  = personList.get(i);
						View view = LayoutInflater.from(Huiyuandengji.this)
								.inflate(R.layout.layout_person_detail_item,
							 			null);
						TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
						tv_title.setText(model.getLabel());
						TextView tv_value = (TextView) view.findViewById(R.id.tv_value);
						tv_value.setText(model.getCount());
						ll_layout_person_detail.addView(view);
					}
				}

				memeber(map);
				setTextColor(userType);
				break;

			default:
				break;
			}
		}
	};
	/**
	 * 渐显动画
	 */
	private void startAlphaAnim() {
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha_anim);
		tv_leader_invite_description.startAnimation(anim);
	}

	DialogDeleteView dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.huiyuandengji);
		ViewUtils.inject(this);

		init();

		if (UserInformation.isLogin()) {
			createImage(inviteUrl, iv_zxing);

			iv_zxing.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					// TODO Auto-generated method stub
					// iv_zxing.setDrawingCacheEnabled(true);
					// Bitmap bitMap = iv_zxing.getDrawingCache();
					// iv_zxing.setDrawingCacheEnabled(false);
					dialog = new DialogDeleteView(Huiyuandengji.this,
							new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									dialog.dismiss();
									saveImage(iv_zxing);
								}
							}).setTitle("保存图片到手机").dialogShow();

					return true;
				}
			});
			iv_zxing2.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					// TODO Auto-generated method stub
					dialog = new DialogDeleteView(Huiyuandengji.this,
							new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									dialog.dismiss();
									saveImage(iv_zxing2);
								}
							}).setTitle("保存图片到手机").dialogShow();
					return true;
				}
			});

		} else {
			iv_zxing.setVisibility(View.GONE);
			tv_hui.setVisibility(View.VISIBLE);
			tv_hui.setText("请登录后获取我的推广二维码.");
			// createImage("请登录后再扫描", iv_zxing);
		}
		// createImage(leaderInviteUrl, iv_zxing2);
		mSchemeData();
	}

	private void saveImage(ImageView img) {
		Bitmap image = ((BitmapDrawable) img.getDrawable()).getBitmap();
		LogManag.d("sss", "ssss" + image);
		// iv_zxing.setDrawingCacheEnabled(true);
		// Bitmap obmp =
		// Bitmap.createBitmap(iv_zxing.getDrawingCache());

		try {
			CommonUtils.saveFile(image, CommonUtils.ZXingImage);
			CommonUtils.scanFileAsync(Huiyuandengji.this,
					CommonUtils.createSDCardDir() + CommonUtils.ZXingImage);
			// CommonUtils.scanDirAsync(dir.this, "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// new downloadImgThread(inviteUrl).start();
	}

	private void memeber(Map<String, Object> map) {
		// ll_1.setVisibility(View.GONE);
		// ll_2.setVisibility(View.GONE);
		// ll_3.setVisibility(View.GONE);
		// ll_4.setVisibility(View.GONE);
		// ll_5.setVisibility(View.GONE);
		// ll_6.setVisibility(View.GONE);
		// ll_7.setVisibility(View.GONE);
		// ll_8.setVisibility(View.GONE);
		if (map == null) {
			return;
		} else {
			// if (map.get("refereeCount") != null) {
			// // ll_1.setVisibility(View.VISIBLE);
			// tv_1.setText(map.get("refereeCount").toString());
			// }
			// if (map.get("leadershipCount") != null) {
			// ll_2.setVisibility(View.VISIBLE);
			// tv_2.setText(map.get("leadershipCount").toString());
			// }
			// if (map.get("epCount") != null) {
			// ll_3.setVisibility(View.VISIBLE);
			// tv_3.setText(map.get("epCount").toString());
			// }
			// if (map.get("studioCount") != null) {
			// ll_4.setVisibility(View.VISIBLE);
			// tv_4.setText(map.get("studioCount").toString());
			// }
			// if (map.get("provinceCount") != null) {
			// ll_5.setVisibility(View.VISIBLE);
			// tv_5.setText(map.get("provinceCount").toString());
			// }
			// if (map.get("provinceLeaderCount") != null) {
			// ll_6.setVisibility(View.VISIBLE);
			// tv_6.setText(map.get("provinceLeaderCount").toString());
			// }
			// if (map.get("lowerShopCount") != null) {
			// ll_7.setVisibility(View.VISIBLE);
			// tv_7.setText(map.get("lowerShopCount").toString());
			// }
			// if (map.get("separateEntrepreneurCount") != null) {
			// ll_8.setVisibility(View.VISIBLE);
			// tv_8.setText(map.get("separateEntrepreneurCount").toString());
			// }
		}

	}

	private void init() {
		memeber(map);

		sp = getSharedPreferences("userInformation1", Context.MODE_PRIVATE);
		// leaderInviteUrl = sp.getString("leaderInviteUrl",
		// "http://www.fgqqg.com");
		inviteUrl = sp.getString("inviteUrl",
				getResources().getString(R.string.buyserver)
						+ "m/reg?inviteCode=admin");
		String isShop = sp.getString("isShop", "N");

		if (isShop.equalsIgnoreCase("Y")) {
			tv_apply_shop.setVisibility(View.GONE);
		} else {
			tv_apply_shop.setVisibility(View.VISIBLE);
		}

		mycode.setText(sp.getString("inviteCode", inviteCode));
		my_member.setText(sp.getString("allCount", "0"));
		my_member.setVisibility(View.GONE);
		my_success_member.setText(sp.getString("effCount", "0"));
		my_success_member.setVisibility(View.GONE);
		setTextColor(userType);
		editor = sp.edit();
		cbm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		type = getIntent().getIntExtra("type", 1);
		if (type == 2) {
			// ll_update.setVisibility(View.GONE);
		}
		api = WXAPIFactory.createWXAPI(this,
				com.llg.privateproject.utils.Constants.APP_ID, true);
		api.registerApp(com.llg.privateproject.utils.Constants.APP_ID);
		bundle = getIntent().getExtras();
		head_tilte.setText("我的会员");

		copemycode.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		pay_instruction.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		basicInfo();
	}

	/** 当前功能介绍 */
	private void currentinfomation(String userType) {
		if (userType.length() < 1) {
			userType = "0";
		}
		switch (Integer.parseInt(userType)) {
		case 0:
			current_value.setText(one);
			next_value.setText(two);

			break;
		case 1:
			current_value.setText(two);
			next_value.setText(three);
			break;
		case 5:
			current_value.setText(three);
			next_value.setText(four);
			break;
		case 7:
			current_value.setText(four);
			next_value.setVisibility(View.GONE);
			next.setVisibility(View.GONE);
			break;

		default:
			break;
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		currentinfomation(userType);
	}

	/** 获取分享邀请链接 */
	private void basicInfo() {
		RequestParams params = new RequestParams();
		params.addHeader(MyFormat.HEADER_KEY, MyFormat.HEADER_VALUE);
		params.addQueryStringParameter("access_token", UserInformation.getAccess_token());
		params.addQueryStringParameter("time", "" + System.currentTimeMillis());
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/develop/basicInfo", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub

						try {
							if (json.getBoolean("success")) {

								// PersonDetailListModel
								JSONArray jsonobj = json.getJSONArray("obj");
								Gson gson = new Gson();
								personList = gson.fromJson(
										jsonobj.toString(),
										new TypeToken<List<PersonDetailListModel>>() {
										}.getType());

								JSONObject obj = json
										.getJSONObject("attributes");
								if (obj.has("developPersons")) {
									JSONObject developPersons = obj
											.getJSONObject("developPersons");
									if (map == null) {
										map = new HashMap<String, Object>();
									}
									if (developPersons.has("refereeCount")) {// 直接发展会员数
										map.put("refereeCount", developPersons
												.getInt("refereeCount"));
									}
									if (developPersons.has("leadershipCount")) {// leadershipCount:作为第一返佣会员，所有下级会员数
										map.put("leadershipCount",
												developPersons
														.getInt("leadershipCount"));
									}
									if (developPersons.has("epCount")) {// epCount:作为企业版，所有下级会员数
										map.put("epCount", developPersons
												.getInt("epCount"));
									}
									if (developPersons.has("studioCount")) {// studioCount:作为工作室，所有下级会员数
										map.put("studioCount", developPersons
												.getInt("studioCount"));
									}
									if (developPersons.has("provinceCount")) {// provinceCount:省代(作为区域代理，所有下级会员数)
										map.put("provinceCount", developPersons
												.getInt("provinceCount"));
									}
									if (developPersons
											.has("provinceLeaderCount")) {// provinceLeaderCount:作为总代，所有下级会员数
										map.put("provinceLeaderCount",
												developPersons
														.getInt("provinceLeaderCount"));
									}
									if (developPersons.has("lowerShopCount")) {// lowerShopCount:企业版，所有下级会员中商家数
										map.put("lowerShopCount",
												developPersons
														.getInt("lowerShopCount"));
									}
									if (developPersons
											.has("separateEntrepreneurCount")) {// separateEntrepreneurCount:企业版,已经脱离我成为企业版数
										map.put("separateEntrepreneurCount",
												developPersons
														.getInt("separateEntrepreneurCount"));
									}
								}
								if (!obj.get("leaderInviteUrl").equals(null)) {
									leaderInviteUrl = obj
											.getString("leaderInviteUrl");
									editor.putString("leaderInviteUrl",
											obj.getString("leaderInviteUrl"));
								}
								if (!obj.get("allCount").equals(null)) {
									allCount = obj.getString("allCount");
									editor.putString("allCount",
											obj.getString("allCount"));
								}
								if (!obj.get("inviteUrl").equals(null)) {
									inviteUrl = obj.getString("inviteUrl");
									editor.putString("inviteUrl",
											obj.getString("inviteUrl"));
								}
								if (!obj.get("inviteCode").equals(null)) {
									inviteCode = obj.getString("inviteCode");
									editor.putString("inviteCode",
											obj.getString("inviteCode"));
								}
								if (!obj.get("effCount").equals(null)) {
									effCount = obj.getString("effCount");
									editor.putString("effCount",
											obj.getString("effCount"));
								}
								if (!obj.get("effCount").equals(null)) {
									effCount = obj.getString("effCount");
									editor.putString("effCount",
											obj.getString("effCount"));
								}
								if (!obj.get("userType").equals(null)) {
									userType = obj.getString("userType");
									editor.putString("userType",
											obj.getString("userType"));
								} else {
									userType = "0";
									editor.putString("userType", "0");
								}
								leader_invite_descriptionStr = obj.getString("leader_invite_description");
								
								editor.commit();
								handler.sendEmptyMessage(1);
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	/** 设置会员等级标识颜色 */
	private void setTextColor(String userType) {
		if (userType.equals("5")) {// 企业版
			huiyuan.setTextColor(getResources().getColor(R.color.black2));
			vip.setTextColor(getResources().getColor(R.color.black2));
			chuangyezhe.setTextColor(getResources().getColor(R.color.orange2));
			quyupingtai.setTextColor(getResources().getColor(R.color.black2));
			ll_huiyuan.setVisibility(View.GONE);
			ll_vip.setVisibility(View.GONE);
			ll_chuangyezhe.setVisibility(View.VISIBLE);
			ll_quyupingtai.setVisibility(View.GONE);
		} else if (userType.equals("1")) {// VIP用户(商家功能)
			huiyuan.setTextColor(getResources().getColor(R.color.black2));
			vip.setTextColor(getResources().getColor(R.color.orange2));
			chuangyezhe.setTextColor(getResources().getColor(R.color.black2));
			quyupingtai.setTextColor(getResources().getColor(R.color.black2));
			ll_huiyuan.setVisibility(View.GONE);
			ll_vip.setVisibility(View.VISIBLE);
			ll_chuangyezhe.setVisibility(View.GONE);
			ll_quyupingtai.setVisibility(View.GONE);
		} else if (userType.equals("7")) {// 区域平台
			huiyuan.setTextColor(getResources().getColor(R.color.black2));
			vip.setTextColor(getResources().getColor(R.color.black2));
			chuangyezhe.setTextColor(getResources().getColor(R.color.black2));
			quyupingtai.setTextColor(getResources().getColor(R.color.orange2));
			ll_huiyuan.setVisibility(View.GONE);
			ll_vip.setVisibility(View.GONE);
			ll_chuangyezhe.setVisibility(View.GONE);
			ll_quyupingtai.setVisibility(View.VISIBLE);
		} else {// 普通用户
			huiyuan.setTextColor(getResources().getColor(R.color.orange2));
			vip.setTextColor(getResources().getColor(R.color.black2));
			chuangyezhe.setTextColor(getResources().getColor(R.color.black2));
			quyupingtai.setTextColor(getResources().getColor(R.color.black2));
			ll_huiyuan.setVisibility(View.VISIBLE);
			ll_vip.setVisibility(View.GONE);
			ll_chuangyezhe.setVisibility(View.GONE);
			ll_quyupingtai.setVisibility(View.GONE);
		}
	}

	@Override
	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		bundle = intent.getExtras();
	}

	// 生成QR图
	private void createImage(String inviteUrl, ImageView v) {
		// text = "风购全球网,给你无限精彩";
		text = inviteUrl;
		// text=AppContext.getHtmlUitls().getDataHttp()+AppContext.setLogurl();
		try {
			// 需要引入core包
			QRCodeWriter writer = new QRCodeWriter();

			// 把输入的文本转为二维码
			BitMatrix martix = writer.encode(inviteUrl, BarcodeFormat.QR_CODE,
					QR_WIDTH, QR_HEIGHT);

			// System.out.println("w:" + martix.getWidth() + "h:"
			// + martix.getHeight());

			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			BitMatrix bitMatrix = new QRCodeWriter().encode(text,
					BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
			int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
			for (int y = 0; y < QR_HEIGHT; y++) {
				for (int x = 0; x < QR_WIDTH; x++) {
					if (bitMatrix.get(x, y)) {
						pixels[y * QR_WIDTH + x] = 0xff000000;
					} else {
						pixels[y * QR_WIDTH + x] = 0xffffffff;
					}

				}
			}

			Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
					Bitmap.Config.ARGB_8888);

			bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
			v.setImageBitmap(bitmap);

		} catch (WriterException e) {
			e.printStackTrace();
		}
	}

	@OnClick({ R.id.turn, R.id.huiyuan, R.id.vip, R.id.chuangyezhe,
			R.id.ll_pay, R.id.pay_instruction, R.id.detail, R.id.iv_friend,
			R.id.quyupingtai, R.id.iv_weixin, R.id.copemycode,
			R.id.tv_apply_shop })
	public void myClick(View v) {
		switch (v.getId()) {
		case R.id.copemycode:// 复制邀请码
			String code = mycode.getText().toString();
			cbm.setText(code);
			Toast.makeText(this, "邀请码已复制", Toast.LENGTH_SHORT).show();
			break;
		case R.id.turn:// 返回
			finish();
			break;
		case R.id.huiyuan:// 会员
			// OrderStatusDialog dialog1=new OrderStatusDialog(this, 8, 1);
			setDialog(1);
			break;
		case R.id.vip:// vip商家功能版
			// OrderStatusDialog dialog2=new OrderStatusDialog(this, 8,2);
			setDialog(2);
			break;
		case R.id.chuangyezhe:// 创业者
			// OrderStatusDialog dialog3=new OrderStatusDialog(this, 8, 3);
			setDialog(3);
			break;
		case R.id.pay_instruction:// 付款升级说明
			// OrderStatusDialog dialog4=new OrderStatusDialog(this, 8, 4);
			setDialog(4);
			break;
		case R.id.quyupingtai:// 区域平台
			// OrderStatusDialog dialog4=new OrderStatusDialog(this, 8, 4);
			setDialog(5);
			break;
		case R.id.ll_pay:// 点我升级

			break;
		case R.id.detail:// 查看详情
			if (appContext.isNetworkConnected()) {
				if (AppContext.isLogin) {
					startActivity(new Intent(this, WoDeHuiYuanActivity.class));
					// OrderStatusDialog dialog9 = new OrderStatusDialog(this,
					// 9);
					// dialog9.show();
					// Window window9 = dialog9.getWindow();
					// WindowManager.LayoutParams lp9 = window9.getAttributes();
					// lp9.width = AppContext.getScreenWidth() * 5 / 6;
					// lp9.height = AppContext.getScreenWidth();
					// dialog9.getWindow().setAttributes(lp9);
				} else {
					toast("亲,您还没有登录哦!");
					startActivity(new Intent(this, WebLoginActivity.class));
				}
			} else {
				toast(R.string.t_Connection);
			}
			break;
		case R.id.iv_friend:// 分享到朋友圈
			// fenxiang(2);
			// fenXiangImg(2);
			if (appContext.isNetworkConnected()) {
				wxinAvilible();
				if (UserInformation.isLogin()) {
					shareUrl(2);
					Toast.makeText(this, "朋友圈", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, "请登录", Toast.LENGTH_SHORT).show();

				}
			} else {
				toast(R.string.t_Connection);
			}
			break;
		case R.id.iv_weixin:// 分享到微信

			if (appContext.isNetworkConnected()) {
				wxinAvilible();

				if (UserInformation.isLogin()) {
					shareUrl(1);
					Toast.makeText(this, "微信", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, "请登录", Toast.LENGTH_SHORT).show();
				}
			} else {
				toast(R.string.t_Connection);
			}

			break;
		// 申请商家
		case R.id.tv_apply_shop:
			// String buy_server = getResources().getString(R.string.buyserver)
			// + "m/applyShop";
			// final Uri uri = Uri.parse(buy_server);
			// final Intent it = new Intent(Intent.ACTION_VIEW, uri);
			// startActivity(it);

			startActivity(new Intent(Huiyuandengji.this, WebApplyActivity.class));
			break;
		default:
			break;
		}
	}

	/** 处理 Scheme */

	private void mSchemeData() {
		Intent i_getvalue = getIntent();
		String action = i_getvalue.getAction();
		Uri uri = i_getvalue.getData();
		String objectId = "";
		if (uri != null && uri.getQueryParameter("objectId") != null) {
			objectId = uri.getQueryParameter("objectId");
			String objectType = "";
			if (Intent.ACTION_VIEW.equals(action)) {
				if (!uri.getScheme().equals("fgqqg")) {
					return;

				}
			}
			objectType = uri.getQueryParameter("objectType");
			if (objectId != null) {
				MyFormat.toSomeWhere(this, objectType, objectId,
						uri.getQueryParameter("forwardId"),
						uri.getQueryParameter("chain"), "");
			}
		}

	}

	private void wxinAvilible() {
		try {
			if (!CommonUtils.isWeixinAvilible(this)) {
				toast("您未安装微信,请安装最新版微信再试!");
				return;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 设置dialog属性 宽高
	 * 
	 * @param:grade:用户等级 1 普通会员,2商家功能版 ,3企业版,4付款升级说明,5区域平台
	 */
	private void setDialog(int grade) {
		OrderStatusDialog dialog1 = new OrderStatusDialog(this, 8, grade);
		dialog1.show();
		Window window1 = dialog1.getWindow();
		WindowManager.LayoutParams lp1 = window1.getAttributes();
		lp1.width = AppContext.getScreenWidth() * 4 / 5;
		lp1.height = AppContext.getScreenWidth() * 3 / 4;
		dialog1.getWindow().setAttributes(lp1);
	}

	/**
	 * url
	 * 
	 * @param type
	 *            :1 微信,2朋友圈
	 */
	private void shareUrl(int type) {
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = inviteUrl;
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = "风购全球";
		msg.description = "此地无银三百两,注册不可得!";
		Bitmap thumb = BitmapFactory.decodeResource(getResources(),
				R.drawable.shareurl);
		msg.thumbData = com.llg.help.Util.bmpToByteArray(thumb, true);

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		if (type == 1) {
			req.scene = SendMessageToWX.Req.WXSceneSession;
		} else if (type == 2) {
			req.scene = SendMessageToWX.Req.WXSceneTimeline;
		}
		// req.scene = isTimelineCb.isChecked() ?
		// SendMessageToWX.Req.WXSceneTimeline :
		// SendMessageToWX.Req.WXSceneSession;
		boolean reqd = api.sendReq(req);
		Log.d("my", "api.sendReq(req)" + reqd);
		// finish();
	}

	/**
	 * 文字
	 * 
	 * @param type
	 *            :1 微信,2朋友圈
	 */
	private void fenxiang(int type) {
		// 初始化WXTextObject
		WXTextObject textObject = new WXTextObject();
		textObject.text = "风购全球--http://www.fgqqg.com测试数据";
		// textObject.text="http://www.hao123.com--测试数据";

		// 初始化WXMediaMessage,用于android端向微信端传输文本
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = textObject;
		msg.description = "测试数据内容";

		// 创建一个用于请求的对象SendMessage用于与微信客户端进行交互
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		// transaction字段用于唯一标识一个请求
		req.transaction = String.valueOf(System.currentTimeMillis());
		req.message = msg;
		if (type == 1) {
			req.scene = SendMessageToWX.Req.WXSceneSession;
		} else if (type == 2) {
			req.scene = SendMessageToWX.Req.WXSceneTimeline;
		}
		// 发送给客户端
		boolean sendState = api.sendReq(req);// 发送成功返回true，否则返回false
		Log.d("my", "sendState:" + sendState);
	}

	/** 分享图片 type:1 微信,2朋友圈 */
	private void fenXiangImg(final int type) {
		new Thread() {
			public void run() {
				String url = "http://pic25.nipic.com/20121209/9252150_194258033000_2.jpg";
				// String url =
				// "http://img1.imgtn.bdimg.com/it/u=3812821659,2811790922&fm=21&gp=0.jpg";
				// String url = "https://www.baidu.com/img/bd_logo1.png";

				try {
					WXImageObject imgObj = new WXImageObject();
					imgObj.imageUrl = url;

					WXMediaMessage msg = new WXMediaMessage();
					msg.mediaObject = imgObj;

					Bitmap bmp = BitmapFactory.decodeStream(new URL(url)
							.openStream());
					Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150,
							true);
					bmp.recycle();
					msg.thumbData = com.llg.help.Util.bmpToByteArray(thumbBmp,
							true);

					SendMessageToWX.Req req = new SendMessageToWX.Req();
					req.transaction = buildTransaction("img");
					req.message = msg;

					if (type == 1) {
						req.scene = SendMessageToWX.Req.WXSceneSession;
					} else if (type == 2) {
						req.scene = SendMessageToWX.Req.WXSceneTimeline;
					}

					// 发送给客户端
					boolean sendState = api.sendReq(req);// 发送成功返回true，否则返回false
					Log.d("my", "sendState:" + sendState);

				} catch (Exception e) {
					e.printStackTrace();

				}

			}
		}.start();

	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	/** 剪裁图片 */
	private Bitmap compressionBitmap(Bitmap bitmap) {
		int wid = 1;
		int hei = 1;
		int newWid = 100;
		int newHei = 100;
		if (bitmap != null) {
			wid = bitmap.getWidth();
			hei = bitmap.getHeight();

		}
		// 设置缩放比
		float scaleW = (float) newWid / wid;
		float scaleH = (float) newHei / hei;
		// 取得要想缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleW, scaleH);
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, wid, hei, matrix,
				true);

		return newBitmap;
	}

	private String getTransaction() {
		final GetMessageFromWX.Req req = new GetMessageFromWX.Req(bundle);
		Log.d("my", "bundle" + bundle);
		return req.transaction;
	}
}
