package com.plumdo.test;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.plumdo.StockApplication;
import com.plumdo.constant.ConfigConstant;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = StockApplication.class)
@WebAppConfiguration
public class TestServicesTest {

	@Autowired
	RestTemplate restTemplate;

	@Test
	public void testShow() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("access_token", "2.00xgGTSEFWN8wDd3c6c979bcw72mPC");
		map.add("status", "xxxxxxxxcxcsdsdxxxxxxxhttp://www.plumdo.com");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		restTemplate.postForEntity(ConfigConstant.SEND_WEIBO_URL, request, Map.class);
	}

}