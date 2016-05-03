package com.llg.privateproject.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

public class TelephonyManagerUtils {

	public static String getDeviceId(Context mContext) {
		TelephonyManager telephonyManager;

		telephonyManager = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);

		/*
		 * 
		 * getDeviceId() function Returns the unique device ID.
		 * 
		 * for example,the IMEI for GSM and the MEID or ESN for CDMA phones.
		 */

		return telephonyManager.getDeviceId();

		/*
		 * 
		 * getSubscriberId() function Returns the unique subscriber ID,
		 * 
		 * for example, the IMSI for a GSM phone.
		 */

		// imsistring = telephonyManager.getSubscriberId();

	}
}
