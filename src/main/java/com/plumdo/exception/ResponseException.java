package com.plumdo.exception;

/**
 * 自定义异常，包含返回类
 * 
 * @author wengwenhui
 * 
 */
public class ResponseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	protected ErrorResponse errorResponse;

	public ResponseException(ErrorResponse errorResponse) {
		super(errorResponse.getMsg());
		this.errorResponse = errorResponse;
	}

	public ResponseException(ErrorResponse errorResponse, Throwable cause) {
		super(errorResponse.getMsg(), cause);
		this.errorResponse = errorResponse;
	}

	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}
}
