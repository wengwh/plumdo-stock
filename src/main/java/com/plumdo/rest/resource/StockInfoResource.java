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

import com.plumdo.domain.StockInfo;
import com.plumdo.jpa.Criteria;
import com.plumdo.jpa.Restrictions;
import com.plumdo.repository.StockInfoRepository;
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
public class StockInfoResource extends AbstractResource {

	@Autowired
	private StockInfoRepository stockInfoRepository;

	private StockInfo getStockInfoFromRequest(int id) {
		StockInfo stockInfo = stockInfoRepository.findOne(id);
		if (stockInfo == null) {
			exceptionFactory.throwObjectNotFound(id);
		}
		return stockInfo;
	}

	@GetMapping("/stock-infos")
	@ResponseStatus(HttpStatus.OK)
	public PageResponse<StockInfo> getStockInfos(@RequestParam Map<String, String> allRequestParams) {
		Criteria<StockInfo> criteria = new Criteria<StockInfo>();
		criteria.add(Restrictions.like("stockCode", allRequestParams.get("stockCode"), true));
		criteria.add(Restrictions.like("stockName", allRequestParams.get("stockName"), true));
		return createPageResponse(stockInfoRepository.findAll(criteria, getPageable(allRequestParams)));
	}

	@PostMapping("/stock-infos")
	@ResponseStatus(HttpStatus.CREATED)
	public StockInfo createStockInfo(@RequestBody StockInfo stockInfoRequest) {
		return stockInfoRepository.save(stockInfoRequest);
	}

	@DeleteMapping("/stock-infos/{stockId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteStockInfo(@PathVariable Integer stockId) {
		StockInfo stockInfo = getStockInfoFromRequest(stockId);
		stockInfoRepository.delete(stockInfo);
	}

	@PutMapping("/stock-infos/{stockId}")
	@ResponseStatus(HttpStatus.OK)
	public StockInfo updateStockInfo(@PathVariable Integer stockId, @RequestBody StockInfo stockInfoRequest) {
		StockInfo stockInfo = getStockInfoFromRequest(stockId);
		stockInfo.setStockCode(stockInfoRequest.getStockCode());
		stockInfo.setStockName(stockInfoRequest.getStockName());
		return stockInfoRepository.save(stockInfo);
	}

	@GetMapping("/stock-infos/{stockId}")
	@ResponseStatus(HttpStatus.OK)
	public StockInfo getStockInfo(@PathVariable Integer stockId) {
		return getStockInfoFromRequest(stockId);
	}

}