package com.plumdo.repository;


import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.plumdo.domain.StockGold;

public interface StockGoldRepository extends BaseRepository<StockGold, Integer> {
	@Query("delete from StockGold where stockDate=?1 ")
	@Transactional
	@Modifying
	int deleteStockGolds(Date stockDate);
}