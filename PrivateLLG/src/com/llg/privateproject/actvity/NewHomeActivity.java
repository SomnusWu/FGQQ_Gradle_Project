package com.llg.privateproject.actvity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

import com.bjg.lcc.privateproject.R;
import com.finddreams.sortedcontact.CityListActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.help.GetProgressBar;
import com.llg.help.MyFormat;
import com.llg.help.ScreenManager;
import com.llg.help.Util;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.adapters.NewHomeViewPagerAdapter;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.utils.ActivitiesManager;
import com.llg.privateproject.view.CubeTransformer;
import com.llg.privateproject.view.FGQQSelectShareDialog;
import com.llg.privateproject.view.IndictorView;
import com.llg.privateproject.view.MyViewPager;
import com.llg.privateproject.view.OrderStatusDialog;

/**
 * 新版首页 yh 2015.9.15(主界面---在这里可以找到所有界面的始端)
 * */
public class NewHomeActivity extends BaseActivity {
	/** 初始化时间 */
	private long mExitTime = 0;
	@ViewInject(R.id.fl)
	private FrameLayout fl;
	@ViewInject(R.id.newhome_ivIndictor)
	private IndictorView indictor;
	@ViewInject(R.id.homebanner)
	private MyViewPager banner;
	@ViewInject(R.id.redpackage)
	private ImageView redpackage;
	@ViewInject(R.id.tv_city)
	private TextView tvCity;
	@ViewInject(R.id.fresh)
	private TextView fresh;

