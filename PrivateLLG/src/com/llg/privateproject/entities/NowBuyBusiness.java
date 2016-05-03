package com.llg.privateproject.entities;

public class NowBuyBusiness {
	public String getADDRESS() {
		return ADDRESS;
	}

	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}

	public Double getAVG_SCORE() {
		return AVG_SCORE;
	}

	public void setAVG_SCORE(Double aVG_SCORE) {
		AVG_SCORE = aVG_SCORE;
	}

	public Integer getCOUNT_SCORE() {
		return COUNT_SCORE;
	}

	public void setCOUNT_SCORE(Integer cOUNT_SCORE) {
		COUNT_SCORE = cOUNT_SCORE;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getIMG() {
		return IMG;
	}

	public void setIMG(String iMG) {
		IMG = iMG;
	}

	public Integer getDISTANCE() {
		return DISTANCE;
	}

	public void setDISTANCE(Integer dISTANCE) {
		DISTANCE = dISTANCE;
	}

	private String ADDRESS;// 地址
	private Double AVG_SCORE;// 综合评分
	private Integer COUNT_SCORE;// 评分人数
	private String NAME;// 商家名字
	private String IMG;// 图片地址
	private Integer DISTANCE;// 距离
	private String ID;// 商家ID
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	private Double SPOT_CO_PERCENT;// 酷币抵现金百分比

	public Double getSPOT_CO_PERCENT() {
		return SPOT_CO_PERCENT;
	}

	public void setSPOT_CO_PERCENT(Double sPOT_CO_PERCENT) {
		SPOT_CO_PERCENT = sPOT_CO_PERCENT;
	}

}
