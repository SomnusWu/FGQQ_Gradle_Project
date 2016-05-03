package com.llg.privateproject.entities;

import java.io.Serializable;
import java.util.Map;

public class SMap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> map;
	public SMap( Map<String, Object> map){
		this.map=map;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	

}
