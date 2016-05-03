package com.llg.privateproject.actvity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.help.ScreenManager;
import com.llg.privateproject.fragment.BaseActivity;

public class MoreFunction extends BaseActivity {
	@ViewInject(R.id.turn)private ImageView turn;
	@ViewInject(R.id.title)private TextView title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_more_function);
		ViewUtils.inject(this);
		title.setText("更多广告投放请上电脑端");
		ScreenManager.getScreenManager().pushActivity(this);
		
	}
	@OnClick({R.id.turn,R.id.title})
	public void myclick(View v){
		switch (v.getId()) {
		case R.id.turn:
			finish();
			break;

		case R.id.title:
			break;
		}
		
	}
}
