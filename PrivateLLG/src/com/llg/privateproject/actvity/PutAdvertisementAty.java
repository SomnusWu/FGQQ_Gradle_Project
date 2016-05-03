package com.llg.privateproject.actvity;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.bjg.lcc.alipay.pay.PayActivity;
import com.bjg.lcc.jsonparser.ParseJson;
import com.bjg.lcc.privateproject.R;
import com.king.photo.activity.GalleryActivity;
import com.king.photo.util.Bimp;
import com.king.photo.util.FileUtils;
import com.king.photo.util.ImageItem;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.help.Util;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.camera.PopupSelectImage;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.utils.CommonUtils;
import com.llg.privateproject.utils.ImageTools;
import com.llg.privateproject.utils.LogManag;
import com.llg.privateproject.utils.StringUtils;
import com.llg.privateproject.view.CustomProgressSmall;
import com.llg.privateproject.view.DialogAuthentication;
import com.llg.privateproject.view.DialogAuthentication.onConfirmListener;
import com.llg.privateproject.view.MyGridView;
import com.llg.privateproject.view.ProductDetailEllipsisPoPuWindow;
import com.llg.privateproject.view.ProductDetailEllipsisPoPuWindow.MineSelectListenr;
import com.llg.privateproject.view.ProductDetailEllipsisPoPuWindow.SelecetListener;

/**
 * 投放广告
 * 
 * @author cc
 * 
 */
