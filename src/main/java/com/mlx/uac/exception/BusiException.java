package com.mlx.uac.exception;

/**
 * 异常处理
 * @author chenfh
 * 2016年4月11日 上午10:08:26
 */
public class BusiException extends RuntimeException {

	private static final long serialVersionUID = -8602600278035087259L;

	private String code;

	private String msg;

	public BusiException(String code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

	public BusiException(String code, String msg, Throwable e) {
		super(msg, e);
		this.code = code;
		this.msg = msg;
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

}
