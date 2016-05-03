package com.llg.privateproject.entities;

import java.io.Serializable;

public class MORE_IMAGEBean implements Serializable {

	private int IMAGE_WIDTH;
	private String IMAGE_URL;
	private int IMAGE_HEIGHT;

	public int getIMAGE_WIDTH() {
		return IMAGE_WIDTH;
	}

	public void setIMAGE_WIDTH(int iMAGE_WIDTH) {
		IMAGE_WIDTH = iMAGE_WIDTH;
	}

	public String getIMAGE_URL() {
		return IMAGE_URL;
	}

	public void setIMAGE_URL(String iMAGE_URL) {
		IMAGE_URL = iMAGE_URL;
	}

	public int getIMAGE_HEIGHT() {
		return IMAGE_HEIGHT;
	}

	public void setIMAGE_HEIGHT(int iMAGE_HEIGHT) {
		IMAGE_HEIGHT = iMAGE_HEIGHT;
	}

}
