package com.llg.privateproject.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.llg.help.GetProgressBar;
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.actvity.WebLoginActivity;
import com.llg.privateproject.entities.FollowModel;
import com.llg.privateproject.entities.MyHistoryModel;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.utils.CommonUtils;
import com.llg.privateproject.utils.StringUtils;

/**
 * 产品适配器
 * 
 * @author gongyibing
 */
public class CommodityAdapter extends BaseAdapter {
	private Context context;
	/** 分类数据 */
	private List<FollowModel> list;
	/**
	 * 浏览记录数据
	 */
	private List<MyHistoryModel> mHistoryList;
	private boolean isHistory = false;
	private LayoutInflater mInflater;
	/** 是否单列显示 */
	private boolean isSingle = true;
	/** 获取配置文件人民币符号 */
	private String priceValue = "";
	private WindowManager windowManager;
	/** 是否显示checkbox */
	private boolean isShowCb = false;
	private AppContext appContext;

	/** 商品列表适配器 */
	public CommodityAdapter(Context context, List<FollowModel> list,
			boolean isSingle) {
		super();
		isHistory = false;
		this.context = context;
		this.list = list;
		appContext = (AppContext) this.context.getApplicationContext();
		this.mInflater = LayoutInflater.from(context);
		this.priceValue = context
				.getString(R.string.h_company_detail_price_value);
		windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		this.isSingle = isSingle;
	}

	/**
	 * 商品列表适配器 isShowCb 是否显示checkbox
	 * */
	public CommodityAdapter(Context context, List<FollowModel> list,
			boolean isSingle, boolean isShowCb) {
		super();
		isHistory = false;
		this.context = context;
		this.list = list;
		this.isShowCb = isShowCb;
		this.mInflater = LayoutInflater.from(context);
		this.priceValue = context
				.getString(R.string.h_company_detail_price_value);
		windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		this.isSingle = isSingle;
	}

	/**
	 * 浏览记录
	 * 
	 * @param context
	 * @param list
	 */
	public CommodityAdapter(Context context, List<MyHistoryModel> list) {
		isHistory = true;
		this.context = context;
		appContext = (AppContext) context.getApplicationContext();
		this.mHistoryList = list;
		this.mInflater = LayoutInflater.from(context);
		windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
	}

