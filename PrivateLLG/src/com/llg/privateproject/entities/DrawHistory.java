package com.llg.privateproject.entities;

public class DrawHistory {
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getApplyMoney() {
		return applyMoney;
	}

	public void setApplyMoney(String applyMoney) {
		this.applyMoney = applyMoney;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getApplyAccount() {
		return applyAccount;
	}

	public void setApplyAccount(String applyAccount) {
		this.applyAccount = applyAccount;
	}

	private String accountType;// 支付宝类型
	private String applyMoney;// 提现金额
	private String applyTime;// 提现时间
	private String applyAccount;// 提现账号
	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	private String auditStatus  ;// 状态
}
