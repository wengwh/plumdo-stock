package com.plumdo.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;


/**
 * 异常工厂类
 * 
 * @author wengwenhui
 * 
 */
public class ExceptionFactory {

	private MessageSource messageSource;

	public ExceptionFactory(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/** 默认local **/
	private static final Locale defaultLocal = Locale.CHINA;

	public String getResource(String code) {
		return messageSource.getMessage(code, null, defaultLocal);
	}

	public String getResource(String code, Object... arg) {
		return messageSource.getMessage(code, arg, defaultLocal);
	}

	public ErrorResponse newResponse(String ret, String msg, Object data) {
		return new ErrorResponse(ret, msg, data);
	}

	public ErrorResponse newTranslateDataResponse(String ret, String msg, Object data, Object... arg) {
		return new ErrorResponse(getResource(ret), getResource(msg, arg), data);
	}

	public ErrorResponse newTranslateResponse(String ret, String msg, Object... arg) {
		return new ErrorResponse(getResource(ret), getResource(msg, arg), null);
	}

	
	public ErrorResponse createInternalError() {
		return newTranslateResponse("ERROR", "ERROR_INFO");
	}
	
	public void throwInternalError() {
		throw new ResponseException(newTranslateResponse("ERROR", "ERROR_INFO"));
	}

	public void throwArgumentOrderInvalid(String order) {
		throw new ResponseException(newTranslateResponse("ARGUMENT_ORDER_INVALID", "ARGUMENT_ORDER_INVALID_INFO",order));
	}

	public void throwObjectNotFound(int id) {
		throw new ResponseException(newTranslateResponse("OBJECT_NOT_FOUND", "OBJECT_NOT_FOUND_INFO",id));
	}


}
