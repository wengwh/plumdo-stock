package com.plumdo.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;


/**
 * The persistent class for the stock_hot_plate database table.
 * 
 */
@Entity
@Table(name="stock_hot_plate")
@DynamicInsert
@DynamicUpdate
public class StockHotPlate implements Serializable {
	private static final long serialVersionUID = 1L;
	private int hotPlateId;
	private Timestamp collectTime;
	private String plateName;

	public StockHotPlate() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="hot_plate_id")
	public int getHotPlateId() {
		return this.hotPlateId;
	}

	public void setHotPlateId(int hotPlateId) {
		this.hotPlateId = hotPlateId;
	}


	@Column(name="collect_time")
	public Timestamp getCollectTime() {
		return this.collectTime;
	}

	public void setCollectTime(Timestamp collectTime) {
		this.collectTime = collectTime;
	}


	@Column(name="plate_name")
	public String getPlateName() {
		return this.plateName;
	}

	public void setPlateName(String plateName) {
		this.plateName = plateName;
	}

}