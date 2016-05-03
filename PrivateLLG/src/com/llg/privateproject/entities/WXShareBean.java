package com.llg.privateproject.entities;

public class WXShareBean {

	private int code;
	private boolean isShare;

	public boolean isShare() {
		return isShare;
	}

	public void setShare(boolean isShare) {
		this.isShare = isShare;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public WXShareBean(int code, boolean isShare) {
		super();
		this.code = code;
		this.isShare = isShare;
	}

}
