package com.llg.privateproject.entities;

public class Advertisement {
	private Integer HAS_MONEY;// 是否还有现金

	public Integer getHAS_MONEY() {
		return HAS_MONEY;
	}

	public void setHAS_MONEY(Integer hAS_MONEY) {
		HAS_MONEY = hAS_MONEY;
	}

	public Integer getHAS_CO() {
		return HAS_CO;
	}

	public void setHAS_CO(Integer hAS_CO) {
		HAS_CO = hAS_CO;
	}

	public String getFORWARD_OR_MY() {
		return FORWARD_OR_MY;
	}

	public void setFORWARD_OR_MY(String fORWARD_OR_MY) {
		FORWARD_OR_MY = fORWARD_OR_MY;
	}

	public String getAD_LOCATION() {
		return AD_LOCATION;
	}

	public void setAD_LOCATION(String aD_LOCATION) {
		AD_LOCATION = aD_LOCATION;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getCREATE_DATE() {
		return CREATE_DATE;
	}

	public void setCREATE_DATE(String cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}

	public String getCOVER_IMAGE_ID() {
		return COVER_IMAGE_ID;
	}

	public void setCOVER_IMAGE_ID(String cOVER_IMAGE_ID) {
		COVER_IMAGE_ID = cOVER_IMAGE_ID;
	}

	public String getTITLE() {
		return TITLE;
	}

	public void setTITLE(String tITLE) {
		TITLE = tITLE;
	}

	private Integer HAS_CO;// 是否还有co币
	private String FORWARD_OR_MY;// 是转发广告还是我的广告
	private String AD_LOCATION;// 广告种类
	private String AD_LOCATION_NAME;// 广告位
	public String getAD_LOCATION_NAME() {
		return AD_LOCATION_NAME;
	}

	public void setAD_LOCATION_NAME(String aD_LOCATION_NAME) {
		AD_LOCATION_NAME = aD_LOCATION_NAME;
	}

	private String ID;// 广告ID
	private String CREATE_DATE;// 广告创建时间
	private String COVER_IMAGE_ID;// 封面图片ID
	private String TITLE;// 标题
	private String AD_STATUS;// 广告状态
	private Integer CO_AMOUNT;// 需支付酷币总额
	private String AD_FORWARD_ID;// 转发广告ID
	private String CHECK_STATUS;// 审核状态

	public String getCHECK_STATUS() {
		return CHECK_STATUS;
	}

	public void setCHECK_STATUS(String cHECK_STATUS) {
		CHECK_STATUS = cHECK_STATUS;
	}

	public Integer getCO_AMOUNT() {
		return CO_AMOUNT;
	}

	public void setCO_AMOUNT(Integer cO_AMOUNT) {
		CO_AMOUNT = cO_AMOUNT;
	}

	public Double getMONEY() {
		return MONEY;
	}

	public void setMONEY(Double mONEY) {
		MONEY = mONEY;
	}

	private Double MONEY;// 需支付酷币总额

	public String getAD_FORWARD_ID() {
		return AD_FORWARD_ID;
	}

	public void setAD_FORWARD_ID(String aD_FORWARD_ID) {
		AD_FORWARD_ID = aD_FORWARD_ID;
	}

	private String COVER_IMAGE_URL;// 图片地址

	public String getCOVER_IMAGE_URL() {
		return COVER_IMAGE_URL;
	}

	public void setCOVER_IMAGE_URL(String cOVER_IMAGE_URL) {
		COVER_IMAGE_URL = cOVER_IMAGE_URL;
	}

	public String getAD_STATUS() {
		return AD_STATUS;
	}

	public void setAD_STATUS(String aD_STATUS) {
		AD_STATUS = aD_STATUS;
	}
}
