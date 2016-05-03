/**
 * 
 */
package com.llg.privateproject.actvity;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.util.StringUtils;
import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.help.GetProgressBar;
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.entities.Consumeentity;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;

/**
 * @author cc
 * @time 2016年4月8日 上午9:43:16
 * @description 商户评论界面
 */
public class FGQQShopEvaluateActivity extends BaseActivity {
	@ViewInject(R.id.product_img)
	private ImageView product_img;
	@ViewInject(R.id.tv_shop_title)
	private TextView tv_shop_title;
	@ViewInject(R.id.tv_original_price)
	private TextView tv_original_price;
	@ViewInject(R.id.tv_actual_price)
	private TextView tv_actual_price;
	@ViewInject(R.id.tv_co_count)
	private TextView tv_co_count;
	@ViewInject(R.id.rb_product)
	private TextView rb_product;
	@ViewInject(R.id.et_pinglun)
	private EditText et_pinglun;
	@ViewInject(R.id.head_tilte)
	private TextView head_tilte;

	private Consumeentity mConsumeentity;

	@ViewInject(R.id.rating_bar)
	RatingBar rating_bar;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.llg.privateproject.fragment.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_evaluate);
		ViewUtils.inject(this);

		init();
	}

	private void init() {
		head_tilte.setText("评价");
		if (getIntent() != null) {
			mConsumeentity = (Consumeentity) getIntent().getSerializableExtra(
					"Consumeentity");
			tv_shop_title.setText(mConsumeentity.SHOP_NAME);
			tv_original_price.setText("消费原价:￥"
					+ MyFormat.getPriceFormat(mConsumeentity.ORIG_PRICE));
			tv_actual_price.setText("实际支付:￥"
					+ MyFormat.getPriceFormat(mConsumeentity.PRICE));
			tv_co_count.setText("使用酷币：" + mConsumeentity.CO + "个,系统返还酷币个数:"
					+ mConsumeentity.BACK_CO + "个");
			MyFormat.setBitmap(this, product_img, mConsumeentity.CUS_IMG, 100,
					100);// 消费者图片
		}
	}

	@OnClick({ R.id.turn, R.id.tv_confirm })
	void eventClick(View v) {
		switch (v.getId()) {
		case R.id.turn:
			finish();
			break;
		case R.id.tv_confirm:
			if (rating_bar.getRating() == 0) {
				toast("请对商家进行评分！");
				return;
			}
//			if (StringUtils.isEmpty(et_pinglun.getText().toString().trim())) {
//				toast("请填写您的意见！");
//				return;
//			}
			requestEvaluate();
			break;

		default:
			break;
		}
	}

	private void requestEvaluate() {
		GetProgressBar.getProgressBar(this);
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token",
				UserInformation.getAccess_token());
		if (mConsumeentity.isPayID) {
			params.addQueryStringParameter("payId", mConsumeentity.ID);
		}else{
			params.addQueryStringParameter("spotOrderId", mConsumeentity.ID);
		}
		params.addQueryStringParameter("shopId", mConsumeentity.SHOP_ID);
		params.addQueryStringParameter("score", (int)rating_bar.getRating()+"");
		params.addQueryStringParameter("context", et_pinglun.getText()
				.toString());

		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/spotComment/save", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						GetProgressBar.dismissMyProgressBar();
						if (!StringUtils.isEmpty(msg)) {
							toast(msg);
						}
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						GetProgressBar.dismissMyProgressBar();
						try {
							boolean success = json.getBoolean("success");
							if (success) {
								toast("评论成功!");
								finish();
							} else {
								toast(json.getString("msg"));
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}
}
