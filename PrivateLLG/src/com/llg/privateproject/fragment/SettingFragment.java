package com.llg.privateproject.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.help.MyFormat;
import com.llg.help.SetListHeigt;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.actvity.Consume;
import com.llg.privateproject.actvity.ContactUsActivity;
import com.llg.privateproject.actvity.FeedBackActivity;
import com.llg.privateproject.actvity.FinancialOrderAty;
import com.llg.privateproject.actvity.GeRenZiLiao;
import com.llg.privateproject.actvity.GuanzhuActivity;
import com.llg.privateproject.actvity.Huiyuandengji;
import com.llg.privateproject.actvity.MineAdvertisementAty;
import com.llg.privateproject.actvity.MineBalanceAty;
import com.llg.privateproject.actvity.OrderStatusActivity;
import com.llg.privateproject.actvity.SecurityCenter;
import com.llg.privateproject.actvity.ShakeScreen;
import com.llg.privateproject.actvity.SysSetting;
import com.llg.privateproject.actvity.WebLoginActivity;
import com.llg.privateproject.adapters.InformationItemAdapter;
import com.llg.privateproject.entities.Customer;
import com.llg.privateproject.entities.InformationItem;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.utils.Constants;
import com.llg.privateproject.utils.StringUtils;

/**
 * 设置 Fragment
 * 
 * @author gongyibing
 * @version 1.0
 * @created 2015-05-07
 */
