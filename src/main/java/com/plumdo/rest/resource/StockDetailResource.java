package com.plumdo.rest.resource;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.plumdo.domain.StockDetail;
import com.plumdo.jpa.Criteria;
import com.plumdo.jpa.Restrictions;
import com.plumdo.repository.StockDetailRepository;
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
public class StockDetailResource extends AbstractResource {

	@Autowired
	private StockDetailRepository stockDetailRepository;

	private StockDetail getStockDetailFromRequest(int id) {
		StockDetail stockDetail = stockDetailRepository.findOne(id);
		if (stockDetail == null) {
			exceptionFactory.throwObjectNotFound(id);
		}
		return stockDetail;
	}

	@GetMapping("/stock-details")
	@ResponseStatus(HttpStatus.OK)
	public PageResponse<StockDetail> getStockDetails(@RequestParam Map<String, String> allRequestParams) {
		Criteria<StockDetail> criteria = new Criteria<StockDetail>();
		criteria.add(Restrictions.like("stockCode", allRequestParams.get("stockCode"), true));
		criteria.add(Restrictions.like("stockName", allRequestParams.get("stockName"), true));
		criteria.add(Restrictions.gte("stockDate", allRequestParams.get("stockDateBegin"), true));
		criteria.add(Restrictions.lte("stockDate", allRequestParams.get("stockDateEnd"), true));
		return createPageResponse(stockDetailRepository.findAll(criteria, getPageable(allRequestParams)));
	}

	@PostMapping("/stock-details")
	@ResponseStatus(HttpStatus.CREATED)
	public StockDetail createStockDetail(@RequestBody StockDetail stockDetailRequest) {
		return stockDetailRepository.save(stockDetailRequest);
	}

	@DeleteMapping("/stock-details/{detailId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteStockDetail(@PathVariable Integer detailId) {
		StockDetail stockDetail = getStockDetailFromRequest(detailId);
		stockDetailRepository.delete(stockDetail);
	}

	@PutMapping("/stock-details/{detailId}")
	@ResponseStatus(HttpStatus.OK)
	public StockDetail updateStockDetail(@PathVariable Integer detailId, @RequestBody StockDetail stockDetailRequest) {
		StockDetail stockDetail = getStockDetailFromRequest(detailId);
		stockDetail.setStockCode(stockDetailRequest.getStockCode());
		stockDetail.setStockName(stockDetailRequest.getStockName());
		stockDetail.setStockMoney(stockDetailRequest.getStockMoney());
		stockDetail.setStockNum(stockDetailRequest.getStockNum());
		stockDetail.setStockDate(stockDetailRequest.getStockDate());
		stockDetail.setBeginPrice(stockDetailRequest.getBeginPrice());
		stockDetail.setEndPrice(stockDetailRequest.getEndPrice());
		stockDetail.setHighestPrice(stockDetailRequest.getHighestPrice());
		stockDetail.setLatestPrice(stockDetailRequest.getLatestPrice());
		return stockDetailRepository.save(stockDetail);
	}

	@GetMapping("/stock-details/{detailId}")
	@ResponseStatus(HttpStatus.OK)
	public StockDetail getStockDetail(@PathVariable Integer detailId) {
		return getStockDetailFromRequest(detailId);
	}

}