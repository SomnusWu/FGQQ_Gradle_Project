package com.llg.privateproject.html;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/**
 * 回调 接口
 * 
 * @author gongyibing
 * @version 1.0
 * @created 2015-05-07
 */
public class AndroidCallBack {
	public interface HttpCallback {
		/***
		 * 请求成功回调
		 * 
		 * @param json
		 *            返回值
		 */
		void onBack(JSONObject json);

		/***
		 * 请求异常 错误消息
		 * 
		 * @param msg
		 *            消息体
		 */
		void onError(String msg);
	}

	public interface BooleanCallback {
		void setBoolean(boolean flag);
	}

	public interface OneHandleCallback {
		void onHandle(String msg);
	}


	public interface TwoHandleCallback {
		void onOneHandle(List<Map<String, Object>> list);

		void onExcpetion(String msg);
	}

	public interface Responses {
		void onResult(String json);
	}

	public interface ListMapCallback {
		void onResult(List<Map<String, Object>> list);
	}
}
