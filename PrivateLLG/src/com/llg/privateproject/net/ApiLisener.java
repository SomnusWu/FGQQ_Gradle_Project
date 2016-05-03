package com.llg.privateproject.net;

import android.view.View;

import com.lidroid.xutils.exception.HttpException;

public interface ApiLisener {
	/**
	 * @param json 字符串json
	 *            请求成功时回调
	 */
	void onSuccess(String json, String mothed);

	/**
	 * @param arg0 异常对象
	 * @param arg1 错误消息
	 *            请求失败时回调
	 */
	void onFail(HttpException arg0, String arg1);

	/**
	 * 网络监听启动前
	 */
	void onPrepare();
}
