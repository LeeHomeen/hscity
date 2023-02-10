package kr.co.socsoft.menu.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class CategoryVo {
	private String pubCategoryCd;
	private String pubCategoryNm;
	private String upperPubCategoryCd;
	private String bizCategoryCd;
	private String bizCategoryNm;
	private String upperBizCategoryCd;
	
	private int sortNo;
	private String description;
	private String rmk;
	private String useYn;
	private String createId;
	private String createDt;
	private String updateId;
	private String updateDt;
	
	private String category1depth;
	private String category2depth;
	private String category3depth;
	private String srchGbn;
	private String srchText;
	private String categoryType;
	private String upperCategoryCd;
	private int level;
	private String crud;
	
	private String menuCd;
	private String menuNm;
	private String categoryCd;
	private String categoryNm;
	private String categoryPath;
	private String contents;
	private String menuTypeScd;
	private String menuRoleScd;
	private String menuUrl;
	private String mainCd;
	private String mainLocNo;
	private String mainUseYn;
	private byte[] menuImg;
	private String menuImgSrc;
	private String menuImgUseYn;
	
	private String layout;
	
	private int seq;
	private String fileNm;
	private String fileSize;
	private byte[] fileData;
	
	private String gbnName;
	private String deptId;
	private String deptName;
	private String deptOrder;
	private String upperDeptId;
	private String userId;
	private String userName;
	private String userStatName;
	private String searchType;
	private String searchText;
	private int userCnt;
	
	public String getPubCategoryCd() {
		return pubCategoryCd;
	}
	public void setPubCategoryCd(String pubCategoryCd) {
		this.pubCategoryCd = pubCategoryCd;
	}
	public String getPubCategoryNm() {
		return pubCategoryNm;
	}
	public void setPubCategoryNm(String pubCategoryNm) {
		this.pubCategoryNm = pubCategoryNm;
	}
	public String getUpperPubCategoryCd() {
		return upperPubCategoryCd;
	}
	public void setUpperPubCategoryCd(String upperPubCategoryCd) {
		this.upperPubCategoryCd = upperPubCategoryCd;
	}
	public String getBizCategoryCd() {
		return bizCategoryCd;
	}
	public void setBizCategoryCd(String bizCategoryCd) {
		this.bizCategoryCd = bizCategoryCd;
	}
	public String getBizCategoryNm() {
		return bizCategoryNm;
	}
	public void setBizCategoryNm(String bizCategoryNm) {
		this.bizCategoryNm = bizCategoryNm;
	}
	public String getUpperBizCategoryCd() {
		return upperBizCategoryCd;
	}
	public void setUpperBizCategoryCd(String upperBizCategoryCd) {
		this.upperBizCategoryCd = upperBizCategoryCd;
	}
	public int getSortNo() {
		return sortNo;
	}
	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRmk() {
		return rmk;
	}
	public void setRmk(String rmk) {
		this.rmk = rmk;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	public String getCreateDt() {
		return createDt;
	}
	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}
	public String getUpdateId() {
		return updateId;
	}
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}
	public String getUpdateDt() {
		return updateDt;
	}
	public void setUpdateDt(String updateDt) {
		this.updateDt = updateDt;
	}
	public String getCrud() {
		return crud;
	}
	public void setCrud(String crud) {
		this.crud = crud;
	}
	public String getCategoryType() {
		return categoryType;
	}
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}
	public String getCategoryCd() {
		return categoryCd;
	}
	public void setCategoryCd(String categoryCd) {
		this.categoryCd = categoryCd;
	}
	public String getCategoryNm() {
		return categoryNm;
	}
	public void setCategoryNm(String categoryNm) {
		this.categoryNm = categoryNm;
	}
	public String getUpperCategoryCd() {
		return upperCategoryCd;
	}
	public void setUpperCategoryCd(String upperCategoryCd) {
		this.upperCategoryCd = upperCategoryCd;
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
	public String getSrchGbn() {
		return srchGbn;
	}
	public void setSrchGbn(String srchGbn) {
		this.srchGbn = srchGbn;
	}
	public String getSrchText() {
		return srchText;
	}
	public void setSrchText(String srchText) {
		this.srchText = srchText;
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
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getMenuTypeScd() {
		return menuTypeScd;
	}
	public void setMenuTypeScd(String menuTypeScd) {
		this.menuTypeScd = menuTypeScd;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public String getMainCd() {
		return mainCd;
	}
	public void setMainCd(String mainCd) {
		this.mainCd = mainCd;
	}
	public String getMainLocNo() {
		return mainLocNo;
	}
	public void setMainLocNo(String mainLocNo) {
		this.mainLocNo = mainLocNo;
	}
	public String getMainUseYn() {
		return mainUseYn;
	}
	public void setMainUseYn(String mainUseYn) {
		this.mainUseYn = mainUseYn;
	}
	public String getCategoryPath() {
		return categoryPath;
	}
	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}
	public byte[] getMenuImg() {
		return menuImg;
	}
	public void setMenuImg(byte[] menuImg) {
		this.menuImg = menuImg;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getFileNm() {
		return fileNm;
	}
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public byte[] getFileData() {
		return fileData;
	}
	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}
	public String getMenuImgSrc() {
		return menuImgSrc;
	}
	public void setMenuImgSrc(String menuImgSrc) {
		this.menuImgSrc = menuImgSrc;
	}
	public String getMenuRoleScd() {
		return menuRoleScd;
	}
	public void setMenuRoleScd(String menuRoleScd) {
		this.menuRoleScd = menuRoleScd;
	}
	public String getMenuImgUseYn() {
		return menuImgUseYn;
	}
	public void setMenuImgUseYn(String menuImgUseYn) {
		this.menuImgUseYn = menuImgUseYn;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGbnName() {
		return gbnName;
	}
	public void setGbnName(String gbnName) {
		this.gbnName = gbnName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptOrder() {
		return deptOrder;
	}
	public void setDeptOrder(String deptOrder) {
		this.deptOrder = deptOrder;
	}
	public String getUpperDeptId() {
		return upperDeptId;
	}
	public void setUpperDeptId(String upperDeptId) {
		this.upperDeptId = upperDeptId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserStatName() {
		return userStatName;
	}
	public void setUserStatName(String userStatName) {
		this.userStatName = userStatName;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public int getUserCnt() {
		return userCnt;
	}
	public void setUserCnt(int userCnt) {
		this.userCnt = userCnt;
	}
	public String getLayout() {
		return layout;
	}
	public void setLayout(String layout) {
		this.layout = layout;
	}
	
}
