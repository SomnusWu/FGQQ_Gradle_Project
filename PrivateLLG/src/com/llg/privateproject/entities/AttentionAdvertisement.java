package com.llg.privateproject.entities;

public class  AttentionAdvertisement {
	public String getCOVER_IMAGE_URL() {
		return COVER_IMAGE_URL;
	}

	public void setCOVER_IMAGE_URL(String cOVER_IMAGE_URL) {
		COVER_IMAGE_URL = cOVER_IMAGE_URL;
	}

	public String getAD_INFO_ID() {
		return AD_INFO_ID;
	}

	public void setAD_INFO_ID(String aD_INFO_ID) {
		AD_INFO_ID = aD_INFO_ID;
	}

	public String getLOCATION_NAME() {
		return LOCATION_NAME;
	}

	public void setLOCATION_NAME(String lOCATION_NAME) {
		LOCATION_NAME = lOCATION_NAME;
	}

	public String getAD_FORWARD_ID() {
		return AD_FORWARD_ID;
	}

	public void setAD_FORWARD_ID(String aD_FORWARD_ID) {
		AD_FORWARD_ID = aD_FORWARD_ID;
	}

	public Integer getHAS_CO() {
		return HAS_CO;
	}

	public void setHAS_CO(Integer hAS_CO) {
		HAS_CO = hAS_CO;
	}

	public Integer getHAS_MONEY() {
		return HAS_MONEY;
	}

	public void setHAS_MONEY(Integer hAS_MONEY) {
		HAS_MONEY = hAS_MONEY;
	}

	public Integer getIS_GRAB() {
		return IS_GRAB;
	}

	public void setIS_GRAB(Integer iS_GRAB) {
		IS_GRAB = iS_GRAB;
	}

	public String getTITLE() {
		return TITLE;
	}

	public void setTITLE(String tITLE) {
		TITLE = tITLE;
	}

	private String COVER_IMAGE_URL;
	private String AD_INFO_ID;
	private String LOCATION_NAME;
	private String AD_LOCATION;
	private String AD_LOCATION_NAME;
	public String getAD_LOCATION_NAME() {
		return AD_LOCATION_NAME;
	}

	public void setAD_LOCATION_NAME(String aD_LOCATION_NAME) {
		AD_LOCATION_NAME = aD_LOCATION_NAME;
	}

	public String getAD_LOCATION() {
		return AD_LOCATION;
	}

	public void setAD_LOCATION(String aD_LOCATION) {
		AD_LOCATION = aD_LOCATION;
	}

	private String AD_FORWARD_ID;
	private Integer HAS_CO;
	private Integer HAS_MONEY;
	private Integer IS_GRAB;
	private String TITLE;
}
