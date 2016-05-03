package com.llg.privateproject.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 我关注的店铺列表
 * 
 * @author cc
 *
 */

// NAME 店铺名称
// REMARKS 备注
// GOOD 好评率
// SHOPID 店铺ID
// ATTENTIONID 关注ID
// ICON_PATH 店铺图片
// ATTENTION_COUNT 关注数量
// MAINSELL 店铺主营
public class MyShopModel implements Serializable {

	private String NAME;
	private String REMARKS;
	private String GOOD;
	private String SHOPID;
	private String ATTENTIONID;
	private String ICON_PATH;
	private String ATTENTION_COUNT;
	private String MAINSELL;
	
	private boolean isChecked;

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getREMARKS() {
		return REMARKS;
	}

	public void setREMARKS(String rEMARKS) {
		REMARKS = rEMARKS;
	}

	public String getGOOD() {
		return GOOD;
	}

	public void setGOOD(String gOOD) {
		GOOD = gOOD;
	}

	public String getSHOPID() {
		return SHOPID;
	}

	public void setSHOPID(String sHOPID) {
		SHOPID = sHOPID;
	}

	public String getATTENTIONID() {
		return ATTENTIONID;
	}

	public void setATTENTIONID(String aTTENTIONID) {
		ATTENTIONID = aTTENTIONID;
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

	public String getMAINSELL() {
		return MAINSELL;
	}

	public void setMAINSELL(String mAINSELL) {
		MAINSELL = mAINSELL;
	}

	public List<MyShopModel> parseJson(String json) {
		List<MyShopModel> arr = new ArrayList<MyShopModel>();
		Gson gson = new Gson();
		arr = gson.fromJson(json, new TypeToken<List<MyShopModel>>() {
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
