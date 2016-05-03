package com.llg.privateproject.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 我的关注 Model -- 关注的商品
 * 
 * @author cc
 *
 */
// type=1
// PRICE 商品价格
// NAME 商品名称
// PRODBASEID 商品ID
// PRODSPECID 商品规格ID
// ATTENTIONID 关注ID
// CO 可使用酷币
// ICON_PATH 商品图片
// ATTENTION_COUNT 关注数量

public class FollowModel implements Serializable {
	private String PRICE;
	private String NAME;
	private String PRODBASEID;
	private String PRODSPECID;
	private String ATTENTIONID;
	private String CO;
	private String ICON_PATH;
	private String ATTENTION_COUNT;
	private boolean isChecked=false;

	public String getPRICE() {
		return PRICE;
	}

	public void setPRICE(String pRICE) {
		PRICE = pRICE;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getPRODBASEID() {
		return PRODBASEID;
	}

	public void setPRODBASEID(String pRODBASEID) {
		PRODBASEID = pRODBASEID;
	}

	public String getPRODSPECID() {
		return PRODSPECID;
	}

	public void setPRODSPECID(String pRODSPECID) {
		PRODSPECID = pRODSPECID;
	}

	public String getATTENTIONID() {
		return ATTENTIONID;
	}

	public void setATTENTIONID(String aTTENTIONID) {
		ATTENTIONID = aTTENTIONID;
	}

	public String getCO() {
		return CO;
	}

	public void setCO(String cO) {
		CO = cO;
	}

	public String getICON_PATH() {
		return ICON_PATH;
	}

	public void setICON_PATH(String iCON_PATH) {
		ICON_PATH = iCON_PATH;
	}

	public String getATTENTION_COUNT() {
		return ATTENTION_COUNT;
	}

	public void setATTENTION_COUNT(String aTTENTION_COUNT) {
		ATTENTION_COUNT = aTTENTION_COUNT;
	}

	public List<FollowModel> parseJson(String json) {
		List<FollowModel> arr = new ArrayList<FollowModel>();
		Gson gson = new Gson();
		arr = gson.fromJson(json, new TypeToken<List<FollowModel>>() {
		}.getType());
		return arr;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

}
