package com.search.search.bean;

import java.util.List;

public class Page<T> {

	public List<T> data;
	
	public Long limit = 0L;
	
	public Long page = 0L;
	
	public Long recordsTotal = 0L;
	public Long recordsFiltered = 0L;
	public String draw = "";

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public Long getPage() {
		return page;
	}

	public void setPage(Long page) {
		this.page = page==null?0L:page;
	}

	public Long getLimit() {
		return limit;
	}

	public void setLimit(Long limit) {
		this.limit = limit;
	}

	public Long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public String getDraw() {
		return draw;
	}

	public void setDraw(String draw) {
		this.draw = draw;
	}
	
}
