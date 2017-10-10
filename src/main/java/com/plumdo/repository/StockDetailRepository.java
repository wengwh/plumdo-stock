package com.plumdo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.plumdo.domain.StockDetail;

public interface StockDetailRepository extends BaseRepository<StockDetail, Integer> {
	@Query("select a from StockDetail a where EXISTS (" 
			+ " select b from StockDetail b where a.stockCode = b.stockCode and b.highestPrice<a.latestPrice  and (b.stockNum*1.3)<a.stockNum"
			+ " and a.stockDate=?1 and b.stockDate=?2)")
	List<StockDetail> findStockGolds(Date collectStockDate, Date compareStockDate);

}