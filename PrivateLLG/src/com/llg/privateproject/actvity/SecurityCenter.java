package com.llg.privateproject.actvity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

/** 安全中心 */
public class SecurityCenter extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.securitycenter);
		ViewUtils.inject(this);
	}

	@OnClick({ R.id.turn, R.id.shimingrensheng, R.id.xiugaimima,
			R.id.youxiangbangding, R.id.tv_binding_alipay })
	public void myClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.turn:
			finish();
			break;
		case R.id.shimingrensheng:// 实名认证
			intent.setClass(this, Shimingrenzheng.class);
			startActivity(intent);

			break;
		case R.id.xiugaimima:// 修改密码

			intent.setClass(this, BangDing.class);
			// intent.putExtra("title", "修改密码");
			// intent.putExtra("name", "原密码:");
			// intent.putExtra("name1", "新密码:");
			intent.putExtra("title", "修改密码");
			intent.putExtra("name", "原    密   码 :");
			intent.putExtra("name1", "新    密   码 :");
			startActivity(intent);

			break;
		case R.id.youxiangbangding:// 邮箱绑定
			intent.setClass(this, BangDing.class);
			intent.putExtra("title", "邮箱绑定");
			intent.putExtra("name", "邮箱账号:");
			intent.putExtra("name1", "验证码:");

			startActivity(intent);

			break;
//		case R.id.shoujibangding:// 手机绑定
//
//			intent.setClass(this, BangDing.class);
//			intent.putExtra("title", "手机绑定");
//			intent.putExtra("name", "手机账号：");
//			intent.putExtra("name1", "验证码:");
//			startActivity(intent);
//
//			break;
			//绑定支付宝
		case R.id.tv_binding_alipay:
			startActivity(new Intent(this, BinDingAliPayActivity.class));
			break;
			
			

		default:
			break;
		}
	}
}
