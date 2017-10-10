package com.plumdo.config;


import java.text.DateFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plumdo.exception.ExceptionFactory;

/**
 * 程序相关配置
 * 
 * @author wengwenhui
 * 
 */
@Configuration
public class ApplicationConfig {
	@Autowired
	private MessageSource messageSource;

	@Bean
	public ExceptionFactory exceptionFactory() {
		return new ExceptionFactory(messageSource);
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.CHINA));
		return mapper;
	}
	
}
