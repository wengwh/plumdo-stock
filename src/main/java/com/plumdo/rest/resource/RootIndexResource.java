package com.plumdo.rest.resource;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.plumdo.rest.AbstractResource;

/**
 * 
 * 
 * @author wengwh
 * @Date 2017-02-06
 * 
 */
@Controller
public class RootIndexResource extends AbstractResource {

	@RequestMapping("/")
	public String index() {
		return "/index.html";
	}


}