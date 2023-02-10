package kr.co.socsoft.common.vo;

/**
 * CodeVO
 * editor G1
 *
 */
public class  CodeVO{
	private String cd;
	private String nm;
	private String detailCd;
	private String groupCd;
	private String detailNm;
	private String description;
	private String useYn;
	
	private String sortCol; 
	private String sort;
	
	private String where;
	
	public String getWhere() {
		return where;
	}
	public void setWhere(String where) {
		this.where = where;
	}
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	public String getNm() {
		return nm;
	}
	public void setNm(String nm) {
		this.nm = nm;
	}
	public String getDetailCd() {
		return detailCd;
	}
	public void setDetailCd(String detailCd) {
		this.detailCd = detailCd;
	}
	public String getGroupCd() {
		return groupCd;
	}
	public void setGroupCd(String groupCd) {
		this.groupCd = groupCd;
	}
	public String getDetailNm() {
		return detailNm;
	}
	public String getSortCol() {
		return sortCol;
	}
	public void setSortCol(String sortCol) {
		this.sortCol = sortCol;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public void setDetailNm(String detailNm) {
		this.detailNm = detailNm;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	
}