package com.finddreams.sortedcontact.sortlist;

public class SortModel {

	private String name; // 显示的数�?
	private String sortLetters; // 显示数据拼音的首字母

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

	private String baidu_code;// 百度城市码

	public String getBaidu_code() {
		return baidu_code;
	}

	public void setBaidu_code(String baidu_code) {
		this.baidu_code = baidu_code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private String code;// 国家城市码

}
