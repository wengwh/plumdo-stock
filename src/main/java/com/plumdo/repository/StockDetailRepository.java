package com.plumdo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.plumdo.domain.StockDetail;

public interface StockDetailRepository extends BaseRepository<StockDetail, Integer> {
	@Query("select a from StockDetail a where  a.stockDate=?1 and EXISTS (" 
			+ " select b from StockDetail b where a.stockCode = b.stockCode and b.highestPrice<a.latestPrice  and (b.stockNum*1.3)<a.stockNum"
			+ "  and b.stockDate=?2)")
	List<StockDetail> findStockGolds(Date collectStockDate, Date compareStockDate);
	
	StockDetail findFirstByStockCodeAndStockDate(String stockCode,Date stockDate);
	

	@Query("delete from StockDetail where stockDate=?1 ")
	@Transactional
	@Modifying
	int deleteStockDetails(Date stockDate);

}