public class PutAdvertisementAty extends BaseActivity implements
		SelecetListener, MineSelectListenr, Serializable {

	private String filename;

	private List<String> list;
	private ProductDetailEllipsisPoPuWindow window;
	@ViewInject(R.id.tv_advertisement)
	private TextView tvAdvertisement;

	/**
	 * 投放酷币总数
	 */
	@ViewInject(R.id.edt_co_number)
	private EditText edtCoNumber;
	/**
	 * 每人可领取CO币个数
	 */
	@ViewInject(R.id.edt_draw_number)
	private EditText edtDrawNumber;
	/**
	 * 每人随机领取现金最小值
	 * 
	 * 
	 */
	@ViewInject(R.id.edt_random_small)
	private EditText edtRandomSmall;
	/**
	 * 投入现金总额
	 */
	@ViewInject(R.id.edt_money_total)
	private EditText edtMoneyTotal;
	/**
	 * 每人随机领取现金最大值
	 */
	@ViewInject(R.id.edt_random_big)
	private EditText edtRandomBig;
	/**
	 * 广告标题
	 */
	@ViewInject(R.id.edt_title)
	private EditText edtTitle;

	@ViewInject(R.id.edt_timu)
	private EditText edtTimu;

	@ViewInject(R.id.edt_lianjie)
	private EditText edtLianjie;

	@ViewInject(R.id.edt_confirm)
	private EditText edtConfirm;

	@ViewInject(R.id.edt_error_one)
	private EditText edtErrorOne;

	@ViewInject(R.id.edt_error_two)
	private EditText edtErrorTwo;

	@ViewInject(R.id.edt_error_three)
	private EditText edtErrorThree;

	@ViewInject(R.id.ly_load)
	private LinearLayout lyLoad;

	@ViewInject(R.id.ly_lianjie)
	private LinearLayout lyLianjie;

	@ViewInject(R.id.ly_fenmian)
	private LinearLayout lyFenmian;

	@ViewInject(R.id.tv_more)
	private TextView tvMore;

	@ViewInject(R.id.tv_kind)
	private TextView tvKind;

	@ViewInject(R.id.tv_push)
	private TextView tvPush;

	@ViewInject(R.id.iv_fengmianguanggao)
	private ImageView ivFenmian;

	private File file;
	@ViewInject(R.id.noScrollgridview)
	private MyGridView noScrollgridview;
	private GridAdapter adapter1;
	private View parentView;
	private String imgID;// 图片ID
	private String coverImageId;// 封面图片ID
	private String adInfoID;// 广告ID
	private String adLoacationName;// 广告位分类名字
	private String stredtMoneyTotal;// 投入现金总值
	private String stredtCoNumber;// 投入CO总值
	private String title;// 投入CO总值

	/**
	 * 上传广告图片Request id
	 */
	private static final int REQUEST_SELECT_IMG_ID = 10001;
	/**
	 * 上传封面图片Request id
	 */
	private static final int REQUEST_SELECT_FM_IMG_ID = 10002;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(
				R.layout.aty_put_advertisement, null);
		setContentView(parentView);
		ViewUtils.inject(this);
		initData();
		initUI();
		validAdvertisement(UserInformation.getAccess_token());

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (Bimp.tempSelectBitmap.size() > 0) {
			adapter1.notifyDataSetChanged();
		}
	}

	private void initData() {
		adLoacationName = "APP_START_PAGE";
		list = new ArrayList<String>();
		list.add("app投放广告");
		list.add("app");
	}

	/**
	 * 初始化界面
	 */
	private void initUI() {
		noScrollgridview = (MyGridView) findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter1 = new GridAdapter(this);
		adapter1.update();
		noScrollgridview.setAdapter(adapter1);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == Bimp.tempSelectBitmap.size()) {
					Log.i("ddddddd", "----------");
				} else {
					Intent intent = new Intent(PutAdvertisementAty.this,
							GalleryActivity.class);
					intent.putExtra("position", "1");
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});

		tvAdvertisement.setText("app开始页滑动广告");
		tvKind.setText("图片广告");
		tvPush.setText("不进行推送");
		lyLianjie.setVisibility(View.GONE);
		tvMore.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); // 下划线
		LayoutParams params = (LayoutParams) noScrollgridview.getLayoutParams();
		params.width = LayoutParams.WRAP_CONTENT;
		params.height = Util.dip2px(this, 100);
		noScrollgridview.setLayoutParams(params);
		ivFenmian.setLayoutParams(params);

		ivFenmian.setMaxWidth(AppContext.getScreenWidth());
		ivFenmian.setMaxHeight(AppContext.getScreenWidth() * 30);
		initAutoLoading(lyLoad);
		autoLoading.setClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				validAdvertisement(UserInformation.getAccess_token());
			}
		});
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
	}

	@OnClick({ R.id.iv_back, R.id.tv_advertisement, R.id.tv, R.id.btn_upload,
			R.id.tv_confirm, R.id.tv_more, R.id.btn_upload_fengmian,
			R.id.tv_push, R.id.tv_kind })
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.tv_more:
			intent = new Intent(this, MoreFunction.class);
			startActivity(intent);
			break;
		case R.id.tv_confirm:// 确认投放

			/**判空处理**/
			boolean verify = verifyData();
			if (verify) {
				/**
				 * 先上传图片 得到id
				 */
				if (Bimp.tempSelectBitmap.size() > 0) {
					customProgressSmall.setMessage("正在上传图片中...");
					customProgressSmall.show();
					uploadPhoto(file, UserInformation
							.getAccess_token(), this);
				} else {
					toast("请上传广告图片...");
					return;
				}
			}
			// 回传图片ID
			setRefreshListener(new RefreshImgID() {
				@Override
				public void refreshImgID(String _imgID, String name) {
					// TODO Auto-generated method stub
					// 得到id
					// PutAdvertisementAty.this.imgID = imgID;
					// String[] ids = imgID.split(",");
					imgID = _imgID;
					coverImageId = name;
					/**
					 * 判断是否上传成功 得到id
					 */
					if (!StringUtils.isEmpty(imgID)) {
						/**
						 * 提交 //上传成功
						 */
						customProgressSmall.setMessage("正在保存广告中...");
						customProgressSmall.show();
						uploadAdvertisement(UserInformation.getAccess_token());
					} else {// 上传失败 ... 请重试..
						toast("请上传广告图片...");
						return;
					}

				}
			});

			break;
		case R.id.btn_upload:// 上传广告图片
			intent = new Intent(this, PopupSelectImage.class);
			intent.putExtra("type_s", "001");
			// (1)com.llg.privateproject.camera.PopupSelectImage
			// (2)com.zhy.imageloader.MainActivityDemoPhoto
			startActivityForResult(intent, REQUEST_SELECT_IMG_ID);
			break;
		case R.id.btn_upload_fengmian:// 上传封面广告图片
			intent = new Intent(this, PopupSelectImage.class);
			intent.putExtra("type_s", "002");
			startActivityForResult(intent, REQUEST_SELECT_FM_IMG_ID);
			break;
		case R.id.tv_advertisement:
			window = new ProductDetailEllipsisPoPuWindow(this, this, 4,
					tvAdvertisement);
			window.setOnMineSelectListener(this);
			break;
		case R.id.tv_kind:
			window = new ProductDetailEllipsisPoPuWindow(this, this, 7, tvKind);
			window.setOnMineSelectListener(this);
			break;
		case R.id.tv_push:
			window = new ProductDetailEllipsisPoPuWindow(this, this, 8, tvPush);
			window.setOnMineSelectListener(this);
			break;

		default:
			break;
		}
	}

	/**
	 * 正则验证
	 */
	private boolean  verifyData() {
		boolean verify = false;
		/** 获取广告图片的ID 然后根据ID在 */
		if (adLoacationName == null) {
			toast("请选择广告位种类");
			return verify;
		}
		title = getStr(edtTitle);
		if (TextUtils.isEmpty(title)) {
			toast("请输入广告标题");
			return verify;
		}
		if (title.length() > 32) {
			toast("广告标题不得超过32个字符");
			return verify;
		}
		if (tvKind.getText().toString().equals("图片+网页链接地址广告")
				&& TextUtils.isEmpty(getStr(edtLianjie))) {
			toast("广告链接地址不能为空");
			return verify;
		}
		// if (TextUtils.isEmpty(imgID)) {
		// toast("请上传广告图片");
		// return;
		// }
		if (!VarifyTimu()) {
			return verify;
		}
		stredtMoneyTotal = getStr(edtMoneyTotal);
		String stredtRandomSmall = getStr(edtRandomSmall);
		String stredtRandomBig = getStr(edtRandomBig);
		String stredtDrawNumber = getStr(edtDrawNumber);
		stredtCoNumber = getStr(edtCoNumber);
		if (stredtMoneyTotal.length() > 0) {
			if (!Util.isDouble(stredtMoneyTotal)) {
				toast("总现金小数点后最多只能输入两位");
				return verify;
			}
			if (stredtRandomSmall.length() == 0
					|| stredtRandomBig.length() == 0) {
				toast("请输入现金领取值");
				return verify;
			}
			if (!Util.isDouble(stredtRandomSmall)
					|| !Util.isDouble(stredtRandomBig)) {
				toast("现金领取值小数点后最多只能输入两位小数");
				return verify;
			}
			try {
				Double smallMoney = Double.parseDouble(stredtRandomSmall);
				Double bigMoney = Double.parseDouble(stredtRandomBig);
				Double MoneyTotal = Double.parseDouble(stredtMoneyTotal);
				if (bigMoney - smallMoney < 0
						|| (MoneyTotal - smallMoney < 0 || MoneyTotal
								- bigMoney < 0)) {
					toast("请输入合法现金领取值");
					return verify;
				}
				if (smallMoney < 0.1) {
					toast("每人领取现金最低金额0.1元");
					return verify;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		if (stredtCoNumber.length() > 0) {
			if (stredtDrawNumber.length() == 0) {
				toast("请输入酷币领取值");
				return verify;
			}
			if ((stredtCoNumber.substring(0, 1).equals("0") || stredtDrawNumber
					.substring(0, 1).equals("0"))) {
				toast("请输入合法酷币值");
				return verify;
			}
			try {
				int CoNumber = Integer.parseInt(stredtCoNumber);
				int DrawNumber = Integer.parseInt(stredtDrawNumber);
				if (CoNumber - DrawNumber < 0) {
					toast("请输入合法酷币领取值");
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		if (!VarifyTimu()) {
			if (!TextUtils.isEmpty(getStr(edtTimu))
					&& (stredtCoNumber.length() == 0 && stredtMoneyTotal
							.length() == 0)) {
				toast("设置了红包抽奖题目,请投放红包");
				return verify;
			}
		}
		return true;
//		customProgressSmall.setMessage("正在保存广告中...");
//		customProgressSmall.show();
//		uploadAdvertisement(UserInformation.getAccess_token());
	}

	/**
	 * 验证抽奖题目
	 * 
	 * @return
	 */
	private boolean VarifyTimu() {
		if (!TextUtils.isEmpty(getStr(edtTimu))) {
			if (TextUtils.isEmpty(getStr(edtConfirm))) {
				toast("请输入正确答案");
				return false;
			}
			if (TextUtils.isEmpty(getStr(edtErrorOne))) {
				toast("至少输入一个错误答案1");
				return false;
			}
		}
		return true;
	}

	/**
	 * 上传广告
	 */
	private void uploadAdvertisement(String access_token) {
		RequestParams params = new RequestParams();
		uploadParams(params, access_token);
		params.addHeader("X-Requested-With", "XMLHttpRequest");
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
		"m/ad/saveNormalAd", params, new HttpCallback() {

			@Override
			public void onError(String msg) {
				// TODO Auto-generated method stub
				customProgressSmall.dismiss();
				Toast.makeText(PutAdvertisementAty.this, "上传异常",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onBack(JSONObject json) {
				// TODO Auto-generated method stub
				Map<String, Object> map = ParseJson.getParseJson()
						.parseIsSuccess(json);

				LogManag.d("json-->", json.toString());
				customProgressSmall.dismiss();
				if ((Boolean) map.get("isSuccess")) {
					try {
						adInfoID = json.getJSONObject("obj").optString("id");
						if ((json.getJSONObject("obj").optString("adStatus"))
								.equals("3")) {
							initDialog();
						} else {
							toast("投放成功");
							Bimp.tempSelectBitmap.clear();
							//投放成功后,删除当初创建寻访图片的img_temp文件夹
							ImageTools.deleteFile(CommonUtils.createSDCardDir() + "img_temp/");
							finish();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					if (map.get("errorCode").equals("NOT_LOGIN")) {
						setRefreshListtener(new Refresh() {

							@Override
							public void refreshRequst(String access_token) {
								// TODO Auto-generated method stub
								uploadAdvertisement(access_token);
							}
						});
						RefeshToken();
					} else {
						toast(json.optString("msg"));
						Toast.makeText(PutAdvertisementAty.this,
								json.optString("msg"), 8 * 1000).show();
					}
				}
			}

			/**
			 * 初始化dialog
			 */
			private void initDialog() {
				final DialogAuthentication dialogAuthentication = new DialogAuthentication(
						PutAdvertisementAty.this);
				dialogAuthentication
						.setOnConfirmListener(new onConfirmListener() {

							@Override
							public void request() {
								// TODO Auto-generated method stub
								dialogAuthentication.dismiss();
								customProgressSmall = CustomProgressSmall
										.initDialog(PutAdvertisementAty.this,
												"正在支付中", true,
												new OnCancelListener() {

													@Override
													public void onCancel(
															DialogInterface arg0) {
														// TODO
														// Auto-generated
														// method stub
														customProgressSmall
																.dismiss();
													}
												});
								customProgressSmall.show();
								requesPay(UserInformation.getAccess_token(),
										stredtMoneyTotal);
							}
						});
				dialogAuthentication.initUI();
				dialogAuthentication.setTitle("支付");
				String count = StringUtils.isEmpty(stredtCoNumber) ? 0 + "" :stredtCoNumber;
				dialogAuthentication.setMessage("本次需要支付现金" + stredtMoneyTotal
						+ "元," + "需要支付酷币" + count + "个,"
						+ "账户酷币余额不足折算成现金支付,10个酷币等于1元现金");
				dialogAuthentication.show();
			}
		});
	}

	/**
	 * 上传参数
	 * 
	 * @param params
	 */
	private void uploadParams(RequestParams params, String access_token) {
		params.addQueryStringParameter("access_token", access_token);
		params.addQueryStringParameter("title", title);
		// params.addQueryStringParameter("imageId", coverImageId);// 图片ID
		params.addBodyParameter("moreImageIds", imgID);// 广告内容

		if (!TextUtils.isEmpty(coverImageId)) {
			params.addQueryStringParameter("coverImageId", coverImageId);// 封面图片ID
		}
		if (!TextUtils.isEmpty(getStr(edtTimu))) {
			params.addQueryStringParameter("question", getStr(edtTimu));// 题目
			params.addQueryStringParameter("correctAnswer", getStr(edtConfirm));// 正确答案
			params.addQueryStringParameter("errorAnswer1", getStr(edtErrorOne));// 错误答案1
			if (!TextUtils.isEmpty(getStr(edtErrorTwo))) {
				params.addQueryStringParameter("errorAnswer2",
						getStr(edtErrorTwo));// 错误答案2
			}
			if (!TextUtils.isEmpty(getStr(edtErrorThree))) {
				params.addQueryStringParameter("errorAnswer3",
						getStr(edtErrorThree));// 错误答案3
			}
		}
		if (tvPush.getText().toString().equals("不进行推送")) {
			params.addQueryStringParameter("isPush", "N");
		} else {
			params.addQueryStringParameter("isPush", "Y");
		}
		if (tvKind.getText().toString().equals("图片广告")) {
			params.addQueryStringParameter("objectType", "0");// 广告位种类
		} else if (tvKind.getText().toString().equals("图片+网页链接地址广告")) {
			params.addQueryStringParameter("objectType", "4");
		}
		if (tvKind.getText().toString().equals("图片+网页链接地址广告")) {
			params.addQueryStringParameter("objectId", getStr(edtLianjie));// 广告位
		}
		params.addQueryStringParameter("adLocationName", adLoacationName);// 广告位
		if (getStr(edtMoneyTotal).length() > 0) {
			params.addQueryStringParameter("money", getStr(edtMoneyTotal));// 现金总额
			params.addQueryStringParameter("moneyMin", getStr(edtRandomSmall));// 最小获取现金值
			params.addQueryStringParameter("moneyMax", getStr(edtRandomBig));// 最大获取现金值
		}
		if (getStr(edtCoNumber).length() > 0) {
			params.addQueryStringParameter("coAmount", getStr(edtCoNumber));// 投放co币总额
			params.addQueryStringParameter("coMin", getStr(edtDrawNumber));// 每人可领取co币最少值
		}

		LogManag.d("aaa", params.toString());
	}

	/**
	 * 验证是否能发布广告
	 */
	private void validAdvertisement(String access_token) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", access_token);
		params.addHeader("X-Requested-With", "XMLHttpRequest");
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,

		"m/ad/validPublishAuth", params, new HttpCallback() {

			@Override
			public void onError(String msg) {
				// TODO Auto-generated method stub
				autoLoading.showExceptionLayout();
			}

			@Override
			public void onBack(JSONObject json) {
				// TODO Auto-generated method stub
				Map<String, Object> map = ParseJson.getParseJson()
						.parseIsSuccess(json);
				if ((Boolean) map.get("isSuccess")) {
					try {
						JSONObject jsonObject = json.getJSONObject("obj");
						String isSuccess = jsonObject.optString("validResult");
						String msg = jsonObject.optString("errorMsg");
						if (isSuccess.equals("N")) {
							toast(msg);
							PutAdvertisementAty.this.finish();
						} else {
							autoLoading.hideAll();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					if (map.get("errorCode").equals("NOT_LOGIN")) {
						setRefreshListtener(new Refresh() {

							@Override
							public void refreshRequst(String access_token) {
								// TODO Auto-generated method stub
								validAdvertisement(access_token);
							}
						});
						RefeshToken();
					}
				}
			}
		});
	}

	private static final int TAKE_PICTURE = 0x000001;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case TAKE_PICTURE:
			if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {
				String fileName = String.valueOf(System.currentTimeMillis());
				Bitmap bm = (Bitmap) data.getExtras().get("data");
				FileUtils.saveBitmap(bm, fileName);
				ImageItem takePhoto = new ImageItem();
				takePhoto.setBitmap(bm);
				Bimp.tempSelectBitmap.add(takePhoto);
			}
			break;
		}
		BitmapUtils bitmapUtils = new BitmapUtils(this);
		if (requestCode == REQUEST_SELECT_IMG_ID && resultCode == RESULT_OK
				&& data != null) {//REQUEST_SELECT_IMG_ID----广告图片
			if (data.getStringExtra("ImageFilePath") == null) {
				return;
			}
			file = new File(data.getStringExtra("ImageFilePath"));
			if (file != null) {
				// bitmapUtils.display(noScrollgridview,
				// file.getAbsolutePath());
				String url = data.getStringExtra("ImageFilePath");

				// Bitmap bm = (Bitmap) data.getExtras().get("ImageFilePath");
				Bitmap bm;
				try {
					bm = Bimp.revitionImageSize(url);
					ImageItem takePhoto = new ImageItem();
					takePhoto.setBitmap(bm);
					takePhoto.setImagePath(url);
					Bimp.tempSelectBitmap.add(takePhoto);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			// // 回调图片ID
			// setRefreshListener(new RefreshImgID() {
			// @Override
			// public void refreshImgID(String imgID,String name) {
			// // TODO Auto-generated method stub
			// PutAdvertisementAty.this.imgID = imgID;
			// }
			// });
		} else if (requestCode == REQUEST_SELECT_FM_IMG_ID
				&& resultCode == RESULT_OK && data != null) {//REQUEST_SELECT_FM_IMG_ID---封面广告图片
			if (data.getStringExtra("ImageFilePath") == null) {
				return;
			}
			file = new File(data.getStringExtra("ImageFilePath"));
			if (file != null) {
				bitmapUtils.display(ivFenmian, file.getAbsolutePath());
			}
			/**
			 * 赋值封面广告的url
			 */
			ImageItem item = new ImageItem();
			item.setImagePath(data.getStringExtra("ImageFilePath"));
			Bimp.setmFMImgItem(item);
			// 回调封面图片ID
			// setRefreshListener(new RefreshImgID() {
			// @Override
			// public void refreshImgID(String imgID) {
			// // TODO Auto-generated method stub
			// PutAdvertisementAty.this.coverImageId = imgID;
			// }
			// });
		}
		// if (file != null) {
		// uploadPhoto(file,
		// AppContext.getUserInformation().getAccess_token(), this);
		// }
	}

	/**
	 * 请求去支付
	 */
	private void requesPay(String access_token, final String money) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", access_token);
		params.addQueryStringParameter("adInfoId", adInfoID);
		AppContext.getHtmlUitls().xUtilsm(PutAdvertisementAty.this,
				HttpMethod.POST, "m/order/saveAdOrder", params,
				new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						// autoLoading.showExceptionLayout();
						customProgressSmall.dismiss();
						Toast.makeText(PutAdvertisementAty.this, "支付失败",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onBack(JSONObject json) {
						Log.d("my", "" + json);
						// TODO Auto-generated method stub
						ParseJson parseJson = ParseJson.getParseJson();
						Map<String, Object> map = parseJson
								.parseIsSuccess(json);
						if ((Boolean) map.get("isSuccess")) {
							String id = null;
							String code = null;
							try {
								id = json.getJSONObject("obj").optString("id");
								code = json.getJSONObject("obj").optString(
										"code");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							customProgressSmall.dismiss();
							Intent intent = new Intent(
									PutAdvertisementAty.this, PayActivity.class);
							intent.putExtra("price", money);
							intent.putExtra("code", code);
							intent.putExtra("id", id);
							finish();
							startActivity(intent);
						} else {
							if (map.get("errorCode").equals("NOT_LOGIN")) {
								setRefreshListtener(new Refresh() {

									@Override
									public void refreshRequst(
											String access_token) {
										// TODO Auto-generated method stub
										requesPay(access_token, money);
									}
								});
								RefeshToken();
							} else {
								Toast.makeText(PutAdvertisementAty.this,
										json.optString("msg"),
										Toast.LENGTH_LONG).show();
							}
						}
					}
				});
	}

	@Override
	public void setMessage(String message, int type) {
		// TODO Auto-generated method stub
		if (type == 4) {
			tvAdvertisement.setText(message);
		} else if (type == 7) {
			tvKind.setText(message);
			if (message.equals("图片广告")) {
				lyLianjie.setVisibility(View.GONE);
			} else {
				lyLianjie.setVisibility(View.VISIBLE);
			}
		} else if (type == 8) {
			tvPush.setText(message);
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void setAdLocationName(String str) {
		// TODO Auto-generated method stub
		adLoacationName = str;
		if (str.equals("APP_HOME_OPEN_APP")) {
			lyFenmian.setVisibility(View.GONE);
		} else {
			lyFenmian.setVisibility(View.VISIBLE);
		}
		Log.i("tag", adLoacationName + "====adLocationName=======");
	}

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			if (Bimp.tempSelectBitmap.size() == 9) {
				return 9;
			}
			return (Bimp.tempSelectBitmap.size());
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// if (position == Bimp.tempSelectBitmap.size()) {
			// holder.image.setImageBitmap(BitmapFactory.decodeResource(
			// getResources(), R.drawable.icon_addpic_unfocused));
			// if (position == 9) {
			// holder.image.setVisibility(View.GONE);
			// }
			// } else {
			holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position)
					.getBitmap());
			// }

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter1.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.tempSelectBitmap.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}
				}
			}).start();
		}
	}
}
