package com.plumdo.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plumdo.exception.ExceptionFactory;

public abstract class AbstractResource {

	@Autowired
	protected ExceptionFactory exceptionFactory;
	@Autowired
	protected ObjectMapper objectMapper;

	
	protected Pageable getPageable(Map<String, String> requestParams) {
		int page = 1;
		if (requestParams.containsKey("page")) {
			page = Integer.parseInt(requestParams.get("page"));
		}
		int size = 1;
		if (requestParams.containsKey("size")) {
			size = Integer.parseInt(requestParams.get("size"));
		}
		Order order = null;
		if (requestParams.containsKey("order")) {
			String orderBy = requestParams.get("order");
			 if(orderBy.startsWith("-")){
				 order = new Order(Direction.DESC, orderBy.substring(1));
			 }else{
				 order = new Order(Direction.ASC, orderBy);
			 }
		}
		
		if (order==null) {
			return new PageRequest(page, size);
		} else {
			return new PageRequest(page, size, new Sort(order));
		}
	}
	
	protected <T> PageResponse<T> createPageResponse(Page<T> page) {
		PageResponse<T> pageResponse = new PageResponse<T>();
		pageResponse.setData(page.getContent());
		pageResponse.setPageTotal(page.getTotalPages());
		pageResponse.setPageNum(page.getNumber() + 1);
		pageResponse.setPageSize(page.getSize());
		pageResponse.setDataTotal(page.getTotalElements());
		pageResponse.setStartNum(page.getNumber() * page.getSize() + 1);
		pageResponse.setEndNum(page.isLast() ? page.getTotalElements() : (page.getNumber() + 1) * page.getSize());
		return pageResponse;
	}

}
