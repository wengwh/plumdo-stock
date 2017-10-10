package com.plumdo.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the stock_detail database table.
 * 
 */
@Entity
@Table(name="stock_detail")
@NamedQuery(name="StockDetail.findAll", query="SELECT s FROM StockDetail s")
public class StockDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	private int detailId;
	private BigDecimal beginPrice;
	private BigDecimal endPrice;
	private BigDecimal highestPrice;
	private BigDecimal latestPrice;
	private String stockCode;
	private Timestamp stockDate;
	private BigDecimal stockMoney;
	private String stockName;
	private int stockNum;

	public StockDetail() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="detail_id")
	public int getDetailId() {
		return this.detailId;
	}

	public void setDetailId(int detailId) {
		this.detailId = detailId;
	}


	@Column(name="begin_price")
	public BigDecimal getBeginPrice() {
		return this.beginPrice;
	}

	public void setBeginPrice(BigDecimal beginPrice) {
		this.beginPrice = beginPrice;
	}


	@Column(name="end_price")
	public BigDecimal getEndPrice() {
		return this.endPrice;
	}

	public void setEndPrice(BigDecimal endPrice) {
		this.endPrice = endPrice;
	}


	@Column(name="highest_price")
	public BigDecimal getHighestPrice() {
		return this.highestPrice;
	}

	public void setHighestPrice(BigDecimal highestPrice) {
		this.highestPrice = highestPrice;
	}


	@Column(name="latest_price")
	public BigDecimal getLatestPrice() {
		return this.latestPrice;
	}

	public void setLatestPrice(BigDecimal latestPrice) {
		this.latestPrice = latestPrice;
	}


	@Column(name="stock_code")
	public String getStockCode() {
		return this.stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}


	@Column(name="stock_date")
	public Timestamp getStockDate() {
		return this.stockDate;
	}

	public void setStockDate(Timestamp stockDate) {
		this.stockDate = stockDate;
	}


	@Column(name="stock_money")
	public BigDecimal getStockMoney() {
		return this.stockMoney;
	}

	public void setStockMoney(BigDecimal stockMoney) {
		this.stockMoney = stockMoney;
	}


	@Column(name="stock_name")
	public String getStockName() {
		return this.stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}


	@Column(name="stock_num")
	public int getStockNum() {
		return this.stockNum;
	}

	public void setStockNum(int stockNum) {
		this.stockNum = stockNum;
	}

}