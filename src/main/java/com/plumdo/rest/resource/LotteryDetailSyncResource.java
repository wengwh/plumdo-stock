package com.plumdo.rest.resource;

import java.io.IOException;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.plumdo.constant.ConfigConstant;
import com.plumdo.domain.LotteryDetail;
import com.plumdo.repository.LotteryDetailRepository;
import com.plumdo.rest.AbstractResource;
import com.plumdo.utils.DateUtils;
import com.plumdo.utils.ObjectUtils;

/**
 * 
 * 
 * @author wengwh
 * @Date 2017-02-06
 * 
 */
@RestController
public class LotteryDetailSyncResource extends AbstractResource {
	@Autowired
	private LotteryDetailRepository lotteryDetailRepository;

	@PostMapping("/lottery-details/sync")
	@ResponseStatus(HttpStatus.OK)
	public void syncStockInfos() throws IOException {
		int yearStart = ConfigConstant.START_LOTTERY_YEAR;
		LotteryDetail lotteryDetail = lotteryDetailRepository.findTopByOrderByLotteryYearDescLotteryPeriodDesc();
		if (lotteryDetail != null) {
			yearStart = lotteryDetail.getLotteryYear();
		}
		int yearEnd = DateUtils.getYear(new Date());
		while (yearStart <= yearEnd) {
			String url = ConfigConstant.LOTTERY_URL + yearStart + ".html";
			logger.debug("采集六合彩url:{}", url);
			Document doc = Jsoup.connect(url).get();
			Element element = doc.getElementById("main");
			Element tbody = element.getElementsByTag("tbody").get(0);
			Elements trElements = tbody.select(".infolist");
			for (Element trElement : trElements) {
				Elements tdElements = trElement.select("td");
				Date date = DateUtils.parseDate(tdElements.get(0).text());
				int lotteryYear = DateUtils.getYear(date);
				String lotteryPeriodStr = tdElements.get(1).text();
				int index = lotteryPeriodStr.indexOf("期");
				int lotteryPeriod = ObjectUtils.convertToInteger(lotteryPeriodStr.substring(0, index));
				String lotteryNumberStr = tdElements.get(2).text();
				String[] lotteryNumbers = lotteryNumberStr.split(" ");
				String lotteryCodeStr = tdElements.get(3).text();
				int lotteryCode = ObjectUtils.convertToInteger(lotteryCodeStr.split(" ")[0]);
				logger.debug("采集六合彩:lotteryYear:{}lotteryPeriodStr:{},lotteryCode:{}", lotteryYear, lotteryPeriod, lotteryCode);
				saveLotteryDetail(lotteryYear, lotteryPeriod, lotteryCode, lotteryNumbers);
			}
			yearStart++;
		}

	}

	private void saveLotteryDetail(int lotteryYear, int lotteryPeriod, int lotteryCode, String[] lotteryNumbers) {
		LotteryDetail lotteryDetail = lotteryDetailRepository.findFirstByLotteryYearAndLotteryPeriod(lotteryYear, lotteryPeriod);
		if (lotteryDetail == null) {
			lotteryDetail = new LotteryDetail();
			lotteryDetail.setLotteryYear(lotteryYear);
			lotteryDetail.setLotteryPeriod(lotteryPeriod);
		}
		lotteryDetail.setLotteryCode(lotteryCode);
		lotteryDetail.setLotteryN1(ObjectUtils.convertToInteger(lotteryNumbers[0]));
		lotteryDetail.setLotteryN2(ObjectUtils.convertToInteger(lotteryNumbers[2]));
		lotteryDetail.setLotteryN3(ObjectUtils.convertToInteger(lotteryNumbers[4]));
		lotteryDetail.setLotteryN4(ObjectUtils.convertToInteger(lotteryNumbers[6]));
		lotteryDetail.setLotteryN5(ObjectUtils.convertToInteger(lotteryNumbers[8]));
		lotteryDetail.setLotteryN6(ObjectUtils.convertToInteger(lotteryNumbers[10]));
		lotteryDetailRepository.save(lotteryDetail);
	}

}