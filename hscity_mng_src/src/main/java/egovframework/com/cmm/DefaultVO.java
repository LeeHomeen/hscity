package egovframework.com.cmm;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;


public class DefaultVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 검색조건
     */
    private String searchCondition = "";

    /**
     * 검색Keyword
     */
    private String searchKeyword = "";

    /**
     * 검색사용여부
     */
    private String searchUseYn = "";

    /**
     * 검색String
     */
    private String searchString = "";

    /**
     * 검색Field
     */
    private String searchField = "";

    /**
     * 검색Operate
     */
    private String searchOperate = "";

    /**
     * 정렬 컬럼
     */
    private String orderByColumn = "";

    /**
     * 정렬 방법
     */
    private String orderBySord = "";

    /**
     * 현재페이지
     */
    private int pageIndex = 1;

    /**
     * 페이지갯수
     */
    private int pageUnit = 10;

    /**
     * 페이지사이즈
     */
    private int pageSize = 10;

    /**
     * firstIndex
     */
    private int firstIndex = 1;

    /**
     * lastIndex
     */
    private int lastIndex = 1;

    /**
     * recordCountPerPage
     */
    private int recordCountPerPage = 10;

    /**
     * 검색KeywordFrom
     */
    private String searchKeywordFrom = "";

    /**
     * 검색KeywordTo
     */
    private String searchKeywordTo = "";

    /**
     * 로우 넘버
     */
    private int rownum = 0;

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getRownum() {
        return rownum;
    }

    public void setRownum(int rownum) {
        this.rownum = rownum;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    public void setFirstIndex(int firstIndex) {
        this.firstIndex = firstIndex;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

    public int getRecordCountPerPage() {
        return recordCountPerPage;
    }

    public void setRecordCountPerPage(int recordCountPerPage) {
        this.recordCountPerPage = recordCountPerPage;
    }

    public String getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(String searchCondition) {
        this.searchCondition = searchCondition;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public String getSearchUseYn() {
        return searchUseYn;
    }

    public void setSearchUseYn(String searchUseYn) {
        this.searchUseYn = searchUseYn;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageUnit() {
        return pageUnit;
    }

    public void setPageUnit(int pageUnit) {
        this.pageUnit = pageUnit;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * searchKeywordFrom attribute를 리턴한다.
     *
     * @return String
     */
    public String getSearchKeywordFrom() {
        return searchKeywordFrom;
    }

    /**
     * searchKeywordFrom attribute 값을 설정한다.
     *
     * @param searchKeywordFrom String
     */
    public void setSearchKeywordFrom(String searchKeywordFrom) {
        this.searchKeywordFrom = searchKeywordFrom;
    }

    /**
     * searchKeywordTo attribute를 리턴한다.
     *
     * @return String
     */
    public String getSearchKeywordTo() {
        return searchKeywordTo;
    }

    /**
     * searchKeywordTo attribute 값을 설정한다.
     *
     * @param searchKeywordTo String
     */
    public void setSearchKeywordTo(String searchKeywordTo) {
        this.searchKeywordTo = searchKeywordTo;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSearchOperate() {
        return searchOperate;
    }

    public void setSearchOperate(String searchOperate) {
        this.searchOperate = searchOperate;
    }

    public String getOrderByColumn() {
        return orderByColumn;
    }

    public void setOrderByColumn(String orderByColumn) {
        this.orderByColumn = orderByColumn;
    }

    public String getOrderBySord() {
        return orderBySord;
    }

    public void setOrderBySord(String orderBySord) {
        this.orderBySord = orderBySord;
    }
}
