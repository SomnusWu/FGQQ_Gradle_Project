package com.llg.privateproject.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.llg.common.constant.enums.UserTypeEnums;
import com.llg.help.GetProgressBar;
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.actvity.NewAppDown;
import com.llg.privateproject.actvity.OrderStatuspingjia;
import com.llg.privateproject.actvity.WebLoginActivity;
import com.llg.privateproject.adapters.QuanAdapter;
import com.llg.privateproject.adapters.UserGradeInstructionAdapter;
import com.llg.privateproject.entities.Quan;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.utils.Constants;
import com.llg.privateproject.utils.update.Update;
import com.mob.tools.utils.Data;
import com.rabbitmq.client.GetResponse;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.AvoidXfermode.Mode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.format.Time;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

/**
 * 订单状态 yh 2015.8.3
 * */
public class OrderStatusDialog extends Dialog implements
		android.view.View.OnClickListener {
	Context context;
	int type;
	/** 用户等级 */
	int grade;
	/** 物流详情添加商品布局 */
	LinearLayout ll_product;
	/** 物流详情添加物流过程布局 */
	LinearLayout ll_wuliu;
	/** 评价图片布局 */
	LinearLayout ll_pics;
	/** 评价对话框 */
	private OrderStatusDialog dialog1;
	/** 订单详情 */
	private OrderStatusDialog dialog2;
	/** 上传图片对话框 */
	private OrderStatusDialog dialog3;
	/*** 商品列表 */
	// List<Map<String, Object>>list_product;
	File file;
	/** sd卡路径 */
	String path = null;
	Uri imageUri;
	private String id = "";// 订单id
	private PicListener picListener;

	private FinishListener finishListener;
	/** 会员及升级介绍 */
	List<Map<String, Object>> list;
	ImageView chuantupian;
	/** 会员及升级 */
	UserGradeInstructionAdapter adapter;
	ListView lv;
	/** 装载我发展的会员 */
	ListView lv_member;
	/** 我的发展的会员列表 */
	List<Quan> list_membus;
	/** 发展的会员适配器 */
	QuanAdapter memberadapter;
	/** 普通会员功能 */
	private String general_user_function = Constants.MEMBER;
	/** vip会员功能 */
	private String vip_user_function = Constants.VIP;
	/** 创业者功能 */
	private String entrepreneur_function = Constants.ENTREPRENEUR_FUNCTION;
	/** 升级为vip和创业者 */
	private String vip_and_entrepreneur_qualification = Constants.VIP_AND_ENTREPRENEUR_QUALIFICATION;
	/** 区域平台功能简介 */
	private String region_platform_qualification = Constants.REGION_PLATFORM_QUALIFICATION;
	/** 升级vip和创业者发展会员数条件 */
	private String updateString = Constants.UPDATEMEMBER_STRING;
	/***/
	TextView tv_upgrade;
	private boolean isStop = false;
	private IsOpen isOpen;

	/** 图片监听 */
	public interface PicListener {
		/**
		 * type 1 本地上传，2 拍照上传
		 * 
		 * */
		void setPictrue(int type);
	}

	/** 是否开启弹窗 */
	public interface IsOpen {
		/**
		 * 是否开启弹窗
		 *
		 * @param type
		 *            =12,接收弹窗,type=14,接收推送
		 * */
		void isOpen(boolean isOpen, int type);
	}

	/** 传送一个结束Activity的结束代码 */
	public interface FinishListener {
		/** finishcode =1时结束Activity */
		void callBack(int finishcode);
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:// 普通会员
				if (adapter != null) {
					adapter.notifyDataSetChanged();
				}
				break;
			case 2:// 我的会员列表
				if (memberadapter != null) {
					memberadapter.notifyDataSetChanged();
				}
				if (list_membus.size() > 0) {
					v1.setVisibility(View.VISIBLE);
					v2.setVisibility(View.VISIBLE);
					l1.setVisibility(View.VISIBLE);
					tv_hint.setVisibility(View.GONE);
				}
				break;
			case 3:// 订单详情
				setdata(sign, list);
				break;
			}
		}
	};

	/**
	 * @param type
	 *            :1 查看物流，3交易成功，7退款进度,9我邀请的会员,13提示加入商品汇或特惠商品
	 * */
	public OrderStatusDialog(Context context, int type) {
		super(context, R.style.agreemdialog);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.type = type;

	}

	/**
	 * @param type
	 *            12是否开启更新,14是否开启推送提示
	 * */
	public OrderStatusDialog(Context context, int type, IsOpen isOpen) {
		super(context, R.style.agreemdialog);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.type = type;
		this.isOpen = isOpen;

	}

	/**
	 * @param type
	 *            :11确认收货,12是否开启更新
	 * */
	public OrderStatusDialog(Context context, int type, String id) {
		super(context, R.style.agreemdialog);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.type = type;
		this.id = id;

	}

	private String sign;

	/**
	 * @param type
	 *            :2订单详情
	 *            查询订单详细(查询的时候要将id和sign进行组合查询，订单id时sign传2，支付id时sign传1)10版本更新提示
	 *            注：未付款订单穿支付ID, 其他状态的订单穿订单ID id:订单ID/订单支付ID
	 *            [已付款:根据订单ID查询，未付款根据订单支付ID查询] sign: 1:付款ID, 2:订单ID,
	 * 
	 * 
	 * */
	public OrderStatusDialog(Context context, int type, String id, String sign) {
		super(context, R.style.agreemdialog);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.type = type;
		this.id = id;
		this.sign = sign;
	}

	/**
	 * type:3发表评价
	 * */
	public OrderStatusDialog(Context context, int type, PicListener picListener) {
		super(context, R.style.agreemdialog);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.type = type;
		this.picListener = picListener;

	}

	/**
	 * type:4上传图片对话框
	 * */
	public OrderStatusDialog(Context context, int type,
			PicListener picListener, LinearLayout ll_pics) {
		super(context, R.style.agreemdialog);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.type = type;
		this.picListener = picListener;
		// this.ll_pics=ll_pics;

	}

	/**
	 * type:5未完成评价提示,6删除订单
	 * */
	public OrderStatusDialog(Context context, FinishListener finishListener,
			int type) {
		super(context, R.style.agreemdialog);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.type = type;
		this.finishListener = finishListener;

	}

	/**
	 * @param type
	 *            :8用户等级介绍对话框
	 * @param grade
	 *            :用户等级 1 普通会员,2VIP用户 ,3创业者,4付款升级说明,5区域平台
	 * */
	public OrderStatusDialog(Context context, int type, int grade) {
		super(context, R.style.agreemdialog);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.type = type;
		this.grade = grade;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		switch (type) {
		case 1:// 物流对话框
			testWuliu();
			break;
		case 2:// 订单详情对话框
			testOrderDetail(sign);
			break;
		case 3:// 交易成功
			jiaoYichenggong();
			break;
		case 4:// 上传图片对话框
			initHead();
			break;
		case 5:// 未完成评价提示
			weiWanchenghint();
			break;
		case 6:// 删除订单
			deleteOrder();
			break;
		case 7:// 退款进度
			returnStatus();
			break;
		case 8:// 特色功能对话框
			tesegongnengDialog(grade);
			break;
		case 9:// 我的会员
			wodeHuiyuan();
			break;
		case 10:// 版本更新提示
			versionCheck(id);
			break;
		case 11:// 确认收货
			querenshouhuo();
			break;
		case 12:// 是否关闭弹窗广告
			isPopup("");
			break;
		case 13:// 提示入驻平台
			join();
			break;
		case 14:
			isPopup("");
			break;
		}
	}

	/** 提示入驻平台 */
	private void join() {
		setContentView(R.layout.orderstatus_weipingjiawan);
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		TextView tv_hint = (TextView) findViewById(R.id.tv_hint);
		TextView turn = (TextView) findViewById(R.id.turn);
		TextView queding = (TextView) findViewById(R.id.queding);
		tv_title.setVisibility(View.INVISIBLE);
		tv_hint.setVisibility(View.VISIBLE);

		tv_hint.setText("入驻商品汇或特惠商家请到电脑端操作!");
		// tv_title.setText("关闭弹窗将不能及时收取红包");

		turn.setText("关闭");
		queding.setText("确认");
		turn.setOnClickListener(this);
		queding.setOnClickListener(this);
	}

	/** 是否接受弹窗红包,是否开启推送 */
	private void isPopup(String hint) {
		setContentView(R.layout.orderstatus_weipingjiawan);
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		TextView tv_hint = (TextView) findViewById(R.id.tv_hint);
		TextView turn = (TextView) findViewById(R.id.turn);
		TextView queding = (TextView) findViewById(R.id.queding);
		tv_hint.setVisibility(View.VISIBLE);

		tv_hint.setText("是否关闭?");
		tv_title.setText("关闭后将不能及时收取红包");

		turn.setText("不关闭");
		queding.setText("关闭");
		turn.setOnClickListener(this);
		queding.setOnClickListener(this);

	}

	/** 物流对话框 */
	private void testWuliu() {
		setContentView(R.layout.orderstatus_wuliuxiangqing);
		ImageView turn = (ImageView) findViewById(R.id.turn);
		TextView head_tilte = (TextView) findViewById(R.id.head_tilte);
		head_tilte.setText("物流详情");
		turn.setOnClickListener(this);
		ll_product = (LinearLayout) findViewById(R.id.ll_product);
		ll_product.setOnClickListener(this);
		// list_product=new ArrayList<Map<String,Object>>();
		for (int i = 1; i < 4; i++) {
			View view = View.inflate(context,
					R.layout.order_gridview_item_item, null);
			ImageView product_img = (ImageView) view
					.findViewById(R.id.product_img);
			TextView product_dsc = (TextView) view
					.findViewById(R.id.product_dsc);
			TextView product_price = (TextView) view
					.findViewById(R.id.product_price);
			TextView product_count = (TextView) view
					.findViewById(R.id.product_count);
			View v = view.findViewById(R.id.view);

			product_img.setBackgroundResource(R.drawable.welcome);
			product_dsc.setText("这里是商品描述信息");
			product_price.setText("￥" + MyFormat.getPriceFormat("122".trim()));
			product_count.setText("X" + i);
			view.setTag(i);
			if (i == 3) {
				v.setVisibility(View.GONE);
			}
			ll_product.addView(view);

		}
		ll_wuliu = (LinearLayout) findViewById(R.id.ll_wuliu);
		ll_wuliu.removeAllViews();
		for (int j = 10; j > 0; j--) {
			View view1 = View.inflate(context,
					R.layout.orderstatus_wuliuguocheng_item, null);
			ImageView iv = (ImageView) view1.findViewById(R.id.iv);
			TextView wuliuguocheng = (TextView) view1
					.findViewById(R.id.wuliuguocheng);
			TextView wuliutime = (TextView) view1.findViewById(R.id.wuliutime);
			wuliuguocheng.setText("第" + j + "站");
			wuliutime.setText(MyFormat.getTimeFormat(new Date()));
			// wuliutime.setText(MyFormat.getTimeFormat(new Date().toString()));
			if (j == 10) {
				View v1 = view1.findViewById(R.id.v1);
				v1.setVisibility(View.VISIBLE);
				View v2 = view1.findViewById(R.id.v2);
				LayoutParams lp = (LayoutParams) v2.getLayoutParams();
				lp.height = 83;
				wuliuguocheng.setTextColor(context.getResources().getColor(
						R.color.orange1));
				wuliutime.setTextColor(context.getResources().getColor(
						R.color.orange1));
				iv.setBackgroundResource(R.drawable.wdedingdan_process_orange);
				v2.setLayoutParams(lp);

			}
			if (j == 1) {
				View v1 = view1.findViewById(R.id.v1);
				v1.setVisibility(View.VISIBLE);
				v1.setBackgroundDrawable(((context.getResources()
						.getDrawable(R.drawable.stroke_vertical_xuxian_c6))));
				View v2 = view1.findViewById(R.id.v2);
				v2.setVisibility(View.INVISIBLE);
				LayoutParams lp = (LayoutParams) v2.getLayoutParams();
				lp.height = 83;
				v2.setLayoutParams(lp);
			}
			ll_wuliu.addView(view1);
		}

	}

	private Map<String, Object> map;
	private TextView tv_receiver;// 收货人
	private TextView tv_telphone;// 收货电话
	private TextView tv_receiver_address;// 收货地址
	private TextView fapiao_head;// 发票开头
	private TextView shopname;// 店铺名
	private TextView totalprice;// 总价
	private TextView yunfei;// 运费
	private TextView ordercode;// 运费
	private TextView orderpayid;// 支付编号
	private TextView creattime;// 创建时间
	private TextView paytime;// 付款时间
	private TextView sendtime;// 发货时间

	/** 订单详情 */
	private void testOrderDetail(String sign) {
		setContentView(R.layout.orderstatus_orderdetail);
		ImageView turn = (ImageView) findViewById(R.id.turn);
		TextView head_tilte = (TextView) findViewById(R.id.head_tilte);
		tv_receiver = (TextView) findViewById(R.id.tv_receiver);
		tv_telphone = (TextView) findViewById(R.id.tv_telphone);
		tv_receiver_address = (TextView) findViewById(R.id.tv_receiver_address);
		fapiao_head = (TextView) findViewById(R.id.fapiao_head);
		shopname = (TextView) findViewById(R.id.shopname);
		totalprice = (TextView) findViewById(R.id.totalprice);
		yunfei = (TextView) findViewById(R.id.yunfei);
		ordercode = (TextView) findViewById(R.id.ordercode);
		orderpayid = (TextView) findViewById(R.id.orderpayid);
		creattime = (TextView) findViewById(R.id.creattime);
		paytime = (TextView) findViewById(R.id.paytime);
		sendtime = (TextView) findViewById(R.id.sendtime);
		ll_product = (LinearLayout) findViewById(R.id.ll_product);
		map = new HashMap<String, Object>();
		list = new ArrayList<Map<String, Object>>();
		map.put("list", list);
		head_tilte.setText("订单详情");
		turn.setOnClickListener(this);
		orderDetail(map, sign);

	}

	/** 设置订单详情数据 */
	private void setdata(String sign, List<Map<String, Object>> list) {
		tv_receiver.setText("收货人:"
				+ MyFormat.isNull(map.get("consignee").toString()));
		tv_telphone.setText("" + MyFormat.isNull(map.get("phone").toString()));
		tv_receiver_address.setText("收货地址"
				+ MyFormat.isNull(map.get("address").toString()));
		fapiao_head
				.setText(MyFormat.isNull(map.get("invoiceTitle").toString()));
		ordercode.setText(MyFormat.isNull(map.get("orderPayId").toString()));
		if (sign.equals("2")) {
			if (map.get("businessName") != null) {

				shopname.setText(MyFormat.isNull(map.get("businessName")
						.toString()));
			} else {
				shopname.setText(MyFormat.isNull("订单编号:"
						+ map.get("orderPayCode").toString()));
			}
		} else {
			shopname.setText(MyFormat
					.isNull(map.get("orderPayCode").toString()));
		}
		totalprice.setText(MyFormat.isNull(map.get("allPrice").toString()));
		yunfei.setText("含运费:"
				+ MyFormat.isNull(map.get("pdAllPrice").toString()) + "酷币抵扣"
				+ MyFormat.isNull(map.get("payCo").toString()) + "元");
		creattime.setText("创建时间:"
				+ MyFormat.isNull(map.get("createDate").toString()));
		paytime.setText("支付时间:"
				+ MyFormat.isNull(map.get("payDate").toString()));
		sendtime.setText("发货时间:"
				+ MyFormat.isNull(map.get("sendDate").toString()));
		List<Map<String, Object>> list0 = (List<Map<String, Object>>) map
				.get("list");
		for (int i = 1; i < list.size(); i++) {
			View view = View.inflate(context,
					R.layout.order_gridview_item_item, null);
			ImageView product_img = (ImageView) view
					.findViewById(R.id.product_img);
			TextView product_dsc = (TextView) view
					.findViewById(R.id.product_dsc);
			TextView product_price = (TextView) view
					.findViewById(R.id.product_price);
			TextView product_count = (TextView) view
					.findViewById(R.id.product_count);
			View v = view.findViewById(R.id.view);
			MyFormat.setBitmap(context, product_img, list.get(i).get("imgUrl")
					.toString(), 0, 0);
			product_dsc.setText(list.get(i).get("prodName").toString());
			product_price.setText("￥"
					+ MyFormat.getPriceFormat(list.get(i).get("finalPrice")
							.toString().trim()));
			product_count.setText("X" + list.get(i).get("amount").toString());

			view.setTag(i);
			if (i == list.size() - 1) {
				v.setVisibility(View.GONE);
			}
			ll_product.addView(view);

		}
	}

	private void orderDetail(final Map<String, Object> map, final String sign) {
		GetProgressBar.getProgressBar(context);
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("id", id);
		params.addQueryStringParameter("access_token", UserInformation.getAccess_token());
		params.addQueryStringParameter("type", sign);
		params.addHeader(MyFormat.HEADER_KEY, MyFormat.HEADER_VALUE);
		AppContext.getHtmlUitls().xUtilsm(context, HttpMethod.POST,
				"m/order/detail", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						Log.d("my", "" + json);
						try {
							if (json.getBoolean("success")) {
								JSONObject attributes = json
										.getJSONObject("attributes");
								JSONObject consignee = attributes
										.getJSONObject("consignee");
								map.put("phone", consignee.getString("phone"));// 电话
								map.put("address",
										consignee.getString("address"));// 地址
								map.put("consignee",
										consignee.getString("consignee"));// 收货人

								JSONObject orderBase = attributes
										.getJSONObject("orderBase");
								map.put("id", orderBase.getString("id"));// 订单id
								map.put("pdAllPrice",
										orderBase.getString("pdAllPrice"));// 运费
								map.put("allPrice",
										orderBase.getString("allPrice"));// 应付总价
								map.put("orderPayId",
										orderBase.getString("orderPayId"));// 支付id
								map.put("orderPayCode",
										orderBase.getString("orderPayCode"));// 付款编号
								map.put("payCo", orderBase.getString("payCo"));// 实付coshu
								map.put("invoiceTitle",
										orderBase.getString("invoiceTitle"));// 发票开头
								map.put("createDate", MyFormat
										.getTimeFormat3(orderBase
												.getString("createDate")));// 创建时间
								if (sign == "2") {
									if (orderBase.get("sendDate").equals(null)) {

										map.put("sendDate", MyFormat
												.isNull(orderBase
														.getString("sendDate")));// 发货时间
									} else {

										map.put("sendDate", MyFormat
												.getTimeFormat3(orderBase
														.getString("sendDate")));// 支付时间
									}
									map.put("payDate", MyFormat
											.getTimeFormat3(orderBase
													.getString("payDate")));// 支付时间
								} else {
									map.put("sendDate", MyFormat
											.isNull(orderBase
													.getString("sendDate")));// 发货时间
									map.put("payDate",
											orderBase.getString("payDate"));// 支付时间

								}
								JSONArray prods = attributes
										.getJSONArray("prods");
								if (prods.length() > 0) {
									List<Map<String, Object>> list = (List<Map<String, Object>>) map
											.get("list");
									Map<String, Object> item;
									JSONObject ob;
									for (int i = 0; i < prods.length(); i++) {
										item = new HashMap<String, Object>();
										ob = prods.getJSONObject(i);
										item.put("amount",
												ob.getString("amount"));// 商品数量
										item.put("imgUrl",
												ob.getString("imgUrl"));// 商品图片
										item.put("prodName",
												ob.getString("prodName"));// 商品名
										item.put("prodSpecId",
												ob.getString("prodSpecId"));// 规格id
										item.put("finalPrice",
												ob.getString("finalPrice"));// 商品价格
										list.add(item);
									}
									map.put("list", list);
									handler.sendEmptyMessage(3);
								}
								GetProgressBar.dismissMyProgressBar();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	/*** 上传评价图片 */
	void initHead() {
		setContentView(R.layout.orderstatus_pinglun_shangchuantupian_dialog);
		ImageView turn = (ImageView) findViewById(R.id.turn);
		TextView bendishangchuan = (TextView) findViewById(R.id.bendishangchuan);
		TextView paizhaoshangchuan = (TextView) findViewById(R.id.paizhaoshangchuan);
		turn.setOnClickListener(this);
		bendishangchuan.setOnClickListener(this);
		paizhaoshangchuan.setOnClickListener(this);

	}

	/** 交易成功提示 */
	private void jiaoYichenggong() {
		setContentView(R.layout.orderstatus_jiaoyichenggong_hint);
		ImageView turn = (ImageView) findViewById(R.id.turn);
		TextView lijipingjia = (TextView) findViewById(R.id.lijipingjia);
		TextView dingdanxiangqing = (TextView) findViewById(R.id.dingdanxiangqing);
		turn.setOnClickListener(this);
		lijipingjia.setOnClickListener(this);
		dingdanxiangqing.setOnClickListener(this);

	}

	/** 确认 */
	private void querenshouhuo() {
		setContentView(R.layout.orderstatus_jiaoyichenggong_hint);
		ImageView turn = (ImageView) findViewById(R.id.turn);
		TextView lijipingjia = (TextView) findViewById(R.id.lijipingjia);
		lijipingjia.setText("确认收货");
		TextView dingdanxiangqing = (TextView) findViewById(R.id.dingdanxiangqing);
		turn.setOnClickListener(this);
		lijipingjia.setOnClickListener(this);
		dingdanxiangqing.setOnClickListener(this);
		dingdanxiangqing.setText("取消");

	}

	/** 获取传图片按钮 */
	public ImageView getChuantupian() {
		return chuantupian;
	}

	/** 获取评价图片布局LinearLayout */
	public LinearLayout getll_pics() {
		return ll_pics;
	}

	/**
	 * 版本更新提示
	 * 
	 * @param hint
	 *            更新提示
	 * @param url
	 *            下载地址
	 * */
	private void versionCheck(String hint) {
		setContentView(R.layout.orderstatus_weipingjiawan);
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		TextView tv_hint = (TextView) findViewById(R.id.tv_hint);
		TextView turn = (TextView) findViewById(R.id.turn);
		TextView queding = (TextView) findViewById(R.id.queding);
		tv_hint.setVisibility(View.GONE);

		if (hint.length() > 0) {

			tv_title.setText(hint);
		} else {

			tv_title.setText("发现新版本,是否更新?");
		}

		turn.setText("更新");
		queding.setText("不更新");
		turn.setOnClickListener(this);
		queding.setOnClickListener(this);

	}

	/** 未完成订单提示 */
	private void weiWanchenghint() {
		setContentView(R.layout.orderstatus_weipingjiawan);
		TextView turn = (TextView) findViewById(R.id.turn);
		TextView queding = (TextView) findViewById(R.id.queding);
		turn.setOnClickListener(this);
		queding.setOnClickListener(this);

	}

	/** 删除订单 */
	private void deleteOrder() {
		setContentView(R.layout.orderstatus_weipingjiawan);
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		TextView tv_hint = (TextView) findViewById(R.id.tv_hint);
		TextView turn = (TextView) findViewById(R.id.turn);
		TextView queding = (TextView) findViewById(R.id.queding);
		turn.setOnClickListener(this);
		queding.setOnClickListener(this);
		tv_title.setText("删除订单");
		tv_hint.setText("亲确认删除该订单吗？");
		turn.setText("删除");
		queding.setText("取消");

	}

	/** 退货进度对话框 */
	private void returnStatus() {
		setContentView(R.layout.orderstatus_return_status);
		ImageView turn = (ImageView) findViewById(R.id.turn);
		TextView head_tilte = (TextView) findViewById(R.id.head_tilte);
		head_tilte.setText("退款进度");
		turn.setOnClickListener(this);
		ll_wuliu = (LinearLayout) findViewById(R.id.ll_return_status);
		ll_wuliu.removeAllViews();
		for (int j = 10; j > 0; j--) {
			View view1 = View.inflate(context,
					R.layout.orderstatus_wuliuguocheng_item, null);
			ImageView iv = (ImageView) view1.findViewById(R.id.iv);
			TextView wuliuguocheng = (TextView) view1
					.findViewById(R.id.wuliuguocheng);
			TextView wuliutime = (TextView) view1.findViewById(R.id.wuliutime);
			wuliuguocheng.setText("进度" + j + "");
			wuliutime.setText(MyFormat.getTimeFormat(new Date()));
			// wuliutime.setText(MyFormat.getTimeFormat(new Date().toString()));
			if (j == 10) {
				View v1 = view1.findViewById(R.id.v1);
				v1.setVisibility(View.VISIBLE);
				View v2 = view1.findViewById(R.id.v2);
				LayoutParams lp = (LayoutParams) v2.getLayoutParams();
				lp.height = 83;
				wuliuguocheng.setTextColor(context.getResources().getColor(
						R.color.orange1));
				wuliutime.setTextColor(context.getResources().getColor(
						R.color.orange1));
				iv.setBackgroundResource(R.drawable.wdedingdan_process_orange);
				v2.setLayoutParams(lp);

			}
			if (j == 1) {
				View v1 = view1.findViewById(R.id.v1);
				v1.setVisibility(View.VISIBLE);
				v1.setBackgroundDrawable(((context.getResources()
						.getDrawable(R.drawable.stroke_vertical_xuxian_c6))));
				View v2 = view1.findViewById(R.id.v2);
				v2.setVisibility(View.INVISIBLE);
				LayoutParams lp = (LayoutParams) v2.getLayoutParams();
				lp.height = 83;
				v2.setLayoutParams(lp);
			}
			ll_wuliu.addView(view1);
		}
	}

	/**
	 * 用户等级特色功能介绍对话框 * @param grade:用户等级 1 普通会员,2VIP用户 ,3创业者,4付款升级说明
	 */
	private void tesegongnengDialog(int grade) {
		setContentView(R.layout.huiyuandengji_dialog);
		ImageView turn = (ImageView) findViewById(R.id.turn);
		TextView huiyuanjianjie = (TextView) findViewById(R.id.huiyuanjianjie);
		lv = (ListView) findViewById(R.id.lv);

		list = new ArrayList<Map<String, Object>>();

		switch (grade) {
		case 1:// 普通会员
			huiyuanjianjie.setText("普通会员功能简介");

			allFunction("general_user_function");
			break;
		case 2:// vip会员

			huiyuanjianjie.setText("商家功能简介");
			allFunction("vip_user_function");
			break;
		case 3:// 创业者
			huiyuanjianjie.setText("企业版功能简介");
			allFunction("entrepreneur_function");
			break;
		case 4:// 付款升级说明
			huiyuanjianjie.setText("会员付款升级说明");
			allFunction("vip_and_entrepreneur_qualification");
			break;

		case 5:// 区域平台
			huiyuanjianjie.setText("区域平台功能简介");
			allFunction("region_platform_qualification");
			break;
		}
		if (adapter == null) {

			adapter = new UserGradeInstructionAdapter(context, list);
		}
		lv.setAdapter(adapter);
		turn.setOnClickListener(this);

	}

	public void allFunction(String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "");
		if (code.equals("general_user_function")) {

			map.put("dsc",
					context.getSharedPreferences("userfunction",
							Context.MODE_PRIVATE).getString(
							"general_user_function", general_user_function));
		} else if (code.equals("vip_user_function")) {

			map.put("dsc",
					context.getSharedPreferences("userfunction",
							Context.MODE_PRIVATE).getString(
							"vip_user_function", vip_user_function));
		} else if (code.equals("entrepreneur_function")) {

			map.put("dsc",
					context.getSharedPreferences("userfunction",
							Context.MODE_PRIVATE).getString(
							"entrepreneur_function", entrepreneur_function));
		} else if (code.equals("vip_and_entrepreneur_qualification")) {

			map.put("dsc",
					context.getSharedPreferences("userfunction",
							Context.MODE_PRIVATE).getString(
							"vip_and_entrepreneur_qualification",
							vip_and_entrepreneur_qualification));
		} else if (code.equals("region_platform_qualification")) {

			map.put("dsc",
					context.getSharedPreferences("userfunction",
							Context.MODE_PRIVATE).getString(
							"region_platform_qualification",
							region_platform_qualification));
		}
		list.add(map);

		userFunction(code);
	}

	/**
	 * 会员等级介绍 general_user_function:普通会员功能简介,
	 * */
	private void userFunction(final String code) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("code", code);
		Log.d("my", "code:" + code);
		AppContext.getHtmlUitls().xUtils(context, HttpMethod.GET,
				"help/getHelpContentByCode", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						try {
							if (json.getBoolean("success")) {

								if (code.equals("general_user_function")) {
									general_user_function = json.getJSONObject(
											"obj").getString("content");
									SharedPreferences sPreferences = context
											.getSharedPreferences(
													"userfunction",
													Context.MODE_PRIVATE);
									sPreferences
											.edit()
											.putString(code,
													general_user_function)
											.commit();
									list.clear();
									Map<String, Object> map = new HashMap<String, Object>();
									map.put("name", "");
									map.put("dsc", general_user_function);
									list.add(map);
									handler.sendEmptyMessage(1);
								} else if (code.equals("vip_user_function")) {
									vip_user_function = json.getJSONObject(
											"obj").getString("content");
									SharedPreferences sPreferences = context
											.getSharedPreferences(
													"userfunction",
													Context.MODE_PRIVATE);
									sPreferences.edit()
											.putString(code, vip_user_function)
											.commit();
									list.clear();
									Map<String, Object> map = new HashMap<String, Object>();
									map.put("name", "");
									map.put("dsc", vip_user_function);
									list.add(map);
									handler.sendEmptyMessage(1);
								} else if (code.equals("entrepreneur_function")) {
									entrepreneur_function = json.getJSONObject(
											"obj").getString("content");
									SharedPreferences sPreferences = context
											.getSharedPreferences(
													"userfunction",
													Context.MODE_PRIVATE);
									sPreferences
											.edit()
											.putString(code,
													entrepreneur_function)
											.commit();
									list.clear();
									Map<String, Object> map = new HashMap<String, Object>();
									map.put("name", "");
									map.put("dsc", entrepreneur_function);
									list.add(map);
									handler.sendEmptyMessage(1);
								} else if (code
										.equals("vip_and_entrepreneur_qualification")) {
									vip_and_entrepreneur_qualification = json
											.getJSONObject("obj").getString(
													"content");
									SharedPreferences sPreferences = context
											.getSharedPreferences(
													"userfunction",
													Context.MODE_PRIVATE);
									sPreferences
											.edit()
											.putString(code,
													vip_and_entrepreneur_qualification)
											.commit();
									list.clear();
									Map<String, Object> map = new HashMap<String, Object>();
									map.put("name", "");
									map.put("dsc",
											vip_and_entrepreneur_qualification);
									list.add(map);
									handler.sendEmptyMessage(1);
								}

							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	View v1;
	View v2;
	LinearLayout l1;
	TextView tv_hint;

	/** 我的会员 */
	private void wodeHuiyuan() {
		setContentView(R.layout.yaoqinghuiyuanshuoming);

		ScrollView sv = (ScrollView) findViewById(R.id.sv);
		v1 = findViewById(R.id.v1);
		v2 = findViewById(R.id.v2);
		l1 = (LinearLayout) findViewById(R.id.l1);
		lv_member = (ListView) findViewById(R.id.lv);
		TextView wodehuiyuan = (TextView) findViewById(R.id.wodehuiyuan);
		tv_upgrade = (TextView) findViewById(R.id.tv_upgrade);
		tv_hint = (TextView) findViewById(R.id.tv_hint);
		TextView tv_huiyuan = (TextView) findViewById(R.id.tv_huiyuan);
		TextView tv_chenggonghuiyuan = (TextView) findViewById(R.id.tv_chenggonghuiyuan);
		SpannableStringBuilder ssbBuilder = new SpannableStringBuilder(
				tv_huiyuan.getText().toString());
		SpannableStringBuilder ssbBuilder1 = new SpannableStringBuilder(
				tv_chenggonghuiyuan.getText().toString());
		ForegroundColorSpan fensColorSpan = new ForegroundColorSpan(context
				.getResources().getColor(R.color.fen));
		ssbBuilder.setSpan(fensColorSpan, 0, 6,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ssbBuilder1.setSpan(fensColorSpan, 0, 9,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv_huiyuan.setText(ssbBuilder);
		tv_chenggonghuiyuan.setText(ssbBuilder1);
		wodehuiyuan.setText("我邀请的会员");

		list_membus = new ArrayList<Quan>();
		// for (int i = 0; i < 15; i++) {
		// Quan quan = new Quan("123456789" + i, "昵称" + i,
		// MyFormat.getTimeFormat1(new Date()), "会员等级" + i);
		// list_membus.add(quan);
		// }

		if (list_membus.size() < 1) {
			v1.setVisibility(View.GONE);
			v2.setVisibility(View.GONE);
			l1.setVisibility(View.GONE);
			tv_hint.setVisibility(View.VISIBLE);
		}

		invitePerson();
		memberadapter = new QuanAdapter(context, list_membus);
		lv_member.setAdapter(memberadapter);
		sv.smoothScrollTo(0, 0);
	}

	/** 获取我发展的会员 */
	private void invitePerson() {
		RequestParams params = new RequestParams();
		params.addHeader(MyFormat.HEADER_KEY, MyFormat.HEADER_VALUE);
		params.addQueryStringParameter("access_token", UserInformation.getAccess_token());
		AppContext.getHtmlUitls().xUtilsm(context, HttpMethod.POST,
				"m/develop/invitePerson", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						try {
							if (json.getBoolean("success")) {
								JSONObject page = json.getJSONObject(
										"attributes").getJSONObject("page");
								JSONArray result = page.getJSONArray("result");
								if (result.length() > 0) {
									list_membus.clear();
									for (int i = 0; i < result.length(); i++) {
										JSONObject obj = result
												.getJSONObject(i);
										String dateString = "";
										if (obj.get("last_pay_date") == null
												|| obj.get("last_pay_date")
														.equals(null)) {
											dateString = "暂无";

										} else {
											dateString = MyFormat.getTimeFormat2(obj
													.getString("last_pay_date"));
										}
										String user_type = "";

										if (obj.getString("user_type").equals(
												UserTypeEnums.GENERAL_USER
														.getValue())) {
											user_type = UserTypeEnums.GENERAL_USER
													.getInfo();
										} else if (obj.getString("user_type")
												.equals(UserTypeEnums.VIP_USER
														.getValue())) {
											user_type = UserTypeEnums.VIP_USER
													.getInfo();
										} else if (obj
												.getString("user_type")
												.equals(UserTypeEnums.ENTREPRENEUR
														.getValue())) {
											user_type = UserTypeEnums.ENTREPRENEUR
													.getInfo();
										} else if (obj
												.getString("user_type")
												.equals(UserTypeEnums.NETWORK_LEADER
														.getValue())) {
											user_type = UserTypeEnums.NETWORK_LEADER
													.getInfo();
										} else if (obj
												.getString("user_type")
												.equals(UserTypeEnums.REGION_PROXY
														.getValue())) {
											user_type = UserTypeEnums.REGION_PROXY
													.getInfo();
										} else if (obj.getString("user_type")
												.equals(UserTypeEnums.MANAGER
														.getValue())) {
											user_type = UserTypeEnums.MANAGER
													.getInfo();
										} else if (obj
												.getString("user_type")
												.equals(UserTypeEnums.SUPER_ADMIN
														.getValue())) {
											user_type = UserTypeEnums.SUPER_ADMIN
													.getInfo();
										} else if (obj.getString("user_type")
												.equals(UserTypeEnums.PROVINCE
														.getValue())) {
											user_type = UserTypeEnums.PROVINCE
													.getInfo();
										} else if (obj.getString("user_type")
												.equals(UserTypeEnums.COUNTY
														.getValue())) {
											user_type = UserTypeEnums.COUNTY
													.getInfo();
										}

										// dateString=obj.getString("last_pay_date");

										MyFormat.replaceString(
												obj.getString("appellation"), 4);
										Quan quan = new Quan(
												MyFormat.replaceString(
														obj.getString("cus_id"),
														4),
												MyFormat.replaceString(
														obj.getString("appellation"),
														4), dateString,
												user_type);

										list_membus.add(quan);
									}
									handler.sendEmptyMessage(2);
								}
							} else {
								if (json.getString("errorCode") != null
										&& json.getString("errorCode").equals(
												"NOT_LOGIN")) {
									Toast.makeText(context,
											json.getString("msg"),
											Toast.LENGTH_SHORT).show();
									context.startActivity(new Intent(context,
											WebLoginActivity.class));
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.turn:// 返回
			cancel();
			if (type == 12 || type == 14) {// 是否开启弹窗广告
				if (isOpen != null) {
					isOpen.isOpen(true, type);
				}
				return;
			}
			if (dialog3 != null) {
				dialog3.cancel();

			}
			if (type == 6) {
				if (finishListener != null) {
					finishListener.callBack(2);
				}
			}
			if (type == 10) {
				new Update(context, sign);
				// sign="http://www.fgqqg.com/homePage/app";
				// initWebviewSetting(sign);
			}
			break;
		case R.id.chuantupian:// 上传图片
			// if(dialog3==null)
			//
			// { dialog3=new OrderStatusDialog(context, 4,picListener,null);
			// Log.e("my",
			// "new OrderStatusDialog(context, 4,(PicListener) context,null)");
			// }
			//
			// dialog3.show();
			// Window window3=dialog3.getWindow();
			// WindowManager.LayoutParams lp3=window3.getAttributes();
			// lp3.width=AppContext.getScreenWidth()*4/5;
			// lp3.height=lp3.WRAP_CONTENT;
			//
			// dialog3.getWindow().setAttributes(lp3);

			break;
		case R.id.bendishangchuan:// 本地上传
			// Log.e("my", "picListener"+picListener+"ll_pics"+ll_pics);
			if (picListener != null) {
				picListener.setPictrue(1);
			}
			cancel();

			break;
		case R.id.paizhaoshangchuan:// 拍照上传
			if (picListener != null) {
				picListener.setPictrue(2);
			}
			cancel();
			break;
		case R.id.ll_product:// 商品
			if (ll_product != null) {

				if (dialog2 == null) {
					dialog2 = new OrderStatusDialog(context, 2);
				}
				dialog2.show();
				Window window = dialog2.getWindow();
				android.view.WindowManager.LayoutParams lp = window
						.getAttributes();
				lp.width = AppContext.getScreenWidth();
				lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
				dialog2.getWindow().setAttributes(lp);
			}
			break;
		case R.id.confirm:// 提交
			Toast.makeText(context, "提交评论", Toast.LENGTH_SHORT).show();
			break;
		case R.id.lijipingjia:// 立即评价
			if (type == 11) {
				RequestParams params = new RequestParams();
				params.addQueryStringParameter("id", id);

				params.addHeader(MyFormat.HEADER_KEY, MyFormat.HEADER_VALUE);
				AppContext.getHtmlUitls().xUtilsm(context, HttpMethod.POST,
						"m/order/confirm", params, new HttpCallback() {

							@Override
							public void onError(String msg) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onBack(JSONObject json) {
								// TODO Auto-generated method stub

								try {
									if (json.getBoolean("success")) {
										Toast.makeText(context, "操作成功",
												Toast.LENGTH_SHORT).show();
									} else {
										if (json.get("msg") != null) {
											if (json.get("errorCode") != null
													&& json.get("errorCode")
															.equals(MyFormat.NOT_LOGIN)) {
												context.startActivity(new Intent(
														context,
														WebLoginActivity.class));
											}
											Toast.makeText(context,
													json.getString("msg"),
													Toast.LENGTH_SHORT).show();
										}
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
			} else {
				Intent intent = new Intent(context, OrderStatuspingjia.class);
				context.startActivity(intent);
				cancel();
				Toast.makeText(context, "立即评论", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.dingdanxiangqing:// 订单详情
			if (type == 11) {
				cancel();
				return;
			}
			if (dialog2 == null) {
				dialog2 = new OrderStatusDialog(context, 2);
			}
			dialog2.show();
			Window window = dialog2.getWindow();
			android.view.WindowManager.LayoutParams lp = window.getAttributes();
			lp.width = AppContext.getScreenWidth();
			lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
			dialog2.getWindow().setAttributes(lp);
			cancel();

			Toast.makeText(context, "订单详情", Toast.LENGTH_SHORT).show();
			break;
		case R.id.queding:// 返回
			if (type == 12 || type == 14) {// 是否开启弹窗广告/开启推送
				if (isOpen != null) {
					isOpen.isOpen(false, type);
				}

			}

			if (finishListener != null) {
				finishListener.callBack(1);
			}
			cancel();

			break;
		default:
			break;
		}
	}

	WebView webView;

	private void initWebviewSetting(String url) {

		// 设置支持JavaScript脚本

		if (webView == null) {
			webView = new WebView(context);
		}
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		// 设置可以访问文件
		webSettings.setAllowFileAccess(true);
		// 设置可以支持缩放
		webSettings.setSupportZoom(true);
		// 设置默认缩放方式尺寸是far
		webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
		// 设置出现缩放工具
		webSettings.setBuiltInZoomControls(true);
		webSettings.setDefaultFontSize(20);

		webView.loadUrl(url);

	}

	@Override
	public Bundle onSaveInstanceState() {
		// TODO Auto-generated method stub
		return super.onSaveInstanceState();
	}
}
