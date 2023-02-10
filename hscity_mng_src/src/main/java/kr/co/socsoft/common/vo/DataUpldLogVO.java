package kr.co.socsoft.common.vo;

/**
 * DataUpldLogVO
 * editor G1
 *
 */
public class  DataUpldLogVO {
	private int upldLogSeq;
	private String tableId;
	private String tableNm;
	private String dataConnScd;
	private String logTypeScd;
	private String logDate;
	private String logMsg;
	private String createId;
	public int getUpldLogSeq() {
		return upldLogSeq;
	}
	public void setUpldLogSeq(int upldLogSeq) {
		this.upldLogSeq = upldLogSeq;
	}
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public String getTableNm() {
		return tableNm;
	}
	public void setTableNm(String tableNm) {
		this.tableNm = tableNm;
	}
	public String getDataConnScd() {
		return dataConnScd;
	}
	public void setDataConnScd(String dataConnScd) {
		this.dataConnScd = dataConnScd;
	}
	public String getLogTypeScd() {
		return logTypeScd;
	}
	public void setLogTypeScd(String logTypeScd) {
		this.logTypeScd = logTypeScd;
	}
	public String getLogDate() {
		return logDate;
	}
	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}
	public String getLogMsg() {
		return logMsg;
	}
	public void setLogMsg(String logMsg) {
		this.logMsg = logMsg;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
}