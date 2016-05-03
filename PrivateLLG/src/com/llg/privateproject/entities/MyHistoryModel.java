package com.llg.privateproject.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 浏览记录Model
 * 
 * @author cc
 *
 */

// "id": "402880fb53827895015383649b02000d",
// "iconpath": "upload/images/prod/d9b16a4b-1b2f-4690-977e-41d511647b8f.jpg",
// "price": 199.000000,
// "prodbaseid": "402880f951f1119e0151f742010b0cce",
// "prodspecid": "8a2d3fd45234f9310152352b7d580028",
// "prodname": "秋冬装新款男士棉服",
// "cusid": "3e7f6b9c-e913-4efa-9588-a22b962761ba",
// "browsedate": 1458789563000
public class MyHistoryModel implements Serializable {
	private String id;
	private String iconpath;
	private String price;
	private String prodbaseid;
	private String prodspecid;
	private String prodname;
	private String cusid;
	private String browsedate;
	private String commentCount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIconpath() {
		return iconpath;
	}

	public void setIconpath(String iconpath) {
		this.iconpath = iconpath;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getProdbaseid() {
		return prodbaseid;
	}

	public void setProdbaseid(String prodbaseid) {
		this.prodbaseid = prodbaseid;
	}

	public String getProdspecid() {
		return prodspecid;
	}

	public void setProdspecid(String prodspecid) {
		this.prodspecid = prodspecid;
	}

	public String getProdname() {
		return prodname;
	}

	public void setProdname(String prodname) {
		this.prodname = prodname;
	}

	public String getCusid() {
		return cusid;
	}

	public void setCusid(String cusid) {
		this.cusid = cusid;
	}

	public String getBrowsedate() {
		return browsedate;
	}

	public void setBrowsedate(String browsedate) {
		this.browsedate = browsedate;
	}

	public List<MyHistoryModel> parseJson(String json) {
		List<MyHistoryModel> arr = new ArrayList<MyHistoryModel>();
		Gson gson = new Gson();
		arr = gson.fromJson(json, new TypeToken<List<MyHistoryModel>>() {
		}.getType());
		return arr;
	}

	/**
	 * 将ShopModel 转为MyHistoryModel
	 * 
	 * @param json
	 * @return
	 */
	public List<MyHistoryModel> mJsonData(String json) {
		List<MyHistoryModel> arr = new ArrayList<MyHistoryModel>();
		Gson gson = new Gson();
		List<ShopModel> shopModelList = gson.fromJson(json,
				new TypeToken<List<ShopModel>>() {
				}.getType());
		for (ShopModel shopModel2 : shopModelList) {
			MyHistoryModel hModel  = new MyHistoryModel();
			hModel.setId(shopModel2.getId());
			hModel.setIconpath(shopModel2.getImage());
			hModel.setPrice(shopModel2.getPrice());
			hModel.setProdname(shopModel2.getName());
			hModel.setProdspecid(shopModel2.getSpecId());
			arr.add(hModel);
		}
		return arr;
	}

	public String getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}

}
