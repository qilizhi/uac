package com.mlx.uac.common;

public class AjaxResult {

	private String code;

	private String msg;

	private Object result;

	public AjaxResult() {

	}

	public AjaxResult(String code, String message, Object result) {
		this.code = code;
		this.msg = message;
		this.result = result;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}