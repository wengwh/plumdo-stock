package com.plumdo.rest;


import java.util.List;

public class PageResponse<T> {
	protected List<T> data;
	protected int pageNum;
	protected int pageSize;
	protected int pageTotal;
	protected long dataTotal;
	protected long startNum;
	protected long endNum;


	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}


	public int getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}

	public long getDataTotal() {
		return dataTotal;
	}

	public void setDataTotal(long dataTotal) {
		this.dataTotal = dataTotal;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getStartNum() {
		return startNum;
	}

	public void setStartNum(long startNum) {
		this.startNum = startNum;
	}

	public long getEndNum() {
		return endNum;
	}

	public void setEndNum(long endNum) {
		this.endNum = endNum;
	}


}