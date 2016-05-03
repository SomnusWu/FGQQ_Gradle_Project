package com.llg.privateproject.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bjg.lcc.privateproject.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.llg.help.Fgqqdb;
import com.llg.help.GetProgressBar;
import com.llg.help.MyFormat;
import com.llg.help.SetListHeigt;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.actvity.CommodityActivity;
import com.llg.privateproject.actvity.ProductDetailActivity;
import com.llg.privateproject.actvity.SearchActivity;
import com.llg.privateproject.actvity.ShopActivity;
import com.llg.privateproject.adapter.CommodityAdapter;
import com.llg.privateproject.adapters.InformationItemAdapter;
import com.llg.privateproject.adapters.ShoplistAdapter;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.utils.Constants;
import com.llg.privateproject.utils.StringUtils;
import com.llg.privateproject.view.MyGridView;
import com.smartandroid.sa.aysnc.Log;
import com.smartandroid.sa.view.AutoLoading;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 获取创业者推荐的店铺
 * 
 * @author yh
 * */
public class RecommendCommodities extends BaseFragment {
	private static final String TAG = "RecommendCommodities";
	private Context context;
	/** 设置标签颜色 */
	@ViewInject(R.id.ll_zonghe)
	private LinearLayout ll_zonghe;
	/** 条件筛选 */
	@ViewInject(R.id.condition)
	private LinearLayout condition;
	/** 条件筛选 */
	@ViewInject(R.id.v)
	private View v;
	/**  */
	@ViewInject(R.id.ly)
	private LinearLayout ly;
	@ViewInject(R.id.tv_zonghe)
	private TextView tv_zonghe;
	@ViewInject(R.id.recommend)
	private TextView recommend;
	@ViewInject(R.id.iv_zonghe)
	private ImageView iv_zonghe;
	@ViewInject(R.id.erji_fanhui)
	private ImageView erji_fanhui;

	@ViewInject(R.id.tv_xiaoliang)
	private TextView tv_xiaoliang;

	@ViewInject(R.id.ll_jiage)
	private LinearLayout ll_jiage;
	@ViewInject(R.id.tv_jiage)
	private TextView tv_jiage;
	@ViewInject(R.id.price_up)
	private ImageView price_up;
	@ViewInject(R.id.price_down)
	private ImageView price_down;

