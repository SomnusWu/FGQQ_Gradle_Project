package com.llg.privateproject.utils.update;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;

import android.content.Context;

/**
 * 版本实体类
 * 
 * @author gongyibing
 * @version 1.0
 * @created 2015-05-07
 */
public class UpdateVersion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int versionCode;// 版本号
	private String versionName;// 版本名称
	private String downloadUrl;// 下载地址
	private String updateLog;// 更新日志

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getUpdateLog() {
		return updateLog;
	}

	public void setUpdateLog(String updateLog) {
		this.updateLog = updateLog;
	}

	public UpdateVersion() {
		super();
	}

	public UpdateVersion(int versionCode, String versionName,
			String downloadUrl, String updateLog) {
		super();
		this.versionCode = versionCode;
		this.versionName = versionName;
		this.downloadUrl = downloadUrl;
		this.updateLog = updateLog;
	}

	/***
	 * 解析服务端版本信息
	 * 
	 * @param appContext
	 * @return
	 */
	public static void getServiceVersion(Context context,
			final UpdateVersion updateVersion) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("method", "queryVerConfiguration");
		AppContext.getHtmlUitls().xUtils(context, HttpMethod.GET,
				"version/check", params, new HttpCallback() {

					@Override
					public void onError(String msg) {

					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						try {
							int versionCode = json.getInt("versionCode");
							String versionName = json.getString("versionName");
							String downloadUrl = json.getString("downloadUrl");
							String updateLog = json.getString("updateLog");
							// updateVersion.setVersionCode(versionCode);
							// updateVersion.setVersionName(versionName);
							// updateVersion.setDownloadUrl(downloadUrl);
							// updateVersion.setUpdateLog(updateLog);
							updateVersion.setVersionCode(11);
							updateVersion.setVersionName("最新版本name");
							updateVersion
									.setDownloadUrl("http://sq.jd.com/Erx2rq");
							updateVersion.setUpdateLog("更新日志");
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}
}
