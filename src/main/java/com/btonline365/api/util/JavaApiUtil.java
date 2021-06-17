package com.btonline365.api.util;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.btonline365.api.model.ReturnCode;
import com.btonline365.api.model.ReturnData;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class JavaApiUtil {
	/**
	 * getBizToken 认证服务获取Biz_token
	 * @param url 请求服务的地址
	 * @param reqParam 请求服务的参数,JSONObject
	 * @param api_key 请求服务的唯一key(服务端提供)
	 * @param api_secret 请求参数签名秘钥(服务端提供)
	 * @return 返回ReturnData code:1 成功 其他：失败
	 */
	public static ReturnData getBizToken(String url,JSONObject reqParam,String api_key,String api_secret) {
		if(StringUtil.isEmpty(url)) {
			return new ReturnData(ReturnCode.PARAMS_ERROR.getCode(),"请求服务【URL】地址为空");
		}
		url=url+"/getBizToken";
		if(StringUtil.isEmpty(reqParam)) {
			return new ReturnData(ReturnCode.PARAMS_ERROR.getCode(),"请求参数为空,请检查请求参数");
		}
		if(StringUtil.isEmpty(api_key)) {
			return new ReturnData(ReturnCode.PARAMS_ERROR.getCode(),"服务标识【api_key】为空");
		}
		reqParam.put("api_key", api_key);
		if(StringUtil.isEmpty(api_secret)) {
			return new ReturnData(ReturnCode.PARAMS_ERROR.getCode(),"签名秘钥【api_secret】为空");
		}
//		if(StringUtil.isEmpty(signType)) {
//			return new ReturnData(ReturnCode.PARAMS_ERROR.getCode(),"签名方式【signType】为空");
//		}
//		if(!SignUtil.HMACSHA256.equals(signType)&&!SignUtil.MD5.equals(signType)) {
//			return new ReturnData(ReturnCode.PARAMS_ERROR.getCode(),"签名方式【signType】错误");
//		}
		try {
			String generateMd5Signature = SignUtil.generateSignature(reqParam, api_secret,SignUtil.HMACSHA256);
			reqParam.put(SignUtil.FIELD_SIGN, generateMd5Signature);
		} catch (Exception e1) {
			log.error("生成签名异常"+e1);
			return new ReturnData(ReturnCode.PARAMS_ERROR.getCode(),"生成签名异常");
		}
		String sendPost=null;
		try {
			JSONObject reqJson=new JSONObject();
			reqJson.put(SignUtil.FIELD_SIGN_TYPE, SignUtil.HMACSHA256);
			reqJson.put("reqParam",reqParam);
			sendPost = HttpClientUtil.sendHttpPost2(url, reqJson.toJSONString());
			JSONObject parseObject = JSONObject.parseObject(sendPost);
			String resCode = parseObject.getString("code");
			if(ReturnCode.SUCCESS.getCode().equals(resCode)) {
				String resUrl = parseObject.getString("result");
				return new ReturnData(ReturnCode.SUCCESS.getCode(), resUrl, ReturnCode.SUCCESS.getMessage());
			}else {
				return new ReturnData(parseObject.getString("code"), parseObject.getString("message"));
			}
		} catch (IOException e) {
			log.error("请求API服务异常:"+e);
			return new ReturnData(ReturnCode.FAIL.getCode(), "请求API服务异常");
		}
	}
	/**
	 * getResult 认证服务获取认证结果
	 * @param url 请求服务的地址
	 * @param reqParam 请求服务的参数,JSONObject
	 * @param api_key 请求服务的唯一key(服务端提供)
	 * @param api_secret 请求参数签名秘钥(服务端提供)
	 * @return 返回ReturnData code:1 成功 其他：失败
	 */
	public static ReturnData getResult(String url,JSONObject reqParam,String api_key,String api_secret) {
		if(StringUtil.isEmpty(url)) {
			return new ReturnData(ReturnCode.PARAMS_ERROR.getCode(),"请求服务【URL】地址为空");
		}
		url=url+"/getResult";
		if(StringUtil.isEmpty(reqParam)) {
			return new ReturnData(ReturnCode.PARAMS_ERROR.getCode(),"请求参数为空,请检查请求参数");
		}
		if(StringUtil.isEmpty(api_key)) {
			return new ReturnData(ReturnCode.PARAMS_ERROR.getCode(),"服务标识【api_key】为空");
		}
		reqParam.put("api_key", api_key);
		if(StringUtil.isEmpty(api_secret)) {
			return new ReturnData(ReturnCode.PARAMS_ERROR.getCode(),"签名秘钥【api_secret】为空");
		}
		try {
			String generateMd5Signature = SignUtil.generateSignature(reqParam, api_secret,SignUtil.HMACSHA256);
			reqParam.put(SignUtil.FIELD_SIGN, generateMd5Signature);
		} catch (Exception e1) {
			log.error("生成签名异常"+e1);
			return new ReturnData(ReturnCode.PARAMS_ERROR.getCode(),"生成签名异常");
		}
		String sendPost=null;
		try {
			JSONObject reqJson=new JSONObject();
			reqJson.put(SignUtil.FIELD_SIGN_TYPE, SignUtil.HMACSHA256);
			reqJson.put("reqParam",reqParam);
			sendPost = HttpClientUtil.sendHttpPost2(url, reqJson.toJSONString());
			JSONObject parseObject = JSONObject.parseObject(sendPost);
			String resCode = parseObject.getString("code");
			if(ReturnCode.SUCCESS.getCode().equals(resCode)) {
				String resUrl = parseObject.getString("result");
				return new ReturnData(ReturnCode.SUCCESS.getCode(), resUrl, ReturnCode.SUCCESS.getMessage());
			}else {
				return new ReturnData(parseObject.getString("code"), parseObject.getString("message"));
			}
		} catch (IOException e) {
			log.error("请求API服务异常:"+e);
			return new ReturnData(ReturnCode.FAIL.getCode(), "请求API服务异常");
		}
	}
	public static void main(String[] args) {
		String url="http://localhost:8083/jqfw_web/api";
		JSONObject reqParam=new JSONObject();
		reqParam.put("nsrmc", "长沙优卡信息科技有限公司");
		reqParam.put("fryddh", "18301388371");
		reqParam.put("nsrsbh", "91430100MA4L5WWM18");
		reqParam.put("area_id", "430000");
		reqParam.put("biz_no", StringUtil.getStringRandom(16));
		reqParam.put("return_url", "https://qyxy.btonline365.com/qyrz_result.html");
		reqParam.put("notify_url", "http://localhost:8081/index");
		String api_key="RafWN914WNpbID4B";
		String api_secret="vYpXx3OJ9JvMlPJPoQevnCsvR6K02ygW";
		ReturnData qyfwInit = JavaApiUtil.getBizToken(url, reqParam,api_key, api_secret);
		System.out.println("请求认证服务返回code:"+qyfwInit.getCode());
		System.out.println("请求认证服务返回result:"+qyfwInit.getResult());
		System.out.println("请求认证服务返回message:"+qyfwInit.getMessage());
	}
}
