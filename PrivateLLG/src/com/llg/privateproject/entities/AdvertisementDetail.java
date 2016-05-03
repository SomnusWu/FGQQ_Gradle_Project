package com.llg.privateproject.entities;

import java.util.List;

public class AdvertisementDetail {
	private String IMAGE_URL;

	public String getIMAGE_URL() {
		return IMAGE_URL;
	}

	public void setIMAGE_URL(String iMAGE_URL) {
		IMAGE_URL = iMAGE_URL;
	}

	public String getAD_LOCATION() {
		return AD_LOCATION;
	}

	public void setAD_LOCATION(String aD_LOCATION) {
		AD_LOCATION = aD_LOCATION;
	}

	public Integer getCO_AMOUNT() {
		return CO_AMOUNT;
	}

	public void setCO_AMOUNT(Integer cO_AMOUNT) {
		CO_AMOUNT = cO_AMOUNT;
	}

	public Integer getCO_MIN() {
		return CO_MIN;
	}

	public void setCO_MIN(Integer cO_MIN) {
		CO_MIN = cO_MIN;
	}

	public Float getMONEY() {
		return MONEY;
	}

	public void setMONEY(Float mONEY) {
		MONEY = mONEY;
	}

	public String getCREATE_DATE() {
		return CREATE_DATE;
	}

	public void setCREATE_DATE(String cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}

	public Float getRECEIVED_MONEY() {
		return RECEIVED_MONEY;
	}

	public void setRECEIVED_MONEY(Float rECEIVED_MONEY) {
		RECEIVED_MONEY = rECEIVED_MONEY;
	}

	public Integer getRECEIVED_CO() {
		return RECEIVED_CO;
	}

	public void setRECEIVED_CO(Integer rECEIVED_CO) {
		RECEIVED_CO = rECEIVED_CO;
	}

	private String AD_LOCATION;
	private Integer CO_AMOUNT;
	private Integer CO_MIN;
	private Float MONEY_MIN;

	public Float getMONEY_MIN() {
		return MONEY_MIN;
	}

	public void setMONEY_MIN(Float mONEY_MIN) {
		MONEY_MIN = mONEY_MIN;
	}

	public Float getMONEY_MAX() {
		return MONEY_MAX;
	}

	public void setMONEY_MAX(Float mONEY_MAX) {
		MONEY_MAX = mONEY_MAX;
	}

	private Float MONEY_MAX;
	private Float MONEY;
	private String CREATE_DATE;
	private Float RECEIVED_MONEY;

	public String getAD_STATUS() {
		return AD_STATUS;
	}

	public void setAD_STATUS(String aD_STATUS) {
		AD_STATUS = aD_STATUS;
	}

	public List<MORE_IMAGEBean> getMORE_IMAGES() {
		return MORE_IMAGES;
	}

	public void setMORE_IMAGES(List<MORE_IMAGEBean> mORE_IMAGES) {
		MORE_IMAGES = mORE_IMAGES;
	}

	private String AD_STATUS;// 广告状态
	private Integer RECEIVED_CO;

	private List<MORE_IMAGEBean> MORE_IMAGES;
}
