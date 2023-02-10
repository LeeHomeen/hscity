package kr.co.socsoft.screen.vo;
/**
 * DATA관리 vo
 * editor G1
 *
 */
public class  ScreenVO{
	private String menuCd;
	private String mainCd;
	private String menuNm;
	private String description;
	private String menuTypeScd;
	private String menuUrl;
	private String mainLocNo;
	private String menuImgYn;
	private String menuImgUseYn;
	private byte[] menuImg;
	
	private String layout;
	
	private String type;
	private String createId;
	private String updateId;
	
	public String getMenuCd() {
		return menuCd;
	}
	public void setMenuCd(String menuCd) {
		this.menuCd = menuCd;
	}
	
	public String getMainCd() {
		return mainCd;
	}
	public void setMainCd(String mainCd) {
		this.mainCd = mainCd;
	}
	public String getMenuNm() {
		return menuNm;
	}
	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getMainLocNo() {
		return mainLocNo;
	}
	public void setMainLocNo(String mainLocNo) {
		this.mainLocNo = mainLocNo;
	}
	public String getMenuImgYn() {
		return menuImgYn;
	}
	public void setMenuImgYn(String menuImgYn) {
		this.menuImgYn = menuImgYn;
	}
	public String getMenuImgUseYn() {
		return menuImgUseYn;
	}
	public void setMenuImgUseYn(String menuImgUseYn) {
		this.menuImgUseYn = menuImgUseYn;
	}
	public byte[] getMenuImg() {
		return menuImg;
	}
	public void setMenuImg(byte[] menuImg) {
		this.menuImg = menuImg;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	public String getUpdateId() {
		return updateId;
	}
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLayout() {
		return layout;
	}
	public void setLayout(String layout) {
		this.layout = layout;
	}
	
	
}