package com.llg.privateproject.actvity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bjg.lcc.jsonparser.ParseJson;
import com.bjg.lcc.privateproject.R;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.help.GetProgressBar;
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.adapter.GalleryAdapter;
import com.llg.privateproject.adapters.MaybeYouLikerAdapter;
import com.llg.privateproject.entities.ProdSpecItemBean;
import com.llg.privateproject.entities.ShoppingCartEntity;
import com.llg.privateproject.entities.ShoppingCartEntity.Store;
import com.llg.privateproject.entities.ShoppingCartEntity.Store.CartSpec;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.view.CustomScrollView;
import com.llg.privateproject.view.CustomScrollView.IScrollChangeListener;
import com.llg.privateproject.view.MyGridView;
import com.llg.privateproject.view.ProductDetailEllipsisPoPuWindow;
import com.llg.privateproject.view.ProductDetailEllipsisPoPuWindow.SelecetListener;
import com.llg.privateproject.view.ProductDetailSizeDialog;
import com.llg.privateproject.view.ProductDetailSizeDialog.SepcCallback;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 产品详情 yh 2015.6.15
 * 
 * */
public class ProductDetailActivity extends BaseActivity implements
		OnItemSelectedListener, OnItemClickListener, OnGestureListener,
		OnTouchListener, SepcCallback, SelecetListener {
	ShoppingCartEntity shoppingCartEntity;
	/** 顶部导航栏布局 */
	@ViewInject(R.id.product_detail_head_rl)
	private RelativeLayout product_detail_head_rl;
	/** popuwindow显示位置 */
	@ViewInject(R.id.v_pop)
	private View view0;
	/** 产品详情布局 */
	@ViewInject(R.id.ll_parent)
	private LinearLayout ll_parent;
	/**加入购物车 */
	@ViewInject(R.id.ll_button)
	private LinearLayout ll_button;
	/** 返回按钮 */
	@ViewInject(R.id.turn)
	private ImageView turn;
	/** 标题 */
	@ViewInject(R.id.head_tilte)
	private TextView head_tilte;
	/** 更多 */
	@ViewInject(R.id.ellipsis)
	private ImageView ellipsis;
	/** 小三角 */
	@ViewInject(R.id.iv_show)
	private ImageView iv_show;
	/** 店铺图标 */
	@ViewInject(R.id.shop_pic)
	private ImageView shop_pic;
	/** co价布局 */
	@ViewInject(R.id.ll_co)
	private LinearLayout ll_co;
	/** co价 */
	@ViewInject(R.id.coprice)
	private TextView coprice;
	@ViewInject(R.id.coprice1)
	private TextView coprice1;
	@ViewInject(R.id.coprice2)
	private TextView coprice2;
	/** 自定义scrollview */
	@ViewInject(R.id.sv)
	private CustomScrollView sv;

	/** 更多弹窗口 */
	private ProductDetailEllipsisPoPuWindow ellipticalsisWindow;
	/** 产品图片 */
	@ViewInject(R.id.gl)
	private Gallery gl;
	/** 刷新 */
	@ViewInject(R.id.fresh)
	private ImageView fresh;
	/** gallery布局 */
	@ViewInject(R.id.fl_gr)
	private FrameLayout fl_gr;
	/** 查看更多详情 */
	@ViewInject(R.id.to_more)
	private TextView to_more;
	private IWXAPI api;
	/** 原价 */
	@ViewInject(R.id.old_price)
	private TextView old_price;
	/** 现价 */
	@ViewInject(R.id.price)
	private TextView price;
	/** co币 */
	@ViewInject(R.id.cq_money)
	private TextView cq_money;
	/** 满减活动 */
	@ViewInject(R.id.sales_content)
	private TextView sales_content;

	/** 查看更多评论 */
	@ViewInject(R.id._commenter)
	private TextView _commenter;

	/** 评论者头像 */
	@ViewInject(R.id.commenter_head)
	private ImageView commenter_head;
	/** 评论者 昵称 */
	@ViewInject(R.id.commenter_petname)
	private TextView commenter_petname;
	/** 评论内容 */
	@ViewInject(R.id.commenter_content)
	private TextView commenter_content;
	/** 评论时间 */
	@ViewInject(R.id.comment_time)
	private TextView comment_time;
	/** 评论规格1 */
	@ViewInject(R.id.comment_size)
	private TextView comment_spec1;
	/** 评论规格2 */
	@ViewInject(R.id.comment_color)
	private TextView comment_spec2;

	/** 店铺名 */
	@ViewInject(R.id.shop_name)
	private TextView shop_name;
	/** 全部商品 */
	@ViewInject(R.id.all_product_count)
	private TextView all_product_count;
	/** 新商品 */
	@ViewInject(R.id.new_product_count)
	private TextView new_product_count;
	/** 关注人数 */
	@ViewInject(R.id.see_peoples)
	private TextView see_peoples;
	/** 查看宝贝分类 */
	@ViewInject(R.id.to_category)
	private TextView to_category;
	/** 进入店铺 */
	@ViewInject(R.id.to_shop)
	private RelativeLayout to_shop;
	/** 猜你喜欢 */
	@ViewInject(R.id.mayby_youlike)
	private TextView mayby_youlike;
	/** 猜你喜欢列表 */
	@ViewInject(R.id.mayby_youlike_lv)
	private MyGridView mayby_youlike_lv;
	/** 猜你喜欢数据配置 */
	private List<Map<String, Object>> list_maybeYouLike = null;

	/** gallery */
	private List<String> listImgs;
	/** 获取第一张传过来的图片 */
	String img;
	/** gallery当前项 */
	@ViewInject(R.id.current_num)
	private TextView current_num;
	/** gallery总项数 */
	@ViewInject(R.id.sum_num)
	private TextView sum_num;
	/** 图片数据适配器 */
	private GalleryAdapter galleryAdapter;
	/** 猜你喜欢数据适配器 */
	private MaybeYouLikerAdapter maybeYOuLikerAdapter;
	/** 更多详情对话框 */
	ProductDetailMoreActivity detailMoredialog;
	/** 尺寸选择对话框 */
	ProductDetailSizeDialog dialog;
	/** 规格对话框map */
	private Map<String, Object> tag;
	/** 规格对话框map */
	private Map<String, Object> map;
	/** 尺寸、颜色选择 */
	@ViewInject(R.id.select)
	private TextView select;
	/** 立即购买 */
	@ViewInject(R.id.buy)
	private TextView buy;
	/** 加入购物车 */
	@ViewInject(R.id.collect)
	private TextView collect;
	/** 继续拖动 */
	@ViewInject(R.id.ll_up)
	private LinearLayout ll_up;
	/** 手势指示剂 */
	GestureDetector detector;
	AppContext appContext;
	/** 店铺map */
	private Map<String, Object> shopBean;
	/** 评论map */
	private List<Map<String, Object>> prodCommentInfoBeanList;
	/** 规格id */
	private String prodspecId = "";
	/** 默认页 */
	private String pageNo = "1";
	/** 组合规格表 */
	private List<Map<String, Object>> list1;
	/** 规格表 */
	private List<Map<String, Object>> list2;
	/** 商品规格id */
	private String sepcId = "";
	/** 购买数量 */
	private int sum = 1;
	/** 店铺id */
	String shopId = "";
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			GetProgressBar.dismissMyProgressBar();
			switch (msg.what) {
			case 1:// 商品基本信息

				maybeYOuLikerAdapter.notifyDataSetChanged();
				mayby_youlike_lv.setNumColumns(list_maybeYouLike.size());
				setProduct(msg);

				break;
			case 0:// 失败

				break;
			case 2:// 店铺信息
				shop_name.setText(shopBean.get("name").toString());
				shopId = shopBean.get("id").toString();
				all_product_count.setText(shopBean.get("numberOfGoods")
						.toString());
				new_product_count.setText(shopBean.get("newCount").toString());
				see_peoples.setText(shopBean.get("attentionCount").toString());

				break;
			case 3:// 获取评论列表
				if (prodCommentInfoBeanList.size() > 0) {
					Map<String, Object> map = prodCommentInfoBeanList.get(0);

					MyFormat.setBitmap(ProductDetailActivity.this,
							commenter_head, map.get("avatar").toString(), 0, 0);
					commenter_petname.setText(MyFormat.replaceString(
							map.get("appellation").toString(), 4));
					commenter_content.setText(map.get("content").toString());
					comment_time.setText(map.get("date").toString());
					JSONArray sArray = (JSONArray) map.get("type");
					try {
						JSONObject object0 = (JSONObject) sArray.get(0);
						comment_spec1.setText(object0.getString("key") + ":"
								+ object0.getString("value"));
						if (sArray.length() > 1) {
							JSONObject object1 = (JSONObject) sArray.get(1);

							comment_spec2.setText(object1.getString("key")
									+ ":" + object1.getString("value"));
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			case 4:// 图片更新
				galleryAdapter.notifyDataSetChanged();
				sum_num.setText("" + listImgs.size());
				if (list1 != null && list1.size() > 0) {
					Map<String, Object> map = list1.get(0);
					price.setText(map.get("price").toString());
					old_price.setText(map.get("originalPrice").toString());
					select.setText(map.get("names").toString());

				}
				sv.smoothScrollTo(0, 0);
				break;
			case 5:// 立即支付
				if (appContext.isNetworkConnected()) {
					if (UserInformation.isLogin()) {

						getCart("forceGetCart", UserInformation
								.getAccess_token());
					} else {
						Log.d("my", "立即支付");
						Intent intent = new Intent(ProductDetailActivity.this,
								WebLoginActivity.class);
						ProductDetailActivity.this.startActivity(intent);
					}
				}
				break;
			case 6:// 跳转到结算页面
				if (shoppingCartEntity.allPrice > 0) {

					startActivity(new Intent(ProductDetailActivity.this,
							OrderClearActivity.class));
				} else {
					Toast.makeText(ProductDetailActivity.this, "支付金额必须大于零",
							Toast.LENGTH_SHORT).show();
				}
				break;
			}
		}

		/*** 设置商品基本信息 */
		public void setProduct(android.os.Message msg) {
			if (list_maybeYouLike.size() > 0) {
				mayby_youlike.setVisibility(View.VISIBLE);
			} else {
				mayby_youlike.setVisibility(View.VISIBLE);
			}
			JSONObject jsonObject = (JSONObject) msg.obj;
			try {
				if (jsonObject.getString("name") != null) {
					to_more.setText(jsonObject.getString("name"));
				}
				if (jsonObject.getString("co") != null) {
					cq_money.setText(jsonObject.getString("co"));
					int co = Integer.parseInt(cq_money.getText().toString());
					if (co > 0) {
						ll_co.setVisibility(View.VISIBLE);
						coprice1.setText(co + "酷币+");

						if (jsonObject.getString("goingPrice") != null
								&& !jsonObject.getString("goingPrice").equals(
										"null")) {
							Double d = Double.parseDouble(jsonObject
									.getString("goingPrice")) - co;
							coprice2.setText("￥" + d);
						} else {
							Double d = Double.parseDouble(jsonObject
									.getString("originalPrice")) - co;
							coprice2.setText("￥" + d);

						}

					}
				}
				if (jsonObject.getString("originalPrice") != null) {

					old_price.setText(MyFormat.getPriceFormat(jsonObject
							.getString("originalPrice")));
				}
				if (jsonObject.getString("goingPrice") != null
						&& !jsonObject.getString("goingPrice").equals("null")) {
					price.setText(MyFormat.getPriceFormat(jsonObject
							.getString("goingPrice")));
				} else {
					price.setText(MyFormat.getPriceFormat(old_price.getText()
							.toString()));

				}
				StringBuffer sb = new StringBuffer();
				if (jsonObject.getJSONArray("promEnoughCutList").length() > 0) {
					JSONObject jsonObject2 = (JSONObject) jsonObject
							.getJSONArray("promEnoughCutList").get(0);
					if (jsonObject2.has("requirePrice")) {

						sb.append("满" + jsonObject2.getString("requirePrice"));
					}
					if (jsonObject2.has("cutPrice")) {
						sb.append("减" + jsonObject2.getString("cutPrice"));
					}
					if (jsonObject2.has("requirePrice2")) {

						sb.append(",满" + jsonObject2.getString("requirePrice2"));
					}
					if (jsonObject2.has("cutPrice2")) {
						sb.append("减" + jsonObject2.getString("cutPrice2"));
					}
					if (jsonObject2.has("requirePrice3")) {

						sb.append(",满" + jsonObject2.getString("requirePrice3"));
					}
					if (jsonObject2.has("cutPrice3")) {
						sb.append("减" + jsonObject2.getString("cutPrice3"));
					}
				}
				sales_content.setText(sb);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_detail);
		ViewUtils.inject(this);
		init();
	}

	/** 初始化数据 */
	void init() {
		ll_button.setVisibility(View.VISIBLE);
		ll_co.setVisibility(View.GONE);
		fresh.setVisibility(View.GONE);
		api = WXAPIFactory.createWXAPI(this,
				com.llg.privateproject.utils.Constants.APP_ID, true);
		api.registerApp(com.llg.privateproject.utils.Constants.APP_ID);
		tag = new HashMap<String, Object>();
		appContext = (AppContext) getApplication();
		prodspecId = getIntent().getStringExtra("prodspecId");

		img = getIntent().getStringExtra("img");
		listImgs = new ArrayList<String>();
		if(img!=null)
		{listImgs.add(img);}
		sum_num.setText("" + listImgs.size());
		fl_gr.setVisibility(View.VISIBLE);
		mayby_youlike.setVisibility(View.GONE);
		shopBean = new HashMap<String, Object>();

		sv.setIScrollChangedListener(new IScrollChangeListener() {

			@Override
			public void setLoction(int deltaX, int deltaY, int scrollX,
					int scrollY) {
				// TODO Auto-generated method stub0
				if (scrollY > 300) {
					product_detail_head_rl.setBackgroundResource(R.color.white);
				} else {
					product_detail_head_rl
							.setBackgroundResource(R.color.transparent);
				}

			}

		});
		list_maybeYouLike = new ArrayList<Map<String, Object>>();
		maybeYOuLikerAdapter = new MaybeYouLikerAdapter(this,
				list_maybeYouLike, false);// 猜你喜欢适配器
		mayby_youlike_lv.setAdapter(maybeYOuLikerAdapter);
		mayby_youlike_lv.setNumColumns(list_maybeYouLike.size());
		if (list_maybeYouLike.size() > 0) {
			mayby_youlike.setVisibility(View.VISIBLE);
		}
		detector = new GestureDetector(this, this);
		head_tilte.setText("");
		old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		map = new HashMap<String, Object>();
		mayby_youlike_lv.setOnItemClickListener(this);
		ll_up.setOnTouchListener(this);
		// sv.setOnTouchListener(this);
		if (appContext.isNetworkConnected()) {
			getData(prodspecId);
		}
		// testGuangGaoJiFenData();

		int h = (int) (AppContext.getScreenWidth() / 1.09);
		android.widget.FrameLayout.LayoutParams params = new android.widget.FrameLayout.LayoutParams(
				AppContext.getScreenWidth(), h);
		gl.setLayoutParams(params);

	}

	/** 获取数据 */
	private void getData(String prodspecId) {

		GetProgressBar.getProgressBar(this);
		RequestParams params = new RequestParams();
		Log.d("my", "prodspecId:" + prodspecId);
		// prodspecId="4028803c51669455015166a8f9df000e";
		// 402880794fcb4de0014fcb531db7000b
		// prodspecId="402880794fcb4de0014fcb531db7000b";//402880794fcb4de0014fcb531db7000b
		params.addQueryStringParameter("prodSpecId", prodspecId);
		params.addQueryStringParameter("cusId",AppContext.userid);
		getCommodityImage("prod/info", params);
		getBaseData("getCommodityDetails", params);
		getShopInfo("getShopInfo", params);
		params.addQueryStringParameter("pageNo", pageNo);
		getCommodityComment("getCommodityComment", params);
	}

	/** 获取商品基本数据 */
	public void getBaseData(String methodName, RequestParams params) {
		AppContext.getHtmlUitls().xUtils(this, HttpMethod.GET, methodName,
				params, new HttpCallback() {
					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						GetProgressBar.dismissMyProgressBar();

						Toast.makeText(ProductDetailActivity.this, "链接异常",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						GetProgressBar.dismissMyProgressBar();

						try {
							if (json.getBoolean("success")) {
								JSONObject jsonObject = json
										.getJSONObject("attributes");
								list_maybeYouLike.clear();
								ParseJson.getParseJson().setDataList(
										"appProdSpecBeans", jsonObject,
										list_maybeYouLike);

								Message msg = new Message();
								msg.obj = jsonObject
										.getJSONObject("ProdBaseBean");
								msg.what = 1;
								handler.sendMessage(msg);

							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	/** gallery商品展示图片,获取规格标签 */
	void getCommodityImage(final String methodName, RequestParams params) {

		AppContext.getHtmlUitls().xUtils(this, HttpMethod.GET, methodName,
				params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

						Log.d("my", "onError" + msg);
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub

						try {
							if (json.getBoolean("success")) {
								JSONObject obj = json.getJSONObject("obj");
								Log.d("my", obj.toString());

								JSONArray sArray = obj.getJSONArray("imgList");

								if (sArray.length() > 0) {
//									listImgs.clear();
									// listImgs.add(img);
									for (int i = 0; i < sArray.length(); i++) {
										listImgs.add(sArray.getString(i));

									}
								}
								JSONArray specInfolist = obj
										.getJSONArray("specInfolist");// 组合规格列表
								if (specInfolist.length() > 0) {
									Map<String, Object> map1;
									if (list1 == null) {
										list1 = new ArrayList<Map<String, Object>>();// 组合规格列表}
										for (int i = 0; i < specInfolist
												.length(); i++) {
											JSONObject obj1 = specInfolist
													.getJSONObject(i);
											map1 = new HashMap<String, Object>();
											if(i==0){
//												sepcId=obj1.getString("ids");
												sepcId=obj1.getString("id");
												Log.d("my",sepcId);
												Log.d("my",obj1.getString("ids"));
											}
											map1.put("id", obj1.getString("id"));
											map1.put("ids",
													obj1.getString("ids"));
											map1.put("names",
													obj1.getString("names"));
											map1.put("count",
													obj1.getString("count"));
											map1.put("originalPrice", obj1
													.getString("originalPrice"));
											map1.put("price",
													obj1.getString("price"));
											list1.add(map1);
										}
										tag.put("list1", list1);
									}
									JSONArray specOptionBeanList = obj
											.getJSONArray("specOptionBeanList");// 规格列表
									Map<String, Object> map2;
									list2 = new ArrayList<Map<String, Object>>();// 规格列表
									if (specOptionBeanList.length() > 0) {
										for (int i = 0; i < specOptionBeanList
												.length(); i++) {
											JSONObject obj2 = specOptionBeanList
													.getJSONObject(i);
											map2 = new HashMap<String, Object>();
											map2.put("id", obj2.getString("id"));
											map2.put("name",
													obj2.getString("name"));
											map2.put("index", "" + i);
											JSONObject items = obj2
													.getJSONObject("items");// 类型值对象
											Iterator it = items.keys();
											ProdSpecItemBean item;
											List<ProdSpecItemBean> list3 = new ArrayList<ProdSpecItemBean>();// 类型列表
											while (it.hasNext()) {
												item = new ProdSpecItemBean();
												item.setId(it.next().toString());
												item.setName(items
														.getString(item.getId()));
												list3.add(item);

											}
											map2.put("list3", list3);
											list2.add(map2);
										}
									}
									tag.put("list2", list2);
								}
								handler.sendEmptyMessage(4);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
		// count=new
		// int[]{R.drawable.welcome,R.drawable.bkg,R.drawable.bkg,R.drawable.welcome,R.drawable.defaultpic};
		sum_num.setText("" + listImgs.size());
		// if(count!=null &&count.length>0){
		// }

		fl_gr.setVisibility(View.VISIBLE);
		galleryAdapter = new GalleryAdapter(this, listImgs);
		gl.setAdapter(galleryAdapter);
		gl.setOnItemSelectedListener(this);
		gl.setOnItemClickListener(this);
	}

	/** 获取用户评论列表 */
	private void getCommodityComment(String methodName, RequestParams params) {
		AppContext.getHtmlUitls().xUtils(this, HttpMethod.GET, methodName,
				params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						if (prodCommentInfoBeanList == null) {
							prodCommentInfoBeanList = new ArrayList<Map<String, Object>>();
						}
						try {
							if (json.getBoolean("success")) {
								ParseJson.getParseJson().setDataList(
										"prodCommentInfoBeanList",
										json.getJSONObject("attributes"),
										prodCommentInfoBeanList);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						handler.sendEmptyMessage(3);

					}
				});
	}

	/** 获取店铺信息 */
	private void getShopInfo(String methodName, RequestParams params) {
		AppContext.getHtmlUitls().xUtils(this, HttpMethod.GET, methodName,
				params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						try {
							if (json.getBoolean("success")) {
								JSONObject jObject = json.getJSONObject(
										"attributes").getJSONObject("shopBean");
								MyFormat.setBitmap(ProductDetailActivity.this,
										shop_pic, jObject.getString("logo"), 0,
										0);
								shopBean = ParseJson.getParseJson().getMap(
										jObject, shopBean);

								handler.sendEmptyMessage(2);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}

	@OnClick({ R.id.turn, R.id.ellipsis, R.id.fresh, R.id.select, R.id.to_more,
			R.id._commenter, R.id.to_category, R.id.to_shop, R.id.buy,
			R.id.collect })
	public void myClick(View v) {
		if (!appContext.isNetworkConnected()) {
			toast(R.string.t_Connection);
			return;
		}
		switch (v.getId()) {
		case R.id.turn:// 返回
			finish();
			break;
		case R.id.ellipsis:// 更多
			if (appContext.isNetworkConnected()) {
				if (ellipticalsisWindow == null) {
					ellipticalsisWindow = new ProductDetailEllipsisPoPuWindow(
							this, this, 6, iv_show);
				}
				// iv_show.setVisibility(View.VISIBLE);
				ellipticalsisWindow.showAsDropDown(iv_show);
			} else {
				toast(R.string.t_Connection);
			}
			// iv_show.setVisibility(View.VISIBLE);
			ellipticalsisWindow.showAsDropDown(iv_show);
			// ellipticalsisWindow.showAtLocation(ll_parent, Gravity.RIGHT, 100,
			// 200);
			break;
		case R.id.fresh:// 刷新
			Toast.makeText(this, "fresh", Toast.LENGTH_SHORT).show();
			break;
		case R.id.select:// 尺寸
			if (dialog == null) {
				String img = "";
				if (listImgs != null && listImgs.size() > 0) {
					img = listImgs.get(0);
				}
				dialog = new ProductDetailSizeDialog(this, 1, list1, list2,
						img, this);
			}
			dialog.show();
			Window window = dialog.getWindow();
			WindowManager.LayoutParams lp = window.getAttributes();
			window.setGravity(Gravity.LEFT | Gravity.TOP);

			lp.x = AppContext.getScreenWidth() / 5;
			lp.y = 0;
			lp.width = AppContext.getScreenWidth() * 4 / 5;
			lp.height = AppContext.getScreenHeight();

			window.setAttributes(lp);

			break;
		case R.id.to_more:
			if (getAppContext().isNetworkConnected() && shopId.length() > 0) {
				Intent to_more = new Intent(this, WebAty.class);

				to_more.putExtra("prodSpecId", prodspecId);
				startActivity(to_more);
			} else {
				toast(R.string.t_Connection);
			}
			// Toast.makeText(this, "查看更多详情", Toast.LENGTH_SHORT).show();
			break;
		case R.id._commenter:// 跳转到评论页面
			if (appContext.isNetworkConnected()) {
				Intent intent = new Intent(this,
						ProductDetailCommentActivity.class);
				intent.putExtra("prodspecId", prodspecId);

				startActivity(intent);
			} else {
				toast(R.string.t_Connection);

			}

			break;
		case R.id.to_category:// 进入店铺查看宝贝

			if (shopId.length() > 0) {
				Intent toShop = new Intent(this, ShopActivity.class);
				toShop.putExtra("shopId", shopId);
				startActivity(toShop);
			} else {
				toast("获取店铺信息失败");
			}

			break;
		case R.id.to_shop:// 现场消费
			if (shopId.length() > 0) {
				Intent toShop = new Intent(this, ShopActivity.class);
				toShop.putExtra("shopId", shopId);
				startActivity(toShop);
			} else {
				toast("获取店铺信息失败");
			}
			break;
		case R.id.buy:// 立即购买
			if (appContext == null) {
				appContext = (AppContext) getApplicationContext();
			}
			if (!UserInformation.isLogin()) {
				Log.d("my", "立即购买");
				Toast.makeText(this, "未登录", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(this, WebLoginActivity.class));
				return;
			}
//			if (sum < 1) {
//				Toast.makeText(this, "请添加商品数量", Toast.LENGTH_SHORT).show();
//				return;
//			}
//			if (appContext.isNetworkConnected()) {
//				addCart(sum, 1);
//			} else {
//				Toast.makeText(this, R.string.t_Connection, Toast.LENGTH_SHORT)
//						.show();
//			}

			startActivity(new Intent(this, ShopCartActivity.class));
			break;
		case R.id.collect:// 加入购物车
			if (appContext == null) {
				appContext = (AppContext) getApplicationContext();
			}
			if (!UserInformation.isLogin()) {
				Toast.makeText(this, "未登录", Toast.LENGTH_SHORT).show();
				Log.d("my", "加入购物车");
				startActivity(new Intent(this, WebLoginActivity.class));
				return;
			}
			if (appContext.isNetworkConnected()) {
				if (sum < 1) {
					Toast.makeText(this, "请添加商品数量", Toast.LENGTH_SHORT).show();
					return;
				}
				addCart(sum, 2);
			} else {
				Toast.makeText(this, R.string.t_Connection, Toast.LENGTH_SHORT)
						.show();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		current_num.setText("" + ++arg2);

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

		Toast.makeText(this, "galleryitem", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.gl:

			// Toast.makeText(this, "galleryitem"+arg2,
			// Toast.LENGTH_SHORT).show();
			break;
		case R.id.mayby_youlike_lv:// 查询猜你喜欢商品
			if (getAppContext().isNetworkConnected()) {
				prodspecId = list_maybeYouLike.get(arg2).get("prodspecId")
						.toString();
				// prodspecId = "402880794fb02563014fb0410ba60013";
				getData(prodspecId);
			} else {
				toast(R.string.t_Connection);
			}

			break;

		default:
			break;
		}

	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		// Log.d("my", "onDown"+e.getY());
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		// Log.d("my", "onShowPress"+e.getY());
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		// Log.d("my", "onSingleTapUp"+e.getY());
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
		// Log.d("my", "onLongPress"+e.getY());
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		// if (detector != null) {
		// if (ll_up.onTouchEvent(ev))
		// //If the gestureDetector handles the event, a swipe has been executed
		// and no more needs to be done.
		// return true;
		// }

		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		// int a=(int) (e1.getY()-e2.getY());
		// Log.d("my", "onFling"+a);

		if (e1 != null && e2 != null && (int) (e1.getY() - e2.getY()) > 50) {
			Intent to_more = new Intent(this, WebAty.class);

			to_more.putExtra("prodSpecId", prodspecId);
			to_more.putExtra("shopId", shopId);
			startActivity(to_more);

		}
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		// detector.onTouchEvent(event);
		return detector.onTouchEvent(event);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub

		detector.onTouchEvent(event);
		// 一定要返回true ，不然获取不到完整的事件

		return true;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		GetProgressBar.dismissMyProgressBar();
	}

	@Override
	public void setSepc(String specId, String originalPrice, String price,
			int count, String names) {
		// TODO Auto-generated method stub
		sepcId = specId;

		sum = count;
		this.price.setText(price);
		old_price.setText(originalPrice);
		select.setText(names + count + "件");

	}

	/** 加入购物车 count 商品数量type=1,立即支付,type=2,加入购物车 */
	private void addCart(final int count, final int type) {
//		sepcId = specId;
//
//		sum = count;
//		this.price.setText(price);
//		old_price.setText(originalPrice);
//		select.setText(names + count + "件");
		Log.d("my", "加入购物车" + count + type+sepcId);
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token",UserInformation.getAccess_token());
		params.addQueryStringParameter("time",""+System.currentTimeMillis());
		params.addQueryStringParameter("specId", sepcId);
		params.addQueryStringParameter("forwardId",
				getIntent().getStringExtra("forwardId"));
		params.addQueryStringParameter("chain",
				getIntent().getStringExtra("chain"));
		params.addQueryStringParameter("count", String.valueOf(count));
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/Cart/addCart", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

						if (msg.equals("401")) {
							Toast.makeText(ProductDetailActivity.this, "未登录",
									Toast.LENGTH_SHORT).show();
							setRefreshListtener(new Refresh() {
								
								@Override
								public void refreshRequst(String access_token) {
									// TODO Auto-generated method stub
									addCart( count,  type);
								}
							});
							RefeshToken();
						}
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						try {
							if (!json.getBoolean("success")) {
								if (json.getString("msg") != null) {

									Toast.makeText(ProductDetailActivity.this,
											json.getString("msg"),
											Toast.LENGTH_SHORT).show();
								}
							} else {
								if (type == 2) {

									Toast.makeText(ProductDetailActivity.this,
											"已加入购物车", Toast.LENGTH_SHORT)
											.show();
								} else {
									handler.sendEmptyMessage(5);
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
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/Cart/" + method, params, new HttpCallback() {

					@Override
					public void onError(String msg) {
if(msg.equals(401)){
	setRefreshListtener(new Refresh() {
		
		@Override
		public void refreshRequst(String access_token) {
			// TODO Auto-generated method stub
			getCart(method, access_token);
		}
	});
	RefeshToken();
	
}
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub

						try {
							if (!json.getBoolean("success")
									&& json.getString("errorCode").equals(
											"NOT_LOGIN")) {
								setRefreshListtener(new Refresh() {
									
									@Override
									public void refreshRequst(String access_token) {
										// TODO Auto-generated method stub
										getCart(method, access_token);
									}
								});
								RefeshToken();

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
								handler.sendEmptyMessage(6);
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}

	/**
	 * url
	 * 
	 * @param type
	 *            :1 微信,2朋友圈
	 */
	private void shareUrl(int type) {
		WXWebpageObject webpage = new WXWebpageObject();
		String url = getResources().getString(R.string.buyserver)+"pages/m/prod/m_";
		// url="http://192.168.0.249:8082/llgweb/pages/m/prod/m_";
		String chain = "";
		if (getIntent().getStringExtra("chain") != null) {
			if (AppContext.userid.length() > 0) {
				chain = getIntent().getStringExtra("chain");
			String chains[]=	chain.split(",");
			String tem="";
			for (int i = 0; i < chains.length; i++) {
				
				if(chains[i].equals(AppContext.userid)){
					tem=chains[i];
					break;
				}
			}
			if(tem.length()<1){
				chain = getIntent().getStringExtra("chain") + ","
						+ AppContext.userid;}
			} else {
				chain = getIntent().getStringExtra("chain");
			}
		} else {
			chain = AppContext.userid;
		}
		String forwardId = "";
		if (getIntent().getStringExtra("forwardId") != null) {
			forwardId = getIntent().getStringExtra("forwardId");
		}

		url = url + prodspecId + ".html?" + "chain=" + chain + "&forwardId="
				+ forwardId;
		Log.d("my", "分享:" + url);
		webpage.webpageUrl = url;
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = "风购全球";
		msg.description = to_more.getText().toString();
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

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	@Override
	public void setMessage(String message, int type) {
		// TODO Auto-generated method stub
		if (message.equals("分享到朋友圈")) {
			toast(message);
			shareUrl(2);
		} else if (message.equals("分享给微信好友")) {
			toast(message);
			shareUrl(1);
		} else if (message.equals("关注")) {
			attion();
		}
	}

	/*** 关注 */
	private void attion() {
		if (!AppContext.isLogin) {
			Toast.makeText(this, "关注前请先登录", Toast.LENGTH_SHORT).show();
			Log.d("my", "关注");
			startActivity(new Intent(this, WebLoginActivity.class));
			return;
		}
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", UserInformation.getAccess_token());
		params.addQueryStringParameter("type", String.valueOf(1));
		params.addQueryStringParameter("objectId", prodspecId);// objectId
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/attention/addAttention", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						try {
							if (json.getBoolean("success")) {
								Toast.makeText(ProductDetailActivity.this,
										"已经关注", Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(ProductDetailActivity.this,
										json.getString("msg"),
										Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				});
	}

}
