package com.plumdo.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * The persistent class for the stock_monster database table.
 * 
 */
@Entity
@Table(name="stock_monster", catalog="plumdo_stock")
@DynamicInsert
@DynamicUpdate
public class StockMonster implements Serializable {
	private static final long serialVersionUID = 1L;
	private int monsterId;
	private Date collectTime;
	private String stockCode;
	private String stockName;

	public StockMonster() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="monster_id")
	public int getMonsterId() {
		return this.monsterId;
	}

	public void setMonsterId(int monsterId) {
		this.monsterId = monsterId;
	}


	@Column(name="collect_time")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public Date getCollectTime() {
		return this.collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}


	@Column(name="stock_code")
	public String getStockCode() {
		return this.stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}


	@Column(name="stock_name")
	public String getStockName() {
		return this.stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

}