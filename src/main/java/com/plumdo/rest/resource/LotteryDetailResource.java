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

import com.plumdo.domain.LotteryDetail;
import com.plumdo.jpa.Criteria;
import com.plumdo.jpa.Restrictions;
import com.plumdo.repository.LotteryDetailRepository;
import com.plumdo.rest.AbstractResource;
import com.plumdo.rest.PageResponse;

/**
 * 六合彩详情数据控制层实现类
 * 
 * @author wengwh
 * @Date 2017-02-06
 * 
 */
@RestController
public class LotteryDetailResource extends AbstractResource {

	@Autowired
	private LotteryDetailRepository lotteryDetailRepository;

	private LotteryDetail getLotteryDetailFromRequest(int id) {
		LotteryDetail lotteryDetail = lotteryDetailRepository.findOne(id);
		if (lotteryDetail == null) {
			exceptionFactory.throwObjectNotFound(id);
		}
		return lotteryDetail;
	}

	@GetMapping("/lottery-details")
	@ResponseStatus(HttpStatus.OK)
	public PageResponse<LotteryDetail> getLotteryDetails(@RequestParam Map<String, String> allRequestParams) {
		Criteria<LotteryDetail> criteria = new Criteria<LotteryDetail>();
		criteria.add(Restrictions.eq("lotteryYear", allRequestParams.get("lotteryYear"), true));
		criteria.add(Restrictions.eq("lotteryPeriod", allRequestParams.get("lotteryPeriod"), true));
		criteria.add(Restrictions.eq("lotteryN1", allRequestParams.get("lotteryN1"), true));
		criteria.add(Restrictions.eq("lotteryN2", allRequestParams.get("lotteryN2"), true));
		criteria.add(Restrictions.eq("lotteryN3", allRequestParams.get("lotteryN3"), true));
		criteria.add(Restrictions.eq("lotteryN4", allRequestParams.get("lotteryN4"), true));
		criteria.add(Restrictions.eq("lotteryN5", allRequestParams.get("lotteryN5"), true));
		criteria.add(Restrictions.eq("lotteryN6", allRequestParams.get("lotteryN6"), true));
		criteria.add(Restrictions.eq("lotteryCode", allRequestParams.get("lotteryCode"), true));
		return createPageResponse(lotteryDetailRepository.findAll(criteria, getPageable(allRequestParams)));
	}

	@PostMapping("/lottery-details")
	@ResponseStatus(HttpStatus.CREATED)
	public LotteryDetail createLotteryDetail(@RequestBody LotteryDetail lotteryDetailRequest) {
		return lotteryDetailRepository.save(lotteryDetailRequest);
	}

	@DeleteMapping("/lottery-details/{detailId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteLotteryDetail(@PathVariable Integer detailId) {
		LotteryDetail lotteryDetail = getLotteryDetailFromRequest(detailId);
		lotteryDetailRepository.delete(lotteryDetail);
	}

	@PostMapping("/lottery-details/batch-delete")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void batchDeleteStockHotPlate(@RequestBody List<Integer> detailIds) {
		List<LotteryDetail> deleteLotteryDetails = new ArrayList<>();
		for (Integer detailId : detailIds) {
			deleteLotteryDetails.add(getLotteryDetailFromRequest(detailId));
		}
		lotteryDetailRepository.delete(deleteLotteryDetails);
	}

	@PutMapping("/lottery-details/{detailId}")
	@ResponseStatus(HttpStatus.OK)
	public LotteryDetail updateLotteryDetail(@PathVariable Integer detailId,
			@RequestBody LotteryDetail lotteryDetailRequest) {
		LotteryDetail lotteryDetail = getLotteryDetailFromRequest(detailId);
		lotteryDetail.setLotteryCode(lotteryDetailRequest.getLotteryCode());
		lotteryDetail.setLotteryPeriod(lotteryDetailRequest.getLotteryPeriod());
		lotteryDetail.setLotteryYear(lotteryDetailRequest.getLotteryYear());
		lotteryDetail.setLotteryN1(lotteryDetailRequest.getLotteryN1());
		lotteryDetail.setLotteryN2(lotteryDetailRequest.getLotteryN2());
		lotteryDetail.setLotteryN3(lotteryDetailRequest.getLotteryN3());
		lotteryDetail.setLotteryN4(lotteryDetailRequest.getLotteryN4());
		lotteryDetail.setLotteryN5(lotteryDetailRequest.getLotteryN5());
		lotteryDetail.setLotteryN6(lotteryDetailRequest.getLotteryN6());
		return lotteryDetailRepository.save(lotteryDetail);
	}

	@GetMapping("/lottery-details/{detailId}")
	@ResponseStatus(HttpStatus.OK)
	public LotteryDetail getLotteryDetail(@PathVariable Integer detailId) {
		return getLotteryDetailFromRequest(detailId);
	}

}