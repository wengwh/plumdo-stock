package com.plumdo.rest.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.plumdo.constant.ConfigConstant;
import com.plumdo.domain.SystemParameter;
import com.plumdo.repository.SystemParameterRepository;
import com.plumdo.rest.AbstractResource;

/**
 * 
 * 
 * @author wengwh
 * @Date 2017-02-06
 * 
 */
@RestController
public class WeiboResource extends AbstractResource {
	@Autowired
	private SystemParameterRepository systemParameterRepository;
	@Autowired
	private RestTemplate restTemplate;

	private SystemParameter getSystemParameterFromRequest(String parameterName) {
		SystemParameter systemParameter = systemParameterRepository.findFirstByParameterName(parameterName);
		if (systemParameter == null) {
			exceptionFactory.throwObjectNotFound(parameterName);
		}
		return systemParameter;
	}

	@PutMapping("/weibos/parameters")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Transactional
	public void updateWeiboSystemParameter(@RequestBody Map<String, String> requestMap) {
		SystemParameter accessTokenParameter = getSystemParameterFromRequest(ConfigConstant.PARAM_WEIBO_ACCESS_TOKEN);
		accessTokenParameter.setParameterValue(requestMap.get("accessToken"));
		systemParameterRepository.save(accessTokenParameter);

		SystemParameter shareUrlParameter = getSystemParameterFromRequest(ConfigConstant.PARAM_WEIBO_SHARE_URL);
		shareUrlParameter.setParameterValue(requestMap.get("shareUrl"));
		systemParameterRepository.save(shareUrlParameter);
	}

	@GetMapping("/weibos/parameters")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, String> getWeiboSystemParameter() {
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("accessToken", getSystemParameterFromRequest(ConfigConstant.PARAM_WEIBO_ACCESS_TOKEN).getParameterValue());
		resultMap.put("shareUrl", getSystemParameterFromRequest(ConfigConstant.PARAM_WEIBO_SHARE_URL).getParameterValue());
		return resultMap;
	}

	@PostMapping("/weibos/send")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void sendWeibo(@RequestBody Map<String, String> requestMap) throws InterruptedException {
		StringBuffer weiBoContent = new StringBuffer(requestMap.get("content"));
		List<String> statusList = new ArrayList<String>();
		while (weiBoContent.length() > 0) {
			String status = "";
			if (weiBoContent.length() > 120) {
				status = weiBoContent.substring(0, weiBoContent.indexOf(",", 120));
				weiBoContent.delete(0, weiBoContent.indexOf(",", 120) + 1);
			} else {
				status = weiBoContent.toString();
				weiBoContent.delete(0, weiBoContent.length());
			}
			statusList.add(status);
		}
		SystemParameter weiboAccessToken = getSystemParameterFromRequest(ConfigConstant.PARAM_WEIBO_ACCESS_TOKEN);
		SystemParameter weiboShareUrl = getSystemParameterFromRequest(ConfigConstant.PARAM_WEIBO_SHARE_URL);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		for (int i = statusList.size(); i > 0; i--) {
			MultiValueMap<String, String> dataMap = new LinkedMultiValueMap<String, String>();
			dataMap.add("access_token", weiboAccessToken.getParameterValue());
			dataMap.add("status", statusList.get(i - 1) + weiboShareUrl.getParameterValue());
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(dataMap, headers);
			restTemplate.postForEntity(ConfigConstant.SEND_WEIBO_URL, request, Map.class);
			if (i > 1) {
				Thread.sleep(5000L);
			}
		}
	}

}