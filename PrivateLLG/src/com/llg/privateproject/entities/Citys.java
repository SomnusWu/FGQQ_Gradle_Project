package com.llg.privateproject.entities;

import com.bjg.lcc.privateproject.R.id;

public class Citys {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String baidu_code;// 百度城市码
	private String code;// 国家城市码
	private String name;// 城市名字
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
