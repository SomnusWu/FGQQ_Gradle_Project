package com.llg.privateproject.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.actvity.LoginActivity;
import com.llg.privateproject.actvity.OrderClearActivity;
import com.llg.privateproject.actvity.ProductDetailActivity;
import com.llg.privateproject.actvity.ShopActivity;
import com.llg.privateproject.actvity.ShopCartActivity;
import com.llg.privateproject.actvity.ShopMap;
import com.llg.privateproject.actvity.WebLoginActivity;
import com.llg.privateproject.adapter.FormatAdapter;
import com.llg.privateproject.entities.ProdSpecItemBean;
import com.llg.privateproject.entities.ShoppingCartEntity;
import com.llg.privateproject.entities.ShoppingCartEntity.Store;
import com.llg.privateproject.entities.ShoppingCartEntity.Store.CartSpec;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseFragment.Refresh;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.utils.CommonUtils;
import com.smartandroid.sa.aysnc.Log;
import com.bjg.lcc.privateproject.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 产品尺寸对话框 yh 2015.6.21
 * 
 * */
public class ProductDetailSizeDialog extends Dialog implements
		android.view.View.OnClickListener, OnGestureListener {
	/** 返回按钮 */
	private ImageView iv_cancel;
	/** 产品图片 */
	private ImageView iv_pic;
	/** 产品价格 */
	private TextView tv_price;
	/** 产品库存 */
	private TextView tv_desc;
	/** 选择尺寸 */
	private TextView tv_size;
	/** 减少数量按钮 */
	private ImageView jian;
	/** 购买数量 */
	private TextView bay_count;
	/** 增加数量 */
	private ImageView plus;
	/** 立即购买 */
	private TextView now_buy;
	/** 加入购物车 */
	private TextView to_collect;
	/** 库存数 */
	private int count1;
	/** 选择数量 */
	private int count2;
	/** 手势检测器 */
	private GestureDetector detector;
	private Context context;
	private int num;
	private Map<String, Object> map;
	private LinearLayout guige_ll;
	/** 组合标签列表 */
	private List<Map<String, Object>> list1;
	/** 标签列表 */
	private List<Map<String, Object>> list2;
	private String img;
	/** 自选组合规格ids */
	private String ids0;
	/** 装载标签 */
	private String[] index;
	AppContext appContext;
	/** 规格id */
	private String specid = "";
	/** 组合规格ids */
	private String ids = "";
	private String price = "";
	private String originalPrice = "";
	private String count = "";
	private String names = "";
	private Double LAT;
	private Double LNG;
	private String name = "";
	TextView address;
	SepcCallback spCallback;
	ShoppingCartEntity shoppingCartEntity;

	public interface SepcCallback {
		/** 规格id */
		void setSepc(String specId, String originalPrice, String price,
					 int count, String names);
	}

	Handler hanlder = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:// 立即支付
				if (appContext.isNetworkConnected()) {
					if (UserInformation.isLogin()) {

						getCart("forceGetCart", UserInformation
								.getAccess_token());
					} else {
						Intent intent = new Intent(context,
								WebLoginActivity.class);
						context.startActivity(intent);
					}
				}
				break;

			case 2:// 跳转到结算页面
				if (shoppingCartEntity.allPrice > 0) {

					context.startActivity(new Intent(context,
							OrderClearActivity.class));
				} else {
					Toast.makeText(context, "支付金额必须大于零", Toast.LENGTH_SHORT)
							.show();
				}
				break;
			case 3:// 跳到地图界面
				Intent it = new Intent(context, ShopMap.class);
				it.putExtra("LAT", LAT);
				it.putExtra("LNG", LNG);
				it.putExtra("name", map.get("name").toString());
				context.startActivity(it);
				break;
			}
		}
	};

	/** num:,2 店铺简介 */
	public ProductDetailSizeDialog(Context context, int num,
			Map<String, Object> map) {
		super(context, R.style.agreemdialog);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.num = num;
		this.map = map;

	}

	/** num:1,商品规格 */
	public ProductDetailSizeDialog(Context context, int num,
			List<Map<String, Object>> list1, List<Map<String, Object>> list2,
			String img, SepcCallback sepcCallback) {
		super(context, R.style.agreemdialog);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.num = num;
		this.list1 = list1;
		this.list2 = list2;
		this.img = img;
		this.spCallback = sepcCallback;
		index = new String[list2.size()];

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (num == 1) {
			init();
		} else if (num == 2) {
			initShopintroduction();
		}
	}

	/** 商品规格选择 */
	void init() {
		setContentView(R.layout.product_detail_size);

		guige_ll = (LinearLayout) findViewById(R.id.guige_ll);
		iv_cancel = (ImageView) findViewById(R.id.iv_cancle);
		iv_pic = (ImageView) findViewById(R.id.iv_pic);
		tv_price = (TextView) findViewById(R.id.tv_price);
		tv_desc = (TextView) findViewById(R.id.tv_desc);
		tv_size = (TextView) findViewById(R.id.tv_size);
		jian = (ImageView) findViewById(R.id.jian);
		bay_count = (TextView) findViewById(R.id.bay_count);
		plus = (ImageView) findViewById(R.id.plus);
		now_buy = (TextView) findViewById(R.id.now_buy);
		to_collect = (TextView) findViewById(R.id.to_collect);

		iv_cancel.setOnClickListener(this);
		jian.setOnClickListener(this);
		plus.setOnClickListener(this);
		now_buy.setOnClickListener(this);
		to_collect.setOnClickListener(this);
		MyFormat.setBitmap(context, iv_pic, img, 0, 0);
		if (list1 != null && list1.size() > 0) {
			Map<String, Object> item = list1.get(0);

			price = item.get("price").toString();
			count = item.get("count").toString();
			names = item.get("names").toString();
			specid = item.get("id").toString();
			tv_price.setText(price);
			tv_desc.setText(count);
			tv_size.setText(names);
			ids = ids0 = item.get("ids").toString();

		}
		addSpec(list2);
		count1 = Integer.parseInt(tv_desc.getText().toString());
		count2 = Integer.parseInt(bay_count.getText().toString());
		detector = new GestureDetector(context, this);
	}

	TextView phone;
	ImageView myqrcode;

	/** 店铺简介 */
	void initShopintroduction() {
		setContentView(R.layout.shopintroduction);
		Log.d("my", "map.toString" + map.toString());
		detector = new GestureDetector(context, this);
		iv_cancel = (ImageView) findViewById(R.id.iv_cancle);
		ImageView shop_head = (ImageView) findViewById(R.id.shop_head);
		LayoutParams params = (LayoutParams) shop_head.getLayoutParams();
		params.width = AppContext.getScreenWidth() / 8;
		params.height = AppContext.getScreenWidth() / 8;
		shop_head.setLayoutParams(params);
		ImageView onlineservice = (ImageView) findViewById(R.id.onlineservice);
		ImageView telphone = (ImageView) findViewById(R.id.telphone);
		myqrcode = (ImageView) findViewById(R.id.myqrcode);
		ImageView qrcode = (ImageView) findViewById(R.id.qrcode);

		TextView shopname = (TextView) findViewById(R.id.shopname);
		if (map.get("name").equals(null)) {
			shopname.setText("");
		} else {
			shopname.setText(map.get("name").toString());
		}
		MyFormat.setBitmap(context, shop_head, map.get("logo").toString(),
				params.width, params.height);

		TextView care = (TextView) findViewById(R.id.care);
		TextView dsc = (TextView) findViewById(R.id.dsc);
		if (!map.get("mainSell").equals(null)
				&& !map.get("mainSell").equals("null")) {
			dsc.setText(map.get("mainSell").toString());
		} else {
			dsc.setText("");
		}
		TextView wuliuscore = (TextView) findViewById(R.id.wuliuscore);
		if (map.get("logistics") != null) {
			wuliuscore.setText(map.get("logistics").toString());
		}

		TextView serverscore = (TextView) findViewById(R.id.serverscore);

		if (map.get("serve") != null) {
			serverscore.setText(map.get("serve").toString());
		}
		TextView productscore = (TextView) findViewById(R.id.productscore);

		if (map.get("information") != null) {
			productscore.setText(map.get("information").toString());
		}
		phone = (TextView) findViewById(R.id.phone);
		if (map.get("phone") != null) {
			phone.setText(map.get("phone").toString());

		}
		TextView shopname1 = (TextView) findViewById(R.id.shopname1);
		if (map.get("name") != null) {
			shopname1.setText(map.get("name").toString());
		}
		address = (TextView) findViewById(R.id.address);
		if (map.get("address") != null) {
			address.setText(map.get("address").toString());
		}
		address.setOnClickListener(this);
		TextView createdate = (TextView) findViewById(R.id.createdate);
		if (map.get("createDate") != null) {
			createdate.setText(map.get("createDate").toString());
		} else {
			createdate.setText("暂无");
		}
		// dsc.setMovementMethod(ScrollingMovementMethod.getInstance());
		iv_cancel.setOnClickListener(this);
		care.setOnClickListener(this);
		onlineservice.setOnClickListener(this);
		telphone.setOnClickListener(this);
		qrcode.setOnClickListener(this);
		myqrcode.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.address:
			RequestParams params = new RequestParams();
			params.addQueryStringParameter("shopId", map.get("id").toString());
			AppContext.getHtmlUitls().xUtilsm(context, HttpMethod.POST,
					"common/getShopGeo", params, new HttpCallback() {

						@Override
						public void onError(String msg) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onBack(JSONObject json) {
							// TODO Auto-generated method stub
							try {
								if (json.getBoolean("success")) {
									LAT = json.getJSONObject("obj").getDouble(
											"LAT");
									LNG = json.getJSONObject("obj").getDouble(
											"LNG");
									hanlder.sendEmptyMessage(3);
								} else {
									Toast.makeText(context, "获取店铺位置失败",
											Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});

			break;
		case R.id.iv_cancle:// 隐藏
			if (isShowing()) {
				cancel();
			}
			break;
		case R.id.jian:// 数量减
			if (count2 >= 1) {
				if (spCallback != null) {
					if (ids0 == ids) {
						bay_count.setText("" + --count2);
						spCallback.setSepc(specid, originalPrice, price,
								count2, names);
					} else {
						Toast.makeText(getContext(), "暂无该规格商品",
								Toast.LENGTH_SHORT).show();
					}
				}

			} else {
				Toast.makeText(getContext(), "数量为零", Toast.LENGTH_SHORT).show();

			}
			break;
		case R.id.plus:// 数量加

			if (count1 > count2) {
				if (spCallback != null) {
					if (ids0 == ids) {
						bay_count.setText("" + ++count2);
						spCallback.setSepc(specid, originalPrice, price,
								count2, names);
					} else {
						Toast.makeText(getContext(), "暂无该规格商品",
								Toast.LENGTH_SHORT).show();
					}
				}

			} else {
				Toast.makeText(getContext(), "库存不足", Toast.LENGTH_SHORT).show();

			}
			break;
		case R.id.now_buy:// 立即购买
			// isHashProduct(specid);
			if (appContext == null) {
				appContext = (AppContext) context.getApplicationContext();
			}
			if (!UserInformation.isLogin()) {
				Toast.makeText(context, "未登录", Toast.LENGTH_SHORT).show();
				context.startActivity(new Intent(context,
						WebLoginActivity.class));
				return;
			}
			// if(count2<1){
			// Toast.makeText(context, "请添加商品数量", Toast.LENGTH_SHORT).show();
			// return;
			// }
			// if (appContext.isNetworkConnected()) {
			// addCart(count2,1);
			// } else {
			// Toast.makeText(context, R.string.t_Connection,
			// Toast.LENGTH_SHORT).show();
			// }
			context.startActivity(new Intent(context, ShopCartActivity.class));
			break;
		case R.id.to_collect:// 加入购物车
			if (appContext == null) {
				appContext = (AppContext) context.getApplicationContext();
			}
			if (!UserInformation.isLogin()) {
				Toast.makeText(context, "未登录", Toast.LENGTH_SHORT).show();
				context.startActivity(new Intent(context,
						WebLoginActivity.class));
				return;
			}
			if (appContext.isNetworkConnected()) {
				if (count2 < 1) {
					Toast.makeText(context, "请添加商品数量", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				addCart(count2, 2);
			} else {
				Toast.makeText(context, R.string.t_Connection,
						Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.care:// 店铺关注

			if (((AppContext) context.getApplicationContext())
					.isNetworkConnected()) {
				attion();
			} else {
				Toast.makeText(context, R.string.t_Connection,
						Toast.LENGTH_SHORT).show();

			}

			break;
		case R.id.onlineservice:// 在线客服
			Toast.makeText(getContext(), "在线客服", Toast.LENGTH_SHORT).show();

			break;
		case R.id.telphone:// 店铺电话
			if (phone.getText().length() > 8) {
				Intent intnt = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ map.get("phone").toString()));
				context.startActivity(intnt);
			} else {
				Toast.makeText(getContext(), "该商家暂无电话", Toast.LENGTH_SHORT)
						.show();
			}

			break;
		case R.id.qrcode:// 我的二维码
			myqrcode.setVisibility(View.VISIBLE);
			createImage(context.getResources().getString(R.string.buyserver)
					+ "common/toApp?objectType=1&objectId="
					+ map.get("id").toString(), myqrcode);

			break;

		case R.id.myqrcode:// 影藏二维码
			myqrcode.setVisibility(View.GONE);
			break;
		}
	}

	String text;
	int QR_WIDTH = 400;
	int QR_HEIGHT = 400;

	// 生成QR图
	private void createImage(String inviteUrl, ImageView v) {
		text = "风购全球网,给你无限精彩";

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
			BitMatrix bitMatrix = new QRCodeWriter().encode(inviteUrl,
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

	/*** 关注 */
	private void attion() {
		if (!AppContext.isLogin) {
			Toast.makeText(context, "关注前请先登录", Toast.LENGTH_SHORT).show();
			context.startActivity(new Intent(context, WebLoginActivity.class));
			return;
		}
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", UserInformation.getAccess_token());
		params.addQueryStringParameter("type", String.valueOf(2));
		params.addQueryStringParameter("objectId", map.get("id").toString());// objectId
		AppContext.getHtmlUitls().xUtilsm(context, HttpMethod.POST,
				"m/attention/addAttention", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						if (msg.equals(401)) {
							Toast.makeText(context, "未登录", Toast.LENGTH_SHORT)
									.show();
							// toast("未登录");
							// setRefreshListtener(new Refresh() {
							//
							// @Override
							// public void refreshRequst(String access_token) {
							// // TODO Auto-generated method stub
							// attion();
							// }
							// });
							// RefeshToken();
						}
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						try {
							if (json.getBoolean("success")) {
								Toast.makeText(context, "已经关注",
										Toast.LENGTH_SHORT).show();

							} else {
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

	/** 添加规格 */
	private void addSpec(List<Map<String, Object>> list) {
		for (int i = 0; i < list.size(); i++) {
			View view = View.inflate(context, R.layout.product_detailsize_item,
					null);
			// view = View
			// .inflate(context, R.layout.product_detailsize_item, null);
			Map<String, Object> map = list.get(i);

			TextView title = (TextView) view.findViewById(R.id.guige_title);
			title.setText(map.get("name").toString());
			final int a = Integer.parseInt(map.get("index").toString());
			GridView gv = (GridView) view.findViewById(R.id.guige_gv);
			final List<ProdSpecItemBean> list3 = (List<ProdSpecItemBean>) map
					.get("list3");

			index[a] = list3.get(0).getId();
			// gv.setAdapter(new ArrayAdapter<String>(context,
			// R.layout.productdetail_size_item_item, list0));
			final FormatAdapter formatAdapter = new FormatAdapter(context,
					list3);
			gv.setAdapter(formatAdapter);
			gv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					ProdSpecItemBean item = (ProdSpecItemBean) parent
							.getItemAtPosition(position);
					index[a] = item.getId();

					formatAdapter.setSelectedPos(position);
					ids0 = "";
					for (int j = 0; j < index.length; j++) {
						if (j == 0) {
							ids0 = ids0 + index[j];
						} else {
							ids0 = ids0 + "," + index[j];
						}
					}
					int j = 0;
					for (j = 0; j < list1.size(); j++) {
						String idj = list1.get(j).get("id").toString();
						ids = list1.get(j).get("ids").toString();
						String pricej = list1.get(j).get("price").toString();
						String originalPrice = list1.get(j)
								.get("originalPrice").toString();
						String countj = list1.get(j).get("count").toString();
						String namesj = list1.get(j).get("names").toString();

						if (ids0.equals(ids)) {

							specid = idj;
							price = pricej;
							count = countj;
							names = namesj;
							tv_price.setText(pricej);
							tv_desc.setText(countj);
							tv_size.setText(namesj);
							if (spCallback != null) {
								spCallback.setSepc(specid, originalPrice,
										price, count2, names);
							}
							break;
						} else {

						}
					}
					if (j == list1.size()) {
						Toast.makeText(context, "暂无该规格商品", Toast.LENGTH_SHORT)
								.show();
					}
				}

			});
			guige_ll.addView(view);
		}

	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		this.cancel();
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		this.cancel();
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		if ((e2.getX() - e1.getX()) > 20) {
			this.cancel();
		}
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return detector.onTouchEvent(event);
	}

	/** 加入购物车 count 商品数量type=1,立即支付,type=2,加入购物车 */
	private void addCart(int count, final int type) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", UserInformation.getAccess_token());
		params.addQueryStringParameter("specId", specid);
		params.addQueryStringParameter("count", String.valueOf(count));
		params.addQueryStringParameter("forwardId", ((Activity) context)
				.getIntent().getStringExtra("forwardId"));
		params.addQueryStringParameter("chain", ((Activity) context)
				.getIntent().getStringExtra("chain"));
		AppContext.getHtmlUitls().xUtilsm(context, HttpMethod.POST,
				"m/Cart/addCart", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

						if (msg.equals("401")) {
							Toast.makeText(context, "未登录", Toast.LENGTH_SHORT)
									.show();
						}
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						try {
							if (!json.getBoolean("success")) {
								if (json.getString("msg") != null) {

									Toast.makeText(context,
											json.getString("msg"),
											Toast.LENGTH_SHORT).show();
								}
							} else {
								if (type == 2) {

									Toast.makeText(context, "已加入购物车",
											Toast.LENGTH_SHORT).show();
								} else {

									hanlder.sendEmptyMessage(1);
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	/**
	 * 获取购物车列表
	 * 
	 * @param :method=getCart快速获取购物车,method=forceGetCart强制获取购物车
	 */
	private void getCart(final String method, String access_token) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", access_token);
		params.addHeader("X-Requested-With", "XMLHttpRequest");
		// params.addHeader("X-Requested-With", "XMLHttpRequest");
		AppContext.getHtmlUitls().xUtilsm(context, HttpMethod.POST,
				"m/Cart/" + method, params, new HttpCallback() {

					@Override
					public void onError(String msg) {

					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub

						try {
							if (!json.getBoolean("success")
									&& json.getString("errorCode").equals(
											"NOT_LOGIN")) {

							} else if (json.getBoolean("success")) {

								if (shoppingCartEntity == null) {
									shoppingCartEntity = new ShoppingCartEntity();
									shoppingCartEntity.stores = new ArrayList<ShoppingCartEntity.Store>();
								}
								Gson gson = new Gson();
								// Type type=new
								// TypeToken<ShoppingCartEntity>(){}.getType();
								JSONObject object = json.getJSONObject("obj");
								shoppingCartEntity.allPrice = Float
										.parseFloat(String.valueOf(object
												.getDouble("allPrice")));
								shoppingCartEntity.allCount = object
										.getInt("allCount");
								shoppingCartEntity.cutPrice = Float
										.parseFloat(String.valueOf(object
												.getDouble("cutPrice")));
								shoppingCartEntity.checked = object
										.getBoolean("checked");
								JSONArray array = object
										.getJSONArray("shopModels");
								shoppingCartEntity.stores.clear();
								if (array.length() > 0) {
									for (int i = 0; i < array.length(); i++) {
										Store store = new Store();
										JSONObject object2 = array
												.getJSONObject(i);
										JSONArray array2 = object2
												.getJSONArray("cartSpecs");
										store = gson.fromJson(
												object2.toString(), Store.class);
										store.cartSpec = new ArrayList<ShoppingCartEntity.Store.CartSpec>();
										for (int j = 0; j < array2.length(); j++) {

											CartSpec carspec = gson.fromJson(
													array2.getJSONObject(j)
															.toString(),
													CartSpec.class);
											store.cartSpec.add(carspec);
										}
										shoppingCartEntity.stores.add(store);
									}
								}
								hanlder.sendEmptyMessage(2);
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}

}
