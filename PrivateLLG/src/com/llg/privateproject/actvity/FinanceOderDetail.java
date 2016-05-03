package com.llg.privateproject.actvity;

import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.llg.help.MyFormat;
import com.llg.privateproject.entities.SMap;
import com.llg.privateproject.fragment.BaseActivity;
import com.smartandroid.sa.aysnc.Log;

public class FinanceOderDetail extends BaseActivity{
	@ViewInject(R.id.turn)
	private ImageView turn;

	@ViewInject(R.id.title)
	private TextView title;
	@ViewInject(R.id.orderstatus)
	private TextView orderstatus;
	@ViewInject(R.id.name)
	private TextView name;
	@ViewInject(R.id.user)
	private TextView user;
	@ViewInject(R.id.order_no)
	private TextView order_no;
	@ViewInject(R.id.shopname)
	private TextView shopname;
	@ViewInject(R.id.other_website_price)
	private TextView other_website_price;
	@ViewInject(R.id.prod_all_price)
	private TextView prod_all_price;
	@ViewInject(R.id.pd_all_price)
	private TextView pd_all_price;
	@ViewInject(R.id.all_price)
	private TextView all_price;
	@ViewInject(R.id.creat_time)
	private TextView creat_time;
	@ViewInject(R.id.pay_date)
	private TextView pay_date;
	@ViewInject(R.id.other_websie_orderno)
	private TextView other_websie_orderno;
	@ViewInject(R.id.other_website_cus_name)
	private TextView other_website_cus_name;
	private SMap sMap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.financeoderdetail);
		ViewUtils.inject(this);
		sMap=(SMap) getIntent().getBundleExtra("bundle").getSerializable("map");
		Map<String, Object>map=sMap.getMap();
		Log.d("my", "map.tostring"+map.toString());
		setData(map);
		back();
	}
	private void setData(Map<String, Object>map){
		orderstatus.setText(map.get("ORDER_STATE_STR").toString());//订单状态
		name.setText(map.get("CONSIGNEE").toString());//收货人
		user.setText(map.get("CUSTOMER_NAME").toString());//用户名
		user.setText(map.get("ORDER_NO").toString());//订单编号
		shopname.setText(map.get("BUSINESS_NAME").toString());//商家
		other_website_price.setText(map.get("OTHER_WEBSITE_PRICE").toString());//外网连接价
		prod_all_price.setText(map.get("PROD_ALL_PRICE").toString());//商品价
		pd_all_price.setText(map.get("PD_ALL_PRICE").toString());//运费
		all_price.setText(map.get("ALL_PRICE").toString());//应付总价
		Log.d("my", "creattime"+map.get("CREATE_DATE").toString());
//		if(map.get("CREATE_DATE")!=null&&map.get("CREATE_DATE").toString().length()>7){
//			
//			creat_time.setText(MyFormat.getTimeFormat3(map.get("CREATE_DATE").toString()));//下单时间
//		}else{
//			creat_time.setText("");
//		}
//		if(map.get("PAY_DATE")!=null&&map.get("PAY_DATE").toString().length()>7){
//			
//			pay_date.setText(MyFormat.getTimeFormat3(map.get("PAY_DATE")));//支付时间
//		}else{
//			pay_date.setText("");//支付时间
//			
//		}
		if(map.get("OTHER_WEBSITE_ORDERNO")!=null){
			
			pay_date.setText(map.get("OTHER_WEBSITE_ORDERNO").toString());//回填订单号
		}else{
			pay_date.setText("");//回填订单号
			
		}
		if(map.get("OTHER_WEBSITE_CUS_NAME")!=null){
			
			pay_date.setText(map.get("OTHER_WEBSITE_CUS_NAME").toString());//回填人姓名
		}else{
			pay_date.setText("");//回填人姓名
			
		}
		
	}
	private void back() {
		turn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

}
