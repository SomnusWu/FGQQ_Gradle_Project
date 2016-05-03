package com.llg.privateproject.utils;

import java.lang.reflect.Method;

import android.telephony.TelephonyManager;

import com.android.internal.telephony.ITelephony;

public class PhoneUtils {
	/**
	 * 根据传入的TelephonyManager来取得系统的ITelephony实例.
	 * 
	 * @param telephony
	 * @return 系统的ITelephony实例
	 * @throws Exception
	 */
	public static ITelephony getITelephony(TelephonyManager telephony)
			throws Exception {
		Method getITelephonyMethod = telephony.getClass().getDeclaredMethod(
				"getITelephony");
		getITelephonyMethod.setAccessible(true);// 私有化函数也能使用
		return (ITelephony) getITelephonyMethod.invoke(telephony);
	}
}