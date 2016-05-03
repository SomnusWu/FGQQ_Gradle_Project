package com.llg.privateproject.actvity;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.view.ProductDetailEllipsisPoPuWindow;
import com.llg.privateproject.view.ProductDetailEllipsisPoPuWindow.SelecetListener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 退货退款窗口 yh 2015.8.10
 * */
public class OrderstatusTuikuan extends Activity implements SelecetListener {
	/** 返回 */
	@ViewInject(R.id.head_tilte)
	private TextView head_tilte;
	/** 退货 */
	@ViewInject(R.id.tuihuo)
	private TextView tuihuo;
	/** 原因 */
	@ViewInject(R.id.yuanyin)
	private TextView yuanyin;
	/** 上传凭证图片 */
	@ViewInject(R.id.pingzheng)
	private TextView pingzheng;

	ProductDetailEllipsisPoPuWindow window;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderstatus_tuihuo);
		ViewUtils.inject(this);

		init();
	}

	private void init() {
		head_tilte.setText("申请退货/退款");
	}

	@OnClick({ R.id.turn, R.id.tuihuo, R.id.yuanyin, R.id.pingzheng,
			R.id.confirm })
	public void myClick(View v) {
		switch (v.getId()) {
		case R.id.turn:// 返回按钮
			finish();
			break;
		case R.id.tuihuo:// 退货
			window = new ProductDetailEllipsisPoPuWindow(this, this, 1, tuihuo);
			window.showAsDropDown(tuihuo);
			Toast.makeText(this, "退货", Toast.LENGTH_SHORT).show();
			break;
		case R.id.yuanyin:// 原因
			window = new ProductDetailEllipsisPoPuWindow(this, this, 2, yuanyin);
			window.update();
			window.showAsDropDown(yuanyin);
			break;
		case R.id.pingzheng:// 上传凭证图片
			window = new ProductDetailEllipsisPoPuWindow(this, this, 3,
					pingzheng);
			// window.showAsDropDown(pingzheng);
			break;
		case R.id.confirm:// 提交申请
			finish();
			Toast.makeText(this, "提交申请", Toast.LENGTH_SHORT).show();

			break;
		default:
			break;
		}
	}

	@Override
	public void setMessage(String message, int type) {
		// TODO Auto-generated method stub
		// Log.d("my", "message"+message);
		switch (type) {
		case 1:// 退货方式
			tuihuo.setText(message);
			break;
		case 2:// 退货原因
			yuanyin.setText(message);
			break;
		case 3:// 拍照、本地上传
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}

	}
}
