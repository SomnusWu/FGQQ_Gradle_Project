package com.llg.privateproject.entities;

import java.io.Serializable;


/**商品规格可选项*/
public class ProdSpecItemBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**id*/
    private java.lang.String id;

    /**可选项名称*/
    private java.lang.String name;
    
    /**是否选中*/
    private Boolean isSelected;
    
    /**传递参数*/
    private java.lang.String defaultValue;

    public ProdSpecItemBean() {
    	super();
    }
    
//	public ProdSpecItemBean(String id, String name, String defaultValue) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.defaultValue = defaultValue;
//	}

    
	public java.lang.String getId() {
		return id;
	}

	public ProdSpecItemBean(String id, String name,String defaultValue, Boolean isSelected) {
		super();
		this.id = id;
		this.name = name;
		this.defaultValue = defaultValue;
		this.isSelected = isSelected;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(java.lang.String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	
    
}
