package com.llg.help;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

public class BadgeUtil {
	   /**
     * Set badge count<br/>
     * 针对 Samsung / xiaomi / sony 手机有效
     * @param context The context of the application package.
     * @param count Badge count to be set
     */
    public static void setBadgeCount(Context context, int count) {
        if (count <= 0) {
            count = 0;
        } else {
            count = Math.max(0, Math.min(count, 99));
        }

//        if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
//            sendToXiaoMi(context, count);
//        } else if (Build.MANUFACTURER.equalsIgnoreCase("sony")) {
//            sendToSony(context, count);
//        } else if (Build.MANUFACTURER.toLowerCase().contains("samsung")) {
//            sendToSamsumg(context, count);
//        } else {
//            Toast.makeText(context, "Not Support", Toast.LENGTH_LONG).show();
//        }
    
}
}