package com.llg.privateproject.actvity;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.utils.CommonUtils;

/**
 * 意见反馈
 * 
 * @author yh 2015.6.7
 */
public class FeedBackActivity extends BaseActivity implements TextWatcher {
	/** 可输入剩余文字 */
	@ViewInject(R.id.tv_restcount)
	private TextView tv_restcount;
	/** 意见输入框 */
	@ViewInject(R.id.et_feedback)
	private EditText et_feedback;
	/** 显示剩余输入数 */
	private int restCount = 300;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		ViewUtils.inject(this);
		et_feedback.addTextChangedListener(this);
		int color = getResources().getColor(R.color.black3);
		et_feedback.setHintTextColor(color);
	}

	@OnClick({ R.id.turn,// 返回

			R.id.tv_commit // 提交意见
	})
	public void myClick(View v) {
		
		switch (v.getId()) {
		
		case R.id.turn:
			finish();

			break;
		case R.id.tv_commit:
			if (et_feedback.getText().toString().length() < 10) {
				Toast.makeText(this, "请填写不少于10个字的意见", Toast.LENGTH_SHORT)
						.show();
			} else {
				if(!appContext.isNetworkConnected()){
					toast(R.string.connection_Exception);
					return;
				}
				save(et_feedback.getText().toString());
				et_feedback.setText("");
				
				Toast.makeText(this, "您的意见已提交", Toast.LENGTH_SHORT).show();

			}
			break;

		default:
			break;
		}
	}
private void save(String feedback){
	RequestParams params=new RequestParams();
	params.addQueryStringParameter("access_token", UserInformation
			.getAccess_token());
	params.addQueryStringParameter("content", feedback);
	AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST, "m/feedback/save", params, new HttpCallback() {
		
		@Override
		public void onError(String msg) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onBack(JSONObject json) {
			// TODO Auto-generated method stub
			
		}
	});
	
}
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

		if (et_feedback.getText() != null) {
			restCount = 300 - et_feedback.getText().toString().length();
			tv_restcount.setText("" + restCount);
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

}
