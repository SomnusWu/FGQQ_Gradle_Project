package com.finddreams.sortedcontact.sortlist;

import java.util.List;

public class PersonSortMode {
	private String name; // 显示的名字?

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

	private String sortLetters; // 显示数据拼音的首字母
	private String number;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	private String contactId; //id

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	private List<String>phoneList;

	public List<String> getPhoneList() {
		return phoneList;
	}

	public void setPhoneList(List<String> phoneList) {
		this.phoneList = phoneList;
	}
	private int phoneCount;

	public int getPhoneCount() {
		return phoneCount;
	}

	public void setPhoneCount(int phoneCount) {
		this.phoneCount = phoneCount;
	}
}
