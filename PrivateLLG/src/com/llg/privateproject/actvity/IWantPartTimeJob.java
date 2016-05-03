package com.llg.privateproject.actvity;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.privateproject.view.CircleMenuLayout;
import com.llg.privateproject.view.PartTimePopuwindow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class IWantPartTimeJob extends Activity {
	private CircleMenuLayout mCircleMenuLayout;

	private String[] mItemTexts = new String[] { "搬家 ", "开锁", "保洁", "维修", "代驾",
			"更多" };
	private int[] mItemImgs = new int[] { R.drawable.daijia, R.drawable.kaisuo,
			R.drawable.qingjie, R.drawable.weixiu, R.drawable.daijia,
			R.drawable.gengduo };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_parttime_job);
		ViewUtils.inject(this);
		mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
		mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
		mCircleMenuLayout
				.setOnMenuItemClickListener(new com.llg.privateproject.view.CircleMenuLayout.OnMenuItemClickListener() {

					@Override
					public void itemClick(View view, int pos) {
						Toast.makeText(IWantPartTimeJob.this, mItemTexts[pos],
								Toast.LENGTH_SHORT).show();

					}

					@Override
					public void itemCenterClick(View view) {
						Intent intent = new Intent(IWantPartTimeJob.this,
								HomeDelivery.class);
						startActivity(intent);
					}
				});
	}

	@OnClick({ R.id.iv_back })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		default:
			break;
		}
	}

}