public class SettingFragment extends BaseFragment implements
		OnItemClickListener {
	private static final String TAG = "SettingFragment";
	private Activity mActivity;
	@ViewInject(R.id.infomation_lv)
	private ListView information_lv;// 个人中心列表
	@ViewInject(R.id.username)
	// 用户名
	private TextView username;
	@ViewInject(R.id.usergrade)
	// 用户标识
	private TextView usergrade;
	@ViewInject(R.id.title_tv2)
	private TextView title_tv2;
	@ViewInject(R.id.title_tv3)
	private TextView title_tv3;

	@ViewInject(R.id.userheadbg)
	private ImageView ivHeadbg;

	@ViewInject(R.id.iv_set)
	private ImageView ivSet;

	@ViewInject(R.id.rl_userinformation)
	private RelativeLayout rl_userinformation;// 用户信息
	UserInformation userInformation = AppContext.getUserInformation();// 用户信息
	@ViewInject(R.id.iv_login)
	private ImageView iv_login;// 用户头像
	@ViewInject(R.id.tv_login)
	private TextView tv_login;// 登录/注册
	private List<InformationItem> list;// 用户信息列表
	private AppContext appContext;
	private String imgUrl;// 用户头像地址;
	private Bitmap bitmap;// 用户头像
	private Drawable roundedAvatarDrawable;// 用户头像

	public Customer customer;// 个人用户资料
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Constants.SUCCESS:

				break;
			case 3:
				setinformation();
				break;

			default:
				break;
			}
		}
	};

	public static SettingFragment newInstance() {
		SettingFragment settingFragment = new SettingFragment();
		return settingFragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		this.mActivity = activity;
		appContext = (AppContext) activity.getApplication();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences userPreferences = mActivity.getSharedPreferences(
				"userInformation1", Context.MODE_PRIVATE);
		// getCusId();

		// 用户信息列表
		list = new ArrayList<InformationItem>();
		// int
		// images[]=getResources().getIntArray(R.array.userinformationimages);
		// 用户信息图片数组
		int images[] = new int[] { R.drawable.gr_wcldd, R.drawable.personer,
				R.drawable.songhuoshangmen_small, R.drawable.guanzhu,
				R.drawable.suggest, R.drawable.wdeguanggao, R.drawable.help,

				R.drawable.level, R.drawable.safe, R.drawable.shake,
				R.drawable.suggest, R.drawable.contact };
		// 用户信息描述列表

		String describe[] = getResources().getStringArray(
				R.array.userinformationdescribe);
		// 用户信息名称
		String name[] = getResources().getStringArray(R.array.userinformation);

		for (int i = 0; i < name.length; i++) {
			if (name[i].equals("摇一摇")) {
				continue;
			}
			if (name[i].equals("联系我们")) {
				continue;
			}
			if (name[i].equals("待处理订单")
					&& (!userPreferences.getString("userType", "0").equals(
							"103"))) {

				continue;
			}
			if (name[i].equals("我的任务")) {
				continue;
			}
			if (name[i].equals("我的发布")) {
				continue;
			}
			// 1:我的订单状态 6:现场消费订单
			list.add(new InformationItem(getActivity(), images[i], name[i],
					describe[i]));
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_setting, container,
				false);
		// 在使用注解绑定控件的时候，一定记得在onCreate中调用ViewUtils.inject(this);
		ViewUtils.inject(this, view);
		InformationItemAdapter adapter = new InformationItemAdapter(
				getActivity(), list);
		SetListHeigt listHeigt = new SetListHeigt();
		information_lv.setAdapter(adapter);
		// 设置列表高度
		listHeigt.setlistHeight(information_lv);
		information_lv.setOnItemClickListener(this);
		getUserInfo();
		return view;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 4 && resultCode == Activity.RESULT_OK
				&& data != null) {
			imgUrl = data.getStringExtra("imgUrl");

			if (imgUrl != null) {
				MyFormat.setBitmap(getActivity(), iv_login, imgUrl, 0, 0);
			}
		}
	}

	/** 设置提示 */
	private void setBadgeView() {

		// badge1=new BadgeView(getActivity(), mTitleTv);
		// badge2=new BadgeView(getActivity(), title_tv1);
		// badge3=new BadgeView(getActivity(), title_tv2);
		// badge4=new BadgeView(getActivity(), title_tv3);

		// badge1.setBadgeMargin(1);
		// badge1.setText("1");
		// badge1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
		// badge1.setTextSize(10);
		// badge1.show();
		//
		// badge2.setBadgeMargin(1);
		// badge2.setText("1");
		// badge2.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
		// badge2.setTextSize(10);
		// badge2.setBackgroundResource(R.color.green1);
		// badge2.show();

	}

	/**
	 * 点击事件
	 * 
	 * @param v
	 */
	@OnClick({ R.id.ralay_head, R.id.address, R.id.title_tv2, R.id.title_tv3,
			R.id.back, R.id.iv_set })
	public void myClick(View v) {
		Intent intent = null;

		switch (v.getId()) {

		/** 跳转没有登录跳转到登录界面、登录了点击修改图片 */
		case R.id.back:
			mActivity.finish();
			break;
		case R.id.ralay_head:
			if (!UserInformation.isLogin()) {
				Intent intent2 = new Intent(getActivity(),
						WebLoginActivity.class);
				startActivity(intent2);
			}
			break;
		case R.id.address:
			Toast.makeText(getActivity(), "address", Toast.LENGTH_SHORT).show();
			break;
		case R.id.title_tv2:// 我的资产
			getMyMoney();
			break;
		case R.id.iv_set:// 我的资产
			intent = new Intent();
			intent.setClass(mActivity, SysSetting.class);
			getActivity().startActivity(intent);
			break;
		case R.id.title_tv3:// 资料设置
			intent = new Intent();
			intent.setClass(mActivity, GeRenZiLiao.class);
			startActivityForResult(intent, 4);
			break;
		default:
			break;
		}
	}

	/** 获取我的资产 */
	private void getMyMoney() {
		if (appContext.isNetworkConnected()) {
			// if (UserInformation.isLogin()) {
			Intent intent = new Intent();
			intent.setClass(mActivity, MineBalanceAty.class);
			mActivity.startActivity(intent);
			// } else {
			// toast("为了您的账户安全请先登录");
			// startActivity(new Intent(mActivity, WebLoginActivity.class));
			//
			// }
		} else {
			toast(R.string.t_Connection);
		}
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// Log.i("tag",isLogin+"========isLogin=======");
		getUserInfo();

	}

	private void getUserInfo() {
		String isLogin = getActivity().getSharedPreferences(
				"userInformation1", Context.MODE_PRIVATE).getString(
				"access_token", "");
		if (StringUtils.isEmpty(isLogin)) {
			username.setText("游客");
			usergrade.setText("游客");
			rl_userinformation.setVisibility(View.GONE);

			ivHeadbg.setVisibility(View.VISIBLE);
			iv_login.setVisibility(View.GONE);
			iv_login.setImageResource(R.drawable.bkg_head3);
			tv_login.setVisibility(View.VISIBLE);
		} else {
			setinformation();
		}
	}

	private void setinformation() {
		rl_userinformation.setVisibility(View.VISIBLE);

		String headString = mActivity.getString(R.string.test_image_server_url)
				+ mActivity.getSharedPreferences("userInformation1",
						Context.MODE_PRIVATE).getString("pictureUrl", "");
		

		iv_login.setVisibility(View.VISIBLE);
		ivHeadbg.setVisibility(View.GONE);
//		new BitmapUtils(mActivity, CommonUtils.createSDCardDir())
//				.configDefaultLoadFailedImage(R.drawable.bkg_head2)
//				.configDefaultLoadingImage(R.drawable.bkg_head2)
//				.display(iv_login, headString);
		MyFormat.setBitmap(getActivity(), iv_login, mActivity.getSharedPreferences("userInformation1",
				Context.MODE_PRIVATE).getString("pictureUrl", ""), 0, 0);
		
		tv_login.setVisibility(View.GONE);
		usergrade.setText(AppContext.userTypeStr);
		username.setText(AppContext.appellation);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (bitmap != null) {
			bitmap.recycle();
		}
		roundedAvatarDrawable = null;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public String getFragmentName() {
		return TAG;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// InformationItem item1=(InformationItem)
		// parent.getItemAtPosition(position);
		// Log.i("my", "item1.dsc"+item1.dsc);
		// TODO Auto-generated method stub
		if (!appContext.isNetworkConnected()) {
			toast(R.string.t_Connection);
			return;
		}
		Intent intent = new Intent();
		InformationItem item = list.get(position);
		if (!UserInformation.isLogin()) {
			toast("请登录");
			intent.setClass(mActivity, WebLoginActivity.class);
			intent.putExtra("isFinishTop", false);
			mActivity.startActivity(intent);
			return;
		}
		// Toast.makeText(getActivity(), "position"+position,
		// Toast.LENGTH_SHORT).show();
		if (item.name.equals("我的关注")) {

			intent.setClass(mActivity, GuanzhuActivity.class);
			mActivity.startActivity(intent);

			// Toast.makeText(getActivity(), "item.name"+item.name,
			// Toast.LENGTH_SHORT).show();
		} else if (item.name.equals("待处理订单")) {
			intent.setClass(mActivity, FinancialOrderAty.class);
			mActivity.startActivity(intent);
		}

		else if (item.name.equals("我的商品订单")) {
			// Toast.makeText(getActivity(), "item.name"+item.name,
			// Toast.LENGTH_SHORT).show();
			intent.setClass(mActivity, OrderStatusActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("position", 0);
			intent.putExtra("bundle", bundle);
			startActivity(intent);
		} else if (item.name.equals("我的任务")) {
			Toast.makeText(getActivity(), "正在开发中...", Toast.LENGTH_SHORT)
					.show();

		} else if (item.name.equals("安全中心")) {
			// Toast.makeText(getActivity(), "item.name"+item.name,
			// Toast.LENGTH_SHORT).show();
			// intent.setClass(mActivity, SecurityCenter.class);
			// intent.setClass(mActivity, BangDing.class);
			// intent.putExtra("title", "修改密码");
			// intent.putExtra("name", "原    密   码 :");
			// intent.putExtra("name1", "新    密   码 :");
			intent.setClass(mActivity, SecurityCenter.class);
			// startActivity(intent);
			mActivity.startActivity(intent);

		} else if (item.name.equals("我的发布")) {
			Toast.makeText(getActivity(), "正在开发中...", Toast.LENGTH_SHORT)
					.show();
		} else if (item.name.equals("现场消费订单")) {
			intent.setClass(mActivity, Consume.class);
			// Bundle bundle = new Bundle();
			// bundle.putInt("position", 0);
			// intent.putExtra("bundle", bundle);
			mActivity.startActivity(intent);

		} else if (item.name.equals("我的广告")) {
			intent.setClass(mActivity, MineAdvertisementAty.class);
			startActivity(intent);
		} else if (item.name.equals("我的会员")) {
			intent.setClass(mActivity, Huiyuandengji.class);
			mActivity.startActivity(intent);
		} else if (item.name.equals("摇一摇")) {
			intent.setClass(mActivity, ShakeScreen.class);
			mActivity.startActivity(intent);

		} else if (item.name.equals("反馈意见")) {
			// Toast.makeText(getActivity(), "item.name"+item.name,
			// Toast.LENGTH_SHORT).show();
			intent.setClass(mActivity, FeedBackActivity.class);
			mActivity.startActivity(intent);

		} else if (item.name.equals("联系我们")) {
			// Toast.makeText(getActivity(), "item.name"+item.name,
			// Toast.LENGTH_SHORT).show();
			intent.setClass(mActivity, ContactUsActivity.class);
			mActivity.startActivity(intent);
		}
	}

	private void getCusId() {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", UserInformation.getAccess_token());
		AppContext.getHtmlUitls().xUtilsm(mActivity, HttpMethod.POST,
				"m/security/getCurrCus", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						if (msg.equals("401")) {
							// SharedPreferences userPreferences = mActivity
							// .getSharedPreferences("userInformation1",
							// Context.MODE_PRIVATE);
							// Editor editor = userPreferences.edit();
							// AppContext.isLogin = false;
							// UserInformation.setLogin(false);
							// // editor.putString("userid", "");
							// editor.putString("userType", "");
							// editor.putString("appellation", "");
							// editor.commit();

						}
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub

						try {
							if (json.getBoolean("success")) {
								// Log.i("tag",
								// System.currentTimeMillis()+"==onBack==onBack====执行了onBack===onBack===onBack====");
								SharedPreferences userPreferences = mActivity
										.getSharedPreferences(
												"userInformation1",
												Context.MODE_PRIVATE);
								Editor editor = userPreferences.edit();
								String userid = "0";
								String userType = "0";
								String isShop = "N";
								String userTypeStr = "会员";
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
								if (json.getJSONObject("attributes").getString(
										"isShop") != null) {
									isShop = json.getJSONObject("attributes")
											.getString("isShop");
								}
								if (json.getJSONObject("attributes").getString(
										"userTypeStr") != null) {
									userTypeStr = json.getJSONObject(
											"attributes").getString(
											"userTypeStr");
								}
								editor.putString("userid", userid);
								editor.putString("userType", userType);
								editor.putString("isShop", isShop);
								editor.putString("userTypeStr", userTypeStr);
								editor.putString("appellation", appellation);
								editor.putString("pictureUrl", pictureUrl);
								editor.commit();
								AppContext.userid = userid;
								AppContext.userType = userType;
								AppContext.isShop = isShop;
								AppContext.userTypeStr = userTypeStr;
								AppContext.appellation = appellation;

								// usergrade.setText(AppContext.userTypeStr);
								// username.setText(AppContext.appellation);
								handler.sendEmptyMessage(3);
								// finish();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

}
