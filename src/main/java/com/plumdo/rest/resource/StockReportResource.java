package com.plumdo.rest.resource;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.plumdo.domain.StockDetail;
import com.plumdo.domain.StockGold;
import com.plumdo.domain.StockInfo;
import com.plumdo.jpa.Criteria;
import com.plumdo.jpa.Restrictions;
import com.plumdo.repository.StockDetailRepository;
import com.plumdo.repository.StockGoldRepository;
import com.plumdo.repository.StockInfoRepository;
import com.plumdo.rest.AbstractResource;
import com.plumdo.rest.PageResponse;
import com.plumdo.utils.ObjectUtils;

/**
 * 
 * 
 * @author wengwh
 * @Date 2017-02-06
 * 
 */
@RestController
public class StockReportResource extends AbstractResource {

	@Autowired
	private StockGoldRepository stockGoldRepository;
	@Autowired
	private StockInfoRepository stockInfoRepository;
	@Autowired
	private StockDetailRepository stockDetailRepository;

	
	@GetMapping("/stock-reports/stock-details")
	@ResponseStatus(HttpStatus.OK)
	public PageResponse<StockDetail> getStockDetails(@RequestParam Map<String, String> allRequestParams) {
		Criteria<StockDetail> criteria = new Criteria<StockDetail>();
		criteria.add(Restrictions.like("stockCode", allRequestParams.get("stockCode"), true));
		criteria.add(Restrictions.like("stockName", allRequestParams.get("stockName"), true));
		criteria.add(Restrictions.gte("stockDate", ObjectUtils.convertToDate(allRequestParams.get("stockDateBegin")), true));
		criteria.add(Restrictions.lte("stockDate", ObjectUtils.convertToDate(allRequestParams.get("stockDateEnd")), true));
		return createPageResponse(stockDetailRepository.findAll(criteria, getPageable(allRequestParams)));
	}
	
	@GetMapping("/stock-reports/stock-golds")
	@ResponseStatus(HttpStatus.OK)
	public PageResponse<StockGold> getStockGolds(@RequestParam Map<String, String> allRequestParams) {
		Criteria<StockGold> criteria = new Criteria<StockGold>();
		criteria.add(Restrictions.like("stockCode", allRequestParams.get("stockCode"), true));
		criteria.add(Restrictions.like("stockName", allRequestParams.get("stockName"), true));
		criteria.add(Restrictions.gte("stockDate", ObjectUtils.convertToDate(allRequestParams.get("stockDateBegin")), true));
		criteria.add(Restrictions.lte("stockDate", ObjectUtils.convertToDate(allRequestParams.get("stockDateEnd")), true));
		return createPageResponse(stockGoldRepository.findAll(criteria, getPageable(allRequestParams)));
	}

	@GetMapping("/stock-reports/stock-weaks")
	@ResponseStatus(HttpStatus.OK)
	public PageResponse<StockInfo> getStockWeaks(@RequestParam Map<String, String> allRequestParams) {
		Date stockDateBegin = new Date();
		if (ObjectUtils.isNotEmpty(allRequestParams.get("stockDateBegin"))) {
			stockDateBegin = ObjectUtils.convertToDate(allRequestParams.get("stockDateBegin"));
		}

		Date stockDateEnd = new Date();
		if (ObjectUtils.isNotEmpty(allRequestParams.get("stockDateEnd"))) {
			stockDateEnd = ObjectUtils.convertToDate(allRequestParams.get("stockDateEnd"));
		}

		BigDecimal stockRange = BigDecimal.ZERO;
		if (ObjectUtils.isNotEmpty(allRequestParams.get("stockRange"))) {
			stockRange = ObjectUtils.convertToBigDecimal(allRequestParams.get("stockRange"));
		}

		return createPageResponse(stockInfoRepository.findWeakStocks(stockDateBegin, stockDateEnd, stockRange, getPageable(allRequestParams)));
	}

}