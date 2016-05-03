package com.llg.privateproject.actvity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.sharesdk.framework.authorize.b;

import com.handmark.pulltorefresh.library.OverscrollHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.llg.privateproject.AppContext;
import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.adapters.ProductDetailMoreParmasAdapter;
import com.llg.privateproject.adapters.ProductDetailMoreHotsAdapter;
import com.llg.privateproject.adapters.ProductDetailMorePicAdapter;
import com.llg.privateproject.entities.HuoDongZhuanTiEntity;
import com.llg.privateproject.entities.MaybeYouLike;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.view.MyGridView;
import com.llg.privateproject.view.MyPullToRefreshScrollView;
import com.llg.privateproject.view.MyPullToRefreshScrollView.MyPullToRefreshScrollViewListener;
import com.llg.privateproject.view.ProductDetailEllipsisPoPuWindow;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 更多详情对话框 yh 2015.6.22
 * 
 * */
public class ProductDetailMoreActivity extends BaseActivity implements
		android.view.View.OnClickListener, OnCheckedChangeListener,
		OnItemClickListener, MyPullToRefreshScrollViewListener {
	Context context = this;
	/** 顶部导航栏布局 */
	@ViewInject(R.id.product_detail_head_rl)
	private RelativeLayout product_detail_head_rl;
	/** 返回按钮 */
	@ViewInject(R.id.turn)
	private ImageView turn;
	/** 刷新 */
	@ViewInject(R.id.fresh)
	private ImageView fresh;
	/** 标题 */
	@ViewInject(R.id.head_tilte)
	private TextView head_tilte;
	/** 更多 */
	@ViewInject(R.id.ellipsis)
	private ImageView ellipsis;
	/** 小三角 */
	@ViewInject(R.id.iv_show)
	private ImageView iv_show;
	/** 更多弹窗口 */
	private ProductDetailEllipsisPoPuWindow ellipticalsisWindow1;
	/** 商品更多详情选择rg */
	@ViewInject(R.id.rg)
	private RadioGroup rg;
	/** 商品图文RadioButton */
	@ViewInject(R.id.rb_pics)
	private RadioButton rb_pics;
	/** 商品参数RadioButton */
	@ViewInject(R.id.rb_params)
	private RadioButton rb_params;
	/** 商品热销RadioButton */
	@ViewInject(R.id.rb_hot)
	private RadioButton rb_hot;
	int position = 0;
	int indictorwidth = AppContext.getScreenWidth() / 3;
	int hotCount = 1;
	int picCount = 1;
	int y1 = 0;
	int y2 = 0;
	/** 指示剂 */
	@ViewInject(R.id.indictor)
	private LinearLayout indictor;
	/** scrollview */
	@ViewInject(R.id.scrollview)
	private MyPullToRefreshScrollView scrollview;
	/** 图片数组 */
	int pics[];
	/** 图片数据配置 */
	private List<HuoDongZhuanTiEntity> listPics = null;

	/** 图片数据适配器 */

	ProductDetailMorePicAdapter picsAdapter;
	/** 图文详情列表 */
	@ViewInject(R.id.mgv_pics)
	private MyGridView mgv_pics;
	/** 产品参数listview列表 */
	@ViewInject(R.id.mgv_params)
	private MyGridView mgv_params;
	/** 参数列表详情 */
	List<MaybeYouLike> mgv_params_list;
	/** 参数列表适配器 */
	ProductDetailMoreParmasAdapter mgv_params_list_Adapter;
	/** 本店热销gridview列表 */
	@ViewInject(R.id.mgv_hots)
	private MyGridView mgv_hots;
	/** 回到顶部 */
	@ViewInject(R.id.iv_totop)
	private ImageView iv_totop;
	/** 立即购买 */
	@ViewInject(R.id.buy)
	private TextView buy;
	/** 加入购物车 */
	@ViewInject(R.id.collect)
	private TextView collect;
	/** 本店热门详情 */
	List<MaybeYouLike> hotlist = new ArrayList<MaybeYouLike>();
	/** 本店热销适配器 */
	ProductDetailMoreHotsAdapter hotAdapter;
	/** 店铺id */
	String shopId = "";
	/** 规格id */
	String prodSpecId = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		init();
	}

	void init() {
		setContentView(R.layout.product_detail_more);
		ViewUtils.inject(this);
		scrollview.setListener(this);
		head_tilte.setText("宝贝详情");
		turn.setOnClickListener(this);
		fresh.setOnClickListener(this);
		ellipsis.setOnClickListener(this);
		buy.setOnClickListener(this);
		collect.setOnClickListener(this);
		rg.setOnCheckedChangeListener(this);
		iv_totop.setOnClickListener(this);
		mgv_hots.setOnItemClickListener(this);
		shopId = getIntent().getStringExtra("shopId");
		prodSpecId = getIntent().getStringExtra("prodSpecId");
		mgv_params_list = new ArrayList<MaybeYouLike>();
		testPicsData();
		testParamdata();
		testGuangGaoJiFenData();
		setIndictor(position);

		scrollview.setOnRefreshListener(new OnRefreshListener2<ScrollView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ScrollView> refreshView) {
				// TODO Auto-generated method stub
				pulldown();

				scrollview.onRefreshComplete();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ScrollView> refreshView) {
				// TODO Auto-generated method stub
				pullUp();
				scrollview.onRefreshComplete();
			}
		});

	}

	/** scrollview下拉刷新数据 */
	private void pulldown() {
		if (rb_pics.isChecked()) {
			listPics.clear();
			for (int i = 0; i < 4; i++) {
				picCount = 0;
				HuoDongZhuanTiEntity entity = new HuoDongZhuanTiEntity();
				entity.id = "id" + picCount;
				entity.name = "name" + picCount;
				entity.endTime = "endTime" + picCount;
				entity.img = "img" + picCount;
				entity.url = "url" + picCount;
				listPics.add(entity);
			}
			// Log.d("my", "listPics.size()"+listPics.size());
			picsAdapter.notifyDataSetChanged();
		} else if (rb_params.isChecked()) {

		} else if (rb_hot.isChecked()) {
			hotlist.clear();
			hotCount = 0;
			for (int i = 0; i < 8; i++) {
				hotCount++;
				// MaybeYouLike entity=new MaybeYouLike("id"+hotCount,
				// "url"+ hotCount, "描述信息"+hotCount, "img"+ hotCount, "￥35"
				// +hotCount,"price"+hotCount);
				//
				// hotlist.add(entity);
			}
			// Log.d("my", "hotlist.size()"+hotlist.size());
			hotAdapter.notifyDataSetChanged();
		}
	}

	/** 上拉添加数据 */
	private void pullUp() {
		if (rb_pics.isChecked()) {
			// listPics.clear();
			iv_totop.setVisibility(View.VISIBLE);
			for (int i = 0; i < 4; i++) {
				picCount++;
				HuoDongZhuanTiEntity entity = new HuoDongZhuanTiEntity();
				entity.id = "id" + picCount;
				entity.name = "name" + picCount;
				entity.endTime = "endTime" + picCount;
				entity.img = "img" + picCount;
				entity.url = "url" + picCount;
				listPics.add(entity);
			}
			picsAdapter.notifyDataSetChanged();
		} else if (rb_params.isChecked()) {

		} else if (rb_hot.isChecked()) {
			// hotlist.clear();
			// hotCount=0;
			iv_totop.setVisibility(View.VISIBLE);
			for (int i = 0; i < 8; i++) {
				hotCount++;
				// MaybeYouLike entity=new MaybeYouLike("id"+hotCount,
				// "url"+ hotCount, "描述信息"+hotCount, "img"+ hotCount, "￥35"
				// +hotCount,"price"+hotCount);

				// hotlist.add(entity);
			}
			hotAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.turn:
			// cancel();
			finish();
			break;
		case R.id.ellipsis:
			if (ellipticalsisWindow1 == null) {
				ellipticalsisWindow1 = new ProductDetailEllipsisPoPuWindow(
						this, iv_show);
			}
			iv_show.setVisibility(View.VISIBLE);
			ellipticalsisWindow1.showAsDropDown(iv_show);
			// ellipticalsisWindow.showAtLocation(ll_parent, Gravity.RIGHT, 100,
			// 200);
			break;
		case R.id.fresh:
			Toast.makeText(context, "fresh", Toast.LENGTH_SHORT).show();
			break;
		case R.id.iv_totop:
			scrollview.getRefreshableView().smoothScrollTo(0, 0);// getRefreshableView().setSelection(0)
			iv_totop.setVisibility(View.GONE);
			// Toast.makeText(context, "toTop", Toast.LENGTH_SHORT).show();
			break;
		case R.id.buy:
			Toast.makeText(context, "立即购买", Toast.LENGTH_SHORT).show();
			break;
		case R.id.collect:
			Toast.makeText(context, "加入购物车", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

	/** 设置指示剂的宽度 */
	private void setIndictor(int position) {
		FrameLayout.LayoutParams lp = (android.widget.FrameLayout.LayoutParams) indictor
				.getLayoutParams();
		lp.width = indictorwidth;
		lp.leftMargin = indictorwidth * position;
		indictor.setLayoutParams(lp);
	}

	/** 图文测试数据 */
	void testPicsData() {
		// pics=new
		// int[]{R.drawable.welcome,R.drawable.bkg,R.drawable.bkg,R.drawable.welcome,R.drawable.defaultpic};
		// picsAdapter=new GalleryAdapter(context, pics);
		// mgv_pics.setAdapter(picsAdapter);
		listPics = new ArrayList<HuoDongZhuanTiEntity>();
		for (int i = 0; i < 4; i++) {
			HuoDongZhuanTiEntity entity = new HuoDongZhuanTiEntity();
			entity.id = "id" + i;
			entity.name = "name" + i;
			entity.endTime = "endTime" + i;
			entity.img = "img" + i;
			entity.url = "url" + i;
			listPics.add(entity);
			picsAdapter = new ProductDetailMorePicAdapter(this, listPics);
			mgv_pics.setAdapter(picsAdapter);
			mgv_pics.setVisibility(View.VISIBLE);
			mgv_params.setVisibility(View.GONE);
			mgv_hots.setVisibility(View.GONE);
		}

	}

	/** 参数列表测试数据 */
	void testParamdata() {
		mgv_params_list = new ArrayList<MaybeYouLike>();
		RequestParams params = new RequestParams();

		params.addQueryStringParameter("prodSpecId", prodSpecId);
		AppContext.getHtmlUitls().xUtils(this, HttpMethod.GET,
				"getCommodityAttribute", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						toast(msg);
					}

					@Override
					public void onBack(JSONObject json) {
						try {
							if (json.getBoolean("success")) {
								JSONArray kvList = json.getJSONObject(
										"attributes").getJSONArray("kvList");
								if (kvList.length() > 0) {
									for (int i = 0; i < kvList.length(); i++) {
										MaybeYouLike itemLike = new MaybeYouLike(
												kvList.getJSONObject(i)
														.getString("key"),
												kvList.getJSONObject(i)
														.getString("value"));
										mgv_params_list.add(itemLike);
									}

								}
								if (mgv_params_list_Adapter != null)
									mgv_params_list_Adapter
											.notifyDataSetChanged();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
		mgv_params_list_Adapter = new ProductDetailMoreParmasAdapter(this,
				mgv_params_list);// 猜你喜欢适配器
		mgv_params.setAdapter(mgv_params_list_Adapter);

	}

	/** 本店热门产品测试数据 */
	void testGuangGaoJiFenData() {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("shopId", shopId);
		AppContext.getHtmlUitls().xUtils(this, HttpMethod.GET,
				"getShopHotCommoditys", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						toast(msg);
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						try {
							if (json.getBoolean("success")) {

								JSONArray shopHotCommoditys = json
										.getJSONObject("attributes")
										.getJSONArray("shopHotCommoditys");
								if (shopHotCommoditys.length() > 0) {
									for (int i = 0; i < shopHotCommoditys
											.length(); i++) {
										JSONArray itemArray = shopHotCommoditys
												.getJSONArray(i);
										MaybeYouLike entity = new MaybeYouLike(
												itemArray.getString(0),// 商品名content
												itemArray.getString(1),// prodSpecId
																		// id
												itemArray.getString(4), // price
												null, // objectId
												null,// name
												itemArray.getString(3),// img
												null, null);

										hotlist.add(entity);
									}
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
		hotlist = new ArrayList<MaybeYouLike>();

		hotAdapter = new ProductDetailMoreHotsAdapter(this, hotlist);// 猜你喜欢适配器
		mgv_hots.setAdapter(hotAdapter);

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int id) {
		// TODO Auto-generated method stub

		switch (id) {
		case R.id.rb_pics:
			position = 0;
			mgv_pics.setVisibility(View.VISIBLE);
			mgv_params.setVisibility(View.GONE);
			mgv_hots.setVisibility(View.GONE);

			break;
		case R.id.rb_params:
			position = 1;
			mgv_pics.setVisibility(View.GONE);
			mgv_params.setVisibility(View.VISIBLE);
			mgv_hots.setVisibility(View.GONE);

			break;
		case R.id.rb_hot:
			mgv_pics.setVisibility(View.GONE);
			mgv_params.setVisibility(View.GONE);
			mgv_hots.setVisibility(View.VISIBLE);
			position = 2;
			break;

		default:
			break;
		}
		iv_totop.setVisibility(View.GONE);
		setIndictor(position);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Toast.makeText(context, "item:" + position, Toast.LENGTH_SHORT).show();

	}

	@Override
	public void setScrollLoction(int deltaX, int deltaY, int scrollX,
			int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY) {
		// TODO Auto-generated method stub
		Log.d("my", "53.28dp"
				+ getResources().getDimension(R.dimen.dimen_120px));
		Log.d("my", "40dp" + getResources().getDimension(R.dimen.dimen_40px));
		if (scrollY > 250) {
			product_detail_head_rl.setVisibility(View.GONE);
		} else if (scrollY < 150) {
			product_detail_head_rl.setVisibility(View.VISIBLE);
		}
		// Log.d("my", "OverScroll. DeltaX: " + deltaX + ", ScrollX: " + scrollX
		// + ", DeltaY: " + deltaY
		// + ", ScrollY: " + scrollY + ", scrollRangeX: " + scrollRangeX +
		// ", maxOverScrollX: " + maxOverScrollX
		// + ", maxOverScrollY: " + maxOverScrollY);

	}

}
