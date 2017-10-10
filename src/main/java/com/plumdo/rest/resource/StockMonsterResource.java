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

import com.plumdo.domain.StockMonster;
import com.plumdo.jpa.Criteria;
import com.plumdo.jpa.Restrictions;
import com.plumdo.repository.StockMonsterRepository;
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
public class StockMonsterResource extends AbstractResource {

	@Autowired
	private StockMonsterRepository stockMonsterRepository;

	private StockMonster getStockMonsterFromRequest(int id) {
		StockMonster stockMonster = stockMonsterRepository.findOne(id);
		if (stockMonster == null) {
			exceptionFactory.throwObjectNotFound(id);
		}
		return stockMonster;
	}

	@GetMapping("/stock-monsters")
	@ResponseStatus(HttpStatus.OK)
	public PageResponse<StockMonster> getStockMonsters(@RequestParam Map<String, String> allRequestParams) {
		Criteria<StockMonster> criteria = new Criteria<StockMonster>();
		criteria.add(Restrictions.like("stockCode", allRequestParams.get("stockCode"), true));
		criteria.add(Restrictions.like("stockName", allRequestParams.get("stockName"), true));
		return createPageResponse(stockMonsterRepository.findAll(criteria, getPageable(allRequestParams)));
	}

	@PostMapping("/stock-monsters")
	@ResponseStatus(HttpStatus.CREATED)
	public StockMonster createStockMonster(@RequestBody StockMonster stockMonsterRequest) {
		return stockMonsterRepository.save(stockMonsterRequest);
	}

	@DeleteMapping("/stock-monsters/{monsterId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteStockMonster(@PathVariable Integer monsterId) {
		StockMonster stockMonster = getStockMonsterFromRequest(monsterId);
		stockMonsterRepository.delete(stockMonster);
	}

	@PutMapping("/stock-monsters/{monsterId}")
	@ResponseStatus(HttpStatus.OK)
	public StockMonster updateStockMonster(@PathVariable Integer monsterId, @RequestBody StockMonster stockMonsterRequest) {
		StockMonster stockMonster = getStockMonsterFromRequest(monsterId);
		stockMonster.setStockCode(stockMonsterRequest.getStockCode());
		stockMonster.setStockName(stockMonsterRequest.getStockName());
		return stockMonsterRepository.save(stockMonster);
	}

	@GetMapping("/stock-monsters/{monsterId}")
	@ResponseStatus(HttpStatus.OK)
	public StockMonster getStockMonster(@PathVariable Integer monsterId) {
		return getStockMonsterFromRequest(monsterId);
	}

}