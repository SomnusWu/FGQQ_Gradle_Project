package com.llg.privateproject.actvity;

import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.fragment.BaseActivity;
/**yh
 * 
 * 2016.1.14
 * 显示店铺地图
 * */
public class ShopMap extends BaseActivity{

	@ViewInject(R.id.bmapView)private MapView mMapView;
	private  BaiduMap mBaiduMap;
	LatLng pt1 ;
	@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.shop_map);
	ViewUtils.inject(this);
	mBaiduMap=mMapView.getMap();
	
	//普通地图  
	mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
	pt1 = new LatLng(getIntent().getDoubleExtra("LAT", 29.519201), getIntent().getDoubleExtra("LNG", 106.553749));
	//移动到店铺位置
	mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLngZoom(pt1,16));
	//定义Maker坐标点  

//	addshopIcon(R.drawable.wyjz_dingwei2,new LatLng(AppContext.myLatitude, AppContext.myLongitude),0);
	addshopIcon(R.drawable.wyjz_dingwei1,pt1,1);
	
	}
	/**添加店铺名*/
	private void addshopName() {
		//构建文字Option对象，用于在地图上添加文字  
		OverlayOptions textOption = new TextOptions()  
		    .bgColor(getResources().getColor(R.color.transparent8))  
		    .fontSize(24)  
		    .fontColor(getResources().getColor(R.color.black2))  
		    .text(getIntent().getStringExtra("name"))  
		    .rotate(0)  
		    .position(pt1);  
		//在地图上添加该文字对象并显示  
		mBaiduMap.addOverlay(textOption);
	}
	/**添加位置图标*/
	private void addshopIcon(int drawid,LatLng pt,int type) {
		if(type==1){
			addshopName() ;
		}
		//构建Marker图标  
		BitmapDescriptor bitmap = BitmapDescriptorFactory  
		    .fromResource(drawid);  
		//构建MarkerOption，用于在地图上添加Marker  
		OverlayOptions option = new MarkerOptions()  
		    .position(pt)  
		    .icon(bitmap);  
		//在地图上添加Marker，并显示  
		mBaiduMap.addOverlay(option);
	}
	
    @Override  
    protected void onDestroy() {  
        super.onDestroy();  
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
        mMapView.onDestroy();  
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
        mMapView.onResume();  
        }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
        mMapView.onPause();  
        }  
    
}
