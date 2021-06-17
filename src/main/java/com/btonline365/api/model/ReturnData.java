package com.btonline365.api.model;

import java.io.Serializable;

public class ReturnData implements Serializable{
	
	private static final long serialVersionUID = -9215026824783327793L;

	private String code;
	
	private String result;
	
	private String message;
	
	public ReturnData(){
		this.code = ReturnCode.SUCCESS.getCode();
	}
	
	public ReturnData(String code){
		this.code = code;
	}
	
	public ReturnData(String code, String message){
		this.code = code;
		this.message = message;
	}
	
	public ReturnData(String code, String result, String message){
		this.code = code;
		this.result = result;
		this.message = message;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