	@Override
	public int getCount() {
		if (isHistory) {
			return mHistoryList.size();
		}
		return (null == list) ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		if (isHistory) {
			return mHistoryList.get(position);
		}
		return (null == list) ? null : list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;

		if (null == convertView) {
			viewHolder = new ViewHolder();
			if (isSingle) {// 单列显示
				convertView = mInflater.inflate(R.layout.home_commodity_itme,
						parent, false);
				viewHolder.commodity_img = (ImageView) convertView
						.findViewById(R.id.commodity_logo_iv);
				viewHolder.cb = (CheckBox) convertView.findViewById(R.id.cb);

			} else {// 两列显示
				convertView = mInflater.inflate(R.layout.home_commodity_item2,
						parent, false);
				viewHolder.commodity_img = (ImageView) convertView
						.findViewById(R.id.commodity_logo_iv1);
			}

			LayoutParams lp = (LayoutParams) viewHolder.commodity_img
					.getLayoutParams();
			int wid = AppContext.getScreenWidth();
			if (isSingle) {
				lp.width = wid * 26 / 100;
				lp.height = wid * 26 / 100;
				viewHolder.commodity_img.setLayoutParams(lp);
				// Log.e("my", "wid*26/100"+wid*26/100);
			} else {

				lp.width = wid / 2;
				lp.height = wid / 2;
				// Log.e("my", "wid/2"+wid/2);
				viewHolder.commodity_img.setLayoutParams(lp);
			}
			// viewHolder.commodity_img.setLayoutParams(lp);
			viewHolder.commodity_dsc = (TextView) convertView
					.findViewById(R.id.commodity_name_tv);
			viewHolder.commodity_oldprice = (TextView) convertView
					.findViewById(R.id.commodity_price_tv);
			viewHolder.company_intro = (TextView) convertView
					.findViewById(R.id.company_intro_tv);
			viewHolder.commodity_price = (TextView) convertView
					.findViewById(R.id.company_price);
			viewHolder.commodity_active1 = (TextView) convertView
					.findViewById(R.id.company_zhe1);
			viewHolder.commodity_activity2 = (TextView) convertView
					.findViewById(R.id.company_zhe2);
			viewHolder.commodity_gouwuche = (ImageView) convertView
					.findViewById(R.id.company_gouwuche);
			viewHolder.commodity_oldprice.getPaint().setFlags(
					Paint.STRIKE_THRU_TEXT_FLAG);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (isShowCb) {
			if (viewHolder.cb != null)
				viewHolder.cb.setVisibility(View.VISIBLE);
			 viewHolder.cb.setChecked(false);
		} else {
			if (viewHolder.cb != null)
				viewHolder.cb.setVisibility(View.GONE);
		}
		if (viewHolder.cb != null) {
			viewHolder.cb
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							// TODO Auto-generated method stub
							// -----------------------------
//							 productMap.put("isGuanzhu", isChecked);
							FollowModel model = list.get(position);
							model.setChecked(isChecked);
						}
					});
		}
		viewHolder.commodity_active1.setVisibility(View.INVISIBLE);
		viewHolder.commodity_activity2.setVisibility(View.INVISIBLE);
		LayoutParams lp = (LayoutParams) viewHolder.commodity_img
				.getLayoutParams();
		int wid = AppContext.getScreenWidth();
		if (isSingle) {
			lp.width = wid * 26 / 100;
			lp.height = wid * 26 / 100;
			viewHolder.commodity_img.setLayoutParams(lp);
			// Log.e("my", "wid*26/100"+wid*26/100);
		} else {

			lp.width = wid / 2;
			lp.height = wid / 2;
			// Log.e("my", "wid/2"+wid/2);
			viewHolder.commodity_img.setLayoutParams(lp);
		}

		if (isHistory) {
			MyHistoryModel _historyModel = mHistoryList.get(position);
			MyFormat.setBitmap(context, viewHolder.commodity_img,
					_historyModel.getIconpath(), lp.width, lp.height);
			viewHolder.commodity_dsc.setText(_historyModel.getProdname());
			// viewHolder.commodity_oldprice.setText("￥"
			// + _historyModel.getPrice());

			DecimalFormat dt = (DecimalFormat) DecimalFormat.getInstance(); // 获得格式化类对象
			dt.applyPattern("0.00");// 设置小数点位数(两位) 余下的会四舍五入
			// viewHolder.commodity_price.setText("￥"
			// + dt.format(_historyModel.getPrice()));

			viewHolder.commodity_price.setText("￥"
					+ dt.format(Double.parseDouble(_historyModel.getPrice()
							.toString().trim())));
		} else {
			FollowModel model = list.get(position);
			if (!StringUtils.isEmpty(model.getICON_PATH())) {
				MyFormat.setBitmap(context, viewHolder.commodity_img,
						model.getICON_PATH(), lp.width, lp.height);
			}
			viewHolder.commodity_dsc.setText(model.getNAME().toString().trim());
			// if (!StringUtils.isEmpty(model.getPRICE())) {
			// viewHolder.commodity_oldprice.setText("￥"
			// + model.getPRICE().toString().trim());
			// }
			if (!StringUtils.isEmpty(model.getATTENTION_COUNT())) {
				viewHolder.company_intro.setText(model.getATTENTION_COUNT()
						+ "关注");
			}
			DecimalFormat dt = (DecimalFormat) DecimalFormat.getInstance(); // 获得格式化类对象
			dt.applyPattern("0.00");// 设置小数点位数(两位) 余下的会四舍五入
			viewHolder.commodity_price.setText("￥"
					+ dt.format(Double.parseDouble(model.getPRICE().toString()
							.trim())));
		}
		viewHolder.commodity_gouwuche.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (appContext == null) {
					appContext = (AppContext) context.getApplicationContext();
				}
				if (appContext.isNetworkConnected()) {
					if (isHistory) {
						MyHistoryModel _historyModel = mHistoryList
								.get(position);
						addCart(_historyModel.getProdspecid());
					} else {
						FollowModel model = list.get(position);
						addCart(model.getPRODSPECID());
					}
				} else {
					Toast.makeText(context, R.string.t_Connection,
							Toast.LENGTH_SHORT).show();
				}

			}

			private void addCart(final String id) {
				GetProgressBar.getProgressBar(context);
				RequestParams params = new RequestParams();
				params.addQueryStringParameter("access_token",
						UserInformation.getAccess_token());
				params.addQueryStringParameter("specId", id);
				params.addQueryStringParameter("count", String.valueOf(1));
				AppContext.getHtmlUitls().xUtilsm(context, HttpMethod.POST,
						"m/Cart/addCart", params, new HttpCallback() {

							@Override
							public void onError(String msg) {
								// TODO Auto-generated method stub

								if (msg.equals("401")) {
									GetProgressBar.dismissMyProgressBar();
									context.startActivity(new Intent(context,
											WebLoginActivity.class));
									Toast.makeText(context, "未登录",
											Toast.LENGTH_SHORT).show();
								}
							}

							@Override
							public void onBack(JSONObject json) {
								// TODO Auto-generated method stub
								GetProgressBar.dismissMyProgressBar();
								try {
									if (!json.getBoolean("success")) {
										if (json.get(MyFormat.errorCode) != null
												&& json.getString(
														MyFormat.errorCode)
														.equals(MyFormat.NOT_LOGIN)) {
											context.startActivity(new Intent(
													context,
													WebLoginActivity.class));
											Toast.makeText(context, "未登录",
													Toast.LENGTH_SHORT).show();

										}
										if (json.getString("msg") != null) {

											Toast.makeText(context,
													json.getString("msg"),
													Toast.LENGTH_SHORT).show();
										}
									} else {

										Toast.makeText(context, "已加入购物车",
												Toast.LENGTH_SHORT).show();
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
			}
		});

		return convertView;
	}

	class ViewHolder {
		/** 产品图片 */
		ImageView commodity_img;
		/** 产品名称描述 */
		TextView commodity_dsc;
		/** 原价 */
		TextView commodity_oldprice;
		/** 产品好评 人数等 */
		TextView company_intro;
		/** 产品价格 */
		TextView commodity_price;
		/** 活动1 */
		TextView commodity_active1;
		/** 活动2 */
		TextView commodity_activity2;
		/** 购物车 */
		ImageView commodity_gouwuche;
		CheckBox cb;

	}

	/** 设置被关注商品列表项复选框是否可见 */
	public void setIsguanzhu(boolean isShowcb) {
		isShowCb = isShowcb;
	}

}
