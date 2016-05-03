package com.llg.common.constant.enums;

public enum ErrorCode {

	COM_IN1("COM_IN1", "公司介绍信息"), COM_INFO1("COM_INFO1", "公司介绍信息"), COM_INFO2(
			"COM_INFO2", "公司介绍信息"), COM_INFO3("COM_INFO3", "公司介绍信息"), COM_INFO4(
			"COM_INFO4", "公司介绍信息");

	private final String value;
	private final String info;

	ErrorCode(String value, String info) {
		this.value = value;
		this.info = info;
	}

	public String getInfo() {
		return info;
	}

	public String getValue() {
		return value;
	}
}
