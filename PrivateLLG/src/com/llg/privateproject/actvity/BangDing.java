package com.llg.privateproject.actvity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;

/**
 * 邮箱手机绑定 yh 2015.6.12
 * */
public class BangDing extends BaseActivity implements OnClickListener {
	/** 返回 */
	@ViewInject(R.id.turn)
	private ImageView turn;
	/** 标题 */
	@ViewInject(R.id.bangding_title)
	private TextView bangding_title;
	/** 邮箱/手机 */
	@ViewInject(R.id._name)
	private TextView _name;
	/** 邮箱注册的新密码/修改密码的新密码*/
	@ViewInject(R.id._newpassword)
	private TextView _newpassword;
	/**  */
	@ViewInject(R.id.v)
	private View v;
	/** 验证码 */
	@ViewInject(R.id._myid)
	private TextView _myid;
	/** 发送验证码 */
	@ViewInject(R.id.code)
	private TextView code;
	/** 提交 */
	@ViewInject(R.id.tv_commit)
	private TextView tv_commit;
	/** 邮箱或手机号输入框 */
	@ViewInject(R.id.et_youxiang)
	private EditText et_youxiang;
	/** 验证码输入框 */
	@ViewInject(R.id.myid)
	private EditText myid;
	/** 修改密密码布局 */
	@ViewInject(R.id.newpassword_rl)
	private RelativeLayout newpassword_rl;

