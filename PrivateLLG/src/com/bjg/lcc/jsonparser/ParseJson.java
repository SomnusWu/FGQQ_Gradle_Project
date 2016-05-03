package com.bjg.lcc.jsonparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.llg.privateproject.entities.Address;
import com.llg.privateproject.entities.AdvertiseComment;
import com.llg.privateproject.entities.Advertisement;
import com.llg.privateproject.entities.AdvertisementDetail;
import com.llg.privateproject.entities.AdvertisementSeeHistory;
import com.llg.privateproject.entities.AttentionAdvertisement;
import com.llg.privateproject.entities.Citys;
import com.llg.privateproject.entities.Customer;
import com.llg.privateproject.entities.DrawHistory;
import com.llg.privateproject.entities.NowBuyBusiness;
import com.llg.privateproject.entities.OrderComment;
import com.llg.privateproject.entities.Region;
import com.llg.privateproject.entities.ThreeRegion;
import com.llg.privateproject.utils.LogManag;

/**
 * 解析数据 yh 2015.08.27
 * */
public class ParseJson {
	private static ParseJson parseJson;

	public static ParseJson getParseJson() {
		if (parseJson == null) {
			parseJson = new ParseJson();
		}
		return parseJson;
	}

	private ParseJson() {
	}

	/** 首页数据解析 */
	public void gethomePageObject(JSONObject jsonObject, Map<String, Object> map) {
		// this.context = context;
		// this.map = map;
		map.clear();
		try {

			if (jsonObject.getBoolean("success")) {

				JSONObject jsonObject2 = jsonObject.getJSONObject("attributes");
				// 轮播广告

				JSONArray AdinfoFlashviewList = jsonObject2
						.getJSONArray("AdinfoFlashviewList");
				List<Map<String, Object>> adinfoFlashviewList = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < AdinfoFlashviewList.length(); i++) {
					JSONObject item = AdinfoFlashviewList.getJSONObject(i);
					Map<String, Object> itemMap = new HashMap<String, Object>();
					if (item.has("content")) {

						itemMap.put("content", item.getString("content"));
					}
					if (item.has("id")) {
						itemMap.put("id", item.getString("id"));
					}
					if (item.has("source")) {
						itemMap.put("source", item.getString("source"));
					}
					if (item.has("objectId")) {
						itemMap.put("objectId", item.getString("objectId"));
					}

					if (item.has("name")) {
						itemMap.put("name", item.getString("name"));
					}
					if (item.has("img")) {
						itemMap.put("img", item.getString("img"));
					}
					if (item.has("objectType")) {
						itemMap.put("objectType", item.getString("objectType"));
					}
					if (item.has("url")) {
						itemMap.put("url", item.getString("url"));
					}
					adinfoFlashviewList.add(itemMap);
				}
				if (map != null) {
					map.put("AdinfoFlashviewList", adinfoFlashviewList);
				}
				// 功能列表
				JSONArray mainFuncList = jsonObject2
						.getJSONArray("mainFuncList");// 功能列表
				// Map<String, Object> mainFunc=new HashMap<String, Object>();
				List<Map<String, Object>> mainFunclist = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < mainFuncList.length(); i++) {
					JSONObject item = mainFuncList.getJSONObject(i);
					Map<String, Object> itemMap = new HashMap<String, Object>();
					if (item.has("funcType")) {

						itemMap.put("funcType", item.getString("funcType"));
					}
					if (item.has("id")) {
						itemMap.put("id", item.getString("id"));
					}
					if (item.has("orderNo")) {
						itemMap.put("orderNo", item.getString("orderNo"));
					}
					if (item.has("status")) {
						itemMap.put("status", item.getString("status"));
					}
					if (item.has("versionId")) {
						itemMap.put("versionId", item.getString("versionId"));
					}
					if (item.has("name")) {
						itemMap.put("name", item.getString("name"));
					}
					if (item.has("img")) {
						itemMap.put("img", item.getString("img"));
					}
					if (item.has("picId")) {
						itemMap.put("picId", item.getString("picId"));
					}
					if (item.has("href")) {
						itemMap.put("href", item.getString("href"));
					}
					if (item.has("categoryId")) {
						itemMap.put("categoryId", item.getString("categoryId"));
					}
					mainFunclist.add(itemMap);
				}
				if (map != null) {
					map.put("mainFunclist", mainFunclist);
				}

				// 积分专区
				JSONArray adinfoList = jsonObject2.getJSONArray("adinfoList");// 积分专区
				// Map<String, Object> adinfo=new HashMap<String, Object>();
				// 积分专区
				List<Map<String, Object>> adinfolstList = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < adinfoList.length(); i++) {
					JSONObject item = adinfoList.getJSONObject(i);
					Map<String, Object> itemMap = new HashMap<String, Object>();

					if (item.has("content")) {
						itemMap.put("content", item.getString("content"));
					}
					if (item.has("id")) {
						itemMap.put("id", item.getString("id"));
					}
					if (item.has("source")) {
						itemMap.put("source", item.getString("source"));
					}
					if (item.has("objectId")) {
						itemMap.put("objectId", item.getString("objectId"));
					}
					if (item.has("name")) {
						itemMap.put("name", item.getString("name"));
					}
					if (item.has("img")) {
						itemMap.put("img", item.getString("img"));
					}
					if (item.has("objectType")) {
						itemMap.put("objectType", item.getString("objectType"));
					}
					if (item.has("url")) {
						itemMap.put("url", item.getString("url"));
					}
					adinfolstList.add(itemMap);
				}
				if (map != null) {
					map.put("adinfoList", adinfolstList);
				}

