package com.plumdo.rest.resource;

import java.io.IOException;

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
import com.plumdo.domain.StockInfo;
import com.plumdo.repository.StockInfoRepository;
import com.plumdo.rest.AbstractResource;
import com.plumdo.utils.ObjectUtils;

/**
 * 
 * 
 * @author wengwh
 * @Date 2017-02-06
 * 
 */
@RestController
public class StockInfoSyncResource extends AbstractResource {
	@Autowired
	private StockInfoRepository stockInfoRepository;

	@PostMapping("/stock-infos/sync")
	@ResponseStatus(HttpStatus.OK)
	public void syncStockInfos() throws IOException {
		Document doc = Jsoup.connect(ConfigConstant.STOCK_INFO_URL).get();
		Element element = doc.getElementById("quotesearch");
		Elements ulElements = element.select("ul");
		for (Element ulElement : ulElements) {
			Elements liElements = ulElement.select("li");
			for (Element liElement : liElements) {
				Elements children = liElement.children();
				if (!children.isEmpty()) {
					String stockInfo = children.get(0).text();
					if (ObjectUtils.isNotEmpty(stockInfo)) {
						stockInfo = stockInfo.substring(0, stockInfo.length() - 1);
						String[] stockInfos = stockInfo.split("\\(");
						String stockName = stockInfos[0];
						String stockCode = stockInfos[1];
						if (stockCode.startsWith(ConfigConstant.SH_STOCK_PRE)) {
							saveStockInfo(stockCode, stockName, ConfigConstant.STOCK_TYPE_SH);
						} else if (stockCode.startsWith(ConfigConstant.SZ_STOCK_PRE)) {
							saveStockInfo(stockCode, stockName, ConfigConstant.STOCK_TYPE_SZ);
						} else if (stockCode.startsWith(ConfigConstant.CY_STOCK_PRE)) {
							saveStockInfo(stockCode, stockName, ConfigConstant.STOCK_TYPE_SZ);
						}
					}
				}
			}
		}
	}

	private void saveStockInfo(String stockCode, String stockName, String stockType) {
		StockInfo stockInfo = stockInfoRepository.findFirstByStockCode(stockCode);
		if (stockInfo != null && stockInfo.getStockName().equals(stockName) && stockInfo.getStockType().equals(stockType)) {
			return;
		}
		if (stockInfo == null) {
			stockInfo = new StockInfo();
			stockInfo.setStockCode(stockCode);
		}
		stockInfo.setStockName(stockName);
		stockInfo.setStockType(stockType);
		stockInfoRepository.save(stockInfo);
	}

}