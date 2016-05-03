package com.finddreams.sortedcontact;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjg.lcc.jsonparser.ParseJson;
import com.bjg.lcc.privateproject.R;
import com.finddreams.sortedcontact.sortlist.CharacterParser;
import com.finddreams.sortedcontact.sortlist.SideBar;
import com.finddreams.sortedcontact.sortlist.SortModel;
import com.finddreams.sortedcontact.sortlist.SideBar.OnTouchingLetterChangedListener;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.actvity.MainActivity;
import com.llg.privateproject.entities.Citys;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;

/**
 * @Description:联系人显示界面
 * @author http://blog.csdn.net/finddreams
 */
public class CityListActivity extends Activity {

	private View mBaseView;
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;
	private List<Citys> list;
	private String path;
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;

	private PinyinComparator pinyinComparator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_contact);
		// path = Environment.getExternalStorageDirectory()
		// .getPath() + File.separator + "Citys";
		// DbUtils dbUtils = DbUtils.create(
		// CityListActivity.this, path, "city");
		// try {
		// Log.i("tag", dbUtils.findAll(Citys.class).size()
		// + "-------------查询到的数据库list大小");
		// } catch (DbException e) {
		// e.printStackTrace();
		// }
		// requestKind();
		initView();
		initData();
	}

	private void initView() {
		sideBar = (SideBar) this.findViewById(R.id.sidrbar);
		dialog = (TextView) this.findViewById(R.id.dialog);
		sortListView = (ListView) this.findViewById(R.id.sortlist);
	}

	private void requestKind() {
		Log.i("tag", "----------执行了requestRegion()------");
		AppContext.getHtmlUitls().xUtils(this, HttpMethod.GET, "getCities",
				null, new HttpCallback() {
					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						Log.i("tag", msg
								+ "-------getCities--getCities-msg------");
						// handler.sendEmptyMessage(2);
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						Map<String, Object> map = ParseJson.getParseJson()
								.parseIsSuccess(json);
						Log.i("tag", "----------成功了------");
						Log.i("tag", map.get("isSuccess")
								+ "---发送个人请求判断isSuccess------onBack------");
						if ((Boolean) map.get("isSuccess")) {
							List<Citys> list = ParseJson.getParseJson()
									.parseCitys(json);
							Log.i("tag", list.size() + "----------List大小------");
						}
					}
				});
	}

	private void initData() {
		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();

		pinyinComparator = new PinyinComparator();

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
				Log.i("tag", position
						+ "=========setOnItemClickListener===position");
				String baidu_code = adapter.getItem(position).getBaidu_code();
				String code = adapter.getItem(position).getCode();
				Intent intent = new Intent();
				intent.putExtra("baidu_code", baidu_code);
				intent.putExtra("code", code);
				intent.putExtra("name", adapter.getItem(position).getName());
				UserInformation.baidu_code = baidu_code;
				UserInformation.code = code;
				Log.i("tag", code + "=====setOnItemClickListener======code");
				Log.i("tag", baidu_code
						+ "=====setOnItemClickListener======baidu_code");
				setResult(RESULT_OK, intent);
				finish();
			}
		});

		new ConstactAsyncTask().execute(0);
	}

	private class ConstactAsyncTask extends
			AsyncTask<Integer, Integer, Integer> {

		@Override
		protected Integer doInBackground(Integer... arg0) {
			int result = -1;
			list = ConstactUtil.getAllCallRecords(CityListActivity.this);
			if (list != null && list.size() == 363) {
				path = Environment.getExternalStorageDirectory().getPath()
						+ File.separator + "Citys";
				DbUtils dbUtils = DbUtils.create(CityListActivity.this, path,
						"city");
				Citys citys = new Citys();
				citys.setName("北京市");
				citys.setBaidu_code("131");
				citys.setCode("1101");

				Citys citys2 = new Citys();
				citys2.setName("重庆市");
				citys2.setBaidu_code("132");
				citys2.setCode("5001");

				Citys citys3 = new Citys();
				citys3.setName("上海市");
				citys3.setBaidu_code("289");
				citys3.setCode("3101");

				Citys citys4 = new Citys();
				citys4.setName("天津市");
				citys4.setBaidu_code("332");
				citys4.setCode("1201");
				list.add(citys4);
				list.add(citys3);
				list.add(citys2);
				list.add(citys);
				try {
					dbUtils.saveBindingId(citys);
					dbUtils.saveBindingId(citys2);
					dbUtils.saveBindingId(citys3);
					dbUtils.saveBindingId(citys4);
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Log.i("tag", ((list == null) ? 0 : list.size())
					+ "------list.size---");
			// list.add(citys);
			// list.add(citys2);
			// list.add(citys3);
			// list.add(citys4);
			result = 1;
			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (result == 1) {
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

				// 根据a-z进行排序源数据
				Collections.sort(SourceDateList, pinyinComparator);
				adapter = new SortAdapter(CityListActivity.this, SourceDateList);
				sortListView.setAdapter(adapter);

				mClearEditText = (ClearEditText) CityListActivity.this
						.findViewById(R.id.filter_edit);
				mClearEditText
						.setOnFocusChangeListener(new OnFocusChangeListener() {

							@Override
							public void onFocusChange(View arg0, boolean arg1) {
								mClearEditText.setGravity(Gravity.LEFT
										| Gravity.CENTER_VERTICAL);

							}
						});
				// 根据输入框输入值的改变来过滤搜索
				mClearEditText.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {
						// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
						filterData(s.toString());
					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {
					}
				});
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}

	/**
	 * 为ListView填充数据
	 * 
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(List<Citys> list) {
		List<SortModel> mSortList = new ArrayList<SortModel>();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				SortModel sortModel = new SortModel();
				sortModel.setName(list.get(i).getName());
				sortModel.setBaidu_code(list.get(i).getBaidu_code());
				sortModel.setCode(list.get(i).getCode());
				// 汉字转换成拼音
				String pinyin = characterParser.getSelling(list.get(i)
						.getName());
				String sortString = pinyin.substring(0, 1).toUpperCase();
				String firstCase = list.get(i).getName().substring(0, 1);
				
				// 正则表达式，判断首字母是否是英文字母
				if (sortString.matches("[A-Z]")) {
					if (TextUtils.equals(firstCase, "重")) {
						sortModel.setSortLetters("C");
					}else{
						sortModel.setSortLetters(sortString.toUpperCase());
					}
					
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
		List<SortModel> filterDateList = new ArrayList<SortModel>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = SourceDateList;
		} else {
			filterDateList.clear();
			for (SortModel sortModel : SourceDateList) {
				String name = sortModel.getName();
				if (name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name).startsWith(
								filterStr.toString())) {
					filterDateList.add(sortModel);
				}

			}
		}

		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}
}