	private NewHomeViewPagerAdapter viewPagerAdapter;
	// private List<View> listBanner;
	List<Map<String, Object>> listBanner;
	AppContext appContext;
	AnimationDrawable anim;
	Timer timer;
	/** 当前图片标识 */
	private int currentImg = 0;
	/** 自动跳动个数 */
	private int count = 0;
	/** 自动跳动个数 */
	private int count1 = 0;
	/** 列表数 */
	private int count2 = 0;
	/** 默认自动轮播 */
	private boolean isRun = true;
	private boolean isChange = true;
	private Thread thread;
	private int index = 0;
	/** imageview状态 */
	private int state = 0;
	private String lastVersion = "0";
	Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			GetProgressBar.dismissMyProgressBar();
			switch (msg.what) {
			case 0:// 访问失败
					// Toast.makeText(NewHomeActivity.this, "访问失败",
					// Toast.LENGTH_SHORT)
					// .show();
				break;
			case 1:// 访问成功

				viewPagerAdapter.notifyDataSetChanged();
				banner.setAdapter(viewPagerAdapter);

				int type = 0;
				if (listBanner.size() > 0) {
					currentImg = 1;
					count2 = listBanner.size();
					setIndicator();

					if (listBanner.get(1).get("type").toString() != null) {
						if (listBanner.get(1).get("type").toString()
								.equals("null")) {
							type = 0;
						} else {
							type = Integer.parseInt(listBanner.get(1)
									.get("type").toString());

						}
					}
					if (type == 1 || type == 3) {
						redpackage.setBackgroundResource(R.drawable.redpackage);
						redpackage.setVisibility(View.VISIBLE);
						anim.stop();
						anim = (AnimationDrawable) redpackage.getBackground();
						anim.start();
					} else if (type == 2) {
						redpackage
								.setBackgroundResource(R.drawable.yellowpackage);
						redpackage.setVisibility(View.VISIBLE);
						anim.stop();
						anim = (AnimationDrawable) redpackage.getBackground();
						anim.start();
					} else if (type == 0
							|| Double.parseDouble(listBanner.get(1).get("q")
									.toString()) == 0
							|| listBanner.get(1).get("isGettedRed").toString()
									.equals("Y")) {
						redpackage.setBackgroundResource(R.color.transparent);
						redpackage.setVisibility(View.GONE);

					}

					if (listBanner.get(1).get("adStatus") != null
							&& listBanner.get(1).get("adStatus").toString()
									.equals("2")) {
						redpackage.setBackgroundResource(R.color.transparent);
						redpackage.setVisibility(View.GONE);
					}
					if (listBanner.get(1).get("isGettedRed").toString()
							.equals("Y")
							|| listBanner.get(1).get("isEmpty").toString()
									.equals("Y")) {
						redpackage.setBackgroundResource(R.color.transparent);
						redpackage.setVisibility(View.GONE);
					}

					if (redpackage.isShown() && anim != null) {
						anim.start();
					} else if (!redpackage.isShown() && anim != null) {
						anim.stop();
					}
					banner.setCurrentItem(currentImg);
					// changeCurrentimg();
					myThreed();
				}
				break;

			case 2://
				if (isRun) {
					banner.setCurrentItem(currentImg);
				}
				break;
			case 3:// 检测更新
				checkedResult(checkResult);
				break;
			case 4:
				startActivity(new Intent(NewHomeActivity.this,
						PhoneActivity.class));
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newhomelayout);
		ViewUtils.inject(this);
		init();
		ScreenManager.getScreenManager().activityList();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		isRun = true;
//		getCusId(index++);
		getAdList("APP_START_PAGE");

	}

	private void myThreed() {
		// TODO Auto-generated method stub
		count1 = count;
		if (listBanner.size() < 2 || count > 0) {
			return;
		}
		if (thread != null) {
			thread.interrupt();

			thread = null;
		}

		if (thread == null) {

			thread = new Thread() {

				public void run() {

					while (isRun) {

						try {
							sleep(4000);
							if (banner != null) {
								if (count == 0) {
									count1 = listBanner.size();
								}
								// count = listBanner.size();
								currentImg++;
								if (currentImg >= (count1)) {
									currentImg = 0;

								}
								if (isChange) {
									handler.sendEmptyMessage(2);
								}
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
			};
		}
		thread.start();

	}

	ViewPagerScroller scroller;

	/**
	 * 初始化控件
	 */
	private void init() {
		LayoutParams params = new LayoutParams(AppContext.getScreenWidth(),
				AppContext.getScreenWidth());
		banner.setLayoutParams(params);

		scroller = new ViewPagerScroller(this);
		scroller.setScrollDuration(2000);
		scroller.initViewPagerScroll(banner);// 这个是设置切换过渡时间为2秒
		banner.setPageTransformer(true, new CubeTransformer());
		indictor.setVisibility(View.INVISIBLE);
		tvCity.setText("[" + AppContext.myCity + "]");
		appContext = (AppContext) getApplication();
		listBanner = new ArrayList<Map<String, Object>>();

		viewPagerAdapter = new NewHomeViewPagerAdapter(this, listBanner);
		anim = (AnimationDrawable) redpackage.getBackground();
		if (appContext.isNetworkConnected()) {

//			getCusId(index++);
			// getAdList("APP_START_PAGE");
			if (packageManager == null) {
				packageManager = getPackageManager();
			}
			if (packageInfo == null) {
				try {
					packageInfo = packageManager.getPackageInfo(
							getPackageName(), 0);
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			// UpdateVersion.getServiceVersion(this, );
			// UpdateManager.getUpdateManager().checkAppUpdate(this, false);

			versionCheck(packageInfo.versionName);
		} else {
			toast(R.string.t_Connection);
		}
		myThreed();

		// banner.setThread(thread);
	}

	/**
	 * 参数:1.cusId 当前登录人cusId 2.(*) locationCode 广告位code （APP_START_PAGE开始页滑动广告/
	 * APP_PHONE_ADIN打电话弹窗广告） 3.lng 手机当前所在经度 4.lat 手机当前所在纬度
	 */
	private void getAdList(String locationCode) {
		RequestParams params = new RequestParams();
		// params.addQueryStringParameter("dd", ""+System.currentTimeMillis());
		params.addQueryStringParameter("cusId", AppContext.userid);
		params.addQueryStringParameter("locationCode", "APP_START_PAGE");
		params.addQueryStringParameter("lng",
				String.valueOf(AppContext.myLongitude));
		params.addQueryStringParameter("lat",
				String.valueOf(AppContext.myLatitude));

		AppContext.getHtmlUitls().xUtils(this, HttpMethod.GET, "ad/getAdList",
				params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						// Log.d("my", json.toString());
						getAdRedPackageParse1(json);

					}
				});
	}

	/***
	 * 查询通用红包解析
	 * */
	public void getAdRedPackageParse1(JSONObject json) {
		try {
			if (json.getBoolean("success")) {
				JSONArray array = new JSONArray();
				array = json.getJSONArray("obj");

				if (array.length() > 0) {
					if (array.length() > 0) {
						listBanner.clear();
						count = 0;
					}
					for (int i = 0; i < array.length(); i++) {
						JSONObject object;

						object = array.getJSONObject(i);
						if (object.getString("adRedType").length() < 1) {
							String adRedType = "0";
							if (i == 0 || i == array.length() - 1) {
								parseObj1(Integer.parseInt(adRedType), object,
										1);
								parseObj1(Integer.parseInt(adRedType), object,
										1);
							} else {
								parseObj1(Integer.parseInt(adRedType), object,
										0);
							}
						} else {
							if (i == 0 || i == array.length() - 1) {
								parseObj1(Integer.parseInt(object
										.getString("adRedType")), object, 1);
								parseObj1(Integer.parseInt(object
										.getString("adRedType")), object, 1);
							} else {
								parseObj1(Integer.parseInt(object
										.getString("adRedType")), object, 0);
							}
						}

					}
					handler.sendEmptyMessage(1);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void parseObj1(final int type, JSONObject object, int index)
			throws JSONException {
		android.support.v4.view.ViewPager.LayoutParams params = new android.support.v4.view.ViewPager.LayoutParams();
		params.width = AppContext.getScreenWidth();
		params.height = AppContext.getScreenWidth();
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("adid", object.get("id"));// 广告id

		map.put("name", "" + object.get("title"));

		map.put("isGettedRed", object.get("isGettedRed"));
		map.put("imageUrl", object.get("img"));
		if (object.has("isEmpty")) {
			map.put("isEmpty", object.getString("isEmpty"));
			if (object.getDouble("q") > 0
					&& object.get("isGettedRed").equals("N")
					&& object.get("isEmpty").equals("N") && type != 0) {
				count++;

			}
		} else {
			map.put("isEmpty", "N");
			if (object.getDouble("q") > 0
					&& object.get("isGettedRed").equals("N") && type != 0) {
				count++;

			}
		}
		map.put("q", object.getDouble("q"));
		map.put("objectType", object.get("objectType"));

		if (object.has("adStatus")) {
			map.put("adStatus", object.getString("adStatus"));
		}
		if (object.get("objectId") == null) {
			String objectId = "";
			map.put("objectId", "" + objectId);
		} else {
			map.put("objectId", "" + object.get("objectId"));
		}
		if (object.get("adRedEnvelopId") == null) {
			map.put("adRedEnvelopId", "");
		} else {
			map.put("adRedEnvelopId", object.get("adRedEnvelopId"));
		}
		map.put("adInfoId", object.get("id"));// 广告id
		map.put("adForwardId", object.get("adForwardId"));
		if (type == 1) {// 整点抢红包是设置type为1

			map.put("type", String.valueOf(type));

		} else {
			if (object.get("adRedType") != null) {
				if (object.get("adRedType").toString().equals("null")
						|| object.get("adRedType").toString().length() < 1) {
					map.put("type", 0);
				} else {

					map.put("type", object.get("adRedType"));

				}
			} else {

				map.put("type", 0);
			}
		}

		ImageView ivImageView = new ImageView(NewHomeActivity.this);
		ivImageView.setScaleType(ScaleType.FIT_XY);
		ivImageView.setLayoutParams(params);
		ivImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO
				// Auto-generated
				// method stub
				Intent intent = new Intent();
				// if (type != 0) {
				intent = new Intent(NewHomeActivity.this, GetRedPackage.class);
				intent.putExtra("img", map.get("imageUrl").toString());
				intent.putExtra("objectId", map.get("objectId").toString());
				intent.putExtra("objectType", map.get("objectType").toString());
				intent.putExtra("isEmpty", map.get("isEmpty").toString());
				intent.putExtra("adRedEnvelopId", map.get("adRedEnvelopId")
						.toString());
				intent.putExtra("adForwardId", map.get("adForwardId")
						.toString());
				intent.putExtra("type", type);
				intent.putExtra("adInfoId", map.get("adid").toString());// 广告id
				startActivity(intent);

				  
				// } else {

				// MyFormat.toSomeWhere(NewHomeActivity.this,
				// map.get("objectType").toString(),
				// map.get("objectId").toString(), getIntent()
				// .getStringExtra("forwardId"), getIntent()
				// .getStringExtra("chain"));
				// }
			}
		});

		map.put("iv", ivImageView);
		MyFormat.setBitmap(NewHomeActivity.this, ivImageView,
				map.get("imageUrl").toString(), 0, 0);
		if (index > 0) {
			// listBanner.add(map);

		}
		listBanner.add(map);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (thread != null) {
			thread.interrupt();
			thread = null;
		}
		isRun = false;
		GetProgressBar.dismissMyProgressBar();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isRun = false;
	}

	/** 设置指示剂 */
	void setIndicator() {
		// LayoutParams params=new LayoutParams(AppContext.getScreenWidth(),
		// AppContext.getScreenWidth()/5);
		// params.gravity=Gravity.BOTTOM ;
		// indictor.setLayoutParams(params);
		fl.setVisibility(View.VISIBLE);
		indictor.setVisibility(View.VISIBLE);
		indictor.setIndictorMargin(4);
		indictor.setIndictorCount(listBanner.size());

		indictor.setData(listBanner);

		banner.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub

				if (arg0 == count2 - 1) {
					if (scroller != null) {
						scroller.setScrollDuration(0);
					}
					currentImg = 1;

					handler.sendEmptyMessage(2);

				} else if (arg0 == 0) {
					if (scroller != null) {
						scroller.setScrollDuration(0);
					}
					currentImg = count2 - 2;

					handler.sendEmptyMessage(2);

				} else {
					if (scroller != null) {
						scroller.setScrollDuration(2000);
					}
					currentImg = arg0;

				}
				arg0 = currentImg;
				indictor.setIndictorSelectedIndex(arg0);
				// Toast.makeText(NewHomeActivity.this, ""+arg0,
				// Toast.LENGTH_SHORT).show();
				int type = Integer.parseInt(listBanner.get(arg0).get("type")
						.toString());

				setRedPackage(type, arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub页码,偏移比,偏移像素

				// if(arg0==1&&state==2){
				//
				// handler.sendEmptyMessage(2);
				// currentImg=(count2-1);
				//
				// }else if(arg0==(count2-1)&&state==2){
				//
				// }
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub0正常,1滑动,2滑动完成
				state = arg0;

				// Log.d("my", "thread"+thread);
				switch (arg0) {
				case 0:
					isRun = true;

					break;
				case 1:
					isChange = false;
					// isRun = false;
					if (thread != null) {
						// try {
						// thread.sleep(5000);
						// } catch (InterruptedException e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// }
					}
					break;

				case 2:
					isChange = true;

					// thread.run();

					// myThreed();

					break;
				}
			}
		});
	}

	/** type=0不显示,type=1显示红包,type=2 显示黄包 ,type=3混合包 */
	public void setRedPackage(int type, int arg0) {
		if (listBanner.get(arg0).get("adStatus") != null
				&& listBanner.get(arg0).get("adStatus").toString().equals("2")) {
			redpackage.setBackgroundResource(R.color.transparent);
			redpackage.setVisibility(View.GONE);
		}
		if (listBanner.get(arg0).get("isGettedRed").toString().equals("Y")
				|| listBanner.get(arg0).get("isEmpty").toString().equals("Y")) {

			redpackage.setBackgroundResource(R.color.transparent);
			redpackage.setVisibility(View.GONE);
		} else {
			if (type == 1 || type == 3) {
				redpackage.setBackgroundResource(R.drawable.redpackage);
				redpackage.setVisibility(View.VISIBLE);
				anim.stop();
				anim = (AnimationDrawable) redpackage.getBackground();
				anim.start();
			} else if (type == 2) {
				redpackage.setBackgroundResource(R.drawable.yellowpackage);
				redpackage.setVisibility(View.VISIBLE);
				anim.stop();
				anim = (AnimationDrawable) redpackage.getBackground();
				anim.start();

			} else {
				redpackage.setBackgroundResource(R.color.transparent);
				redpackage.setVisibility(View.GONE);

			}
		}
		if (redpackage.isShown() && anim != null) {
			anim.start();
		} else if (!redpackage.isShown() && anim != null) {
			anim.stop();
		}
	}

	/** 物理按键返回退出系统 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) < 2000) {
				android.os.Process.killProcess(android.os.Process.myPid());
			} else {
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				ActivitiesManager.getAppManager().AppExit(NewHomeActivity.this);
				mExitTime = System.currentTimeMillis();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@OnClick({ R.id.wodehoutai, R.id.dianhua, R.id.shangpinhui,
			R.id.xianchangxiaofei, R.id.haoyoufazhan, R.id.yewulianxi,
			R.id.tv_city, R.id.fresh })
	public void myclick(View v) {
		switch (v.getId()) {
		case R.id.xianchangxiaofei:// 特惠商家
			startActivity(new Intent(this, BusinessListAty.class));
			break;
		case R.id.shangpinhui:// 商品汇
			startActivity(new Intent(this, MainActivity.class));
			break;
		case R.id.wodehoutai:// 个人中心
			startActivity(new Intent(this, MyHome.class));
//			FGQQSelectShareDialog dialog =new FGQQSelectShareDialog(this, null);
//			dialog.show();
			break;
		case R.id.haoyoufazhan:// 邀请好友(我的会员)
			if (UserInformation.isLogin()) {
				Intent intent = new Intent(this, Huiyuandengji.class);
				intent.putExtra("type", 2);
				startActivity(intent);
			} else {
				goWebLoginActivity();
			}
			break;
		case R.id.yewulianxi:// 我的广告
			// intent.setClass(mActivity, MineAdvertisementAty.class);
			startActivity(new Intent(this, MineAdvertisementAty.class));
			break;
		case R.id.tv_city:// 城市选择
			Intent intent2 = new Intent(this, CityListActivity.class);
			startActivityForResult(intent2, 1);
			break;
		case R.id.fresh:// 刷新

			break;
		case R.id.dianhua:// 打电话

			if (TextUtils
					.isEmpty(getSharePrefence().getString("phone", "null"))
					|| getSharePrefence().getString("phone", "null").equals(
							"null")) {
				customProgressSmall.show();
				getPhone();
				return;
			}
			startActivity(new Intent(this, PhoneActivity.class));
			break;
		}
	}

	private void getPhone() {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("appid", Util.appId(this));
		AppContext.getHtmlUitls().xUtils(this, HttpMethod.POST,
				"tel/getRegMobile", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						customProgressSmall.dismiss();
						toast(msg);
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						Log.i("tag", json.toString());
						if (json.optBoolean("success")) {
							customProgressSmall.dismiss();
							String phone = json.optString("obj");
							if (TextUtils.isEmpty(phone)
									|| phone.equals("null")) {
								if (!UserInformation.isLogin()) {
									startActivity(new Intent(
											NewHomeActivity.this,
											WebLoginActivity.class));
									return;
								} else {
									startActivity(new Intent(
											NewHomeActivity.this,
											PhoneRegistAty.class));
								}
								return;
							}
							getSharePrefence().edit().putString("phone", phone)
									.commit();
							handler.sendEmptyMessage(4);
						} else {
							customProgressSmall.dismiss();
							toast("未知错误");
						}
					}
				});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
			tvCity.setText("[" + data.getStringExtra("name") + "]");
			AppContext.selectCityCode = data.getStringExtra("code");
			AppContext.selectBaiduCode = data.getStringExtra("baidu_code");
		}
	}

	private void getCusId(final int index) {
		if (index > 2) {
			return;
		}
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", UserInformation.getAccess_token());
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/security/getCurrCus", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						if (msg.equals("401")) {
							AppContext.isLogin = false;
							UserInformation.setLogin(false);
							// getSharedPreferences("userInformation1",
							// Context.MODE_PRIVATE).edit()
							// .putBoolean("isLogin", false)
							// .putString("userid", "").commit();
							// AppContext.userid = "";
						}
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub

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
								String pictureUrl = "";
								if (json.getJSONObject("obj").getString(
										"pictureUrl") != null) {
									pictureUrl = json.getJSONObject("obj")
											.getString("pictureUrl");
								}
								editor.putString("userid", userid);
								editor.putString("userType", userType);
								editor.putString("appellation", appellation);
								editor.putString("pictureUrl", pictureUrl);
								editor.commit();
								AppContext.userid = userid;
								AppContext.userType = userType;
								AppContext.appellation = appellation;
								// finish();
							} 
//							else if (json.get(MyFormat.errorCode) != null
//									&& json.getString(MyFormat.errorCode)
//											.equals(MyFormat.NOT_LOGIN)) {
//								setRefreshListtener(new Refresh() {
//
//									@Override
//									public void refreshRequst(
//											String access_token) {
//										// TODO Auto-generated method stub
//										getCusId(NewHomeActivity.this.index++);
//									}
//								});
//								RefeshToken();
//							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	private int checkResult = 4;
	private String url = "";
	private PackageManager packageManager;
	private PackageInfo packageInfo;

	/**
	 * 检查版本更新
	 * 
	 * @param :version 返回:checkResult:1~4 (1必须强制更新，2提示更新，3检查更新，4无需更新)
	 * */
	private void versionCheck(String versionname) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("version", String.valueOf(versionname));
		params.addQueryStringParameter("osType", String.valueOf(2));
		AppContext.getHtmlUitls().xUtils(this, HttpMethod.GET, "version/check",
				params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						// Log.d("my", "版本更新信息" + json);
						try {
							if (json.getBoolean("success")) {
								JSONObject obj = json.getJSONObject("obj");
								checkResult = obj.getInt("checkResult");
								if (obj.has("updateLog")
										&& null != obj.get("updateLog")) {
									hint = obj.getString("updateLog");
								}
								if (obj.has("url") && obj.get("url") != null) {

									url = obj.getString("url");
								}
								lastVersion = obj.getString("lastVersion");

							} else {
								// toast("当前版本:" + packageInfo.versionCode);
							}
							handler.sendEmptyMessage(3);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	private OrderStatusDialog dialog;
	private String hint = "";

	private void checkedResult(int checkResult) {
		if (packageInfo.versionName.equals(lastVersion)) {

		}
		if (checkResult == 1) {// 必须强制更新代码
			toast("当前版本:" + packageInfo.versionName + ",当前版本存在异常,请及时更新");
			if (dialog == null) {

				dialog = new OrderStatusDialog(this, 10, hint, url);
			}
			dialog.show();
			Window window = dialog.getWindow();
			android.view.WindowManager.LayoutParams lp = window.getAttributes();
			lp.width = AppContext.getScreenWidth() * 4 / 5;
			lp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
			window.setAttributes(lp);
		} else if (checkResult == 2) {// 提示更新
			toast("当前版本:" + packageInfo.versionName + ",有新版本可更新");
			if (dialog == null) {

				dialog = new OrderStatusDialog(this, 10, hint, url);

			}
			dialog.show();
			Window window = dialog.getWindow();
			android.view.WindowManager.LayoutParams lp = window.getAttributes();
			lp.width = AppContext.getScreenWidth() * 4 / 5;
			lp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
			window.setAttributes(lp);

		} else if (checkResult == 3) {// 检查更新
			toast("当前版本:" + packageInfo.versionName);
		} else if (checkResult == 4) {// 无需更新
			// toast("当前版本:" + packageInfo.versionName + ",当前已是最新版本,无需更新");
		}
	}

}
