package com.llg.privateproject.actvity;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
//import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.help.Code;
import com.llg.help.GetProgressBar;
import com.llg.privateproject.AppContext;
import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.utils.Constants;
import com.llg.privateproject.view.DialogRegist;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebView.HitTestResult;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements
		PlatformActionListener, WeiboAuthListener {
	@ViewInject(R.id.rg_l_r)
	private RadioGroup rg_l_r;// 登录注册
	@ViewInject(R.id.iv_back)
	private ImageView iv_back;// 返回
	@ViewInject(R.id.rb_login)
	private RadioButton rb_login;// 跳到登录界面
	@ViewInject(R.id.rb_register)
	private TextView rb_register;// 跳到注册
	/** 跳到忘记密码 */
	@ViewInject(R.id.tv_wangjimima)
	private TextView tv_wangjimima;
	// 登录界面控件
	@ViewInject(R.id.sv_l)
	private LinearLayout sv_l;// 登录界面
	@ViewInject(R.id.sv_r)
	private LinearLayout sv_r;// 注册界面
	@ViewInject(R.id.ll_r_p)
	private LinearLayout ll_r_p;// 注册协议
	@ViewInject(R.id.fresh_code)
	private ImageView fresh_code;// 刷新验证码
	Platform platform;// 三方平台
	/** 用户输入框 */
	@ViewInject(R.id.et_username)
	private EditText et_username;
	/** 用户密码输入框 */
	@ViewInject(R.id.et_password)
	private EditText et_password;
	/** 验证码输入框 */
	@ViewInject(R.id.et_code)
	private EditText et_code;

	// 注册界面控件
	@ViewInject(R.id.r_tv_smscode)
	private TextView r_tv_smscode;// 获取短信验证码
	@ViewInject(R.id.r_cb_isagree)
	private CheckBox r_cb_isagree;// 选择是否同意注册协议
	@ViewInject(R.id.r_tv_agreement)
	private TextView r_tv_agreement;// 注册协议按钮
	@ViewInject(R.id.r_tv_register)
	private TextView r_tv_register;// 刷新验证码
	@ViewInject(R.id.r_tv_agreement)
	private TextView tvAgreement;// 刷新验证码

	/** 用户输入框 */
	@ViewInject(R.id.r_et_username)
	private EditText r_et_username;
	/** 注册密码输入框 */
	@ViewInject(R.id.r_et_password)
	private EditText r_et_password;
	/** 注册用户输入框 */
	@ViewInject(R.id.r_et_passwordconfirm)
	private EditText r_et_passwordconfirm;
	/** 短信验证码输入框 */
	@ViewInject(R.id.r_et_smscode)
	private EditText r_et_smscode;
	/** WebView */
	@ViewInject(R.id.wv)
	private WebView wv;

	/** 微博AuthInfo对象 */
	private AuthInfo mauthInfo;
	/** 微博Oauth2AccessToken对象 */
	private Oauth2AccessToken mAccessToken;
	/** 微博SsoHandler对象用于授权 */
	private SsoHandler mSsoHandler;
	/** 用户名 */
	private String username = "";
	private String password = "";
	SharedPreferences userPreferences;
	@SuppressLint("ResourceAsColor")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			GetProgressBar.dismissMyProgressBar();
			switch (msg.what) {
			case 0:// 访问失败

				break;
			case 1:// 访问成功
				getCusId();
				break;

			default:
				break;
			}
		}
	};

	private void getCusId() {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", UserInformation.getAccess_token());
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.GET,
				"m/security/getCurrCus", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						// myLogShow("my", "m/security/getCurrCus:"+json);
						try {
							if (json.getBoolean("success")) {

								SharedPreferences userPreferences = getSharedPreferences(
										"userInformation1",
										Context.MODE_PRIVATE);
								Editor editor = userPreferences.edit();
								String userid = "0";
								String userType = "0";
								String appellation = "快乐天使";
								userid = json.getJSONObject("obj").getString(
										"id");
								userType = json.getJSONObject("obj").getString(
										"userType");
								if (json.getJSONObject("obj").getString(
										"appellation") == null) {
									appellation = "快乐天使";
								} else {
									appellation = json.getJSONObject("obj")
											.getString("appellation");
								}
								editor.putString("userid", userid);
								editor.putString("userType", userType);
								editor.putString("appellation", appellation);
								editor.commit();
								AppContext.userid = userid;
								AppContext.userType = userType;
								AppContext.appellation = appellation;
								finish();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginregister);
		ViewUtils.inject(this);
		userPreferences = getSharedPreferences("userInformation1",
				Context.MODE_PRIVATE);

		et_username.setText(userPreferences.getString("username", ""));
		et_password.setText(userPreferences.getString("password", ""));
		// iv_back.setOnClickListener(this);
		fresh_code.setImageBitmap(Code.getInstance().getBitmap());
		et_username.setHintTextColor(getResources().getColor(
				R.color.login_unchecked));
		et_password.setHintTextColor(getResources().getColor(
				R.color.login_unchecked));
		et_code.setHintTextColor(getResources().getColor(
				R.color.login_unchecked));
		r_et_username.setHintTextColor(getResources().getColor(
				R.color.login_unchecked));
		r_et_password.setHintTextColor(getResources().getColor(
				R.color.login_unchecked));
		r_et_passwordconfirm.setHintTextColor(getResources().getColor(
				R.color.login_unchecked));
		r_et_smscode.setHintTextColor(getResources().getColor(
				R.color.login_unchecked));
		r_tv_smscode.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		rb_register.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		tv_wangjimima.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		ShareSDK.initSDK(this);
		mauthInfo = new AuthInfo(this, Constants.APP_KEY,
				Constants.REDIRECT_URL, Constants.SCOPE);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		GetProgressBar.dismissMyProgressBar();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ShareSDK.stopSDK(this);
		if (platform != null)
			platform.removeAccount();

	}

	// case R.id.rb_login:// 显示登录界面
	// sv_l.setVisibility(View.VISIBLE);
	// sv_r.setVisibility(View.GONE);

	// case R.id.rb_register:// 显示注册界面

	/** 点击按钮 */
	@OnClick({ R.id.iv_back, R.id.fresh_code, R.id.tv_wangjimima,
			R.id.tv_login, R.id.iv_qqlogin, R.id.iv_weibologin,
			R.id.r_tv_smscode, R.id.r_cb_isagree, R.id.r_tv_agreement,
			R.id.r_tv_register, R.id.rb_register })
	public void myClick(View v) {
		int type;
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.tv_wangjimima:// 忘记密码
			// sv_l.setVisibility(View.GONE);
			// sv_r.setVisibility(View.VISIBLE);
			type = 1;
			Intent intent1 = new Intent(this, RegisterActivity.class);
			intent1.putExtra("type", type);
			startActivity(intent1);
			// r_tv_register.setText("提交");
			// ll_r_p.setVisibility(View.INVISIBLE);
			// Toast.makeText(this, "忘记密码", Toast.LENGTH_SHORT).show();
			break;
		case R.id.rb_register:// 去注册
			// sv_l.setVisibility(View.GONE);
			// sv_r.setVisibility(View.VISIBLE);
			type = 2;
			Intent intent = new Intent(this, RegisterActivity.class);
			intent.putExtra("type", type);
			startActivity(intent);
			// r_tv_register.setText("注册");
			// ll_r_p.setVisibility(View.VISIBLE);
			// Toast.makeText(this, "去注册", Toast.LENGTH_SHORT).show();
			break;
		// ++++++++++++++++登录界面按钮+++++++++++++++++++++++++++++++++++
		case R.id.fresh_code:// 刷新验证码
			fresh_code.setImageBitmap(Code.getInstance().getBitmap());
			LogUtils.d("" + Code.getInstance().getCode());
			Toast.makeText(this, "刷新验证码", Toast.LENGTH_SHORT).show();
			break;

		case R.id.tv_login:// 登录
			login();
			break;
		case R.id.iv_qqlogin:// 刷新验证码
			Toast.makeText(this, "qq登录", Toast.LENGTH_SHORT).show();
			platform = ShareSDK.getPlatform(this, QQ.NAME);
			platform.setPlatformActionListener(this);
			platform.showUser(null);// 执行登录，登录后在回调里面获取用户资料
			// platform.showUser(“3189087725”);//获取账号为“3189087725”的资料platform.showUser(null);
			platform.authorize();
			break;
		case R.id.iv_weibologin:// 刷新验证码
			Toast.makeText(this, "微博登录", Toast.LENGTH_SHORT).show();
			// platform=ShareSDK.getPlatform(this,SinaWeibo.NAME);
			// platform.SSOSetting(true);//设置false表示未授权
			// platform.setPlatformActionListener(this);
			// platform.showUser(null);//执行登录，登录后在回调里面获取用户资料
			// platform.authorize();
			mSsoHandler = new SsoHandler(this, mauthInfo);
			mSsoHandler.authorize(this);
			break;
		// +++++++++++++++++++++注册界面按钮+++++++++++++++++++++++++++++++++++++++++++++
		case R.id.r_tv_smscode:// 获取短信验证码
			Toast.makeText(this, "已向您手机发了一条验证码", Toast.LENGTH_SHORT).show();
			r_tv_smscode.setClickable(false);
			CountDownTimer timer = new CountDownTimer(1000 * 60, 1000) {

				@Override
				public void onTick(long m) {
					// TODO Auto-generated method stub
					r_tv_smscode.setText("" + m / 1000 + "s重发");
					// Log.e("my","m:"+ m);
				}

				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					r_tv_smscode.setText("获取验证码");
					r_tv_smscode.setClickable(true);

				}
			};
			timer.start();
			break;

		case R.id.r_cb_isagree:// 是否同意注册协议#fbc16d
			// if (!r_cb_isagree.isChecked()) {
			// // r_tv_register.setEnabled(true);
			// // r_tv_register.setBackgroundResource(R.color.login_checked);
			// Toast.makeText(this, "亲同意注册协议才可注册哦!", Toast.LENGTH_SHORT)
			// .show();
			// return;
			// }
			break;
		case R.id.r_tv_agreement:// 注册协议按钮
			startActivity(new Intent(this, MyDialog.class));
			Toast.makeText(this, "注册协议按钮", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

	/** 登录 */
	private void login() {
		GetProgressBar.getProgressBar(this);
		username = et_username.getText().toString().trim();
		password = et_password.getText().toString().trim();

		if (username == null || username.length() < 1) {
			Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
			return;
		}
		if (password == null || password.length() < 6) {
			Toast.makeText(this, "请输入密码不小于6位密码", Toast.LENGTH_SHORT).show();
			return;

		}
		RequestParams params = new RequestParams();
		params.addBodyParameter("client_id", "mobile-client");
		params.addBodyParameter("client_secret", "mobile");
		params.addBodyParameter("grant_type", "password");
		params.addBodyParameter("username", username);
		params.addBodyParameter("password", password);
		params.addBodyParameter("scope", "read+write");

		SharedPreferences userPreferences = getSharedPreferences(
				"userInformation1", Context.MODE_PRIVATE);
		Editor editor = userPreferences.edit();
		String userid = "0";
		String userType = "0";
		editor.putBoolean("isLogin", false);
//		editor.putString("userid", "");
		editor.putString("userType", "");
		editor.commit();
//		AppContext.userid = "";
		AppContext.userType = "0";
		AppContext.isLogin = false;
		UserInformation.setLogin(false);
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				AppContext.setLogurl(), params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

						handler.sendEmptyMessage(0);
						Toast.makeText(LoginActivity.this, "登录失败,请重试",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						handler.sendEmptyMessage(1);
						try {
							UserInformation.setAccess_token(
									json.get("access_token").toString());
							UserInformation.setLogin(true);
							AppContext.isLogin = true;
							SharedPreferences userPreferences = getSharedPreferences(
									"userInformation1", Context.MODE_PRIVATE);
							Editor editor = userPreferences.edit();
							AppContext.username = username;
							editor.putString("username", username);
							editor.putString("password", password);

							editor.commit();
							getSharedPreferences("userInformation1",
									Context.MODE_PRIVATE).edit()
									.putBoolean("isLogin", true).commit();
							Toast.makeText(LoginActivity.this, "登录成功",
									Toast.LENGTH_SHORT).show();

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}

	@Override
	public void onCancel(Platform arg0, int arg1) {
		// TODO Auto-generated method stub
		LogUtils.e("onCancel+++arg0.toString" + arg0.toString() + arg1);

	}

	@Override
	public void onComplete(Platform arg0, int arg1, HashMap<String, Object> res) {
		// TODO Auto-generated method stub
		LogUtils.e("onComplete+++arg0.toString" + arg0.toString() + arg1
				+ res.toString());
		// 解析部分用户资料字段
		String id, name, description, profile_image_url;
		id = res.get("id").toString();// ID
		name = res.get("name").toString();// 用户名
		description = res.get("description").toString();// 描述
		profile_image_url = res.get("profile_image_url").toString();// 头像链接

		String str = "ID: " + id + ";\n" + "用户名： " + name + ";\n" + "描述："
				+ description + ";\n" + "用户头像地址：" + profile_image_url + "token"
				+ res.get("").toString();
		System.out.println("用户资料: " + str);
	}

	@Override
	public void onError(Platform arg0, int arg1, Throwable arg2) {
		// TODO Auto-generated method stub
		LogUtils.e("onError+++arg0.toString" + arg0.toString() + arg1
				+ arg2.toString());

	}

	// ++++++++++++++++++++++++++++++微博接口WeiboAuthListener重写方法++++++++++++++++++++++++++++++++++
	@Override
	public void onCancel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onComplete(Bundle values) {
		// TODO Auto-generated method stub
		mAccessToken = Oauth2AccessToken.parseAccessToken(values);
		if (mAccessToken.isSessionValid()) {
			Log.d("my", "token:" + mAccessToken);
			Log.d("my", "mAccessToken.getUid():" + mAccessToken.getUid());
			Log.d("my", "mAccessToken.getToken():" + mAccessToken.getToken());
			Log.d("my",
					"mAccessToken.getRefreshToken():"
							+ mAccessToken.getRefreshToken());
			Log.d("my",
					"mAccessToken.getExpiresTime():"
							+ mAccessToken.getExpiresTime());

		} else {
			Log.d("my", "code:" + values.getString("code"));

		}
	}

	@Override
	public void onWeiboException(WeiboException arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * 以上三种授权需要在Activity的`onActivityResult`函数中，调用以下方法： ```java
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
		if (requestCode == 10) {
			Log.d("my", "+data.toString()" + data.toString());
		}
	}

}
