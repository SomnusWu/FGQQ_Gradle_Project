package com.llg.privateproject.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjg.lcc.privateproject.R;
import com.finddreams.sortedcontact.ClearEditText;
import com.finddreams.sortedcontact.PersonPinyinComarator;
import com.finddreams.sortedcontact.PersonSortAdapter;
import com.finddreams.sortedcontact.sortlist.CharacterParser;
import com.finddreams.sortedcontact.sortlist.PersonSortMode;
import com.finddreams.sortedcontact.sortlist.SideBar;
import com.finddreams.sortedcontact.sortlist.SideBar.OnTouchingLetterChangedListener;
import com.llg.help.GetProgressBar;
import com.llg.privateproject.actvity.PhoneDetailAty;
import com.llg.privateproject.utils.LogManag;
import com.llg.privateproject.utils.StringUtils;
import com.smartandroid.sa.aysnc.AsyncTask;

/**
 * 通讯录列表 yh 2015.01.03
 * */

public class FragmentPhonelist extends BaseFragment {
	private View mBaseView;
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private PersonSortAdapter adapter;
	private ClearEditText mClearEditText;
	private List<PersonSortMode> list;
	private String path;
	private CharacterParser characterParser;
	private List<PersonSortMode> SourceDateList;

	private PersonPinyinComarator pinyinComparator;
	private Context context;

