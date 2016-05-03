package com.llg.help;

import java.text.BreakIterator;
import java.util.Date;
import java.util.Iterator;
import java.util.Stack;

import android.app.Activity;
import android.util.Log;

public class ScreenManager {
	private static Stack<Activity> activityStack;
	private static ScreenManager instance;

	private ScreenManager() {
	}

	/**
	 * @return 获取activity管理实例
	 */
	public static ScreenManager getScreenManager() {
		if (instance == null) {
			instance = new ScreenManager();
		}
		return instance;
	}

	/**
	 * 杀死栈顶activity
	 */
	public void popActivity() {
		Log.i("tag", activityStack.hashCode()+"activityStack是否为空");
		Activity activity = activityStack.lastElement();
		Log.i("tag", activity.hashCode()+"activity是否为空");
		if (activity != null) {
			activity.finish();
			activity = null;
		}
	}

	/**
	 * @param activity
	 *            杀死指定的activity
	 */
	public void popActivity(Class cls) {
		// if (activity != null) {
		// activity.finish();
		// activityStack.remove(activity);
		// activity = null;
		// }
		if (cls != null) {
			Iterator<Activity> iterator = activityStack.listIterator();
			if (iterator.hasNext()) {
				Activity activity = iterator.next();
				activity.getClass().equals(cls);
				activityStack.remove(activity);
				return;
			}
		}
	}

	/**
	 * @param activity
	 *            杀死指定的activity
	 */
	public void popActivity(Activity activity) {
		if (activity != null) {
			activity.finish();
			activityStack.remove(activity);
			activity = null;
		}

	}

	/**
	 * @return 获取栈顶activity
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * @param activity
	 *            添加指定activity到栈
	 */
	public void pushActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * @param cls
	 *            杀死所有activity
	 */
	public void popAllActivityExceptOne() {
		while (true) {
			Activity activity = currentActivity();
			if (activity == null) {
				break;
			}
			// if (activity.getClass().equals(cls)) {
			// break;
			// }
			popActivity(activity.getClass());
		}
	}

	/**
	 * @param cls
	 *            杀死所有activity
	 */
	public void popOtherActivity(Class cls) {
		for (int i = 0; i < activityStack.size(); i++) {
			Activity activity = currentActivity();
//			if (activity == null) {
//				break;
//			}
//			if (activity.getClass().equals(cls)) {
//				break;
//			}
//			popActivity(activity.getClass());
			if (activity!=null&&!activity.getClass().equals(cls)) {
				popActivity(activity.getClass());
			}
		}
	}
	public void activityList(){
		Log.i("tag", activityStack.size()+"====================size()");
	}
}
