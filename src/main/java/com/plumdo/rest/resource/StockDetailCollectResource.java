package com.plumdo.rest.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.plumdo.constant.ConfigConstant;
import com.plumdo.domain.StockDetail;
import com.plumdo.domain.StockGold;
import com.plumdo.domain.StockInfo;
import com.plumdo.repository.StockDetailRepository;
import com.plumdo.repository.StockGoldRepository;
import com.plumdo.repository.StockInfoRepository;
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
public class StockDetailCollectResource extends AbstractResource {
	@Autowired
	private StockDetailRepository stockDetailRepository;
	@Autowired
	private StockInfoRepository  stockInfoRepository;
	@Autowired
	private StockGoldRepository stockGoldRepository;
	@Autowired
	private RestTemplate restTemplate;

	@PutMapping("/stock-details/collect")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Transactional
	public void collectStockDetails(@RequestParam(value = "threadNum") int threadNum) {
		List<StockInfo> stockInfos = stockInfoRepository.findAll();
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threadNum);
		int loopNum = stockInfos.size() % 10 == 0 ? stockInfos.size() / 10 : stockInfos.size() / 10 + 1;
		CountDownLatch countDownLatch = new CountDownLatch(loopNum);
		StringBuffer sbParam = new StringBuffer();
		for (int i = 0; i < stockInfos.size(); i++) {
			StockInfo stockInfo = stockInfos.get(i);
			sbParam.append(stockInfo.getStockType()).append(stockInfo.getStockCode()).append(",");
			if ((i + 1) % 10 == 0 || (i + 1) == stockInfos.size()) {
				fixedThreadPool.execute(new CollectDataThread(sbParam.toString(), countDownLatch));
				sbParam.delete(0, sbParam.length());
			}
		}
		fixedThreadPool.execute(new InsertGoldThread(countDownLatch));
	}

	class CollectDataThread implements Runnable {
		private String sParam;
		private CountDownLatch countDownLatch;

		public CollectDataThread(String sParam, CountDownLatch countDownLatch) {
			this.sParam = sParam;
			this.countDownLatch = countDownLatch;
		}

		public void run() {
			try {
				String httpResult = restTemplate.getForObject(ConfigConstant.COLLECT_STOCK_URL + sParam, String.class);
				stockDetailRepository.save(convertResult2Details(httpResult));
			} finally {
				countDownLatch.countDown();
			}
		}

		private List<StockDetail> convertResult2Details(String result) {
			List<StockDetail> stockDetails = new ArrayList<StockDetail>();
			result = result.replaceAll("\\t|\r|\n", "");
			String[] aResult = result.split(";");
			for (String stock : aResult) {
				if (ObjectUtils.isNotEmpty(stock) && stock.contains(",")) {
					StockDetail stockDetail = new StockDetail();
					stockDetail.setStockCode(stock.substring(13, 19));
					String[] aStock = stock.substring(21, stock.length() - 2).split(",");
					stockDetail.setStockName(aStock[0]);
					stockDetail.setBeginPrice(ObjectUtils.convertToBigDecimal(aStock[1]));
					stockDetail.setEndPrice(ObjectUtils.convertToBigDecimal(aStock[3]));
					stockDetail.setHighestPrice(ObjectUtils.convertToBigDecimal(aStock[4]));
					stockDetail.setLatestPrice(ObjectUtils.convertToBigDecimal(aStock[5]));
					stockDetail.setStockNum(ObjectUtils.convertToInteger(aStock[8]));
					stockDetail.setStockMoney(ObjectUtils.convertToBigDecimal(aStock[9]));
					stockDetail.setStockDate(ObjectUtils.convertToTimestap(aStock[30]));
					stockDetails.add(stockDetail);
				}
			}
			return stockDetails;
		}
	}

	class InsertGoldThread implements Runnable {
		private CountDownLatch countDownLatch;

		public InsertGoldThread(CountDownLatch countDownLatch) {
			this.countDownLatch = countDownLatch;
		}

		public void run() {
			try {
				countDownLatch.await();
				List<StockGold> stockGolds = new ArrayList<StockGold>();
				List<StockDetail> stockDetails = stockDetailRepository.findStockGolds(DateUtils.getCurrentDay(), DateUtils.getYesterdayOutWeek());
				StringBuffer weiBoContent = new StringBuffer("今日跳开股票:");
				if (stockDetails != null && stockDetails.size() > 0) {
					for (StockDetail stockDetail : stockDetails) {
						StockGold stockGold = new StockGold();
						stockGold.setStockCode(stockDetail.getStockCode());
						stockGold.setStockName(stockDetail.getStockName());
						stockGold.setStockDate(stockDetail.getStockDate());
						stockGold.setStockMoney(stockDetail.getStockMoney());
						stockGold.setStockNum(stockDetail.getStockNum());
						stockGold.setBeginPrice(stockDetail.getBeginPrice());
						stockGold.setEndPrice(stockDetail.getEndPrice());
						stockGold.setHighestPrice(stockDetail.getHighestPrice());
						stockGold.setLatestPrice(stockDetail.getLatestPrice());
						stockGolds.add(stockGold);
						weiBoContent.append(stockDetail.getStockCode()).append(":").append(stockDetail.getStockName()).append(",");
					}
					stockGoldRepository.save(stockGolds);
				} else {
					weiBoContent.append("暂无");
				}

				sendWeiBo(weiBoContent);

			} catch (InterruptedException e) {
				logger.error("获取黄金股异常", e);
			}
		}

		private void sendWeiBo(StringBuffer weiBoContent) throws InterruptedException {
			List<String> statusList = new ArrayList<String>();
			while (weiBoContent.length() > 0) {
				String status = "";
				if (weiBoContent.length() > 120) {
					status = weiBoContent.substring(0, weiBoContent.indexOf(",", 120));
					weiBoContent.delete(0, weiBoContent.indexOf(",", 120) + 1);
				} else {
					status = weiBoContent.toString();
					weiBoContent.delete(0, weiBoContent.length());
				}
				statusList.add(status);
			}
			for (int i = statusList.size(); i > 0; i--) {
				Map<String,String> request = new HashMap<>();
				request.put("access_token", "2.00xgGTSEFWN8wD43a3c832af1bPXLC");
				request.put("status", statusList.get(i - 1));
				restTemplate.postForLocation(ConfigConstant.SEND_WEIBO_URL, request);
				Thread.sleep(5000L);
			}

		}
	}

}