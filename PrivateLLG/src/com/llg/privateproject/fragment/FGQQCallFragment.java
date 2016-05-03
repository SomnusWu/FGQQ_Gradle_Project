package com.llg.privateproject.fragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjg.lcc.privateproject.R;
import com.finddreams.sortedcontact.sortlist.PersonSortMode;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.actvity.PhoneDetailAty;
import com.llg.privateproject.actvity.WebLoginActivity;
import com.llg.privateproject.adapters.PhoneCallAdapter;
import com.llg.privateproject.adapters.PhoneSearchAdapter;
import com.llg.privateproject.entities.KeyBoradBean;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.utils.LogManag;
import com.llg.privateproject.utils.SharedPreferencesUtil;
import com.llg.privateproject.utils.StringUtils;
import com.llg.privateproject.utils.TelephonyManagerUtils;
import com.llg.privateproject.utils.VersionUtils;
import com.llg.privateproject.view.AutoScrollViewPager;
import com.llg.privateproject.view.FGQQInputKeyBoard;
import com.llg.privateproject.view.FGQQInputKeyBoard.iInputKey;
import com.llg.privateproject.view.KeyBoradBottomView.KeyBoradBottom;
import com.smartandroid.sa.aysnc.AsyncTask;
import com.smartandroid.sa.eventbus.EventBus;

