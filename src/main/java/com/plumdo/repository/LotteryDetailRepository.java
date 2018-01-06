package com.plumdo.repository;

import com.plumdo.domain.LotteryDetail;

public interface LotteryDetailRepository extends BaseRepository<LotteryDetail, Integer> {
	LotteryDetail findFirstByLotteryYearAndLotteryPeriod(int lotteryYear,int lotteryPeriod);
	
	LotteryDetail findTopByOrderByLotteryYearDescLotteryPeriodDesc();
}