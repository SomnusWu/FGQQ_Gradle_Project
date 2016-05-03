package com.llg.privateproject.entities;

import java.io.Serializable;
import java.util.List;

import android.R.integer;

/**
 * 购物车实体类 yh 2015.11.06
 * */
public class ShoppingCartEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// /***/
	// public String customerId;
	/** 是否选中 */
	public boolean checked;
	/** 所有商品数量 */
	public int allCount;
	/** 所有商品价格 */
	public float allPrice;
	/** 优惠的值 */
	public float cutPrice;
	/** 店铺列表 */
	public List<Store> stores;

	public ShoppingCartEntity() {
		super();
	}

	/** 店铺信息 */
	public static class Store {
		/** 店铺Id */
		public String shopId;
		/** 店铺名 */
		public String shopName;
		/** 被选中状态 */
		public boolean checked;
		/** 所有商品数 */
		public int allCount;
		/*** 所有商品总价 */
		public float allPrice;
		/** 优惠的值 */
		public float cutPrice;
		/** 规格商品列表 */
		public List<CartSpec> cartSpec;

		/** 规格商品 */
		public static class CartSpec {
			/** 商品id */
			public String prodId;
			/** 规格id */
			public String specId;
			/** 图片url */
			public String iconPath;
			/** 商品名 */
			public String prodName;
			/** 规格 */
			public String specName;
			/** 库存 */
			public int cnt;
			/** 可用co数 */
			public int co;
			/**
			 * YOUHUO,WUHUO,BUSONG,NOREV没有地址
			 * */
			public String status;
			// "shareBuyReward":null,
			// "shareSenderReward":null,
			// "status":"YOUHUO",
			/** 价格 */
			public float price;
			/** 数量 */
			public int count;
			public boolean checked;
			// "epcusId":null,
			// "shareCusId":null,
			// "tlCus1":null,
			// "tlCus2":null,
			// "tlCus3":null,
			/** 选中个数 */
			public int allCheckedCount;
			/** 选中价格 */
			public float allCheckedPrice;
			/** 所有商品总价 */
			public float sum;
		}
	}
}