package com.llg.privateproject.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.actvity.WebLoginActivity;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.FragmentPhonelist.PrintContactsListTask;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.swiplistview.SwipeMenu;
import com.llg.privateproject.swiplistview.SwipeMenuCreator;
import com.llg.privateproject.swiplistview.SwipeMenuItem;
import com.llg.privateproject.swiplistview.SwipeMenuListView;
import com.llg.privateproject.swiplistview.SwipeMenuListView.OnMenuItemClickListener;
import com.llg.privateproject.utils.SharedPreferencesUtil;
import com.smartandroid.sa.aysnc.AsyncTask;

/**
 * 最近通话列表 ljm
 */
public class FragmentPhoneRecent extends BaseFragment implements
		SwipeMenuCreator, OnMenuItemClickListener {
	private SwipeMenuItem itemDelete;
	private SwipeMenuListView listview;
	List<Map<String, String>> list;
	private Myadapter myadapter;
	private Context context;
	private TextView tvNet;

	// /** 控制如果没有允许手机通话记录,拒绝后显示的控件*/
	// private TextView text;

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
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fgt_recent, null);
		listview = (SwipeMenuListView) v.findViewById(R.id.listview);
		// listview.setEnabled(false);
		tvNet = (TextView) v.findViewById(R.id.tv_net);
		listview.setMenuCreator(FragmentPhoneRecent.this);

		myadapter = new Myadapter();
		listview.setAdapter(myadapter);
		getPhoneList();
		listview.setOnMenuItemClickListener(FragmentPhoneRecent.this);
		registerReceiver();
		tvNet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
				startActivity(intent);
			}
		});
		return v;

	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			listview.setMenuCreator(FragmentPhoneRecent.this);
			Myadapter myadapter = new Myadapter();
			listview.setAdapter(myadapter);
			listview.setOnMenuItemClickListener(FragmentPhoneRecent.this);
		}
	};

	@Override
	public String getFragmentName() {
		// TODO Auto-generated method stub
		return "FragmentPhoneRecent";
	}

	/**
	 * 获取通讯录记录
	 */
	private List<?> getList() {

		try {
			list = new ArrayList<Map<String, String>>();
			Map<String, String> map;
			// Cursor cursor =
			// getActivity().getContentResolver().query(Phone.CONTENT_URI,
			// null, null, null, "date desc limit 50");
			
			Uri uri = android.provider.CallLog.Calls.CONTENT_URI;
			Cursor cursor = (getActivity().getContentResolver()).query(uri,
					null, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);

			if (cursor != null && cursor.moveToFirst()) {

				do {
					// CallLog calls = new CallLog();
					// // 号码
					String number = cursor.getString(cursor
							.getColumnIndex(Calls.NUMBER));

					// 从本手机中读取数据,分辨呼叫类型,从而用不同的符号显示出来
					String type;
					switch (Integer.parseInt(cursor.getString(cursor
							.getColumnIndex(Calls.TYPE)))) {
					case Calls.INCOMING_TYPE:
						type = "呼入";
						break;
					case Calls.OUTGOING_TYPE:
						type = "呼出";
						break;
					case Calls.MISSED_TYPE:
						type = "未接";
						break;
					default:
						type = "挂断";// 应该是挂断.根据我手机类型判断出的
						break;
					}
					SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");
					Date date = new Date(Long.parseLong(cursor.getString(cursor
							.getColumnIndexOrThrow(Calls.DATE))));
					String time = sfd.format(date);

					// String name = cursor.getString(cursor
					// .getColumnIndexOrThrow(Calls.CACHED_NAME));

					// String name = cursor.getString(cursor
					// .getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME));
					String name = cursor.getString(cursor
							.getColumnIndex(CallLog.Calls.CACHED_NAME));

					String id = cursor.getString(cursor
							.getColumnIndexOrThrow(Calls._ID));
					// 通话时间,单位:s
					String duration = cursor.getString(cursor
							.getColumnIndexOrThrow(Calls.DURATION));
					map = new HashMap<String, String>();
					map.put("time", time);
					map.put("name", name);
					map.put("duration", duration);
					map.put("number", number);
					map.put("type", type);
					map.put("id", id + "");
					list.add(map);
				} while (cursor.moveToNext());
			}
			if (list.size() == 0) {
				// Toast.makeText(getActivity(), "请检测是否开启通话记录权限",
				// Toast.LENGTH_LONG).show();
			}
			Log.i("tag", list.size() + "list大小");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 用map遍历通话记录数据
	 * 
	 * @return
	 */
	public void getPhoneList() {

		PrintContactsListTask listTask = new PrintContactsListTask();
		listTask.execute(1);

	}

	public class PrintContactsListTask extends
			AsyncTask<Integer, Integer, List<?>> {

		public PrintContactsListTask() {

		}

		/**
		 * 这里的Integer参数对应AsyncTask中的第一个参数 这里的String返回值对应AsyncTask的第三个参数
		 * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
		 * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作
		 * 
		 * @return
		 */
		@Override
		protected List<?> doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			return getList();
		}

		/**
		 * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
		 * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
		 */
		@Override
		protected void onPostExecute(List<?> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result.size() > 0) {
				// initEdtListener();
				myadapter.notifyDataSetChanged();
			}
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
		}

	}

	@Override
	public void onMenuItemClick(int position, SwipeMenu menu, int index) {
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			Log.i("tag", list.get(position).get("id") + "===id==");
			ContentResolver resolver = getActivity().getContentResolver();
			resolver.delete(CallLog.Calls.CONTENT_URI, "_id=?",
					new String[] { list.get(position).get("id") });
			list.remove(position);
			myadapter.notifyDataSetChanged();
			break;
		default:
			break;
		}

	}

	@Override
	public void create(SwipeMenu menu) {
		// TODO Auto-generated method stub
		int width = AppContext.getScreenWidth() / 5;
		itemDelete = new SwipeMenuItem(getActivity());
		itemDelete.setBackground(R.color.red);
		itemDelete.setWidth(width);
		itemDelete.setTitle("删除");
		itemDelete.setTitleColor(Color.WHITE);
		itemDelete.setTitleSize(18);
		menu.addMenuItem(itemDelete);
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
		super.doAction2();
		tvNet.setVisibility(View.GONE);
	}

	private class Myadapter extends BaseAdapter {
		private ViewHolder viewholder;

		@Override
		public int getCount() {
			return (list == null) ? 0 : list.size();
		}

		@Override
		public Map<String, String> getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				viewholder = new ViewHolder();
				convertView = LayoutInflater.from(getActivity()).inflate(
						R.layout.listitem_recent, null);
				ViewUtils.inject(viewholder, convertView);
				convertView.setTag(viewholder);
			} else {
				viewholder = (ViewHolder) convertView.getTag();
			}
			if (list.size() > 0) {
				final Map<String, String> map = list.get(position);
				Log.i("tag", (viewholder == null) + "viewholder");
				viewholder.tvPhone.setText(map.get("number"));
				Log.i("tag", map.get("name") + "==map==name");

				viewholder.tvName.setText(map.get("name"));

				String type = map.get("type");
				viewholder.tvPhone.setTextColor(getResources().getColor(
						R.color.black1));
				if (type.equals("呼入")) {
					viewholder.ivJiantou
							.setImageResource(R.drawable.dianhuabh_dajin);
				} else if (type.equals("呼出")) {
					viewholder.ivJiantou
							.setImageResource(R.drawable.dianhuabh_dachu);
				} else if (type.equals("未接")) {
					viewholder.ivJiantou
							.setImageResource(R.drawable.dianhuabh_weijie);
					viewholder.tvPhone.setTextColor(getResources().getColor(
							R.color.home_t));
				}
				viewholder.tvTime.setText(map.get("time"));
				int duration = 0;
				try {
					duration = Integer.parseInt(map.get("duration"));
				} catch (Exception e) {
					// TODO: handle exception
				}
				if (type.equals("呼入") || type.equals("呼出")) {
					if (duration / 60 >= 1 && duration / 60 < 60) {
						int fen = duration / 60;
						int miao = duration % 60;
						viewholder.tvDuration.setText(fen + "分" + miao + "秒");
					} else if (duration / 3600 >= 1) {
						int hour = duration / 3600;
						int m = duration % 3600;
						int fen = m / 60;
						if (fen >= 1) {
							int miao = m % 60;
							viewholder.tvDuration.setText(type + hour + "小时" + fen
									+ "分" + m + "秒");
						} else {
							viewholder.tvDuration.setText(type + hour + "小时" + m
									+ "秒");
						}
					} else {
						viewholder.tvDuration.setText(type + duration + "秒");
					}
				} else if (type.equals("未接")) {
					viewholder.tvDuration.setText("未接来电");
				}
				viewholder.ivCall.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						// toast("电话已拨出");
						call(map.get("number"), UserInformation.getAccess_token());
					}
				});
			}
			
			return convertView;
		}

		private class ViewHolder {
			@ViewInject(R.id.tv_name)
			private TextView tvName;
			@ViewInject(R.id.tv_time)
			private TextView tvTime;
			@ViewInject(R.id.iv_jiantou)
			private ImageView ivJiantou;
			@ViewInject(R.id.iv_call)
			private ImageView ivCall;
			@ViewInject(R.id.tv_duration)
			private TextView tvDuration;
			@ViewInject(R.id.tv_phone)
			private TextView tvPhone;
		}
	}

	/** 打电话 */
	private void call(final String number, String access_token) {
		if (!appContext.isNetworkConnected()) {
			toast("网络未连接");
			return;
		}

		// 记录拨打电话的时间 ， 在20s内 就自动接听该电话
		SharedPreferencesUtil.setParam(getActivity(), "Phone", number);
		SharedPreferencesUtil.setParam(getActivity(), "Time",
				System.currentTimeMillis());

		customProgressSmall.setMessage("正在拨号中");
		customProgressSmall.show();
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", access_token);
		params.addQueryStringParameter("tel",
				getSharePrefence().getString("phone", "null"));
		params.addQueryStringParameter("callerDisplayNumber", number);
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
							toast("拨打失败");
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
									// toast("电话已拨出2");
								} else {
									customProgressSmall.dismiss();
									toast("余额不足");
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
											call(number, access_token);
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
}