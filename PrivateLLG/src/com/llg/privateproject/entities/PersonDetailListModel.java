/**
 * 
 */
package com.llg.privateproject.entities;

import java.io.Serializable;

/**
 * @author cc
 * @time 2016年4月26日 上午11:41:04
 * @description 
 */
public class PersonDetailListModel implements Serializable {

	private String count;
	private String label;
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	@Override
	public String toString() {
		return "PersonDetailListModel [count=" + count + ", label=" + label
				+ "]";
	}
	
	
	
}
