package com.plumdo.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;


/**
 * The persistent class for the stock_monster database table.
 * 
 */
@Entity
@Table(name="stock_monster")
@DynamicInsert
@DynamicUpdate
public class StockMonster implements Serializable {
	private static final long serialVersionUID = 1L;
	private int monsterId;
	private Timestamp collectTime;
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
	public Timestamp getCollectTime() {
		return this.collectTime;
	}

	public void setCollectTime(Timestamp collectTime) {
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