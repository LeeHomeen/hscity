package kr.co.socsoft.permission.vo;

/**
 * DATA관리 vo
 * editor G1
 *
 */
public class  LogSyncUserVO{
	private int logSeq;
	private String logDate;
	private String logTypeScd;
	private String logType;

	private String logTypeScdNm;
	private String stdYm1;
	private String srch;
	private String logMsg;
	private String createId;

	
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	
	
	public String getSrch() {
		return srch;
	}
	public void setSrch(String srch) {
		this.srch = srch;
	}
	public String getStdYm1() {
		return stdYm1;
	}
	public void setStdYm1(String stdYm1) {
		this.stdYm1 = stdYm1;
	}
	public String getStdYm2() {
		return stdYm2;
	}
	public void setStdYm2(String stdYm2) {
		this.stdYm2 = stdYm2;
	}
	private String stdYm2;
	
	
	public String getLogTypeScdNm() {
		return logTypeScdNm;
	}
	public void setLogTypeScdNm(String logTypeScdNm) {
		this.logTypeScdNm = logTypeScdNm;
	}

	
	public int getLogSeq() {
		return logSeq;
	}
	public void setLogSeq(int logSeq) {
		this.logSeq = logSeq;
	}

	public String getLogDate() {
		return logDate;
	}
	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}
	public String getLogTypeScd() {
		return logTypeScd;
	}
	public void setLogTypeScd(String logTypeScd) {
		this.logTypeScd = logTypeScd;
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