	@ViewInject(R.id.ll_shaixuan)
	private LinearLayout ll_shaixuan;
	@ViewInject(R.id.tv_shaixuan)
	private TextView tv_shaixuan;
	@ViewInject(R.id.iv_shaixuan)
	private ImageView iv_shaixuan;
	private AutoLoading autoLoading;
	private Fgqqdb fgqqdb;
	private Set<String > set;
	@Override
	public String getFragmentName() {
		// TODO Auto-generated method stub
		return TAG;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		context = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.home_commodity, container, false);
		// 在使用注解绑定控件的时候，一定记得在onCreate中调用ViewUtils.inject(this);
		ly = (LinearLayout) view.findViewById(R.id.ly);
		// autoLoading=new AutoLoading(context, ly);
		// autoLoading.showLoadingLayout();
		ViewUtils.inject(this, view);
		condition.setVisibility(View.GONE);
		recommend.setVisibility(View.VISIBLE);
		v.setVisibility(View.VISIBLE);
		// autoLoading.setClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// getCommodityData("common/getEpShops", ""+currentPage);
		// }
		// });
		erji_fanhui.setVisibility(View.GONE);
		home_search_id
				.setHintTextColor(getResources().getColor(R.color.black3));
		layoutListView.setNumColumns(2);
		initData();
		return view;
	}

	/** 商品数据集合 */
	private List<Map<String, Object>> listData = null;

	/** 商品列表适配器 */
	private ShoplistAdapter adapter = null;
	@ViewInject(R.id.commodity_id)
	private MyGridView layoutListView;

	/** 切换展示方式 */
	@ViewInject(R.id.pulltorefreshscrollview)
	private PullToRefreshScrollView pulltorefreshscrollview;
	/** 切换展示方式 */
	@ViewInject(R.id.home_sweep_id)
	private ImageView home_sweep_id;
	/** 搜索框 */
	@ViewInject(R.id.home_search_id)
	private TextView home_search_id;
	/** 二级分类id */
	private String parentId = "";
	/** 商品总页数 */
	private int totalPages = 5;
	/** 条数 */
	private int dd = 10;
	/** 是否单列显示 */
	private boolean isSingle = false;

	/** 全局Context */
	private AppContext appContext;
	/** 查询当前页 */
	private int currentPage = 1;
	private SetListHeigt setHeigt;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			GetProgressBar.dismissMyProgressBar();
			switch (msg.what) {
			case Constants.SUCCESS:
				/** 刷新列表 */
			
				setitem();
			
				getCommodityData("common/getEpShops", "" + currentPage);
				break;
			case Constants.REFRESH:
				/** 刷新分类产品列表 */
				if (setHeigt == null) {
					setHeigt = new SetListHeigt();
				}
				setitem();
				setHeigt.setlistHeight(layoutListView);
				Log.d("my", "set.toString()"+set.toString());
				break;
			case Constants.FAILED:
				/** 提示显示 */
				getCommodityData("common/getEpShops", "" + currentPage);

				Toast.makeText(context, msg.obj.toString(), Toast.LENGTH_SHORT)
						.show();
				adapter.notifyDataSetInvalidated();
				break;
			}
		}

		private void setitem() {
			Map<String, Object> map1;
			Iterator< String> it=set.iterator();
			listData.clear();
			while(it.hasNext()){
				try {
					JSONObject obj1=new JSONObject(it.next());
					map1=new HashMap<String, Object> ();
					addData(map1, obj1);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			adapter.notifyDataSetInvalidated();
		}
	};

	public static RecommendCommodities newInstance() {
		RecommendCommodities recommendCommodities = new RecommendCommodities();
		return recommendCommodities;
	}

	/** 初始化配置 */
	private void initData() {
		 fgqqdb=new  Fgqqdb() ;
		try {
			pulltorefreshscrollview.setMode(Mode.BOTH);
			listData = new ArrayList<Map<String, Object>>();
			set=new HashSet<String>();
			pulltorefreshscrollview
					.setOnRefreshListener(new OnRefreshListener() {

						@Override
						public void onRefresh(PullToRefreshBase refreshView) {
							// TODO Auto-generated method stub
							
							getCommodityData("common/getEpShops",
									String.valueOf(currentPage++));

						}
					});
		} catch (Exception e) {
			LogUtils.e("转换异常", e);
		}
		intiView();

		initDisplay();

	}

	/** 初始化界面 */
	private void intiView() {
		// testCommodityData();
		
		adapter = new ShoplistAdapter(context, listData, isSingle);
		layoutListView.setAdapter(adapter);
		
	try {
			
			if(fgqqdb.selectData("c").length()>10){
				JSONObject obj=new JSONObject(fgqqdb.selectData("c"));
				
				getdata("common/getEpShops",obj);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
	}

	/** 网络验证数据请求 */
	private void initDisplay() {
		appContext = (AppContext) getActivity().getApplication();
		if (appContext.isNetworkConnected()) {
			currentPage = 1;
			getCommodityData("m/epShop/getShopsss", String.valueOf(currentPage));
		} else {
			Message _Msg = mHandler.obtainMessage(Constants.FAILED,
					getResources().getString(R.string.t_Connection));
			mHandler.sendMessage(_Msg);
		}
	}

	/***
	 * 查询创业者产品数据
	 * 
	 * @param
	 * 
	 */
	private void getCommodityData(final String method, final String currentPage) {
		if (!appContext.isNetworkConnected()) {
			// autoLoading.showInternetOffLayout();
			toast(R.string.t_Connection);
			return;
		}
		Log.d("my", "currentPage"+currentPage);
Log.d("my", "totalPages"+totalPages);
		if (method.equals("common/getEpShops")
				&& Integer.parseInt(currentPage) > totalPages) {
			toast("暂无更多");
			pulltorefreshscrollview.onRefreshComplete();
			return;
		}
		// autoLoading.showLoadingLayout();

		RequestParams params = new RequestParams();
		// params.addQueryStringParameter("categoryId", parentId);

		params.addQueryStringParameter("access_token", UserInformation.getAccess_token());
		params.addQueryStringParameter("pageNo", String.valueOf(currentPage));
		// params.addQueryStringParameter("currentPage",
		// String.valueOf(currentPage++));
		params.addHeader(MyFormat.HEADER_KEY, MyFormat.HEADER_VALUE);
		AppContext.getHtmlUitls().xUtilsm(context, HttpMethod.GET, method,
				params, new HttpCallback() {
					@Override
					public void onBack(JSONObject json) {
						/** 获取分类商品数据 */
						// autoLoading.hideAll();
						getdata(method, json);
						if(method.equals("common/getEpShops")&&Integer.parseInt(currentPage)<2){
							fgqqdb.update(json.toString(), "c");
						}
						//
					}

				

					@Override
					public void onError(String msg) {

						if (method.equals("m/epShop/getShopsss")) {
							Message _Msg = mHandler.obtainMessage(
									Constants.FAILED,
									getResources().getString(
											R.string.connection_Exception));
							mHandler.sendMessage(_Msg);

						} else {
							// autoLoading.showExceptionLayout();
							pulltorefreshscrollview.onRefreshComplete();
						}
					}
				});

	}
	private void getdata(final String method, JSONObject json) {
		try {
			if (json.getBoolean("success")) {
				
				
				

				if (method.equals("m/epShop/getShopsss")
						&& json.get("obj").equals(null)) {

					Message _Msgs = mHandler.obtainMessage(
							Constants.SUCCESS, listData);
					mHandler.sendMessage(_Msgs);
					return;
				}
				if(json.get("obj")!=null&&json.getJSONObject("obj").has("totalPages")){
					totalPages = json.getJSONObject("obj").getInt(
							"totalPages");	
					Log.d("my", "json.getJSONObject"+totalPages);
				}
				JSONObject obj = json.getJSONObject("obj");
				if (!obj.equals(null)
						&& !obj.getJSONArray("result").equals(
								null)) {
					JSONArray result = obj
							.getJSONArray("result");
					if (result.length() > 0) {
						Map map;
						for (int i = 0; i < result.length(); i++) {
							map = new HashMap<String, Object>();
							JSONObject obj1 = result
									.getJSONObject(i);
							
							set.add(obj1.toString());
							// map.put("price",
							// obj1.getString("price"));
//							addData(map, obj1);
						}
					}
				}
				Log.d("my", "method:" + method);
				if (method.equals("m/epShop/getShopsss")) {
					Message _Msgs = mHandler.obtainMessage(
							Constants.SUCCESS, listData);
					mHandler.sendMessage(_Msgs);

				} else if (method.equals("common/getEpShops")) {
					Message _Msgs = mHandler.obtainMessage(
							Constants.REFRESH, listData);
					mHandler.sendMessage(_Msgs);

				}
				pulltorefreshscrollview.onRefreshComplete();
				
			} else {
				Log.d("my", method);
				if (method.equals("m/epShop/getShopsss")) {
					Message _Msgs = mHandler.obtainMessage(
							Constants.SUCCESS, listData);
					Log.d("my", "sendmessage");
					mHandler.sendMessage(_Msgs);

				}
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void addData(Map map, JSONObject obj1) throws JSONException {
		map.put("name",
				obj1.getString("NAME"));
		if (obj1.get("IMG") != null) {

			map.put("img",
					obj1.getString("IMG"));
		} else {
			map.put("img", "");

		}
		map.put("prodSpecId",
				obj1.getString("ID"));
		listData.add(map);
	}
	@OnItemClick(R.id.commodity_id)
	public void onItemClick(AdapterView<?> parent, View arg1, int position,
			long arg3) {
		if (appContext.isNetworkConnected()) {
			String prodspecId = listData.get(position).get("prodSpecId")
					.toString();

			String img = listData.get(position).get("img").toString();

			Intent intent = new Intent(context, ShopActivity.class);
			intent.putExtra("shopId", prodspecId);
			intent.putExtra("img", img);
			startActivity(intent);
		} else {
			Toast.makeText(context, "网络不可用", Toast.LENGTH_SHORT).show();
		}

	}

	@OnClick({ R.id.erji_fanhui, R.id.home_search_id, R.id.home_sweep_id,
			R.id.tv_xiaoliang, R.id.ll_jiage, R.id.ll_shaixuan, R.id.ll_zonghe })
	public void myClick(View v) {
		setColors();
		switch (v.getId()) {
		case R.id.erji_fanhui:// 返回
			// finish();
			break;
		case R.id.home_search_id:// 搜索框
			startActivity(new Intent(context, SearchActivity.class));
			Toast.makeText(context, "搜索框", Toast.LENGTH_SHORT).show();
			break;
		case R.id.home_sweep_id:// 切换单双列显示
			// if (isSingle) {
			// isSingle = false;
			// layoutListView.setNumColumns(2);
			// home_sweep_id.setImageResource(R.drawable.erji_liebiao_fenlei);
			// } else {
			// isSingle = true;
			// layoutListView.setNumColumns(1);
			// home_sweep_id.setImageResource(R.drawable.erji_pinpu_fenlei);
			// }
			// adapter = new ShoplistAdapter(context, listData, isSingle);
			// layoutListView.setAdapter(adapter);
			break;
		// case R.id.iv_zonghe:
		// Toast.makeText(this, "综合", Toast.LENGTH_SHORT).show();
		// break;
		case R.id.tv_xiaoliang:// 销量
			tv_xiaoliang.setTextColor(getResources().getColor(R.color.orange1));
			Toast.makeText(context, "销量", Toast.LENGTH_SHORT).show();
			break;
		case R.id.ll_jiage:// 价格
			tv_jiage.setTextColor(getResources().getColor(R.color.orange1));
			price_up.setBackgroundResource(R.drawable.erji_shangfan_fenlei_selected);
			price_down.setBackgroundResource(R.color.white);
			Toast.makeText(context, "价格升", Toast.LENGTH_SHORT).show();
			break;
		case R.id.price_up:// 价格升
			break;
		case R.id.price_down:// 价格降
			Toast.makeText(context, "价格降", Toast.LENGTH_SHORT).show();
			break;
		case R.id.iv_shaixuan:// 筛选
			// Toast.makeText(this, "筛选", Toast.LENGTH_SHORT).show();
			break;
		case R.id.ll_zonghe:// 综合
			tv_zonghe.setTextColor(getResources().getColor(R.color.orange1));
			iv_zonghe.setBackgroundResource(R.drawable.erji_xiala);
			Toast.makeText(context, "综合", Toast.LENGTH_SHORT).show();
			break;
		case R.id.ll_shaixuan:// 筛选
			tv_shaixuan.setTextColor(getResources().getColor(R.color.orange1));
			iv_shaixuan
					.setBackgroundResource(R.drawable.erji_shaixuan_fenlei_selected);
			Toast.makeText(context, "筛选", Toast.LENGTH_SHORT).show();
			break;

		}
	}

	/** 设置标签为灰色 */
	void setColors() {

		tv_zonghe.setTextColor(getResources().getColor(R.color.black1));
		tv_xiaoliang.setTextColor(getResources().getColor(R.color.black1));
		tv_jiage.setTextColor(getResources().getColor(R.color.black1));
		tv_shaixuan.setTextColor(getResources().getColor(R.color.black1));

		iv_zonghe.setBackgroundResource(R.drawable.erji_xiala_normal);
		price_up.setBackgroundResource(R.drawable.erji_shangfan_fenlei);
		price_down.setBackgroundResource(R.color.white);
		iv_shaixuan.setBackgroundResource(R.drawable.erji_shaixuan_fenlei);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		GetProgressBar.dismissMyProgressBar();
	}

}
