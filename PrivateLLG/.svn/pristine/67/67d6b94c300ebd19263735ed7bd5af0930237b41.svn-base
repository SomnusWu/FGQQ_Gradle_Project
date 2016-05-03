package com.llg.privateproject.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 尚品汇 - 商品列表
 * 
 * @author cc
 *
 */
// "id": null,
// "title": "英特尔测试商品200 i5处理器",
// "price": 235,
// "name": "英特尔测试商品200 i5处理器",
// "specId": "40288079531c46db01531c635c7b001d",
// "image": "upload\/images\/prod\/6b725062-0d54-4786-8e4d-76206ec7ace1.jpg",
// "commentCount": 0,
// "salesCount": 0,
// "url": "pages\/prod\/40288079531c46db01531c635c7b001d.html"
public class ShopModel implements Serializable {
	private String id;
	private String title;
	private String price;
	private String name;
	private String specId;
	private String image;
	private String commentCount;
	private String salesCount;
	private String url;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecId() {
		return specId;
	}

	public void setSpecId(String specId) {
		this.specId = specId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}

	public String getSalesCount() {
		return salesCount;
	}

	public void setSalesCount(String salesCount) {
		this.salesCount = salesCount;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<ShopModel> parseJson(String json) {
		List<ShopModel> arr = new ArrayList<ShopModel>();
		Gson gson = new Gson();
		arr = gson.fromJson(json, new TypeToken<List<ShopModel>>() {
		}.getType());
		return arr;
	}

}
