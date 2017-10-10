package com.plumdo.repository;


import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.plumdo.domain.StockInfo;

public interface StockInfoRepository extends BaseRepository<StockInfo, Integer> {
	@Query("select a from StockInfo a where EXISTS (" 
			+ " select d from StockDetail d where a.stockCode = d.stockCode and d.beginPrice<>0" 
			+ "	and d.stockDate>=?1 and d.stockDate<=?2)"
			+ " and NOT EXISTS ("
			+ " select d2 from StockDetail d2 where a.stockCode = d2.stockCode and "
			+ " ((d2.endPrice-d2.beginPrice)/d2.beginPrice) >?3 "
			+ " and d2.stockDate>=?1 and d2.stockDate<=?2)")
	Page<StockInfo> findWeakStocks(Date stockDateBegin,Date stockDateEnd,Double stockRange,Pageable pageable);

}