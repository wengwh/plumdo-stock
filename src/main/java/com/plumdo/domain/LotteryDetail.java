package com.plumdo.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


/**
 * The persistent class for the lottery_detail database table.
 * 
 */
@Entity
@Table(name="lottery_detail")
@DynamicInsert
@DynamicUpdate
public class LotteryDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	private int detailId;
	private int lotteryCode;
	private int lotteryN1;
	private int lotteryN2;
	private int lotteryN3;
	private int lotteryN4;
	private int lotteryN5;
	private int lotteryN6;
	private int lotteryPeriod;
	private int lotteryYear;

	public LotteryDetail() {
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


	@Column(name="lottery_code")
	public int getLotteryCode() {
		return this.lotteryCode;
	}

	public void setLotteryCode(int lotteryCode) {
		this.lotteryCode = lotteryCode;
	}


	@Column(name="lottery_n1")
	public int getLotteryN1() {
		return this.lotteryN1;
	}

	public void setLotteryN1(int lotteryN1) {
		this.lotteryN1 = lotteryN1;
	}


	@Column(name="lottery_n2")
	public int getLotteryN2() {
		return this.lotteryN2;
	}

	public void setLotteryN2(int lotteryN2) {
		this.lotteryN2 = lotteryN2;
	}


	@Column(name="lottery_n3")
	public int getLotteryN3() {
		return this.lotteryN3;
	}

	public void setLotteryN3(int lotteryN3) {
		this.lotteryN3 = lotteryN3;
	}


	@Column(name="lottery_n4")
	public int getLotteryN4() {
		return this.lotteryN4;
	}

	public void setLotteryN4(int lotteryN4) {
		this.lotteryN4 = lotteryN4;
	}


	@Column(name="lottery_n5")
	public int getLotteryN5() {
		return this.lotteryN5;
	}

	public void setLotteryN5(int lotteryN5) {
		this.lotteryN5 = lotteryN5;
	}


	@Column(name="lottery_n6")
	public int getLotteryN6() {
		return this.lotteryN6;
	}

	public void setLotteryN6(int lotteryN6) {
		this.lotteryN6 = lotteryN6;
	}


	@Column(name="lottery_period")
	public int getLotteryPeriod() {
		return this.lotteryPeriod;
	}

	public void setLotteryPeriod(int lotteryPeriod) {
		this.lotteryPeriod = lotteryPeriod;
	}


	@Column(name="lottery_year")
	public int getLotteryYear() {
		return this.lotteryYear;
	}

	public void setLotteryYear(int lotteryYear) {
		this.lotteryYear = lotteryYear;
	}

}