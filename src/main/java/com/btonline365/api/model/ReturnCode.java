package com.btonline365.api.model;

public enum ReturnCode {
	
	SUCCESS("1", "处理成功"), 
	FAIL("-1", "处理失败"),
	SYS_ERROR("-2", "系统错误"),
	PARAMS_ERROR("-3", "请求参数错误");
	
	private String code;
	private String message;

	ReturnCode(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
