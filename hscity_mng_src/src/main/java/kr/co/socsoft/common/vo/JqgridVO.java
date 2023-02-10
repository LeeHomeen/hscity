package kr.co.socsoft.common.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class JqgridVO {
	/**
	 * 페이지 번호
	 * */
	private Integer page;
	
	/**
	 * 행 건수
	 * */
	private Integer rows;
	
	/**
	 * 정렬 인덱스
	 * */
	private String sidx;
	
	/**
	 * 오름차순, 내림차순
	 * */
	private String sord;
	
	/**
	 * 검색항목
	 * */
	private String searchField;
	
	/**
	 * 검색키워드
	 * */
	private String searchString;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
