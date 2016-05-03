package com.llg.privateproject.adapters;

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
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.actvity.ShopActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.utils.CommonUtils;
import com.llg.privateproject.utils.StringUtils;

/**
 * 产品适配器
 * 
 * @author gongyibing
 */
public class ShoplistAdapter extends BaseAdapter {
	private Context context;
	/** 分类数据 */
	private List<Map<String, Object>> list;
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
	public ShoplistAdapter(Context context, List<Map<String, Object>> list,
			boolean isSingle) {
		super();
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
	public ShoplistAdapter(Context context, List<Map<String, Object>> list,
			boolean isSingle, boolean isShowCb) {
		super();
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

	@Override
	public int getCount() {
		return (null == list) ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		return (null == list) ? null : list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (null == context) {
			return null;
		}
		if (null == list || list.size() == 0 || list.size() <= position) {
			return null;
		}
		final ViewHolder viewHolder;
	
		final Map<String, Object> productMap = list.get(position);
		// Log.e("my", "productMap:"+productMap);
		if (null == convertView) {
			viewHolder = new ViewHolder();
			if (isSingle) {// 单列显示
				convertView = mInflater.inflate(R.layout.home_commodity_itme,
						parent, false);
				viewHolder.commodity_img = (ImageView) convertView
						.findViewById(R.id.commodity_logo_iv);
				

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
				 Log.e("my", "wid*26/100"+wid*26/100);
			} else {

				lp.width =( wid-MyFormat.dip2px(context, 30)) / 2;
				lp.height = ( wid-MyFormat.dip2px(context, 30)) / 2;
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
	
		viewHolder.commodity_gouwuche.setVisibility(View.GONE);
		viewHolder.commodity_price.setVisibility(View.GONE);
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

			lp.width = (wid-MyFormat.dip2px(context, 30)) / 2;
			lp.height =(wid-MyFormat.px2dip(context, 30)) / 2;
			 Log.e("my", "lp.width"+lp.width);
			 Log.e("my", "MyFormat.dip2px(context, 30)"+MyFormat.dip2px(context, 30));
			 Log.e("my", "width"+wid);
			viewHolder.commodity_img.setLayoutParams(lp);
		}
		MyFormat.setBitmap(context, viewHolder.commodity_img,
				productMap.get("img").toString().trim(), lp.width, lp.height);
		viewHolder.commodity_dsc.setText(productMap.get("name").toString()
				.trim());

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


	}

	

}