public class FGQQCallFragment extends BaseFragment implements
		OnItemClickListener {

	/**
	 * 自动循环滚动
	 */
	private AutoScrollViewPager mPosterPager;
	private FrameLayout frame_layout_view_pager;
	private LinearLayout pointsLayout;
	private int index = 0;
	/**
	 * 广告图片
	 */
	private List<String> posterImage = null;

	/**
	 * 标记点集合
	 */
	private List<ImageView> points = null;
	/**
	 * 广告个数
	 */
	private int count = 0;
	/**
	 * 循环间隔
	 */
	private int interval = 3000;

	/**
	 * 键盘
	 */
	private FGQQInputKeyBoard mFGQQInputKeyBoard;
	private PosterPagerAdapter mPosterPagerAdapter;

	private TextView tv_title;
	private TextView tv_call;

	private GridView gridView;
	private TextView edtNumber;
	private ImageView ivClear;
	private ImageView ivCall;
	private List<String> list;
	private Context context;
	private TextView tvNet;

	/**
	 * 显示联系人的list
	 * 
	 * @param activity
	 */
	private ListView lv_phone_list;
	private PhoneSearchAdapter mPhoneSearchAdapter;
	private List<PersonSortMode> mPersonSortModeList;// 所有的联系人
	private List<PersonSortMode> mPersonSortList;// 匹配到的联系人
	private LinearLayout ll_layout_phone_top;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		context = activity;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (customProgressSmall.isShowing()) {
			customProgressSmall.dismiss();
		}
		if (mPosterPager != null) {
			mPosterPager.stopAutoScroll();
		}

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (receiver != null) {
			getActivity().unregisterReceiver(receiver);
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fgt_call_layout, null);
		mPosterPager = (AutoScrollViewPager) v.findViewById(R.id.asv_viewpager);
		pointsLayout = (LinearLayout) v.findViewById(R.id.points);

		
		ll_layout_phone_top = (LinearLayout) v
				.findViewById(R.id.ll_layout_phone_top);
		frame_layout_view_pager = (FrameLayout) v
				.findViewById(R.id.frame_layout_view_pager);
		mPersonSortModeList = new ArrayList<PersonSortMode>();
		mPersonSortList = new ArrayList<PersonSortMode>();
		lv_phone_list = (ListView) v.findViewById(R.id.lv_phone_list);
		mPhoneSearchAdapter = new PhoneSearchAdapter(getActivity(),
				mPersonSortList);
		lv_phone_list.setAdapter(mPhoneSearchAdapter);

		tv_title = (TextView) v.findViewById(R.id.tv_title);
		tv_call = (TextView) v.findViewById(R.id.tv_call);
		gridView = (GridView) v.findViewById(R.id.gridview);
		edtNumber = (TextView) v.findViewById(R.id.edt_number);
		tvNet = (TextView) v.findViewById(R.id.tv_net);
		ivClear = (ImageView) v.findViewById(R.id.iv_clear);
		ivCall = (ImageView) v.findViewById(R.id.iv_call);
		list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		list.add("7");
		list.add("8");
		list.add("9");
		list.add("*");
		list.add("0");
		list.add("#");
		PhoneCallAdapter adapter = new PhoneCallAdapter(getActivity(), list);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(this);
		ivClear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int lengh = edtNumber.getText().length();
				if (lengh == 0) {
					return;
				}
				String text = edtNumber.getText().toString()
						.substring(0, lengh - 1);
				edtNumber.setText(text);
			}
		});
		// ivCall.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// // if
		// //
		// (edtNumber.getText().toString().trim().charAt(0)!=0&&edtNumber.getText().toString().length()<11)
		// // {
		// if (edtNumber.getText().toString().length() < 11) {
		// toast("请输入正确的手机号");
		// return;
		// }
		// if (edtNumber.getText().toString().trim().charAt(0) == 0
		// || !TextUtils.isEmpty(edtNumber.getText().toString())) {
		// customProgressSmall.setMessage("正在拨号中");
		// call(UserInformation.getAccess_token());
		// }
		// }
		//
		// });
		registerReceiver();
		tvNet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
				startActivity(intent);
			}
		});
		/**
		 * 拨打电话
		 */
		tv_call.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				callEvent();

				// if (tv_title.getText().toString().length() < 11) {
				// toast("请输入正确的手机号");
				// return;
				// }
				// if (tv_title.getText().toString().trim().charAt(0) == 0
				// || !TextUtils.isEmpty(tv_title.getText().toString())) {
				// customProgressSmall.setMessage("正在拨号中");
				// call(UserInformation.getAccess_token());
				// }
			}
		});

		lv_phone_list.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				// TODO Auto-generated method stub
				// 如果开始滑动就将键盘隐藏掉
				mFGQQInputKeyBoard.setVisibility(View.GONE);
			}

			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}
		});

		lv_phone_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				String name = mPhoneSearchAdapter.getItem(position).getName();
				String contactId = mPhoneSearchAdapter.getItem(position)
						.getContactId();
				int phoneCount = mPhoneSearchAdapter.getItem(position)
						.getPhoneCount();
				Intent intent = new Intent(getActivity(), PhoneDetailAty.class);
				intent.putExtra("name", name);
				intent.putExtra("id", contactId);
				intent.putExtra("phoneCount", phoneCount);
				startActivity(intent);
			}
		});
		
		init();
		initKeyBoard(v);


		return v;
	}

	/** 获取通讯录中的所有联系人 **/
	private void executeTask() {
		PrintContactsListTask phoneTask = new PrintContactsListTask();
		phoneTask.execute(1);
	}

	private void callEvent() {

		String phone = tv_title.getText().toString().trim();
		if (phone.equals(getResources().getString(R.string.app_name))) {
			toast("请输入正确的手机号或者区号加座机号！");
			return;
		}
		if (phone.length() != 0) {
			// 手机号
			boolean isMobile = phone.length() == 11 && phone.startsWith("1");
			boolean isLandline = (phone.length() > 10 && phone.length() < 13)
					&& phone.startsWith("0");
			if (isMobile || isLandline) {
				customProgressSmall.setMessage("正在拨号中");
				call(UserInformation.getAccess_token());
			} else {
				toast("请输入正确的手机号或者区号加座机号！");
				return;
			}

			// if (phone.length() < 11) {
			// if (phone.startsWith("1")) {
			// toast("请输入正确的手机号或者区号加座机号！");
			// }else{
			// toast("请输入正确的手机号或者区号加座机号！");
			// }
			// } else if (phone.startsWith("0")
			// && (phone.length() < 10 || phone.length() > 13)) {
			// toast("请输入正确的手机号或者区号加座机号！");
			// return;
			// } else {
			// customProgressSmall.setMessage("正在拨号中");
			// call(UserInformation.getAccess_token());
			// }
		}
	}

	@SuppressWarnings("deprecation")
	private void init() {
		points = new LinkedList<ImageView>();
		posterImage = new LinkedList<String>();
		getAdList("APP_START_PAGE");
		EventBus.getDefault().register(this, "onEventKeyBorad");
		/** 得到所有联系人 */
		
		executeTask();
	}

	/**
	 * 处理键盘
	 * 
	 * @param method
	 */
	public void onEventKeyBorad(KeyBoradBottom method) {
		switch (method) {
		case KeyBoradSwitch:
			LogManag.d("键盘", "处理键盘的关闭" + method);
			if (mFGQQInputKeyBoard.getVisibility() == View.VISIBLE) {
				mFGQQInputKeyBoard.setVisibility(View.GONE);
			} else {
				mFGQQInputKeyBoard.setVisibility(View.VISIBLE);
			}
			break;
		case KeyBoradCall:
			// 拨打电话
			callEvent();
			break;
		case KeyBoradDelete:
			mFGQQInputKeyBoard.setVisibility(View.VISIBLE);
			// 删除按钮
			int lengh = tv_title.getText().length();
			if (lengh == 1) {
				tv_title.setText(getResources().getString(R.string.app_name));
				number = "";
				frame_layout_view_pager.setVisibility(View.VISIBLE);
				lv_phone_list.setVisibility(View.GONE);
				// EventBus.getDefault().post(new KeyBoradBean(number));
				setKeyBoradHeight(number);
				return;
			}
			String text = tv_title.getText().toString().substring(0, lengh - 1);
			number = text;
			tv_title.setText(text);
			setKeyBoradHeight(number);
			// getPersonList(number);
			// executeTask(number);
			matchingPerson(number);
			// EventBus.getDefault().post(new KeyBoradBean(number));
			break;

		default:
			break;
		}
		// TODO Auto-generated method stub

	}

	private void initPoints() {
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(8, 15, 8, 15);
		for (int i = 0; i < count; i++) {
			// 添加标记点
			ImageView point = new ImageView(context);

			if (i == index % count) {
				point.setBackgroundResource(R.drawable.feature_point_cur);
			} else {
				point.setBackgroundResource(R.drawable.feature_point);
			}
			point.setLayoutParams(lp);

			points.add(point);
			pointsLayout.addView(point);
		}
	}

	private void initPoster() {
		// 设置 ViewPager的高度
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				getScreen(getActivity()).widthPixels / 2);
		mPosterPager.setLayoutParams(params);

		mPosterPagerAdapter = new PosterPagerAdapter();
		mPosterPager.setAdapter(mPosterPagerAdapter);
		mPosterPager.setCurrentItem(count * 500);
		mPosterPager.setInterval(interval);
		mPosterPager.setOnPageChangeListener(new PosterPageChange());
		mPosterPager
				.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_CYCLE);
		mPosterPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mPosterPager.stopAutoScroll();
					break;
				case MotionEvent.ACTION_MOVE:
					mPosterPager.startAutoScroll();
					break;
				case MotionEvent.ACTION_UP:
					mPosterPager.startAutoScroll();
					break;
				default:
					break;
				}
				return false;
			}

		});
	}

	class PosterPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
			// return count;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			ImageView imageView = new ImageView(context);
			imageView.setAdjustViewBounds(true);
			// TODO 调整图片大小
			imageView.setScaleType(ScaleType.CENTER_CROP);
			// imageView.setScaleType(ScaleType.FIT_XY);

			android.view.ViewGroup.LayoutParams params = new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			imageView.setLayoutParams(params);

			// bitmapUtils.display(imageView, posterImage.get(position %
			// count));
			MyFormat.setBitmap(context, imageView,
					posterImage.get(position % count),
					AppContext.getScreenWidth(), LayoutParams.WRAP_CONTENT);

			container.addView(imageView);
			imageView.setOnClickListener(new PosterClickListener(position
					% count));
			return imageView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

			container.removeView((ImageView) object);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

	class PosterPageChange implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {
			index = position;
			for (int i = 0; i < count; i++) {
				points.get(i).setBackgroundResource(R.drawable.feature_point);
			}
			points.get(position % count).setBackgroundResource(
					R.drawable.feature_point_cur);
		}

	}

	class PosterClickListener implements OnClickListener {

		private int position;

		public PosterClickListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			// mPosterPager.stopAutoScroll();
			// Toast.makeText(context, "position---->" + position, 0).show();
		}

	}

	String number = "";

	private void initKeyBoard(View view) {
		mFGQQInputKeyBoard = (FGQQInputKeyBoard) view
				.findViewById(R.id.view_keyboard);
		mFGQQInputKeyBoard.setmInputKey(new iInputKey() {

			@Override
			public void inputKey(String keyNumber) {
				// TODO Auto-generated method stub
				// toast(keyNumber);

				if (keyNumber.equals("#")) {
					if (tv_title.getText().equals(
							getResources().getString(R.string.app_name))) {
						return;
					}
					int lengh = tv_title.getText().length();
					if (lengh == 1) {
						tv_title.setText(getResources().getString(
								R.string.app_name));
						number = "";
						setKeyBoradHeight(number);
						return;
					}
					String text = tv_title.getText().toString()
							.substring(0, lengh - 1);
					number = text;
					tv_title.setText(text);
					setKeyBoradHeight(number);
					return;
				} else {
					number += keyNumber;
					tv_title.setText(number);
					setKeyBoradHeight(number);
					// 执行搜索本地联系人
					// getPersonList(number);
					// executeTask(number);
					matchingPerson(number);
				}

			}
		});
	}

	/** 匹配本地联系人 **/
	private void matchingPerson(String mNumber) {

		mPersonSortList.clear();
		mPhoneSearchAdapter.notifyDataSetChanged();

		if (mPersonSortModeList.size() > 0) {
			for (PersonSortMode sortModel : mPersonSortModeList) {
				if (StringUtils.isNumber(mNumber)) {
					String _number = sortModel.getNumber();
					if (_number.contains(mNumber)) {
						mPersonSortList.add(sortModel);
					}
				}
			}
			if (mPersonSortList.size() > 0) {
				// 将ViewPager隐藏 listView显示
				frame_layout_view_pager.setVisibility(View.GONE);
				lv_phone_list.setVisibility(View.VISIBLE);
				// 如果用户使用键盘删除了 则需要将ViewPager显示出来 listView隐藏掉
			}
			mPhoneSearchAdapter.notifyDataSetChanged();
		}
	}

	/** 处理键盘高度 */
	private void setKeyBoradHeight(String content) {
		EventBus.getDefault().post(new KeyBoradBean(content));

		android.widget.LinearLayout.LayoutParams paramsWight_1 = new android.widget.LinearLayout.LayoutParams(
				android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

		// 当没有数据的时候就按照原本的高度来展示
		if (StringUtils.isEmpty(content)) {
			mFGQQInputKeyBoard.setLayoutParams(paramsWight_1);
			// 顶部的高度
			android.widget.LinearLayout.LayoutParams topParams = new android.widget.LinearLayout.LayoutParams(
					android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
					getScreen(getActivity()).widthPixels / 2);
			ll_layout_phone_top.setLayoutParams(topParams);

		} else {
			// 当有数据的时候就设置高度
			int keyHeight = getScreen(getActivity()).heightPixels / 2;
			android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(
					android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
					keyHeight);
			mFGQQInputKeyBoard.setLayoutParams(params);
			ll_layout_phone_top.setLayoutParams(paramsWight_1);
		}
	}

	/**
	 * 匹配本地通讯录中的联系人信息
	 * 
	 * @param number
	 */
	private List<?> getPersonList() {
		try {

			// 生成ContentResolver对象
			ContentResolver contentResolver = getActivity()
					.getContentResolver();
			// 获得所有的联系人
			/*
			 * Cursor cursor = contentResolver.query(
			 * ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
			 */
			// 这段代码和上面代码是等价的，使用两种方式获得联系人的Uri
			// Cursor cursor = contentResolver.query(
			// Uri.parse("content://com.android.contacts/contacts"), null,
			// null, null, null);

			Cursor cursor = contentResolver.query(Phone.CONTENT_URI, null,
					null, null, null);

			// 循环遍历
			if (cursor.moveToFirst()) {
				// int idColumn = cursor
				// .getColumnIndex(ContactsContract.Contacts._ID);

				int idColumn = cursor
						.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID);
				int displayNameColumn = cursor
						.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
				int phoneIndex = cursor
						.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

				// String ContactId = cursor.getString(cursor
				// .getColumnIndex(ContactsContract.Contacts._ID));

				do {

					// 获得联系人的ID
					String contactId = cursor.getString(idColumn);
					// 获得联系人姓名
					String displayName = cursor.getString(displayNameColumn);
					// 电话号码
					String phoneNumber = cursor.getString(phoneIndex);

					PersonSortMode contact = new PersonSortMode();
					// 查看联系人有多少个号码，如果没有号码，返回0
					int phoneCount = cursor
							.getInt(cursor
									.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

					// Cursor phoneCursor = contentResolver.query(
					// ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					// null,
					// ContactsContract.CommonDataKinds.Phone.CONTACT_ID
					// + "=" + contactId, null, null);

					// if (phoneCount > 0 && phoneCursor.moveToFirst()) {
					// do {
					// // 遍历所有的联系人下面所有的电话号码
					// phoneNumber = phoneCursor
					// .getString(phoneCursor
					// .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					// } while (phoneCursor.moveToNext());
					// }
					// Log.i("tag", displayName + "=displayName");
//					Log.i("tag", phoneCount + "=phoneCount");
//					LogManag.d("通讯录  Number->", phoneNumber);
					contact.setNumber(phoneNumber);
					contact.setName(displayName);
					contact.setContactId(contactId);
					contact.setPhoneCount(phoneCount);
					mPersonSortModeList.add(contact);
				} while (cursor.moveToNext());

				if (cursor != null) {
					cursor.close();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return mPersonSortModeList;

	}

	/** 请求顶部广告 */
	private void getAdList(String locationCode) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("locationCode", locationCode);
		params.addQueryStringParameter("lng",
				String.valueOf(AppContext.myLongitude));
		params.addQueryStringParameter("lat",
				String.valueOf(AppContext.myLatitude));
		params.addQueryStringParameter("cusId",
				String.valueOf(AppContext.userid));
		params.addQueryStringParameter("app_id",
				TelephonyManagerUtils.getDeviceId(getActivity()));
		params.addQueryStringParameter("app_version",
				VersionUtils.getVersionName(getActivity()));
		HttpUtils client = new HttpUtils(1000 * 5);
		/** 设置超时时间 */
		client.configSoTimeout(1000 * 5);
		// client.configCurrentHttpCacheExpiry(currRequestExpiry)
		client.configResponseTextCharset("UTF-8");
		client.send(HttpMethod.GET, AppContext.getHtmlUitls().getDataHttp()
				+ "ad/getAdList", params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				try {
					JSONObject json = new JSONObject(arg0.result);
					if (json.getBoolean("success")
							&& json.getJSONArray("obj") != null) {
						JSONArray obj = json.getJSONArray("obj");
						if (obj.length() > 0) {
							JSONObject object = null;
							for (int i = 0; i < obj.length(); i++) {
								object = obj.getJSONObject(i);
								String url = object.getString("img");
								posterImage.add(url);
							}
							count = posterImage.size();
							initPoints();
							initPoster();
							mPosterPagerAdapter.notifyDataSetChanged();

						} else {
							return;
						}

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		mPosterPager.startAutoScroll();
	}

	@Override
	protected void doAction1() {
		// TODO Auto-generated method stub
		super.doAction1();
		tvNet.setVisibility(View.VISIBLE);
	}

	@Override
	protected void doAction2() {
		// TODO Auto-generated method stub
		super.doAction1();
		tvNet.setVisibility(View.GONE);
	}

	/** 打电话 */
	private void call(String access_token) {
		if (!appContext.isNetworkConnected()) {
			toast("网络未连接");
			return;
		}
		customProgressSmall.show();

		// 记录拨打电话的时间 ， 在20s内 就自动接听该电话
		SharedPreferencesUtil.setParam(getActivity(), "Phone", tv_title
				.getText().toString().trim());

		SharedPreferencesUtil.setParam(getActivity(), "Time",
				System.currentTimeMillis());

		// xUtils(getActivity(), HttpMethod.POST, "tel/call", null, new
		// HttpCallback() {
		//
		// @Override
		// public void onError(String msg) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void onBack(JSONObject json) {
		// // TODO Auto-generated method stub
		//
		// }
		// });
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", access_token);
		params.addQueryStringParameter("tel",
				getSharePrefence().getString("phone", "null"));
		params.addQueryStringParameter("callerDisplayNumber", tv_title
				.getText().toString().trim());
		AppContext.getHtmlUitls().xUtils(getActivity(), HttpMethod.POST,
				"tel/call", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						customProgressSmall.dismiss();
						if (msg.equals("401")) {
							startActivity(new Intent(getActivity(),
									WebLoginActivity.class));
						} else {
							toast(msg);
						}
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						Log.i("tag", json.toString());
						try {
							if (json.getBoolean("success")) {
								if (json.getJSONObject("obj")
										.getString("retCode").equals("0")) {
									customProgressSmall.dismiss();
									customProgressSmall.setMessage("正在拨号中...");
									customProgressSmall.show();
									// GetProgressBar.getProgressBar(context,
									// 1);
								} else {
									customProgressSmall.dismiss();
									toast(json.optString("msg"));
								}

							} else {
								if (json.get("errorCode").equals("NOT_LOGIN")) {
									// Log.i("tag", json.toString()
									// + "------监听上一句--------");
									setRefreshListtener(new Refresh() {
										@Override
										public void refreshRequst(
												String access_token) {
											// TODO Auto-generated method
											// stub
											Log.i("tag", "------监听里-------");
											call(access_token);
										}
									});
									RefeshToken();
								} else {
									customProgressSmall.dismiss();
									toast("拨打失败");
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	@Override
	public String getFragmentName() {
		// TODO Auto-generated method stub
		return "FragmentPhoneCall";
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		String text = edtNumber.getText().toString();
		edtNumber.setText(text + list.get(arg2));
	}

	public class PrintContactsListTask extends
			AsyncTask<Integer, Integer, List<?>> {
		// private String number = "";

		public PrintContactsListTask() {
			// this.number = number;
		}

		/**
		 * 这里的Integer参数对应AsyncTask中的第一个参数 这里的String返回值对应AsyncTask的第三个参数
		 * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
		 * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作
		 */
		@Override
		protected List<?> doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			publishProgress(0);
			List<?> list = getPersonList();
			return list;
		}

		/**
		 * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
		 * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
		 */
		@Override
		protected void onPostExecute(List<?> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// if (result.size() > 0) {

			// try {
			// if (mPersonSortModeList.size() > 0) {
			// // initEdtListener();
			// for (PersonSortMode sortModel : mPersonSortModeList) {
			// if (StringUtils.isNumber(number)) {
			// String _number = sortModel.getNumber();
			// if (_number.contains(number)) {
			// mPersonSortList.add(sortModel);
			// }
			// }
			// }
			// if (mPersonSortList.size() > 0) {
			// // 将ViewPager隐藏 listView显示
			// frame_layout_view_pager.setVisibility(View.GONE);
			// lv_phone_list.setVisibility(View.VISIBLE);
			// // 如果用户使用键盘删除了 则需要将ViewPager显示出来 listView隐藏掉
			// }
			// mPhoneSearchAdapter.notifyDataSetChanged();
			// }
			// } catch (Exception e) {
			// // TODO: handle exception
			// e.printStackTrace();
			// }

			// if (mPersonSortModeList.size() == 0) {
			// Toast.makeText(getActivity(), "请检测是否开启系统联系人权限",
			// Toast.LENGTH_LONG).show();
			// }
			// }
		}

		// 该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		/**
		 * 这里的Intege参数对应AsyncTask中的第二个参数
		 * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行
		 * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作
		 */
		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			mPersonSortModeList.clear();
			mPersonSortList.clear();
			mPhoneSearchAdapter.notifyDataSetChanged();
		}

	}

}
