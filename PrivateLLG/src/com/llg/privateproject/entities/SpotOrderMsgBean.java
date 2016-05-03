package com.llg.privateproject.entities;

import java.io.Serializable;

public class SpotOrderMsgBean implements Serializable {
	private String payPrice="";
	private String diCoPrice="";
	private String buyCoPrice="";
	private String backCo="";
	private String origPrice="";
	private String myCo="";

	public String getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(String payPrice) {
		this.payPrice = payPrice;
	}

	public String getDiCoPrice() {
		return diCoPrice;
	}

	public void setDiCoPrice(String diCoPrice) {
		this.diCoPrice = diCoPrice;
	}

	public String getBuyCoPrice() {
		return buyCoPrice;
	}

	public void setBuyCoPrice(String buyCoPrice) {
		this.buyCoPrice = buyCoPrice;
	}

	public String getBackCo() {
		return backCo;
	}

	public void setBackCo(String backCo) {
		this.backCo = backCo;
	}

	public String getOrigPrice() {
		return origPrice;
	}

	public void setOrigPrice(String origPrice) {
		this.origPrice = origPrice;
	}

	public String getMyCo() {
		return myCo;
	}

	public void setMyCo(String myCo) {
		this.myCo = myCo;
	}

	@Override
	public String toString() {
		return "SpotOrderMsgBean [payPrice=" + payPrice + ", diCoPrice="
				+ diCoPrice + ", buyCoPrice=" + buyCoPrice + ", backCo="
				+ backCo + ", origPrice=" + origPrice + ", myCo=" + myCo + "]";
	}

}
