package com.plumdo.rest.resource;

import java.util.ArrayList;
import java.util.List;
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

import com.plumdo.domain.StockHotPlate;
import com.plumdo.jpa.Criteria;
import com.plumdo.jpa.Restrictions;
import com.plumdo.repository.StockHotPlateRepository;
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
public class StockHotPlateResource extends AbstractResource {

	@Autowired
	private StockHotPlateRepository stockHotPlateRepository;

	private StockHotPlate getStockHotPlateFromRequest(int id) {
		StockHotPlate stockHotPlate = stockHotPlateRepository.findOne(id);
		if (stockHotPlate == null) {
			exceptionFactory.throwObjectNotFound(id);
		}
		return stockHotPlate;
	}

	@GetMapping("/stock-hot-plates")
	@ResponseStatus(HttpStatus.OK)
	public PageResponse<StockHotPlate> getStockHotPlates(@RequestParam Map<String, String> allRequestParams) {
		Criteria<StockHotPlate> criteria = new Criteria<StockHotPlate>();
		criteria.add(Restrictions.like("plateName", allRequestParams.get("plateName"), true));
		criteria.add(Restrictions.gte("collectTime", ObjectUtils.convertToDate(allRequestParams.get("collectTimeBegin")), true));
		criteria.add(Restrictions.lte("collectTime", ObjectUtils.convertToDate(allRequestParams.get("collectTimeEnd")), true));
		return createPageResponse(stockHotPlateRepository.findAll(criteria, getPageable(allRequestParams)));
	}

	@PostMapping("/stock-hot-plates")
	@ResponseStatus(HttpStatus.CREATED)
	public StockHotPlate createStockHotPlate(@RequestBody StockHotPlate stockHotPlateRequest) {
		return stockHotPlateRepository.save(stockHotPlateRequest);
	}

	@DeleteMapping("/stock-hot-plates/{hotPlateId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteStockHotPlate(@PathVariable Integer hotPlateId) {
		StockHotPlate stockHotPlate = getStockHotPlateFromRequest(hotPlateId);
		stockHotPlateRepository.delete(stockHotPlate);
	}
	
	@PostMapping("/stock-hot-plates/batch-delete")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void batchDeleteStockHotPlate(@RequestBody List<Integer> hotPlateIds) {
		List<StockHotPlate> deleteStockHotPlates = new ArrayList<>();
		for(Integer hotPlateId : hotPlateIds) {
			deleteStockHotPlates.add(getStockHotPlateFromRequest(hotPlateId));
		}
		stockHotPlateRepository.delete(deleteStockHotPlates);
	}

	@PutMapping("/stock-hot-plates/{hotPlateId}")
	@ResponseStatus(HttpStatus.OK)
	public StockHotPlate updateStockHotPlate(@PathVariable Integer hotPlateId, @RequestBody StockHotPlate stockHotPlateRequest) {
		StockHotPlate stockHotPlate = getStockHotPlateFromRequest(hotPlateId);
		stockHotPlate.setCollectTime(stockHotPlateRequest.getCollectTime());
		stockHotPlate.setPlateName(stockHotPlateRequest.getPlateName());
		return stockHotPlateRepository.save(stockHotPlate);
	}

	@GetMapping("/stock-hot-plates/{hotPlateId}")
	@ResponseStatus(HttpStatus.OK)
	public StockHotPlate getStockHotPlate(@PathVariable Integer hotPlateId) {
		return getStockHotPlateFromRequest(hotPlateId);
	}

}