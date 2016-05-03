package com.bjg.lcc.alipay.pay;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.utils.ValidateUtils;

public class payfragment extends Fragment implements OnCheckedChangeListener {
	public interface Mydismiss {
		boolean mDismiss(boolean isDismiss);
		/** 我的余额,是否选中余额支付,是否选中支付宝支付 */
		void setPrice(double totalBalances, boolean cbisChecked,
					  boolean iszhi);
	}

	private Mydismiss mydismiss;
	/** 我的余额 */
	private double totalBalances;
	/** 商品支付价 */
	private String payPrice;
	/** 显示我的余额*/
	private TextView sname;
	TextView product_allprice;
	/** 是否选择余额支付 */
	private boolean iscb = false;
	/** 是否选择支付宝支付 */
	private boolean iszhi = true;
	/** 是否选择微信支付 */
	private boolean iswei = false;
	private BigDecimal TotalBalances = BigDecimal.ZERO;
	
	
	
	private PayActivity context;
	private LinearLayout ll_cb;
	private TextView sprice;
	private CheckBox cb;
	private CheckBox wei_cb;
	private CheckBox zhi_cb;
	private LinearLayout ll_zhi;
	private LinearLayout ll_wei;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 2:
				//				mydismiss.setPrice(totalBalances, cb.isChecked(),
				//						zhi_cb.isChecked());
				if (totalBalances > 0) {
					//					iscb = true;
					ll_cb.setVisibility(View.VISIBLE);
					//					cb.setChecked(iscb);
					showPayHowMuch();
				} else {
					//					iscb = false;
					//					cb.setChecked(iscb);
					ll_cb.setVisibility(View.GONE);
				}
				break;

			default:
				break;
			}
		}

	};
	
	
	/** 显示需支付的现金 */
	private void showPayHowMuch() {
		sname.setText("" + totalBalances);
		if (totalBalances >= Double.parseDouble(payPrice)) {
			sprice.setText("" + 0.00);
			TotalBalances = BigDecimal.ZERO;
		} else {
			TotalBalances = new BigDecimal(totalBalances).setScale(2, RoundingMode.DOWN);
			payPrice = new BigDecimal(payPrice).setScale(2, RoundingMode.DOWN)+"";
			BigDecimal pay2 = new BigDecimal(payPrice).subtract(TotalBalances);
//			sprice.setText(MyFormat.getPriceFormat(Double.parseDouble(payPrice) - Double.parseDouble(df.format(totalBalances)) + ""));
			if(pay2.compareTo(BigDecimal.ZERO)>=0){
				
			}else{
				pay2=BigDecimal.ZERO;
			}
			sprice.setText(pay2+"");
			mydismiss.setPrice(Double.parseDouble(TotalBalances+""), iscb, iszhi);
		}

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		context = (PayActivity) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		assets();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.pay_external, container, false);
		sname = (TextView) v.findViewById(R.id.product_subject);
		product_allprice = (TextView) v.findViewById(R.id.product_allprice);
		sprice = (TextView) v.findViewById(R.id.product_price);
		ll_cb = (LinearLayout) v.findViewById(R.id.ll_cb);
		wei_cb = (CheckBox) v.findViewById(R.id.wei_cb);
		zhi_cb = (CheckBox) v.findViewById(R.id.zhi_cb);
		ll_zhi = (LinearLayout) v.findViewById(R.id.ll_zhi);
		ll_wei = (LinearLayout) v.findViewById(R.id.ll_wei);
		ll_cb.setVisibility(View.GONE);

		ImageView back = (ImageView) v.findViewById(R.id.iv_pay_back);
		cb = (CheckBox) v.findViewById(R.id.cb);

		Intent intent = getActivity().getIntent();
		sname.setText("");
		// sname.setText(intent.getStringExtra("name"));
		// sdesc.setText(intent.getStringExtra("singlePrice"));
		mydismiss = (Mydismiss) getActivity();
		payPrice = intent.getStringExtra("price");
		sprice.setText(payPrice);
		product_allprice.setText(payPrice);
		back.setOnClickListener(l);
		zhi_cb.setOnCheckedChangeListener(this);
		wei_cb.setOnCheckedChangeListener(this);
		cb.setOnCheckedChangeListener(this);
		
		zhi_cb.performClick();
//		cb.performClick();
		// cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		//
		// @Override
		// public void onCheckedChanged(CompoundButton buttonView, boolean
		// isChecked) {
		// // TODO Auto-generated method stub
		// if(isChecked){
		// showPayHowMuch();
		// }else{
		// sprice.setText(payPrice);
		// }
		// mydismiss.setPrice(totalBalances, isChecked);
		// }
		// });

		//我的余额一行
		//		ll_cb.setOnClickListener(new OnClickListener() {
		//			
		//			@Override
		//			public void onClick(View arg0) {
		//				// TODO Auto-generated method stub
		//				if (iscb) {
		//					iscb = false;
		//					cb.setChecked(false);
		//				}else{
		//					iscb = true;
		//					cb.setChecked(true);
		//				}
		//			}
		//		});
		return v;
	}

	/** 获取我的资产 */
	private void assets() {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token",
				UserInformation.getAccess_token());
		params.addHeader(MyFormat.HEADER_KEY, MyFormat.HEADER_VALUE);
		AppContext.getHtmlUitls().xUtilsm(context, HttpMethod.POST,
				"m/assets/assets", params, new HttpCallback() {

			@Override
			public void onBack(JSONObject json) {
				// TODO Auto-generated method stub
				try {
					if (json.getBoolean("success")) {
						// myco = json.getJSONObject("attributes")
						// .getJSONObject("attentionList")
						// .getInt("coAmount");
						totalBalances = json
								.getJSONObject("attributes")
								.getJSONObject("attentionList")
								.getDouble("totalBalances");
						if (totalBalances>0) {
							iscb = true;
							cb.performClick();
						}
						handler.sendEmptyMessage(2);
						

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onError(String msg) {
				// TODO Auto-generated method stub

			}
		});
	}

	OnClickListener l = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			mydismiss.mDismiss(true);
		}
	};

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch (buttonView.getId()) {
		case R.id.cb:
			if (isChecked) {// 是否选中余额支付 
				iscb = true;
				showPayHowMuch();
			} else {
				iscb = false;
				sprice.setText(payPrice);// 是否使用余额支付
			}
			break;
		case R.id.zhi_cb:// 选择支付宝支付
			// zhi_cb.setChecked(true);
			if (isChecked) {// 选支付宝就不能选微信
				iszhi = true;
				iswei = false;
			} else {// 选微信就不能选支付宝
				iszhi = false;
				iswei = true;
			}
			wei_cb.setChecked(!iszhi);
			break;
		case R.id.wei_cb:// 选择微信支付
			// wei_cb.setChecked(false);
			if (isChecked) {// 选微信就不能选支付宝
				iswei = true;
				iszhi = false;
			} else {// 选支付宝就不能选微信
				iswei = false;
				iszhi = true;
			}
			zhi_cb.setChecked(!iswei);
			break;
		}
		mydismiss.setPrice(Double.parseDouble(TotalBalances+""), iscb, iszhi);

	}
}
