package com.llg.privateproject.adapters;

import java.util.List;
import java.util.Map;

import com.bjg.lcc.privateproject.R;
import com.llg.help.MyFormat;
import com.llg.privateproject.entities.SMap;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/**财务待处理订单
 * @author yh
 * 2016.1.29
 * */
public class FinancialOderAdapter extends MyBaseAdapter{
private 	Context context;
private  List<SMap> list;
	
	/**财务待处理订单
	 * @author yh
	 * 2016.1.29
	 * */
	public FinancialOderAdapter(Context context, List<SMap> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.list=list;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		 super.getView(position, v, parent);
		 Holder h=null;
		 Map<String, Object> map=list.get(position).getMap();
		 if(v==null){
			 h=new Holder();
			 v=View.inflate(context, R.layout.financialoder_item, null);
			 h.order_id=(TextView) v.findViewById(R.id.order_id);
			 h.creat_time=(TextView) v.findViewById(R.id.creat_time);
			 h.order_status=(TextView) v.findViewById(R.id.order_status);
			 v.setTag(h);
		 }else{
			h= (Holder)v.getTag();
		 }
		 h.order_status.setText(map.get("ORDER_STATE_STR").toString());
		 h.order_id.setText(map.get("ORDER_NO").toString());
		 h.creat_time.setText(MyFormat.getTimeFormat3(map.get("CREATE_DATE").toString()));
		 
		 return v;
		
	}
private class Holder{
	 TextView order_id;
	 TextView creat_time;
	 TextView order_status;
}
}
