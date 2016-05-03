/**
 * 
 */
package com.llg.privateproject.entities;

import java.io.Serializable;
import java.util.List;

import com.google.gson.Gson;

/**
 * @author cc
 * @time 2016年4月21日 下午1:57:21
 * @description
 */
public class IncomeDetailModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * success : true msg : 操作成功 errorCode : null level : info obj : null result
	 * : [] attributes :
	 * {"status":null,"pages":{"pageNo":1,"pageSize":10,"orderBy"
	 * :"createDate","order"
	 * :"desc","autoCount":true,"result":[{"code":null,"remarks"
	 * :null,"createBy":
	 * null,"createByName":null,"createDate":"2016-04-11 07:56:02"
	 * ,"updateBy":null
	 * ,"updateByName":null,"updateDate":"2016-04-11 07:56:02","delFlag"
	 * :"0","id":"402880795404383301540451fab8000a","recordId":
	 * "402880795404383301540451faac0009"
	 * ,"subjectCode":"AGENT-PROVINCE-LEADER","finalCusId"
	 * :"3e7f6b9c-e913-4efa-9588-a22b962761ba"
	 * ,"finalCusName":"张无忌1","money":0.094
	 * ,"percent":2,"status":"1","description":
	 * "购买商品分利。订单编号：R2016041100000030，商品名称[英特尔测试商品200 i5处理器]，规格[i5盒装 ]，件数[1],所属商家[星二代人参专卖]"
	 * ,"createDateFormat":"2016-04-11 03:56:02","deleted":false},{"code":null,
	 * "remarks":null,"createBy":null,"createByName":null,"createDate":
	 * "2016-04-01 07:48:57"
	 * ,"updateBy":"USER0000000375","updateByName":null,"updateDate"
	 * :"2016-04-08 06:50:09"
	 * ,"delFlag":"0","id":"4028804453cfdff40153d0cbe7a3001d"
	 * ,"recordId":"4028804453cfdff40153d0cbe786001b"
	 * ,"subjectCode":"AGENT-PROVINCE-LEADER"
	 * ,"finalCusId":"3e7f6b9c-e913-4efa-9588-a22b962761ba"
	 * ,"finalCusName":"张无忌1"
	 * ,"money":0.4,"percent":2,"status":"1","description":
	 * "购买商品分利。订单编号：R2016040100000006，商品名称[嗡嗡嗡嗡嗡嗡]，规格[浅灰色 ]，件数[1],所属商家[巨锤旅游]"
	 * ,"createDateFormat"
	 * :"2016-04-01 03:48:57","deleted":false},{"code":null,"remarks"
	 * :null,"createBy"
	 * :null,"createByName":null,"createDate":"2016-03-31 03:02:14"
	 * ,"updateBy":"USER0000000375"
	 * ,"updateByName":null,"updateDate":"2016-04-08 06:50:09"
	 * ,"delFlag":"0","id":"4028807953ca92b20153ca9f0c43000e","recordId":
	 * "4028807953ca92b20153ca9f0c35000c"
	 * ,"subjectCode":"AGENT-PROVINCE-LEADER","finalCusId"
	 * :"3e7f6b9c-e913-4efa-9588-a22b962761ba"
	 * ,"finalCusName":"张无忌1","money":0.024
	 * ,"percent":2,"status":"1","description"
	 * :"现场消费返利。商家：星二代人参专卖，订单号：R2016033100000001"
	 * ,"createDateFormat":"2016-03-31 11:02:14"
	 * ,"deleted":false},{"code":null,"remarks"
	 * :null,"createBy":null,"createByName"
	 * :null,"createDate":"2016-03-24 09:49:17"
	 * ,"updateBy":"USER0000000375","updateByName"
	 * :null,"updateDate":"2016-04-08 06:50:09"
	 * ,"delFlag":"0","id":"4028807953a75e140153a8072fe20016"
	 * ,"recordId":"4028807953a75e140153a8072fc00014"
	 * ,"subjectCode":"AGENT-PROVINCE-LEADER"
	 * ,"finalCusId":"3e7f6b9c-e913-4efa-9588-a22b962761ba"
	 * ,"finalCusName":"张无忌1"
	 * ,"money":50,"percent":2.5,"status":"1","description"
	 * :"合作商家服务费分利,昵称（14012345655）"
	 * ,"createDateFormat":"2016-03-24 05:49:17","deleted"
	 * :false},{"code":null,"remarks"
	 * :null,"createBy":null,"createByName":null,"createDate"
	 * :"2016-03-24 09:44:01"
	 * ,"updateBy":"USER0000000375","updateByName":null,"updateDate"
	 * :"2016-04-08 06:50:09"
	 * ,"delFlag":"0","id":"402880fb53a733590153a80260bc0070"
	 * ,"recordId":"402880fb53a733590153a802608f006e"
	 * ,"subjectCode":"AGENT-PROVINCE-LEADER"
	 * ,"finalCusId":"3e7f6b9c-e913-4efa-9588-a22b962761ba"
	 * ,"finalCusName":"张无忌1"
	 * ,"money":100.000032,"percent":2.083334,"status":"1",
	 * "description":"企业版会员10年服务费分利,昵称（14012345656）"
	 * ,"createDateFormat":"2016-03-24 05:44:01"
	 * ,"deleted":false},{"code":null,"remarks"
	 * :null,"createBy":null,"createByName"
	 * :null,"createDate":"2016-03-24 01:59:55"
	 * ,"updateBy":"USER0000000375","updateByName"
	 * :null,"updateDate":"2016-04-08 06:50:09"
	 * ,"delFlag":"0","id":"4028807953a639bb0153a65977db0051"
	 * ,"recordId":"4028807953a639bb0153a65977bc004f"
	 * ,"subjectCode":"AGENT-PROVINCE-LEADER"
	 * ,"finalCusId":"3e7f6b9c-e913-4efa-9588-a22b962761ba"
	 * ,"finalCusName":"张无忌1"
	 * ,"money":50.0004,"percent":2.7778,"status":"1","description"
	 * :"合作商家服务费分利,昵称（14012345653）"
	 * ,"createDateFormat":"2016-03-24 09:59:55","deleted"
	 * :false},{"code":null,"remarks"
	 * :null,"createBy":null,"createByName":null,"createDate"
	 * :"2016-03-24 01:44:08"
	 * ,"updateBy":"USER0000000375","updateByName":null,"updateDate"
	 * :"2016-04-08 06:50:09"
	 * ,"delFlag":"0","id":"4028807953a639bb0153a64b07d10036"
	 * ,"recordId":"4028807953a639bb0153a64b079f0034"
	 * ,"subjectCode":"AGENT-PROVINCE-LEADER"
	 * ,"finalCusId":"3e7f6b9c-e913-4efa-9588-a22b962761ba"
	 * ,"finalCusName":"张无忌1"
	 * ,"money":50.0004,"percent":2.7778,"status":"1","description"
	 * :"合作商家服务费分利,昵称（14012345652）"
	 * ,"createDateFormat":"2016-03-24 09:44:08","deleted"
	 * :false},{"code":null,"remarks"
	 * :null,"createBy":null,"createByName":null,"createDate"
	 * :"2016-03-24 01:35:57"
	 * ,"updateBy":"USER0000000375","updateByName":null,"updateDate"
	 * :"2016-04-08 06:50:09"
	 * ,"delFlag":"0","id":"4028807953a639bb0153a643870a001b"
	 * ,"recordId":"4028807953a639bb0153a64386df0019"
	 * ,"subjectCode":"AGENT-PROVINCE-LEADER"
	 * ,"finalCusId":"3e7f6b9c-e913-4efa-9588-a22b962761ba"
	 * ,"finalCusName":"张无忌1"
	 * ,"money":50.0004,"percent":2.7778,"status":"1","description"
	 * :"合作商家服务费分利,昵称（14012345651）"
	 * ,"createDateFormat":"2016-03-24 09:35:57","deleted"
	 * :false},{"code":null,"remarks"
	 * :null,"createBy":"admin","createByName":null
	 * ,"createDate":"2016-03-21 06:05:29"
	 * ,"updateBy":"USER0000000375","updateByName"
	 * :null,"updateDate":"2016-04-08 06:50:09"
	 * ,"delFlag":"0","id":"402880795396d73f015397c7382a007e"
	 * ,"recordId":"402880795396d73f015397c7380b007c"
	 * ,"subjectCode":"AGENT-PROVINCE-LEADER"
	 * ,"finalCusId":"3e7f6b9c-e913-4efa-9588-a22b962761ba"
	 * ,"finalCusName":"张无忌1"
	 * ,"money":210,"percent":5,"status":"1","description":
	 * "企业版会员10年服务费分利,昵称（14012345661）"
	 * ,"createDateFormat":"2016-03-21 02:05:29","deleted"
	 * :false},{"code":null,"remarks"
	 * :null,"createBy":null,"createByName":null,"createDate"
	 * :"2016-03-21 05:48:01"
	 * ,"updateBy":"USER0000000375","updateByName":null,"updateDate"
	 * :"2016-04-08 06:50:09"
	 * ,"delFlag":"0","id":"402880795396d73f015397b73ba70064"
	 * ,"recordId":"402880795396d73f015397b73b790062"
	 * ,"subjectCode":"AGENT-PROVINCE-LEADER"
	 * ,"finalCusId":"3e7f6b9c-e913-4efa-9588-a22b962761ba"
	 * ,"finalCusName":"张无忌1"
	 * ,"money":210,"percent":5,"status":"1","description":
	 * "企业版会员10年服务费分利,昵称（14012345661）"
	 * ,"createDateFormat":"2016-03-21 01:48:01","deleted"
	 * :false}],"allResult":null
	 * ,"totalCount":37,"orderbyMap":[],"inverseOrder":"asc"
	 * ,"nextPage":2,"totalPages"
	 * :4,"prePage":1,"first":0,"pageSizeSetted":true,"orderBySetted"
	 * :true,"firstSetted":true},"AUTO_PROFIT_TO_ASSET_DAY":"7"}
	 */

	private boolean success;
	private String msg;
	private Object errorCode;
	private String level;
	private Object obj;
	/**
	 * status : null pages :
	 * {"pageNo":1,"pageSize":10,"orderBy":"createDate","order"
	 * :"desc","autoCount"
	 * :true,"result":[{"code":null,"remarks":null,"createBy":
	 * null,"createByName"
	 * :null,"createDate":"2016-04-11 07:56:02","updateBy":null
	 * ,"updateByName":null
	 * ,"updateDate":"2016-04-11 07:56:02","delFlag":"0","id"
	 * :"402880795404383301540451fab8000a"
	 * ,"recordId":"402880795404383301540451faac0009"
	 * ,"subjectCode":"AGENT-PROVINCE-LEADER"
	 * ,"finalCusId":"3e7f6b9c-e913-4efa-9588-a22b962761ba"
	 * ,"finalCusName":"张无忌1"
	 * ,"money":0.094,"percent":2,"status":"1","description":
	 * "购买商品分利。订单编号：R2016041100000030，商品名称[英特尔测试商品200 i5处理器]，规格[i5盒装 ]，件数[1],所属商家[星二代人参专卖]"
	 * ,"createDateFormat":"2016-04-11 03:56:02","deleted":false},{"code":null,
	 * "remarks":null,"createBy":null,"createByName":null,"createDate":
	 * "2016-04-01 07:48:57"
	 * ,"updateBy":"USER0000000375","updateByName":null,"updateDate"
	 * :"2016-04-08 06:50:09"
	 * ,"delFlag":"0","id":"4028804453cfdff40153d0cbe7a3001d"
	 * ,"recordId":"4028804453cfdff40153d0cbe786001b"
	 * ,"subjectCode":"AGENT-PROVINCE-LEADER"
	 * ,"finalCusId":"3e7f6b9c-e913-4efa-9588-a22b962761ba"
	 * ,"finalCusName":"张无忌1"
	 * ,"money":0.4,"percent":2,"status":"1","description":
	 * "购买商品分利。订单编号：R2016040100000006，商品名称[嗡嗡嗡嗡嗡嗡]，规格[浅灰色 ]，件数[1],所属商家[巨锤旅游]"
	 * ,"createDateFormat"
	 * :"2016-04-01 03:48:57","deleted":false},{"code":null,"remarks"
	 * :null,"createBy"
	 * :null,"createByName":null,"createDate":"2016-03-31 03:02:14"
	 * ,"updateBy":"USER0000000375"
	 * ,"updateByName":null,"updateDate":"2016-04-08 06:50:09"
	 * ,"delFlag":"0","id":"4028807953ca92b20153ca9f0c43000e","recordId":
	 * "4028807953ca92b20153ca9f0c35000c"
	 * ,"subjectCode":"AGENT-PROVINCE-LEADER","finalCusId"
	 * :"3e7f6b9c-e913-4efa-9588-a22b962761ba"
	 * ,"finalCusName":"张无忌1","money":0.024
	 * ,"percent":2,"status":"1","description"
	 * :"现场消费返利。商家：星二代人参专卖，订单号：R2016033100000001"
	 * ,"createDateFormat":"2016-03-31 11:02:14"
	 * ,"deleted":false},{"code":null,"remarks"
	 * :null,"createBy":null,"createByName"
	 * :null,"createDate":"2016-03-24 09:49:17"
	 * ,"updateBy":"USER0000000375","updateByName"
	 * :null,"updateDate":"2016-04-08 06:50:09"
	 * ,"delFlag":"0","id":"4028807953a75e140153a8072fe20016"
	 * ,"recordId":"4028807953a75e140153a8072fc00014"
	 * ,"subjectCode":"AGENT-PROVINCE-LEADER"
	 * ,"finalCusId":"3e7f6b9c-e913-4efa-9588-a22b962761ba"
	 * ,"finalCusName":"张无忌1"
	 * ,"money":50,"percent":2.5,"status":"1","description"
	 * :"合作商家服务费分利,昵称（14012345655）"
	 * ,"createDateFormat":"2016-03-24 05:49:17","deleted"
	 * :false},{"code":null,"remarks"
	 * :null,"createBy":null,"createByName":null,"createDate"
	 * :"2016-03-24 09:44:01"
	 * ,"updateBy":"USER0000000375","updateByName":null,"updateDate"
	 * :"2016-04-08 06:50:09"
	 * ,"delFlag":"0","id":"402880fb53a733590153a80260bc0070"
	 * ,"recordId":"402880fb53a733590153a802608f006e"
	 * ,"subjectCode":"AGENT-PROVINCE-LEADER"
	 * ,"finalCusId":"3e7f6b9c-e913-4efa-9588-a22b962761ba"
	 * ,"finalCusName":"张无忌1"
	 * ,"money":100.000032,"percent":2.083334,"status":"1",
	 * "description":"企业版会员10年服务费分利,昵称（14012345656）"
	 * ,"createDateFormat":"2016-03-24 05:44:01"
	 * ,"deleted":false},{"code":null,"remarks"
	 * :null,"createBy":null,"createByName"
	 * :null,"createDate":"2016-03-24 01:59:55"
	 * ,"updateBy":"USER0000000375","updateByName"
	 * :null,"updateDate":"2016-04-08 06:50:09"
	 * ,"delFlag":"0","id":"4028807953a639bb0153a65977db0051"
	 * ,"recordId":"4028807953a639bb0153a65977bc004f"
	 * ,"subjectCode":"AGENT-PROVINCE-LEADER"
	 * ,"finalCusId":"3e7f6b9c-e913-4efa-9588-a22b962761ba"
	 * ,"finalCusName":"张无忌1"
	 * ,"money":50.0004,"percent":2.7778,"status":"1","description"
	 * :"合作商家服务费分利,昵称（14012345653）"
	 * ,"createDateFormat":"2016-03-24 09:59:55","deleted"
	 * :false},{"code":null,"remarks"
	 * :null,"createBy":null,"createByName":null,"createDate"
	 * :"2016-03-24 01:44:08"
	 * ,"updateBy":"USER0000000375","updateByName":null,"updateDate"
	 * :"2016-04-08 06:50:09"
	 * ,"delFlag":"0","id":"4028807953a639bb0153a64b07d10036"
	 * ,"recordId":"4028807953a639bb0153a64b079f0034"
	 * ,"subjectCode":"AGENT-PROVINCE-LEADER"
	 * ,"finalCusId":"3e7f6b9c-e913-4efa-9588-a22b962761ba"
	 * ,"finalCusName":"张无忌1"
	 * ,"money":50.0004,"percent":2.7778,"status":"1","description"
	 * :"合作商家服务费分利,昵称（14012345652）"
	 * ,"createDateFormat":"2016-03-24 09:44:08","deleted"
	 * :false},{"code":null,"remarks"
	 * :null,"createBy":null,"createByName":null,"createDate"
	 * :"2016-03-24 01:35:57"
	 * ,"updateBy":"USER0000000375","updateByName":null,"updateDate"
	 * :"2016-04-08 06:50:09"
	 * ,"delFlag":"0","id":"4028807953a639bb0153a643870a001b"
	 * ,"recordId":"4028807953a639bb0153a64386df0019"
	 * ,"subjectCode":"AGENT-PROVINCE-LEADER"
	 * ,"finalCusId":"3e7f6b9c-e913-4efa-9588-a22b962761ba"
	 * ,"finalCusName":"张无忌1"
	 * ,"money":50.0004,"percent":2.7778,"status":"1","description"
	 * :"合作商家服务费分利,昵称（14012345651）"
	 * ,"createDateFormat":"2016-03-24 09:35:57","deleted"
	 * :false},{"code":null,"remarks"
	 * :null,"createBy":"admin","createByName":null
	 * ,"createDate":"2016-03-21 06:05:29"
	 * ,"updateBy":"USER0000000375","updateByName"
	 * :null,"updateDate":"2016-04-08 06:50:09"
	 * ,"delFlag":"0","id":"402880795396d73f015397c7382a007e"
	 * ,"recordId":"402880795396d73f015397c7380b007c"
	 * ,"subjectCode":"AGENT-PROVINCE-LEADER"
	 * ,"finalCusId":"3e7f6b9c-e913-4efa-9588-a22b962761ba"
	 * ,"finalCusName":"张无忌1"
	 * ,"money":210,"percent":5,"status":"1","description":
	 * "企业版会员10年服务费分利,昵称（14012345661）"
	 * ,"createDateFormat":"2016-03-21 02:05:29","deleted"
	 * :false},{"code":null,"remarks"
	 * :null,"createBy":null,"createByName":null,"createDate"
	 * :"2016-03-21 05:48:01"
	 * ,"updateBy":"USER0000000375","updateByName":null,"updateDate"
	 * :"2016-04-08 06:50:09"
	 * ,"delFlag":"0","id":"402880795396d73f015397b73ba70064"
	 * ,"recordId":"402880795396d73f015397b73b790062"
	 * ,"subjectCode":"AGENT-PROVINCE-LEADER"
	 * ,"finalCusId":"3e7f6b9c-e913-4efa-9588-a22b962761ba"
	 * ,"finalCusName":"张无忌1"
	 * ,"money":210,"percent":5,"status":"1","description":
	 * "企业版会员10年服务费分利,昵称（14012345661）"
	 * ,"createDateFormat":"2016-03-21 01:48:01","deleted"
	 * :false}],"allResult":null
	 * ,"totalCount":37,"orderbyMap":[],"inverseOrder":"asc"
	 * ,"nextPage":2,"totalPages"
	 * :4,"prePage":1,"first":0,"pageSizeSetted":true,"orderBySetted"
	 * :true,"firstSetted":true} AUTO_PROFIT_TO_ASSET_DAY : 7
	 */

	private AttributesBean attributes;
	private List<?> result;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Object errorCode) {
		this.errorCode = errorCode;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public AttributesBean getAttributes() {
		return attributes;
	}

	public void setAttributes(AttributesBean attributes) {
		this.attributes = attributes;
	}

	public List<?> getResult() {
		return result;
	}

	public void setResult(List<?> result) {
		this.result = result;
	}

	public static class AttributesBean {
		private Object status;
		/**
		 * pageNo : 1 pageSize : 10 orderBy : createDate order : desc autoCount
		 * : true result :
		 * [{"code":null,"remarks":null,"createBy":null,"createByName"
		 * :null,"createDate"
		 * :"2016-04-11 07:56:02","updateBy":null,"updateByName"
		 * :null,"updateDate":"2016-04-11 07:56:02","delFlag":"0","id":
		 * "402880795404383301540451fab8000a"
		 * ,"recordId":"402880795404383301540451faac0009"
		 * ,"subjectCode":"AGENT-PROVINCE-LEADER"
		 * ,"finalCusId":"3e7f6b9c-e913-4efa-9588-a22b962761ba"
		 * ,"finalCusName":"张无忌1"
		 * ,"money":0.094,"percent":2,"status":"1","description":
		 * "购买商品分利。订单编号：R2016041100000030，商品名称[英特尔测试商品200 i5处理器]，规格[i5盒装 ]，件数[1],所属商家[星二代人参专卖]"
		 * ,"createDateFormat":"2016-04-11 03:56:02","deleted":false},{"code":
		 * null,"remarks":null,"createBy":null,"createByName":null,"createDate":
		 * "2016-04-01 07:48:57"
		 * ,"updateBy":"USER0000000375","updateByName":null,
		 * "updateDate":"2016-04-08 06:50:09"
		 * ,"delFlag":"0","id":"4028804453cfdff40153d0cbe7a3001d"
		 * ,"recordId":"4028804453cfdff40153d0cbe786001b"
		 * ,"subjectCode":"AGENT-PROVINCE-LEADER"
		 * ,"finalCusId":"3e7f6b9c-e913-4efa-9588-a22b962761ba"
		 * ,"finalCusName":"张无忌1"
		 * ,"money":0.4,"percent":2,"status":"1","description":
		 * "购买商品分利。订单编号：R2016040100000006，商品名称[嗡嗡嗡嗡嗡嗡]，规格[浅灰色 ]，件数[1],所属商家[巨锤旅游]"
		 * ,"createDateFormat":"2016-04-01 03:48:57","deleted":false},{"code":
		 * null,"remarks":null,"createBy":null,"createByName":null,"createDate":
		 * "2016-03-31 03:02:14"
		 * ,"updateBy":"USER0000000375","updateByName":null,
		 * "updateDate":"2016-04-08 06:50:09"
		 * ,"delFlag":"0","id":"4028807953ca92b20153ca9f0c43000e"
		 * ,"recordId":"4028807953ca92b20153ca9f0c35000c"
		 * ,"subjectCode":"AGENT-PROVINCE-LEADER"
		 * ,"finalCusId":"3e7f6b9c-e913-4efa-9588-a22b962761ba"
		 * ,"finalCusName":"张无忌1"
		 * ,"money":0.024,"percent":2,"status":"1","description"
		 * :"现场消费返利。商家：星二代人参专卖，订单号：R2016033100000001"
		 * ,"createDateFormat":"2016-03-31 11:02:14"
		 * ,"deleted":false},{"code":null
		 * ,"remarks":null,"createBy":null,"createByName"
		 * :null,"createDate":"2016-03-24 09:49:17"
		 * ,"updateBy":"USER0000000375","updateByName"
		 * :null,"updateDate":"2016-04-08 06:50:09"
		 * ,"delFlag":"0","id":"4028807953a75e140153a8072fe20016"
		 * ,"recordId":"4028807953a75e140153a8072fc00014"
		 * ,"subjectCode":"AGENT-PROVINCE-LEADER"
		 * ,"finalCusId":"3e7f6b9c-e913-4efa-9588-a22b962761ba"
		 * ,"finalCusName":"张无忌1"
		 * ,"money":50,"percent":2.5,"status":"1","description"
		 * :"合作商家服务费分利,昵称（14012345655）"
		 * ,"createDateFormat":"2016-03-24 05:49:17",
		 * "deleted":false},{"code":null
		 * ,"remarks":null,"createBy":null,"createByName"
		 * :null,"createDate":"2016-03-24 09:44:01"
		 * ,"updateBy":"USER0000000375","updateByName"
		 * :null,"updateDate":"2016-04-08 06:50:09"
		 * ,"delFlag":"0","id":"402880fb53a733590153a80260bc0070"
		 * ,"recordId":"402880fb53a733590153a802608f006e"
		 * ,"subjectCode":"AGENT-PROVINCE-LEADER"
		 * ,"finalCusId":"3e7f6b9c-e913-4efa-9588-a22b962761ba"
		 * ,"finalCusName":"张无忌1"
		 * ,"money":100.000032,"percent":2.083334,"status":
		 * "1","description":"企业版会员10年服务费分利,昵称（14012345656）"
		 * ,"createDateFormat":"2016-03-24 05:44:01"
		 * ,"deleted":false},{"code":null
		 * ,"remarks":null,"createBy":null,"createByName"
		 * :null,"createDate":"2016-03-24 01:59:55"
		 * ,"updateBy":"USER0000000375","updateByName"
		 * :null,"updateDate":"2016-04-08 06:50:09"
		 * ,"delFlag":"0","id":"4028807953a639bb0153a65977db0051"
		 * ,"recordId":"4028807953a639bb0153a65977bc004f"
		 * ,"subjectCode":"AGENT-PROVINCE-LEADER"
		 * ,"finalCusId":"3e7f6b9c-e913-4efa-9588-a22b962761ba"
		 * ,"finalCusName":"张无忌1"
		 * ,"money":50.0004,"percent":2.7778,"status":"1","description"
		 * :"合作商家服务费分利,昵称（14012345653）"
		 * ,"createDateFormat":"2016-03-24 09:59:55",
		 * "deleted":false},{"code":null
		 * ,"remarks":null,"createBy":null,"createByName"
		 * :null,"createDate":"2016-03-24 01:44:08"
		 * ,"updateBy":"USER0000000375","updateByName"
		 * :null,"updateDate":"2016-04-08 06:50:09"
		 * ,"delFlag":"0","id":"4028807953a639bb0153a64b07d10036"
		 * ,"recordId":"4028807953a639bb0153a64b079f0034"
		 * ,"subjectCode":"AGENT-PROVINCE-LEADER"
		 * ,"finalCusId":"3e7f6b9c-e913-4efa-9588-a22b962761ba"
		 * ,"finalCusName":"张无忌1"
		 * ,"money":50.0004,"percent":2.7778,"status":"1","description"
		 * :"合作商家服务费分利,昵称（14012345652）"
		 * ,"createDateFormat":"2016-03-24 09:44:08",
		 * "deleted":false},{"code":null
		 * ,"remarks":null,"createBy":null,"createByName"
		 * :null,"createDate":"2016-03-24 01:35:57"
		 * ,"updateBy":"USER0000000375","updateByName"
		 * :null,"updateDate":"2016-04-08 06:50:09"
		 * ,"delFlag":"0","id":"4028807953a639bb0153a643870a001b"
		 * ,"recordId":"4028807953a639bb0153a64386df0019"
		 * ,"subjectCode":"AGENT-PROVINCE-LEADER"
		 * ,"finalCusId":"3e7f6b9c-e913-4efa-9588-a22b962761ba"
		 * ,"finalCusName":"张无忌1"
		 * ,"money":50.0004,"percent":2.7778,"status":"1","description"
		 * :"合作商家服务费分利,昵称（14012345651）"
		 * ,"createDateFormat":"2016-03-24 09:35:57",
		 * "deleted":false},{"code":null
		 * ,"remarks":null,"createBy":"admin","createByName"
		 * :null,"createDate":"2016-03-21 06:05:29"
		 * ,"updateBy":"USER0000000375","updateByName"
		 * :null,"updateDate":"2016-04-08 06:50:09"
		 * ,"delFlag":"0","id":"402880795396d73f015397c7382a007e"
		 * ,"recordId":"402880795396d73f015397c7380b007c"
		 * ,"subjectCode":"AGENT-PROVINCE-LEADER"
		 * ,"finalCusId":"3e7f6b9c-e913-4efa-9588-a22b962761ba"
		 * ,"finalCusName":"张无忌1"
		 * ,"money":210,"percent":5,"status":"1","description"
		 * :"企业版会员10年服务费分利,昵称（14012345661）"
		 * ,"createDateFormat":"2016-03-21 02:05:29"
		 * ,"deleted":false},{"code":null
		 * ,"remarks":null,"createBy":null,"createByName"
		 * :null,"createDate":"2016-03-21 05:48:01"
		 * ,"updateBy":"USER0000000375","updateByName"
		 * :null,"updateDate":"2016-04-08 06:50:09"
		 * ,"delFlag":"0","id":"402880795396d73f015397b73ba70064"
		 * ,"recordId":"402880795396d73f015397b73b790062"
		 * ,"subjectCode":"AGENT-PROVINCE-LEADER"
		 * ,"finalCusId":"3e7f6b9c-e913-4efa-9588-a22b962761ba"
		 * ,"finalCusName":"张无忌1"
		 * ,"money":210,"percent":5,"status":"1","description"
		 * :"企业版会员10年服务费分利,昵称（14012345661）"
		 * ,"createDateFormat":"2016-03-21 01:48:01","deleted":false}] allResult
		 * : null totalCount : 37 orderbyMap : [] inverseOrder : asc nextPage :
		 * 2 totalPages : 4 prePage : 1 first : 0 pageSizeSetted : true
		 * orderBySetted : true firstSetted : true
		 */

		private PagesBean pages;
		private String AUTO_PROFIT_TO_ASSET_DAY;

		public Object getStatus() {
			return status;
		}

		public void setStatus(Object status) {
			this.status = status;
		}

		public PagesBean getPages() {
			return pages;
		}

		public void setPages(PagesBean pages) {
			this.pages = pages;
		}

		public String getAUTO_PROFIT_TO_ASSET_DAY() {
			return AUTO_PROFIT_TO_ASSET_DAY;
		}

		public void setAUTO_PROFIT_TO_ASSET_DAY(String AUTO_PROFIT_TO_ASSET_DAY) {
			this.AUTO_PROFIT_TO_ASSET_DAY = AUTO_PROFIT_TO_ASSET_DAY;
		}

		public static class PagesBean {
			private String pageNo;
			private String pageSize;
			private String orderBy;
			private String order;
			private boolean autoCount;
			private Object allResult;
			private String totalCount;
			private String inverseOrder;
			private String nextPage;
			private String totalPages;
			private String prePage;
			private String first;
			private boolean pageSizeSetted;
			private boolean orderBySetted;
			private boolean firstSetted;
			/**
			 * code : null remarks : null createBy : null createByName : null
			 * createDate : 2016-04-11 07:56:02 updateBy : null updateByName :
			 * null updateDate : 2016-04-11 07:56:02 delFlag : 0 id :
			 * 402880795404383301540451fab8000a recordId :
			 * 402880795404383301540451faac0009 subjectCode :
			 * AGENT-PROVINCE-LEADER finalCusId :
			 * 3e7f6b9c-e913-4efa-9588-a22b962761ba finalCusName : 张无忌1 money :
			 * 0.094 percent : 2 status : 1 description :
			 * 购买商品分利。订单编号：R2016041100000030，商品名称[英特尔测试商品200 i5处理器]，规格[i5盒装
			 * ]，件数[1],所属商家[星二代人参专卖] createDateFormat : 2016-04-11 03:56:02
			 * deleted : false
			 */

			private List<ResultBean> result;
			private List<?> orderbyMap;

			public String getPageNo() {
				return pageNo;
			}

			public void setPageNo(String pageNo) {
				this.pageNo = pageNo;
			}

			public String getPageSize() {
				return pageSize;
			}

			public void setPageSize(String pageSize) {
				this.pageSize = pageSize;
			}

			public String getOrderBy() {
				return orderBy;
			}

			public void setOrderBy(String orderBy) {
				this.orderBy = orderBy;
			}

			public String getOrder() {
				return order;
			}

			public void setOrder(String order) {
				this.order = order;
			}

			public boolean isAutoCount() {
				return autoCount;
			}

			public void setAutoCount(boolean autoCount) {
				this.autoCount = autoCount;
			}

			public Object getAllResult() {
				return allResult;
			}

			public void setAllResult(Object allResult) {
				this.allResult = allResult;
			}

			public String getTotalCount() {
				return totalCount;
			}

			public void setTotalCount(String totalCount) {
				this.totalCount = totalCount;
			}

			public String getInverseOrder() {
				return inverseOrder;
			}

			public void setInverseOrder(String inverseOrder) {
				this.inverseOrder = inverseOrder;
			}

			public String getNextPage() {
				return nextPage;
			}

			public void setNextPage(String nextPage) {
				this.nextPage = nextPage;
			}

			public String getTotalPages() {
				return totalPages;
			}

			public void setTotalPages(String totalPages) {
				this.totalPages = totalPages;
			}

			public String getPrePage() {
				return prePage;
			}

			public void setPrePage(String prePage) {
				this.prePage = prePage;
			}

			public String getFirst() {
				return first;
			}

			public void setFirst(String first) {
				this.first = first;
			}

			public boolean isPageSizeSetted() {
				return pageSizeSetted;
			}

			public void setPageSizeSetted(boolean pageSizeSetted) {
				this.pageSizeSetted = pageSizeSetted;
			}

			public boolean isOrderBySetted() {
				return orderBySetted;
			}

			public void setOrderBySetted(boolean orderBySetted) {
				this.orderBySetted = orderBySetted;
			}

			public boolean isFirstSetted() {
				return firstSetted;
			}

			public void setFirstSetted(boolean firstSetted) {
				this.firstSetted = firstSetted;
			}

			public List<ResultBean> getResult() {
				return result;
			}

			public void setResult(List<ResultBean> result) {
				this.result = result;
			}

			public List<?> getOrderbyMap() {
				return orderbyMap;
			}

			public void setOrderbyMap(List<?> orderbyMap) {
				this.orderbyMap = orderbyMap;
			}

			public static class ResultBean {
				private Object code;
				private Object remarks;
				private Object createBy;
				private Object createByName;
				private String createDate;
				private Object updateBy;
				private Object updateByName;
				private String updateDate;
				private String delFlag;
				private String id;
				private String recordId;
				private String subjectCode;
				private String finalCusId;
				private String finalCusName;
				private String money;
				private String percent;
				private String status;
				private String description;
				private String createDateFormat;
				private boolean deleted;
				private String type_str;

				public Object getCode() {
					return code;
				}

				public void setCode(Object code) {
					this.code = code;
				}

				public Object getRemarks() {
					return remarks;
				}

				public void setRemarks(Object remarks) {
					this.remarks = remarks;
				}

				public Object getCreateBy() {
					return createBy;
				}

				public void setCreateBy(Object createBy) {
					this.createBy = createBy;
				}

				public Object getCreateByName() {
					return createByName;
				}

				public void setCreateByName(Object createByName) {
					this.createByName = createByName;
				}

				public String getCreateDate() {
					return createDate;
				}

				public void setCreateDate(String createDate) {
					this.createDate = createDate;
				}

				public Object getUpdateBy() {
					return updateBy;
				}

				public void setUpdateBy(Object updateBy) {
					this.updateBy = updateBy;
				}

				public Object getUpdateByName() {
					return updateByName;
				}

				public void setUpdateByName(Object updateByName) {
					this.updateByName = updateByName;
				}

				public String getUpdateDate() {
					return updateDate;
				}

				public void setUpdateDate(String updateDate) {
					this.updateDate = updateDate;
				}

				public String getDelFlag() {
					return delFlag;
				}

				public void setDelFlag(String delFlag) {
					this.delFlag = delFlag;
				}

				public String getId() {
					return id;
				}

				public void setId(String id) {
					this.id = id;
				}

				public String getRecordId() {
					return recordId;
				}

				public void setRecordId(String recordId) {
					this.recordId = recordId;
				}

				public String getSubjectCode() {
					return subjectCode;
				}

				public void setSubjectCode(String subjectCode) {
					this.subjectCode = subjectCode;
				}

				public String getFinalCusId() {
					return finalCusId;
				}

				public void setFinalCusId(String finalCusId) {
					this.finalCusId = finalCusId;
				}

				public String getFinalCusName() {
					return finalCusName;
				}

				public void setFinalCusName(String finalCusName) {
					this.finalCusName = finalCusName;
				}

				public String getMoney() {
					return money;
				}

				public void setMoney(String money) {
					this.money = money;
				}

				public String getPercent() {
					return percent;
				}

				public void setPercent(String percent) {
					this.percent = percent;
				}

				public String getStatus() {
					return status;
				}

				public void setStatus(String status) {
					this.status = status;
				}

				public String getDescription() {
					return description;
				}

				public void setDescription(String description) {
					this.description = description;
				}

				public String getCreateDateFormat() {
					return createDateFormat;
				}

				public void setCreateDateFormat(String createDateFormat) {
					this.createDateFormat = createDateFormat;
				}

				public boolean isDeleted() {
					return deleted;
				}

				public void setDeleted(boolean deleted) {
					this.deleted = deleted;
				}

				public String getType_str() {
					return type_str;
				}

				public void setType_str(String type_str) {
					this.type_str = type_str;
				}
			}
		}
	}

	public static IncomeDetailModel parseJson(String json) {
		Gson gson = new Gson();
		IncomeDetailModel model = gson.fromJson(json, IncomeDetailModel.class);
		return model;
	}
}
