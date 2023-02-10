package kr.co.socsoft.stats.vo;

import egovframework.com.cmm.DefaultVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AccessVO extends DefaultVO {
    private String connDt;
    private String connIp;
    private String connBrowser;
    private String connUserId;
    private String sDate;
    private String eDate;
    private String logSeq;
    private String searchString;
    private String userId;
    private String userNm;
    private String searchField;
    private String category;
    private String menuCd;
    
	private String menuNm;
    private String category1depth;
    private String category2depth; 
    private String category3depth;
    private String categoryCd;
    private String categoryPath;
    private int categoryLevel;
    
  
    public int getCategoryLevel() {
		return categoryLevel;
	}



	public void setCategoryLevel(int categoryLevel) {
		this.categoryLevel = categoryLevel;
	}



	public String getCategory() {
		return category;
	}



	public void setCategory(String category) {
		this.category = category;
	}



	public String getMenuCd() {
		return menuCd;
	}



	public void setMenuCd(String menuCd) {
		this.menuCd = menuCd;
	}



	public String getMenuNm() {
		return menuNm;
	}



	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}



	public String getCategory1depth() {
		return category1depth;
	}



	public void setCategory1depth(String category1depth) {
		this.category1depth = category1depth;
	}



	public String getCategory2depth() {
		return category2depth;
	}



	public void setCategory2depth(String category2depth) {
		this.category2depth = category2depth;
	}



	public String getCategory3depth() {
		return category3depth;
	}



	public void setCategory3depth(String category3depth) {
		this.category3depth = category3depth;
	}



	public String getCategoryCd() {
		return categoryCd;
	}



	public void setCategoryCd(String categoryCd) {
		this.categoryCd = categoryCd;
	}



	public String getCategoryPath() {
		return categoryPath;
	}



	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}




    
    public String getSearchString() {
		return searchString;
	}



	public String getSearchField() {
		return searchField;
	}



	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getUserNm() {
		return userNm;
	}



	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}



	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}



	public String getLogSeq() {
		return logSeq;
	}



	public void setLogSeq(String logSeq) {
		this.logSeq = logSeq;
	}



	public String getConnDt() {
		return connDt;
	}



	public void setConnDt(String connDt) {
		this.connDt = connDt;
	}



	public String getConnIp() {
		return connIp;
	}



	public void setConnIp(String connIp) {
		this.connIp = connIp;
	}



	public String getConnBrowser() {
		return connBrowser;
	}



	public void setConnBrowser(String connBrowser) {
		this.connBrowser = connBrowser;
	}
 


	public String getConnUserId() {
		return connUserId;
	}



	public void setConnUserId(String connUserId) {
		this.connUserId = connUserId;
	}



	public String getsDate() {
		return sDate;
	}



	public void setsDate(String sDate) {
		this.sDate = sDate;
	}



	public String geteDate() {
		return eDate;
	}



	public void seteDate(String eDate) {
		this.eDate = eDate;
	}



	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
