package com.llg.privateproject.actvity;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.activity.BaseActivity;
import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;

/**
 * 显示通知消息 yh 2015.12.30
 * */
public class Notify extends BaseActivity {
	@ViewInject(R.id.notify_content)
	private TextView notify_content;
	@ViewInject(R.id.tv_edit)
	private TextView tv_edit;
	@ViewInject(R.id.title_tv)
	private TextView title_tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notify);
		ViewUtils.inject(this);
		tv_edit.setVisibility(View.GONE);
		comeHere();

	}

	@OnClick(R.id.back)
	public void myclick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		default:
			break;
		}
	}

	private void comeHere() {
		XGPushClickedResult click = XGPushManager.onActivityStarted(this);
		if (click != null) {
			Log.d("my", "title" + click.getTitle());
			Log.d("my", "click.getContent()" + click.getContent());
			Log.d("my", "click.getCustomContent()" + click.getCustomContent());
			Log.d("my", "click.getActivityName()" + click.getActivityName());
			title_tv.setText("" + click.getTitle());
			notify_content.setText("" + click.getContent());
			JSONObject obj;
			try {
				obj = new JSONObject(click.getCustomContent());
				if (obj.has("objectId") && obj.get("objectId") != null) {

				} else {
					finish();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
