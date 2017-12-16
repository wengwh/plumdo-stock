package com.plumdo.rest.resource;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.plumdo.domain.SystemParameter;
import com.plumdo.jpa.Criteria;
import com.plumdo.jpa.Restrictions;
import com.plumdo.repository.SystemParameterRepository;
import com.plumdo.rest.AbstractResource;
import com.plumdo.rest.PageResponse;

/**
 * 
 * 
 * @author wengwh
 * @Date 2017-02-06
 * 
 */
@RestController
public class SystemParameterResource extends AbstractResource {

	@Autowired
	private SystemParameterRepository systemParameterRepository;

	private SystemParameter getSystemParameterFromRequest(String parameterName) {
		SystemParameter systemParameter = systemParameterRepository.findFirstByParameterName(parameterName);
		if (systemParameter == null) {
			exceptionFactory.throwObjectNotFound(parameterName);
		}
		return systemParameter;
	}

	@GetMapping("/system-parameters")
	@ResponseStatus(HttpStatus.OK)
	public PageResponse<SystemParameter> getSystemParameters(@RequestParam Map<String, String> allRequestParams) {
		Criteria<SystemParameter> criteria = new Criteria<SystemParameter>();
		criteria.add(Restrictions.eq("parameterName", allRequestParams.get("parameterName"), true));
		return createPageResponse(systemParameterRepository.findAll(criteria, getPageable(allRequestParams)));
	}

	@PutMapping("/system-parameters/{parameterName}")
	@ResponseStatus(HttpStatus.OK)
	public SystemParameter updateSystemParameter(@PathVariable String parameterName, @RequestBody SystemParameter systemParameterRequest) {
		SystemParameter systemParameter = getSystemParameterFromRequest(parameterName);
		systemParameter.setParameterValue(systemParameterRequest.getParameterValue());
		return systemParameterRepository.save(systemParameter);
	}

	@GetMapping("/system-parameters/{parameterName}")
	@ResponseStatus(HttpStatus.OK)
	public SystemParameter getSystemParameter(@PathVariable String parameterName) {
		return getSystemParameterFromRequest(parameterName);
	}

}