				// 猜你喜欢
				JSONArray AppProdSpecList = jsonObject2
						.getJSONArray("AppProdSpecList");// 猜你喜欢
				// Map<String, Object> AppProdSpec=new HashMap<String,
				// Object>();
				List<Map<String, Object>> appProdSpeclist = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < AppProdSpecList.length(); i++) {
					JSONObject item = AppProdSpecList.getJSONObject(i);
					Map<String, Object> itemMap = new HashMap<String, Object>();
					if (item.has("pic")) {
						itemMap.put("pic", item.getString("pic"));
					}
					if (item.has("comodityName")) {
						itemMap.put("comodityName",
								item.getString("comodityName"));
					}
					if (item.has("price")) {
						itemMap.put("price", item.getString("price"));
					}
					if (item.has("commodityId")) {
						itemMap.put("commodityId",
								item.getString("commodityId"));
					}
					if (item.has("dsc")) {
						itemMap.put("dsc", item.getString("dsc"));
					}
					if (item.has("shopId")) {
						itemMap.put("shopId", item.getString("shopId"));
					}
					if (item.has("shopName")) {
						itemMap.put("shopName", item.getString("shopName"));
					}

					appProdSpeclist.add(itemMap);
				}
				if (map != null) {
					map.put("AppProdSpecList", appProdSpeclist);
				}
				List<Map<String, Object>> list = (List<Map<String, Object>>) map
						.get("AppProdSpecList");

				// 活动专题
				JSONArray AdinfoActivityList = jsonObject2
						.getJSONArray("AdinfoActivityList");// 活动专题
				// Map<String, Object> adinfo=new HashMap<String, Object>();
				// 活动专题列表
				List<Map<String, Object>> AdinfoActivitylist = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < AdinfoActivityList.length(); i++) {
					JSONObject item = AdinfoActivityList.getJSONObject(i);
					Map<String, Object> itemMap = new HashMap<String, Object>();

					if (item.has("content")) {
						itemMap.put("content", item.getString("content"));

					}
					if (item.has("id")) {
						itemMap.put("id", item.getString("id"));
					}
					if (item.has("source")) {
						itemMap.put("source", item.getString("source"));

					}
					if (item.has("objectId")) {
						itemMap.put("objectId", item.getString("objectId"));
					}
					if (item.has("objectType")) {
						itemMap.put("objectType", item.getString("objectType"));
					}
					if (item.has("name")) {
						itemMap.put("name", item.getString("name"));
					}
					if (item.has("img")) {
						itemMap.put("img", item.getString("img"));
					}
					if (item.has("url")) {
						itemMap.put("url", item.getString("url"));
					}
					AdinfoActivitylist.add(itemMap);
				}
				if (map != null) {
					map.put("AdinfoActivityList", AdinfoActivitylist);

				}

				// 品牌推荐
				JSONArray AdinfoBrandList = jsonObject2
						.getJSONArray("AdinfoBrandList");// 品牌推荐
				// Map<String, Object> adinfo=new HashMap<String, Object>();
				// 品牌推荐列表
				List<Map<String, Object>> adinfoBrandList = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < AdinfoBrandList.length(); i++) {
					JSONObject item = AdinfoBrandList.getJSONObject(i);
					Map<String, Object> itemMap = new HashMap<String, Object>();

					if (item.has("content")) {
						itemMap.put("content", item.getString("content"));
					}
					if (item.has("id")) {
						itemMap.put("id", item.getString("id"));
					}
					if (item.has("source")) {
						itemMap.put("source", item.getString("source"));
					}
					if (item.has("objectId")) {
						itemMap.put("objectId", item.getString("objectId"));
					}
					if (item.has("objectType")) {
						itemMap.put("objectType", item.getString("objectType"));
					}
					if (item.has("name")) {
						itemMap.put("name", item.getString("name"));
					}
					if (item.has("img")) {
						itemMap.put("img", item.getString("img"));

					}
					if (item.has("url")) {
						itemMap.put("url", item.getString("url"));
					}
					adinfoBrandList.add(itemMap);
				}
				if (map != null) {
					map.put("AdinfoBrandList", adinfoBrandList);
				}

				// 主题馆列表
				setData("adinfoThemeList", jsonObject2, map);

				// 店铺推荐
				setData("adinfoShopList", jsonObject2, map);

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @param listName
	 *            列表名
	 * @param jsonObject2
	 *            传入json数据
	 * @param list
	 *            用于保存数据
	 * */
	public void getFirstInstall(String listName, JSONObject jsonObject,
			List<Map<String, Object>> list) {
		list.clear();
		try {
			if (jsonObject.getBoolean("success")) {
				JSONObject jsonObject2 = jsonObject.getJSONObject("attributes");
				setDataList(listName, jsonObject2, list);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param jsonArrayName
	 *            json列表名
	 * @param jsonObject
	 *            传入json数据
	 * @param list
	 *            用于保存Map数据(List<Map<String, Object>> list)
	 * */
	public void setDataList(String jsonArrayName, JSONObject jsonObject,
			List<Map<String, Object>> list) {
		if (list == null) {
			list = new ArrayList<Map<String, Object>>();
		}
		JSONArray jsonArray;
		try {
			jsonArray = jsonObject.getJSONArray(jsonArrayName);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject item = jsonArray.getJSONObject(i);
				Map<String, Object> itemMap = new HashMap<String, Object>();

				list.add(getMap(item, itemMap));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/** JSONObject解析成Map<String, Object> */
	public Map<String, Object> getMap(JSONObject item,
			Map<String, Object> itemMap) {
		if (itemMap == null) {
			itemMap = new HashMap<String, Object>();
		}
		isHash(item, itemMap, "ads");
		isHash(item, itemMap, "address");
		isHash(item, itemMap, "appellation");
		isHash(item, itemMap, "avatar");
		isHash(item, itemMap, "attentionCount");
		isHash(item, itemMap, "brands");
		isHash(item, itemMap, "children");
		isHash(item, itemMap, "co");
		isHash(item, itemMap, "comodityName");
		isHash(item, itemMap, "commodityId");
		isHash(item, itemMap, "categoryId");
		isHash(item, itemMap, "commentCount");
		isHash(item, itemMap, "comment");
		isHash(item, itemMap, "content");
		isHash(item, itemMap, "contact");
		isHash(item, itemMap, "cutPrice");
		isHash(item, itemMap, "cutPrice2");
		isHash(item, itemMap, "cutPrice3");
		isHash(item, itemMap, "createDate");
		isHash(item, itemMap, "date");
		isHash(item, itemMap, "dsc");
		isHash(item, itemMap, "distributionScores");
		isHash(item, itemMap, "fullPrice");
		isHash(item, itemMap, "goingPrice");
		isHash(item, itemMap, "hot");
		isHash(item, itemMap, "id");
		isHash(item, itemMap, "img");
		isHash(item, itemMap, "image");
		isHash(item, itemMap, "imgs");
		isHash(item, itemMap, "integration");
		isHash(item, itemMap, "information");
		isHash(item, itemMap, "informationYoY");
		isHash(item, itemMap, "isProdLimit");
		isHash(item, itemMap, "key");
		isHash(item, itemMap, "logo");
		isHash(item, itemMap, "logistics");
		isHash(item, itemMap, "logisticsYoY");
		isHash(item, itemMap, "name");
		isHash(item, itemMap, "numberOfGoods");
		isHash(item, itemMap, "newCount");
		isHash(item, itemMap, "objectId");
		isHash(item, itemMap, "objectType");
		isHash(item, itemMap, "originalPrice");
		isHash(item, itemMap, "pIdNames");
		isHash(item, itemMap, "pIds");
		isHash(item, itemMap, "pic");
		isHash(item, itemMap, "price");
		isHash(item, itemMap, "phone");
		isHash(item, itemMap, "prodspecId");
		isHash(item, itemMap, "prodSpecId");
		isHash(item, itemMap, "requirePrice");
		isHash(item, itemMap, "requirePrice2");
		isHash(item, itemMap, "requirePrice3");
		isHash(item, itemMap, "remarks");
		isHash(item, itemMap, "shopId");
		isHash(item, itemMap, "salesCount");
		isHash(item, itemMap, "shopName");
		isHash(item, itemMap, "source");
		isHash(item, itemMap, "score");
		isHash(item, itemMap, "serve");
		isHash(item, itemMap, "serveYoY");
		isHash(item, itemMap, "specId");
		isHash(item, itemMap, "title");
		isHash(item, itemMap, "type");
		isHash(item, itemMap, "url");
		isHash(item, itemMap, "value");
		return itemMap;
	}

	/**
	 * item 中存在 value 就向itemMap中添加 value
	 * */
	private void isHash(JSONObject item, Map<String, Object> itemMap,
			String value) {
		if (item.has(value)) {
			try {

				itemMap.put(value, item.get(value));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param listName
	 *            列表名
	 * @param jsonObject2
	 *            传入json数据
	 * @param map
	 *            用于保存数据
	 * */
	private void setData(String listName, JSONObject jsonObject2,
			Map<String, Object> map) {

		JSONArray list;
		try {
			list = jsonObject2.getJSONArray(listName);
			List<Map<String, Object>> myList = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < list.length(); i++) {
				JSONObject item = list.getJSONObject(i);
				Map<String, Object> itemMap = new HashMap<String, Object>();

				isHash(item, itemMap, "content");
				isHash(item, itemMap, "id");
				isHash(item, itemMap, "source");
				isHash(item, itemMap, "objectId");
				isHash(item, itemMap, "name");
				isHash(item, itemMap, "img");
				isHash(item, itemMap, "objectType");
				isHash(item, itemMap, "url");

				myList.add(itemMap);
			}
			if (map != null) {
				map.put(listName, myList);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param jsonArrayName
	 *            json列表名
	 * @param JSONArray
	 *            传入json数组
	 * @param list
	 *            用于保存Map数据(List<Map<String, Object>> list)
	 * */
	public void setDataList(JSONArray jsonArray, List<Map<String, Object>> list) {

		try {

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject item = jsonArray.getJSONObject(i);
				Map<String, Object> itemMap = new HashMap<String, Object>();
				if (item.has("content")) {

					itemMap.put("content", item.getString("content"));
				}
				if (item.has("id")) {
					itemMap.put("id", item.getString("id"));
				}
				if (item.has("source")) {
					itemMap.put("source", item.getString("source"));
				}
				if (item.has("objectId")) {
					itemMap.put("objectId", item.getString("objectId"));
				}

				if (item.has("name")) {
					itemMap.put("name", item.getString("name"));
				}
				if (item.has("img")) {
					itemMap.put("img", item.getString("img"));
				}
				if (item.has("objectType")) {
					itemMap.put("objectType", item.getString("objectType"));
				}
				if (item.has("url")) {
					itemMap.put("url", item.getString("url"));
				}

				if (item.has("brands")) {
					itemMap.put("brands", item.getString("brands"));
				}
				if (item.has("pId")) {
					itemMap.put("pId", item.getString("pId"));
				}
				if (item.has("pIdNames")) {
					itemMap.put("pIdNames", item.getString("pIdNames"));
				}
				if (item.has("children")) {
					itemMap.put("children", item.getString("children"));
				}
				if (item.has("hot")) {
					itemMap.put("hot", item.getString("hot"));
				}
				if (item.has("pIds")) {
					itemMap.put("pIds", item.getString("pIds"));
				}
				if (item.has("ads")) {
					itemMap.put("ads", item.getString("ads"));
				}

				list.add(itemMap);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param jsonObject
	 * @return 个人资料对象 解析资料设置，获取用户资料
	 */
	public Customer parseCustomer(JSONObject jsonObject) {
		String json = null;
		try {
			json = jsonObject.getJSONObject("attributes")
					.getJSONObject("customersResult").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new Gson();
		Customer customer = gson.fromJson(json, Customer.class);
		return customer;
	}

	/**
	 * @param jsonObject
	 * @return 布尔值 解析请求是否成功
	 */
	public Map<String, Object> parseIsSuccess(JSONObject jsonObject) {
		boolean isSuccess;
		isSuccess = jsonObject.optBoolean("success");
		String errorCode = jsonObject.optString("errorCode");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isSuccess", isSuccess);
		map.put("errorCode", errorCode);
		return map;

	}

	/**
	 * @param jsonObject
	 * @return 图片ID 解析图片上传
	 */
	public String parseImg(JSONObject jsonObject) {
		String id = null;
		try {
			id = jsonObject.getJSONObject("attributes").getJSONObject("icon")
					.optString("id");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated catch block
		return id;

	}

	/**
	 * 得到上传广告图片的解析方法 (不包括封面的id)
	 * 
	 * @param jsonObject
	 * @return 返回的就是id
	 */
	public Map parseimgId(JSONObject jsonObject) {
		String id = "";
		Map<String, String> map = new HashMap<String, String>();
		String orig = "";
		try {

			JSONArray mArray = jsonObject.getJSONArray("result");
			for (int i = 0; i < mArray.length(); i++) {
				String temp = mArray.getJSONObject(i).getString("id");
				String origParam = mArray.getJSONObject(i).getString(
						"origParam");
				if (!origParam.equals("img_fm")) {
					map.put(origParam, temp);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated catch block
		return map;
	}

	public String parseimgName(JSONObject jsonObject) {
		String id = "";
		try {
			JSONArray mArray = jsonObject.getJSONArray("result");
			for (int i = 0; i < mArray.length(); i++) {
				String temp = mArray.getJSONObject(i).getString("id");
				String origParam = mArray.getJSONObject(i).getString(
						"origParam");
				if (origParam.equals("img_fm")) {
					id = temp;
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated catch block
		return id;
	}

	/**
	 * @param jsonObject
	 * @return 广告列表集合 解析我的广告页面
	 */
	public List<Advertisement> parseAdvertisementList(JSONObject jsonObject) {
		Gson gson = new Gson();
		List<Advertisement> list = null;
		try {
			JSONArray array = jsonObject.getJSONObject("obj").getJSONArray(
					"result");
			if (array != null) {
				list = gson.fromJson(array.toString(),
						new TypeToken<List<Advertisement>>() {
						}.getType());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @param jsonObject
	 * @return 广告列表集合 解析我的广告页面
	 */
	public List<DrawHistory> parseDrawHistory(String jsonObject) {
		Gson gson = new Gson();
		List<DrawHistory> list = null;
		list = gson.fromJson(jsonObject, new TypeToken<List<DrawHistory>>() {
		}.getType());

		return list;
	}

	/**
	 * @param jsonObject
	 * @return 广告列表集合 解析关注的广告页面
	 */
	public List<AttentionAdvertisement> parseAttentionAdvertisementList(
			JSONObject jsonObject) {
		Gson gson = new Gson();
		List<AttentionAdvertisement> list = null;
		try {
			JSONArray array = jsonObject.getJSONObject("obj").getJSONArray(
					"result");
			if (array != null) {
				list = gson.fromJson(array.toString(),
						new TypeToken<List<AttentionAdvertisement>>() {
						}.getType());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @param jsonObject
	 * @return 城市列表
	 */
	public List<Citys> parseCitys(JSONObject jsonObject) {
		Gson gson = new Gson();
		List<Citys> list = null;
		try {
			JSONArray array = jsonObject.getJSONArray("obj");
			if (array != null) {

				list = gson.fromJson(array.toString(),
						new TypeToken<List<Citys>>() {
						}.getType());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @param jsonObject
	 * @return 广告浏览历史记录
	 */
	public List<AdvertisementSeeHistory> parseAdvertisemntHistory(
			JSONObject jsonObject) {
		Gson gson = new Gson();
		List<AdvertisementSeeHistory> list = null;
		JSONArray array = null;
		try {
			JSONObject jsonObject2 = jsonObject.getJSONObject("obj");
			if (!jsonObject2.isNull("result")) {
				array = jsonObject2.getJSONArray("result");
			}
			if (array != null) {
				list = gson.fromJson(array.toString(),
						new TypeToken<List<AdvertisementSeeHistory>>() {
						}.getType());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @param jsonObject
	 * @return 城市所属的地区及其下属商圈
	 */
	public List<Region> parseRegion(JSONObject jsonObject) {
		Gson gson = new Gson();
		JSONArray jsonArray;
		List<Region> list = null;
		try {
			jsonArray = jsonObject.getJSONObject("attributes").getJSONArray(
					"RegionBeanList");
			if (jsonArray != null) {
				list = gson.fromJson(jsonArray.toString(),
						new TypeToken<List<Region>>() {
						}.getType());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @param jsonObject
	 * @return
	 */
	public List<NowBuyBusiness> parseNowBuyBusiness(JSONObject jsonObject) {
		Gson gson = new Gson();
		JSONArray jsonArray = null;
		List<NowBuyBusiness> list = null;
		JSONObject jsonObject3 = null;
		try {
			JSONObject jsonObject2 = jsonObject.getJSONObject("attributes");
			if (jsonObject2 != null) {
				jsonObject3 = jsonObject2.getJSONObject("page");
			}
			if (jsonObject3 != null) {
				if (!jsonObject3.isNull("result")) {
					jsonArray = jsonObject3.getJSONArray("result");
				}

			}
			if (jsonArray != null) {
				list = gson.fromJson(jsonArray.toString(),
						new TypeToken<List<NowBuyBusiness>>() {
						}.getType());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @param jsonObject
	 * @return 收货地址选择区域的集合
	 */
	public List<ThreeRegion> parseThreeRegion(JSONObject jsonObject) {
		Gson gson = new Gson();
		JSONArray jsonArray;
		List<ThreeRegion> list = null;
		try {
			jsonArray = jsonObject.getJSONArray("obj");
			if (jsonArray != null) {
				list = gson.fromJson(jsonArray.toString(),
						new TypeToken<List<ThreeRegion>>() {
						}.getType());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @param jsonObject
	 * @return
	 */
	public List<Address> parseAddressList(JSONObject jsonObject) {
		Gson gson = new Gson();
		JSONArray jsonArray;
		List<Address> list = null;
		try {
			jsonArray = jsonObject.getJSONArray("obj");
			if (jsonArray != null) {
				list = gson.fromJson(jsonArray.toString(),
						new TypeToken<List<Address>>() {
						}.getType());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @param jsonObject
	 * @return
	 */
	public List<AdvertiseComment> parseAdvertiseComment(JSONObject jsonObject) {
		Gson gson = new Gson();
		JSONArray jsonArray;
		List<AdvertiseComment> list = null;
		try {
			jsonArray = jsonObject.getJSONObject("obj").getJSONArray("result");
			if (jsonArray != null) {
				list = gson.fromJson(jsonArray.toString(),
						new TypeToken<List<AdvertiseComment>>() {
						}.getType());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @param jsonObject
	 * @return 我的订单-待评价
	 */
	public List<OrderComment> parseOrderComment(JSONObject jsonObject) {
		Gson gson = new Gson();
		JSONArray jsonArray;
		List<OrderComment> list = null;
		try {
			jsonArray = jsonObject.getJSONObject("obj").getJSONArray("result");
			if (jsonArray != null) {
				list = gson.fromJson(jsonArray.toString(),
						new TypeToken<List<OrderComment>>() {
						}.getType());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @param jsonObject
	 * @return
	 */
	public AdvertisementDetail parseAdvertisementDetail(JSONObject jsonObject) {
		Gson gson = new Gson();
		JSONObject jsonObject2;
		AdvertisementDetail advertisementDetail = null;
		try {
			jsonObject2 = jsonObject.getJSONObject("obj");
			if (jsonObject2 != null) {
				advertisementDetail = gson.fromJson(jsonObject2.toString(),
						AdvertisementDetail.class);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return advertisementDetail;
	}

	/**
	 * @param jsonObject
	 * @return
	 */
	public List<Map<String, String>> parseKindList(JSONObject jsonObject) {
		JSONArray jsonArray;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map;
		try {
			jsonArray = jsonObject.getJSONArray("result");
			if (jsonArray != null) {
				for (int i = 0; i < jsonArray.length(); i++) {
					map = new HashMap<String, String>();
					map.put("id", jsonArray.getJSONArray(i).getString(0));
					map.put("name", jsonArray.getJSONArray(i).getString(1));
					list.add(map);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
