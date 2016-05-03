package com.llg.privateproject.adapters;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.entities.ContactBean;
import com.llg.privateproject.view.QuickAlphabeticBar;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.QuickContactBadge;
import android.widget.TextView;

public class ContactListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<ContactBean> list;
	private HashMap<String, Integer> alphaIndexer; // 字母索引
	private String[] sections; // 存储每个章节
	private Context ctx; // 上下文

	public ContactListAdapter(Context context, List<ContactBean> list,
			QuickAlphabeticBar alpha) {
		this.ctx = context;
		this.inflater = LayoutInflater.from(context);
		this.list = list;
		this.alphaIndexer = new HashMap<String, Integer>();
		this.sections = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {
			// 得到字母
			String name = getAlpha(list.get(i).getSortKey());
			if (!alphaIndexer.containsKey(name)) {
				alphaIndexer.put(name, i);
			}
		}

		Set<String> sectionLetters = alphaIndexer.keySet();
		ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);
		Collections.sort(sectionList); // 根据首字母进行排序
		sections = new String[sectionList.size()];
		sectionList.toArray(sections);

		alpha.setAlphaIndexer(alphaIndexer);

	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void remove(int position) {
		list.remove(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.contact_list_item, null);
			holder = new ViewHolder();
			holder.tvIcon = (TextView) convertView.findViewById(R.id.tv_icon);
			holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.number = (TextView) convertView.findViewById(R.id.number);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ContactBean contact = list.get(position);
		String name = contact.getDesplayName();
		String number = contact.getPhoneNum();
		holder.name.setText(name);
		holder.number.setText(number);
		String newName = name.substring(0, 1);
		holder.tvIcon.setText(newName);
		// 当前字母
		String currentStr = getAlpha(contact.getSortKey());
		int color=getColor(currentStr);
		holder.tvIcon.setBackgroundColor(color);
		// 前面的字母
		String previewStr = (position - 1) >= 0 ? getAlpha(list.get(
				position - 1).getSortKey()) : " ";

		if (!previewStr.equals(currentStr)) {
			holder.alpha.setVisibility(View.VISIBLE);
			holder.alpha.setText(currentStr);
		} else {
			holder.alpha.setVisibility(View.GONE);
		}
		return convertView;
	}

	private static class ViewHolder {
		TextView tvIcon;
		TextView alpha;
		TextView name;
		TextView number;
	}

	/**
	 * 获取首字母
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		if (str == null) {
			return "#";
		}
		if (str.trim().length() == 0) {
			return "#";
		}
		char c = str.trim().substring(0, 1).charAt(0);
		// 正则表达式匹配
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		if (pattern.matcher(c + "").matches()) {
			return (c + "").toUpperCase(); // 将小写字母转换为大写
		} else {
			return "#";
		}
	}

	public int getColor(String str){
		String yellow="ABCDEFG";
				String green="HIJKLMN" ;
						String qing="OPQRSTU";
				String blue="VWXYZ";
				int color=0;
		if (green.contains(str)) {
			color=R.color.green;
		}else if(yellow.contains(str)) {
			color=R.color.yellow;
		}else if (blue.contains(str)) {
			color=R.color.blue;
		}else if (qing.contains(str)) {
			color=R.color.qing;
		}else {
			color=R.color.orange1;
		}
		return color;
	}
}