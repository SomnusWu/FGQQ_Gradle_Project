package com.llg.privateproject.entities;

public class AdvertiseComment {
	public Double getMONEY() {
		return MONEY;
	}

	public void setMoney(Double money) {
		MONEY = money;
	}

	public String getPICTURE_URL() {
		return PICTURE_URL;
	}

	public void setPICTURE_URL(String pICTURE_URL) {
		PICTURE_URL = pICTURE_URL;
	}

	public String getAPPELLATION() {
		return APPELLATION;
	}

	public void setAPPELLATION(String aPPELLATION) {
		APPELLATION = aPPELLATION;
	}

	public Long getCREATE_DATE() {
		return CREATE_DATE;
	}

	public void setCREATE_DATE(Long cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}

	public String getCOMMON_TEXT() {
		return COMMON_TEXT;
	}

	public void setCOMMON_TEXT(String cOMMON_TEXT) {
		COMMON_TEXT = cOMMON_TEXT;
	}

	private Double MONEY;
	private String PICTURE_URL;
	private String APPELLATION;
	private Long CREATE_DATE;
	private String COMMON_TEXT;
}