	/** 新密码输入框 */
	@ViewInject(R.id.et_newpassword)
	private EditText et_newpassword;
	/**  */
	@ViewInject(R.id.rl_code)
	private RelativeLayout rl_code;
	Intent intent = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.youxiang_shouji_bangding);
		ViewUtils.inject(this);
		intent = getIntent();
		code.setOnClickListener(this);
		turn.setOnClickListener(this);
		tv_commit.setOnClickListener(this);
		et_youxiang.setHintTextColor(getResources().getColor(R.color.black3));
		myid.setHintTextColor(getResources().getColor(R.color.black3));
		et_newpassword
				.setHintTextColor(getResources().getColor(R.color.black3));
		bangding_title.setText(intent.getStringExtra("title"));
		_name.setText(intent.getStringExtra("name"));
		if (intent.getStringExtra("title").equals("邮箱绑定")) {
			rl_code.setVisibility(View.GONE);
			et_youxiang.setHint("请输入您要绑定的邮箱");
			v.setVisibility(View.INVISIBLE);
			et_youxiang
					.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
			newpassword_rl.setVisibility(View.GONE);
		} else if (intent.getStringExtra("title").equals("手机绑定")) {
			et_youxiang.setHint("请输入您要绑定的手机");
			et_youxiang.setInputType(InputType.TYPE_CLASS_PHONE);
			newpassword_rl.setVisibility(View.GONE);

		} else if (intent.getStringExtra("title").equals("修改密码")) {
			code.setVisibility(View.GONE);
			newpassword_rl.setVisibility(View.VISIBLE);
			et_youxiang.setHint("请输入原密码");
			et_youxiang.setTransformationMethod(PasswordTransformationMethod
					.getInstance());
			et_newpassword.setHint("请输入新密码");
			_newpassword.setText(intent.getStringExtra("name1"));
			_myid.setText("确认新密码 :");
			myid.setHint("请确认新密码");
			myid.setHintTextColor(getResources().getColor(R.color.black3));
			myid.setTransformationMethod(PasswordTransformationMethod
					.getInstance());
		}
	}

	// @OnChildClick({ R.id.turn, R.id.code, R.id.tv_commit })
	// public void myClick(View v) {
	//
	// switch (v.getId()) {
	// case R.id.turn:
	// finish();
	// break;
	// case R.id.code:
	// Toast.makeText(this, "验证码", Toast.LENGTH_SHORT).show();
	// break;
	// case R.id.tv_commit:
	// Toast.makeText(this, "提交", Toast.LENGTH_SHORT).show();
	//
	// break;
	//
	// default:
	// break;
	// }
	// }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.turn:
			finish();
			break;
		case R.id.code:
			Toast.makeText(this, "验证码", Toast.LENGTH_SHORT).show();
			break;
		case R.id.tv_commit://提交修改密码
			String methodString = "";
			RequestParams params = new RequestParams();

			if (intent.getStringExtra("title").equals("邮箱绑定")) {// 邮箱绑定
				if (et_youxiang.getText() == null
						|| et_youxiang.getText().length() < 6) {
					Toast.makeText(this, "请输入邮箱", Toast.LENGTH_LONG).show();
					return;
				}
				params.addQueryStringParameter("email", et_youxiang.getText()
						.toString());
				methodString = "m/security/valiDataEmail";
				params.addQueryStringParameter("access_token", UserInformation.getAccess_token());
				params.addQueryStringParameter("time",""+ System.currentTimeMillis());
//				final CustomProgressSmall customProgressSmall = new CustomProgressSmall(
//						this);
//				customProgressSmall.show();
				AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
						methodString, params, new HttpCallback() {

							@Override
							public void onError(String msg) {
								// TODO Auto-generated method stub
								Log.i("tag", "----------失败了------");
//								customProgressSmall.dismiss();
								finish();
							}

							@Override
							public void onBack(JSONObject json) {
								// TODO Auto-generated method stub

//								customProgressSmall.dismiss();
								finish();
								try {

									Toast.makeText(BangDing.this,
											json.getString("msg"),
											Toast.LENGTH_SHORT).show();
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						});
			} else if (intent.getStringExtra("title").equals("修改密码")) {// 修改密码
				if (et_newpassword.getText() == null
						|| et_newpassword.getText().length() < 6) {
					Toast.makeText(this, "请输入不小于6位密码", Toast.LENGTH_LONG)
							.show();
					return;
				}
				if( et_youxiang
						.getText().toString().equals(et_newpassword
						.getText().toString())){
					Toast.makeText(this, "原密码和新密码不能相同", Toast.LENGTH_SHORT)
					.show();
				return;	
				}
				if (!myid.getText().toString()
						.equals(et_newpassword.getText().toString())) {
					Toast.makeText(this, "您两次输入的新密码不一致", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if( et_youxiang
						.getText().toString().equals(et_newpassword
						.getText().toString())){
					Toast.makeText(this, "原密码和新密码不能相同", Toast.LENGTH_SHORT)
					.show();
				return;	
				}
				params.addQueryStringParameter("formerPwd", et_youxiang
						.getText().toString());
				params.addQueryStringParameter("newPwd", et_newpassword
						.getText().toString());
				methodString = "m/security/updatePwd";
				params.addQueryStringParameter("access_token", UserInformation.getAccess_token());
				params.addQueryStringParameter("time",""+ System.currentTimeMillis());
//				final CustomProgressSmall customProgressSmall = new CustomProgressSmall(
//						this);
//				customProgressSmall.show();
				AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
						methodString, params, new HttpCallback() {

							@Override
							public void onError(String msg) {
								// TODO Auto-generated method stub
					
//								customProgressSmall.dismiss();
							}

							@Override
							public void onBack(JSONObject json) {
								// TODO Auto-generated method stub
								
								try {
									if(json.getBoolean("success")){
										Toast.makeText(BangDing.this, "修改密码成功",
												Toast.LENGTH_SHORT).show();
//										customProgressSmall.dismiss();
										UserInformation.setLogin(true);
										AppContext.isLogin = true;
//										SharedPreferences userPreferences = getSharedPreferences(
//												"userInformation1",
//												Context.MODE_PRIVATE);
//										Editor editor = userPreferences.edit();
//										editor.putString("password", et_newpassword
//												.getText().toString());
//										editor.commit();
										getSharedPreferences("userInformation1",
												Context.MODE_PRIVATE).edit()
												.putBoolean("isLogin", true).commit();
										finish();
									}else{
										if(json.get("msg")!=null){
					
											toast(json.getString("msg"));
				}
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							
							}
						});
			}

	

			break;

		default:
			break;
		}
	}
}
