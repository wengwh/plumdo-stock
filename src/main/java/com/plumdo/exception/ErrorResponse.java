package com.plumdo.exception;


/**
 * 结果实体类
 * 
 * @author wengwenhui
 * 
 */
public class ErrorResponse {
	private String ret;
	private String msg;
	private Object data;

	public ErrorResponse() {

	}

	public ErrorResponse(String ret, String msg, Object data) {
		this.ret = ret;
		this.msg = msg;
		this.data = data;
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
