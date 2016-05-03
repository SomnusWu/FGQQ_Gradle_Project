package com.llg.common.constant.enums;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class MessageUtil {
	public static String str = "";
	public static Map<String, String> map = new HashMap<String, String>();

	public static String getValue(String key) {

		return map.get(key);
	}

	public static String putValue(String key, String val) {

		return map.put(key, val);
	}

	public static void main(String[] args) {
		System.out.println(MessageUtil.getValue(ErrorCode.COM_IN1.getInfo()));

		MessageUtil.putValue(ErrorCode.COM_IN1.getInfo(), "111");
		System.out.println(MessageUtil.getValue(ErrorCode.COM_IN1.getInfo()));

	}
}
