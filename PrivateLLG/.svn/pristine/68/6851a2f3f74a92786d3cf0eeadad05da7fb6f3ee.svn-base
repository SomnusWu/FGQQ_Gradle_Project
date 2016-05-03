package com.llg.privateproject.actvity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.privateproject.fragment.BaseActivity;

public class ExamResultAty extends BaseActivity {
	@ViewInject(R.id.tv_grade)
	private TextView tvGrade;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_exam_result);
		ViewUtils.inject(this);
		initUI();
		tvGrade.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); // 下划线
		
		 
	}

	private void initUI() {
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