	// /** 用来显示如果拒绝获取通讯录的权限 则显示的控件*/
	// private TextView oneone;

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		GetProgressBar.dismissMyProgressBar();
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.activity_main_contact, null);
		// oneone = (TextView) v.findViewById(R.id.oneone);
		sideBar = (SideBar) v.findViewById(R.id.sidrbar);
		dialog = (TextView) v.findViewById(R.id.dialog);
		sortListView = (ListView) v.findViewById(R.id.sortlist);
		mClearEditText = (ClearEditText) v.findViewById(R.id.filter_edit);
		// asyncQueryHandler = new MyAsyncQueryHandler(getActivity()
		// .getContentResolver());
		// init();
		initData();

		// printContacts();

		PrintContactsListTask listTask = new PrintContactsListTask();
		listTask.execute(1);
		return v;
	}

	@Override
	public String getFragmentName() {
		// TODO Auto-generated method stub
		return "FragmentPhonelist";
	}

	private void initData() {
		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PersonPinyinComarator();

		sideBar.setTextView(dialog);

		// 设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@SuppressLint("NewApi")
			@Override
			public void onTouchingLetterChanged(String s) {
				// 该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					sortListView.setSelection(position);
				}
			}
		});

		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 这里要利用adapter.getItem(position)来获取当前position所对应的对象
				// Toast.makeText(getApplication(),
				// ((SortModel)adapter.getItem(position)).getName(),
				// Toast.LENGTH_SHORT).show();
				// String name = list.get(position).getName();
				ArrayList<String> mlist = (ArrayList<String>) adapter.getItem(
						position).getPhoneList();
				String name = adapter.getItem(position).getName();
				String contactId = adapter.getItem(position).getContactId();
				int phoneCount = adapter.getItem(position).getPhoneCount();
				Intent intent = new Intent(getActivity(), PhoneDetailAty.class);
				intent.putExtra("name", name);
				intent.putExtra("id", contactId);
				intent.putExtra("phoneCount", phoneCount);
				Log.i("tag", phoneCount + "=传递的phoneCount");
				Log.i("tag", name + "传递的name");
				startActivity(intent);
			}
		});
	}

	private void initEdtListener() {
		List<String> constact = new ArrayList<String>();
		// for (Iterator<String> keys = callRecords.keySet().iterator();
		// keys
		// .hasNext();) {
		// String key = keys.next();
		// constact.add(key);
		// }
		// String[] names = new String[] {};
		// names = constact.toArray(names);
		SourceDateList = filledData(list);
		Log.i("tag", SourceDateList + "===============SourceDateList");

		// 根据a-z进行排序源数据
		Collections.sort(SourceDateList, pinyinComparator);
		adapter = new PersonSortAdapter(getActivity(), SourceDateList);
		sortListView.setAdapter(adapter);

		mClearEditText.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				mClearEditText.setGravity(Gravity.LEFT
						| Gravity.CENTER_VERTICAL);

			}
		});
		// 根据输入框输入值的改变来过滤搜索
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				filterData(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	/*
	 * 自定义显示Contacts提供的联系人的方法
	 */
	private static final String[] CONTACTOR_ION = new String[] {
			ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
			ContactsContract.Contacts.DISPLAY_NAME,
			ContactsContract.CommonDataKinds.Phone.NUMBER };

	public List<?> printContacts() {
		Cursor phoneCursor = null;
		try {
			list = new ArrayList<PersonSortMode>();
			// 生成ContentResolver对象
			ContentResolver contentResolver = getActivity()
					.getContentResolver();
			// 获得所有的联系人
			/*
			 * Cursor cursor = contentResolver.query(
			 * ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
			 */
			// 这段代码和上面代码是等价的，使用两种方式获得联系人的Uri
//			Cursor cursor = contentResolver.query(
//					Uri.parse("content://com.android.contacts/contacts"), null,
//					null, null, null);
			
//			Cursor cursor = contentResolver  
//		            .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI  
//		                    , CONTACTOR_ION, null, null, "sort_key"); 
			
			 Cursor cursor = contentResolver.query(Phone.CONTENT_URI,
					 null, null, null, null);
			// 循环遍历
			if (cursor != null && cursor.moveToFirst()) {
//				int idColumn = cursor
//						.getColumnIndex(ContactsContract.Contacts._ID);
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

					// 使用Toast技术显示获得的联系人信息
					// Toast.makeText(geta, "联系人姓名：" +
					// displayName,Toast.LENGTH_LONG).show();

					// 查看联系人有多少个号码，如果没有号码，返回0
					int phoneCount = cursor
							.getInt(cursor
									.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

					// phoneCursor = contentResolver.query(
					// ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					// null,
					// ContactsContract.CommonDataKinds.Phone.CONTACT_ID
					// + "=" + contactId, null, null);

					// if (phoneCount > 0 && phoneCursor != null
					// && phoneCursor.moveToFirst()) {
					// do {
					// // 遍历所有的联系人下面所有的电话号码
					// phoneNumber = phoneCursor
					// .getString(phoneCursor
					// .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					// } while (phoneCursor.moveToNext());
					// }
					Log.i("tag", displayName + "=displayName");
					// Log.i("tag", phoneCount + "=phoneCount");
					LogManag.d("通讯录  Number->", phoneNumber);
					contact.setNumber(phoneNumber);
					contact.setName(displayName);
					contact.setContactId(contactId);
					contact.setPhoneCount(phoneCount);
					list.add(contact);
				} while (cursor.moveToNext());

				if (cursor != null) {
					// oneone.setVisibility(View.VISIBLE);
					// sideBar.setVisibility(View.GONE);
					cursor.close();
				}
				if (list.size() > 0) {
					// initEdtListener();
					return list;
				} else {
					Toast.makeText(getActivity(), "获取通话记录失败!",
							Toast.LENGTH_LONG).show();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (phoneCursor != null) {
				phoneCursor.close();
			}
		}
		return list;
	}

	/**
	 * 为ListView填充数据
	 * 
	 * @param date
	 * @return
	 */
	private List<PersonSortMode> filledData(List<PersonSortMode> list) {
		List<PersonSortMode> mSortList = new ArrayList<PersonSortMode>();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				PersonSortMode sortModel = new PersonSortMode();
				sortModel.setName(list.get(i).getName());
				sortModel.setNumber(list.get(i).getNumber());
				sortModel.setContactId(list.get(i).getContactId());
				sortModel.setPhoneCount(list.get(i).getPhoneCount());
				// sortModel.setPhoneList(list.get(i).getPhoneList());
				// 汉字转换成拼音
				String pinyin = characterParser.getSelling(list.get(i)
						.getName());
				String sortString = pinyin.substring(0, 1).toUpperCase();

				// 正则表达式，判断首字母是否是英文字母
				if (sortString.matches("[A-Z]")) {
					sortModel.setSortLetters(sortString.toUpperCase());
				} else {
					sortModel.setSortLetters("#");
				}

				mSortList.add(sortModel);
			}
		}
		return mSortList;

	}

	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		List<PersonSortMode> filterDateList = new ArrayList<PersonSortMode>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = SourceDateList;
		} else {
			filterDateList.clear();
			for (PersonSortMode sortModel : SourceDateList) {

				if (StringUtils.isNumber(filterStr)) {
					String number = sortModel.getNumber();
					if (number.contains(filterStr)) {
						filterDateList.add(sortModel);
					}
				} else {
					String name = sortModel.getName();
					if (name.indexOf(filterStr.toString()) != -1
							|| characterParser.getSelling(name).startsWith(
									filterStr.toString())) {
						filterDateList.add(sortModel);
					}

				}

			}
		}

		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}

//	private void init() {
//		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; //
//		//
//		String[] projection = { ContactsContract.CommonDataKinds.Phone._ID,
//				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
//				ContactsContract.CommonDataKinds.Phone.DATA1, "sort_key",
//				ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
//				ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
//				ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY };
//		//
//		asyncQueryHandler.startQuery(0, null, uri, projection, null, null,
//				"sort_key COLLATE LOCALIZED asc");
//	}

	/**
	 * 
	 */

	public class PrintContactsListTask extends
			AsyncTask<Integer, Integer, List<?>> {

		public PrintContactsListTask() {

		}

		/**
		 * 这里的Integer参数对应AsyncTask中的第一个参数 这里的String返回值对应AsyncTask的第三个参数
		 * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
		 * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作
		 */
		@Override
		protected List<?> doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			return printContacts();
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
				initEdtListener();
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

}