package com.llg.privateproject.actvity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.llg.help.GetProgressBar;
import com.llg.help.SetListHeigt;
import com.llg.privateproject.AppContext;
import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.adapters.CommentAdapter;
import com.llg.privateproject.entities.Commententity;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.view.ProductDetailEllipsisPoPuWindow;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 品论界面 yh 2015.6.23
 * 
 * */
public class ProductDetailCommentActivity extends BaseActivity implements
		OnClickListener, OnCheckedChangeListener {
	/** 标题 */
	@ViewInject(R.id.head_tilte)
	private TextView head_tilte;
	/** 返回按钮 */
	@ViewInject(R.id.turn)
	private ImageView turn;
	/** 刷新 */
	@ViewInject(R.id.fresh)
	private ImageView fresh;
	/** 更多 */
	@ViewInject(R.id.ellipsis)
	private ImageView ellipsis;
	/** 小三角 */
	@ViewInject(R.id.iv_show)
	private ImageView iv_show;
	/** 更多弹窗口 */
	private ProductDetailEllipsisPoPuWindow ellipticalsisWindow1;
	/** 商品评论条件选择rg */
	@ViewInject(R.id.rg)
	private RadioGroup rg;
	/** 商品全部评论 */
	@ViewInject(R.id.rb_all)
	private RadioButton rb_all;
	/** 商品好评评论 */
	@ViewInject(R.id.tv_good)
	private RadioButton tv_good;
	/** 商品中评评论 */
	@ViewInject(R.id.tv_middle)
	private RadioButton tv_middle;
	/** 商品差评评论 */
	@ViewInject(R.id.tv_bad)
	private RadioButton tv_bad;
	/** 商品有图片评论 */
	@ViewInject(R.id.tv_haspic)
	private RadioButton tv_haspic;
	/** 商品评论列表 */
	@ViewInject(R.id.gv_comments)
	private PullToRefreshListView gv_comments;
	/** 商品评论列表适配器 */
	private CommentAdapter adapter;
	int position = 0;
	int indictorwidth = AppContext.getScreenWidth() / 5;
	/** 所有评论列表 */
	List<Commententity> listAll;
	/** 好评列表 */
	List<Commententity> listGood;
	/** 中评列表 */
	List<Commententity> listMiddle;
	/** 差评列表 */
	List<Commententity> listBad;
	/** 有图列表 */
	List<Commententity> listHaspics;
	/** 部分评论列表 */
	List<Commententity> listPart;
	int count[] = new int[5];
	@ViewInject(R.id.iv_totop)
	private ImageView iv_totop;
	/** 立即购买 */
	@ViewInject(R.id.buy)
	private TextView buy;
	/** 加入购物车 */
	@ViewInject(R.id.collect)
	private TextView collect;
	/** 设置gridview高度 */
	private SetListHeigt setHeigt;
	/** 指示剂 */
	@ViewInject(R.id.indictor)
	private LinearLayout indictor;
	String prodSpecId = "";
	int pageNo = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_detail_comment);
		ViewUtils.inject(this);
		init();
	}

	private void init() {
		setHeigt = new SetListHeigt();
		head_tilte.setText("宝贝评价");
		rg.setOnCheckedChangeListener(this);
		turn.setOnClickListener(this);
		fresh.setOnClickListener(this);
		fresh.setVisibility(View.GONE);
		ellipsis.setVisibility(View.GONE);
		ellipsis.setOnClickListener(this);
		buy.setOnClickListener(this);
		collect.setOnClickListener(this);
		listAll = new ArrayList<Commententity>();
		listPart = new ArrayList<Commententity>();
		listGood = new ArrayList<Commententity>();
		listMiddle = new ArrayList<Commententity>();
		listBad = new ArrayList<Commententity>();
		listHaspics = new ArrayList<Commententity>();

		// setHeigt.setlistHeight(gv_comments);
		// adapter.notifyDataSetChanged();
		RequestParams params = new RequestParams();
		prodSpecId = getIntent().getStringExtra("prodspecId");
		params.addQueryStringParameter("prodSpecId", prodSpecId);// intent.putExtra("prodspecId",
																	// prodspecId);

		params.addQueryStringParameter("pageNo", String.valueOf(pageNo));

		testCommentdata("getCommodityComment", params);
		// (int)(Math.random()*5)+1
		setIndictor(position);

	}

	/** 评论列表测试数据 */
	void testCommentdata(String method, RequestParams params) {
		GetProgressBar.getProgressBar(this);

		AppContext.getHtmlUitls().xUtils(this, HttpMethod.GET, method, params,
				new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						GetProgressBar.dismissMyProgressBar();
						toast(msg);
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						GetProgressBar.dismissMyProgressBar();

						try {
							if (json.getBoolean("success")) {
								JSONArray jsonArray = json.getJSONObject(
										"attributes").getJSONArray(
										"prodCommentInfoBeanList");
								if (jsonArray.length() > 0) {

									for (int i = 0; i < jsonArray.length(); i++) {
										JSONObject jObject = jsonArray
												.getJSONObject(i);
										JSONArray type = jObject
												.getJSONArray("type");
										String sizeString = "";
										String color = "";

										for (int j = 0; j < type.length(); j++) {
											JSONObject jsb = type
													.getJSONObject(j);
											if (j == 0) {
												sizeString = jsb
														.getString("key")
														+ ":"
														+ jsb.getString("value");
											}
											if (j == 1) {
												color = jsb.getString("key")
														+ ":"
														+ jsb.getString("value");

											}

										}

										List<String> listimg = new ArrayList<String>();
										// if( jObject//
										// .getJSONArray("imgs")!=null&&
										// !jObject
										// .getJSONArray("imgs").equals("null")){
										// JSONArray jArray1 = jObject
										// .getJSONArray("imgs");
										// Log.d("my",
										// "jArray1.length()"+jArray1.length());
										// if(jArray1.length()>0){
										// for (int j = 0; j < jArray1.length();
										// j++) {
										// listimg.add(jArray1.getString(j));
										// }}}
										Commententity entity = new Commententity(
												jObject.getString("appellation"),
												jObject.getString("avatar"),
												jObject.getString("content"),
												"等级",
												null,// 评论时间
												jObject.getString("date"),
												sizeString, color, jObject
														.getString("score"),
												"状态", listimg);
										listAll.add(entity);
									}
								}
								Log.d("my", "listAll.size()" + listAll.size());
								adapter = new CommentAdapter(
										ProductDetailCommentActivity.this,
										listAll);
								// gv_comments.getRefreshableView().setAdapter(adapter);
								gv_comments.setAdapter(adapter);
								gv_comments.setVisibility(View.VISIBLE);
								// setHeigt.setlistHeight(gv_comments.getRefreshableView());
								adapter.notifyDataSetChanged();
								Log.d("my",
										"listAll.size()" + adapter.getCount());
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
		int imgHeads[] = { R.drawable.bkg_head1, R.drawable.bkg_head2,
				R.drawable.bkg_head3 };
		int imgs[] = { R.drawable.welcome, R.drawable.defaultpic,
				R.drawable.welcome, R.drawable.bkg, R.drawable.defaultpic };
		// listAll.clear();
		listPart.clear();
		listGood.clear();
		listMiddle.clear();
		listBad.clear();
		listHaspics.clear();
		String colors[] = { "红色", "绿色", "白色", "蓝色", "黄色" };

		for (int i = 0; i < (int) (Math.random() * 16) + 35; i++) {
			int m = new Random().nextInt(4) + 2;
			// Log.d("my", ""+m);

			// new Commententity(petName, imgHead, content, grade, imgs,
			// commentTime, size, color, score, status)
			// Commententity entity = new Commententity("昵称" + i, // 昵称
			// imgHeads[(int) Math.random() * 3 + 1],// 头像
			// "评价内容" + i, "" + (int) Math.random() * 3 + 1, // 等级
			// imgs,// 图片
			// new Date().toString(),// 评论时间
			// (int) (Math.random() * 36) + 7 + "",// 尺寸
			// "" + colors[(int) (Math.random() * 4) + 1], // 颜色
			// "5", "" + m,null);// 什么评价
			// switch (Integer.parseInt(entity.status)) {
			//
			// case 2:
			// listGood.add(entity);
			// break;
			// case 3:
			// listMiddle.add(entity);
			//
			// break;
			// case 4:
			// listBad.add(entity);
			//
			// break;
			// case 5:
			// listHaspics.add(entity);
			//
			// break;
			//
			// default:
			// break;
			// }
			// listAll.add(entity);
			//
			// listPart.add(entity);
		}
		// adapter.notifyDataSetChanged();

		rb_all.setText("全部" + "\n" + "(" + listAll.size() + ")");
		tv_good.setText("好评" + "\n" + "(" + listGood.size() + ")");
		tv_middle.setText("中评" + "\n" + "(" + listMiddle.size() + ")");
		tv_bad.setText("差评" + "\n" + "(" + listBad.size() + ")");
		tv_haspic.setText("有图" + "\n" + "(" + listHaspics.size() + ")");
		position = 0;
		setIndictor(position);
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
			listAll.clear();
			RequestParams params = new RequestParams();
			params.addQueryStringParameter("prodSpecId", prodSpecId);// intent.putExtra("prodspecId",
																		// prodspecId);
			pageNo = 1;
			params.addQueryStringParameter("pageNo", String.valueOf(pageNo));

			testCommentdata("getCommodityComment", params);

			if (position == 0) {
				rb_all.setChecked(true);
				tv_good.setChecked(false);
				tv_middle.setChecked(false);
				tv_bad.setChecked(false);
				tv_haspic.setChecked(false);
			}
			Toast.makeText(this, "fresh", Toast.LENGTH_SHORT).show();
			break;
		case R.id.buy:
			Toast.makeText(this, "立即购买", Toast.LENGTH_SHORT).show();
			break;
		case R.id.collect:
			Toast.makeText(this, "加入购物车", Toast.LENGTH_SHORT).show();
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

	@Override
	public void onCheckedChanged(RadioGroup group, int id) {
		// TODO Auto-generated method stub
		// listPart.clear();
		switch (id) {
		case R.id.rb_all:
			position = 0;

			updateListpart(listAll);
			break;
		case R.id.tv_good:
			position = 1;
			updateListpart(listGood);
			break;
		case R.id.tv_middle:
			position = 2;
			updateListpart(listMiddle);
			break;
		case R.id.tv_bad:
			position = 3;
			updateListpart(listBad);
			break;
		case R.id.tv_haspic:
			position = 4;
			updateListpart(listHaspics);
			break;

		default:
			break;
		}
		// Log.d("my", "listpart.size()"+listPart.size());
		// if (adapter == null) {
		// adapter = new CommentAdapter(this, listPart);
		// }
		// gv_comments.setAdapter(adapter);
		// adapter.notifyDataSetChanged();
		setIndictor(position);
	}

	/** 更新listpart数 */
	void updateListpart(List<Commententity> list) {
		listPart.clear();
		for (int i = 0; i < list.size(); i++) {
			listPart.add(list.get(i));
		}
		adapter.notifyDataSetChanged();
		// setHeigt.setlistHeight(gv_comments);
	}
}
