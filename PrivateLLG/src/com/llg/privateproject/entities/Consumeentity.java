package com.llg.privateproject.entities;

import java.io.Serializable;

/** 现场消费订单 */
public class Consumeentity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 支付价格price */
	public String PRICE;
	/** 订单号 */
	public String ID;
	public String SHOP_ID;
	/** 支付日期 */
	public String PAY_DATE;
	/** 用co数 */
	public String CO;
	/** 原价 */
	public String ORIG_PRICE;
	/** 返回co个数 */
	public String BACK_CO;
	/** 买家昵称 */
	public String APPELLATION;
	/** 订单编号 */
	public String CODE;
	/** 消费者图片*/
	public String SHOP_IMG;
	/** 店铺图片*/
	public String CUS_IMG;
	/** 店铺名称*/
	public String SHOP_NAME;
	public boolean isPayID =false;

}
